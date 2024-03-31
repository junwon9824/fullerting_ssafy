import DaumPostcode from "react-daum-postcode";
import { CommonTopBar } from "../../components/common/Navigator/navigator";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { BottomButton } from "../../components/common/Button/LargeButton";
import { useState } from "react";
import { updateTown } from "../../apis/UserApi";
import { useMutation } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";

const SearchAddressPage = () => {
  const navigate = useNavigate();
  const [sigungu, setSigungu] = useState<string | null>(null);

  const { mutate } = useMutation({
    mutationFn: updateTown,
    onSuccess: () => {
      console.log("성공!");
      alert("동네인증이 완료되었습니다");
      navigate(`/trade`);
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const handleConfirmClick = () => {
    if (sigungu) mutate(sigungu);
  };

  const completeHandler = (data: any) => {
    setSigungu(`${data.sido} ${data.sido} ${data.bname}`);
  };

  return (
    <>
      <CommonTopBar title="동네인증" backNavigate="trade" />

      <LayoutMainBox>
        <DaumPostcode onComplete={completeHandler} />
        <LayoutInnerBox>
          {sigungu && (
            <>
              <div style={{ fontWeight: "bold", fontSize: "1.25rem" }}>
                {sigungu}
              </div>
              <div>내 동네가 맞나요?</div>
            </>
          )}
          <button
            style={{
              border: "1px solid #c8c8c8",
              borderRadius: "0.6rem",
              padding: "0.5rem 0.9rem",
            }}
            onClick={() => {
              navigate("/town");
            }}
          >
            현재 위치로 동네인증하기 🍀
          </button>
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default SearchAddressPage;
