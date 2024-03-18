import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
const ProposePost = () => {
  return (
    <>
      <TopBar title="나의 제안 목록" showBack={false} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <h1>나의 제안 목록</h1>
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};
export default ProposePost;
