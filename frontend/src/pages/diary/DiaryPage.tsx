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
import { useAtom } from "jotai";
import { cropAtom, menuAtom } from "../../stores/diary";
import CropTips from "../../components/diary/CropTips";

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
  const [crop, setCrop] = useAtom(cropAtom);
  const [menu, setMenu] = useAtom(menuAtom);

  const diaries: DiaryType[] = [
    {
      diaryId: 1,
      packDiaryId: 1,
      diaryBehavior: "다이어리",
      diaryTitle: "토마토는 빨강색",
      diaryContent:
        "멋쟁이 토마토 울퉁불통멋진몸매에 빨간 옷을 입고 나는야 주스될거야 꿀꺽 나는야 춤을 출거야 멋쟁이 토마토 토마토~~",
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
    {
      diaryId: 3,
      packDiaryId: 1,
      diaryBehavior: "다이어리",
      diaryTitle: "케찹 만들거임",
      diaryContent: "오므라이스 감자튀김",
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
            {crop && <CropProfile crop={crop} />}
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
            {menu === "작물꿀팁" ? (
              <CropTips />
            ) : (
              <DiaryList diaries={diaries} />
            )}
          </MiddleBox>
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default DiaryPage;
