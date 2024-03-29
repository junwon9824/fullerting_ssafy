import { api } from "./Base";

export const getExchange = async (accessToken: string) => {
  try {
  
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
