import { api } from "./Base";

export const getallcommunities = async () => {
  const accessToken = sessionStorage.getItem("accessToken");
  try {
    const response = await api.get("/articles/all", {
      headers: { Authorization: `Bearer ${accessToken}` },
    });

    console.log(response)
    return response.data.data_body;
  } catch (error) {
    console.error("Error : ", error);
    throw error;
  }
};

