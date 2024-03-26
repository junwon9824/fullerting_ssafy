import React from "react";
import styled from "styled-components";

interface ScrollExchangeProps {
  currentIndex: number;
  data: any[];
  onClick: (index: number) => void;
}
interface StateGap {
  gap?: number;
  fontSize?: number;
  color?: string;
  justifyContent?: string;
}
interface Icon {
  width?: number;
  height: number;
  backgroundColor: string;
  color: string;
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
`;

const Price = styled.span`
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 1rem;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
`;

const Location = styled.span`
  display: flex;
  align-items: center;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 1rem;
  color: #000;
  gap: 8px;
`;

const LocationImage = styled.div`
  margin-right: 0.5rem;
  width: 1em;
  height: 1em;
`;

const StateIcon = styled.div<Icon & { children?: React.ReactNode }>`
  width: ${(props) => `${props.width}rem`};
  height: ${(props) => `${props.height}rem`};
  border-radius: 0.3125rem;
  background: ${(props) => props.backgroundColor};
  color: ${(props) => props.color};
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.5625rem; /* 텍스트 크기 */
`;
const State = styled.div<StateGap>`
  width: 100%;
  height: auto;
  gap: ${(props) => `${props.gap}rem`};
  font-size: ${(props) => `${props.fontSize}rem`};
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
const ExplainBox = styled.div`
  width: auto;
  height: auto;
  justify-content: flex-end;
  align-items: center;
  display: flex;
  flex-direction: row;
  gap: 0.25rem;
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
  const exArticleType = currentItem?.exArticleResponse?.exArticleType;
  return (
    <ExchangeItemContainer onClick={() => onClick(currentIndex)}>
      <ExchangeItemImage src={image} alt="Exchange Item" />
      <ExchangeItemInfo>
        <Town>
          <img src={Location} alt="location" />
          {location}
        </Town>
        <Title>{title}</Title>
        <State gap={0.44} fontSize={1}>
          {price === 0 ? (
            <StateIcon
              width={1.5}
              height={0.9375}
              backgroundColor="#A0D8B3"
              color="#ffffff"
            >
              나눔
            </StateIcon>
          ) : (
            <StateIcon
              width={1.5}
              height={0.9375}
              backgroundColor="#A0D8B3"
              color="#ffffff"
            >
              현재
            </StateIcon>
          )}
          {price}원
        </State>

        <State fontSize={0.5625} color="#BEBEBE" justifyContent="space-between">
          <ExplainBox>
            <StateIcon
              width={1.5}
              height={0.9375}
              backgroundColor="#F4F4F4"
              color="#8c8c8c"
            >
              {exArticleType === "DEAL"
                ? "제안"
                : exArticleType === "SHARING"
                ? "나눔"
                : exArticleType === "GENERAL_TRANSACTION"
                ? "거래"
                : "error"}
            </StateIcon>
          </ExplainBox>
        </State>
        <Price>{currentIndex}</Price>
      </ExchangeItemInfo>
    </ExchangeItemContainer>
  );
};

export default ScrollExchange;
