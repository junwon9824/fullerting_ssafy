import React, {
  useEffect,
  useRef,
  useState,
  useContext,
  createContext,
} from "react";
import { Client, IMessage } from "@stomp/stompjs"; // STOMP 프로토콜을 사용하기 위한 라이브러리를 가져옵니다.
import axios from "axios"; // HTTP 클라이언트 라이브러리를 가져옵니다.
import { Link, useParams } from "react-router-dom"; // 라우터 관련 기능을 사용하기 위한 라이브러리를 가져옵니다.
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
  // const {chatId} = useParams();

  const chattingRoomId = 91; // 현재 게시물 (exarticleid 를 채팅룸 id 로 한다.)

  const [stompClient, setStompClient] = useState<Client | null>(null); // STOMP 클라이언트 상태 관리
  const [messages, setMessages] = useState<MessageRes[]>([]); // 채팅 메시지 목록 상태 관리

  const [newMessage, setNewMessage] = useState<string>(""); // 새 메시지 입력 상태 관리

  const loadMessages = async () => {
    //기존 디비에 저장된 제안 목록 불러오기
    try {
      // const response = await api.get(
      //     `/exchanges/${chattingRoomId}/suggestion`
      // );
      const accessToken = sessionStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("Access token is not available.");
      }
      // const response = await api.get(`/badges`, {
      //   headers: { Authorization: `Bearer ${accessToken}` },
      // });

      const response = await api.get(
        `/exchanges/${chattingRoomId}/suggestion`,
        {
          headers: { Authorization: `Bearer ${accessToken}` },
        }
      );
      //  const response = await axios.get(
      //   `http://localhost:8080/v1/exchanges/${chattingRoomId}/suggestion`,
      //   {
      //     headers: { Authorization: `Bearer ${accessToken}` },
      //   }
      // );

      console.log(response.data.data_body);
      setMessages(response.data.data_body);
    } catch (error) {
      console.error("채팅 내역 로드 실패", error);
    }
  };

  useEffect(() => {
    loadMessages();

    const client = new Client({
      // const baseURL = "https://j10c102.p.ssafy.io/api/v1";

      // brokerURL: `ws://localhost:8080/ws`, // Server WebSocket URL
      brokerURL: `wss://j10c102.p.ssafy.io:8080/ws`, // Server WebSocket URL

      reconnectDelay: 5000, // 연결 끊겼을 때, 재연결시도까지 지연시간(ms)
      onConnect: () => {
        console.log("WebSocket 연결됨"); // 이 위치가 서버와의 연결이 성공적으로 이루어졌음을 보장
        client.subscribe(
          `/sub/chattings/${chattingRoomId}/messages`,
          (message: IMessage) => {
            const msg: MessageRes = JSON.parse(message.body); // 메시지를 JSON형태로 파싱
            setMessages((prevMessages) => [...prevMessages, msg]); // 기존 메시지 목록에 새 메시지 추가
          }
        );
      },
    });
    client.activate(); // STOMP 클라이언트 활성화
    setStompClient(client); // STOMP 클라이언트 상태 업데이트
    return () => {
      client.deactivate(); // 컴포넌트 언마운트 시, STOMP 클라이언트 비활성화
    };
  }, [chattingRoomId]); // chatId가 변경될 때마다 useEffect 실행

  const sendMessage = async () => {
    if (newMessage.trim() !== "") {
      try {
        const messageReq = {
          dealCurPrice: newMessage,
        };

        // await axios.post(
        //   `http://localhost:8080/v1/exchanges/${chattingRoomId}/deal_bid`,
        //   messageReq
        // ); //입찰 제안하기

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

        // await api.post(
        //   `http://localhost:8080/v1/exchanges/${chattingRoomId}/deal_bid`,
        //   messageReq
        // ); //입찰 제안하기

        setNewMessage(""); // 메시지 전송 후 입력 필드 초기화
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
              입찰희망자 아이디 {msg.user_id}: 입찰 제안 가격{" "}
              {msg.bid_log_price} 제안 날짜 ({msg.localDateTime})
              <hr />
            </li>
          ))}
        </ul>

        <input
          type="text"
          value={newMessage}
          onChange={(e) => setNewMessage(e.target.value)}
          style={{
            width: "100%", // 너비를 100%로 설정하여 부모 요소에 꽉 차도록 함
            padding: "10px", // 안쪽 여백을 설정하여 입력 필드의 텍스트가 잘 보이도록 함
            fontSize: "16px", // 글꼴 크기를 조정하여 텍스트가 잘 보이도록 함
            border: "1px solid #ccc", // 테두리를 설정하여 입력 필드가 화면에서 잘 구분되도록 함
            borderRadius: "5px", // 테두리의 모서리를 둥글게 만듦
            boxSizing: "border-box", // 테두리와 안쪽 여백이 요소의 크기에 영향을 주지 않도록 함
            outline: "none", // 포커스 효과를 제거하여 입력 필드가 클릭되었을 때 시각적으로 잘 보이도록 함
          }}
        />
        <button onClick={sendMessage}>
          {/* <PaperPlaneTilt size={32} /> Add icon import or component */}
          전송
        </button>
      </div>
    </div>
  );
}

export default TestPage;
