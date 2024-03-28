import { Map } from "react-kakao-maps-sdk";
import useKakaoLoader from "../../hooks/useKakaoLoader";

const GardenMap = () => {
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
    />
  );
};

export default GardenMap;
