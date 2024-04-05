import { useState } from "react";
import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { FormEvent } from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import {
  DeleteComment,
  createComment,
  fetchAllComments,
} from "../../apis/CommunityApi";
import { getUsersInfo } from "../../apis/MyPage";
interface ImgTypes {
  diary: number;
  exArticle: number;
  id: number;
  imgStoreUrl: string;
}
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

const DeleteButton = styled.button`
  font-family: "GamtanRoad Dotum TTF";
  background: var(--sub0, #a0d8b3);
  border: none;
  color: white;
  padding: 5px 10px;
  text-align: center;
  font-size: 10px;
  margin-top: 0.5rem;
  margin-left: 13rem;
  cursor: pointer;
  border-radius: 0.5rem;
`;

const ModalText = styled.p`
  margin-bottom: 20px;
`;
const ModalOverlay = styled.div`
  position: fixed;
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: rgba(0, 0, 0, 0.5);
  display: flex;
  justify-content: center;
  align-items: center;
  z-index: 1000;
`;
const ModalContent = styled.div`
  background-color: white;
  padding: 20px;
  border-radius: 10px;
  box-shadow: 0 4px 6px rgba(0, 0, 0, 0.1);
  z-index: 1001;
`;

const CloseButton = styled.button`
  font-family: "GamtanRoad Dotum TTF";
  background: var(--sub0, #b9bbba);
  border: none;
  color: white;
  padding: 5px 10px;
  text-align: center;
  font-size: 10px;
  cursor: pointer;
  border-radius: 0.5rem;
  margin-left: 0.3rem;
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
  const [showModal, setShowModal] = useState(false);
  const [commentIdToDelete, setCommentIdToDelete] = useState("");
  const { communityId } = useParams<{ communityId: string }>();
  const queryClient = useQueryClient();

  // 댓글 생성
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

    mutate({
      commentContent: comment,
      communityId: communityId,
    });
  };

  // 댓글 삭제
  const { mutate: deleteCommentMutation } = useMutation({
    mutationFn: DeleteComment,
    onSuccess: () => {
      queryClient.invalidateQueries(["allCommentData", communityId]);
      console.log("댓글 삭제 성공");
    },
    onError: (error) => {
      console.error("댓글 삭제 오류:", error);
    },
  });

  const handleDeleteClick = (commentId: string) => {
    setCommentIdToDelete(commentId);
    setShowModal(true);
  };

  const confirmDelete = () => {
    deleteCommentMutation({ communityId, commentId: commentIdToDelete });
    setShowModal(false);
  };

  // 전체 댓글 조회
  const { data: allCommentData, isLoading: Loading } = useQuery({
    queryKey: ["allCommentData", communityId],
    queryFn: communityId ? () => fetchAllComments(communityId) : undefined,
    enabled: !!communityId,
  });

  // 댓글남긴 사용자 조회
  const { data: userData } = useQuery({
    queryKey: ["UserData"],
    queryFn: getUsersInfo,
    enabled: !!communityId,
  });

  if (Loading) {
    return <div>Loading...</div>;
  }

  if (showModal) {
    document.body.style.overflow = "hidden";
  } else {
    document.body.style.overflow = "auto";
  }

  console.log(userData);
  console.log("커뮤니티 아이디 타입", communityId);
  console.log(typeof communityId);
  console.log(allCommentData);
  return (
    <All>
      {allCommentData?.map((CommentData) => {
        if (!CommentData) return null;
        return (
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
              {CommentData?.authorid === userData?.data.data_body.id && (
                <DeleteButton
                  onClick={() => handleDeleteClick(CommentData?.id)}
                >
                  댓글 삭제
                </DeleteButton>
              )}
            </CommentTime>
          </CommentContainer>
        );
      })}

      <CommentInputContainer as="form" onSubmit={handleSubmit}>
        <CommentInput
          type="text"
          placeholder="댓글을 입력하세요..."
          value={comment}
          onChange={(e) => setComment(e.target.value)}
        />
        <SubmitButton type="submit">등록</SubmitButton>
      </CommentInputContainer>

      {/* 삭제 모달 */}
      {showModal && (
        <ModalOverlay onClick={() => setShowModal(false)}>
          <ModalContent onClick={(e) => e.stopPropagation()}>
            <ModalText>정말로 삭제하시겠습니까?</ModalText>
            <DeleteButton onClick={confirmDelete}>확인</DeleteButton>
            <CloseButton onClick={() => setShowModal(false)}>취소</CloseButton>
          </ModalContent>
        </ModalOverlay>
      )}
    </All>
  );
};

export default CommunityComment;
