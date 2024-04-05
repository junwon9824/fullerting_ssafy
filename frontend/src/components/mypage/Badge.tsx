import styled from "styled-components";
import React, { useState } from "react";
import { useNavigate } from "react-router-dom";
import load from "../../assets/svg/loader.svg";
import { fetchBadges, logoutUser } from "../../apis/MyPage";
import arrow from "/src/assets/svg/arrow_forward_ios.svg";
import { useQuery } from "@tanstack/react-query";
import { useMutation } from "@tanstack/react-query";

interface BadgeProps {
  badgeImg: string;
}
interface LogoutModalProps {
  onClose: () => void;
  onConfirm: () => void;
}
interface BadgeData {
  badgeImg: string;
}

const LogoutModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  align-items: center;
  justify-content: center;
  z-index: 1000;
`;

const LogoutModalContent = styled.div`
  background-color: white;
  padding: 2rem 3rem;
  border-radius: 1rem;
  text-align: center;
`;

const LogoutButtonleft = styled.button`
  width: 4.125rem;
  height: 1.875rem;
  border-radius: 0.625rem;
  color: #fff;
  margin-top: 2rem;
  margin-right: 0.5rem;
  font-family: "Noto Sans";
  font-size: 0.7rem;
  font-weight: 700;
  background: var(--sub0, #a0d8b3);
`;

const LogoutButtonright = styled.button`
  width: 4.125rem;
  height: 1.875rem;
  border-radius: 0.625rem;
  color: #fff;
  margin-left: 0.5rem;
  font-family: "Noto Sans";
  font-size: 0.7rem;
  font-weight: 700;
  background: var(--gray1, #8c8c8c);
`;

const LogoutText = styled.div`
  color: #000;
  text-align: center;
  font-size: 0.9rem;
  font-style: normal;
  font-weight: bold;
`;
const ProfileContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
`;
const BadgesContainer = styled.div`
  display: flex;
  align-items: center;
  margin-right: auto;
  margin-top: 1rem;
`;

const Badge = styled.div<BadgeProps>`
  width: 3.125rem;
  height: 3.125rem;
  border-radius: 50%;
  background-color: #cdc8c8;
  margin-right: 0.8rem;
  background-image: url(${(props) => props.badgeImg});
  background-size: cover;
`;

const AdditionalBadges = styled.div`
  font-size: 0.875rem;
  line-height: 3.125rem;
  color: #606060;
  margin-right: 0.5rem;
`;

const ProfileText = styled.div`
  display: flex;
  flex-direction: column;
`;

const Nickname = styled.span`
  color: #000;

  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.875rem;
  font-style: normal;
  font-weight: bold;
  line-height: 1.3rem;
`;

const Line = styled.hr`
  background: rgba(244, 244, 244, 1);
  width: 19.87513rem;
  height: 0.0625rem;
`;

const LogoutModal: React.FC<LogoutModalProps> = ({ onClose, onConfirm }) => {
  return (
    <LogoutModalOverlay>
      <LogoutModalContent>
        <LogoutText>정말 로그아웃하시겠습니까?</LogoutText>
        <LogoutButtonleft onClick={onConfirm}>확인</LogoutButtonleft>
        <LogoutButtonright onClick={onClose}>취소</LogoutButtonright>
      </LogoutModalContent>
    </LogoutModalOverlay>
  );
};

const Maintop = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const navigate = useNavigate();

  const { mutate: performLogout } = useMutation({
    mutationFn: logoutUser,
    onSuccess: () => {
      navigate("/");
    },

    onError: (error) => {
      console.error("로그아웃 실패:", error);
    },
  });

  const closeModal = () => {
    setIsModalOpen(false);
  };

  const confirmLogout = () => {
    performLogout();
  };

  const { data, isLoading, error } = useQuery({
    queryKey: ["badges"],
    queryFn: fetchBadges,
  });

  if (isLoading) {
    console.log("데이터 로딩 중...");
    return <img src={load} alt="" style={{ width: "80px", height: "80px" }} />;
  }

  if (error) {
    console.error("뱃지 데이터를 가져오는데 실패했습니다:", error);
    return <div>뱃지 데이터를 가져오는데 실패했습니다: {error.message}</div>;
  }

  console.log("로드된 데이터:", data);

  const pages = [
    { title: "보유 뱃지", onClick: () => navigate("/mypage/allbadge") },
    { title: "", onClick: () => navigate("/mypage/allbadge") },
    { title: "나의 제안 목록", onClick: () => navigate("/mypage/proposepost") },
    { title: "관심 게시글", onClick: () => navigate("/mypage/likedpost") },
    { title: "나의 거래 게시글", onClick: () => navigate("/mypage/transpost") },
    { title: "로그아웃", onClick: () => setIsModalOpen(true) },
  ];

  return (
    <>
      {isModalOpen && (
        <LogoutModal onClose={closeModal} onConfirm={confirmLogout} />
      )}
      {pages.map((page, index) => (
        <React.Fragment key={index}>
          <ProfileContent onClick={page.onClick}>
            <ProfileText>
              <Nickname>{page.title}</Nickname>
            </ProfileText>
            {page.title === "" && (
              <BadgesContainer>
                {data &&
                  data
                    .slice(0, 4)
                    .map((badge: BadgeData, index: number) => (
                      <Badge key={index} badgeImg={badge.badgeImg} />
                    ))}
                {data && data.length - 4 > 0 && (
                  <AdditionalBadges>+{data.length - 4}</AdditionalBadges>
                )}
              </BadgesContainer>
            )}
            {page.title !== "" && <img src={arrow} alt="" />}
          </ProfileContent>
          {page.title !== "보유 뱃지" && <Line />}
        </React.Fragment>
      ))}
    </>
  );
};
export default Maintop;
