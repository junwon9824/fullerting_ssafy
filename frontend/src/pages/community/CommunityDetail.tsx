import React from "react";
import { useParams } from "react-router-dom";
import styled from "styled-components";
import bell from "../../assets/svg/bell-ring.svg";

const dummys = [
  {
    id: "18",
    title: "무순",
    img: bell,
    nick: "kim",
    grade: "새싹",
    time: "22:33",
    content: "무슨 말이야",
  },
];

const Container = styled.div`
  padding: 20px;
`;

const Title = styled.h1`
  font-size: 1.5em;
  color: #333;
`;

const PostDetail = styled.div`
  margin-top: 20px;
  background-color: #f8f8f8;
  padding: 10px;
  border-radius: 8px;
`;

const PostTitle = styled.h2`
  font-size: 1.2em;
  margin-bottom: 5px;
`;

const PostImg = styled.img`
  width: 100%;
  margin-top: 10px;
`;

const PostInfo = styled.p`
  margin: 5px 0;
  color: #666;
`;

const CommunityDetail = () => {
  const { id } = useParams();
  const post = dummys.find((post) => post.id === id);

  return (
    <Container>
      <Title>커뮤니티 세부사항</Title>
      <p>게시물 ID: {id}</p>
      {post ? (
        <PostDetail>
          <PostTitle>{post.title}</PostTitle>
          <PostInfo>닉네임: {post.nick}</PostInfo>
          <PostInfo>등급: {post.grade}</PostInfo>
          <PostInfo>시간: {post.time}</PostInfo>
          <PostImg src={post.img} alt="bell-icon" />
          <PostInfo>내용: {post.content}</PostInfo>
        </PostDetail>
      ) : (
        <p>해당 게시물을 찾을 수 없습니다.</p>
      )}
    </Container>
  );
};

export default CommunityDetail;
