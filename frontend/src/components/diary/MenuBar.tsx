import styled from "styled-components";
import { HalfColoredLine, HalfLine } from "../common/Line";
import { useAtom } from "jotai";
import { menuAtom } from "../../stores/diary";
import { useNavigate } from "react-router-dom";

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
  font-weight: bold;
  color: ${({ selected, theme }) =>
    selected ? theme.colors.black : theme.colors.gray0};
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
  const navigate = useNavigate();

  const handleMenuClick = () => {
    setMenu(text);
    // console.log("Menu clicked: ", text);

    // if (text === "다이어리") {
    //   navigate("/diary/detail");
    // } else if (text === "작물꿀팁") {
    //   navigate("/diary/detail/tips");
    // }
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
  const [menu] = useAtom(menuAtom);

  return (
    <MenuBarBox>
      <Menu text="다이어리" />
      <Menu text="작물꿀팁" />
    </MenuBarBox>
  );
};

export default MenuBar;
