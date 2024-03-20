import axios from "axios";

const baseURL = import.meta.env.VITE_SERVER_URL;

const api = axios.create({
  baseURL,
});

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
