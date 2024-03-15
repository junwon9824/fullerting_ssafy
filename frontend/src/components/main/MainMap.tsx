import styled from "styled-components";

const MainBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  display: inline;
  gap: 0.8125rem;
  height: 5.5rem;
  padding: 1% 0.5rem 0;
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
  margin-bottom: 0.5rem;
`;

const ExchangeBox = styled.div`
  display: flex;
  padding: 0.4375rem 0.5rem;
  height: 7rem;
  width: 18rem;
  border-radius: 0rem 0.9375rem 0.9375rem 0.9375rem;
  border: 1.5px solid var(--sub0, #a0d8b3);
`;

const MainMap = () => {
  return (
    <MainBox>
      <LogoContent>
        <LogoText>전국</LogoText>
        텃밭정보 보러 가기
      </LogoContent>
      <ExchangeBox />
    </MainBox>
  );
};

export default MainMap;
