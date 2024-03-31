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
const Img = styled.div`
  width: 1.875rem;
  height: 1.875rem;
  flex-shrink: 0;
  border-radius: 1.6875rem;
  background: url(<path-to-image>) lightgray 50% / cover no-repeat;
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

const CommunityTitle = () => {
  const { communityId } = useParams();
  const { data: community, isLoading } = useQuery({
    queryKey: ["CommunityDetail"],
    queryFn: communityId ? () => getDetailCommunities(communityId) : undefined,
  });
  if (isLoading) {
    return <div>Loading..</div>;
  }

  return (
    <All>
      <Title>{community.title}</Title>
      <Profile>



        
        <Img />
        <NickGrade>
          <Nick>닉네임</Nick>
          <Grade>등급</Grade>
        </NickGrade>
      </Profile>
      <Time>시간</Time>
      <Container></Container>
    </All>
  );
};

export default CommunityTitle;
