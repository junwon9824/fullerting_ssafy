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
