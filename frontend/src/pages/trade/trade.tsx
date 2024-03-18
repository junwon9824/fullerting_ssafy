import CheckModal from "../../components/Trade/finishModal";
import MenuBarButton from "../../components/Trade/menuBarButton";

const handelMenuBarClick = () => {};
const TradePage = () => {
  return (
    <>
      <CheckModal CheckText="거래를 종료하시겠습니까?"></CheckModal>
      <MenuBarButton
        onClick={handelMenuBarClick}
        text="전체"
        backgroundColor="#A0D8B3"
      />
    </>
  );
};

export default TradePage;
