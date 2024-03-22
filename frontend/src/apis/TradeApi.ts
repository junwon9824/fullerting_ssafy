import { useMutation } from "@tanstack/react-query";
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

export const getLikeㅁㄴㅇㄹㅁㄴㅇㄹㅁㄴㅇㅋㄹ = async (
  accessToken: string,
  postId: number
) => {
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

export const useLike = () => {
  // useMutation 훅은 여기에서 동기적으로 호출됩니다.

  return useMutation({
    mutationFn: (postId: number) => {
      // 여기서 accessToken을 검색하고, 요청에 포함합니다.
      const accessToken = sessionStorage.getItem("accessToken");
      console.log("토큰이에요", accessToken);
      if (!accessToken) {
        // accessToken이 없는 경우, 오류를 반환하거나 다른 처리를 할 수 있습니다.
        throw new Error("No access token available");
      }
      return api.post(`/exchanges/${postId}/like`, {
        headers: { Authorization: `Bearer ${accessToken}` },
      });
    },
    onSuccess: (res) => {
      console.log(res);
    },
    onError: (error) => {
      console.log(error);
    },
  });
};
