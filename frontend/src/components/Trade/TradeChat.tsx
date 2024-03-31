import styled from "styled-components";
import { TopBar } from "../common/Navigator/navigator";
import { useEffect, useState } from "react";
import Send from "/src/assets/images/send.png";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { useParams } from "react-router-dom";
import {
  getChatRecord,
  getChatRoomDetail,
  useDealFinish,
} from "../../apis/TradeApi";
import Stomp from "stompjs";
import { userCheck } from "../../apis/UserApi";
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
interface ChattingBoxProps {
  isCurrentUser: boolean;
}
interface ContentBoxProps {
  backgroundColor: string;
}
const ProductBox = styled.div`
  width: 100%;
  justify-content: space-between;
  height: 3.06325rem;
  /* align-items: center; */
  display: flex;
  border-bottom: 1px solid #d9d9d9;
`;
const TitleBox = styled.div`
  width: 13.875rem;
  height: auto;
  color: #000;
  font-size: 1rem;
  font-weight: bold;
  display: flex;
`;

const FisishButton = styled.button`
  width: 3.9375rem;
  height: 1.75rem;
  border-radius: 0.625rem;
  background: var(--a-0-d-8-b-3, #2a7f00);
  display: flex;
  align-items: center;
  justify-content: center;
  color: #ffffff;
  font-size: 0.6875rem;
  font-weight: 400;
`;
const LayoutMainBox = styled.main`
  width: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 100vh;
  padding-top: 3.125rem;
  padding-bottom: 6rem;
  gap: 1rem;
  /* overflow: hidden; */
`;
const LayoutInnerBox = styled.div`
  display: flex;
  width: 19.875rem;
  /* height: 100%; */
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  gap: 1.5rem;
  padding: 1.12rem 0;
  overflow: hidden;
`;

const SendBox = styled.div`
  display: flex;
  position: fixed;
  width: 90%;
  justify-content: center;
  align-items: center;
  /* padding-left: 2rem;
  padding-right: 0.5rem; */
  gap: 1rem;
  bottom: 2rem;
`;
const SendInput = styled.input`
  display: flex;
  justify-content: flex-start;
  padding-left: 1rem;
  width: 17rem;
  height: 2.1875rem;
  border-radius: 1rem;
  border: 1px solid var(--gray2, #c8c8c8);
  font-size: 0.875rem;
  &:focus {
    border: 1px solid var(--gray2, #c8c8c8);
  }
`;
const ChatBox = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 1rem;
  overflow-y: scroll;
  /* height: auto; */
  flex-grow: 1;
  height: 40rem;
`;
const SendButton = styled.img`
  width: 2.1875rem;
  height: 2.1875rem;
`;

const ChattingBox = styled.div<ChattingBoxProps>`
  display: flex;
  flex-direction: row;
  width: 100%;
  justify-content: ${(props) =>
    props.isCurrentUser ? "flex-end" : "flex-start"};
  gap: 0.25rem;
`;

const Thumbnail = styled.img`
  width: 2.1875rem;
  height: 2.1875rem;
  border-radius: 50%;
`;
const ContentBox = styled.div<ContentBoxProps>`
  padding-top: 0.5rem;
  padding-bottom: 0.5rem;
  padding-left: 0.2rem;
  padding-right: 0.2rem;
  width: 13.0625rem;
  height: auto;
  border-radius: 0rem 0.625rem 0.625rem 0.625rem;
  background-color: ${(props) => props.backgroundColor};
