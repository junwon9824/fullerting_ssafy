interface CropType {
  packDiaryId: number;
  packDiaryTitle: string;
  packDiaryCulStartAt: string;
  packDiaryCulEndAt: string | null;
  packDiaryGrowthStep: number;
  packDiaryCreatedAt: string;
  cropTypeId: number;
  cropTypeName: string;
  cropTypeImgUrl: string;
  cropGrowDay: number | null;
}

interface CropTypeType {
  cropTypeId: number;
  cropTypeImgUrl: string;
  cropTypeName: string;
}

interface CropFormType {
  packDiaryId?: string;
  cropTypeId?: number;
  packDiaryTitle: string;
  packDiaryCulStartAt: string;
}

interface DiaryFormType {
  packDiaryId: string;
  diarySelectedAt: string;
  images: File[];
  diaryTitle: string;
  diaryContent: string;
}

interface ImageType {
  id: number;
  imgStoreUrl: string;
}

interface DiaryEntry {
  diaryId: number;
  diaryBehavior: "다이어리" | "물주기";
  diaryTitle: string | null;
  diaryContent: string | null;
  diaryCreatedAt: string;
  imageResponseList: ImageType[];
}

interface DiaryType {
  diarySelectedAt: string;
  getSelectedAtDiaryResponse: DiaryEntry[];
}

interface TipType {
  cropTipId: number;
  cropTipGrowthStep: number;
  cropTipContent: string;
}
