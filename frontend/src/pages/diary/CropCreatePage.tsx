import { useState } from "react";
import StyledInput from "../../components/common/Input/StyledInput";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { TopBar } from "../../components/common/Navigator/navigator";
import { BottomButton } from "../../components/common/Button/LargeButton";
import useInput from "../../hooks/useInput";
import styled from "styled-components";

const SelectBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.6rem;
`;

const Select = styled.select`
  border: 2px solid ${({ theme }) => theme.colors.gray1};
  border-radius: 0.5rem;
  width: 19.875rem;
  height: 3rem;
  &:focus {
    border: 2px solid ${({ theme }) => theme.colors.primary};
  }
  font-size: 0.875rem;
  font-weight: bold;
  padding: 0.75rem 1rem;
  appearance: none;
`;

const Label = styled.label`
  display: flex;
  color: ${({ theme }) => theme.colors.gray0};
  text-align: center;
  font-size: 0.875rem;
  font-weight: bold;
`;

const RedCircle = styled.div`
  width: 0.25rem;
  height: 0.25rem;
  background-color: ${({ theme }) => theme.colors.red0};
  margin: 0 0.2rem;
  border-radius: 50%;
`;

const SelectCrop = () => {
  return (
    <SelectBox>
      <Label>
        <p> 작물 선택하기</p>
        <RedCircle />
      </Label>
      <Select name="selectCrop" id="selectCrop" required>
        <option value="" disabled selected>
          작물을 선택해주세요
        </option>
        <option value="tomato">토마토</option>
        <option value="radish">무순</option>
        <option value="sprout">콩나물</option>
        <option value="potato">감자</option>
        <option value="lettuce">상추</option>
      </Select>
    </SelectBox>
  );
};

const CropCreatePage = () => {
  const [selectedDate, setSelectedDate] = useState<string>(
    new Date().toISOString().slice(0, 10)
  );
  const [cropName, setCropName] = useInput("");

  const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedDate(event.target.value);
  };

  const handleConfirmClick = () => {};

  return (
    <>
      <TopBar title="작물일지" />
      <LayoutMainBox>
        <LayoutInnerBox>
          <SelectCrop />
          <StyledInput
            label="시작일 선택하기"
            type="date"
            id="date"
            name="date"
            placeholder=""
            value={selectedDate}
            onChange={handleDateChange}
          />
          <StyledInput
            label="작물 닉네임"
            type="text"
            id="nickname"
            name="nickname"
            placeholder="닉네임을 입력해주세요"
            onChange={setCropName}
          />
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default CropCreatePage;
