import {} from "../../components/common/Layout/Box";
import { Line } from "../../components/common/Line";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import CommunityContent from "../../components/community/CommunityContent";
import CommunityTitle from "../../components/community/CommunityTitle";
import CommunityComment from "../../components/community/Communitycomment";

const CommunityDetail = () => {
  return (
    <>
      <TopBar title="커뮤니티" showEdit={true} />
      <CommunityTitle />
      <Line />
      <CommunityContent />
      <Line />
      <CommunityComment />
      <NavBar />
    </>
  );
};

export default CommunityDetail;
