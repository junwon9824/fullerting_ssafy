import { TopBar } from "../../components/common/Navigator/navigator";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import GeoLocation from "../../components/user/GeoLocation";
import { BottomButton } from "../../components/common/Button/LargeButton";
import { useMutation } from "@tanstack/react-query";
import { updateTown } from "../../apis/UserApi";
import { useAtom } from "jotai";
import { locationAtom } from "../../stores/user";
import { useNavigate } from "react-router-dom";

const TownCertifyPage = () => {
  const navigate = useNavigate();
  const [address, setAddress] = useAtom(locationAtom);

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
    const sigungu = `${address.region_1depth_name} ${address.region_2depth_name} ${address.region_3depth_name}`;

    mutate(sigungu);
  };

  return (
    <>
      <TopBar title="동네인증" />
      <GeoLocation />
      <LayoutMainBox>
        <LayoutInnerBox>
          {address && (
            <div
              style={{ fontWeight: "bold", fontSize: "1.25rem" }}
            >{`${address.region_1depth_name} ${address.region_2depth_name} ${address.region_3depth_name}`}</div>
          )}
          <div>내 동네가 맞나요?</div>
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default TownCertifyPage;
