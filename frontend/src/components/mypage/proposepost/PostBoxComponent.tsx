import React from "react";
import styled from "styled-components";
import StateIconComponent from "./StateIconComponent";
import HeartBoxComponent from "./HeartBoxComponent";
import ExplainBoxComponent from "./ExplainBoxComponent";
interface ImageResponse {
  id: number;
  img_store_url: string;
}
interface DataItem {
  imageResponses: ImageResponse[];
  exLocation: string;
  exArticleTitle: string;
  price: number;
  isLikeCnt: number;
  exArticleType: "DEAL" | "SHARING" | "GENERAL_TRANSACTION" | "OTHER";
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
const ImgBox = styled.div`
  width: 9rem;
  height: 9rem;
  border-radius: 0.9375rem;
  top: 0;
  left: 0;
  position: relative;
  overflow: hidden;

  cursor: pointer;
`;

const StyledImg = styled.img`
  width: 9.5rem;
  height: 9.5rem;
  object-fit: cover;
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
  justify-content: space-between;
`;
const PostBox = styled.div`
  width: 9rem;
  height: auto;
  margin-bottom: 1.38rem;
`;
const Title = styled.div`
  width: auto;
  margin-top: 0.5rem;
  align-items: center;
  font-size: 0.75rem;
  font-style: normal;
  font-weight: 400;
`;

const PostBoxComponent: React.FC<{ item: DataItem }> = ({ item }) => (
  <PostBox>
    <ImgBox>
      <StyledImg src={item.imageResponses[0]?.img_store_url} alt="Post Image" />
    </ImgBox>
    <Town>
      <img src={Location} alt="location" />
      {item.exLocation}
    </Town>
    <Title>{item.exArticleTitle}</Title>
    <StateIconComponent item={item.exArticleResponse} />
    <HeartBoxComponent isLikeCnt={item.isLikeCnt} />
    <ExplainBoxComponent packDiaryResponse={item.packDiaryResponse} />
  </PostBox>
);

export default PostBoxComponent;
