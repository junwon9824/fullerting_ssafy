import styled from "styled-components";
import { PropsWithChildren } from "react";
import { Outlet } from "react-router-dom";
import { NavBar, TopBar } from "../Navigator/navigator";
import { BottomButton } from "../Button/LargeButton";

const MainBox = styled.main`
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 100%;
  width: 100%;
  padding-top: 3.125rem;
  padding-bottom: 6rem;
`;

const InnerBox = styled.div`
  display: flex;
  width: 19.875rem;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1.56rem;
  padding: 1.12rem 0;
`;
const handleCheck = () => {};
const BasicLayout = ({
  children,
  title,
  showEdit,
}: PropsWithChildren & {
  title: string;
  showEdit?: boolean;
}) => {
  return (
    <>
      <TopBar title={title} showEdit={showEdit} />
      <MainBox>
        <InnerBox>{children || <Outlet />}</InnerBox>
      </MainBox>
      <BottomButton onClick={handleCheck} text="등록하기" />
    </>
  );
};

export default BasicLayout;
