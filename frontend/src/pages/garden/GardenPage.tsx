import styled from "styled-components";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import GardenMap from "../../components/garden/GardenMap";
import { regionAtom } from "../../stores/garden";
import { useAtom } from "jotai";

const RegionButton = styled.button`
  font-size: 0.9rem;
  color: ${({ theme }) => theme.colors.gray1};
  padding: 0.5rem 0.67rem;
  border-radius: 1rem;
  border: 1px solid ${({ theme }) => theme.colors.gray1};
  background-color: ${({ theme }) => theme.colors.white};
`;

const SelectedRegionButton = styled.button`
  font-size: 0.9rem;
  color: ${({ theme }) => theme.colors.white};
  padding: 0.5rem 0.67rem;
  border-radius: 1rem;
  border: 1px solid ${({ theme }) => theme.colors.primary};
  background-color: ${({ theme }) => theme.colors.primary};
`;

const regionList = {
  0: "전체",
  1: "서울",
  2: "부산",
  3: "대구",
  4: "인천",
  5: "광주",
  6: "대전",
  7: "울산",
  8: "강원도",
  9: "경기도",
  10: "경상남도",
  11: "경상북도",
  12: "전라남도",
  13: "전라북도",
  15: "충청남도",
  16: "충청북도",
  17: "세종",
};

const GardenPage = () => {
  const [selectedRegion, setSelectedRegion] = useAtom(regionAtom);

  const handleRegionClick = (regionId: string) => {
    setSelectedRegion(regionId);
  };

  return (
    <>
      <TopBar title="텃밭정보" showBack={false} />
      <GardenMap />
      <LayoutMainBox>
        <LayoutInnerBox>
          <div
            style={{
              display: "flex",
              width: "100%",
              flexWrap: "wrap",
              gap: "0.35rem",
            }}
          >
            {Object.entries(regionList).map(([key, value]) =>
              selectedRegion === key ? (
                <SelectedRegionButton
                  key={key}
                  value={key}
                  onClick={() => handleRegionClick(key)}
                >
                  {value}
                </SelectedRegionButton>
              ) : (
                <RegionButton
                  key={key}
                  value={key}
                  onClick={() => handleRegionClick(key)}
                >
                  {value}
                </RegionButton>
              )
            )}
          </div>
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default GardenPage;
