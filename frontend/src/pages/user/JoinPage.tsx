import { useState } from "react";
import StyledInputWithButton from "../../components/common/Input/StyledInputWithButton";
import useInput from "../../hooks/useInput";
import styled from "styled-components";
import StyledInput from "../../components/common/Input/StyledInput";
import StyledTextArea from "../../components/common/Input/StyledTextArea";
import { TitleBar } from "../../components/common/Navigator/navigater";
import { BottomButton } from "../../components/common/Button/LargeButton";

const MainBox = styled.main`
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 100vh;
  width: 100vw;
  padding-top: 2.75rem;
`;

const JoinBox = styled.div`
  display: flex;
  width: 19.875rem;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 1.56rem;
  padding: 1.12rem 0;
`;

const JoinPage = () => {
  const [name, setName] = useInput("");
  const [email, setEmail] = useInput("");
  const [authCode, setAuthCode] = useInput("");
  const [password, setPassword] = useInput("");
  const [verifyPassword, setVerifyPassword] = useInput("");
  const [isEmailVerify, setIsEmailVerify] = useState<boolean>(false);
  const [selectedDate, setSelectedDate] = useState<string>(
    new Date().toISOString().slice(0, 10)
  );
  const [content, setContent] = useInput("");

  const handleAuthCodeSend = async () => {};

  const handleDateChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    setSelectedDate(event.target.value);
  };

  const handleConfirmClick = () => {};

  return (
    <>
      <TitleBar title="회원가입" />
      <MainBox>
        <JoinBox>
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
          <StyledInput
            label="날짜"
            type="date"
            id="date"
            name="date"
            placeholder=""
            value={selectedDate}
            onChange={handleDateChange}
          />
          <StyledTextArea
            label="내용"
            name="content"
            placeholder="내용을 입력해주세요."
            value={content}
            onChange={setContent}
            maxLength={300}
          />
        </JoinBox>
      </MainBox>
      <BottomButton onClick={handleConfirmClick} children="확인" />
    </>
  );
};

export default JoinPage;
