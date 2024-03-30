import {} from "../../components/common/Layout/Box";
import { Line } from "../../components/common/Line";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import CommunityContent from "../../components/community/CommunityContent";
import CommunityTitle from "../../components/community/CommunityTitle";

const CommunityDetail = () => {
  return (
    <>
      <TopBar title="커뮤니티" />
      <CommunityTitle />
      <Line />
      <CommunityContent />
      <Line />
      <NavBar />
    </>
  );
};

export default CommunityDetail;
