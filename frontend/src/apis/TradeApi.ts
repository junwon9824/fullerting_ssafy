import { api } from "./Base";

export const getTradeList = async (accessToken: string) => {
  try {
    const response = await api.get("/exchanges/all", {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (e) {
    console.log(e);
    throw e;
  }
};
