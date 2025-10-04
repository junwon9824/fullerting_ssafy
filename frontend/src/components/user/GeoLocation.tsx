import { useAtom } from "jotai";
import { useEffect, useState } from "react";
import { Map, MapMarker, useKakaoLoader } from "react-kakao-maps-sdk";
import { locationAtom } from "../../stores/user";
import { useNavigate } from "react-router-dom";

const GeoLocation = () => {
  const { kakao } = window;
  const navigate = useNavigate();

  const [loading, error] = useKakaoLoader({
    appkey: "563ce3be1426203d4293138c499aea59",
    libraries: ["clusterer", "drawing", "services"],
  });

  const [state, setState] = useState({
    center: {
      lat: 37.5566803113882,
      lng: 126.904501286522,
    },
    errMsg: null,
    isLoading: true,
    isPanto: true,
  });

  const [, setAddress] = useAtom(locationAtom);

  const getAddress = (lat: number, lng: number) => {
    const geocoder = new kakao.maps.services.Geocoder(); // 좌표 -> 주소로 변환해주는 객체
    const coord = new kakao.maps.LatLng(lat, lng); // 주소로 변환할 좌표 입력
    const callback = function (result: any, status: any) {
      if (status === kakao.maps.services.Status.OK) {
        setAddress(result[0].address);
        console.log(result[0].address);
      }
    };
    geocoder.coord2Address(coord.getLng(), coord.getLat(), callback);
  };

  useEffect(() => {
    if (navigator.geolocation) {
      navigator.geolocation.getCurrentPosition((position) => {
        setState((prev) => ({
          ...prev,
          center: {
            lat: position.coords.latitude,
            lng: position.coords.longitude,
          },
          isLoading: false,
        }));

        getAddress(position.coords.latitude, position.coords.longitude);
      });
    } else {
      alert("geolocation을 사용할수 없어요!");
      navigate("/");
    }
  }, []);

  return (
    <Map
      id="map"
      center={state.center}
      style={{
        width: "100%",
        height: "28rem",
      }}
      level={3}
      isPanto={state.isPanto}
    >
      {!state.isLoading && <MapMarker position={state.center}></MapMarker>}
    </Map>
  );
};

export default GeoLocation;
