import React from "react";
import styled from "styled-components";
import LocationImg from "../../assets/svg/location.svg";

interface ScrollExchangeProps {
  currentIndex: number;
  data: any[];
  onClick: (index: number) => void;
}
interface StateGap {
  gap?: number;
  justifyContent?: string;
}
interface Icon {
  width?: number;
  backgroundColor: string;
  text?: string;
}
const ExchangeItemContainer = styled.div`
  display: flex;
  align-items: center;
  border-radius: 0.9375rem;
`;

const ExchangeItemImage = styled.img`
  border-radius: 0.9375rem;
  background: url(<path-to-image>) lightgray 50% / cover no-repeat;
  width: 5rem;
  height: 5rem;
`;

const ExchangeItemInfo = styled.div`
  display: flex;
  flex-direction: column;
  margin: 1rem;
`;

const Title = styled.span`
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 1rem;
  font-style: normal;
  font-weight: bold;
  line-height: normal;
  margin: 0.3rem;
`;

const StateIcon = styled.div<Icon & { children?: React.ReactNode }>`
  width: 1.5rem;
  height: 1rem;
  border-radius: 0.3125rem;
  background: ${(props) => props.backgroundColor};
  color: white;
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.5625rem;
`;
const State = styled.div<StateGap>`
  width: 100%;
  height: auto;
  gap: ${(props) => `${props.gap}rem`};
  font-size: 1.3rem;
  font-style: normal;
  font-weight: bold;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 0.44rem;
  justify-content: ${(props) => `${props.justifyContent}`};
  color: ${(props) => `${props.color}`};
`;

const Town = styled.div`
  width: auto;
  gap: 0.12rem;
  margin-top: 0.62rem;
  color: var(--gray1, #8c8c8c);
  font-size: 0.625rem;
  font-style: normal;
  font-weight: 400;
  display: flex;
  align-items: center;
`;

const ScrollExchange: React.FC<ScrollExchangeProps> = ({
  currentIndex,
  data,
  onClick,
}) => {
  const currentItem = data[currentIndex];
  const image =
    currentItem?.exArticleResponse?.imageResponses?.[0]?.imgStoreUrl;
  const title = currentItem?.exArticleResponse?.exArticleTitle;
  const price = currentItem?.exArticleResponse?.price;
  const location = currentItem?.exArticleResponse?.exLocation;
  return (
    <ExchangeItemContainer onClick={() => onClick(currentIndex)}>
      <ExchangeItemImage src={image} alt="Exchange Item" />
      <ExchangeItemInfo>
        <Town>
          <img src={LocationImg} alt="location" />
          {location}
        </Town>
        <Title>{title}</Title>
        <State gap={0.44}>
          {price === 0 ? (
            <StateIcon backgroundColor="#5ba7de">나눔</StateIcon>
          ) : (
            <StateIcon backgroundColor="#A0D8B3">현재</StateIcon>
          )}
          {price}원
        </State>

        <State color="#BEBEBE" justifyContent="space-between"></State>
      </ExchangeItemInfo>
    </ExchangeItemContainer>
  );
};

export default ScrollExchange;
