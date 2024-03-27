import { TopBar } from "../../components/common/Navigator/navigator";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { useNavigate, useParams } from "react-router-dom";
import { useMutation } from "@tanstack/react-query";
import { deleteDiary } from "../../apis/DiaryApi";

const DiaryDetailPage = () => {
  const { diaryId } = useParams();
  const navigate = useNavigate();

  const { mutate: deleteMutate } = useMutation({
    mutationFn: deleteDiary,
    onSuccess: () => {
      navigate(-1);
    },
    onError: (err) => {
      console.log(err);
    },
  });

  const handleDeleteDiary = () => {
    if (!diaryId) return;
    deleteMutate(diaryId);
  };

  return (
    <>
      <TopBar title="다이어리" showEdit={true} deleteFunc={handleDeleteDiary} />
      <LayoutMainBox>
        <LayoutInnerBox>다이어리임</LayoutInnerBox>
      </LayoutMainBox>
      {/* <NavBar /> */}
    </>
  );
};

export default DiaryDetailPage;
