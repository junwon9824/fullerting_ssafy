import styled from "styled-components";
import { TopBar } from "../../components/common/Navigator/navigator";
import Coli from "/src/assets/images/브로콜리.png";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { BottomButton } from "../../components/common/Button/LargeButton";
import { useNavigate } from "react-router-dom";
import SvgProfile from "/src/assets/images/김진명프로필사진.png";
import Sprout from "/src/assets/svg/classes.svg";
import NotLike from "/src/assets/svg/notlike.svg";
import Like from "/src/assets/svg/like.svg";
import { useState } from "react";
import Tree from "/src/assets/svg/diarytree.svg";
const ImgBox = styled.img`
  width: 100%;
  height: 15.5625rem;
  display: flex;
`;
const InfoBox = styled.div`
  width: 100%;
  height: 2.125rem;
  display: flex;
  justify-content: space-between;
  gap: 8.81rem;
  position: relative;
`;
const Profile = styled.div`
  width: 5.875rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.2rem;
`;
const Name = styled.div`
  width: auto;
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
`;
const NameText = styled.text`
  font-size: 0.8125rem;
  font-style: normal;
  font-weight: bold;
  color: #000000;
`;
const ClassesText = styled.div`
  color: #4f4f4f;
  display: flex;
  font-size: 0.6875rem;
  font-weight: 400;
  align-items: center;
  gap: 0.2rem;
`;
const Date = styled.div`
  width: auto;
  position: absolute;
  right: 0;
  bottom: 0;
  color: "#8C8C8C";
  font-size: 0.6875rem;
  font-weight: "400";
`;
const Title = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  color: #000;
  font-size: 1.1rem;
  font-weight: 400;
`;
const TitleBox = styled.div`
  padding-top: 2.19rem;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;
const Price = styled.text`
  color: #000;
  font-size: 1.25rem;
  font-weight: bold;
`;
const DiaryBox = styled.div`
  width: 100%;
  gap: 0.3rem;
  align-items: center;
  display: flex;
  flex-direction: row;
`;
const NavigateText = styled.div`
  color: var(--gray1, #8c8c8c);
  font-size: 0.75rem;
  font-weight: bold;
`;
const ExplainText = styled.div`
  color: #000;
  font-size: 0.875rem;
  font-weight: 400;
  width: 100%;
  height: auto;
  line-height: 1.375rem;
`;

const TradeGeneralDetail = () => {
  const navigate = useNavigate();
  const BtnClick = () => {
    navigate("/trade/chat");
  };
  const [like, setLike] = useState<boolean>(false);
  const handleLike = () => {
    setLike(!like);
  };
  const handleDiary = () => {
    navigate("/diary/detail");
  };
  return (
    <>
      <TopBar title="작물거래" showBack={true} showEdit={true} />
      <LayoutMainBox>
        <ImgBox src={Coli} alt="coli" />
        <LayoutInnerBox>
          <InfoBox>
            <Profile>
              <img src={SvgProfile} alt="profile" />
              <Name>
                <NameText>김진명란젓</NameText>
                <ClassesText>
                  새싹 <img src={Sprout} alt="Sprout" />
                </ClassesText>
              </Name>
            </Profile>
            <Date>2024-03-06 14:40</Date>
          </InfoBox>
          <TitleBox>
            <Title>
              심우석 머리 브로콜리
              <img
                src={like ? Like : NotLike}
                alt="like"
                onClick={handleLike}
              />
            </Title>
            <Price>800원</Price>
            <DiaryBox>
              <img src={Tree} alt="tree" />
              <NavigateText onClick={handleDiary}>
                작물일지 이동하기
              </NavigateText>
            </DiaryBox>
            <ExplainText>
              심우석의 머리를 브로콜리에 비유하는 것은 그의 독특하고 특이한 헤어
              스타일을 묘사하기 위한 창의적인 방법입니다. 이 비유는 특히 그의
              머리카락이 풍성하고 볼륨감이 많으며, 위로 솟아 오른 모양이 마치
              브로콜리의 녹색 송이와 유사하다는 점에서 온 것일 수 있습니다.
              브로콜리의 작은 꽃송이들이 모여 있는 모양은, 심우석의 머리카락이
              여러 방향으로 풍성하게 서 있는 것과 비슷하다고 할 수 있습니다.
            </ExplainText>
          </TitleBox>
        </LayoutInnerBox>
        <BottomButton text="채팅하기" onClick={BtnClick} />
      </LayoutMainBox>
    </>
  );
};

export default TradeGeneralDetail;
