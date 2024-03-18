import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
const AllBadge = () => {
  return (
    <>
      <TopBar title="보유 뱃지" showBack={false} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <h1>전체뱃지</h1>
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default AllBadge;
