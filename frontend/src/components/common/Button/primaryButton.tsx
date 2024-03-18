import styled from "styled-components";

interface ButtonProps {
  onClick: () => void;
  width: number;
  height: number;
  backgroundColor: string;
  fontSize?: string;
  fontWeight?: string;
  padding?: string;
  text?: string;
  color?: string;
  borderRadius?: number;
  border?: string;
}
const ColorStyle = styled.button<ButtonProps>`
  background-color: ${(props) => props.backgroundColor};
  width: ${(props) => `${props.width}rem`};
  height: ${(props) => `${props.height}rem`};
  font-weight: ${(props) => `${props.fontWeight}`};
  border-radius: ${(props) => `${props.borderRadius}rem`};
  border: ${(props) => `${props.border}`};
  font-size: ${(props) => `${props.fontSize}rem`};
  color: ${(props) => `${props.color}`};
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
        borderRadius={props.borderRadius}
        color={props.color}
        border={props.border}
        fontSize={props.fontSize}
      >
        {props.text}
      </ColorStyle>
    </>
  );
};

export default Button;
