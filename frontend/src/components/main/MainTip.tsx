import styled from "styled-components";
import pullright from "/src/assets/svg/pullright.svg";
import { useNavigate } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { getallcommunities } from "../../apis/CommunityApi";

interface Post {
  id: string;
  title: string;
  content: string;
}

const MainBox = styled.div`
  flex-direction: column;
  align-items: end;
  justify-content: center;
`;
const LogoAndTextContainer = styled.div`
  display: flex;
  justify-content: end;
  align-items: center;
  gap: 0.5rem;
`;

const Character = styled.button`
  display: flex;
  align-items: center;
  margin-right: 0.5rem;
`;

const LogoContent = styled.div`
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.75rem;
  font-style: normal;
  font-weight: 600;
  line-height: 1.125rem;
`;
const LogoText = styled.div`
  color: var(--sub0, #a0d8b3);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 1.2rem;
  font-style: normal;
  font-weight: 800;
  line-height: 1.125rem;
  margin-top: 0.5rem;
`;

const SliderContainer = styled.div`
  overflow-x: auto;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const DiarySlider = styled.div`
  display: flex;
  gap: 0.3rem;
`;

const DiaryBox = styled.button`
  padding: 0.5rem 1rem;
  margin: 0.9rem 0 1rem 0.7rem;
  width: 9rem;
  height: 5.2rem;
  border-radius: 0.9375rem;
  background: rgba(229, 249, 219, 0.37);
  box-shadow: 0px 1px 1px 1px rgba(161, 161, 161, 0.1);
  justify-content: space-between;
  cursor: pointer;
  transition: transform 0.3s ease;

  &:hover {
    transform: translateY(-5px);
  }
`;

const TextContent = styled.div`
  font-family: "GamtanRoad Dotum TTF";
  width: 7rem;
`;

const DiaryText = styled.div`
  color: #000000;
  font-size: 0.8rem;
  font-weight: bold;
  white-space: nowrap;
  overflow: hidden;
  text-overflow: ellipsis;
`;

const BasicText = styled.div`
  color: #000;
  font-size: 0.6rem;
  margin: 0.3rem;
  line-height: 1.2;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
`;

const ExchangeBox = styled.div`
  height: 5.2rem;
  display: flex;
  padding: 0.4375rem 0.5rem;
  width: 18rem;
  border-radius: 0.9375rem 0rem 0.9375rem 0.9375rem;
  border: 1.5px solid var(--sub0, #a0d8b3);
`;

const TokenBox = styled.div`
  font-family: "GamtanRoad Dotum TTF";
  display: flex;
  align-items: center;
  font-size: 0.9rem;
  margin-left: 3rem;
  font-weight: bold;
  color: #a0d8b3;
`;

const MainTip = () => {
  const accessToken = sessionStorage.getItem("accessToken");
  const navigate = useNavigate();

  const { data: posts } = useQuery<Post[]>({
    queryKey: ["communityList"],
    queryFn: getallcommunities,
  });
  const goToLogin = () => {
    navigate("/login");
  };
  const goCommunity = () => {
    console.log("comm");
    navigate("community");
  };
  return (
    <MainBox>
      <LogoAndTextContainer>
        <LogoContent>
          작물 꿀팁을 공유해보세요
          <LogoText onClick={goCommunity}>커뮤니티 바로가기</LogoText>
        </LogoContent>
        <Character>
          <img src={pullright} alt="" />
        </Character>
      </LogoAndTextContainer>

      {accessToken ? ( // 로그인 상태일 때만 SliderContainer 렌더링
        <SliderContainer onClick={goCommunity}>
          <DiarySlider>
            {posts?.map((post) => (
              <DiaryBox key={post.id}>
                <TextContent>
                  <DiaryText>{post.title}</DiaryText>
                  <BasicText>{post.content}</BasicText>
                </TextContent>
              </DiaryBox>
            ))}
          </DiarySlider>
        </SliderContainer>
      ) : (
        <ExchangeBox onClick={goToLogin}>
          <TokenBox>커뮤니티를 작성 해주세요</TokenBox>
        </ExchangeBox>
      )}
    </MainBox>
  );
};

export default MainTip;
