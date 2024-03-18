import styled from "styled-components";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import CropProfile from "../../components/diary/CropProfile";
import Button from "../../components/common/Button/primaryButton";
import RecognizeButton from "../../components/diary/RecognizeButton";
import DiaryList from "../../components/diary/DiaryList";
import MenuBar from "../../components/diary/MenuBar";

interface CropType {
  packDiaryId: number;
  cropType: string;
  packDiaryTitle: string;
  packDiaryCulStartAt: string;
  packDiaryCulEndAt: string | null;
  packDiaryGrowthStep: string;
  packDiaryCreatedAt: string;
  cropTypeImgUrl: string;
}

interface DiaryType {
  diaryId: number;
  packDiaryId: number;
  diaryBehavior: "다이어리" | "물주기";
  diaryTitle: string;
  diaryContent: string;
  diarySelectedAt: string;
  diaryCreatedAt: string;
}

const TopBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 0.5rem;
`;

const MiddleBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 0.5rem;
  margin-top: 0.5rem;
`;

const ButtonBox = styled.div`
  display: flex;
  width: 100%;
  justify-content: space-between;
`;

const DiaryPage = () => {
  const crop: CropType = {
    packDiaryId: 1,
    cropType: "토마토",
    packDiaryTitle: "똘똘한토마토",
    packDiaryCulStartAt: "2024-03-01",
    packDiaryCulEndAt: "2024-04-01",
    packDiaryGrowthStep: "2",
    packDiaryCreatedAt: "2024-03-01",
    cropTypeImgUrl: "tomato_img.jpg",
  };

  const diaries: DiaryType[] = [
    {
      diaryId: 1,
      packDiaryId: 1,
      diaryBehavior: "다이어리",
      diaryTitle: "토마토는 빨강색",
      diaryContent:
        "멋쟁이 토마토 울퉁불통멋진몸매에 빨간 옷을 입고 나는야 주스될거야",
      diarySelectedAt: "2024-03-05",
      diaryCreatedAt: "2024-03-05T10:00:00Z",
    },
    {
      diaryId: 2,
      packDiaryId: 1,
      diaryBehavior: "물주기",
      diaryTitle: "",
      diaryContent: "",
      diarySelectedAt: "2024-03-04",
      diaryCreatedAt: "2024-03-05T12:00:00Z",
    },
  ];

  const handleClick = () => {};

  return (
    <>
      <TopBar title="작물일기" showBack={false} showEdit={true} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <TopBox>
            <CropProfile crop={crop} />
            <ButtonBox>
              <RecognizeButton />
              <Button
                onClick={handleClick}
                width={9.5}
                height={2.5625}
                borderRadius={1.28125}
                backgroundColor="#A0D8B3"
                color="white"
                fontSize="1"
                fontWeight="bold"
                text="종료하기"
              />
            </ButtonBox>
          </TopBox>
          <MiddleBox>
            <MenuBar />
            <div>calendar</div>
            <span>2024년</span>
            <DiaryList diaries={diaries} />
          </MiddleBox>
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default DiaryPage;
