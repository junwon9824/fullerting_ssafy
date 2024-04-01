import Search from "../../components/common/Input/Search";
import useInput from "../../hooks/useInput";
import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import TradeLikeCategory from "../../components/Trade/TradeLikeCategory";
const LikedPost = () => {
  const [search, setSearch] = useInput("");
  return (
    <>
      <TopBar title="관심 게시물" />
      <LayoutMainBox>
        <LayoutInnerBox>
          <Search
            type="text"
            id="search"
            name="search"
            placeholder="내용 또는 작성자를 입력해주세요"
            onChange={setSearch}
          />
          <TradeLikeCategory />
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};
export default LikedPost;