`;

const TradeChat = () => {
  const { chatId } = useParams();
  const chatNumber = Number(chatId);
  const accessToken = sessionStorage.getItem("accessToken");
  const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
  const [messages, setMessages] = useState<ChatResponse[]>([]);
  const [newMessage, setNewMessage] = useState<string>("");
  const [messageSubscribed, setMessageSubscribed] = useState<boolean>(false);
  const socket = new WebSocket("wss://j10c102.p.ssafy.io/api/ws");
  const client = Stomp.over(socket);
  const queryClient = useQueryClient();
  const { isLoading, data, error } = useQuery({
    queryKey: ["chatList", chatNumber],
    queryFn: accessToken
      ? () => getChatRecord(accessToken, chatNumber)
      : undefined,
  });
  const {
    isLoading: detailIsLoading,
    data: detailData,
    error: detailError,
  } = useQuery({
    queryKey: ["chatRoomDetail", chatNumber],
    queryFn: accessToken
      ? () => getChatRoomDetail(accessToken, chatNumber)
      : undefined,
  });
  const {
    isLoading: userDataIsLoading,
    data: userData,
    error: userDataError,
  } = useQuery({
    queryKey: ["userData"],
    queryFn: accessToken ? () => userCheck(accessToken) : undefined,
  });
  const { mutate: finishClick } = useDealFinish();
  const handleFinishClick = () => {
    finishClick(detailData?.chatRoomExArticleId);
  };
  console.log("유저", userData);
  console.log("글", data);
  useEffect(() => {
    console.log("저 호출됐어요");
    const transformedData = data?.map((item: ChatResponse) => ({
      chatId: item.chatId,
      chatRoomId: item.chatRoomId,
      chatSenderId: item.chatSenderId,
      chatSenderNick: item.chatSenderNick,
      chatSenderThumb: item.chatSenderThumb,
      chatSendAt: item.chatSendAt,
      chatMessage: item.chatMessage,
    }));
    setMessages(transformedData);
    client.connect(
      {
        Authorization: `Bearer ${accessToken}`,
      },
      () => {
        console.log("WebSocket 연결됨");

        // 백엔드로부터 메시지를 받는 부분
        // 이전에 구독했던 채널에 대한 구독은 여기서 하도록 수정
        client.subscribe(`/sub/chat/${chatNumber}`, (message) => {
          const msg: ChatResponse = JSON.parse(message.body);
          console.log(msg);

          queryClient.invalidateQueries({
            queryKey: ["chatList", chatNumber],
          });
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
          console.log("disconnect");
        });
      }
    };
  }, [data]);
  const sendMessage = async () => {
    if (stompClient && newMessage.trim() !== "") {
      try {
        const messageReq = {
          chatMessage: newMessage,
        };

        const chatRequest = {
          chatRoomId: chatNumber,
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
  return (
    <>
      <TopBar title="채팅" showBack={true} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <ProductBox>
            <TitleBox>{detailData?.chatRoomExArticleTitle}</TitleBox>
            <FisishButton onClick={handleFinishClick}>거래종료</FisishButton>
          </ProductBox>
          <ChatBox>
            {data?.map((item: any) =>
              item.chatSenderId === detailData?.chatRoomUserId ? (
                <ChattingBox
                  key={item?.chatId}
                  isCurrentUser={
                    item.chatSenderId === detailData?.chatRoomUserId
                  }
                >
                  <ContentBox backgroundColor="var(--sub1, #E5F9DB)">
                    보낸 아이디 {item.chatSenderNick} : {item.chatMessage}
                  </ContentBox>
                  <Thumbnail src={item.chatSenderThumb} />
                </ChattingBox>
              ) : (
                <ChattingBox
                  key={item?.chatId}
                  isCurrentUser={
                    item.chatSenderId === detailData?.chatRoomUserId
                  }
                >
                  <Thumbnail src={item.chatSenderThumb} />
                  <ContentBox backgroundColor="var(--gary3, #F4F4F4)">
                    보낸 아이디 {item.chatSenderNick} : {item.chatMessage}
                  </ContentBox>
                </ChattingBox>
              )
            )}
          </ChatBox>
          <SendBox>
            <SendInput
              value={newMessage}
              onChange={(e) => setNewMessage(e.target.value)}
            />
            <SendButton src={Send} alt="send" onClick={sendMessage} />
          </SendBox>
        </LayoutInnerBox>
      </LayoutMainBox>
    </>
  );
};

export default TradeChat;
