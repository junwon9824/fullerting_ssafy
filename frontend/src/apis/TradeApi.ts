import { useMutation, useQueryClient } from "@tanstack/react-query";
import { api } from "./Base";
import { imageFilesAtom } from "../stores/trade";
import { atom } from "jotai";
import { useNavigate } from "react-router-dom";
interface DataItem {
  exArticleResponse: ExArticleResponse;
  packDiaryResponse: PackDiaryResponse | null; // JSON 예제에는 객체가 있지만, 여기서는 null일 수도 있음을 표현
  favoriteResponse: FavoriteResponse;
}
interface ImageResponse {
  id: number;
  imgStoreUrl: string;
}

interface ExArticleResponse {
  exLocation: string;
  exArticleId: number;
  exArticleTitle: string;
  exArticleType: string;
  imageResponses: ImageResponse[];
  price: number;
}

interface PackDiaryResponse {
  packDiaryId: number;
}
interface FavoriteResponse {
  islike: boolean;
  isLikeCnt: number;
}
interface AxiosError {
  message: string;
  code: string;
  config: any; // Axios 요청 구성, 더 상세한 타입을 위해 AxiosRequestConfig 사용 가능
  request?: XMLHttpRequest;
  response?: {
    // 실제 오류 응답 구조에 맞춰 작성
    data: any; // 응답 본문, 실제 데이터 타입에 맞게 수정 가능
    status: number;
    statusText: string;
    headers: any; // 헤더 정보, 실제 헤더 타입에 맞게 수정 가능
  };
  name: string;
  stack?: string;
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
export const getTradeDetail = async (accessToken: string, postId: number) => {
  try {
    const response = await api.get(`/exchanges/${postId}/detail`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    console.log('gettradedetail'+response.data.data_body.exArticleResponse.imageResponses[0].imgStoreUrl)
    return response.data.data_body;
  } catch (e) {
    console.log("에러났어요", e);
  }
};
export const getDealList = async (accessToken: string, postId: number) => {
  try {
    const response = await api.get(`/exchanges/${postId}/suggestion`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });

    return response.data.data_body;
  } catch (e) {
    console.log("에러났어요", e);
  }
};
export const getDealCategoryList = async (accessToken: string) => {
  try {
    const response = await api.get(`/exchanges/category/deal`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    console.log(response);
    return response.data.data_body;
  } catch (e) {
    console.log("경매 카테고리 조회 실패", e);
  }
};
export const getGeneralCategoryList = async (accessToken: string) => {
  try {
    const response = await api.get(`/exchanges/category/trans`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (e) {
    console.log("제안 카테고리 조회 실패", e);
  }
};
export const getSharingCategoryList = async (accessToken: string) => {
  try {
    const response = await api.get(`/exchanges/category/share`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (e) {
    console.log("나눔 카테고리 조회 실패", e);
  }
};
interface UseLikeArgs {
  queryKeys: string[]; // 무효화할 쿼리 키 목록
}
export const useLike = ({ queryKeys }: UseLikeArgs) => {
  // useMutation 훅은 여기에서 동기적으로 호출됩니다.
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (postId: number) => {
      // 여기서 accessToken을 검색하고, 요청에 포함합니다.
      const accessToken = sessionStorage.getItem("accessToken");

      return await api.post(
        `/exchanges/${postId}/convert_like`,
        {},
        {
          headers: { Authorization: `Bearer ${accessToken}` },
        }
      );
    },
    onSuccess: (res) => {
      queryKeys.forEach((key) => {
        queryClient.invalidateQueries({ queryKey: [key] });
      });
    },
    onError: (error) => {
      console.log("에러났어요", error);
    },
  });
};

export const usePost = () => {
  const queryClient = useQueryClient();
  const navigate = useNavigate();
  return useMutation({
    mutationFn: async (formData: FormData) => {
      const accessToken = sessionStorage.getItem("accessToken");

      if (!accessToken) {
        throw new Error("로그인이 필요합니다.");
      }

      for (const [key, value] of formData.entries()) {
        console.log(`Key: ${key}, Value: ${value}`);
      } // API 요청을 보냅니다. formData는 바로 사용됩니다.

      const response = await api.post("/exchanges", formData, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "multipart/form-data",
        },
      });

      return response.data;
    },
    onSuccess: (res) => {
      queryClient.invalidateQueries({ queryKey: ["tradeList"] });
      navigate("/trade");
      console.log("업로드 성공:", res);
    },
    onError: (error: AxiosError) => {
      if (error.response?.status === 400) {
        alert("알맞은 확장자의 사진을 올려주세요");
        console.log(error);
      } else {
        alert("필수 항목을 모두 입력해주세요");
      }
      // console.error("업로드 에러:", error);
      // // alert("필수 항목을 모두 입력해주세요");
    },
  });
};

export const useUpdateArticle = () => {
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async ({
      postId,
      formData,
    }: {
      postId: number;
      formData: FormData;
    }) => {
      const accessToken = sessionStorage.getItem("accessToken");
      if (!accessToken) {
        throw new Error("로그인이 필요합니다.");
      }
      console.log("저는 폼데이터입니다", formData);

      const response = await api.patch(`exchanges/${postId}/modify`, formData, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
          "Content-Type": "multipart/form-data",
        },
      });

      return response.data;
    },
    onSuccess: () => {
      // 캐시된 쿼리 데이터 갱신 등 후속 처리
      queryClient.invalidateQueries({ queryKey: ["tradeDetail"] });
      console.log("success");
    },
    onError: (error) => {
      console.error("업로드 에러:", error);
    },
  });
};

export const deletePost = async (postId: string) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.delete(`/exchanges/${postId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });

    return response.data;
  } catch (error) {
    console.error("Error delete: ", error);
    throw error;
  }
};

export const createChatRoom = () => {
  const navigate = useNavigate();
  const queryClient = useQueryClient();
  return useMutation({
    mutationFn: async (exArticleId: number) => {
      const accessToken = sessionStorage.getItem("accessToken");

      if (!accessToken) {
        throw new Error("로그인이 필요합니다.");
      }
      const response = await api.post(
        "/chat-room",
        { exArticleId },
        {
          headers: {
            Authorization: `Bearer ${accessToken}`,
          },
        }
      );
      return response.data.data_body;
    },
    onSuccess: (res) => {
      const chatRoomId = res.chatRoomId;
      console.log("방 만들기 성공", res);
      navigate(`/trade/${chatRoomId}/chat`);
    },
    onError: (res) => {
      console.log("방 만들기 실패", res);
    },
  });
};

export const getChatRecord = async (accessToken: string, postId: number) => {
  try {
    const response = await api.get(`/chat/${postId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (e) {
    console.log("채팅방 조회 실패", e);
  }
};
export const getChatRoomDetail = async (
  accessToken: string,
  chatRoomId: number
) => {
  try {
    const response = await api.get(`/chat-room/${chatRoomId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (e) {
    console.log("채팅방 상세 조회 실패", e);
  }
};

export const useDealFinish = () => {
  return useMutation({
    mutationFn: async (postId: number) => {
      const accessToken = sessionStorage.getItem("accessToken");
      const response = await api.patch(`/exchanges/${postId}/done`, {
        headers: {
          Authorization: `Bearer ${accessToken}`,
        },
      });
      return response.data;
    },
    onSuccess: (res) => {
      console.log("거래 종료", res);
    },
    onError: (error) => {
      console.log("거래 종료 실패", error);
    },
  });
};

export const getChatRoomList = async () => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.get("/chat-room", {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (e) {
    console.log("채팅방 전체 조회 실패");
  }
};
