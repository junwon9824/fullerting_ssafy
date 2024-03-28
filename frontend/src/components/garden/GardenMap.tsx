import { Map, MapMarker } from "react-kakao-maps-sdk";
import useKakaoLoader from "../../hooks/useKakaoLoader";
import shovelImg from "../../assets/images/shovel.png";

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
    >
      <MapMarker
        position={{
          lat: 35.2059392,
          lng: 126.81216,
        }}
        image={{
          src: shovelImg,
          size: {
            width: 64,
            height: 69,
          },
          options: {
            offset: {
              x: 27,
              y: 69,
            },
          },
        }}
      />
    </Map>
  );
};

export default GardenMap;
