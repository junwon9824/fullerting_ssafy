import styled from "styled-components";
import pull from "/src/assets/svg/pullright.svg";

const BadgesContainer = styled.div`
  display: flex;
  flex-wrap: wrap;
  justify-content: space-around;
`;

const Badge = styled.img`
  width: 5.625rem;
  height: 5.625rem;
  margin: 1.5rem 0;
`;

const BadgeText = styled.div`
  color: var(--gray1, #8c8c8c);
  text-align: center;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.75rem;
  font-style: normal;
  font-weight: 400;
`;

const AllBadgesPage = () => {
  const badges = new Array(10).fill({ imageUrl: pull, name: "더미 뱃지" });

  return (
    <BadgesContainer>
      {badges.map((badge, index) => (
        <div key={index}>
          <Badge src={badge.imageUrl} alt={`뱃지 ${index + 1}`} />
          <BadgeText>{badge.name}</BadgeText>
        </div>
      ))}
    </BadgesContainer>
  );
};

export default AllBadgesPage;
