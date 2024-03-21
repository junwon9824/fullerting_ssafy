import { useNavigate } from "react-router-dom";
import styled from "styled-components";

const MainContainer = styled.div`
  justify-content: center;
  flex-direction: column;
  height: 15rem;
  background-color: #a0d8b3;
  padding: 1rem 0.8rem;
`;
const TextBox = styled.div`
  border-radius: 0.78125rem;
  background: var(--sub1, #e5f9db);
  font-size: 0.75rem;
  width: 12rem;
  height: 1.5rem;
  display: flex;
  flex-direction: row;
  font-weight: 600;
  justify-content: center;
  align-items: center;
  text-align: center;
  margin: 1rem 0 0 0;
`;

const TextContent = styled.div`
  display: flex;
  flex-direction: row;
`;
const BasicText = styled.div`
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.75rem;
  font-weight: 600;
`;

const Content = styled.div`
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
`;
const MainText = styled.div`
  color: #fffefe;
  font-size: 1.7rem;
  font-style: normal;
  font-weight: 600;
  line-height: normal;
  ${({ theme }) => theme.fonts.logo}
`;

const DiaryBox = styled.button`
  display: flex;
  padding: 0.5rem 1rem;
  justify-content: space-between;
  align-items: center;
  text-align: center;
  margin: 0.7rem 0;
  width: 21rem;
  height: 7.7rem;
  border-radius: 0.9375rem;
  background: rgba(255, 255, 255, 0.26);
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
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
  width: 6rem;
  height: 6rem;
  border: 2px solid var(--point, #3d0c11);
  opacity: 0.9;
  background: url(<path-to-image>) lightgray 50% / cover no-repeat;
`;

const DiaryText = styled.span`
  font-size: 1rem;
  font-weight: bold;
  color: #ffffff;
`;

const DDayCounter = styled.div`
  color: #ffffff;
  padding: 0.7rem 0;
  font-weight: bold;
  font-size: 2rem;
  width: 5rem;
`;

const Maintop = () => {
  const crop = {
    packDiaryId: 1,
    cropType: "토마토",
    packDiaryTitle: "똘똘한 토마토",
    packDiaryCulStartAt: "2024-03-01",
    packDiaryCulEndAt: "2024-04-01",
    packDiaryGrowthStep: "2",
    packDiaryCreatedAt: "2024-03-01",
    cropTypeImgUrl: "/src/assets/svg/pullright.svg",
    dDayCount: "D+21",
  };

  const navigate = useNavigate();

  const goToDiary = () => {
    navigate("/diary");
  };
  return (
    <MainContainer>
      <MainText>풀러팅</MainText>
      <TextBox>"2개의 작물을 가꾸고 계시군요"</TextBox>
      {crop && (
        <DiaryBox onClick={goToDiary}>
          <Content>
            <TextContent>
              <DiaryText>{crop.packDiaryTitle}</DiaryText>
              <BasicText>와 함께한 시간</BasicText>
            </TextContent>
            <DDayCounter>{crop.dDayCount}</DDayCounter>
            <Diary>일지 작성하러 가기</Diary>
          </Content>
          <CropImage src={crop.cropTypeImgUrl} alt="Crop image" />
        </DiaryBox>
      )}
    </MainContainer>
  );
};

export default Maintop;
