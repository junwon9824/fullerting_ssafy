import React from "react";
import styled, { keyframes } from "styled-components";
import { useAtom } from "jotai";
import { notificationAtom } from "../../stores/notification";
import { useEffect, useState } from "react";
import alarmticon from "../../assets/svg/alarmticon.svg";
import alarmback from "../../assets/svg/alramback.svg";


interface ModalContainerProps {
  isAnimating: boolean;
}
const slideDown = keyframes`
  from {
    transform: translateY(-100%);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
`;

const slideUp = keyframes`
  from {
    transform: translateY(0);
    opacity: 1;
  }
  to {
    transform: translateY(-100%);
    opacity: 0;
  }
`;

const ModalContainer = styled.div<ModalContainerProps>`
  position: fixed;
  top: 2%;
  width:90%;
  height: 7rem;
  border-radius: 6px;
  margin-left: 18px;
  background-color: white;
  padding: 10px;
  z-index: 1000;
  animation: ${(props) => (props.isAnimating ? slideDown : slideUp)} 1s ease-out
    forwards;
  filter: drop-shadow(0px 7px 7px rgba(0, 0, 0, 0.25));
`;

const NotiFrame = styled.div`
  display: fixed;
  padding: 1rem 0.8rem 0rem 0.8rem;
`;

const NotificationContent = styled.div`
  position: relative; /* 부모 요소에 대해 상대 위치 설정 */
  color: #000000;
  text-align: center;
  margin: 10px;
  font-family: "Noto Sans KR";
  font-size: 13px;
  font-style: normal;
  line-height: 18px; /* 169.231% */
  letter-spacing: -0.3px;
`;

const CloseButton = styled.button`
  position: absolute; /* 절대 위치 설정 */
  bottom: -28px; /* 원하는 위치로 조정 */
  left: 50%; /* 가운데 정렬 */
  transform: translateX(-50%); /* 가운데 정렬 */
  width: 17%;
  padding: 4px 5px;
  background-color: #b3e8c4;
  border-radius: 9px;
  cursor: pointer;
  color: var(--Warning-25, #ffffff);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 12px;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
`;

const AlarmIcon = styled.img`
  position: absolute; /* 절대 위치 설정 */
  top: 72px; /* 원하는 위치로 조정 */
  left: 12px; /* 원하는 위치로 조정 */
  width: 30px; /* 이미지 너비 조정 */
  height: auto; /* 이미지 높이 자동으로 조정 */
`;

const AlarmBackIcon = styled.img`
  position: absolute; // Adjust positioning as needed
  width: 20%; // Ensure it covers the container as intended
  left: 259px;
  top: 33px;
  height: 80px; // Match the container's height
  object-fit: cover; // Ensure the image covers the space without distorting
  z-index: -1; // Place it behind the content
`;
const parseContent = (content: string): React.ReactNode => {
  const regex = /#(.*?)#/g;
  const parts = content.split(regex);
  return parts.map((part, index) => {
    if (index % 2 === 1) {
      return <strong key={index} style={{ color: '#A0D8B3', fontWeight: 'bold' }}>{part}</strong>;
    } else {
      return part;
    }
  });
};

const NotificationModal: React.FC = () => {
  const [notification, setNotification] = useAtom(notificationAtom);
  const [isAnimating, setIsAnimating] = useState(false);

  useEffect(() => {
    if (notification.show) {
      setIsAnimating(true);
      // 모달을 보여준 후 3초 뒤에 자동으로 닫힘
      const timer = setTimeout(() => {
        setNotification((prev) => ({ ...prev, show: false }));
      }, 5000);

      return () => {
        clearTimeout(timer); // 컴포넌트가 언마운트될 때 타이머 정리
      };
    }
  }, [notification.show]);

  const handleRedirect = () => {
    if (notification.redirectURL) {
      window.location.pathname = notification.redirectURL; // pathname을 사용하여 리다이렉트
    }
  };

  if (!notification.show) {
    return null; // show가 false면 아무 것도 렌더링하지 않음
  }
  
  return (
    <>
      <ModalContainer isAnimating={isAnimating} onClick={handleRedirect}>
      <AlarmBackIcon src={alarmback} alt="Background Icon" />
      <AlarmIcon src={alarmticon} alt="Alarm Icon" />
        <NotificationContent>
        {parseContent(notification.content)}
        <NotiFrame>
          <CloseButton
            onClick={(e) => {
              e.stopPropagation(); // 모달 자체의 클릭 이벤트가 실행되지 않도록 이벤트 전파를 막음
              setNotification((prev) => ({ ...prev, show: false }))
            }
            }
          >
            확인
          </CloseButton>
        </NotiFrame>
        </NotificationContent>
      </ModalContainer>
    </>
  );
};

export default NotificationModal;
