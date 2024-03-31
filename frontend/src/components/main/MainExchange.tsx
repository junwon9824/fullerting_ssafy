import styled from "styled-components";
import pullleft from "/src/assets/svg/pullleft.svg";
import ScrollExchange from "./Exchange";
import { useQuery } from "@tanstack/react-query";
import { useState } from "react";
import { getExchange } from "../../apis/Main";
import { useNavigate } from "react-router-dom";
import arrow_forward_ios from "../../assets/svg/arrow_forward_ios.svg";

const MainBox = styled.div`
  justify-content: center;
  flex-direction: column;
  height: 12.4rem;
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
  font-size: 0.9rem;
  font-weight: bold;
  line-height: 2.3rem;
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
`;
const LogoText = styled.div`
  color: var(--sub0, #a0d8b3);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 1.2rem;
  font-style: normal;
  font-weight: 800;
  line-height: 1.125rem;
  margin-top: 0.5rem;
`;

const ExchangeBox = styled.div`
  display: flex;
  padding: 0.4375rem 0.5rem;
  height: 6rem;
  width: 18rem;
  border-radius: 0rem 0.9375rem 0.9375rem 0.9375rem;
  border: 1.5px solid var(--sub0, #a0d8b3);
`;

const ExchangeContainer = styled.div`
  display: flex;
  gap: 1.0625rem;
`;
const NextBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  padding: 0.5rem;
  background-color: #a0d8b3;
  border-radius: 50%;

  margin: auto;
`;
const NextButton = styled.button`
  padding: 0;
  background-color: a0d8b3;
  background-image: url("${arrow_forward_ios}");
  background-repeat: no-repeat;
  background-position: center;
  width: 1rem;
  height: 1rem;
  border: none;
  cursor: pointer;
`;

const TokenBox = styled.div`
  font-family: "GamtanRoad Dotum TTF";
  display: flex;
  align-items: center;
  font-size: 0.8rem;
  margin-left: 0.5rem;
  font-weight: bold;
  color: #a0d8b3;
`;

const MainExchange = () => {
  const accessToken = sessionStorage.getItem("accessToken");
  const [currentIndex, setCurrentIndex] = useState(0);
  const navigate = useNavigate();

  const { data } = useQuery({
    queryKey: ["Exchange"],
    queryFn: accessToken ? () => getExchange(accessToken) : undefined,
  });

  const handleItemClick = () => {
    navigate(`/trade`);
  };

  const goToLogin = () => {
    navigate("/login");
  };

  const handleNextClick = () => {
    if (data) {
      setCurrentIndex((prevIndex) => (prevIndex + 1) % data.length);
    }
  };
  if (!data || data.length === 0) return <div>거래 데이터 없음</div>;

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
      {accessToken ? (
        <ExchangeContainer>
          <ExchangeBox>
            <ScrollExchange
              onClick={handleItemClick}
              currentIndex={currentIndex}
              data={data}
            />
          </ExchangeBox>
          <NextBox>
            <NextButton onClick={handleNextClick} />
          </NextBox>
        </ExchangeContainer>
      ) : (
        <ExchangeBox onClick={goToLogin}>
          <TokenBox>
            <div>로그인을 해주세요</div>
          </TokenBox>
        </ExchangeBox>
      )}
    </MainBox>
  );
};

export default MainExchange;
