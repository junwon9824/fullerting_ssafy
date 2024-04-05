import { useMutation } from "@tanstack/react-query";
import { api } from "./Base";

export const useChange = () => {
  return useMutation({
    mutationFn: async (imageFile: File) => {
      const formData = new FormData();
      formData.append('multipartFile', imageFile);

      const accessToken = sessionStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("No access token available");
      }
      
      return await api.post('/users/profile', formData, {
        headers: { Authorization: `Bearer ${accessToken}` },
      });
    },
    
    onSuccess: (data) => {
      console.log("업로드 성공!", data);
    },
    onError: (error) => {
      console.log("에러났어요", error);
    },
  });
};




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

export const getPropose = async () => {
  try {
    const accessToken = sessionStorage.getItem('accessToken');
    if (!accessToken) {
      throw new Error('Access token is not available.');
    }

    const response = await api.get(`/exchanges/mybidarticles`, {
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



export const updateProfile = async ( newNickname:JSON ) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.patch(
      `/users`,
      newNickname,
      {
        headers: { Authorization: `Bearer ${accessToken}` },
      }
    );
    return response.data.data_body;
  } catch (error) {
    console.error("editnick error: ", error);
    throw error;
  }
};
