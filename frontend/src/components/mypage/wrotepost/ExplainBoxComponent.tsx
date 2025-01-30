import React from "react";
import styled from "styled-components";
interface DataItem {
  imageResponses: ImageResponse[];
  exLocation: string;
  exArticleTitle: string;
  price: number;
  isLikeCnt: number;
  exArticleType: "DEAL" | "SHARING" | "GENERAL_TRANSACTION" | "OTHER";
}

interface Props {
  item: DataItem["exArticleType"]; // 타입을 수정했습니다.
}

interface ImageResponse {
  id: number;
  imgStoreUrl: string;
}
interface Icon {
  width?: number;
  height: number;
  backgroundColor: string;
  color: string;
  text?: string;
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

const ExplainBox = styled.div`
  width: auto;
  height: auto;
  justify-content: flex-end;
  align-items: center;
  display: flex;
  flex-direction: row;
  gap: 0.25rem;
`;

const ExplainBoxComponent: React.FC<Props> = ({ item }) => (
  <ExplainBox>
    {item ? (
      <StateIcon
        width={2.5}
        height={1.3}
        backgroundColor="#0c560f"
        color="#ffffff"
      >
        {item === "DEAL" ? "제안" : "기타"}
      </StateIcon>
    ) : null}
  </ExplainBox>
);

export default ExplainBoxComponent;
