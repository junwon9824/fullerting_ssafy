import styled from "styled-components";

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

interface CropProfileType {
  crop: CropType;
  direction?: "row" | "column";
}

const CropProfileBox = styled.div`
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: center;
`;

const RowBox = styled.div`
  display: flex;
  width: 100%;
  align-items: center;
  justify-content: flex-start;
`;

const ColumnBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  align-items: center;
  justify-content: center;
`;

const CropImageBox = styled.div`
  width: 6.25rem;
  height: 6.25rem;
  flex-shrink: 0;
  border-radius: 50%;
  border: 2.5px solid #3d0c112c;
  margin: 0.6rem;
`;

const CropInfoBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const CropInfoBoxCenter = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  gap: 0.5rem;
`;

const CropTitle = styled.div`
  font-size: 1rem;
  font-weight: bold;
`;

const CropDescriptionBox = styled.div`
  color: ${({ theme }) => theme.colors.gray0};
  font-size: 0.75rem;
  font-weight: bold;
`;

const CropEnd = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  font-weight: bold;
  font-size: 0.75rem;
  height: 1.5625rem;
  background-color: ${({ theme }) => theme.colors.sub0};
  padding: 0 1rem;
  border-radius: 0.78125rem;
`;

const CropProfile = ({ crop, direction }: CropProfileType) => {
  const Box = direction === "column" ? ColumnBox : RowBox;
  const Align = direction === "column" ? CropInfoBoxCenter : CropInfoBox;

  const calculateDDay = (createdAt: string) => {
    const today = new Date();
    const createdDate = new Date(createdAt);
    const diffTime = Math.abs(today.getTime() - createdDate.getTime());
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return `D+${diffDays}`;
  };

  return (
    <CropProfileBox>
      <Box>
        <CropImageBox>
          <img src={crop.cropTypeImgUrl} alt="" />
        </CropImageBox>
        <Align>
          <CropTitle>
            <p>{crop.packDiaryTitle}</p>
          </CropTitle>
          <CropDescriptionBox>
            <span>
              {crop.cropType} {crop.packDiaryGrowthStage}단계
            </span>
            <span> · </span>
            <span>{calculateDDay(crop.packDiaryCreatedAt)}</span>
          </CropDescriptionBox>
          {direction !== "column" && (
            <CropEnd>"재배까지 20일 남았습니다"</CropEnd>
          )}
        </Align>
      </Box>
    </CropProfileBox>
  );
};

export default CropProfile;
