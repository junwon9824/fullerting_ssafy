import axios from "axios";

const baseURL = import.meta.env.VITE_SERVER_URL;
const api = axios.create({
  baseURL,
});

export const fetchBadges = () => {
    console.log("`fetchBadges` 함수 호출됨");
  const accessToken = sessionStorage.getItem('accessToken');

  if (!accessToken) {
      console.log("토큰없음")
    return Promise.reject(new Error('Access token is not available.')); 
  }

  return api.get(`/badges`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  }).then(response => {
   console.log("정보 받아짐")
   return response.data.data_body;
}).catch(error => {
    
    console.log("정보 왜 안받아짐?")
    console.error('Error fetching badges:', error);
    throw error; 
  });
};
