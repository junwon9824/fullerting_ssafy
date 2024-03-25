// AuthCallbackPage.tsx
import React, { useEffect } from "react";
import { useNavigate } from "react-router-dom";

const AuthCallbackPage = () => {
  console.log("authcallbackpage들어옴");
  const navigate = useNavigate();

  useEffect(() => {
    const preTag = document.querySelector("pre");
    console.log("1");
    if (preTag && preTag.textContent) {
      try {
        // JSON 파싱을 시도합니다.
        console.log("2");
        const tokenData = JSON.parse(preTag.textContent);
        console.log("3");
        // 파싱된 데이터에서 액세스 토큰과 리프레시 토큰을 추출합니다.
        if (tokenData && tokenData.data_body) {
          sessionStorage.setItem(
            "accessToken",
            tokenData.data_body.accessToken
          );
          sessionStorage.setItem(
            "refreshToken",
            tokenData.data_body.refreshToken
          );

          // 성공적으로 토큰을 저장한 후, 메인 페이지로 리다이렉트합니다.
          navigate("/");
        }
      } catch (e) {
        console.error("토큰 파싱 중 에러가 발생했습니다:", e);
        // 파싱 중 에러가 발생한 경우 로그인 페이지로 리다이렉트합니다.
        navigate("/login");
      }
    }
  }, [navigate]);

  // 사용자에게 보여줄 로딩 관련 UI를 렌더링합니다.
  return <div>Loading...</div>;
};

export default AuthCallbackPage;
