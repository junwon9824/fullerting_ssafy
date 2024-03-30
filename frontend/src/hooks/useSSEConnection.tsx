import { useEffect, useState } from "react";
import { EventSourcePolyfill } from "event-source-polyfill";
import { useAtom } from "jotai";
import { notificationAtom } from "../stores/notification";

export const useSSEConnection = () => {
  const [notification, setNotification] = useAtom(notificationAtom);
  const [messages, setMessages] = useState([]);

  useEffect(() => {
    const accessToken = sessionStorage.getItem("accessToken") || "";
    const url = "https://j10c102.p.ssafy.io/api/v1/noti/pub";

    const eventSource = new EventSourcePolyfill(url, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });

    eventSource.onmessage = (event) => {
      if (event.data !== "heartbeat") {
        try {
          const newMessage = JSON.parse(event.data);
          setNotification({
            show: true,
            name: newMessage.alarmType,
            type: newMessage.alarmRedirect,
            content: newMessage.alarmContent,
          });
          console.log("데이터정보", notification);
        } catch (error) {
          console.error("메시지 파싱 오류:", error);
        }
      }
    };

    eventSource.onerror = (error) => {
      console.error("EventSource failed:", error);
      eventSource.close();
    };

    return () => {
      eventSource.close();
    };
  }, []);

  useEffect(() => {}, [notification]);

  return { messages, notification };
};
