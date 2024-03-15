import styled from "styled-components";
import pullleft from "/src/assets/svg/pullleft.svg";

const MainBox = styled.div`
  justify-content: center;
  flex-direction: column;
  height: 14.5rem;
  padding: 1%.5rem 0;
`;
const LogoAndTextContainer = styled.div`
  display: flex;
  align-items: center;
  gap: 0.5rem;
`;
const Text = styled.div`
  color: #2b5f50;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.75rem;
  font-weight: 600;
  line-height: 1.125rem;
`;

const Character = styled.button`
  display: flex;
  align-items: center;
  padding-top: 1rem;
  margin-right: 0.5rem;
`;

const LogoContent = styled.div`
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.75rem;
  font-style: normal;
  font-weight: 600;
  line-height: 1.125rem;
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
  display: flex;
  padding: 0.4375rem 0.5rem;
  height: 7rem;
  width: 18rem;
  border-radius: 0rem 0.9375rem 0.9375rem 0.9375rem;
  border: 1.5px solid var(--sub0, #a0d8b3);
`;

const ExchangeContainer = styled.div`
  margin-top: 0.8rem;
  display: flex;
  gap: 1.0625rem;
`;

const MainExchange = () => {
  return (
    <MainBox>
      <Text>나의 반려작물의 모든 것, 풀러팅에서 함께</Text>
      <LogoAndTextContainer>
        <Character>
          <img src={pullleft} alt="" />
        </Character>
        <LogoContent>
          나의 작물을 판매하고 나눔해 보세요
          <LogoText>작물 거래하기</LogoText>
        </LogoContent>
      </LogoAndTextContainer>
      <ExchangeContainer>
        <ExchangeBox>작물거래</ExchangeBox>
      </ExchangeContainer>
    </MainBox>
  );
};

export default MainExchange;
