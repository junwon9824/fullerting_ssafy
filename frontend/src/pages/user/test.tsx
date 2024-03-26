import React, { useEffect, useState } from "react";
import Stomp from "stompjs";
import { api } from "../../apis/Base";


interface MessageRes {
  id: string; //bidlogid
  localDateTime: string;
  userId: number;
  chattingRoomId: number;
  bidLogPrice: number;
}

function TestPage() {
  const chattingRoomId = 138;

  const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
  const [messages, setMessages] = useState<MessageRes[]>([]);
  const [newMessage, setNewMessage] = useState<string>("");
  const [messageSubscribed, setMessageSubscribed] = useState<boolean>(false);

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

    // const socket = new SockJS("/ws");
    // const socket = new SockJS("http://localhost:8080/ws");
    // const socket = new WebSocket(import.meta.env.__WEBSOCKET_URL__);

    const socket = new WebSocket("ws://localhost:8080/ws");

    // const socket = new WebSocket("wss://j10c102.p.ssafy.io/api/ws");

    const client = Stomp.over(socket);

    console.log(socket);

    client.connect(
      {},
      () => {
        console.log("WebSocket 연결됨");

        // 백엔드로부터 메시지를 받는 부분
        // 이전에 구독했던 채널에 대한 구독은 여기서 하도록 수정
        client.subscribe(
          `/sub/chattings/${chattingRoomId}`,
          (message) => {
            const msg: MessageRes = JSON.parse(message.body);
            console.log('message arrived' + msg)
            const lastMessageId = msg.id;

            setMessages((prevMessages) => [...prevMessages,
            { ...msg, id: String(lastMessageId), userId: msg.userId, bidLogPrice: msg.bidLogPrice, localDateTime: msg.localDateTime },
            ]);
            // id: string; //bidlogid
            // localDateTime: string;
            // user_id: number;
            // chattingRoomId: number;
            // bid_log_price: number;
            // setMessages((prevMessages) => [...prevMessages, msg]);
          }
        );
        setMessageSubscribed(true); // 한 번만 실행되도록 플래그 설정
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

    if (stompClient && newMessage.trim() !== "") {

      try {
        const messageReq = { 
          dealCurPrice: newMessage,
          // userId: , /////수정필요!!!!!!!!!!!!!!!!!1
        };

        const accessToken = sessionStorage.getItem("accessToken");
        if (!accessToken) {
          throw new Error("Access token is not available.");
        }

        const res = await api.post(
          `/exchanges/${chattingRoomId}/deal_bid`,
          messageReq,
          {
            headers: { Authorization: `Bearer ${accessToken}` },
          }

        );
        // private Long id;

        // private Long userId;
        // private LocalDateTime localDateTime;

        // private int bidLogPrice;
        // private Long exarticleid;
        const DealstartRequest = {

          id: res.data.id,
          dealCurPrice: res.data.dealCurPrice,
          localDateTime: res.data.localDateTime,
          userId: res.data.userId,
          chattingRoomId: chattingRoomId,
          bidLogPrice: messageReq.dealCurPrice,
        }

        stompClient.send(`/pub/chattings/${chattingRoomId}/messages`, {}, JSON.stringify(DealstartRequest));
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
              messageid  {msg.id}입찰희망자 아이디 {msg.userId}: 입찰 제안 가격 {msg.bidLogPrice} 제안 날짜 ({msg.localDateTime})
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
