import React from "react";
import styled from "styled-components";
interface ImageResponse {
  img_store_url: string;
}

interface DataItem {
  exArticleResponse: {
    exLocation: string;
    exArticleId: number;
    exArticleTitle: string;
    exArticleType: "DEAL" | "SHARING" | "GENERAL_TRANSACTION" | "OTHER";
    imageResponses: ImageResponse[];
    price: number;
  };
  packDiaryResponse: null | number;
  favoriteResponse: {
    islike: boolean;
    isLikeCnt: number;
  };
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
interface ImageResponse {
  id: number;
  img_store_url: string;
}

interface ExArticleResponse {
  exLocation: string;
  exArticleId: number;
  exArticleTitle: string;
  exArticleType: "DEAL" | "SHARING" | "GENERAL_TRANSACTION" | "OTHER";
  imageResponses: ImageResponse[];
  price: number;
}

interface FavoriteResponse {
  islike: boolean;
  isLikeCnt: number;
}

interface DataItem {
  exArticleResponse: ExArticleResponse;
  packDiaryResponse: null | number;
  favoriteResponse: FavoriteResponse;
}

const StateIcon = styled.div<Icon & { children?: React.ReactNode }>`
  width: ${(props) => `${props.width}rem`};
  height: ${(props) => `${props.height}rem`};
  border-radius: 0.3125rem;
  background: ${(props) => props.backgroundColor};
  color: ${(props) => props.color};
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.5625rem;
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

const StateIconComponent: React.FC<{ item?: DataItem }> = ({ item }) => (
  <State gap={0.44} fontSize={1}>
    <StateIcon
      width={1.5}
      height={0.9375}
      backgroundColor={
        item?.exArticleResponse?.price === 0 ? "#A0D8B3" : "#FFFFFF"
      }
      color="#ffffff"
    >
      {item?.exArticleResponse?.price === 0 ? "나눔" : "현재"}
    </StateIcon>
    {item?.exArticleResponse?.price
      ? `${item.exArticleResponse.price}원`
      : "가격 정보 없음"}
  </State>
);
export default StateIconComponent;
