import { useMutation } from "@tanstack/react-query";
import { BottomButton } from "../../components/common/Button/LargeButton";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { TopBar } from "../../components/common/Navigator/navigator";
import { calculateStep } from "../../apis/AIApi";
import { useNavigate, useParams } from "react-router-dom";
import { useAtom } from "jotai";
import { fileAtom } from "../../stores/diary";
import AIFileUploadInput from "../../components/common/Input/AIFileUploadInput";
import { changeStep } from "../../apis/DiaryApi";
import { useState } from "react";

const RecognizePage = () => {
  const navigate = useNavigate();
  const { packDiaryId } = useParams();
  const [selectedFiles, setSelectedFiles] = useAtom(fileAtom);
  const [isLoading, setIsLoading] = useState(false);

  const { mutate: aiMutate } = useMutation({
    mutationFn: calculateStep,
    onSuccess: (res) => {
      setIsLoading(false);

      const confirmed = window.confirm(
        `${res.crop_type} ${res.grade}단계 입니다.\n작물 단계를 바꿀까요?`
      );

      if (!packDiaryId) return;

      if (confirmed) {
        mutate({
          packDiaryId: packDiaryId,
          cropStepGrowth: res.grade,
        });
        navigate(`/crop/${packDiaryId}`);
        setSelectedFiles([]);
      }
    },
    onError: (error) => {
      setIsLoading(false);
      setSelectedFiles([]);
      console.log(error);
    },
  });

  const { mutate } = useMutation({
    mutationFn: changeStep,
    onSuccess: () => {},
    onError: () => {
      alert("작물 단계 변경에 실패하였습니다.\n다시 시도해주세요.");
    },
  });

  const handleConfirmClick = () => {
    setIsLoading(true);
    aiMutate(selectedFiles[0]);
  };

  return (
    <>
      <TopBar title="작물인식" />
      <LayoutMainBox>
        <LayoutInnerBox>
          {isLoading ? <div>로딩중...</div> : <AIFileUploadInput />}
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default RecognizePage;
