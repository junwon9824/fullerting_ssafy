import styled from "styled-components";
import cartIcon from "../../assets/svg/cart.svg";
import diaryIcon from "../../assets/svg/diary.svg";
import { useQuery } from "@tanstack/react-query";
import { getAlarms } from "../../apis/Alarm";
import { useNavigate } from "react-router-dom";
interface NotificationProps {
  alarmType: "커뮤니티" | "작물거래";
  alarmContent: string;
  alarmRedirect: string;
  alarmId: number;
  checked: boolean;
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
      props.alarmType === "커뮤니티" ? diaryIcon : cartIcon});
    background-size: cover;
    background-repeat: no-repeat;
  }
`;
const NotificationItem = styled.div<{ isNew: boolean }>`
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.8rem;
  font-weight: 400;
  line-height: 1.7rem;
  width: 23.45rem;
  height: 5.5rem;
  padding: 0.5rem 1.5rem;
  background-color: ${(props) => (props.isNew ? "none" : "#E5F9DB")};
  border-bottom: 1px solid #e5e7eb;
  cursor: pointer;

  &:first-child {
    border-top: 1.5px solid #e5e7eb;
  }
`;

const AllAlarm = () => {
  const navigate = useNavigate();
  const {
    data: mockNotifications,
    isLoading,
    error,
  } = useQuery({
    queryKey: ["alarms"],
    queryFn: getAlarms,
  });

  if (isLoading) {
    console.log("데이터 로딩 중...");
    return <div>로딩중..</div>;
  }

  if (error) {
    console.error("알람 데이터를 가져오는데 실패했습니다:", error);
    return <div>알람 데이터를 가져오는데 실패했습니다: {error.message}</div>;
  }
  console.log("alarm", mockNotifications);

  const parseContent = (content: string): React.ReactNode => {
    const regex = /#(.*?)#/g;
    const parts = content.split(regex);

    return parts.map((part, index) => {
      if (index % 2 === 1) {
        return <strong key={index}>{part}</strong>;
      } else {
        return part;
      }
    });
  };

  const handleClick = (alarmId: number) => {
    const notification = mockNotifications.find(
      (notification) => notification.alarmId === alarmId
    );

    if (notification) {
      console.log(`Notification ${alarmId} clicked.`);
      switch (notification.alarmType) {
        case "작물거래":
          navigate("/trade");
          break;
        case "커뮤니티":
          navigate("/community");
          break;
        default:
          console.error("alarmType 오류:", notification.alarmType);
      }
    } else {
      console.error("알림 id를 찾을수없음", alarmId);
    }
  };

  return (
    <div>
      <NotificationList>
        {mockNotifications.map((notification) => (
          <NotificationItem
            key={notification.alarmId}
            isNew={notification.checked}
            onClick={() => handleClick(notification.alarmId)}
          >
            <Notification alarmType={notification.alarmType}>
              {notification.alarmType}
              {notification.alarmId}
            </Notification>
            <div>{parseContent(notification.alarmContent)}</div>
          </NotificationItem>
        ))}
      </NotificationList>
    </div>
  );
};

export default AllAlarm;
