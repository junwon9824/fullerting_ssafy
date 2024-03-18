import styled from "styled-components";
import Tomato from "/src/assets/images/토마토.png";
import Location from "/src/assets/svg/location.svg";
import Like from "/src/assets/svg/notlike.svg";
import LikeCircle from "/src/assets/images/좋아요동그라미.png";
import RedLike from "/src/assets/svg/like.svg";
import { useState } from "react";

interface ClickLike {
  onClick: () => void;
}
interface LocationText {
  text: string;
}
interface Icon {
  width: number;
  height: number;
  backgroundColor: string;
  color: string;
  text?: string;
}
const ImgBox = styled.div`
  width: 9rem;
  height: 9rem;
  border-radius: 0.9375rem;
  top: 0;
  left: 0;
  z-index: 2;
  position: relative;
  overflow: hidden;

  cursor: pointer;
`;
const ContentBox = styled.div`
  width: 100%;
  /* height: auto; */
  margin-bottom: 1.38rem;
  flex-wrap: wrap;
  overflow-y: auto;
  display: flex;
  justify-content: space-between;
  position: relative;
  z-index: 1;
`;

const LikeBox = styled.div<ClickLike>`
  width: 1.25rem;
  height: 1.25rem;
  display: flex;
  position: absolute;
  bottom: 0.38rem;
  right: 0.38rem;
  z-index: 3;
`;

const StyledImg = styled.img`
  width: 100%;
  height: 100%;
  object-fit: cover; /* 이미지가 컨테이너를 채우도록 설정 */
`;

const Town = styled.div`
  width: auto;
  gap: 0.12rem;
  margin-top: 0.62rem;
  color: var(--gray1, #8c8c8c);
  font-size: 0.625rem;
  font-style: normal;
  font-weight: 400;
  display: flex;
  align-items: center;
`;
const PostBox = styled.div`
  width: 9rem;
  height: auto;
  margin-bottom: 1.38rem;
`;
const Title = styled.div`
  width: auto;
  margin-top: 0.5rem;
  align-items: center;
  font-size: 0.75rem;
  font-style: normal;
  font-weight: 400;
`;
const CostBox = styled.div`
  gap: 0.44rem;
  width: auto;
  margin-top: 0.37rem;
  font-size: 1rem;
  font-style: normal;
  font-weight: bold;
`;
const StateIcon = styled.div<Icon & { children?: React.ReactNode }>`
  width: ${(props) => `${props.width}rem`};
  height: ${(props) => `${props.height}rem`};
  border-radius: 0.3125rem;
  background: ${(props) => props.backgroundColor};
  color: ${(props) => props.color};
  display: flex;
  justify-content: center;
  align-items: center;
  font-size: 0.5625rem; /* 텍스트 크기 */
`;
const State = styled.div`
  width: auto;
  height: auto;
  gap: 0.44rem;
  font-size: 0.5625rem;
  font-style: normal;
  font-weight: bold;
  text-align: center;
  display: flex;
  align-items: center;
  margin-top: 0.44rem;
`;
const Post = (props: Icon) => {
  const [like, setLike] = useState([false, false, false]);
  const handleLike = (index: number) => {
    setLike(like.map((like, i) => (i === index ? !like : like)));
  };
  return (
    <ContentBox>
      {like.map((like, index) => (
        <PostBox>
          <ImgBox key={index}>
            <StyledImg src={Tomato} alt="tomato" />
            <LikeBox onClick={() => handleLike(index)}>
              <StyledImg src={like ? RedLike : Like} alt="like button" />
            </LikeBox>
          </ImgBox>
          <Town>
            <img src={Location} alt="location" />
            오선동
          </Town>
          <Title>토마토 500g 판매</Title>
          <State>
            <StateIcon
              width={1.5}
              height={0.9375}
              backgroundColor="#A0D8B3"
              color="#ffffff"
            >
              현재
            </StateIcon>
            300원
          </State>
        </PostBox>
      ))}
    </ContentBox>
  );
};

export default Post;
