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

const CardListBox = styled.div`
  display: flex;
  width: 19.875rem;
  align-items: center;
  align-content: center;
  gap: 1rem 1.125rem;
  flex-shrink: 0;
  flex-wrap: wrap;
`;

const CardItemBox = styled.div`
  position: relative;
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 9.375rem;
  height: 13.3125rem;
  border-radius: 0.3125rem;
  border: 3px solid ${({ theme }) => theme.colors.primary};
  background: #fff;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  gap: 0.3rem;
  margin: 0.5rem 0;
`;

const CardItemDecoBox = styled.div`
  position: absolute;
  top: -0.8rem;
  /* width: 6.625rem; */
  /* height: 1.6875rem; */
  /* flex-shrink: 0; */
  /* z-index: 1; */
`;

const CropImageBox = styled.div`
  width: 6.25rem;
  height: 6.25rem;
  flex-shrink: 0;
  border-radius: 50%;
  border: 2.5px solid #3d0c112c;
  opacity: 0.9;
  margin: 0.4rem;
`;

const CropTitle = styled.div`
  font-size: 1rem;
`;

const CropInfoBox = styled.div`
  color: ${({ theme }) => theme.colors.gray0};
  font-size: 0.6rem;
  font-weight: bold;
`;

const CropCard = () => {
  const crops: CropType[] = [
    {
      packDiaryId: 1,
      cropType: "토마토",
      packDiaryTitle: "똘똘한토마토",
      packDiaryCulStartAt: "2024-03-01",
      packDiaryCulEndAt: "2024-04-01",
      packDiaryGrowthStage: "2",
      packDiaryCreatedAt: "2024-03-01",
      cropTypeImgUrl: "wheat_img.jpg",
    },
    {
      packDiaryId: 2,
      cropType: "브로콜리",
      packDiaryTitle: "데프콘",
      packDiaryCulStartAt: "2024-02-15",
      packDiaryCulEndAt: "2024-04-15",
      packDiaryGrowthStage: "1",
      packDiaryCreatedAt: "2024-02-15",
      cropTypeImgUrl: "corn_img.jpg",
    },
  ];

  const calculateDDay = (createdAt: string) => {
    const today = new Date();
    const createdDate = new Date(createdAt);
    const diffTime = Math.abs(today.getTime() - createdDate.getTime());
    const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
    return `D+${diffDays}`;
  };

  return (
    <CardListBox>
      {crops &&
        crops.map((crop, index) => (
          <CardItemBox key={index}>
            <CardItemDecoBox>
              <svg
                xmlns="http://www.w3.org/2000/svg"
                width="106"
                height="27"
                viewBox="0 0 106 27"
                fill="none"
              >
                <circle cx="5" cy="22" r="5" fill="#A8A9AD" />
                <circle cx="53" cy="22" r="5" fill="#A8A9AD" />
                <circle cx="77" cy="22" r="5" fill="#A8A9AD" />
                <circle cx="101" cy="22" r="5" fill="#A8A9AD" />
                <circle cx="29" cy="22" r="5" fill="#A8A9AD" />
                <rect x="2" width="6" height="22" rx="2" fill="#575759" />
                <rect x="26" width="6" height="22" rx="2" fill="#575759" />
                <rect x="50" width="6" height="22" rx="2" fill="#575759" />
                <rect x="74" width="6" height="22" rx="2" fill="#575759" />
                <rect x="98" width="6" height="22" rx="2" fill="#575759" />
              </svg>
            </CardItemDecoBox>
            <CropImageBox>
              <img src={crop.cropTypeImgUrl} alt="" />
            </CropImageBox>
            <CropTitle>
              <p>{crop.packDiaryTitle}</p>
            </CropTitle>
            <CropInfoBox>
              <span>
                {crop.cropType} {crop.packDiaryGrowthStage}단계
              </span>
              <span> · </span>
              <span>{calculateDDay(crop.packDiaryCreatedAt)}</span>
            </CropInfoBox>
          </CardItemBox>
        ))}
    </CardListBox>
  );
};

export default CropCard;
