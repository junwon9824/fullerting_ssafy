import styled from "styled-components";
import Tomato from "/src/assets/images/토마토.png";
import Location from "/src/assets/svg/location.svg";
import Like from "/src/assets/svg/notlike.svg";
import GrayHeart from "/src/assets/svg/grayheart.svg";
import RedLike from "/src/assets/svg/like.svg";
import { useState } from "react";
import Write from "/src/assets/images/글쓰기.png";
import { useNavigate } from "react-router-dom";

interface ClickLike {
  onClick: () => void;
}
interface StateGap {
  gap?: number;
  fontSize?: number;
  color?: string;
  justifyContent?: string;
}
interface Icon {
  width?: number;
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
const State = styled.div<StateGap>`
  width: 100%;
  height: auto;
  gap: ${(props) => `${props.gap}rem`};
  font-size: ${(props) => `${props.fontSize}rem`};
  font-style: normal;
  font-weight: bold;
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-top: 0.44rem;
  justify-content: ${(props) => `${props.justifyContent}`};
  color: ${(props) => `${props.color}`};
`;

const ExplainBox = styled.div`
  width: auto;
  height: auto;
  justify-content: flex-end;
  align-items: center;
  display: flex;
  flex-direction: row;
  gap: 0.25rem;
`;
const HeartBox = styled.div`
  width: auto;
  height: auto;
  justify-content: space-between;
  display: flex;
  flex-direction: row;
  left: 0;
  gap: 0.19rem;
  align-items: center;
`;
const WriteBox = styled.img`
  position: fixed;
  right: 1.19rem;
  bottom: 4.75rem;
`;
const stateArray = ["제안", "작물일지"];

const Post = (props: Icon) => {
  const [like, setLike] = useState([false, false, false]);
  const [word, setword] = useState<number>(12);
  const navigate = useNavigate();
  const handelWriteClick = () => {
    navigate("/trade/post");
  };
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
          <State gap={0.44} fontSize={1}>
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

          <State
            // gap={3.75}
            fontSize={0.5625}
            color="#BEBEBE"
            justifyContent="space-between"
          >
            <HeartBox>
              <img
                src={GrayHeart}
                alt="gray"
                style={{ marginRight: "0.19rem" }}
              />
              {word}
            </HeartBox>
            <ExplainBox>
              {stateArray.map((text, index) => (
                <StateIcon
                  width={text === "작물일지" ? 2.5625 : 1.5}
                  height={0.9375}
                  backgroundColor={text === "작물일지" ? "#A0D8B3" : "#F4F4F4"}
                  color="#8c8c8c"
                >
                  {text}
                </StateIcon>
              ))}
            </ExplainBox>
          </State>
        </PostBox>
      ))}
      <WriteBox src={Write} onClick={handelWriteClick} />
    </ContentBox>
  );
};

export default Post;
