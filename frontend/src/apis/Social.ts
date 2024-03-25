export const SocialGoogle = () => {
  window.location.href =
    "https://j10c102.p.ssafy.io/oauth2/authorization/google";
};
// import axios from "axios";

// const baseURL = "https://j10c102.p.ssafy.io/api/oauth2/authorization/google";

// export const api = axios.create({
//   baseURL,
// });

// export const SocialGoogle = async () => {
//   try {
//     const accessToken = sessionStorage.getItem('accessToken');
//     if (!accessToken) {
//       throw new Error('Access token is not available.');
//     }

//     const response = await api.get(baseURL, {
//       headers: { Authorization: `Bearer` },
//     });
//     return response.data.data_body;
//   } catch (error) {
//     console.log("거래 완료 API요청 실패", error);
//     throw error;
//   }
// };
