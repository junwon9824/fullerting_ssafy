import { api } from "./Base";


export const fetchBadges = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    if (!accessToken) {
      throw new Error('Access token is not available.');
    }
    const response = await api.get(`/badges`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });

    return response.data.data_body;
  } catch (error) {
    console.log(error);
    throw error;
  }
};

export const getUsersInfo = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    if (!accessToken) {
      throw new Error('Access token is not available.');
    }

    const response = await api.get(`/users/info`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });

    return response;
  } catch (error) {
    console.log(error);
    throw error;
  }
};


export const getExchanges = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    if (!accessToken) {
      throw new Error('Access token is not available.');
    }

    const response = await api.get(`/exchanges/done`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (error) {
    console.log("거래 완료 API요청 실패", error);
    throw error;
  }
};


export const logoutUser = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    if (!accessToken) {
      throw new Error('Access token is not available.');
    }

    const response = await api.post(`/auth/logout`, {}, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });

    console.log("로그아웃 성공");
    sessionStorage.removeItem('accessToken');
    return response.data;
  } catch (error) {
    console.log("로그아웃 실패", error);
    throw error;
  }
};
