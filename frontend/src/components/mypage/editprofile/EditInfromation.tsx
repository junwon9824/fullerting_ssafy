import styled from "styled-components";
import StyledInput from "../../common/Input/StyledInput";
import { BottomButton } from "../../common/Button/LargeButton";
import { getUsersInfo, updateProfile } from "../../../apis/MyPage";
import { useMutation, useQuery } from "@tanstack/react-query";
import { useNavigate } from "react-router-dom";
import { useState } from "react";

const BiddingBox = styled.div`
  background-color: #eee;
  width: 100%;
  padding: 0.625rem 0.9375rem;
  border: 2px solid #d3d3d3;
  height: 3rem;
  border-radius: 0.5rem;
  align-items: center;
  display: flex;
`;

const CashText = styled.div`
  width: auto;
  font-size: 0.875rem;
  font-style: normal;
  font-weight: 400;
  color: #000000;
`;

const ProfileContent = styled.div`
  align-items: center;
  width: 100%;
  margin-top: 3rem;
`;

const Name = styled.div`
  color: var(--gray1, #8c8c8c);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.875rem;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
  letter-spacing: 0.07rem;
  margin: 0.5rem 0;
`;

const Maintop = () => {
  const navigate = useNavigate();
  const [nickname, setNickname] = useState("");

  const { data: profile, error } = useQuery({
    queryKey: ["Edit"],
    queryFn: getUsersInfo,
  });

  const { mutate: editProfile } = useMutation({
    mutationFn: updateProfile,
    onSuccess: () => {
      navigate("/mypage");
    },
    onError: (error) => {
      console.log("닉네임변경실패", error);
    },
  });

  const handleChangeNickname = (e) => {
    setNickname(e.target.value);
    console.log(nickname);
  };

  const handleSubmit = async () => {
      if(nickname==""){
        window.alert("닉네임을 입력해주세요")
      }

    editProfile({ newNickname: nickname });

  };

  if (error) {
    return <div>사용자 데이터를 가져오는데 실패했습니다: {error.message}</div>;
  }

  return (
    <>
      <ProfileContent>
        <Name>닉네임</Name>
        <StyledInput
          type="nickname"
          id="nickname"
          name="nickname"
          placeholder={profile?.data.data_body.nickname}
          onChange={handleChangeNickname}
        />
        <Name>이메일</Name>
        <BiddingBox>
          <CashText>{profile?.data.data_body.email}</CashText>
        </BiddingBox>
        <Name>비밀번호</Name>
        <BiddingBox>
          <CashText>******</CashText>
        </BiddingBox>
      </ProfileContent>
      <BottomButton onClick={handleSubmit} text="수정" />
    </>
  );
};
export default Maintop;
