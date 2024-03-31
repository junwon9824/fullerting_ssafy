import { useQuery } from "@tanstack/react-query";
import { useParams } from "react-router-dom";
import { getDetailCommunities } from "../../apis/CommunityApi";
import styled from "styled-components";

interface ImgProps {
  backgroundImage: string;
}

const All = styled.div`
  width: 100%;
  font-family: "GamtanRoad Dotum TTF";
  height: 100%;
  padding-right: 3rem;
  padding-left: 3rem;
  align-items: center;
`;
const Container = styled.div`
  flex-direction: column;
  padding-left: 2rem;
`;
const Title = styled.div`
  padding-top: 4rem;
  font-size: 1rem;
  font-weight: bold;
`;

const Profile = styled.div`
  display: flex;
  margin-top: 1.5rem;
`;
const Img = styled.div<ImgProps>`
  width: 1.875rem;
  height: 1.875rem;
  flex-shrink: 0;
  border-radius: 1.6875rem;
  background: ${(props) =>
    `url(${props.backgroundImage}) lightgray 50% / cover no-repeat`};
`;
const NickGrade = styled.div`
  margin-left: 0.5rem;
`;
const Nick = styled.div`
  font-size: 0.8125rem;
  font-style: normal;
  font-weight: 400;
  line-height: 1.125rem;
`;
const Grade = styled.div`
  color: #4f4f4f;
  font-family: Inter;
  font-size: 0.6875rem;
  font-weight: 400;
  line-height: 1rem;
`;

const Time = styled.div`
  display: flex;
  justify-content: end;
  color: #8c8c8c;
  font-size: 0.6875rem;
  font-style: normal;
  font-weight: 400;
  line-height: 1rem;
`;

const getTimeDifference = (minutes: number) => {
  if (minutes < 1) {
    return `방금 전`;
  } else if (minutes < 60) {
    return `${minutes}분 전`;
  } else if (minutes < 1440) {
    const hours = Math.floor(minutes / 60);
    return `${hours}시간 전`;
  } else {
    const days = Math.floor(minutes / 1440);
    return `${days}일 전`;
  }
};

const CommunityTitle = () => {
  const { communityId } = useParams();
  const { data: community, isLoading: isCommunityDetailLoading } = useQuery({
    queryKey: ["CommunityDetail"],
    queryFn: communityId ? () => getDetailCommunities(communityId) : undefined,
  });

  if (isCommunityDetailLoading) {
    return <div>Loading..</div>;
  }
  return (
    <All>
      <Title>{community.title}</Title>
      <Profile>
        <Img backgroundImage={community.thumbnail} />

        <NickGrade>
          <Nick>{community.authornickname}</Nick>
          <Grade>{community.rank}</Grade>
        </NickGrade>
      </Profile>
      <Time>{getTimeDifference(community.time)}</Time>
      <Container></Container>
    </All>
  );
};

export default CommunityTitle;
