import axios from "axios";

// const baseURL = import.meta.env.VITE_SERVER_URL;
// const baseURL = import.meta.env.VITE_REACT_APP_API_URL

const baseURL = "http://localhost:5000/service/v1";
const loginURL = "http://localhost:5000/public/v1";

// API 인스턴스 생성
export const api = axios.create({
  baseURL,
});

// 로그인 API 인스턴스 생성
export const loginapi = axios.create({
  baseURL: loginURL, // 객체 형태로 baseURL 지정
});
