import styled from "styled-components";
import StyledInput from "../../components/common/Input/StyledInput";
import useInput from "../../hooks/useInput";
import { useNavigate } from "react-router-dom";
import { Line } from "../../components/common/Line";
import { LargeButton } from "../../components/common/Button/LargeButton";

const MainBox = styled.main`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 100vh;
  width: 100vw;
`;

const LoginBox = styled.div`
  display: flex;
  width: 19.875rem;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
const LoginInput = styled.div`
  gap: 0.9375rem;
  display: flex;
  width: 19.875rem;
  flex-direction: column;
  justify-content: center;
  align-items: center;
`;
const Logo = styled.div`
  display: flex;
  padding: 1rem 8.25rem;
  justify-content: center;
  align-items: center;
  gap: 0.625rem;
  ${({ theme }) => theme.fonts.logo}
  font-size: 1.875rem;
`;
const SignupButton = styled.button`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  flex-direction: row;
  text-align: center;
  font-size: 0.75rem;
  width: 100%;
  font-weight: bold;
`;
const Character = styled.button`
  display: flex;
  justify-content: flex-end;
  align-items: center;
  width: 100%;
  padding-top: 1.3rem;
`;

const SocialLoginBox = styled.div`
  display: flex;
  flex-direction: row;
  justify-content: space-between;
  align-items: center;
  width: 19.875rem;
`;
const Description = styled.p`
  color: ${({ theme }) => theme.colors.gray0};
  font-size: 0.75rem;
  align-items: center;
  gap: 9.5rem;
  width: 5.5rem;
  line-height: 1rem;
`;
const SvgBox = styled.div`
  width: 2.5rem;
  height: 2.5rem;
