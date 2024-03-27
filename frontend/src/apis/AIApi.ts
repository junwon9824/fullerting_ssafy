import axios from "axios";

export const calculateStep = async (imageData: File) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const formData = new FormData();
    formData.append("image", imageData);

    for (const x of formData.entries()) {
      console.log(x);
    }

    const response = await axios.post(
      "https://j10c102.p.ssafy.io/ai/v1/calc",
      formData,
      {
        headers: { Authorization: `Bearer ${accessToken}` },
      }
    );

    console.log(response.data);

    return response.data.data_body;
  } catch (error) {
    console.error("Error calculateStep: ", error);
    throw error;
  }
};
