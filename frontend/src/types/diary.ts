interface CropType {
  packDiaryId: number;
  packDiaryTitle: string;
  packDiaryCulStartAt: string;
  packDiaryCulEndAt: string | null;
  packDiaryGrowthStep: number;
  packDiaryCreatedAt: string;
  cropTypeId?: number;
  cropTypeName: string;
  cropTypeImgUrl?: string;
}

interface CropTypeType {
  cropTypeId: number;
  cropTypeImgUrl: string;
  cropTypeName: string;
}

interface DiaryType {
  diaryId: number;
  packDiaryId: number;
  diaryBehavior: "다이어리" | "물주기";
  diaryTitle: string | null;
  diaryContent: string | null;
  diarySelectedAt: string;
  diaryCreatedAt: string;
}

interface TipType {
  cropTipId: number;
  cropTipGrowthStep: number;
  cropTipContent: string;
}
