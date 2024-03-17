import styled from "styled-components";
import Arrow from "/src/assets/svg/backarrow.svg";
import Cart from "/src/assets/svg/cart.svg";
import Chat from "/src/assets/svg/chat.svg";
import Diary from "/src/assets/svg/diary.svg";
import Home from "/src/assets/svg/home.svg";
import Mypage from "/src/assets/svg/mypage.svg";
import Modify from "/src/assets/svg/modify.svg";
import Delete from "/src/assets/svg/delete.svg";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

interface NavItem {
  Icon: string;
  path: string;
}

const NavBox = styled.div`
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  background-color: ${({ theme }) => theme.colors.white};
`;

const NavInnerBox = styled.div`
  width: 22.5rem;
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
  background-color: ${({ theme }) => theme.colors.white};
`;

const SvgBox = styled.img`
  height: 1.25rem;
  cursor: pointer;
`;

interface TopBarType {
  title: string;
  showEdit?: boolean;
  showBack?: boolean;
  showTitle?: boolean;
}

const TopBox = styled.div`
  display: inline-flex;
  padding: 0rem 1.3125rem;
  justify-content: center;
  align-items: center;
  background-color: ${({ theme }) => theme.colors.white};
  width: 100%;
  position: fixed;
`;
const TopInnerBox = styled.div`
  display: flex;
  flex-direction: row;
  align-items: center;
  justify-content: center;
  width: 19.875rem;
  height: 3.125rem;
  position: relative;
`;
const TitleBox = styled.div`
  font-size: 1rem;
  font-weight: bold;
`;
const BackSvgBox = styled.div`
  width: 1.5rem;
  height: 1.5rem;
  position: absolute;
  top: 0.8125rem;
  left: 0rem;
  cursor: pointer;
`;
const EditBox = styled.div`
  display: flex;
  gap: 0.375rem;
  position: absolute;
  right: 0rem;
  top: 0.8125rem;
`;

const EditButton = styled.button``;
const DeleteButton = styled.button``;

const TopBar = ({
  title,
  showEdit = false,
  showBack = true,
  showTitle = true,
}: TopBarType) => {
  const navigate = useNavigate();

  const onClickBack = () => {
    navigate(-1);
  };

  const onClickEdit = () => {};

  const onClickDelete = () => {};

  return (
    <TopBox>
      <TopInnerBox>
        {showBack && (
          <BackSvgBox onClick={onClickBack}>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
            >
              <path
                d="M20 11H7.83L13.42 5.41L12 4L4 12L12 20L13.41 18.59L7.83 13H20V11Z"
                fill="black"
              />
            </svg>
          </BackSvgBox>
        )}
        <TitleBox>{showTitle && title}</TitleBox>
        {showEdit && (
          <EditBox>
            <EditButton onClick={onClickEdit}>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
              >
                <path
                  d="M14.0588 9.02L14.9788 9.94L5.91878 19H4.99878V18.08L14.0588 9.02ZM17.6588 3C17.4088 3 17.1488 3.1 16.9588 3.29L15.1288 5.12L18.8788 8.87L20.7088 7.04C21.0988 6.65 21.0988 6.02 20.7088 5.63L18.3688 3.29C18.1688 3.09 17.9188 3 17.6588 3ZM14.0588 6.19L2.99878 17.25V21H6.74878L17.8088 9.94L14.0588 6.19Z"
                  fill="black"
                />
              </svg>
            </EditButton>
            <DeleteButton onClick={onClickDelete}>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="24"
                height="24"
                viewBox="0 0 24 24"
                fill="none"
              >
                <path
                  d="M3 6H21M19 6V20C19 21 18 22 17 22H7C6 22 5 21 5 20V6M8 6V4C8 3 9 2 10 2H14C15 2 16 3 16 4V6M10 11V17M14 11V17"
                  stroke="black"
                  stroke-width="2"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </svg>
            </DeleteButton>
          </EditBox>
        )}
      </TopInnerBox>
    </TopBox>
  );
};

const navItems: NavItem[] = [
  { Icon: Home, path: "/" },
  { Icon: Cart, path: "/cart" },
  { Icon: Chat, path: "/chat" },
  { Icon: Diary, path: "/diary" },
  { Icon: Mypage, path: "/mypage" },
];

const NavBar = (): JSX.Element => {
  const navigate = useNavigate();
  const onNavClick = (path: string) => {
    navigate(path);
  };
  return (
    <NavBox>
      <NavInnerBox>
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
      </NavInnerBox>
    </NavBox>
  );
};

export { TopBar, NavBar };
