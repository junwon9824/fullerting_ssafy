import { useState } from "react";
import styled from "styled-components";
import { HalfLine } from "../common/Line";

const MenuBarBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;
const MenuBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  flex-direction: column;
  font-size: 1rem;
  color: ${({ theme }) => theme.colors.gray0};
  font-weight: bold;
`;
const Menu = ({ text }) => {
  return (
    <MenuBox>
      <span>{text}</span>
      <HalfLine />
    </MenuBox>
  );
};

const MenuBar = () => {
  const [menu, setMenu] = useState("다이어리"); // 다이어리, 작물꿀팁

  const handleMenuClick = (text) => {
    setMenu(text);
  };

  return (
    <MenuBarBox>
      <Menu text="다이어리" />
      <Menu text="작물꿀팁" />
    </MenuBarBox>
  );
};

export default MenuBar;
