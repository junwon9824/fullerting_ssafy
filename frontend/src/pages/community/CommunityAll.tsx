import styled from "styled-components";
import likeIcon from "../../assets/svg/greenheart.svg";
import commentIcon from "../../assets/svg/Speech Bubble.svg";
import { selectedTypeAtom } from "../../stores/community";
import { useNavigate } from "react-router-dom";
import { useAtom } from "jotai";
import { getallcommunities } from "../../apis/CommunityApi";
import { useState, useEffect } from "react";

interface Post {
  id: number;
  title: string;
  content: string;
  imgurls: string;
  name: string;
  time: number;
  likes: number;
  commentsize: number;
  love: number;
  authornickname: string;
  type: string;
}

const CommunityItem = styled.div`
  box-shadow: 0 1px 0 rgba(0, 0, 0, 0.1);
  font-family: "GamtanRoad Dotum TTF";
  margin: 0.5rem;
  display: flex;
  padding: 1.25rem 0rem;
  flex-direction: column;
  align-items: center;
  gap: 0.8125rem;
`;

const PostHeader = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
`;

const PostTitle = styled.h2`
  font-size: 1rem;
  font-style: normal;
  font-weight: bold;
  line-height: normal;
`;

const PostContent = styled.p`
  font-size: 0.875rem;
  font-style: normal;
  font-weight: 400;
  line-height: 1rem;
`;

const PostMeta = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
  margin-bottom: 0.5rem;
`;

const UserMeta = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;

const UserName = styled.span`
  color: var(--gray1, #8c8c8c);
  font-size: 0.7rem;
`;

const PostTime = styled.span`
  color: var(--gray1, #8c8c8c);
  font-family: "Noto Sans KR";
  font-size: 0.6rem;
  font-weight: 700;
  line-height: 1rem;
`;

const InteractionIcons = styled.div`
  margin-top: 1rem;
  display: flex;
  align-items: center;
  gap: 0.2rem;
`;

const Icon = styled.img`
  height: 0.7rem;
  margin: 0 0.25rem;
`;

const LikeCommentCount = styled.span`
  font-size: 0.75rem;
  color: #999;
`;

const PostImage = styled.img`
  width: 5rem;
  object-fit: cover;
  border-radius: 10px;
  margin-bottom: 1rem;
`;

const ContentImage = styled.div`
  display: flex;
  justify-content: space-between;
  gap: 7em;
`;

const ImgCon = styled.div``;

const NameTime = styled.div`
  margin-top: 1rem;
`;
const ContentTitle = styled.div``;

const CommunityAll = () => {
  const [posts, setPosts] = useState<Post[]>([]);
  const [selectedType] = useAtom(selectedTypeAtom);

  useEffect(() => {
    const fetchData = async () => {
      try {
        const data = await getallcommunities();
        console.log(data);
        setPosts(data);
      } catch (error) {
        console.error("Error occurred while fetching data: ", error);
      }
    };

    fetchData();
  }, []);

  const navigate = useNavigate();

  const handlePostClick = (id: number) => {
    navigate(`/community/${id}`);
  };

  const getTimeDifference = (minutes: number) => {
    if (minutes < 1) {
      return `방금 전`;
    } else if (minutes < 60) {
      return `${minutes}분 전`;
    } else if (minutes < 1440) {
      const hours = Math.floor(minutes / 60);
      return `${hours}시간 전`;
    } else {
      const days = Math.floor(minutes / 1440);
      return `${days}일 전`;
    }
  };

  return (
    <div>
      {posts
        .filter((post) => post.type === selectedType)
        .map((post) => (
          <CommunityItem key={post.id} onClick={() => handlePostClick(post.id)}>
            <PostHeader>
              <ContentImage>
                <ContentTitle>
                  <PostTitle>{post.title}</PostTitle>
                  <PostContent>{post.content}</PostContent>
                </ContentTitle>
                <ImgCon>
                  <PostImage src={post.imgurls} alt="Post image" />
                </ImgCon>
              </ContentImage>
              <PostMeta>
                <UserMeta>
                  <NameTime>
                    <UserName>{post.authornickname} - </UserName>
                    <PostTime>{getTimeDifference(post.time)}</PostTime>
                  </NameTime>
                </UserMeta>
                <InteractionIcons>
                  <Icon src={likeIcon} alt="Likes" />
                  <LikeCommentCount>{post.love}</LikeCommentCount>
                  <Icon src={commentIcon} alt="Comments" />
                  <LikeCommentCount>{post.commentsize}</LikeCommentCount>
                </InteractionIcons>
              </PostMeta>
            </PostHeader>
          </CommunityItem>
        ))}
    </div>
  );
};

export default CommunityAll;
