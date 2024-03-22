import React from "react";
import styled from "styled-components";

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

const ExplainBoxComponent: React.FC<{ packDiaryResponse: null | number }> = ({
  packDiaryResponse,
}) => (
  <ExplainBox>
    {packDiaryResponse ? (
      <StateIcon
        width={2.5625}
        height={0.9375}
        backgroundColor="#A0D8B3"
        color="#8c8c8c"
      >
        작물일지
      </StateIcon>
    ) : null}
  </ExplainBox>
);

export default ExplainBoxComponent;
