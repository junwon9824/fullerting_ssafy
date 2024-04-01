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
import TradeLikeCategory from "../../components/Trade/TradeLikeCategory";
import { useSSEConnection } from "../../hooks/useSSEConnection";
import { useNavigate } from "react-router-dom";

const TownBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  gap: 0.1rem;
  background-color: #ffca60;
  padding: 0.55rem 0.5rem;
  border-radius: 1.3rem;
  color: white;
  font-weight: bold;
`;
const SvgBox = styled.svg`
  height: 1.3rem;
`;

const TradePage = () => {
  const navigate = useNavigate();
  useSSEConnection();
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
        return <TradeLikeCategory />;
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
          <TownBox
            onClick={() => {
              navigate("/town");
            }}
          >
            <p>동네인증</p>
            <SvgBox
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M19 9.8V4.5H16V7.1L12 3.5L2 12.5H5V20.5H11V14.5H13V20.5H19V12.5H22L19 9.8ZM17 18.5H15V12.5H9V18.5H7V10.69L12 6.19L17 10.69V18.5Z"
                fill="white"
              />
              <path
                d="M10 10.5H14C14 9.4 13.1 8.5 12 8.5C10.9 8.5 10 9.4 10 10.5Z"
                fill="white"
              />
            </SvgBox>
          </TownBox>
          {renderSelectedComponent()}
          <NavBar />
        </LayoutInnerBox>
      </LayoutMainBox>
    </>
  );
};

export default TradePage;
