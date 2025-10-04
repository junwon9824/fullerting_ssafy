import styled from "styled-components";
import { HalfColoredLine, HalfLine } from "../common/Line";
import { useAtom } from "jotai";
import { menuAtom } from "../../stores/diary";

interface MenuBoxType {
  selected: boolean;
  theme: any;
}

const MenuBarBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;
const MenuBox = styled.button<MenuBoxType>`
  display: flex;
  align-items: center;
  justify-content: center;
  text-align: center;
  flex-direction: column;
  font-size: 0.9rem;
  font-weight: bold;
  color: ${({ selected, theme }) =>
    selected ? theme.colors.black : theme.colors.gray0};
  height: 1.3rem;
  width: 9.93rem;
`;
const FlexColumnBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
`;
const Menu = ({ text }: { text: string }) => {
  const [menu, setMenu] = useAtom(menuAtom);
  const selected = menu === text;

  const handleMenuClick = () => {
    setMenu(text);
  };

  return (
    <FlexColumnBox>
      <MenuBox selected={selected} onClick={handleMenuClick}>
        <span>{text}</span>
      </MenuBox>
      {selected ? <HalfColoredLine /> : <HalfLine />}
    </FlexColumnBox>
  );
};

const MenuBar = () => {
  return (
    <MenuBarBox>
      <Menu text="다이어리" />
      <Menu text="작물꿀팁" />
    </MenuBarBox>
  );
};

export default MenuBar;
