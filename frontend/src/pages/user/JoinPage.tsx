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

const JoinPage = () => {
  const [name, setName] = useInput("");
  const [email, setEmail] = useInput("");
  const [authCode, setAuthCode] = useInput("");
  const [password, setPassword] = useInput("");
  const [verifyPassword, setVerifyPassword] = useInput("");
  const [isEmailVerify, setIsEmailVerify] = useState<boolean>(false);

  const handleAuthCodeSend = async () => {};
  const handleConfirmClick = () => {};

  return (
    <>
      <TopBar title="회원가입" />
      <LayoutMainBox>
        <LayoutInnerBox>
          <StyledInputWithButton
            label="이메일"
            type="email"
            id="email"
            name="email"
            placeholder="이메일"
            onChange={setEmail}
            onClick={handleAuthCodeSend}
            disabled={isEmailVerify}
          />
          <StyledInputWithButton
            label="인증번호"
            isRequired={false}
            type="text"
            id="code"
            name="code"
            placeholder="인증번호"
            onChange={setEmail}
            onClick={handleAuthCodeSend}
            disabled={isEmailVerify}
          />
          <StyledInput
            label="비밀번호"
            type="password"
            id="password"
            name="password"
            placeholder="비밀번호"
            onChange={setPassword}
          />
          <StyledInput
            label="비밀번호 확인"
            type="password"
            id="password"
            name="password"
            placeholder="비밀번호 확인"
            onChange={setVerifyPassword}
          />
          <StyledInput
            label="닉네임"
            type="text"
            id="password"
            name="password"
            placeholder="닉네임"
            onChange={setName}
          />
        </LayoutInnerBox>
      </LayoutMainBox>
      <BottomButton onClick={handleConfirmClick} text="확인" />
    </>
  );
};

export default JoinPage;
