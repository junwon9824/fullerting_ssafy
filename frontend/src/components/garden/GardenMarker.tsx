import { useState } from "react";
import { MapMarker } from "react-kakao-maps-sdk";
import shovelImg from "../../assets/images/shovel.png";
import styled from "styled-components";

const InfoBox = styled.div`
  position: relative;
  background-color: #ffffff;
  padding: 0.4rem;
  width: 100%;
`;

const CloseButton = styled.img`
  position: absolute;
  top: 5px;
  right: 5px;
  cursor: pointer;
`;

const InfoItemBox = styled.div`
  padding: 0.2rem;
  display: flex;
  flex-direction: column;
  gap: 0.5rem;
`;

const OffSite = styled.span`
  background-color: #ffca60;
  padding: 0.3rem 0.5rem;
  color: ${({ theme }) => theme.colors.white};
  border-radius: 0.7rem;
  font-weight: bold;
`;

const GardenMarker = ({ farm }: { farm: FarmType }) => {
  const [isOpen, setIsOpen] = useState(false);

  const renderOffSite = (offSite: string) => {
    const offSiteList = offSite.split(",").map((item) => item.trim());
    const filteredOffSiteList = offSiteList.filter((item) => item !== "등");

    return filteredOffSiteList.map((item, index) => (
      <OffSite key={index}>{item}</OffSite>
    ));
  };

  return (
    <>
      <MapMarker
        key={`${farm.farmPosLat}-${farm.farmPosLng}`}
        position={{
          lat: farm.farmPosLat,
          lng: farm.farmPosLng,
        }}
        image={{
          src: shovelImg,
          size: {
            width: 45,
            height: 50,
          },
          options: {
            offset: {
              x: 27,
              y: 69,
            },
          },
        }}
        clickable={true} // 마커를 클릭했을 때 지도의 클릭 이벤트가 발생하지 않도록 설정합니다
        onClick={() => setIsOpen(true)}
      >
        {isOpen && (
          <InfoBox>
            <CloseButton
              src="https://t1.daumcdn.net/localimg/localimages/07/mapjsapi/2x/bt_close.gif"
              alt="close"
              width="14"
              height="13"
              onClick={() => setIsOpen(false)}
            />
            <InfoItemBox>
              <div
                style={{
                  display: "flex",
                  flexDirection: "row",
                  alignItems: "center",
                  gap: "0.3rem",
                }}
              >
                <div style={{ fontSize: "0.9rem", fontWeight: "bold" }}>
                  {farm.farmName}
                </div>
                <div
                  style={{
                    fontSize: "0.6rem",
                    color: "#8c8c8c",
                    fontWeight: "bold",
                  }}
                >
                  {farm.farmType}
                </div>
              </div>
              <div style={{ fontSize: "0.8rem" }}>주소: {farm.farmAddress}</div>
              <div
                style={{
                  display: "flex",
                  flexDirection: "row",
                  gap: "0.3rem",
                  fontSize: "0.5rem",
                  flexWrap: "wrap",
                }}
              >
                {renderOffSite(farm.farmOffSite)}
              </div>
            </InfoItemBox>
          </InfoBox>
        )}
      </MapMarker>
    </>
  );
};

export default GardenMarker;
