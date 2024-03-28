import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import GardenMap from "../../components/garden/GardenMap";

const GardenPage = () => {
  return (
    <>
      <TopBar title="작물일지" showBack={false} />
      <GardenMap />
      <LayoutMainBox>
        <LayoutInnerBox></LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default GardenPage;
