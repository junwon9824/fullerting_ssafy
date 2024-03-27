import { useMutation } from "@tanstack/react-query";
import { BottomButton } from "../../components/common/Button/LargeButton";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { TopBar } from "../../components/common/Navigator/navigator";
import { calculateStep } from "../../apis/AIApi";
import { useNavigate } from "react-router-dom";
import { useAtom } from "jotai";
import { fileAtom } from "../../stores/diary";
import AIFileUploadInput from "../../components/common/Input/AIFileUploadInput";

const RecognizePage = () => {
  const navigate = useNavigate();
  const [selectedFiles, setSelectedFiles] = useAtom(fileAtom);

  const { mutate } = useMutation({
    mutationFn: calculateStep,
    onSuccess: () => {
      navigate(-1);
      setSelectedFiles([]);
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const handleConfirmClick = () => {
    mutate(selectedFiles[0]);
  };

  return (
    <>
      <TopBar title="작물인식" />
      <LayoutMainBox>
        <LayoutInnerBox>
          <AIFileUploadInput />
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default RecognizePage;
