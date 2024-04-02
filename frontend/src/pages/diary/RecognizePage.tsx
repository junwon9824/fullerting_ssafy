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
import Loading from "../../components/common/Loading";

const RecognizePage = () => {
  const navigate = useNavigate();
  const { packDiaryId } = useParams();
  const [selectedFiles, setSelectedFiles] = useAtom(fileAtom);
  const [isLoading, setIsLoading] = useState(false);

  const { mutate: aiMutate } = useMutation({
    mutationFn: calculateStep,
    onSuccess: (res) => {
      setIsLoading(false);

      if (!packDiaryId) return;

      alert(`${res.crop_type} ${res.grade}단계 입니다.`);

      stepMutate({
        packDiaryId: packDiaryId,
        cropTypeName: res.crop_type,
        cropStepGrowth: res.grade,
        confidenceScore: res.confidence_score,
      });
      navigate(`/crop/${packDiaryId}`);
      setSelectedFiles([]);
    },
    onError: (error) => {
      setIsLoading(false);
      setSelectedFiles([]);
      console.log(error);
      alert("[Error]작물 인식에 실패ㅜㅜ");
    },
  });

  const { mutate: stepMutate } = useMutation({
    mutationFn: changeStep,
    onSuccess: (res) => {
      if (res.cropRenewal === true) {
        if (res.myBadgeResponse) {
          alert(
            `${res.cropTypeName} ${res.cropStepGrowth}단계!!\n[${res.myBadgeResponse.badgeName}] 뱃지를 획득하였습니다!`
          );
        } else {
          alert(
            `${res.cropTypeName} ${res.cropStepGrowth}단계로 업그레이드 완료!!!!`
          );
        }
      } else {
        alert(
          `해당 작물이 아니거나 단계 및 정확도가 낮습니다. 다시 촬영해 주세요.`
        );
      }
    },
    onError: (e) => {
      console.log(e);
      alert("[Error]작물 단계 변경에 실패ㅜㅜ");
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
          {isLoading ? <Loading /> : <AIFileUploadInput />}
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default RecognizePage;
