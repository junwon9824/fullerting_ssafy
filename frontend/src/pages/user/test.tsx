import React, { useEffect, useState } from "react";
import Stomp from "stompjs";
import { api } from "../../apis/Base";


interface MessageRes {


  bidLogId: number; // 입찰제안 ID
  exArticleId: number; // 가격제안 게시물 id
  UserResponse: object; // 입찰자 ID, 썸네일, 닉네임
  dealCurPrice: number; // 입찰자가 제안한 금액
  maxPrice: number; // 현재 이 경매글의 최고가
  bidderCount: number;
}

function TestPage() {


  const exArticleId = 140;



  const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
  const [messages, setMessages] = useState<MessageRes[]>([]);
  const [newMessage, setNewMessage] = useState<string>("");
  const [messageSubscribed, setMessageSubscribed] = useState<boolean>(false);

  const accessToken = sessionStorage.getItem("accessToken") || ""; // accessToken이 null인 경우에는 빈 문자열로 대체

  const loadMessages = async () => {
    try {
      const accessToken = sessionStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("Access token is not available.");
      }

      const response = await api.get(`/exchanges/${exArticleId}/suggestion`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      });

      setMessages(response.data.data_body);

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
    const socket = new WebSocket("wss://j10c102.p.ssafy.io/api/ws");

    const client = Stomp.over(socket);

    console.log(socket);

    client.connect(
      {
        "Authorization": `Bearer ${accessToken}`
      },
      () => {
        console.log("WebSocket 연결됨");

        // 백엔드로부터 메시지를 받는 부분
        // 이전에 구독했던 채널에 대한 구독은 여기서 하도록 수정
        client.subscribe(
          `/sub/chattings/${exArticleId}`,
          (message) => {


            console.log(message.body)
            const msg: MessageRes = JSON.parse(message.body);

            // console.log('message arrived' + msg.userId)
            // console.log('message arrived' + msg.bidLogPrice)
            console.log('message arrived' + msg.maxPrice)

            const lastMessageId = msg.bidLogId;


            setMessages((prevMessages) => [...prevMessages,
            { ...msg, id: String(lastMessageId), userId: msg.UserResponse, dealCurPrice: msg.dealCurPrice },
            ]);

            // id: string; //bidlogid
            // localDateTime: string;
            // user_id: number;
            // exArticleId: number;
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
  }, [exArticleId]);


  const sendMessage = async () => {

    if (stompClient && newMessage.trim() !== "") {

      try {

        const messageReq = {
          dealCurPrice: newMessage,
        };

        const accessToken = sessionStorage.getItem("accessToken");
        if (!accessToken) {
          throw new Error("Access token is not available.");
        }

        // const res = await api.post(
        //   `/exchanges/${exArticleId}/deal_bid`,
        //   messageReq,
        //   {
        //     headers: { Authorization: `Bearer ${accessToken}` },
        //   }

        // );
        //  Long id;

        //  Long userId;
        //  LocalDateTime localDateTime;

        //  int bidLogPrice;
        //  Long exarticleid;
        // console.log( res.data.data_body)
        const DealstartRequest = {


          exArticleId: exArticleId,
          dealCurPrice: messageReq.dealCurPrice,

        }


        stompClient.send(`/pub/chattings/${exArticleId}/messages`, {}, JSON.stringify(DealstartRequest));
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
            <li key={msg.bidLogId}>
              messageid  {msg.bidLogId}입찰희망자 아이디 {msg.UserResponse.id}: 입찰 제안 가격 {msg.dealCurPrice}  
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
