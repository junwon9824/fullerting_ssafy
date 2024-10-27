import { api } from "./Base";

export const SocialGoogle = () => {

  // 환경 변수에서 클라이언트 ID와 리디렉션 URI를 가져옵니다.
  const clientId = import.meta.env.VITE_REACT_APP_GOOGLE_CLIENT_ID;

  const redirectUri = import.meta.env.VITE_REACT_APP_GOOGLE_REDIRECT_URI;
  // console.log(clientId)


  // window.location.href = `https://accounts.google.com/o/oauth2/v2/auth?client_id=${clientId}&redirect_uri=${redirectUri}&response_type=code&scope=email profile`;



  fetch('http://localhost:8080/api/v1/oauth2/google', {
    method: 'POST',
  })
    .then(response => {
      // 응답 처리
      console.log('url' + JSON.stringify(response));

    })
    .catch(error => {
      // 오류 처리
    });



  // OAuth2 인증 코드 추출
  // const urlParams = new URLSearchParams(window.location.search);
  // const code = urlParams.get('code');
  // console.log('sssssss' + window.location.search)

  // // OAuth2 코드가 존재하는 경우에만 백엔드로 전송
  // if (code) {

  //   console.log('ccccccccccccc')
  //   // 백엔드 엔드포인트로 코드 전송
  //   api.post('/oauth2/code', { code })
  //     .then(response => {
  //       // 응답 처리
  //       console.log(response.data);
  //     })
  //     .catch(error => {
  //       // 오류 처리
  //       console.error(error);
  //     });

  // }

  // window.location.href =
  //   "http://localhost:8080/oauth2/authorization/google";


};
