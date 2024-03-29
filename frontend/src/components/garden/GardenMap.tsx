import {
  Map,
  MarkerClusterer,
  useKakaoLoader,
  useMap,
} from "react-kakao-maps-sdk";
// import useKakaoLoader from "../../hooks/useKakaoLoader";
import { useEffect, useMemo, useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { getGardenList } from "../../apis/GardenApi";
import GardenMarker from "./GardenMarker";

const ReSettingMapBounds = ({ farmList }: { farmList: FarmType[] }) => {
  const map = useMap();
  const bounds = useMemo(() => {
    const bounds = new kakao.maps.LatLngBounds();

    farmList.forEach((farm: FarmType) => {
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
  // useKakaoLoader();

  const [loading, error] = useKakaoLoader({
    appkey: import.meta.env.VITE_KAKAO_MAP_KEY,
    libraries: ["clusterer", "drawing", "services"],
  });

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
      level={25}
    >
      <MarkerClusterer averageCenter={true} minLevel={10}>
        {farmList &&
          farmList.map((farm: FarmType) => (
            <GardenMarker key={farm.farmId} farm={farm} />
          ))}
      </MarkerClusterer>
      {farmList && <ReSettingMapBounds farmList={farmList} />}
    </Map>
  );
};

export default GardenMap;
