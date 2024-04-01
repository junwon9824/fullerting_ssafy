import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { FormEvent, useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import {
  DeleteComment,
  createComment,
  fetchAllComments,
} from "../../apis/CommunityApi";

interface ImgProps {
  backgroundImage: string;
}

const CommentContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 1rem;
  margin-bottom: 1rem;
`;
const Comment = styled.div`
  background-color: #f4f4f4;
  padding: 10px;
  border-radius: 10px;
  max-width: 80%;
  margin-left: 1rem;
  word-wrap: break-word;
  font-size: 14px;
  line-height: 1.5;
  align-self: flex-end;
`;
const All = styled.div`
  margin-top: 1rem;
  font-family: "GamtanRoad Dotum TTF";
  padding-left: 3rem;
  margin-bottom: 9rem;
`;

const Profile = styled.div`
  display: flex;
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
const CommentTime = styled.div``;
const Time = styled.span`
  margin-left: 1rem;
  font-size: 12px;
  color: #888;
  display: flex;
  margin-left: 11rem;
`;
const CommentInputContainer = styled.div`
  margin-bottom: 3rem;
  position: fixed;
  bottom: 0;
  left: 0;
  width: 100%;
  padding: 1rem;
  background-color: #fff;
`;

const CommentInput = styled.input`
  font-family: "GamtanRoad Dotum TTF";
  width: calc(100% - 4rem);
  padding: 0.5rem;
  border-radius: 0.4rem;
  border: 1px solid #ccc;
`;

const SubmitButton = styled.button`
  font-family: "GamtanRoad Dotum TTF";
  background: var(--sub0, #a0d8b3);
  border: none;
  color: white;
  padding: 10px 12px;
  text-align: center;
  font-size: 12px;
  margin: 5px 8px;
  cursor: pointer;
  border-radius: 0.5rem;
`;

const Delete = styled.div`
  margin-top: 0.5rem;
  margin-left: 14.1rem;
  font-size: 12px;
  color: #888;
`;

const formatDate = (isoString: string) => {
  const date = new Date(isoString);
  const year = date.getFullYear();
  const month = date.getMonth() + 1;
  const day = date.getDate();
  const hour = date.getHours();
  const minute = date.getMinutes();

  const paddedMonth = month < 10 ? `0${month}` : month;
  const paddedDay = day < 10 ? `0${day}` : day;
  const paddedHour = hour < 10 ? `0${hour}` : hour;
  const paddedMinute = minute < 10 ? `0${minute}` : minute;

  return `${year}-${paddedMonth}-${paddedDay} ${paddedHour}:${paddedMinute}`;
};

const CommunityComment = () => {
  const [comment, setComment] = useState("");
  const { communityId } = useParams<{ communityId: string }>();
  const queryClient = useQueryClient();

  const { mutate, isLoading } = useMutation({
    mutationFn: createComment,
    onSuccess: () => {
      queryClient.invalidateQueries(["communityComments", communityId]);
      setComment("");
    },
    onError: (error) => {
      console.error("댓글 생성 오류:", error);
    },
  });

  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!comment.trim()) return;
    if (isLoading) return;

    mutate({ commentContent: comment, communityId: communityId });
  };
  const { data: allCommentData, isLoading: Loading } = useQuery({
    queryKey: ["allCommentData", communityId],
    queryFn: communityId ? () => fetchAllComments(communityId) : undefined,
    enabled: !!communityId,
  });

  if (Loading) {
    return <div>Loading...</div>;
  }
  return (
    <All>
      {allCommentData?.map((CommentData) => (
        <CommentContainer key={CommentData.id}>
          <Profile>
            <Img backgroundImage={CommentData.thumbnail} />
            <NickGrade>
              <Nick>{CommentData.nickname}</Nick>
              <Grade>{CommentData.rank}</Grade>
            </NickGrade>
          </Profile>
          <CommentTime>
            <Comment>{CommentData.commentcontent}</Comment>
            <Time>{formatDate(CommentData.localDateTime)}</Time>
            <Delete>댓글삭제</Delete>
          </CommentTime>
        </CommentContainer>
      ))}
      <CommentInputContainer as="form" onSubmit={handleSubmit}>
        <CommentInput
          type="text"
          placeholder="댓글을 입력하세요..."
          value={comment}
          onChange={(e) => setComment(e.target.value)}
        />
        <SubmitButton type="submit">등록</SubmitButton>
      </CommentInputContainer>
    </All>
  );
};

export default CommunityComment;
