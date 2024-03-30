import { useQuery } from "@tanstack/react-query";
import { useParams } from "react-router-dom";
import { getDetailCommunities } from "../../apis/CommunityApi";
import styled from "styled-components";

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
  margin-top: 3rem;
`;
const Title = styled.div`
  padding-top: 4rem;
  font-size: 1rem;
  font-weight: bold;
`;

const Time = styled.div``;

const Profile = styled.div`
  display: flex;
  margin-top: 1.5rem;
  justify-content: space-between;
`;
const Nick = styled.div`
  font-size: 1rem;
  font-weight: bold;
`;
const CommunityTitle = () => {
  const { communityId } = useParams();
  console.log(communityId);
  const { data: community, isLoading } = useQuery({
    queryKey: ["CommunityDetail"],
    queryFn: communityId ? () => getDetailCommunities(communityId) : undefined,
  });
  if (isLoading) {
    return <div>Loading..</div>;
  }
  console.log(community);

  return (
    <All>
      <Title>{community.title}</Title>
      <Profile>
        <Nick>{community.id}</Nick>
        <Time>시간</Time>
      </Profile>
      <Container></Container>
    </All>
  );
};

export default CommunityTitle;
