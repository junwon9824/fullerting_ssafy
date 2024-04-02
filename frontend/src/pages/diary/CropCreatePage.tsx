import { ChangeEvent, useState } from "react";
import StyledInput from "../../components/common/Input/StyledInput";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { TopBar } from "../../components/common/Navigator/navigator";
import { BottomButton } from "../../components/common/Button/LargeButton";
import useInput from "../../hooks/useInput";
import styled from "styled-components";
import { createCrop, getCropType } from "../../apis/DiaryApi";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { useSSEConnection } from "../../hooks/useSSEConnection";

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

const CropCreatePage = () => {
  useSSEConnection();
  const navigate = useNavigate();
  const [selectedDate, setSelectedDate] = useState<string>(
    new Date().toISOString().slice(0, 10)
  );
  const [cropTypeId, setCropTypeId] = useState<number>(0);
  const [cropName, setCropName] = useInput("");

  const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedDate(event.target.value);
  };

  const { data: types } = useQuery({
    queryKey: ["types"],
    queryFn: getCropType,
  });

  const { mutate } = useMutation({
    mutationFn: createCrop,
    onSuccess: () => {
      navigate("/crop");
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const handleCropTypeChange = (e: ChangeEvent<HTMLSelectElement>) => {
    const value = parseInt(e.currentTarget.value);
    setCropTypeId(value);
  };

  const handleConfirmClick = () => {
    if (!cropTypeId || !selectedDate || !cropName) {
      alert("모든 필수 정보를 입력해주세요");
      return;
    }

    const packDiaryData = {
      cropTypeId: cropTypeId,
      packDiaryTitle: cropName,
      packDiaryCulStartAt: selectedDate,
    };

    mutate(packDiaryData);
  };

  return (
    <>
      <TopBar title="작물일지" />
      <LayoutMainBox>
        <LayoutInnerBox>
          <SelectBox>
            <Label>
              <p> 작물 선택하기</p>
              <RedCircle />
            </Label>
            <Select
              onChange={handleCropTypeChange}
              name="selectCrop"
              id="selectCrop"
              required
            >
              <option value="" disabled selected>
                작물을 선택해주세요
              </option>
              {types &&
                types.map((type: CropTypeType) => (
                  <option key={type.cropTypeId} value={type.cropTypeId}>
                    {type.cropTypeName}
                  </option>
                ))}
            </Select>
          </SelectBox>
          <StyledInput
            label="시작일 선택하기"
            type="date"
            id="date"
            name="date"
            placeholder=""
            value={selectedDate}
            onChange={handleDateChange}
            max={new Date().toISOString().slice(0, 10)}
          />
          <StyledInput
            label="작물 닉네임"
            type="text"
            id="nickname"
            name="nickname"
            placeholder="닉네임을 입력해주세요"
            onChange={setCropName}
            maxLength={8}
          />
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default CropCreatePage;
