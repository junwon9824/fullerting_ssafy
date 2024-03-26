import { defineConfig } from 'vite'
import react from '@vitejs/plugin-react-swc'
import svgr from 'vite-plugin-svgr';

export default defineConfig({
  plugins: [react(),svgr()],
  

  server: {
    // Proxy 설정
    proxy: {
      // 경로가 "/websocket" 로 시작하는 요청을 대상으로 proxy 설정
      '/websocket': {
        // 요청 전달 대상 서버 주소 설정
        // target: 'https://j10c102.p.ssafy.io/websocket/ws',
        target: 'wss://j10c102.p.ssafy.io/websocket/ws',
        // 요청 헤더 host 필드 값을 대상 서버의 호스트 이름으로  변경
        
        changeOrigin: true,
        ws: true,
      },
    },
  },


})
