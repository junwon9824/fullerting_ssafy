import React, { useEffect, useState } from "react";
import styled from "styled-components";
import useInput from "../../hooks/useInput";
import { Line } from "../../components/common/Line";
import SockJS from "sockjs-client";
import Stomp from "stompjs";
import { LayoutMainBox } from "../../components/common/Layout/Box";

const TestPage = () => {
    const [email, setEmail] = useInput("");
    const [password, setPassword] = useInput("");
    const [receivedMessages, setReceivedMessages] = useState<string[]>([]);

    const handleClick = () => {
        // 거래 종료 이벤트를 발행하는 함수
        const dealEndData = {
            // 이벤트에 필요한 데이터를 객체로 전달
            // 예: 거래 ID, 종료 시간 등
        };
        publishDealEndEvent();
    };

    const handleConfirmClick = () => { };

    useEffect(() => {
        const socket = new SockJS("https://j10c102.p.ssafy.io/api/ws"); // 또는 다른 HTTPS URL

        const stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log("Connected: " + frame);
            const subscription = stompClient.subscribe("/sub/chattings/1", function (message) {
                console.log("Received message: " + message.body);
                // 이곳에서 메시지를 처리할 수 있습니다.
                setReceivedMessages(prevMessages => [...prevMessages, message.body]);
            });

            return () => {
                // 컴포넌트가 언마운트될 때 구독 해제
                subscription.unsubscribe();
                // 연결 해제
                stompClient.disconnect(() => {
                    console.log('Disconnected from the server');
                });
            };
            
        });
    }, []); // 빈 배열을 전달하여 컴포넌트가 처음 마운트될 때만 실행되도록 함

    // 거래 종료 이벤트를 발행하는 함수
    const publishDealEndEvent = () => {
        const socket = new SockJS("/ws"); // 웹소켓 엔드포인트에 연결
        const stompClient = Stomp.over(socket);

        stompClient.connect({}, function (frame) {
            console.log("Connected: " + frame);
            //   stompClient.send("/app/deal-end", {}, JSON.stringify(dealEndData));
            // 연결 해제
            stompClient.disconnect(() => {
                console.log('Disconnected from the server');
            });
        });
    }; 

    return (
        <LayoutMainBox>
            <div>socket</div>
            <button onClick={handleClick}>거래 종료</button>
            <Line />
            <div>
                {receivedMessages.map((message, index) => (
                    <div key={index}>{message}</div>
                ))}
            </div>
        </LayoutMainBox>
    );
};

export default TestPage;
