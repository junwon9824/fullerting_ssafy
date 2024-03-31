import styled from "styled-components";
import { TopBar } from "../common/Navigator/navigator";
import { useQuery } from "@tanstack/react-query";
import { getChatRoomList } from "../../apis/TradeApi";
import { useNavigate } from "react-router-dom";
interface ChatData {
  chatOtherNick: string;
  chatRoomId: number;
  chatRoomLastMessage: string;
  chatRoomLastMessageSendAt: string;
  chatRoomOtherThumb: string;
}
const ChatList = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-around;
  flex-direction: column;
  gap: 1.19rem;
  overflow-y: auto;
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
const ProfileImage = styled.img`
  width: 2.5625rem;
  height: 2.5625rem;
  border-radius: 50%;
`;
const ChatRoom = styled.div`
  display: flex;
  justify-content: space-around;
  gap: 0.75rem;
  width: 100%;
  height: 2.5625rem;
`;
const ChatDetail = styled.div`
  width: 15.75rem;
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  gap: 0.31rem;
`;
const TitleBox = styled.div`
  width: auto;
  display: flex;
  flex-direction: row;
  gap: 0.2rem;
`;
const Title = styled.div`
  align-items: center;
  display: flex;
  color: #000;
  font-size: 0.8125rem;
  font-weight: bold;
  width: auto;
`;
const Time = styled.div`
  align-items: center;
  display: flex;
  width: auto;
  color: var(--gray1, #8c8c8c);
  font-size: 0.6875rem;
  font-weight: 400;
`;
const Content = styled.div`
  width: auto;
  color: var(--gray1, #8c8c8c);
  font-size: 0.8125rem;
  font-weight: 400;
  display: flex;
`;
const TradeChatRoom = () => {
  const accessToken = sessionStorage.getItem("accessToken");
  const { isLoading, data, error } = useQuery({
    queryKey: ["chatRoomList"],
    queryFn: accessToken ? () => getChatRoomList() : undefined,
  });
  console.log(data);
  const renderContentText = (text: string) => {
    // 먼저 `text`가 정의되어 있는지 확인합니다.
    if (text === undefined || text === null) {
      return ""; // `text`가 정의되어 있지 않으면 빈 문자열을 반환합니다.
    }

    // `text`의 길이가 20자를 초과하면 앞의 20자만 가져오고, '...'를 붙입니다.
    // 그렇지 않으면 `text` 그대로 반환합니다.
    return text.length > 20 ? `${text.slice(0, 20)}...` : text;
  };
  //시간
  const renderTimeAgo = (timeString: string) => {
    if (!timeString) {
      return "날짜 정보 없음";
    }
    const time = new Date(timeString);
    const now = new Date();
    const difference = now.getTime() - time.getTime(); // 밀리초 단위
    // console.log("time", time);
    // console.log("now", now);

    const minutes = Math.floor(difference / (1000 * 60));
    const hours = Math.floor(difference / (1000 * 60 * 60));
    const days = Math.floor(difference / (1000 * 60 * 60 * 24));

    if (minutes < 60) {
      return `${minutes}분 전`;
    } else if (hours < 24) {
      return `${hours}시간 전`;
    } else {
      return `${days}일 전`;
    }
  };
  // 시간 끝

  const navigate = useNavigate();
  const handleChatRoomClick = (chatRoomId: number) => {
    navigate(`/trade/${chatRoomId}/chat`);
  };
  return (
    <>
      <TopBar title="채팅 목록" showBack={true} />

      <LayoutMainBox>
        <LayoutInnerBox>
          {data?.map((item: ChatData, index: number) => (
            <ChatRoom
              key={item.chatRoomId}
              onClick={() => handleChatRoomClick(item.chatRoomId)}
            >
              <ProfileImage src={item?.chatRoomOtherThumb} />
              <ChatDetail>
                <TitleBox>
                  <Title>{item.chatOtherNick}</Title>
                  <Time>{renderTimeAgo(item?.chatRoomLastMessageSendAt)}</Time>
                </TitleBox>
                <Content>
                  {renderContentText(item?.chatRoomLastMessage)}
                </Content>
              </ChatDetail>
            </ChatRoom>
          ))}
        </LayoutInnerBox>
      </LayoutMainBox>
    </>
  );
};

export default TradeChatRoom;
