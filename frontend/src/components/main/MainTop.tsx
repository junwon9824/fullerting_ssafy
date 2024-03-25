import { useNavigate } from "react-router-dom";
import { useQuery } from "@tanstack/react-query";
import { getCropList } from "../../apis/DiaryApi";
import styled from "styled-components";
const MainContainer = styled.div`
  justify-content: center;
  flex-direction: column;
  height: 12.2rem;
  background-color: #a0d8b3;
  padding: 0.5rem 0.8rem;
`;
const TextBox = styled.div`
  border-radius: 0.78125rem;
  background: var(--sub1, #e5f9db);
  font-size: 0.75rem;
  width: 12rem;
  height: 1.2rem;
  display: flex;
  flex-direction: row;
  font-weight: 600;
  justify-content: center;
  align-items: center;
  text-align: center;
  margin: 0.3rem 0 0 0;
`;

const TextContent = styled.div`
  font-family: "GamtanRoad Dotum TTF";
  display: flex;
  flex-direction: row;
`;
const BasicText = styled.div`
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.6rem;
  margin: 0.2rem;
  font-weight: 600;
`;

const Content = styled.div`
  display: flex;
  justify-content: flex-start;
  flex-direction: column;
`;
const MainText = styled.div`
  color: #fffefe;
  font-size: 1.7rem;
  font-style: normal;
  font-weight: 600;
  line-height: normal;
  ${({ theme }) => theme.fonts.logo}
`;

const DiaryBox = styled.button`
  display: flex;
  padding: 0.5rem 1rem;
  justify-content: space-between;
  align-items: center;
  text-align: center;
  margin: 0.7rem 0;
  width: 20rem;
  height: 6.3rem;
  border-radius: 0.9375rem;
  background: rgba(255, 255, 255, 0.26);
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
`;
const Diary = styled.button`
  width: 6.4rem;
  height: 1.74375rem;
  border-radius: 0.5rem;
  background: var(--sub0, #a0d8b3);
  color: #fff;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.625rem;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
`;
const CropImage = styled.img`
  border-radius: 50rem;
  width: 5rem;
  height: 5rem;
  border: 2px solid var(--point, #3d0c11);
  opacity: 0.9;
  background: url(<path-to-image>) lightgray 50% / cover no-repeat;
`;

const DiaryText = styled.span`
  font-size: 0.8rem;
  font-weight: bold;
  color: #ffffff;
`;

const DDayCounter = styled.div`
  color: #ffffff;
  padding: 0.4rem 0;
  font-weight: bold;
  font-size: 1.5rem;
  width: 3rem;
`;

const SliderContainer = styled.div`
  display: flex;
  overflow-x: auto;
  scroll-snap-type: x mandatory;
  scrollbar-width: none;
  &::-webkit-scrollbar {
    display: none;
  }
`;

const DiarySlider = styled.div`
  display: flex;
  scroll-snap-type: x mandatory;
  gap: 1rem;
  & > div {
    scroll-snap-align: start;
    flex: 0 0 auto;
  }
`;

const Maintop = () => {
  const accessToken = sessionStorage.getItem("accessToken");
  const navigate = useNavigate();

  const { isLoading, data: cropList } = useQuery({
    queryKey: ["cropList"],
    queryFn: accessToken ? () => getCropList(accessToken) : undefined,
  });

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (!cropList) {
    return <div>작물을 등록해주세요</div>;
  }
  console.log("작물데이터", cropList);
  const goToDiary = () => {
    navigate("/diary");
  };

  const goToLogin = () => {
    navigate("/login");
  };

  return (
    <MainContainer>
      <MainText>풀러팅</MainText>
      <TextBox>"{cropList?.length || 0}개의 작물을 가꾸고 계시군요"</TextBox>
      {accessToken ? (
        <SliderContainer>
          <DiarySlider>
            {cropList?.map((crop, index) => (
              <DiaryBox key={crop.packDiaryId} onClick={goToDiary}>
                <Content>
                  <TextContent>
                    <DiaryText>{cropList[index].packDiaryTitle}</DiaryText>
                    <BasicText>와 함께한 시간</BasicText>
                  </TextContent>
                  <DDayCounter>더미</DDayCounter>
                  <Diary>일지 작성하러 가기</Diary>
                </Content>
                <CropImage
                  src={cropList[index].cropTypeImgUrl}
                  alt="Crop image"
                />
              </DiaryBox>
            ))}
          </DiarySlider>
        </SliderContainer>
      ) : (
        <DiaryBox onClick={goToLogin}>
          <Content>
            <TextContent>
              <DiaryText>로그인을 해주세요</DiaryText>
            </TextContent>
          </Content>
        </DiaryBox>
      )}
    </MainContainer>
  );
};

export default Maintop;
