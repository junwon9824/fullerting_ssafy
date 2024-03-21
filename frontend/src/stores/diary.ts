import { atom } from "jotai";

export const menuAtom = atom("다이어리");

export const cropAtom = atom<CropType | null>({
  packDiaryId: 1,
  packDiaryTitle: "무수니",
  packDiaryCulStartAt: "2024-03-19",
  packDiaryCulEndAt: "2024-03-20",
  packDiaryGrowthStep: 0,
  packDiaryCreatedAt: "2024-03-19 20:21:01",
  cropTypeId: 1,
  cropTypeName: "무순",
  cropTypeImgUrl: "dump",
  cropGrowDay: 0,
});
