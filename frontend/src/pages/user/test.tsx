import React, { useEffect, useState } from "react";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import axios from "axios";
import { api } from "../../apis/Base";

interface MessageReq {
  dealCurPrice: number;
}

interface MessageRes {
  id: string; //bidlogid
  localDateTime: string;
  user_id: number;
  chattingRoomId: number;
  bid_log_price: number;
}

function TestPage() {
  const chattingRoomId = 91;

  const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
  const [messages, setMessages] = useState<MessageRes[]>([]);
  const [newMessage, setNewMessage] = useState<string>("");

  const loadMessages = async () => {
    try {
      const accessToken = sessionStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("Access token is not available.");
      }

      const response = await api.get(`/exchanges/${chattingRoomId}/suggestion`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      });

      console.log(response.data.data_body);
      setMessages(response.data.data_body);
    } catch (error) {
      console.error("채팅 내역 로드 실패", error);
    }
  };

  useEffect(() => {
    loadMessages();

    const socket = new SockJS("/websocket");
    const client = Stomp.over(socket);

    client.connect(
      {},
      () => {
        console.log("WebSocket 연결됨");
        client.subscribe(
          `/sub/chattings/${chattingRoomId}/messages`,
          (message) => {
            const msg: MessageRes = JSON.parse(message.body);
            setMessages((prevMessages) => [...prevMessages, msg]);
          }
        );
      },
      (error) => {
        console.error("WebSocket 연결 실패", error);
      }
    );

    setStompClient(client);

    return () => {
      if (client.connected) {
        client.disconnect(() => {
            console.log("Disconnected from WebSocket server");
        });
      }
    };
  }, [chattingRoomId]);

  const sendMessage = async () => {
    if (stompClient && stompClient.connected && newMessage.trim() !== "") {
        
      try {
        const messageReq = {
          dealCurPrice: newMessage,
        };

        const accessToken = sessionStorage.getItem("accessToken");
        if (!accessToken) {
          throw new Error("Access token is not available.");
        }

        const response = await api.post(
          `/exchanges/${chattingRoomId}/deal_bid`,
          messageReq,
          {
            headers: { Authorization: `Bearer ${accessToken}` },
          }
        );

        stompClient.send(`/pub/chattings/${chattingRoomId}/messages`, {}, JSON.stringify(messageReq));
        setNewMessage("");
      } catch (error) {
        console.error("메시지 전송 실패", error);
      }
    }
  };

  return (
    <div>
      <div>
        <ul>
          {messages.map((msg) => (
            <li key={msg.id}>
              입찰희망자 아이디 {msg.user_id}: 입찰 제안 가격 {msg.bid_log_price} 제안 날짜 ({msg.localDateTime})
              <hr />
            </li>
          ))}
        </ul>

        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          style={{
            width: "100%",
            padding: "10px",
            fontSize: "16px",
            border: "1px solid #ccc",
            borderRadius: "5px",
            boxSizing: "border-box",
            outline: "none",
          }}
        />
        <button onClick={sendMessage}>전송</button>
      </div>
    </div>
  );
}

export default TestPage;
