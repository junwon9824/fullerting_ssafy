import { useMutation } from "@tanstack/react-query";
import {} from "../../components/common/Layout/Box";
import { Line } from "../../components/common/Line";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import CommunityContent from "../../components/community/CommunityContent";
import CommunityTitle from "../../components/community/CommunityTitle";
import CommunityComment from "../../components/community/Communitycomment";
import { useNavigate } from "react-router-dom";
import { DeleteArticle } from "../../apis/CommunityApi";
import { useParams } from "react-router-dom";

const CommunityDetail = () => {
  const { communityId } = useParams();
  const navigate = useNavigate();
  const { mutate: deleteMutate } = useMutation({
    mutationFn: DeleteArticle,
    onSuccess: () => {
      navigate("/community");
    },
    onError: (err) => {
      console.log(err);
    },
  });
  console.log(communityId);

  const handleDeleteCrop = () => {
    if (!communityId) return;
    deleteMutate(communityId);
  };

  return (
    <>
      <TopBar title="커뮤니티" showEdit={true} deleteFunc={handleDeleteCrop} />
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
