import React, { useEffect, useState } from "react";
import Stomp from "stompjs";
import { api } from "../../apis/Base";
import {
  createChatRoom,
  getChatRecord,
  useSendChat,
} from "../../apis/TradeApi";
import { useQuery } from "@tanstack/react-query";

interface UserResponse {
  id: number;
  email: string;
  role: string;
  nickname: string;
  thumbnail: string;
  rank: string;
  location: string;
  authProvider: string;
}

interface MessageRes {
  bidLogId: number; // 입찰제안 ID
  chatRoomId: number; // 가격제안 게시물 id
  userResponse: UserResponse; // 입찰자 ID, 썸네일, 닉네임
  dealCurPrice: number; // 입찰자가 제안한 금액
  maxPrice: number; // 현재 이 경매글의 최고가
  bidderCount: number; //참여자수
}

interface ChatResponse {
  // id: number;
  chatRoomId: number;
  chatSenderThumb: string;
  chatSenderNick: string;
  chatSenderId: number; //전송자 ID
  chatMessage: string; //채팅 내용
  chatSendAt: Date; // 전송일자
  chatId: number;
}

function ChatTestPage() {
  const chatRoomId = 168;
  const postId = 1;
  const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
  const [messages, setMessages] = useState<ChatResponse[]>([]);
  const [newMessage, setNewMessage] = useState<string>("");
  const [messageSubscribed, setMessageSubscribed] = useState<boolean>(false);
  const socket = new WebSocket("wss://j10c102.p.ssafy.io/api/ws");
  const client = Stomp.over(socket);
  const accessToken = sessionStorage.getItem("accessToken") || ""; // accessToken이 null인 경우에는 빈 문자열로 대체
  const { mutate: createChat } = createChatRoom();
  const { mutate: sendChat } = useSendChat();
  const { isLoading, data, error } = useQuery({
    queryKey: ["chatList", postId],
    queryFn: accessToken ? () => getChatRoom(accessToken, postId) : undefined,
  });
  console.log(data);
  const loadMessages = async () => {
    try {
      const accessToken = sessionStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("Access token is not available.");
      }

      const response = await api.get(`/chat/${postId}`, {
        //지금까지 채팅 내역 디비에서 가져오기
        headers: { Authorization: `Bearer ${accessToken}` },
      });
      console.log("데이터", response.data.data_body);

      // API 응답 데이터를 변환하는 부분 id: number;

      const transformedData = response.data.data_body.map(
        (item: ChatResponse) => ({
          chatId: item.chatId,
          chatRoomId: item.chatRoomId,
          chatSenderId: item.chatSenderId,
          chatSenderNick: item.chatSenderNick,
          chatSenderThumb: item.chatSenderThumb,
          chatSendAt: item.chatSendAt,
          chatMessage: item.chatMessage,
        })
      );

      setMessages(transformedData);
    } catch (error) {
      console.error("채팅 내역 로드 실패", error);
    }
  };

  useEffect(() => {
    loadMessages();

    const accessToken = sessionStorage.getItem("accessToken");
    if (!accessToken) {
      throw new Error("Access token is not available.");
    }

    //  const socket = new WebSocket("ws://localhost:8080/ws");

    console.log(socket);

    client.connect(
      {
        Authorization: `Bearer ${accessToken}`,
      },
      () => {
        console.log("WebSocket 연결됨");

        // 백엔드로부터 메시지를 받는 부분
        // 이전에 구독했던 채널에 대한 구독은 여기서 하도록 수정
        client.subscribe(`/sub/chat/${postId}`, (message) => {
          const msg: ChatResponse = JSON.parse(message.body);
          console.log(msg);

          setMessages((prevMessages) => [...prevMessages, msg]);
        });
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
  }, [chatRoomId]);

  const sendMessage = async () => {
    if (stompClient && newMessage.trim() !== "") {
      try {
        const messageReq = {
          chatMessage: newMessage,
        };

        const accessToken = sessionStorage.getItem("accessToken");
        if (!accessToken) {
          throw new Error("Access token is not available.");
        }

        const chatRequest = {
          chatRoomId: postId,
          chatMessage: messageReq.chatMessage,
          // redirectURL: window.location.href,
        };

        stompClient.send(`/pub/chat`, {}, JSON.stringify(chatRequest));
        setNewMessage("");
      } catch (error) {
        console.error("메시지 전송 실패", error);
      }
    }
  };
  // chatId:item.chatId,
  // chatRoomId: item.chatRoomId,
  // chatSenderId: item.chatSenderId,
  // chatSenderNick: item.chatSenderNick,
  // chatSenderThumb: item.chatSenderThumb,
  // chatSendAt: item.chatSendAt,
  // chatMessage:item.chatMessage,

  return (
    <div>
      <div>
        <ul>
          {messages &&
            messages.map((msg) => (
              <li key={msg.chatId}>
                messageid {msg.chatId}상대 아이디 {msg.chatSenderId}: 채팅내용{" "}
                {msg.chatMessage}
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

export default ChatTestPage;
