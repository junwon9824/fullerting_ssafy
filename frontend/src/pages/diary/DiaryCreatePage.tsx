import { useAtom } from "jotai";
import StyledInput from "../../components/common/Input/StyledInput";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { TopBar } from "../../components/common/Navigator/navigator";
import CropProfile from "../../components/diary/CropProfile";
import { cropAtom } from "../../stores/diary";
import { useState } from "react";
import { BottomButton } from "../../components/common/Button/LargeButton";
import StyledTextArea from "../../components/common/Input/StyledTextArea";
import useInput from "../../hooks/useInput";
import FileUploadInput from "../../components/common/Input/FileUploadInput";

const DiaryCreatePage = () => {
  const [crop, setCrop] = useAtom(cropAtom);
  const [selectedDate, setSelectedDate] = useState<string>(
    new Date().toISOString().slice(0, 10)
  );
  const [title, setTitle] = useInput("");
  const [content, setContent] = useInput("");

  const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedDate(event.target.value);
  };

  const handleConfirmClick = () => {};

  return (
    <>
      <TopBar title="작물일기" />
      <LayoutMainBox>
        <LayoutInnerBox>
          <CropProfile crop={crop} />
          <StyledInput
            label="날짜 선택하기"
            type="date"
            id="date"
            name="date"
            placeholder=""
            value={selectedDate}
            onChange={handleDateChange}
          />
          <StyledInput
            label="제목"
            type="title"
            id="title"
            name="title"
            placeholder="제목을 입력해주세요"
            onChange={setTitle}
          />
          <StyledTextArea
            label="내용"
            name="content"
            placeholder="내용을 입력해주세요."
            value={content}
            onChange={setContent}
            maxLength={300}
          />

          <FileUploadInput />
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default DiaryCreatePage;
