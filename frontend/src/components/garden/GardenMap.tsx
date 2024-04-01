import {
  CustomOverlayMap,
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
import styled from "styled-components";
import { markerAtom, regionAtom } from "../../stores/garden";
import { useAtom } from "jotai";

const InfoBox = styled.div`
  position: relative;
  background-color: #ffffff;
  padding: 0.5rem;
  width: 18rem;
  margin-bottom: 4.8rem;
  border-radius: 0.6rem;
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
  width: 100%;
`;

const OffSite = styled.span`
  background-color: #ffca60;
  padding: 0.3rem 0.5rem;
  color: ${({ theme }) => theme.colors.white};
  border-radius: 0.7rem;
  font-weight: bold;
`;

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
  const [selectedMarker, setSelectedMarker] = useAtom(markerAtom);
  const [selectedRegion] = useAtom(regionAtom);

  // useKakaoLoader();

  const [loading, error] = useKakaoLoader({
    appkey: "563ce3be1426203d4293138c499aea59",
    libraries: ["clusterer", "drawing", "services"],
  });

  const { data: farmList, refetch: refetchFarmList } = useQuery({
    queryKey: ["farmList"],
    queryFn: () => getGardenList(selectedRegion),
  });

  useEffect(() => {
    refetchFarmList();
  }, [selectedRegion]);

  const renderOffSite = (offSite: string) => {
    const offSiteList = offSite.split(",").map((item) => item.trim());
    const filteredOffSiteList = offSiteList.filter((item) => item !== "등");

    return (
      filteredOffSiteList[0] !== "" &&
      filteredOffSiteList[0] !== "-" && (
        <div
          style={{
            display: "flex",
            flexDirection: "row",
            gap: "0.3rem",
            fontSize: "0.5rem",
            flexWrap: "wrap",
          }}
        >
          {filteredOffSiteList.map(
            (item, index) => item && <OffSite key={index}>{item}</OffSite>
          )}
        </div>
      )
    );
  };

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
        {selectedMarker && (
          <CustomOverlayMap
            position={{
              lat: selectedMarker.farmPosLat,
              lng: selectedMarker.farmPosLng,
            }}
            yAnchor={1}
          >
            <InfoBox>
              <CloseButton
                src="https://t1.daumcdn.net/localimg/localimages/07/mapjsapi/2x/bt_close.gif"
                alt="close"
                width="14"
                height="13"
                onClick={() => {
                  setSelectedMarker(null);
                }}
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
                    {selectedMarker.farmName}
                  </div>
                  <div
                    style={{
                      fontSize: "0.6rem",
                      color: "#8c8c8c",
                      fontWeight: "bold",
                    }}
                  >
                    {selectedMarker.farmType}
                  </div>
                </div>
                <div
                  style={{
                    fontSize: "0.8rem",
                    display: "flex",
                    justifyContent: "flex-start",
                    alignItems: "center",
                    gap: "0.14rem",
                  }}
                >
                  <svg
                    width="11"
                    height="12"
                    viewBox="0 0 14 18"
                    fill="none"
                    xmlns="http://www.w3.org/2000/svg"
                  >
                    <path
                      fill-rule="evenodd"
                      clip-rule="evenodd"
                      d="M6.99998 2.33329C4.23856 2.33329 1.99998 4.57187 1.99998 7.33329C1.99998 8.18985 2.30327 9.14729 2.8179 10.1398C3.32879 11.1251 4.01964 12.0909 4.72659 12.9494C5.4315 13.8053 6.13873 14.5381 6.67064 15.0573C6.79023 15.174 6.9007 15.2797 6.99998 15.3734C7.09926 15.2797 7.20973 15.174 7.32932 15.0573C7.86123 14.5381 8.56846 13.8053 9.27337 12.9494C9.98032 12.0909 10.6712 11.1251 11.1821 10.1398C11.6967 9.14729 12 8.18985 12 7.33329C12 4.57187 9.7614 2.33329 6.99998 2.33329ZM6.99998 16.5C6.45765 17.1327 6.4575 17.1325 6.45733 17.1324L6.45557 17.1309L6.45137 17.1273L6.43675 17.1146C6.4243 17.1038 6.40646 17.0882 6.38361 17.0681C6.33791 17.0278 6.27211 16.9693 6.18908 16.8939C6.02308 16.7431 5.78784 16.5247 5.50641 16.2499C4.94456 15.7015 4.19346 14.9238 3.44004 14.0089C2.68865 13.0965 1.92117 12.0311 1.33831 10.907C0.759193 9.79013 0.333313 8.56007 0.333313 7.33329C0.333313 3.65139 3.31808 0.666626 6.99998 0.666626C10.6819 0.666626 13.6666 3.65139 13.6666 7.33329C13.6666 8.56007 13.2408 9.79013 12.6616 10.907C12.0788 12.0311 11.3113 13.0965 10.5599 14.0089C9.8065 14.9238 9.0554 15.7015 8.49355 16.2499C8.21212 16.5247 7.97688 16.7431 7.81088 16.8939C7.72785 16.9693 7.66205 17.0278 7.61635 17.0681C7.5935 17.0882 7.57566 17.1038 7.56321 17.1146L7.54859 17.1273L7.54439 17.1309L7.54308 17.132C7.54291 17.1322 7.54231 17.1327 6.99998 16.5ZM6.99998 16.5L6.45733 17.1324C6.76941 17.3999 7.23023 17.4002 7.54231 17.1327L6.99998 16.5ZM6.99998 5.66663C6.0795 5.66663 5.33331 6.41282 5.33331 7.33329C5.33331 8.25377 6.0795 8.99996 6.99998 8.99996C7.92045 8.99996 8.66665 8.25377 8.66665 7.33329C8.66665 6.41282 7.92045 5.66663 6.99998 5.66663ZM3.66665 7.33329C3.66665 5.49234 5.15903 3.99996 6.99998 3.99996C8.84093 3.99996 10.3333 5.49234 10.3333 7.33329C10.3333 9.17424 8.84093 10.6666 6.99998 10.6666C5.15903 10.6666 3.66665 9.17424 3.66665 7.33329Z"
                      fill="#A0D8B3"
                    />
                    <path
                      d="M6.45733 17.1324C6.4575 17.1325 6.45765 17.1327 6.99998 16.5M6.45733 17.1324L6.99998 16.5M6.45733 17.1324C6.76941 17.3999 7.23023 17.4002 7.54231 17.1327L6.99998 16.5M6.45733 17.1324L6.45557 17.1309L6.45137 17.1273L6.43675 17.1146C6.4243 17.1038 6.40646 17.0882 6.38361 17.0681C6.33791 17.0278 6.27211 16.9693 6.18908 16.8939C6.02308 16.7431 5.78784 16.5247 5.50641 16.2499C4.94456 15.7015 4.19346 14.9238 3.44004 14.0089C2.68865 13.0965 1.92117 12.0311 1.33831 10.907C0.759193 9.79013 0.333313 8.56007 0.333313 7.33329C0.333313 3.65139 3.31808 0.666626 6.99998 0.666626C10.6819 0.666626 13.6666 3.65139 13.6666 7.33329C13.6666 8.56007 13.2408 9.79013 12.6616 10.907C12.0788 12.0311 11.3113 13.0965 10.5599 14.0089C9.8065 14.9238 9.0554 15.7015 8.49355 16.2499C8.21212 16.5247 7.97688 16.7431 7.81088 16.8939C7.72785 16.9693 7.66205 17.0278 7.61635 17.0681C7.5935 17.0882 7.57566 17.1038 7.56321 17.1146L7.54859 17.1273L7.54439 17.1309L7.54308 17.132C7.54291 17.1322 7.54231 17.1327 6.99998 16.5M1.99998 7.33329C1.99998 4.57187 4.23856 2.33329 6.99998 2.33329C9.7614 2.33329 12 4.57187 12 7.33329C12 8.18985 11.6967 9.14729 11.1821 10.1398C10.6712 11.1251 9.98032 12.0909 9.27337 12.9494C8.56846 13.8053 7.86123 14.5381 7.32932 15.0573C7.20973 15.174 7.09926 15.2797 6.99998 15.3734C6.9007 15.2797 6.79023 15.174 6.67064 15.0573C6.13873 14.5381 5.4315 13.8053 4.72659 12.9494C4.01964 12.0909 3.32879 11.1251 2.8179 10.1398C2.30327 9.14729 1.99998 8.18985 1.99998 7.33329ZM5.33331 7.33329C5.33331 6.41282 6.0795 5.66663 6.99998 5.66663C7.92045 5.66663 8.66665 6.41282 8.66665 7.33329C8.66665 8.25377 7.92045 8.99996 6.99998 8.99996C6.0795 8.99996 5.33331 8.25377 5.33331 7.33329ZM6.99998 3.99996C5.15903 3.99996 3.66665 5.49234 3.66665 7.33329C3.66665 9.17424 5.15903 10.6666 6.99998 10.6666C8.84093 10.6666 10.3333 9.17424 10.3333 7.33329C10.3333 5.49234 8.84093 3.99996 6.99998 3.99996Z"
                      stroke="#A0D8B3"
                      stroke-width="0.3"
                      stroke-miterlimit="10"
                      stroke-linecap="round"
                      stroke-linejoin="round"
                    />
                  </svg>
                  {selectedMarker.farmAddress}
                </div>
                {renderOffSite(selectedMarker.farmOffSite)}
              </InfoItemBox>
            </InfoBox>
          </CustomOverlayMap>
        )}
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
