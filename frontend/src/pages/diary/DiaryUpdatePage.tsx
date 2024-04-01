import { useAtom } from "jotai";
import StyledInput from "../../components/common/Input/StyledInput";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { TopBar } from "../../components/common/Navigator/navigator";
import CropProfile from "../../components/diary/CropProfile";
import { cropAtom, diaryAtom, fileAtom } from "../../stores/diary";
import { useEffect, useState } from "react";
import { BottomButton } from "../../components/common/Button/LargeButton";
import StyledTextArea from "../../components/common/Input/StyledTextArea";
import useInput from "../../hooks/useInput";
import FileUploadInput from "../../components/common/Input/FileUploadInput";
import { getDiaryData, updateDiary } from "../../apis/DiaryApi";
import { useNavigate } from "react-router-dom";
import { useMutation, useQuery } from "@tanstack/react-query";
import styled from "styled-components";
import Loading from "../../components/common/Loading";

const PreviewImage = styled.img`
  width: 4.2rem;
  height: 4.2rem;
  border-radius: 1rem;
  background: ${({ theme }) => theme.colors.white};
`;

const DeleteImageButton = styled.div`
  position: absolute;
  top: -0.5rem;
  right: -0.5rem;
  width: 1.5rem;
  height: 1.5rem;
  background-color: rgba(59, 59, 59, 0.804);
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  cursor: pointer;

  svg {
    width: 0.7rem;
    height: 0.7rem;
    fill: #ffffff;
  }
`;

const RegisterBox = styled.div`
  display: flex;
  gap: 0.8rem;
  flex-wrap: wrap;
`;

const DiaryUpdatePage = () => {
  const navigate = useNavigate();
  const [isLoading, setIsLoading] = useState(false);
  const [diaryId] = useAtom(diaryAtom);
  const [crop] = useAtom(cropAtom);
  const [selectedFiles, setSelectedFiles] = useAtom(fileAtom);
  const [selectedDate, setSelectedDate] = useState<string>(
    new Date().toISOString().slice(0, 10)
  );
  const [images, setImages] = useState<ImageType[]>([]);
  const [title, onTitle, setTitle] = useInput("");
  const [content, onContent, setContent] = useInput("");

  useEffect(() => {
    if (!crop) {
      alert("작물일기를 먼저 선택해주세요!");
      navigate("/crop");
    }
  }, []);

  const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedDate(event.target.value);
  };

  const { mutate } = useMutation({
    mutationFn: updateDiary,
    onSuccess: () => {
      navigate(`/diary/${diaryId}`);
      setIsLoading(false);
      setSelectedFiles([]);
    },
    onError: (error) => {
      console.log(error);
      setIsLoading(false);
    },
  });

  const { data: diaryData, isSuccess: isDiaryDataSuccess } = useQuery({
    queryKey: ["diaryData"],
    queryFn: diaryId ? () => getDiaryData(diaryId) : undefined,
  });

  useEffect(() => {
    if (isDiaryDataSuccess && diaryData) {
      setSelectedDate(diaryData.diarySelectedAt);
      setTitle(diaryData.diaryTitle);
      setContent(diaryData.diaryContent);
      setImages(diaryData.imageResponseList);
    }
  }, [isDiaryDataSuccess, diaryData]);

  const handleConfirmClick = () => {
    setIsLoading(true);
    mutate({
      diaryId: diaryId,
      diarySelectedAt: selectedDate,
      diaryTitle: title,
      diaryContent: content,
      images: selectedFiles,
      originImages: images.map((img) => img.id),
    });
  };

  const handleDeleteImage = (id: number) => {
    setImages(images.filter((img) => img.id !== id));
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
              <StyledInput
                label="날짜 선택하기"
                type="date"
                id="date"
                name="date"
                placeholder=""
                value={selectedDate}
                onChange={handleDateChange}
                max={new Date().toISOString().slice(0, 10)}
              />
              <StyledInput
                label="제목"
                type="title"
                id="title"
                name="title"
                placeholder="제목을 입력해주세요"
                onChange={onTitle}
                value={title}
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
              <RegisterBox>
                {images.map((image) => (
                  <div key={image.id} style={{ position: "relative" }}>
                    <PreviewImage
                      src={image.imgStoreUrl}
                      alt={`Preview ${image.id}`}
                    />
                    <DeleteImageButton
                      onClick={() => handleDeleteImage(image.id)}
                    >
                      <svg
                        xmlns="http://www.w3.org/2000/svg"
                        width="24"
                        height="24"
                        viewBox="0 0 24 24"
                        fill="none"
                      >
                        <path
                          d="M3 3L21 21M21 3L3 21"
                          stroke="white"
                          strokeWidth="5"
                          strokeLinecap="round"
                          strokeLinejoin="round"
                        />
                      </svg>
                    </DeleteImageButton>
                  </div>
                ))}
              </RegisterBox>
            </>
          )}
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default DiaryUpdatePage;
