import styled from "styled-components";
import MenuBarButton from "./menuBarButton";
import { useState } from "react";

const MenuContainer = styled.div`
  width: auto;
  height: auto;
  padding-left: 1.31rem;
  padding-right: 3.5rem;
  justify-content: space-between;
  display: flex;
  gap: 0.5rem;
`;

const MenuBar = () => {
  const [selectButton, setSelectButton] = useState<number>(1);
  const handleButtonClick = (index: number) => {
    setSelectButton(index);
  };
  const menuArray = ["전체", "거래", "제안", "나눔", "관심"];
  return (
    <MenuContainer>
      {menuArray.map((key, index) => (
        <MenuBarButton
          text={menuArray[index]}
          onClick={() => {
            handleButtonClick(index);
          }}
          backgroundColor={selectButton === index ? "#A0D8B3" : "#ffffff"}
          border={selectButton === index ? undefined : "1px solid #eee"}
          color={selectButton === index ? "#ffffff" : "#c8c8c8"}
        />
      ))}
    </MenuContainer>
  );
};

export default MenuBar;
