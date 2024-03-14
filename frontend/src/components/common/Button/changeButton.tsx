import React from "react";
import styled from "styled-components";

interface ChangeButtonProps {
  onClick: () => void;
  height?: number;
  width?: number;
  backgroundColor?: string;
  fontSize?: number;
  fontWeight?: number;
  padding?: string;
  children?: React.ReactNode;
  color?: string;
}

const ColorStyle = styled.button<ChangeButtonProps>`
  height: 32px;
  width: 46px;
  border-radius: 100px;
  font-size: 12px;
  background-color: #a0d8b3;
  color: #ffffff;
`;

const ChangeButton = (props: ChangeButtonProps) => {
  return (
    <>
      <ColorStyle onClick={props.onClick}>{props.children}</ColorStyle>
    </>
  );
};

export default ChangeButton;
