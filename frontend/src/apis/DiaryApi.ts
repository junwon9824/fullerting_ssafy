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

export const createDiary = async (diaryData: DiaryFormType) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const formData = new FormData();
    formData.append("diarySelectedAt", diaryData.diarySelectedAt);
    formData.append("diaryTitle", diaryData.diaryTitle);
    formData.append("diaryContent", diaryData.diaryContent);
    diaryData.images.forEach((image) => {
      formData.append(`images`, image);
    });

    const response = await api.post(
      `/diaries/${diaryData.packDiaryId}`,
      formData,
      {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          // "Content-Type": "multipart/form-data",
        },
      }
    );
    return response.data.data_body;
  } catch (error) {
    console.error("Error createDiary: ", error);
    throw error;
  }
};

export const createWater = async (waterData: DiaryFormType) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");

    const response = await api.post(
      `/diaries/${waterData.packDiaryId}/water`,
      {
        diarySelectedAt: waterData.diarySelectedAt,
      },
      {
        headers: { Authorization: `Bearer ${accessToken}` },
      }
    );
    return response.data.data_body;
  } catch (error) {
    console.error("Error createWater: ", error);
    throw error;
  }
};

export const updateCrop = async (cropForm: CropFormType) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.patch(
      `/pack-diaries/${cropForm.packDiaryId}`,
      {
        cropTypeId: cropForm.cropTypeId,
        packDiaryTitle: cropForm.packDiaryTitle,
        packDiaryCulStartAt: cropForm.packDiaryCulStartAt,
      },
      {
        headers: { Authorization: `Bearer ${accessToken}` },
      }
    );
    return response.data.data_body;
  } catch (error) {
    console.error("Error updateCrop: ", error);
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
    console.error("Error updateHarvest: ", error);
    throw error;
  }
};

export const deleteCrop = async (packDiaryId: string) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.delete(`/pack-diaries/${packDiaryId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("Error deleteCrop: ", error);
    throw error;
  }
};

export const deleteDiary = async (diaryId: string) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.delete(`diaries/${diaryId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    console.log(response.data);
    return response.data;
  } catch (error) {
    console.error("Error deleteDiary: ", error);
    throw error;
  }
};
