import { Map, MapMarker } from "react-kakao-maps-sdk";
import useKakaoLoader from "../../hooks/useKakaoLoader";
import shovelImg from "../../assets/images/shovel.png";
import { useState } from "react";

const GardenMap = () => {
  const [isOpen, setIsOpen] = useState(false);

  useKakaoLoader();

  return (
    <Map
      id="map"
      center={{
        lat: 35.2059392,
        lng: 126.81216,
      }}
      style={{
        width: "100%",
        height: "28rem",
      }}
      level={3}
    >
      <MapMarker
        position={{
          lat: 35.2059392,
          lng: 126.81216,
        }}
        image={{
          src: shovelImg,
          size: {
            width: 50,
            height: 55,
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
          <div style={{ minWidth: "150px" }}>
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
            <div style={{ padding: "0.28rem" }}>SSAFY</div>
          </div>
        )}
      </MapMarker>
    </Map>
  );
};

export default GardenMap;
