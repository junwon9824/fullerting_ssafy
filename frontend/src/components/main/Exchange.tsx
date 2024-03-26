import React, { useState } from "react";
import styled from "styled-components";

interface ScrollExchangeProps {
  currentIndex: number;
  data: any[];
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
  gap: 8px; // Add space between the icon and text
`;

const LocationImage = styled.div`
  margin-right: 0.5rem;
  width: 1em;
  height: 1em;
`;

const ScrollExchange: React.FC<ScrollExchangeProps> = ({
  currentIndex,
  data,
}) => {
  const currentItem = data[currentIndex];
  const image =
    currentItem?.exArticleResponse?.imageResponses?.[0]?.imgStoreUrl;
  const title = currentItem?.exArticleResponse?.exArticleTitle;
  const price = currentItem?.exArticleResponse?.price;
  const location = currentItem?.exArticleResponse?.exLocation;
  return (
    <ExchangeItemContainer>
      <ExchangeItemImage src={image} alt="Exchange Item" />
      <ExchangeItemInfo>
        <Location>{location}</Location>

        <Title>{title}</Title>
        <Price>{`${price}원`}</Price>
        <Price>{currentIndex}</Price>
      </ExchangeItemInfo>
    </ExchangeItemContainer>
  );
};

export default ScrollExchange;
