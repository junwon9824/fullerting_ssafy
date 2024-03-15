// import React, { useEffect } from "react";
// import styled from "styled-components";
// import useInput from "../../hooks/useInput";
// import { Line } from "../../components/common/Line";
//  import SockJS from "sockjs-client";
// import Stomp from "stompjs";
// import { LayoutMainBox } from "../../components/common/Layout/Box";

// const TestPage = () => {
//   const [email, setEmail] = useInput("");
//   const [password, setPassword] = useInput("");

//   const handleClick = () => {};

//   const handleConfirmClick = () => {};

//   useEffect(() => {
//     const socket = new SockJS("/ws"); // 웹소켓 엔드포인트에 연결
//     const stompClient = Stomp.over(socket);

//     stompClient.connect({}, function (frame) {
//       console.log("Connected: " + frame);
//       stompClient.subscribe("/topic/messages", function (message) {
//         console.log("Received message: " + message.body);
//         // 이곳에서 메시지를 처리할 수 있습니다.
//       });
//     });

//     return () => {
//       // 컴포넌트가 언마운트될 때 연결 해제
//       stompClient.disconnect();
//     };
//   }, []); // 빈 배열을 전달하여 컴포넌트가 처음 마운트될 때만 실행되도록 함

//   return (
//     <LayoutMainBox>
//       <div>socket</div>

//       <Line />
//     </LayoutMainBox>
//   );
// };

// export default TestPage;
