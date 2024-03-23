import axios from "axios";

// const baseURL = import.meta.env.VITE_SERVER_URL;
const baseURL = "https://j10c102.p.ssafy.io/api/v1";

export const api = axios.create({
  baseURL,
});

 