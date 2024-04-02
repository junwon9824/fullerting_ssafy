import { useEffect, useState } from "react";
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
import { createWater } from "../../apis/DiaryApi";
import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";

const DiaryWaterPage = () => {
  const [crop] = useAtom(cropAtom);
  // const { packDiaryId } = useParams();
  const navigate = useNavigate();
  const [selectedDate, setSelectedDate] = useState<string>(
    new Date().toISOString().slice(0, 10)
  );

  useEffect(() => {
    if (!crop) {
      alert("작물일기를 먼저 선택해주세요!");
      navigate("/crop");
    }
  }, []);

  const { mutate } = useMutation({
    mutationFn: createWater,
    onSuccess: () => {
      if (crop) navigate(`/crop/${crop.packDiaryId}`);
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedDate(event.target.value);
  };

  const handleConfirmClick = () => {
    if (!crop || !selectedDate) return;

    const waterData = {
      packDiaryId: crop.packDiaryId.toString(),
      diarySelectedAt: selectedDate,
    };

    mutate(waterData);
  };

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
            min={crop.packDiaryCulStartAt}
            max={new Date().toISOString().slice(0, 10)}
          />
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default DiaryWaterPage;
