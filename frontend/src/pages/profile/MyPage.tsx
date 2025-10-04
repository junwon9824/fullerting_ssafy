import styled from "styled-components";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import MyPageTop from "../../components/mypage/MyPageTop";
import Badge from "../../components/mypage/Badge";
import { LayoutMainBox } from "../../components/common/Layout/Box";
import { Line } from "../../components/common/Line";

const LayoutInnerBox = styled.div`
  display: flex;
  width: 19.875rem;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1.12rem 0;
  gap: 0.5rem;
`;

const MainPage = () => {
  return (
    <>
      <TopBar title="마이페이지" showBack={false} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <MyPageTop />
          <Line />
          <Badge />
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default MainPage;
