import { useMutation } from "@tanstack/react-query";
import { api } from "./Base";
import { imageFilesAtom } from "../stores/trade";
import { atom } from "jotai";
interface LikeData {
  success_code: number;
  result_code: string;
  result_message: string;
}
interface PostData {
  exArticleTitle: string;
  exArticleContent: string;
  imgFiles: File[];
  ex_article_location: string;
  exArticleType: string;
  packdiaryid: string;
  deal_cur_price: string;
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

// export const usePost = () => {
//   return useMutation({
//     mutationFn: async (postData: PostData) => {
//       const {
//         exArticleTitle,
//         exArticleContent,
//         imgFiles,
//         ex_article_location,
//         exArticleType,
//         packdiaryid,
//         deal_cur_price,
//       } = postData;

//       const accessToken = sessionStorage.getItem("accessToken");
//       if (!accessToken) {
//         throw new Error("로그인이 필요합니다.");
//       }

//       const formData = new FormData();

//       // JSON 데이터를 별도의 객체로 준비하여 formData에 추가
//       const jsonPayload = {
//         exArticleTitle,
//         exArticleContent,
//         ex_article_location,
//         exArticleType,
//         packdiaryid,
//         deal_cur_price,
//       };
//       formData.append("exArticleRegisterRequest", JSON.stringify(jsonPayload));

//       // 파일 데이터를 formData에 추가
//       imgFiles.forEach((file) => {
//         formData.append("file", file);
//       });

//       // API 요청을 보냅니다.
//       const response = await api.post("/exchanges", formData, {
//         headers: {
//           Authorization: `Bearer ${accessToken}`,
//         },
//       });

//       return response.data;
//     },
//     onSuccess: (res) => {
//       // 성공 처리 로직
//       console.log("업로드 성공:", res);
//     },
//     onError: (error) => {
//       // 에러 처리 로직
//       console.error("업로드 에러:", error);
//     },
//   });
// };
export const usePost = () => {
  return useMutation({
    mutationFn: async (formData: FormData) => {
      const accessToken = sessionStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("로그인이 필요합니다.");
      }

      // API 요청을 보냅니다. formData는 바로 사용됩니다.
      const response = await api.post("/exchanges", formData, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          // 'Content-Type': 'multipart/form-data'는 FormData를 사용할 때 자동으로 설정됩니다.
        },
      });

      return response.data;
    },
    onSuccess: (res) => {
      console.log("업로드 성공:", res);
    },
    onError: (error) => {
      console.error("업로드 에러:", error);
    },
  });
};
