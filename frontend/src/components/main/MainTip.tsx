import styled from "styled-components";
import pullright from "/src/assets/svg/pullright.svg";

const MainBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: end;
`;
const LogoAndTextContainer = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  gap: 0.5rem;
`;

const Character = styled.button`
  display: flex;
  align-items: center;
  margin-right: 0.5rem;
`;

const LogoContent = styled.div`
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.75rem;
  font-style: normal;
  font-weight: 600;
  line-height: 1.125rem;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;
const LogoText = styled.div`
  color: var(--sub0, #a0d8b3);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 1.3rem;
  font-style: normal;
  font-weight: 800;
  line-height: 1.125rem;
  margin-top: 0.5rem;
`;

const ExchangeBox = styled.div`
  width: 6.5rem;
  height: 5.9375rem;
  border-radius: 0.875rem;
  background: rgba(229, 249, 219, 0.37);
  display: flex;
  justify-content: center;
  gap: 0.6875rem;
`;

const ExchangeContainer = styled.div`
  margin-top: 0.8rem;
  display: flex;
  gap: 0.6875rem;
`;

const MainTip = () => {
  return (
    <MainBox>
      <LogoAndTextContainer>
        <LogoContent>
          작물 꿀팁을 공유해보세요
          <LogoText>꿀팁 공유하기</LogoText>
        </LogoContent>
        <Character>
          <img src={pullright} alt="" />
        </Character>
      </LogoAndTextContainer>
      <ExchangeContainer>
        <ExchangeBox />
        <ExchangeBox />
        <ExchangeBox />
      </ExchangeContainer>
    </MainBox>
  );
};

export default MainTip;
