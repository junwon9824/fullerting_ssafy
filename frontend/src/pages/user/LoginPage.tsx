import styled from "styled-components";
import { Line } from "../../components/common/Line";
import LoginForm from "../../components/user/LoginForm";

const MainBox = styled.main`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  height: 100vh;
  width: 100vw;
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
  return (
    <MainBox>
      <Logo>풀러팅</Logo>
      <LoginForm />
      <Line />
      <SocialLoginBox>
        <Description>카카오로 풀러팅 간편하게 시작하기</Description>
        <SvgBox>카카오</SvgBox>
      </SocialLoginBox>
    </MainBox>
  );
};

export default LoginPage;
