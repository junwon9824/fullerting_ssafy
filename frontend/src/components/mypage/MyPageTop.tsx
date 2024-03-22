import styled from "styled-components";
import { useNavigate } from "react-router-dom";
import arrow from "/src/assets/svg/arrow_forward_ios.svg";
import { getUsersInfo } from "../../apis/MyPage";
import { useQuery } from "@tanstack/react-query";
import pull from "../../assets/svg/pullright.svg";

interface ProfileImage {
  backgroundImage: string;
}

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

const ProfileImageContainer = styled.div<ProfileImage>`
  width: 5rem;
  height: 5rem;
  border-radius: 50%;
  overflow: hidden;
  background-image: url(${(props) => props.backgroundImage || pull});
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
  line-height: 1rem;
`;

const Maintop = () => {
  console.log("메인 TOP 컴포넌트");
  const navigate = useNavigate();

  const goToProfilePage = () => {
    navigate("editprofile");
    console.log("durldudrludlruld", badges?.data.data_body.thumbnail);
  };
  const { data: badges, error } = useQuery({
    queryKey: ["Info"],
    queryFn: getUsersInfo,
  });

  if (error) {
    console.error("뱃지 데이터를 가져오는데 실패했습니다:", error);
    return <div>사용자 데이터를 가져오는데 실패했습니다: {error.message}</div>;
  }

  console.log("여기는 메인 TOP", badges?.data.data_body);
  return (
    <ProfileContent onClick={goToProfilePage}>
      <ProfileContainer>
        <ProfileImageContainer
          backgroundImage={badges?.data.data_body.thumbnail}
        />
        <ProfileText>
          <Nickname>{badges?.data.data_body.nickname}</Nickname>
          <Grade>{badges?.data.data_body.rank}</Grade>
        </ProfileText>
      </ProfileContainer>
      <img src={arrow} alt="" />
    </ProfileContent>
  );
};
export default Maintop;
