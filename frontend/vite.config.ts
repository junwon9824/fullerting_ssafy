import { defineConfig } from 'vite';
import react from '@vitejs/plugin-react-swc';
import svgr from 'vite-plugin-svgr';
import dotenv from 'dotenv';

dotenv.config();

export default defineConfig({
  plugins: [react(), svgr()],

  define: {
    'process.env': {
      REACT_APP_DEFAULT_IMAGE_URL: JSON.stringify(process.env.REACT_APP_DEFAULT_IMAGE_URL),
    },
  },

  server: {
    proxy: {
      '/websocket': {
        target: 'wss://j10c102.p.ssafy.io/websocket/ws',
        changeOrigin: true,
        ws: true,
      },
    },
  },
});
