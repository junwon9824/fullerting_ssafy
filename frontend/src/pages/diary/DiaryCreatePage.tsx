import { useAtom } from "jotai";
import StyledInput from "../../components/common/Input/StyledInput";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { TopBar } from "../../components/common/Navigator/navigator";
import CropProfile from "../../components/diary/CropProfile";
import { cropAtom, fileAtom } from "../../stores/diary";
import { useEffect, useState } from "react";
import { BottomButton } from "../../components/common/Button/LargeButton";
import StyledTextArea from "../../components/common/Input/StyledTextArea";
import useInput from "../../hooks/useInput";
import FileUploadInput from "../../components/common/Input/FileUploadInput";
import { createDiary } from "../../apis/DiaryApi";
import { useNavigate } from "react-router-dom";
import { useMutation } from "@tanstack/react-query";
import Loading from "../../components/common/Loading";

const DiaryCreatePage = () => {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  const [crop] = useAtom(cropAtom);
  const [selectedFiles, setSelectedFiles] = useAtom(fileAtom);
  const [selectedDate, setSelectedDate] = useState<string>(
    new Date().toISOString().slice(0, 10)
  );
  const [title, onTitle] = useInput("");
  const [content, onContent] = useInput("");

  useEffect(() => {
    if (!crop) {
      alert("작물을 먼저 선택해주세요!");
      navigate("/crop");
    }
  }, []);

  const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedDate(event.target.value);
  };

  const { mutate } = useMutation({
    mutationFn: createDiary,
    onSuccess: () => {
      if (crop) {
        navigate(`/crop/${crop.packDiaryId}`);
      }
      setIsLoading(false);
      setSelectedFiles([]);
    },
    onError: (error) => {
      alert("다이어리 작성해 실패하였습니다.");
      console.log(error);
      setIsLoading(false);
    },
  });

  const handleConfirmClick = () => {
    if (crop) {
      setIsLoading(true);

      const diaryData = {
        packDiaryId: crop.packDiaryId.toString(),
        diarySelectedAt: selectedDate,
        images: selectedFiles,
        diaryTitle: title,
        diaryContent: content,
      };

      mutate(diaryData);
    }
  };

  return (
    <>
      <TopBar title="작물일기" />
      <LayoutMainBox>
        <LayoutInnerBox>
          {isLoading ? (
            <Loading />
          ) : (
            <>
              {crop && <CropProfile crop={crop} />}
              {crop && (
                <StyledInput
                  label="날짜 선택하기"
                  type="date"
                  id="date"
                  name="date"
                  placeholder=""
                  value={selectedDate}
                  onChange={handleDateChange}
                  min={crop.packDiaryCulStartAt}
                  max={new Date().toISOString().slice(0, 10)}
                />
              )}
              <StyledInput
                label="제목"
                type="title"
                id="title"
                name="title"
                placeholder="제목을 입력해주세요"
                onChange={onTitle}
              />
              <StyledTextArea
                label="내용"
                name="content"
                placeholder="내용을 입력해주세요."
                value={content}
                onChange={onContent}
                maxLength={300}
              />
              <FileUploadInput />
            </>
          )}
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default DiaryCreatePage;
