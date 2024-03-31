import React from "react";
import { useParams } from "react-router-dom";
import posts from "./posts"; // 더미 데이터가 들어 있는 파일

const MainTipDetail = () => {
  const { id } = useParams();
  const post = posts.find((post) => post.id.toString() === id);

  if (!post) return <div>포스트를 찾을 수 없습니다.</div>;

  return (
    <div>
      <h1>{post.title}</h1>
      <p>{post.content}</p>
    </div>
  );
};

export default MainTipDetail;
