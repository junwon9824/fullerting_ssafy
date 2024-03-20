import styled from "styled-components";
import React from "react";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { fetchBadges } from "../../apis/MyPage";
import arrow from "/src/assets/svg/arrow_forward_ios.svg";
import { useQuery } from "@tanstack/react-query";

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

const Badge = styled.div`
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
const Maintop = () => {
  const navigate = useNavigate();
  console.log("Maintop 컴포넌트 렌더링");

  // useEffect(() => {
  //   fetchBadges()
  //     .then((data) => console.log("가가가가가가", data.data_body))
  //     .catch((err) => console.error("아아아아아아", err));
  // }, []);

  const { data, isLoading, error } = useQuery({
    queryKey: ["badges"],
    queryFn: fetchBadges,
  });

  // 로딩 상태 처리
  if (isLoading) {
    console.log("데이터 로딩 중...");
    return <div>로딩 중...</div>;
  }

  // 에러 처리
  if (error) {
    console.error("뱃지 데이터를 가져오는데 실패했습니다:", error);
    return <div>뱃지 데이터를 가져오는데 실패했습니다: {error.message}</div>;
  }

  // 데이터가 성공적으로 로드되었을 때의 로그
  console.log("로드된 데이터:", data);

  const pages = [
    { title: "보유 뱃지", onClick: () => navigate("/mypage/allbadge") },
    { title: "", onClick: () => navigate("/mypage/allbadge") },
    { title: "나의 제안 목록", onClick: () => navigate("/mypage/proposepost") },
    { title: "관심 게시글", onClick: () => navigate("/mypage/likedpost") },
    { title: "나의 거래 게시글", onClick: () => navigate("/mypage/transpost") },
    { title: "로그아웃", onClick: () => navigate("/mypage/logout") },
  ];

  return (
    <>
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
                    .map((badge, index) => (
                      <Badge key={index} badgeImg={badge.badgeImg} />
                    ))}
                {data && data.length - 4 > 0 && (
                  <AdditionalBadges>+{data.length - 4}</AdditionalBadges>
                )}
              </BadgesContainer>
            )}
            <img src={arrow} alt="" />
          </ProfileContent>
          {page.title !== "보유 뱃지" && <Line />}
        </React.Fragment>
      ))}
    </>
  );
};
export default Maintop;
