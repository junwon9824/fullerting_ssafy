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

export const getCropData = async (packDiaryId: string) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.get(`/pack-diaries/${packDiaryId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (error) {
    console.error("Error getCropData:", error);
    throw error;
  }
};

export const getDiaryList = async (packDiaryId: string) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.get(`/diaries/${packDiaryId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (error) {
    console.error("Error getDiaryList:", error);
    throw error;
  }
};

export const getCropType = async () => {
  const accessToken = sessionStorage.getItem("accessToken");

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

export const getTipList = async (cropTypeId: number) => {
  const accessToken = sessionStorage.getItem("accessToken");

  try {
    const response = await api.get(`/crop-tips/${cropTypeId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (error) {
    console.error("Error getCropType:", error);
    throw error;
  }
};

export const createCrop = async (cropData: CropFormType) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");

    const response = await api.post("/pack-diaries", cropData, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (error) {
    console.error("Error createCrop: ", error);
    throw error;
  }
};

export const updateHarvest = async (packDiaryId: string) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.patch(`/pack-diaries/${packDiaryId}/end`, null, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data;
  } catch (error) {
    console.error("Error userLogin: ", error);
    throw error;
  }
};
