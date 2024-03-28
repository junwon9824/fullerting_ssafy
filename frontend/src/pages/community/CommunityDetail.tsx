import React from "react";
import { useParams } from "react-router-dom";

const CommunityDetail = () => {
  const { id } = useParams();

  return (
    <>
      <h1>커뮤니티 세부사항</h1>
      <p>게시물 ID: {id}</p>
    </>
  );
};

export default CommunityDetail;
