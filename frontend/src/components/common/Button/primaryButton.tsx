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
}
const ColorStyle = styled.button<ButtonProps>`
  background-color: ${(props: ButtonProps) => props.backgroundColor};
  width: ${(props: ButtonProps) => `${props.width}rem`};
  height: ${(props: ButtonProps) => `${props.height}rem`};
  font-weight: ${(props: ButtonProps) => `${props.fontWeight}`};
  border-radius: ${(props: ButtonProps) => `${props.borderRadius}rem`};
  font-size: 1rem;
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
        borderRadius={props.borderRadius}
      >
        {props.text}
      </ColorStyle>
    </>
  );
};

export default Button;
