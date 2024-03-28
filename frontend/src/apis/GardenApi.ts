import { api } from "./Base";

export const getGardenList = async (accessToken: string) => {
  try {
    const response = await api.get("/farms", {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (error) {
    console.error("Error getGardenList:", error);
    throw error;
  }
};
