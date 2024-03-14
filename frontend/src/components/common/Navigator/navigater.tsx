import styled from "styled-components";
import Arrow from "/src/assets/svg/backarrow.svg";
import Cart from "/src/assets/svg/cart.svg";
import Chat from "/src/assets/svg/chat.svg";
import Diary from "/src/assets/svg/diary.svg";
import Home from "/src/assets/svg/home.svg";
import Mypage from "/src/assets/svg/mypage.svg";
import { useNavigate } from "react-router-dom";

let svgArray: string[] = [Home, Cart, Chat, Diary, Mypage];
interface DiaryTitleBarProps {
  title: string;
}

interface NavItem {
  Icon: string;
  path: string;
}
const Titlebox = styled.div`
  width: 100%;
  height: 2.75rem;
  position: relative;
  align-items: center;
  text-align: center;
  justify-content: center;
  display: flex;
  font-size: 1rem;
  font-style: normal;
  font-weight: bold;
`;
const Backsvgbox = styled.div`
  width: 1.5rem;
  height: 1.5rem;
  justify-content: center;
  align-items: center;
  left: 1.31rem;
  display: flex;
  position: absolute;
`;

const NavBox = styled.div`
  width: 100%;
  height: 3.625rem;
  justify-content: space-between;
  border-radius: 0.9375rem 0.9375rem 0rem 0rem;
  border: 2px solid var(--gary3, #f4f4f4);
  align-items: center;
  bottom: 0;
  display: flex;
  padding-left: 2rem;
  padding-right: 2rem;
  position: absolute;
`;

const SvgBox = styled.img`
  width: 1.75rem;
  height: 1.75rem;
  display: flex;
`;
const navItems: NavItem[] = [
  { Icon: Home, path: "/main" },
  { Icon: Cart, path: "/cart" },
  { Icon: Chat, path: "/chat" },
  { Icon: Diary, path: "/diary" },
  { Icon: Mypage, path: "/mypage" },
];

const DiaryTitleBar = ({ title }: DiaryTitleBarProps): JSX.Element => {
  const navigate = useNavigate();
  const onClickBack = (event: React.MouseEvent<HTMLDivElement>) => {
    navigate(-1);
  };
  return (
    <Titlebox>
      <Backsvgbox onClick={onClickBack}>
        <img src={Arrow} alt="" />
      </Backsvgbox>
      {title}
    </Titlebox>
  );
};

const NavBar = (): JSX.Element => {
  const navigate = useNavigate();
  const onNavClick = (path: string) => {
    navigate(path);
  };
  return (
    <NavBox>
      {navItems.map((item, index) => (
        <SvgBox
          key={index}
          src={item.Icon}
          alt="svg"
          onClick={() => {
            onNavClick(item.path);
          }}
        />
      ))}
    </NavBox>
  );
};

export { DiaryTitleBar, NavBar };
