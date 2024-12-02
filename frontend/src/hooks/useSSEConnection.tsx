import { useEffect } from "react";
import { EventSourcePolyfill } from "event-source-polyfill";
import { useAtom } from "jotai";
import { notificationAtom } from "../stores/notification";
// import "./NotificationPopup.css"; // CSS 스타일 임포트

// const NotificationPopup = ({ notification, onClose }) => {
//   if (!notification.show) return null;
//
//   return (
//       <div className="popup">
//         <div className="popup-content">
//           <h4>{notification.name}</h4>
//           <p>{notification.content}</p>
//           {notification.redirectURL && (
//               <a href={notification.redirectURL}>상세보기</a>
//           )}
//           <button onClick={onClose}>닫기</button>
//         </div>
//       </div>
//   );
// };

export const useSSEConnection = () => {
  const [notification, setNotification] = useAtom(notificationAtom);
  const wssURL = import.meta.env.VITE_REACT_APP_SSE_URL;

  useEffect(() => {
    let isMounted = true;

    const accessToken = sessionStorage.getItem("accessToken") || "";
    const url = wssURL;

    const connectSSE = () => {

      const eventSource = new EventSourcePolyfill(url, {
        headers: { Authorization: `Bearer ${accessToken}` },
        heartbeatTimeout: 20 * 60 * 1000,
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
                content: newMessage.alarmContent,
                redirectURL: newMessage.alarmRedirect,
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
        if (isMounted) {
          setTimeout(connectSSE, 1000);
        }
      };

      return () => {
        isMounted = false;
        eventSource.close();
      };
    };

    connectSSE();

    return () => {
      isMounted = false;
    };
  }, [setNotification]);

  // return { notification, setNotification, NotificationPopup }; // NotificationPopup 컴포넌트도 반환
  return { notification, setNotification};
};

// CSS 스타일
const styles = `
.popup {
  position: fixed;
  top: 20px;
  right: 20px;
  background-color: #f0c14b;
  border: 1px solid #a88734;
  padding: 10px;
  z-index: 1000;
  transition: opacity 0.5s;
}

.popup-content {
  display: flex;
  flex-direction: column;
}

.popup button {
  margin-top: 10px;
}
`;

// 스타일을 문서에 추가
const styleElement = document.createElement("style");
styleElement.innerHTML = styles;
document.head.appendChild(styleElement);
