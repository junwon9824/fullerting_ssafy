import {api} from "./Base";
// import {  loginapi} from "./Base";

interface LoginType {
  email: string;
  password: string;
}

interface JoinType {
  email: string;
  password: string;
  nickname: string;
  auth_provider: "MYAPP";
}
const handleApiError = (error: any, functionName: string) => {
  console.error(`Error in ${functionName}: `, error);
  throw error;
};

export const userLogin = async (loginData: LoginType) => {
  try {
    const response = await api.post("/auth/login", loginData);
    return response.data;
  } catch (error) {
    handleApiError(error, "userLogin");
  }
};

export const userJoin = async (joinData: JoinType) => {
  try {
    const response = await api.post("/users/register", joinData);
    return response.data;
  } catch (error) {
    handleApiError(error, "userJoin");
  }
};

// 나머지 API 호출도 동일한 패턴으로 개선 가능

export const userCheck = async (accessToken: string) => {
  try {
    const response = await api.get("/users/info", {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (e) {
    console.log("user가 안불러와져요!!", e);
    throw e;
  }
};
export const userIndividualCheck = async (
  accessToken: string,
  userId: number
) => {
  try {
    const response = await api.get(`users/info/${userId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });
    return response.data.data_body;
  } catch (e) {
    console.log("user가 안불러와져요!!", e);
    throw e;
  }
};

export const updateTown = async (location: string) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.patch(
      `/users/town`,
      {
        userLocation: location,
      },
      {
        headers: { Authorization: `Bearer ${accessToken}` },
      }
    );
    return response.data.data_body;
  } catch (error) {
    console.error("Error updateTown: ", error);
    throw error;
  }
};
