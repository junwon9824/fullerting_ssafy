import {
  LayoutMainBox,
  LayoutInnerBox,
} from "../../components/common/Layout/Box";
import MyTrade from "../../components/mypage/trade/MyTrade";
import useInput from "../../hooks/useInput";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
const TransPost = () => {
  const [search, setSearch] = useInput("");
  return (
    <>
      <TopBar title="거래 완료 게시글" />
      <LayoutMainBox>
        <LayoutInnerBox>
          {/* <Search
            type="text"
            id="search"
            name="search"
            placeholder="내용 또는 작성자를 입력해주세요"
            onChange={setSearch}
          /> */}
          <MyTrade />
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};
export default TransPost;