`;

const LoginPage = () => {
  const [email, setEmail] = useInput("");
  const [password, setPassword] = useInput("");

  const navigate = useNavigate();

  const handleSignupClick = () => {
    navigate("/join");
  };

  const handleConfirmClick = () => {};

  return (
    <MainBox>
      <Logo>풀러팅</Logo>
      <LoginBox>
        <Character>
          <svg
            width="104"
            height="64"
            viewBox="0 0 104 64"
            fill="none"
            xmlns="http://www.w3.org/2000/svg"
          >
            <line
              x1="85.5"
              y1="51"
              x2="85.5"
              y2="61"
              stroke="#3D0C11"
              stroke-width="3"
            />
            <line
              x1="74.5"
              y1="51"
              x2="74.5"
              y2="61"
              stroke="#3D0C11"
              stroke-width="3"
            />
            <rect
              x="57"
              y="13.5112"
              width="47"
              height="43.3778"
              rx="21.6889"
              fill="#A0D8B3"
            />
            <path
              d="M79.3131 16.1776C79.3131 13.8665 80.8501 12.0484 83.5336 11.5553C85.5442 11.1856 87.6212 10.0146 88.452 9.24423M86.7904 15.4072C85.3315 15.4113 83.9243 14.9068 82.8478 13.9939C81.7713 13.0809 81.1042 11.8262 80.9787 10.4784C80.8532 9.13073 81.2786 7.78851 82.1704 6.71799C83.0622 5.64747 84.3554 4.92684 85.7934 4.69905C90.529 3.85164 91.7752 3.45105 93.4369 1.54053C94.2677 3.08127 95.0985 4.76068 95.0985 7.70349C95.0985 11.9405 91.1272 15.4072 86.7904 15.4072Z"
              stroke="black"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <ellipse
              cx="70.5303"
              cy="31.8444"
              rx="2.84848"
              ry="2.84445"
              fill="black"
            />
            <ellipse
              cx="89.0454"
              cy="31.8444"
              rx="2.84848"
              ry="2.84445"
              fill="black"
            />
            <path
              d="M84 60H88.5C89.8807 60 91 61.1193 91 62.5C91 63.3284 90.3284 64 89.5 64H87C85.3431 64 84 62.6569 84 61V60Z"
              fill="#2A7F00"
            />
            <rect
              x="59"
              y="37.1333"
              width="9"
              height="3"
              rx="1.5"
              fill="#FFD9D9"
            />
            <rect
              x="92"
              y="37.1333"
              width="9"
              height="3"
              rx="1.5"
              fill="#FFD9D9"
            />
            <path
              d="M69 62.5C69 61.1193 70.1193 60 71.5 60H76V61C76 62.6569 74.6569 64 73 64H70.5C69.6716 64 69 63.3284 69 62.5Z"
              fill="#2A7F00"
            />
            <line
              y1="-1.5"
              x2="10"
              y2="-1.5"
              transform="matrix(6.14691e-08 1 1 -3.10837e-08 20 51)"
              stroke="#3D0C11"
              stroke-width="3"
            />
            <line
              y1="-1.5"
              x2="10"
              y2="-1.5"
              transform="matrix(6.14691e-08 1 1 -3.10837e-08 31 51)"
              stroke="#3D0C11"
              stroke-width="3"
            />
            <rect
              width="47"
              height="43.3778"
              rx="21.6889"
              transform="matrix(-1 0 0 1 47 13.5112)"
              fill="#A0D8B3"
            />
            <path
              d="M24.6869 16.1776C24.6869 13.8665 23.1499 12.0484 20.4664 11.5553C18.4558 11.1856 16.3788 10.0146 15.548 9.24423M17.2096 15.4072C18.6685 15.4113 20.0757 14.9068 21.1522 13.9939C22.2287 13.0809 22.8958 11.8262 23.0213 10.4784C23.1468 9.13073 22.7214 7.78851 21.8296 6.71799C20.9378 5.64747 19.6446 4.92684 18.2066 4.69905C13.471 3.85164 12.2248 3.45105 10.5631 1.54053C9.73233 3.08127 8.90152 4.76068 8.90152 7.70349C8.90152 11.9405 12.8728 15.4072 17.2096 15.4072Z"
              stroke="black"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
            <ellipse
              cx="2.84848"
              cy="2.84445"
              rx="2.84848"
              ry="2.84445"
              transform="matrix(-1 0 0 1 36.3182 29.8667)"
              fill="black"
            />
            <ellipse
              cx="2.84848"
              cy="2.84445"
              rx="2.84848"
              ry="2.84445"
              transform="matrix(-1 0 0 1 17.803 29.8667)"
              fill="black"
            />
            <path
              d="M20 60H15.5C14.1193 60 13 61.1193 13 62.5C13 63.3284 13.6716 64 14.5 64H17C18.6569 64 20 62.6569 20 61V60Z"
              fill="#2A7F00"
            />
            <rect
              width="9"
              height="3"
              rx="1.5"
              transform="matrix(-1 0 0 1 40 25)"
              fill="black"
            />
            <rect
              width="9"
              height="3"
              rx="1.5"
              transform="matrix(-1 0 0 1 18 25)"
              fill="black"
            />
            <path
              d="M35 62.5C35 61.1193 33.8807 60 32.5 60H28V61C28 62.6569 29.3431 64 31 64H33.5C34.3284 64 35 63.3284 35 62.5Z"
              fill="#2A7F00"
            />
          </svg>
        </Character>
        <LoginInput>
          <StyledInput
            type="email"
            id="email"
            name="email"
            placeholder="이메일"
            value={email}
            onChange={setEmail}
          />
          <StyledInput
            type="password"
            id="password"
            name="password"
            placeholder="비밀번호"
            value={password}
            onChange={setPassword}
          />
          <SignupButton onClick={handleSignupClick}>회원가입</SignupButton>
          <LargeButton onClick={handleConfirmClick} children="확인" />
        </LoginInput>
      </LoginBox>
      <Line />
      <SocialLoginBox>
        <Description>카카오로 풀러팅 간편하게 시작하기</Description>
        <SvgBox>카카오</SvgBox>
      </SocialLoginBox>
    </MainBox>
  );
};

export default LoginPage;
