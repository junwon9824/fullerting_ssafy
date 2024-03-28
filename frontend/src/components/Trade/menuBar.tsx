import styled from "styled-components";
import MenuBarButton from "./menuBarButton";
import { useState } from "react";
import { useAtom } from "jotai";
import { selectedCategory } from "../../stores/trade";

const MenuContainer = styled.div`
  width: 100%;
  height: auto;
  /* padding-left: 1.31rem; */
  padding-right: 3.5rem;
  justify-content: space-between;
  display: flex;
  gap: 0.5rem;
`;

const MenuBar = () => {
  const [selectButton, setSelectButton] = useAtom(selectedCategory);
  const handleButtonClick = (index: number) => {
    setSelectButton(index);
  };
  const menuArray = ["전체", "제안", "거래", "나눔", "관심"];
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
