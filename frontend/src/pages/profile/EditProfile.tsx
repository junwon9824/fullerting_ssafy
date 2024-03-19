import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
const EditProfile = () => {
  return (
    <>
      <TopBar title="프로필 수정" showBack={false} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <h1>프로필수정</h1>
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};
export default EditProfile;
