import styled from "styled-components";
import CropProfile from "./CropProfile";
import { useQuery } from "@tanstack/react-query";
import { getCropList } from "../../apis/DiaryApi";
import { useNavigate } from "react-router-dom";
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
  cursor: pointer;
`;

const CardItemDecoBox = styled.div`
  position: absolute;
  top: -0.8rem;
`;

const CropList = () => {
  const accessToken = sessionStorage.getItem("accessToken");
  const navigate = useNavigate();

  const { isLoading, data: cropList } = useQuery({
    queryKey: ["cropList"],
    queryFn: accessToken ? () => getCropList(accessToken) : undefined,
  });

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!cropList) {
    return <div>작물을 등록해주세요</div>;
  }

  const handleCardClick = (cropData: CropType) => {
    navigate(`/crop/${cropData.packDiaryId}`);
  };

  return (
    <CardListBox>
      {cropList.map((crop: CropType) => (
        <CardItemBox
          key={crop.packDiaryId}
          onClick={() => handleCardClick(crop)}
        >
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
          <CropProfile crop={crop} direction="column" />
        </CardItemBox>
      ))}
    </CardListBox>
  );
};

export default CropList;
