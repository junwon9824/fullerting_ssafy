import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
const LikedPost = () => {
  return (
    <>
      <TopBar title="관심 게시물" showBack={false} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <h1>관심 게시물</h1>
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};
export default LikedPost;
