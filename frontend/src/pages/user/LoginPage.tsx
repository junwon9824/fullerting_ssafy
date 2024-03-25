import styled from "styled-components";
import { Line } from "../../components/common/Line";
import LoginForm from "../../components/user/LoginForm";
import Social from "../../assets/svg/web_light_rd_na.svg";
import { useNavigate } from "react-router-dom";
import { SocialGoogle } from "../../apis/Social";
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
  const navigate = useNavigate();

  const googleLogin = () => {
    try {
      SocialGoogle();
      navigate("/");
    } catch (error) {
      console.error("로그인 실패:", error);
    }
  };

  // const { isLoading, data } = useQuery({
  //   queryKey: ["SocialGoogle"],
  //   queryFn: SocialGoogle,
  // });

  // if (isLoading) {
  //   return <div>Loading...</div>;
  // }

  // if (data) {
  //   console.log("페이지 들어옴", data);
  //   return;
  // }
  return (
    <MainBox>
      <Logo>풀러팅</Logo>
      <LoginForm />
      <Line />
      <SocialLoginBox>
        <Description>카카오로 풀러팅 간편하게 시작하기</Description>
        <img src={Social} onClick={googleLogin} alt="" />
        <SvgBox>카카오</SvgBox>
      </SocialLoginBox>
    </MainBox>
  );
};

export default LoginPage;
