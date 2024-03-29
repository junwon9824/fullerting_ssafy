import { api } from "./Base";

export const getGardenList = async () => {
  const accessToken = sessionStorage.getItem("accessToken");

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
