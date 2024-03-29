import { Map, MapMarker, MarkerClusterer, useMap } from "react-kakao-maps-sdk";
import useKakaoLoader from "../../hooks/useKakaoLoader";
import shovelImg from "../../assets/images/shovel.png";
import { useEffect, useMemo, useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { getGardenList } from "../../apis/GardenApi";

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

  const { data: farmList } = useQuery({
    queryKey: ["farmList"],
    queryFn: getGardenList,
  });

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
        {farmList &&
          farmList.map((farm) => (
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
      {farmList && <ReSettingMapBounds farmList={farmList} />}
    </Map>
  );
};

export default GardenMap;
