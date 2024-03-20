import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import BadgeImage from "../../components/mypage/badge/BadgeImage";
import BadgeTop from "../../components/mypage/badge/BadgeTop";
const AllBadge = () => {
  return (
    <>
      <TopBar title="보유 뱃지" />
      <LayoutMainBox>
        <LayoutInnerBox>
          <BadgeTop />
          <BadgeImage />
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default AllBadge;
