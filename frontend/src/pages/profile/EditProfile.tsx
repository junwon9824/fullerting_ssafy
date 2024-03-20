import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import { TopBar } from "../../components/common/Navigator/navigator";
import EditImage from "../../components/mypage/editprofile/EditImage";
import EditInformation from "../../components/mypage/editprofile/EditInfromation";
const EditProfile = () => {
  return (
    <>
      <TopBar title="프로필 수정" />
      <LayoutMainBox>
        <LayoutInnerBox>
          <EditImage />
          <EditInformation />
        </LayoutInnerBox>
      </LayoutMainBox>
    </>
  );
};
export default EditProfile;
