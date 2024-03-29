// SSETest.tsx
import React, { useEffect, useState } from 'react';
import { EventSourcePolyfill } from 'event-source-polyfill';
interface AlarmPayload {
    receiveUserId: number;
    alarmId: number;
    alarmType: string;
    alarmContent: string;
    alarmRedirect: string;
}

const SSETest: React.FC = () => {
    const [messages, setMessages] = useState<AlarmPayload[]>([]);

    const accessToken = sessionStorage.getItem("accessToken") || ""; // accessToken이 null인 경우에는 빈 문자열로 대체

    useEffect(() => {
        
        EventSourcePolyfill

        // SSE 연결 URL. 서버 설정에 따라 변경해주세요.
        const url = 'http://localhost:8080/v1/noti/pub';
        // const eventSourc`e = new EventSource(url);
        const eventSource = new EventSourcePolyfill(url, { 
            headers: { 'Authorization': `Bearer ${accessToken}` } });

        
        console.log("sse 생성확인 : ", eventSource)
        eventSource.onmessage = (event) => {
            const newMessage: AlarmPayload = JSON.parse(event.data);
            setMessages((prevMessages) => [...prevMessages, newMessage]);
        };

        eventSource.onerror = (error) => {
            console.error('EventSource failed:', error);
            eventSource.close();
        };

        // 컴포넌트가 언마운트될 때 SSE 연결 종료
        return () => {
            eventSource.close();
        };
    }, []);

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