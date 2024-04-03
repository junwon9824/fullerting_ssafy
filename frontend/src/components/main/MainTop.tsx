import { useNavigate } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { getCropList } from "../../apis/DiaryApi";
import styled from "styled-components";
import Chat from "/src/assets/svg/chat.svg";
import bell from "../../assets/svg/bell-ring.svg";

const MainContainer = styled.div`
  justify-content: center;
  flex-direction: column;
  height: 13.3rem;
  background-color: #a0d8b3;
  padding: 0.5rem 0.8rem;
`;
const TextBox = styled.div`
  border-radius: 0.78125rem;
  background: var(--sub1, #e5f9db);
  font-size: 0.75rem;
  width: 11rem;
  height: 1.2rem;
  display: flex;
  flex-direction: row;
  font-weight: 600;
  justify-content: center;
  align-items: center;
  text-align: center;
  margin: 0.7rem 0 0 0;
`;

const TextContent = styled.div`
  font-family: "GamtanRoad Dotum TTF";
  display: flex;
  flex-direction: row;
`;
const BasicText = styled.div`
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.6rem;
  margin: 0.2rem;
  font-weight: 600;
`;

const Content = styled.div`
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
  padding: 0 0rem;
`;
const MainText = styled.div`
  color: #fffefe;
  font-size: 1.7rem;
  font-style: normal;
  font-weight: 600;
  line-height: normal;
  ${({ theme }) => theme.fonts.logo}
`;

const BellIcon = styled.img`
  position: absolute;
  top: 15px;
  right: 15px;
  height: 1.6rem;
`;

const ChatIcon = styled.img`
  position: absolute;
  top: 15px;
  right: 53px;
  height: 1.6rem;
`;
const DiaryBox = styled.button`
  display: flex;
  padding: 0.5rem 1rem;
  justify-content: space-between;
  align-items: center;
  text-align: center;
  margin: 0.9rem 0 1rem 0.7rem;
  width: 19.5rem;
  height: 6.7rem;
  border-radius: 0.9375rem;
  background: rgba(255, 255, 255, 0.26);
  box-shadow: 0px 2px 2px 2px rgba(161, 161, 161, 0.1);
`;
const Diary = styled.button`
  width: 6.4rem;
  height: 1.74375rem;
  border-radius: 0.5rem;
  background: var(--sub0, #a0d8b3);
  color: #fff;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.625rem;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
`;
const CropImage = styled.img`
  border-radius: 50rem;
  width: 5rem;
  height: 5rem;
  border: 2px solid var(--point, #3d0c11);
  opacity: 0.9;
  background: url(<path-to-image>) lightgray 50% / cover no-repeat;
  margin-right: 1rem;
`;

const DiaryText = styled.span`
  font-size: 0%.8rem;
  font-weight: bold;
  color: #ffffff;
`;

const DDayCounter = styled.div`
  color: #ffffff;
  padding: 0.4rem 0;
  font-weight: bold;
  font-size: 1.5rem;
  width: 3rem;
`;

const SliderContainer = styled.div`
  display: flex;
  overflow-x: auto;
  scroll-snap-type: x mandatory;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const DiarySlider = styled.div`
  display: flex;
  scroll-snap-type: x mandatory;
  gap: 1rem;
  & > div {
    scroll-snap-align: start;
    flex: 0 0 auto;
  }
`;
const calculateDDay = (createdAt: string) => {
  const today = new Date();
  const createdDate = new Date(createdAt);
  const diffTime = Math.abs(today.getTime() - createdDate.getTime());
  const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
  return `D+${diffDays}`;
};
const Maintop = () => {
  const accessToken = sessionStorage.getItem("accessToken");
  const navigate = useNavigate();

  const { data: cropList } = useQuery({
    queryKey: ["cropList"],
    queryFn: accessToken ? () => getCropList(accessToken) : undefined,
  });

  const goToDiary = () => {
    navigate(`/crop`);
  };

  const goToLogin = () => {
    navigate("/login");
  };
  const handleViewAlarm = () => {
    window.location.href = "/alarm";
  };
  const handleChat = () => {
    navigate("/trade/chatroom");
  };

  return (
    <MainContainer>
      <MainText>
        풀러팅{" "}
        <BellIcon
          src={bell}
          alt="Notification bell"
          onClick={handleViewAlarm}
        />
        <ChatIcon src={Chat} alt="Notification bell" onClick={handleChat} />
      </MainText>
      <TextBox>"{cropList?.length || 0}개의 작물을 가꾸고 계시군요"</TextBox>
      {accessToken ? (
        <SliderContainer>
          <DiarySlider>
            {cropList?.map((crop, index) => (
              <DiaryBox key={crop.packDiaryId} onClick={goToDiary}>
                <Content>
                  <TextContent>
                    <DiaryText>{crop.packDiaryTitle}</DiaryText>
                    <BasicText>와 함께한 시간</BasicText>
                  </TextContent>
                  <DDayCounter>
                    {calculateDDay(crop.packDiaryCulStartAt)}
                  </DDayCounter>
                  <Diary>일지 작성하러 가기</Diary>
                </Content>
                <CropImage
                  src={cropList[index].cropTypeImgUrl}
                  alt="Crop image"
                />
              </DiaryBox>
            ))}
          </DiarySlider>
        </SliderContainer>
      ) : (
        <DiaryBox onClick={goToLogin}>
          <Content>
            <TextContent>
              <DiaryText>작물일지를 작성해주세요</DiaryText>
            </TextContent>
          </Content>
        </DiaryBox>
      )}
    </MainContainer>
  );
};

export default Maintop;
