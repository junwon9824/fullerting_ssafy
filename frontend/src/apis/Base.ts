import axios from "axios";

// const baseURL = import.meta.env.VITE_SERVER_URL;
const baseURL = import.meta.env.VITE_REACT_APP_API_URL

// const baseURL = "http://localhost:8080/v1";


export const api = axios.create({
  baseURL,
});
