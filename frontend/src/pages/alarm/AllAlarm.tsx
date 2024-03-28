import React, { useEffect, useState } from "react";
import styled from "styled-components";
import cartIcon from "../../assets/svg/cart.svg";
import diaryIcon from "../../assets/svg/diary.svg";
interface NotificationProps {
  type: "커뮤니티" | "작물거래";
}
const NotificationList = styled.div`
  display: flex;
  flex-direction: column;
`;
const Notification = styled.div<NotificationProps>`
  color: var(--gray1, #8c8c8c);
  display: flex;
  align-items: center;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.7rem;

  &::before {
    content: "";
    display: block;
    width: 0.75rem;
    height: 0.75rem;
    margin-right: 10px;
    background-image: url(${(props) =>
      props.type === "커뮤니티" ? diaryIcon : cartIcon});
    background-size: cover;
    background-repeat: no-repeat;
  }
`;
const NotificationItem = styled.div<{ isNew: boolean }>`
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.9rem;
  font-weight: 400;
  line-height: 1.7rem;
  width: 23.45rem;
  height: 5.5rem;
  padding: 0.5rem 1.5rem;
  background-color: ${(props) => (props.isNew ? "#E5F9DB" : "none")};
  border-bottom: 1px solid #e5e7eb;
  cursor: pointer;

  &:first-child {
    border-top: 1.5px solid #e5e7eb;
  }
`;
const mockNotifications = [
  {
    type: "커뮤니티",
    id: 1,
    message: "찐명님이 친구 요청을 보냈습니다.",
    isNew: true,
  },
  {
    type: "작물거래",
    id: 2,
    message: "찐명님이 당신의 게시물에 댓글을 남겼습니다.",
    isNew: true,
  },
  {
    type: "커뮤니티",
    id: 3,
    message: "찐명님이 당신의 게시물에 댓글을 남겼습니다.",
    isNew: true,
  },
  {
    type: "작물거래",
    id: 4,
    message: "찐명님이 당신의 게시물에 댓글을 남겼습니다.",
    isNew: true,
  },
  {
    type: "커뮤니티",
    id: 5,
    message: "찐명님이 당신의 게시물에 댓글을 남겼습니다.",
    isNew: false,
  },
  {
    type: "작물거래",
    id: 6,
    message: "찐명님이 당신의 게시물에 댓글을 남겼습니다.",
    isNew: false,
  },
];

const AllAlarm = () => {
  const [notifications, setNotifications] = useState(mockNotifications);

  useEffect(() => {
    setNotifications((notifications) =>
      [...notifications].sort((a, b) => Number(b.isNew) - Number(a.isNew))
    );
  }, []);

  const handleClick = (id: number) => {
    setNotifications((notifications) =>
      notifications.map((notification) =>
        notification.id === id
          ? { ...notification, isNew: false }
          : notification
      )
    );
  };
  return (
    <div>
      <NotificationList>
        {notifications.map((notification) => (
          <NotificationItem
            key={notification.id}
            isNew={notification.isNew}
            onClick={() => handleClick(notification.id)}
          >
            <Notification type={notification.type}>
              {notification.type}
            </Notification>
            {notification.message}
          </NotificationItem>
        ))}
      </NotificationList>
    </div>
  );
};

export default AllAlarm;
