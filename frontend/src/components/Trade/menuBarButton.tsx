import styled from "styled-components";
import Button from "../common/Button/primaryButton";

interface ButtonType {
  onClick: () => void;
  text: string;
  backgroundColor: string;
}

const MenuBarButton = ({ onClick, text, backgroundColor }: ButtonType) => {
  return (
    <Button
      width={2.875}
      height={2}
      color="#ffffff"
      backgroundColor={backgroundColor}
      onClick={onClick}
      fontWeight="bold"
      text={text}
      borderRadius={6.25}
    />
  );
};

export default MenuBarButton;
