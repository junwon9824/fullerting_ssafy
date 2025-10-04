import { MapMarker } from "react-kakao-maps-sdk";
import shovelImg from "../../assets/images/shovel.png";
import { useAtom } from "jotai";
import { markerAtom } from "../../stores/garden";

const GardenMarker = ({ farm }: { farm: FarmType }) => {
  const [, setSelectedMarker] = useAtom(markerAtom);

  return (
    <>
      <MapMarker
        key={farm.farmId}
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
        onClick={() => {
          setSelectedMarker(farm);
        }}
      />
    </>
  );
};

export default GardenMarker;
