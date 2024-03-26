import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const AuthCallbackPage = () => {
  const navigate = useNavigate();

  useEffect(() => {
    // 쿠키에서 accessToken과 refreshToken을 추출하는 함수
    console.log("페이지 들어옴");
    const getCookieValue = (name) => {
      const value = `; ${document.cookie}`;
      const parts = value.split(`; ${name}=`);
      if (parts.length === 2) return parts.pop().split(";").shift();
      return null;
    };

    try {
      // 쿠키에서 토큰을 가져옵니다.
      console.log("쿠키에 접근");
      const accessToken = getCookieValue("accessToken");
      const refreshToken = getCookieValue("refreshToken");

      // 가져온 토큰을 세션 스토리지에 저장합니다.
      if (accessToken && refreshToken) {
        sessionStorage.setItem("accessToken", accessToken);
        sessionStorage.setItem("refreshToken", refreshToken);
        console.log("세션에 저장");

        // 성공적으로 토큰을 저장한 후, 메인 페이지로 리다이렉트합니다.
        navigate("/");
      } else {
        // 토큰이 존재하지 않는 경우 로그인 페이지로 리다이렉트합니다.
        navigate("/login");
      }
    } catch (e) {
      console.error("토큰 파싱 중 에러가 발생했습니다:", e);
      // 에러가 발생한 경우 로그인 페이지로 리다이렉트합니다.
      navigate("/login");
    }
  }, [navigate]);

  return <div>Loading...</div>;
};

export default AuthCallbackPage;
