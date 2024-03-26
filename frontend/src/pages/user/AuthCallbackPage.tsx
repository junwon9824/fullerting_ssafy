import { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const AuthCallbackPage = () => {
  const navigate = useNavigate();

  useEffect(() => {
    console.log("페이지 들어옴");
    const getCookieValue = (name) => {
      const value = `; ${document.cookie}`;
      const parts = value.split(`; ${name}=`);
      if (parts.length === 2) return parts.pop().split(";").shift();
      return null;
    };

    const deleteCookie = (name) => {
      document.cookie = `${name}=; expires=Thu, 01 Jan 1970 00:00:00 GMT; path=/`;
    };

    try {
      const accessToken = getCookieValue("accessToken");
      const refreshToken = getCookieValue("refreshToken");
      console.log("쿠키에 접근");

      if (accessToken && refreshToken) {
        sessionStorage.setItem("accessToken", accessToken);
        sessionStorage.setItem("refreshToken", refreshToken);
        console.log("세션에 저장");

        deleteCookie("accessToken");
        deleteCookie("refreshToken");

        navigate("/");
      } else {
        navigate("/login");
      }
    } catch (e) {
      console.error("토큰 파싱 중 에러가 발생했습니다:", e);
      navigate("/login");
    }
  }, [navigate]);

  return <div>Loading...</div>;
};

export default AuthCallbackPage;
