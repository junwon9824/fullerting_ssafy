import { useMutation, useQueryClient } from "@tanstack/react-query";
import { FormEvent, useState } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import { createComment } from "../../apis/CommunityApi";

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

  // 댓글 제출 핸들러
  const handleSubmit = (event: FormEvent<HTMLFormElement>) => {
    event.preventDefault();
    if (!comment.trim()) return;
    if (isLoading) return;

    mutate({ commentContent: comment, communityId: communityId }); // 'commentcontent' 필드에 'comment' 상태 값을 전달합니다.
  };
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
