import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import profileImage from "/src/assets/svg/profileimage.svg";
import arrow from "/src/assets/svg/arrow_forward_ios.svg";

const ProfileContent = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  width: 100%;
  margin-top: 3rem;
`;

const ProfileContainer = styled.div`
  display: flex;
  align-items: center;
`;

const ProfileImageContainer = styled.div`
  width: 5rem;
  height: 5rem;
  border-radius: 50%;
  overflow: hidden;
  background-image: url(${profileImage});
  background-size: cover;
  background-position: center;
  margin-right: 1rem;
`;

const ProfileText = styled.div`
  display: flex;
  flex-direction: column;
`;

const Nickname = styled.span`
  margin-bottom: 0.6rem;
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 1.25rem;
  font-weight: bold;
`;

const Grade = styled.span`
  color: #4f4f4f;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 1rem;
  font-style: normal;
  font-weight: 400;
  line-height: 1rem; /* 100% */
`;

const Maintop = () => {
  const navigate = useNavigate();

  const goToProfilePage = () => {
    navigate("/editprofile");
  };

  return (
    <ProfileContent onClick={goToProfilePage}>
      <ProfileContainer>
        <ProfileImageContainer />
        <ProfileText>
          <Nickname>닉네임</Nickname>
          <Grade>등급</Grade>
        </ProfileText>
      </ProfileContainer>
      <img src={arrow} alt="" />
    </ProfileContent>
  );
};
export default Maintop;
