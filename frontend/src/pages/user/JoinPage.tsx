import { useState } from "react";
import StyledInputWithButton from "../../components/common/Input/StyledInputWithButton";
import useInput from "../../hooks/useInput";
import StyledInput from "../../components/common/Input/StyledInput";
import { TopBar } from "../../components/common/Navigator/navigator";
import { BottomButton } from "../../components/common/Button/LargeButton";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { useMutation } from "@tanstack/react-query";
import { userJoin } from "../../apis/UserApi";
import { useNavigate } from "react-router-dom";

const JoinPage = () => {
  const [name, onName] = useInput("");
  const [email, onEmail] = useInput("");
  // const [authCode, setAuthCode] = useInput("");
  const [password, onPassword] = useInput("");
  const [verifyPassword, onVerifyPassword] = useInput("");
  // const [isEmailVerify, setIsEmailVerify] = useState<boolean>(false);

  const navigate = useNavigate();

  const { mutate } = useMutation({
    mutationFn: userJoin,
    onSuccess: (res) => {
      alert("회원가입이 완료되었습니다.\n로그인해주세요!");
      navigate("/login");
    },
    onError: (error) => {
      console.log(error);
    },
  });

  const handleAuthCodeSend = () => {};
  const handleAuthCodeConfirm = () => {
    // 인증번호 확인 로직 추가 필요
    // setIsEmailVerify(true);
  };
  const handleConfirmClick = () => {
    // if (isEmailVerify && password === verifyPassword) {
    if (email && name && password) {
      if (password === verifyPassword) {
        mutate({
          email: email,
          password: password,
          nickname: name,
          authProvider: "MYAPP",
        });
      } else {
        alert("비밀번호를 다시 확인해 주세요!");
      }
    } else {
      alert("모든 정보를 입력해주세요!");
    }
  };

  return (
    <>
      <TopBar title="회원가입" />
      <LayoutMainBox>
        <LayoutInnerBox>
          {/*<StyledInputWithButton
            label="이메일"
            type="email"
            id="email"
            name="email"
            placeholder="이메일"
            onChange={onEmail}
            onClick={handleAuthCodeSend}
            // disabled={isEmailVerify}
          />
          <StyledInputWithButton
            label="인증번호"
            isRequired={false}
            type="text"
            id="code"
            name="code"
            placeholder="인증번호"
            onChange={onEmail}
            onClick={handleAuthCodeConfirm}
          //  disabled={isEmailVerify}
          />*/}
          <StyledInput
            label="이메일"
            type="email"
            id="email"
            name="email"
            placeholder="이메일"
            onChange={onEmail}
          />
          <StyledInput
            label="비밀번호"
            type="password"
            id="password"
            name="password"
            placeholder="비밀번호"
            onChange={onPassword}
          />
          <StyledInput
            label="비밀번호 확인"
            type="password"
            id="password"
            name="password"
            placeholder="비밀번호 확인"
            onChange={onVerifyPassword}
          />
          <StyledInput
            label="닉네임"
            type="text"
            id="password"
            name="password"
            placeholder="닉네임"
            onChange={onName}
          />
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default JoinPage;
