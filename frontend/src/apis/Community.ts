import { api } from "./Base";

interface LoginType {
  email: string;
  password: string;
}
 

export const getallcommunities = async ( ) => {
  try {
    const response = await api.get("/articles/all");
    return response.data;
  } catch (error) {
    console.error("Error : ", error);
    throw error;
  }
};
 
