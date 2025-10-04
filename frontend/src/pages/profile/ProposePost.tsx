import Search from "../../components/common/Input/Search";
import useInput from "../../hooks/useInput";
import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import Propose from "../../components/mypage/proposepost/Propose";

const ProposePost = () => {
  const [search, setSearch] = useInput("");
  return (
    <>
      <TopBar title="나의 제안 목록" />
      <LayoutMainBox>
        <LayoutInnerBox>
          {/* <Search
            type="text"
            id="search"
            name="search"
            placeholder="내용 또는 작성자를 입력해주세요"
            onChange={setSearch}
          /> */}
          <Propose />
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};
export default ProposePost;
