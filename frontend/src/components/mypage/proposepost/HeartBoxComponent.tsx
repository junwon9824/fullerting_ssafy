import React from "react";
import styled from "styled-components";
import GrayHeart from "../../..//assets/svg/grayheart.svg";

const HeartBox = styled.div`
  width: auto;
  height: auto;
  justify-content: space-between;
  display: flex;
  flex-direction: row;
  left: 0;
  gap: 0.19rem;
  align-items: center;
`;

const HeartBoxComponent: React.FC<{ isLikeCnt: number }> = ({ isLikeCnt }) => (
  <HeartBox>
    <img src={GrayHeart} alt="like" style={{ marginRight: "0.19rem" }} />
    {isLikeCnt}
  </HeartBox>
);

export default HeartBoxComponent;
