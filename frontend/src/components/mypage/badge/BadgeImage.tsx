import styled from "styled-components";
import load from "../../../assets/svg/loader.svg";
import { fetchBadges } from "../../../apis/MyPage";
import { useQuery } from "@tanstack/react-query";

const BadgesContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
`;

const Badge = styled.img`
  width: 5.625rem;
  height: 5.625rem;
  margin: 1.5rem 0.5rem;
`;

const BadgeText = styled.div`
  color: var(--gray1, #8c8c8c);
  text-align: center;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.75rem;
  font-style: normal;
  font-weight: 400;
  white-space: pre-wrap;
`;

const AllBadgesPage = () => {
  const {
    data: badges,
    isLoading,
    error,
  } = useQuery({
    queryKey: ["allbadges"],
    queryFn: fetchBadges,
  });

  if (isLoading) {
    console.log("데이터 로딩 중...");
    return <div>로딩중</div>;
  }

  if (error) {
    console.error("뱃지 데이터를 가져오는데 실패했습니다:", error);
    return <div>뱃지 데이터를 가져오는데 실패했습니다: {error.message}</div>;
  }
  console.log("전체전체", badges);

  return (
    <BadgesContainer>
      {badges.map((badge, index) => (
        <div key={index}>
          <Badge src={badge.badgeImg} alt={`뱃지 ${index + 1}`} />
          <BadgeText>
            {badge.badgeName.length > 10 ? (
              <>
                {badge.badgeName.slice(0, 9)}
                <br />
                {badge.badgeName.slice(9)}
              </>
            ) : (
              badge.badgeName
            )}
          </BadgeText>
        </div>
      ))}
    </BadgesContainer>
  );
};

export default AllBadgesPage;
