import React from "react";
import styled from "styled-components";

interface ButtonProps {
  onClick: () => void;
  width: number;
  height: number;
  backgroundColor: string;
  fontSize?: string;
  fontWeight?: string;
  padding?: string;
  children?: React.ReactNode;
  color?: string;
}

const ColorStyle = styled.button<ButtonProps>`
  background-color: ${(props: ButtonProps) => props.backgroundColor};
  width: ${(props: ButtonProps) => `${props.width}rem`};
  height: ${(props: ButtonProps) => `${props.height}rem`};
  font-weight: ${(props: ButtonProps) => `${props.fontWeight}`};
  border-radius: 8px;
  font-size: 16px;
  color: white;
`;

const Button = (props: ButtonProps) => {
  return (
    <>
      <ColorStyle
        onClick={props.onClick}
        width={props.width}
        height={props.height}
        fontWeight={props.fontWeight}
        backgroundColor={props.backgroundColor}
      >
        {props.children}
      </ColorStyle>
    </>
  );
};

export default Button;
