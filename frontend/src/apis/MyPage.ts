import { api } from "./Base";

// export const getUsersInfo = async (accessToken: string) => {
//   try {
//     console.log("dddddddddddddddddd")
//     const response = await api.get(`/users/info`, {
//       headers: { Authorization: `Bearer ${accessToken}` },
//     });
//     return response.data.data_body;
//   } catch (error) {
//     console.error("Error Info:sdddddddddddddddddd", error);
//     throw error;
//   }
// }
export const fetchBadges = () => {
  const accessToken = sessionStorage.getItem('accessToken');

  if (!accessToken) {
    console.log("abababababab")
    return Promise.reject(new Error('Access token is not available.')); 
  }
  
  return api.get(`/badges`, {
    headers: {
      Authorization: `Bearer ${accessToken}`,
    },
  }).then(response => {
    console.log("ddfdfdfdfdfdfdfd")
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

