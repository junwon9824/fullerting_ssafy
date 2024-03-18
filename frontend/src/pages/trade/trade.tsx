import CheckModal from "../../components/Trade/finishModal";
import MenuBar from "../../components/Trade/menuBar";
import MenuBarButton from "../../components/Trade/menuBarButton";

const handelMenuBarClick = () => {};
const TradePage = () => {
  return (
    <>
      <CheckModal CheckText="거래를 종료하시겠습니까?"></CheckModal>
      <MenuBar />
    </>
  );
};

export default TradePage;
