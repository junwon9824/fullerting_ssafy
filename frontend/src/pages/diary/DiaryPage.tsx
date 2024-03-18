import styled from "styled-components";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import CropProfile from "../../components/diary/CropProfile";
import Button from "../../components/common/Button/primaryButton";
import RecognizeButton from "../../components/diary/RecognizeButton";

interface CropType {
  packDiaryId: number;
  cropType: string;
  packDiaryTitle: string;
  packDiaryCulStartAt: string;
  packDiaryCulEndAt: string | null;
  packDiaryGrowthStage: String | null;
  packDiaryCreatedAt: string;
  cropTypeImgUrl: string;
}

const ConButton = styled.button`
  width: 9.5rem;
  height: 2.5625rem;
  border-radius: 1.28125rem;
  background: ${({ theme }) => theme.colors.primary};
  color: ${({ theme }) => theme.colors.white};
  color: #fff;
  font-size: 1rem;
  font-weight: bold;
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
    packDiaryGrowthStage: "2",
    packDiaryCreatedAt: "2024-03-01",
    cropTypeImgUrl: "tomato_img.jpg",
  };

  const handleClick = () => {};

  return (
    <>
      <TopBar title="작물일지" showBack={false} showEdit={true} />
      <LayoutMainBox>
        <LayoutInnerBox>
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
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default DiaryPage;
