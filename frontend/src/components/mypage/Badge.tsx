import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import arrow from "/src/assets/svg/arrow_forward_ios.svg";
import pul from "/src/assets/svg/pullleft.svg";

const ProfileContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
`;
const BadgesContainer = styled.div`
  display: flex;
  align-items: center;
  margin-right: auto; // 오른쪽으로 여백을 줘서 Next와 떨어지게 함
`;

const Badge = styled.div`
  width: 3.125rem;
  height: 3.125rem;
  border-radius: 50%;
  background-color: #cdc8c8;
  margin-right: 0.8rem;
`;

const AdditionalBadges = styled.div`
  font-size: 0.875rem; // 추가 뱃지 수의 폰트 사이즈 설정
  line-height: 3.125rem; // 뱃지 높이에 맞춤
  color: #606060;
  margin-right: 0.5rem;
`;

const ProfileText = styled.div`
  display: flex;
  flex-direction: column;
`;

const Nickname = styled.span`
  font-family: "GamtanRoad Dotum TTF";
  color: #000;
  font-size: 0.875rem;
  font-style: normal;
  font-weight: 400;
  line-height: 1.125rem;
`;

const Line = styled.hr`
  background: rgba(244, 244, 244, 1);
  width: 19.87513rem;
  height: 0.0625rem;
`;
const Maintop = () => {
  const navigate = useNavigate();
  const badges = [
    { imageUrl: pul },
    { imageUrl: pul },
    { imageUrl: pul },
    { imageUrl: pul },
    { imageUrl: pul },
    { imageUrl: pul },
    { imageUrl: pul },
  ];

  const additionalBadges = badges.length - 4;
  const goToBadge = () => {
    navigate("/allbadge");
  };
  const goToPropose = () => {
    navigate("/proposepost");
  };
  const goToLike = () => {
    navigate("/likedpost");
  };
  const goToTrans = () => {
    navigate("/transpost");
  };
  const goToLogout = () => {
    navigate("/logout");
  };

  return (
    <>
      <ProfileContent onClick={goToBadge}>
        <ProfileText>
          <Nickname>보유 뱃지</Nickname>
        </ProfileText>
      </ProfileContent>
      <ProfileContent onClick={goToBadge}>
        <ProfileText></ProfileText>
        <BadgesContainer>
          {badges.slice(0, 4).map((badge, index) => (
            <Badge
              key={index}
              style={{ backgroundImage: `url(${badge.imageUrl})` }}
            />
          ))}
          {additionalBadges > 0 && (
            <AdditionalBadges>+{additionalBadges}</AdditionalBadges>
          )}
        </BadgesContainer>
      </ProfileContent>
      <Line />
      <ProfileContent onClick={goToPropose}>
        <ProfileText>
          <Nickname>나의 제안 목록</Nickname>
        </ProfileText>
        <img src={arrow} alt="" />
      </ProfileContent>
      <Line />
      <ProfileContent onClick={goToLike}>
        <ProfileText>
          <Nickname>관심 게시글</Nickname>
        </ProfileText>
        <img src={arrow} alt="" />
      </ProfileContent>
      <Line />
      <ProfileContent onClick={goToTrans}>
        <ProfileText>
          <Nickname>나의 거래 게시글</Nickname>
        </ProfileText>
        <img src={arrow} alt="" />
      </ProfileContent>
      <Line />
      <ProfileContent onClick={goToLogout}>
        <ProfileText>
          <Nickname>로그아웃</Nickname>
        </ProfileText>
        <img src={arrow} alt="" />
      </ProfileContent>
      <Line />
    </>
  );
};
export default Maintop;
