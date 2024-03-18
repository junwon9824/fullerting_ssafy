import styled from "styled-components";
import { HalfColoredLine, HalfLine } from "../common/Line";
import { useAtom } from "jotai";
import { menuAtom } from "../../stores/diary";

const MenuBarBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;
const MenuBox = styled.button`
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  flex-direction: column;
  font-size: 1rem;
  color: ${({ theme }) => theme.colors.gray0};
  font-weight: bold;
  height: 1.8rem;
  width: 9.93rem;
`;
const FlexColumnBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
const Menu = ({ text }) => {
  const [menu, setMenu] = useAtom(menuAtom);
  const selected = menu === text;

  const handleMenuClick = () => {
    setMenu(text);
    console.log("Menu clicked: ", text);
  };

  return (
    <FlexColumnBox>
      <MenuBox onClick={handleMenuClick}>
        <span>{text}</span>
      </MenuBox>
      {selected ? <HalfColoredLine /> : <HalfLine />}
    </FlexColumnBox>
  );
};

const MenuBar = () => {
  const [menu] = useAtom(menuAtom);

  return (
    <MenuBarBox>
      <Menu text="다이어리" />
      <Menu text="작물꿀팁" />
    </MenuBarBox>
  );
};

export default MenuBar;
