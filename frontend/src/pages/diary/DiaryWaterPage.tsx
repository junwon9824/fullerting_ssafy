import { useState } from "react";
import { BottomButton } from "../../components/common/Button/LargeButton";
import StyledInput from "../../components/common/Input/StyledInput";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { TopBar } from "../../components/common/Navigator/navigator";
import CropProfile from "../../components/diary/CropProfile";
import { useAtom } from "jotai";
import { cropAtom } from "../../stores/diary";

const DiaryWaterPage = () => {
  const [crop, setCrop] = useAtom(cropAtom);
  const [selectedDate, setSelectedDate] = useState<string>(
    new Date().toISOString().slice(0, 10)
  );

  const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedDate(event.target.value);
  };

  const handleConfirmClick = () => {};

  return (
    <>
      <TopBar title="물주기" />
      <LayoutMainBox>
        <LayoutInnerBox>
          {crop && <CropProfile crop={crop} />}
          <StyledInput
            label="날짜 선택하기"
            type="date"
            id="date"
            name="date"
            placeholder=""
            value={selectedDate}
            onChange={handleDateChange}
          />
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default DiaryWaterPage;
