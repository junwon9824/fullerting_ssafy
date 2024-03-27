import React from "react";
import styled from "styled-components";
import likeIcon from "../../assets/svg/like.svg"; // 실제 경로로 대체해야 합니다.
import commentIcon from "../../assets/svg/classes.svg"; // 실제 경로로 대체해야 합니다.

const posts = [
  {
    id: 1,
    title: "이게 무슨 식물이야?",
    content: "마리가 풀 삼키려고 하는데 무슨 식물인지 모르겠어...",
    imageUrl: "/path/to/image",
    name: "작심삼일",
    time: 13,
    likes: 17,
    comments: 3,
  },
  {
    id: 1,
    title: "이게 무슨 식물이야?",
    content: "마리가 풀 삼키려고 하는데 무슨 식물인지 모르겠어...",
    imageUrl: "/path/to/image",
    name: "작심삼일",
    time: 13,
    likes: 17,
    comments: 3,
  },
  {
    id: 1,
    title: "이게 무슨 식물이야?",
    content: "마리가 풀 삼키려고 하는데 무슨 식물인지 모르겠어...",
    imageUrl: "/path/to/image",
    name: "작심삼일",
    time: 13,
    likes: 17,
    comments: 3,
  },
  {
    id: 1,
    title: "이게 무슨 식물이야?",
    content: "마리가 풀 삼키려고 하는데 무슨 식물인지 모르겠어...",
    imageUrl: "/path/to/image",
    name: "작심삼일",
    time: 13,
    likes: 17,
    comments: 3,
  },
];

const CommunityItem = styled.div`
  background-color: white;
  border-radius: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin: 0.5rem;
  padding: 1rem;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;

const PostHeader = styled.div`
  display: flex;
  justify-content: space-between;
  width: 100%;
  margin-bottom: 0.5rem;
`;

const PostDetails = styled.div`
  flex-grow: 1;
`;

const PostTitle = styled.h2`
  font-size: 1rem;
  font-weight: 700;
  margin: 0;
`;

const PostContent = styled.p`
  font-size: 0.875rem;
  color: #666;
  margin: 0.5rem 0;
`;

const PostNameAndTime = styled.div`
  display: flex;
  align-items: center;
  font-size: 0.75rem;
  color: #999;
`;

const UserName = styled.span`
  margin-right: 0.5rem;
`;

const PostTime = styled.span``;

const PostImage = styled.img`
  height: 80px;
  width: 80px;
  border-radius: 10px;
  object-fit: cover;
  align-self: center;
`;

const LikeAndComment = styled.div`
  display: flex;
  align-items: center;
  font-size: 0.75rem;
  color: #999;
  margin-top: 0.5rem;
`;

const LikeIcon = styled.img`
  height: 1rem;
  width: auto;
  margin-right: 0.25rem;
`;

const CommentIcon = styled(LikeIcon)``;

const CommunityAll = () => {
  return (
    <div>
      {posts.map((post) => (
        <CommunityItem key={post.id}>
          <PostHeader>
            <PostDetails>
              <PostTitle>{post.title}</PostTitle>
              <PostContent>{post.content}</PostContent>
              <PostNameAndTime>
                <UserName>{post.name}</UserName>
                <PostTime>{post.time}분 전</PostTime>
              </PostNameAndTime>
            </PostDetails>
            <PostImage src={post.imageUrl} alt="Post" />
          </PostHeader>
          <LikeAndComment>
            <LikeIcon src={likeIcon} alt="Likes" />
            {post.likes}
            <CommentIcon src={commentIcon} alt="Comments" />
            {post.comments}
          </LikeAndComment>
        </CommunityItem>
      ))}
    </div>
  );
};

export default CommunityAll;
