import { api } from "./Base";
interface LikeData {
  success_code: number;
  result_code: string;
  result_message: string;
}
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

export const getLike = async ({
  accessToken,
  postId,
}: {
  accessToken: string;
  postId: number;
}) => {
  try {
    const response = await api.post(`/exchanges/${postId}/like`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data;
  } catch (e) {
    console.log(1);
    throw e;
  }
};
