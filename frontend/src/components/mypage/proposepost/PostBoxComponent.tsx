import React from "react";
import styled from "styled-components";
import Location from "../../../assets/svg/location.svg";
import StateIconComponent from "./StateIconComponent";
import ExplainBoxComponent from "./ExplainBoxComponent";
interface ImageResponse {
  id: number;
  imgStoreUrl: string;
}
interface DataItem {
  imageResponses: ImageResponse[];
  exLocation: string;
  exArticleTitle: string;
  price: number;
  isLikeCnt: number;
  exArticleType: "DEAL" | "SHARING" | "GENERAL_TRANSACTION" | "OTHER";
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
      <StyledImg src={item.imageResponses[0].imgStoreUrl} alt="tomato" />
    </ImgBox>
    <Town>
      <div>
        <img src={Location} alt="location" />
        {item.exLocation}
      </div>
      <ExplainBoxComponent item={item.exArticleType} />
    </Town>
    <Title>{item.exArticleTitle}</Title>
    <StateIconComponent item={item} />
  </PostBox>
);

export default PostBoxComponent;
