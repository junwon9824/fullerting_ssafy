import styled from "styled-components";
import { PropsWithChildren } from "react";
import { Outlet, useNavigate } from "react-router-dom";
import {
  NavBar,
  PostTopBar,
  TopBar,
  TradeTopBar,
} from "../Navigator/navigator";
import { BottomButton } from "../Button/LargeButton";

const MainBox = styled.main`
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 100%;
  width: 100%;
  padding-top: 3.125rem;
  padding-bottom: 6rem;
  overflow-y: scroll;
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

const TradePostLayout = ({
  children,
  title,
  showEdit,
}: PropsWithChildren & {
  title: string;
  showEdit?: boolean;
}) => {
  const navigate = useNavigate();
  const handleCheck = () => {
    navigate("/trade");
  };
  return (
    <>
      <PostTopBar title={title} showEdit={showEdit} />
      <MainBox>
        <InnerBox>{children || <Outlet />}</InnerBox>
      </MainBox>
    </>
  );
};

export default TradePostLayout;
