import { useState } from "react";
import { MapMarker } from "react-kakao-maps-sdk";
import shovelImg from "../../assets/images/shovel.png";

const GardenMarker = ({ farm }: { farm: FarmType }) => {
  const [isOpen, setIsOpen] = useState(false);

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
          <div style={{ minWidth: "15rem" }}>
            <img
              alt="close"
              width="14"
              height="13"
              src="https://t1.daumcdn.net/localimg/localimages/07/mapjsapi/2x/bt_close.gif"
              style={{
                position: "absolute",
                right: "5px",
                top: "5px",
                cursor: "pointer",
              }}
              onClick={() => setIsOpen(false)}
            />
            <div>{farm.farmName}</div>
            <div>{farm.farmType}</div>
            <div>{farm.farmAddress}</div>
            <div>{farm.farmOffSite}</div>
          </div>
        )}
      </MapMarker>
    </>
  );
};

export default GardenMarker;
