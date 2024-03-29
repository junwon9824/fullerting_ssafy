import { api } from "./Base";

interface LoginType {
  email: string;
  password: string;
}

interface JoinType {
  email: string;
  password: string;
  nickname: string;
  authProvider: "MYAPP";
}

export const userLogin = async (loginData: LoginType) => {
  try {
    const response = await api.post("/auth/login", loginData);
    return response.data;
  } catch (error) {
    console.error("Error userLogin: ", error);
    throw error;
  }
};

export const userJoin = async (joinData: JoinType) => {
  try {
    const response = await api.post("/users/register", joinData);
    return response.data;
  } catch (error) {
    console.error("Error userLogin: ", error);
    throw error;
  }
};
export const userCheck = async (accessToken: string) => {
  try {
    const response = await api.get("users/info", {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (e) {
    console.log("user가 안불러와져요!!", e);
    throw e;
  }
};
export const userIndividualCheck = async (
  accessToken: string,
  userId: number
) => {
  try {
    const response = await api.get(`users/info/${userId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (e) {
    console.log("user가 안불러와져요!!", e);
    throw e;
  }
};

export const updateTown = async (location: string) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.patch(
      `/users/town`,
      {
        userLocation: location,
      },
      {
        headers: { Authorization: `Bearer ${accessToken}` },
      }
    );
    return response.data.data_body;
  } catch (error) {
    console.error("Error updateTown: ", error);
    throw error;
  }
};
