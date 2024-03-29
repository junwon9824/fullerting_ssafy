import React, { useEffect, useState } from 'react';
import { EventSourcePolyfill } from 'event-source-polyfill';

interface AlarmPayload {
    receiveUserId: number;
    alarmType: string;
    alarmContent: string;
    alarmRedirect: string;
}

const SSETest: React.FC = () => {
    const [messages, setMessages] = useState<AlarmPayload[]>([]);

    const accessToken = sessionStorage.getItem("accessToken") || ""; // accessToken이 null인 경우에는 빈 문자열로 대체

    useEffect(() => {
        const connectSSE = () => {
            // SSE 연결 URL. 서버 설정에 따라 변경해주세요.
            // const url = 'http://localhost:8080/v1/noti/pub';
            const url = 'https://j10c102.p.ssafy.io/api/v1/noti/pub';
            

            const eventSource = new EventSourcePolyfill(url, {
                headers: { 'Authorization': `Bearer ${accessToken}` }
            });

            console.log("sse 생성확인 : ", eventSource)
            eventSource.onmessage = (event) => {
                // 서버로부터 받은 메시지가 하트비트인지 확인
                if (event.data === "heartbeat") {
                    console.log("Heartbeat received");
                    // 하트비트 메시지인 경우 여기에서 추가 처리를 수행할 수 있습니다.
                } else {
                    // 하트비트가 아닌 경우, 기존 로직대로 메시지 처리
                    const newMessage: AlarmPayload = JSON.parse(event.data);
                    setMessages((prevMessages) => [...prevMessages, newMessage]);
                }
            };

            eventSource.onerror = (error) => {
                console.error('EventSource failed:', error);
                eventSource.close();
                
                // 재연결 로직: 오류 발생 시 일정 시간 후 재연결 시도
                setTimeout(() => {
                    console.log("SSE 재연결 시도...");
                    connectSSE();
                }, 1000); // 1초 후 재연결 시도
            };

            // 컴포넌트 언마운트 시 연결 종료
            return () => {
                eventSource.close();
            };
        };

        // 최초 연결 시도
        connectSSE();

        // 컴포넌트 언마운트 시 실행될 클린업 함수를 리턴
        return () => {
            console.log("Cleanup on unmount");
        };
    }, []); // 의존성 배열이 빈 배열이므로, 컴포넌트 마운트 시 1회 실행

    return (
        <div>
            <h2>Received Messages</h2>
            <ul>
                {messages.map((message, index) => (
                    <li key={index}>
                        {message.alarmType}: {message.alarmContent}
                    </li>
                ))}
            </ul>
        </div>
    );
};

export default SSETest;
