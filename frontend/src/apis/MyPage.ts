import { api } from "./Base";


export const fetchBadges = () => {
  const accessToken = sessionStorage.getItem('accessToken');

  if (!accessToken) {
    return Promise.reject(new Error('Access token is not available.')); 
  }
  
  return api.get(`/badges`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  }).then(response => {
   return response.data.data_body;
}).catch(error => {
    
    throw error; 
  });
};

export const getUsersInfo = () => {
  const accessToken = sessionStorage.getItem('accessToken');

  if (!accessToken) {
    return Promise.reject(new Error('Access token is not available.')); 
  }

  return api.get(`/users/info`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  }).then(response => {
    return response;
  }).catch(error => {
    
    throw error; 
  });
};

export const logoutUser = async () => {
  const accessToken = sessionStorage.getItem('accessToken');

  if (!accessToken) {
    return Promise.reject(new Error('Access token is not available.'));
  }

  return api.post(`/auth/logout`, {}, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  }).then(response => {
    console.log("로그아웃 성공")
    sessionStorage.removeItem('accessToken'); 
    return response.data;
  }).catch(error => {
    console.log("로그아웃 실패")
    throw error;
  });
};