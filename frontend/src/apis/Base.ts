import axios from "axios";

// const baseURL = import.meta.env.VITE_SERVER_URL;
const baseURL = import.meta.env.VITE_REACT_APP_API_URL

// const baseURL = "http://localhost:5000/service/v1";
// const loginURL = "http://localhost:5000/public/v1";

// API 인스턴스 생성
export const api = axios.create({
  baseURL: import.meta.env.VITE_API_URL ,
  // baseURL: import.meta.env.VITE_API_URL || "https://j10c102.p.ssafy.io/api",
  withCredentials: true,
});


function showError(message) {
  alert(message); // 또는 toast.error(message)
}

// 로그인 API 인스턴스 생성
// export const loginapi = axios.create({
//   baseURL: loginURL, // 객체 형태로 baseURL 지정
// });

// 2. 요청 인터셉터: access token 자동 첨부
api.interceptors.request.use(
  (config) => {
    const accessToken = sessionStorage.getItem("accessToken");
    if (accessToken) {
      config.headers.Authorization = `Bearer ${accessToken}`;
    }
    return config;
  },
  (error) => {
    showError("요청을 생성하는 중 오류가 발생했습니다.");

    Promise.reject(error)
  }
);

// 3. 응답 인터셉터: 401 발생 시 refresh token으로 자동 재발급
let isRefreshing = false;
let failedQueue: any[] = [];

const processQueue = (error: any, token: string | null = null) => {
  failedQueue.forEach((prom) => {
    if (error) {
      prom.reject(error);
    } else {
      prom.resolve(token);
    }
  });
  failedQueue = [];
};

api.interceptors.response.use(
  (response) => response,
  async (error) => {
    const originalRequest = error.config;

    // access token 만료(401) & 재발급 시도 중이 아닐 때
    if (
      error.response &&
      error.response.status === 401 &&
      !originalRequest._retry
    ) {

      if (isRefreshing) {
        // 이미 재발급 중이면 큐에 추가
        return new Promise(function (resolve, reject) {
          failedQueue.push({ resolve, reject });
        })
          .then((token) => {
            originalRequest.headers.Authorization = "Bearer " + token;
            return api(originalRequest);
          })
          .catch((err) => Promise.reject(err));
      }

      originalRequest._retry = true;
      isRefreshing = true;

      const refreshToken = sessionStorage.getItem("refreshToken");
      if (!refreshToken) {
        // refresh token도 없으면 로그아웃
        window.location.href = "/user/logout";
        return Promise.reject(error);
      }

      try {
        // refresh token으로 access token 재발급 요청
        const res = await axios.post(
          `${import.meta.env.VITE_API_URL || "https://j10c102.p.ssafy.io/api"}/auth/refresh`,
          {},
          {
            headers: {
              Authorization: `Bearer ${refreshToken}`,
            },
          }
        );
        const newAccessToken = res.data.data_body.accessToken;
        const newRefreshToken = res.data.data_body.refreshToken;
        sessionStorage.setItem("accessToken", newAccessToken);
        if (newRefreshToken) {
          sessionStorage.setItem("refreshToken", newRefreshToken);
        }
        api.defaults.headers.common.Authorization = "Bearer " + newAccessToken;
        processQueue(null, newAccessToken);
        // 원래 요청 재시도
        return api(originalRequest);
      } catch (err) {
        processQueue(err, null);
        // refresh token도 만료 → 로그아웃
        window.location.href = "/user/logout";
        return Promise.reject(err);
      } finally {
        isRefreshing = false;
      }
    }
    return Promise.reject(error);
  }
);
