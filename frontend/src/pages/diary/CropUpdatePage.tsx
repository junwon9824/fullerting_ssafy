import { TopBar } from "../../components/common/Navigator/navigator";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { BottomButton } from "../../components/common/Button/LargeButton";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";
import useInput from "../../hooks/useInput";
import { getCropData, updateCrop } from "../../apis/DiaryApi";
import StyledInput from "../../components/common/Input/StyledInput";
import { useSSEConnection } from "../../hooks/useSSEConnection";

const CropUpdatePage = () => {
  useSSEConnection();
  const navigate = useNavigate();
  const { packDiaryId } = useParams();
  const [selectedDate, setSelectedDate] = useState<string>(
    new Date(Date.now() + 1000 * 60 * 60 * 9).toISOString().slice(0, 10)
  );
  const [cropTypeName, setCropTypeName] = useState<string>("");
  const [cropName, onChangeName, setCropName] = useInput("");

  const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedDate(event.target.value);
  };

  const { data: cropData, isSuccess: isCropDataSuccess } = useQuery({
    queryKey: ["cropData"],
    queryFn: packDiaryId ? () => getCropData(packDiaryId) : undefined,
  });

  useEffect(() => {
    if (isCropDataSuccess && cropData) {
      setSelectedDate(cropData.packDiaryCulStartAt);
      setCropTypeName(cropData.cropTypeName);
      setCropName(cropData.packDiaryTitle);
    }
  }, [isCropDataSuccess, cropData]);

  const { mutate } = useMutation({
    mutationFn: updateCrop,
    onSuccess: () => {
      navigate(`/crop/${packDiaryId}`);
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const handleConfirmClick = () => {
    if (!packDiaryId || !selectedDate || !cropName) {
      alert("모든 필수 정보를 입력해주세요");
      return;
    }

    const packDiaryData = {
      packDiaryId: packDiaryId,
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
          <StyledInput
            label="작물"
            type="text"
            id="type"
            name="type"
            value={cropTypeName}
            isRequired={false}
            disabled
            maxLength={8}
          ></StyledInput>
          <StyledInput
            label="시작일 선택하기"
            type="date"
            id="date"
            name="date"
            placeholder=""
            value={selectedDate}
            onChange={handleDateChange}
            max={new Date(Date.now() + 1000 * 60 * 60 * 9)
              .toISOString()
              .slice(0, 10)}
          />
          <StyledInput
            label="작물 닉네임"
            type="text"
            id="nickname"
            name="nickname"
            placeholder="닉네임을 입력해주세요"
            onChange={onChangeName}
            value={cropName}
            maxLength={8}
          />
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default CropUpdatePage;
