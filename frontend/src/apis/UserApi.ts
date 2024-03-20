import { api } from "./Base";

interface LoginType {
  email: string;
  password: string;
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
