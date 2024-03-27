import styled from "styled-components";
import likeIcon from "../../assets/svg/like.svg";
import commentIcon from "../../assets/svg/classes.svg";
import { selectedTypeAtom } from "../../stores/community";
import pullright from "../../assets/svg/pullright.svg";
import { useAtom } from "jotai";

const posts = [
  {
    id: 1,
    title: "자유게시판이게 무슨 식물이야?",
    content: "마리가 풀 삼키려고 하는데 무슨 식물인지 모르겠어...",
    imageUrl: pullright,
    name: "작심삼일",
    time: 13,
    likes: 17,
    comments: 3,
    type: "자유게시판",
  },
  {
    id: 1,
    title: "꿀팁공유이게 무슨 식물이야?",
    content: "마리가 풀 삼키려고 하는데 무슨 식물인지 모르겠어...",
    imageUrl: pullright,
    name: "작심삼일",
    time: 13,
    likes: 17,
    comments: 3,
    type: "꿀팁공유",
  },
  {
    id: 1,
    title: "꿀팁공유이게 무슨 식물이야?",
    content: "마리가 풀 삼키려고 하는데 무슨 식물인지 모르겠어...",
    imageUrl: pullright,
    name: "작심삼일",
    time: 13,
    likes: 17,
    comments: 3,
    type: "꿀팁공유",
  },
  {
    id: 1,
    title: "꿀팁공유이게 무슨 식물이야?",
    content: "마리가 풀 삼키려고 하는데 무슨 식물인지 모르겠어...",
    imageUrl: pullright,
    name: "작심삼일",
    time: 13,
    likes: 17,
    comments: 3,
    type: "꿀팁공유",
  },
];

const CommunityItem = styled.div`
  background-color: white;
  border-radius: 16px;
  box-shadow: 0 2px 4px rgba(0, 0, 0, 0.1);
  margin: 0.5rem;
  display: flex;
  flex-direction: column;
  align-items: center;
  padding: 1rem;
`;

const PostHeader = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;

const PostTitle = styled.h2`
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.8rem;
  font-weight: bold;
  margin-bottom: 0.5rem;
`;

const PostContent = styled.p`
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.7rem;
  font-weight: 400;
  line-height: 1.2rem;
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
  font-family: "GamtanRoad Dotum TTF";
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
  display: flex;
  align-items: center;
`;

const Icon = styled.img`
  height: 1rem;
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
  margin-left: 1rem;
`;

const ContentImage = styled.div`
  display: flex;
`;

const NameTime = styled.div``;
const ContentTitle = styled.div``;
const CommunityAll = () => {
  const [selectedType] = useAtom(selectedTypeAtom);

  return (
    <div>
      {posts
        .filter((post) => post.type === selectedType)
        .map((post) => (
          <CommunityItem key={post.id}>
            <PostHeader>
              <ContentImage>
                <ContentTitle>
                  <PostTitle>{post.title}</PostTitle>
                  <PostContent>{post.content}</PostContent>
                </ContentTitle>
                <PostImage src={post.imageUrl} alt="Post image" />
              </ContentImage>
              <PostMeta>
                <UserMeta>
                  <NameTime>
                    <UserName>{post.name} - </UserName>
                    <PostTime>{post.time}분 전</PostTime>
                  </NameTime>
                </UserMeta>
                <InteractionIcons>
                  <Icon src={likeIcon} alt="Likes" />
                  <LikeCommentCount>{post.likes}</LikeCommentCount>
                  <Icon src={commentIcon} alt="Comments" />
                  <LikeCommentCount>{post.comments}</LikeCommentCount>
                </InteractionIcons>
              </PostMeta>
            </PostHeader>
          </CommunityItem>
        ))}
    </div>
  );
};

export default CommunityAll;
