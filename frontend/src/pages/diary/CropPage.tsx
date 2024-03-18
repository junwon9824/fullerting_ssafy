import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import useInput from "../../hooks/useInput";
import Search from "../../components/common/Input/Search";
import CropList from "../../components/diary/CropList";

const CropPage = () => {
  const [search, setSearch] = useInput("");

  return (
    <>
      <TopBar title="작물일지" showBack={false} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <Search
            type="text"
            id="search"
            name="search"
            placeholder="작물명 또는 닉네임을 입력해주세요"
            onChange={setSearch}
          />
          <CropList />
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default CropPage;
