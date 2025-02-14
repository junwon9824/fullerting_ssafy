import Search from "../../components/common/Input/Search";
import useInput from "../../hooks/useInput";
import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import Propose from "../../components/mypage/proposepost/Propose";
import Wrote from "../../components/mypage/wrotepost/Wrote.tsx";

const WrotePost = () => {
  const [search, setSearch] = useInput("");
  return (
    <>
      <TopBar title="내가 작성한 게시물 목록" />
      <LayoutMainBox>
        <LayoutInnerBox>
          {/* <Search
            type="text"
            id="search"
            name="search"
            placeholder="내용 또는 작성자를 입력해주세요"
            onChange={setSearch}
          /> */}
          <Wrote />
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};
export default WrotePost;
