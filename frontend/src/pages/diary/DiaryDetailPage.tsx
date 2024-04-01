import { TopBar } from "../../components/common/Navigator/navigator";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { useNavigate, useParams } from "react-router-dom";
import { useMutation, useQuery } from "@tanstack/react-query";
import { deleteDiary, getDiaryData } from "../../apis/DiaryApi";
import styled from "styled-components";
import { BottomButton } from "../../components/common/Button/LargeButton";
import { useEffect } from "react";
import { useAtom } from "jotai";
import { diaryAtom } from "../../stores/diary";

const Title = styled.div`
  font-size: 1.4rem;
  font-weight: bold;
  width: 100%;
`;
const Content = styled.div`
  font-size: 1rem;
  line-height: 1.5rem;
  width: 100%;
`;

const Date = styled.div`
  font-size: 0.8rem;
  width: 100%;
  font-weight: bold;
  color: ${({ theme }) => theme.colors.primary};
`;

const ImageBox = styled.div`
  display: flex;
  width: 100%;
  height: 19.875rem;
  overflow: auto;
  gap: 0.5rem;

  &::-webkit-scrollbar {
    display: none;
  }
  scrollbar-width: none; /* Firefox */
  -ms-overflow-style: none; /* Internet Explorer 10+ */
`;

const ImageItem = styled.img`
  width: 100%;
  max-height: 19.875rem;
  border-radius: 0.8rem;
`;

const DiaryDetailPage = () => {
  const { diaryId } = useParams();
  const navigate = useNavigate();
  const [, setDiary] = useAtom(diaryAtom);

  useEffect(() => {
    if (diaryId) {
      setDiary(diaryId);
    } else {
      alert("다이어리를 먼저 선택해주세요");
      navigate("/crop");
    }
  }, [diaryId]);

  const formatDate = (dateString: string) => {
    const [year, month, day] = dateString.split("-");

    return `${year}년 ${month}월 ${day}일`;
  };

  const { mutate: deleteMutate } = useMutation({
    mutationFn: deleteDiary,
    onSuccess: () => {
      navigate(-1);
      //   navigate(`/crop/${packDiaryId}`);
    },
    onError: (err) => {
      console.log(err);
    },
  });

  const { data: diaryData } = useQuery({
    queryKey: ["diaryData"],
    queryFn: diaryId ? () => getDiaryData(diaryId) : undefined,
  });

  const handleDeleteDiary = () => {
    if (!diaryId) return;
    deleteMutate(diaryId);
  };

  if (!diaryData) {
    return null;
  }

  const handleConfirmClick = () => {
    navigate(`/crop/${diaryData.packDiaryId}`);
  };

  return (
    <>
      <TopBar
        title="다이어리"
        showBack={false}
        showEdit={true}
        deleteFunc={handleDeleteDiary}
      />
      <LayoutMainBox>
        <LayoutInnerBox>
          <div
            style={{
              display: "flex",
              flexDirection: "column",
              width: "100%",
              gap: "0.8rem",
            }}
          >
            <Date>
              <p>{formatDate(diaryData.diarySelectedAt)}</p>
            </Date>
            <Title>
              <p>{diaryData.diaryTitle}</p>
            </Title>
          </div>

          {diaryData.imageResponseList.length > 0 && (
            <ImageBox>
              {diaryData.imageResponseList.map((img: ImageType) => (
                <ImageItem src={img.imgStoreUrl} key={img.id} />
              ))}
            </ImageBox>
          )}
          <Content>
            <p>{diaryData.diaryContent}</p>
          </Content>
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="돌아가기" />
    </>
  );
};

export default DiaryDetailPage;
