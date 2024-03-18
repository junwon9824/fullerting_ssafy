import styled from "styled-components";
import CheckModal from "../../components/Trade/finishModal";
import MenuBar from "../../components/Trade/menuBar";
import MenuBarButton from "../../components/Trade/menuBarButton";
import Search from "../../components/common/Input/Search";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import useInput from "../../hooks/useInput";
import Tomato from "/src/assets/images/토마토.png";
import Post from "../../components/Trade/post";

const ImgBox = styled.img`
  width: 8rem;
  height: 8rem;
  border-radius: 0.9375rem;
  top: 0;
  left: 0;
  z-index: -1;
`;
const ContentBox = styled.div`
  width: 100%;
  /* height: auto; */
  gap: 1.38rem;
  flex-wrap: wrap;
  overflow-y: auto;
  display: flex;
  justify-content: space-between;
`;
const TradePage = () => {
  const [search, setSearch] = useInput("");
  return (
    <>
      <TopBar title="작물거래" />
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

          <Post />

          <NavBar />
        </LayoutInnerBox>
      </LayoutMainBox>
    </>
  );
};

export default TradePage;
