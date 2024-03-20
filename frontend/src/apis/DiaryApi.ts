import { api } from "./Base";

export const getCropList = async (accessToken: string) => {
  try {
    const response = await api.get("/pack-diaries", {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (error) {
    console.error("Error getCropList:", error);
    throw error;
  }
};
