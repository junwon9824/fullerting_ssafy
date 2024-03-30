import styled from "styled-components";

const Comment = styled.div`
  margin-top: 0.5rem;
  margin-left: 2rem;
  font-size: 0.875rem;
  font-style: normal;
  font-weight: 400;
  line-height: 1.25rem;
  display: flex;
  padding: 0.375rem 0.6875rem;
  gap: 0.625rem;
  border-radius: 0.4rem;
  background: var(--gary3, #f4f4f4);
`;
const All = styled.div`
  margin-top: 1rem;
  font-family: "GamtanRoad Dotum TTF";
  padding-right: 3rem;
  padding-left: 3rem;
`;

const Profile = styled.div`
  display: flex;
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
  margin-top: 0.5rem;
  margin-bottom: 1rem;
  display: flex;
  justify-content: end;
  color: #8c8c8c;
  font-size: 0.6875rem;
  font-style: normal;
  font-weight: 400;
  line-height: 1rem;
`;
const CommunityComment = () => {
  return (
    <All>
      <Profile>
        <Img />
        <NickGrade>
          <Nick>닉네임</Nick>
          <Grade>등급</Grade>
        </NickGrade>
      </Profile>
      <Comment>
        댓글 더미 무슨 보고 있으니 기분이 좋네요 얼마나 더 자랄지 기대가 됩니다
      </Comment>
      <Time>시간</Time>
    </All>
  );
};

export default CommunityComment;
