import styled from "styled-components";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import { LayoutMainBox } from "../../components/common/Layout/Box";
import useInput from "../../hooks/useInput";
import Search from "../../components/common/Input/Search";
import RadioButton from "../../components/common/Button/radioButton";

const LayoutInnerBox = styled.div`
  display: flex;
  width: 19.875rem;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1.12rem 0;
  gap: 0.5rem;
`;
const Button = styled.div`
  margin-top: 1rem;
`;
const MainPage = () => {
  const [search, setSearch] = useInput("");
  const handleRadioButtonChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    console.log(event.target.value);
  };
  return (
    <>
      <TopBar title="커뮤니티" showBack={false} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <Search
            type="text"
            id="search"
            name="search"
            placeholder="내용 또는 작성자를 입력해주세요"
            onChange={setSearch}
          />
          <Button>
            <RadioButton
              name="exampleRadioGroup"
              value="자유게시판"
              onChange={handleRadioButtonChange}
            />
            <RadioButton
              name="exampleRadioGroup"
              value="작물소개"
              onChange={handleRadioButtonChange}
            />
            <RadioButton
              name="exampleRadioGroup"
              value="텃밭요리"
              onChange={handleRadioButtonChange}
            />
            <RadioButton
              name="exampleRadioGroup"
              value="꿀팁공유"
              onChange={handleRadioButtonChange}
            />
          </Button>
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default MainPage;
