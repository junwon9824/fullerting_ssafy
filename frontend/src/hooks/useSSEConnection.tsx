import { useEffect, useState } from "react";
import { EventSourcePolyfill } from "event-source-polyfill";
import { useAtom } from "jotai";
import { notificationAtom } from "../stores/notification";

export const useSSEConnection = () => {
  const [notification, setNotification] = useAtom(notificationAtom);
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    let isMounted = true; // 컴포넌트 마운트 상태를 추적

    const accessToken = sessionStorage.getItem("accessToken") || "";
    const url = "http://localhost:8080/v1/noti/pub";

    const connectSSE = () => {
      const eventSource = new EventSourcePolyfill(url, {
        headers: { Authorization: `Bearer ${accessToken}` },
        heartbeatTimeout: 20 * 60 * 1000, // 20분
      });

      eventSource.onmessage = (event) => {
        if (event.data === "heartbeat") {
          console.log("Heartbeat received");
        } else {
          try {
            const newMessage = JSON.parse(event.data);
            if (isMounted) {
              setNotification({
                show: true,
                name: newMessage.alarmType,
                type: newMessage.alarmRedirect,
                content: newMessage.alarmContent,
              });
            }
            console.log("데이터정보", newMessage);
          } catch (error) {
            console.error("메시지 파싱 오류:", error);
          }
        }
      };

      eventSource.onerror = (error) => {
        console.error("EventSource failed:", error);
        eventSource.close();
        // 재연결 로직: 일정 시간 후 재연결 시도
        if (isMounted) {
          setTimeout(connectSSE, 1000); // 5초 후 재시도
        }
      };

      return () => {
        isMounted = false; // 컴포넌트 언마운트 시 재연결 중단
        eventSource.close();
      };
    };

    const disconnectSSE = connectSSE(); // SSE 연결 시도 및 연결 해제 함수 반환

    return () => {
      disconnectSSE(); // 컴포넌트 언마운트 시 연결 해제
    };
  }, []); // 의존성 배열이 빈 배열이므로, 컴포넌트가 마운트될 때 한 번만 실행됨

  useEffect(() => {}, [notification]); // notification 상태 업데이트 시 리렌더링을 위해 필요

  return { messages, notification };
};
