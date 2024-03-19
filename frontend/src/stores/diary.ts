import { atom } from "jotai";

export const menuAtom = atom("다이어리");

export const cropAtom = atom<CropType>({
  packDiaryId: 1,
  cropType: "토마토",
  packDiaryTitle: "똘똘한토마토",
  packDiaryCulStartAt: "2024-03-01",
  packDiaryCulEndAt: "2024-04-01",
  packDiaryGrowthStep: "2",
  packDiaryCreatedAt: "2024-03-01",
  cropTypeImgUrl: "tomato_img.jpg",
});
