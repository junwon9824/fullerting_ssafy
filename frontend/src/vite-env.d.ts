// <reference types="vite/client" />

interface ImportMetaEnv {
  readonly VITE_CUSTOM_ENV_VARIABLE: string;
  readonly VITE_SERVER_URL: string;
  readonly __WEBSOCKET_URL__: string;
  readonly VITE_KAKAO_MAP_KEY: string;
  readonly VITE_REACT_APP_API_URL: string;
  readonly VITE_REACT_APP_WSS_URL: string;
  readonly VITE_REACT_APP_SSE_URL: string;
}

interface ImportMeta {
  readonly env: ImportMetaEnv;
}
