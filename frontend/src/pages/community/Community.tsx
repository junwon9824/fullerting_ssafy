import styled from "styled-components";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import { LayoutMainBox } from "../../components/common/Layout/Box";
import useInput from "../../hooks/useInput";
import Search from "../../components/common/Input/Search";
import RadioButton from "../../components/common/Button/radioButton";
import Write from "/src/assets/images/글쓰기.png";
import { useNavigate } from "react-router-dom";
import CommunityAll from "./CommunityAll";
import { selectedTypeAtom } from "../../stores/community";
import { useAtom } from "jotai";
import { useEffect } from "react";

const LayoutInnerBox = styled.div`
  padding: 1.25rem 0rem;
`;
const Button = styled.div`
  margin-top: 1rem;
`;
const WriteBox = styled.img`
  position: fixed;
  right: 1.19rem;
  bottom: 4.75rem;
`;

const Community = () => {
  const [search, setSearch] = useInput("");
  const [selectedType, setSelectedType] = useAtom(selectedTypeAtom);
  const navigate = useNavigate();

  useEffect(() => {
    // 컴포넌트가 처음 렌더링될 때 "전체" 라디오 버튼을 선택합니다.
    setSelectedType("전체");
  }, []); // 빈 배열을 전달하여 컴포넌트가 처음 렌더링될 때만 실행되도록 설정합니다.

  const handleRadioButtonChange = (
    event: React.ChangeEvent<HTMLInputElement>
  ) => {
    setSelectedType(event.target.value);
    
  };

  const handelWriteClick = () => {
    navigate("createcommunity");
  };

  return (
    <>
      <TopBar title="커뮤니티" />
      <LayoutMainBox>
        <LayoutInnerBox>
          {/* <Search
            type="text"
            id="search"
            name="search"
            placeholder="내용 또는 작성자를 입력해주세요"
            onChange={setSearch}
          /> */}
          <Button>

            <RadioButton
              name="exampleRadioGroup"
              value="전체"
              onChange={handleRadioButtonChange}
            />

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
              value="꿀팁공유"
              onChange={handleRadioButtonChange}
            />
            
            <RadioButton
              name="exampleRadioGroup"
              value="텃밭요리"
              onChange={handleRadioButtonChange}
            />
          </Button>
          <WriteBox src={Write} onClick={handelWriteClick} />

          <CommunityAll />
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default Community;
