import styled from "styled-components";
import CheckModal from "../../components/Trade/finishModal";
import MenuBar from "../../components/Trade/menuBar";
// import MenuBarButton from "../../components/Trade/menuBarButton";
import Search from "../../components/common/Input/Search";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import useInput from "../../hooks/useInput";
// import Tomato from "/src/assets/images/토마토.png";
// import Post from "../../components/Trade/post";
import { useAtom } from "jotai";
import { selectedCategory } from "../../stores/trade";
import TradeMainCategory from "../../components/Trade/TradeMainCategory";
import TradeDealCategory from "../../components/Trade/TradeDealCategory";
import TradeGeneralCategory from "../../components/Trade/TradeGeneralCategory";
import TradeSharingCategory from "../../components/Trade/TradeSharingCategory";

const TradePage = () => {
  const [selectButton, setSelectButton] = useAtom(selectedCategory);
  const [search, setSearch] = useInput("");
  const renderSelectedComponent = () => {
    switch (selectButton) {
      case 0:
        return <TradeMainCategory />;
      case 1:
        return <TradeDealCategory />; // 1일 때 렌더링할 컴포넌트
      case 2:
        return <TradeGeneralCategory />;
      case 3:
        return <TradeSharingCategory />;
      case 4:
        return <TradeMainCategory />;
    }
  };
  console.log(selectButton, "선택한 번호");
  return (
    <>
      <TopBar title="작물거래" showBack={false} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <Search
            type="text"
            id="search"
            name="search"
            placeholder="내용 또는 작성자를 입력해주세요"
            onChange={setSearch}
          />
          <MenuBar />
          {renderSelectedComponent()}
          <NavBar />
        </LayoutInnerBox>
      </LayoutMainBox>
    </>
  );
};

export default TradePage;
