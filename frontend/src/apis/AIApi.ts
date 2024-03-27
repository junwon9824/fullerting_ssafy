import axios from "axios";

export const calculateStep = async (imageData: File) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const formData = new FormData();
    formData.append("image", imageData);

    const response = await axios.post(
      "https://j10c102.p.ssafy.io/ai/v1/calc",
      formData,
      {
        headers: { Authorization: `Bearer ${accessToken}` },
      }
    );

    return response.data;
  } catch (error) {
    console.error("Error calculateStep: ", error);
    throw error;
  }
};
