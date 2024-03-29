import { Map, MapMarker, MarkerClusterer, useMap } from "react-kakao-maps-sdk";
import useKakaoLoader from "../../hooks/useKakaoLoader";
import shovelImg from "../../assets/images/shovel.png";
import { useEffect, useMemo, useState } from "react";

const gardenDummy = [
  {
    farmId: 1631,
    farmType: "지자체/주말농장",
    farmName: "행복키움주말농장(소태동)",
    farmAreaLnm: "광주광역시",
    farmAreaMnm: "동구",
    farmAddress: "광주광역시 동구 소태동 94",
    farmOffSite: "관수시설, 원두막, 농기구함 등",
    farmPosLat: 35.12,
    farmPosLng: 126.944,
  },
  {
    farmId: 1632,
    farmType: "지자체/주말농장",
    farmName: "행복키움주말농장(내남동)",
    farmAreaLnm: "광주광역시",
    farmAreaMnm: "동구",
    farmAddress: "광주광역시 동구 내남동 184",
    farmOffSite: "관수시설, 휴게쉼터, 농기구함 등",
    farmPosLat: 35.0862,
    farmPosLng: 126.94,
  },
  {
    farmId: 1633,
    farmType: "지자체/주말농장",
    farmName: "행복키움주말농장(용연동)",
    farmAreaLnm: "광주광역시",
    farmAreaMnm: "동구",
    farmAddress: "광주광역시 동구 용연동 423-1,426-1",
    farmOffSite: "관수시설, 파고라, 농기구함 등",
    farmPosLat: 35.0947,
    farmPosLng: 126.963,
  },
  {
    farmId: 1634,
    farmType: "지자체/주말농장",
    farmName: "행복키움주말농장(용산동)",
    farmAreaLnm: "광주광역시",
    farmAreaMnm: "동구",
    farmAddress: "광주광역시 동구 용산동 80-1",
    farmOffSite: "관수시설, 휴게쉼터, 농기구함 등",
    farmPosLat: 35.1114,
    farmPosLng: 126.934,
  },
  {
    farmId: 1635,
    farmType: "지자체/공영도시텃밭",
    farmName: "양동 친환경공영도시텃밭",
    farmAreaLnm: "광주광역시",
    farmAreaMnm: "서구",
    farmAddress: "광주광역시 서구 양동 406",
    farmOffSite: "창고, 휴게실, 관정",
    farmPosLat: 35.1567,
    farmPosLng: 126.894,
  },
  {
    farmId: 1636,
    farmType: "지자체/공영도시텃밭",
    farmName: "풍암동 친환경공영도시텃밭",
    farmAreaLnm: "광주광역시",
    farmAreaMnm: "서구",
    farmAddress: "광주광역시 서구 풍암동 383-1",
    farmOffSite: "창고, 휴게실, 관정",
    farmPosLat: 35.1293,
    farmPosLng: 126.876,
  },
  {
    farmId: 1637,
    farmType: "민간단체",
    farmName: "건강희망텃밭",
    farmAreaLnm: "광주광역시",
    farmAreaMnm: "남구",
    farmAddress: "광주광역시 남구 양과동 957 외 2필지",
    farmOffSite: "",
    farmPosLat: 35.0874,
    farmPosLng: 126.864,
  },
  {
    farmId: 1638,
    farmType: "민간단체",
    farmName: "무명(광주 월계동)",
    farmAreaLnm: "광주광역시",
    farmAreaMnm: "북구",
    farmAddress: "광주광역시 월계동 830-2",
    farmOffSite: "수도시설",
    farmPosLat: 35.2122,
    farmPosLng: 126.842,
  },
  {
    farmId: 1639,
    farmType: "민간단체",
    farmName: "반월공동체텃밭",
    farmAreaLnm: "광주광역시",
    farmAreaMnm: "광산구",
    farmAddress: "광주광역시 광산구 월곡동 110-7, 산105-12",
    farmOffSite: "",
    farmPosLat: 35.1658,
    farmPosLng: 126.816,
  },
];

const ReSettingMapBounds = ({ farmList }) => {
  const map = useMap();
  const bounds = useMemo(() => {
    const bounds = new kakao.maps.LatLngBounds();

    farmList.forEach((farm) => {
      bounds.extend(new kakao.maps.LatLng(farm.farmPosLat, farm.farmPosLng));
    });
    return bounds;
  }, [farmList]);

  useEffect(() => {
    map.setBounds(bounds);
  }, [bounds]);
  return <></>;
};

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
      level={14}
    >
      <MarkerClusterer averageCenter={true} minLevel={10}>
        {gardenDummy &&
          gardenDummy.map((farm) => (
            <MapMarker
              key={`${farm.farmPosLat}-${farm.farmPosLng}`}
              position={{
                lat: farm.farmPosLat,
                lng: farm.farmPosLng,
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
                  <div style={{ padding: "0.28rem" }}>{farm.farmName}</div>
                </div>
              )}
            </MapMarker>
          ))}
      </MarkerClusterer>
      {gardenDummy && <ReSettingMapBounds farmList={gardenDummy} />}
    </Map>
  );
};

export default GardenMap;
