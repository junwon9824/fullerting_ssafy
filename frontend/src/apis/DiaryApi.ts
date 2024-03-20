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

export const getCropType = async (accessToken: string) => {
  try {
    const response = await api.get("/crop-types", {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (error) {
    console.error("Error getCropType:", error);
    throw error;
  }
};

export const createCrop = async (
  cropData: {
    cropTypeId: number;
    packDiaryTitle: string;
    packDiaryCulStartAt: string;
  },
  accessToken: string
) => {
  try {
    const response = await api.post("/pack-diaries", cropData, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (error) {
    console.error("Error createCrop: ", error);
    throw error;
  }
};
