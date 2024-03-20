import Search from "../../components/common/Input/Search";
import Post from "../../components/Trade/post";
import useInput from "../../hooks/useInput";
import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
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
          <Post />
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};
export default LikedPost;
