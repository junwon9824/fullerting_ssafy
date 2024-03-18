import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
const Logout = () => {
  return (
    <>
      <TopBar title="로그아웃" showBack={false} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <h1>로그아웃</h1>
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};
export default Logout;
