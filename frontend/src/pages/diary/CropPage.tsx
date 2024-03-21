import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import useInput from "../../hooks/useInput";
import Search from "../../components/common/Input/Search";
import CropList from "../../components/diary/CropList";
import styled from "styled-components";
import { useNavigate } from "react-router-dom";

const PlusButton = styled.button`
  position: fixed;
  border-radius: 50%;
  margin-bottom: 3.625rem;
  right: 1.25rem;
  bottom: 1.25rem;
  width: 3.125rem;
  height: 3.125rem;
  background-color: ${({ theme }) => theme.colors.primary};
`;

const CropPage = () => {
  const [search, setSearch] = useInput("");
  const navigate = useNavigate();

  const handlePlusButtonClick = () => {
    navigate("/diary/create");
  };

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
      <PlusButton onClick={handlePlusButtonClick}>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="26"
          height="26"
          viewBox="0 0 26 26"
          fill="none"
        >
          <path
            d="M5.70834 13H20.2917M13 5.70837V20.2917"
            stroke="white"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </svg>
      </PlusButton>
      <NavBar />
    </>
  );
};

export default CropPage;
