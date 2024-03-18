import styled from "styled-components";
import Button from "../common/Button/primaryButton";

interface ButtonType {
  onClick: () => void;
  text: string;
  backgroundColor: string;
  border?: string;
  color: string;
}

const MenuBarButton = ({
  onClick,
  text,
  backgroundColor,
  border,
  color,
}: ButtonType) => {
  return (
    <Button
      width={2.875}
      height={2}
      color={color}
      backgroundColor={backgroundColor}
      onClick={onClick}
      fontWeight="bold"
      text={text}
      borderRadius={6.25}
      border={border}
      fontSize="0.75"
    />
  );
};

export default MenuBarButton;
