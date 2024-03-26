import { api } from "./Base";

export const getExchange = async () => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    if (!accessToken) {
      throw new Error("Access token is not available.");
    }
    const response = await api.get(`/exchanges/all`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    console.log("데이터 연결 성공", response.data.data_body);

    return response.data.data_body;
  } catch (error) {
    console.log("데이터 연결 성공");
    throw error;
  }
};
