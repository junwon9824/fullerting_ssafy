import styled from "styled-components";
import { TopBar, TradeTopBar } from "../common/Navigator/navigator";
import Coli from "/src/assets/images/브로콜리.png";
import { LayoutInnerBox, LayoutMainBox } from "../common/Layout/Box";
import { BottomButton } from "../common/Button/LargeButton";
import { useNavigate } from "react-router-dom";
import SvgProfile from "/src/assets/images/김진명프로필사진.png";
import Sprout from "/src/assets/svg/classes.svg";
import NotLike from "/src/assets/svg/notlike.svg";
import Like from "/src/assets/svg/like.svg";
import { useState } from "react";
import Tree from "/src/assets/svg/diarytree.svg";
import { useQuery } from "@tanstack/react-query";
import { getTradeDetail, useLike } from "../../apis/TradeApi";
import { useParams } from "react-router-dom";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/navigation";
import SwiperCore, { Navigation, Pagination } from "swiper/modules";
import { userCheck } from "../../apis/UserApi";
import TradePostLayout from "../common/Layout/TradePostLayout";
interface ImageResponse {
  imgStoreUrl: string;
}

const ImgBox = styled.img`
  width: 100%;
  height: 15.5625rem;
  object-fit: cover;
`;
const InfoBox = styled.div`
  width: 100%;
  height: 2.125rem;
  display: flex;
  justify-content: space-between;
  gap: 8.81rem;
  position: relative;
`;
const Profile = styled.div`
  width: 5.875rem;
  display: flex;
  flex-direction: row;
  align-items: center;
  gap: 0.2rem;
`;
const Name = styled.div`
  width: auto;
  display: flex;
  flex-direction: column;
  gap: 0.2rem;
`;
const NameText = styled.text`
  font-size: 0.8125rem;
  font-style: normal;
  font-weight: bold;
  color: #000000;
`;
const ClassesText = styled.div`
  color: #4f4f4f;
  display: flex;
  font-size: 0.6875rem;
  font-weight: 400;
  align-items: center;
  gap: 0.2rem;
`;
const Date = styled.div`
  width: auto;
  position: absolute;
  right: 0;
  bottom: 0;
  color: "#8C8C8C";
  font-size: 0.6875rem;
  font-weight: "400";
`;
const Title = styled.div`
  width: 100%;
  display: flex;
  justify-content: space-between;
  color: #000;
  font-size: 1.1rem;
  font-weight: 400;
`;
const TitleBox = styled.div`
  padding-top: 2.19rem;
  width: 100%;
  display: flex;
  flex-direction: column;
  gap: 0.8rem;
`;
const Price = styled.text`
  color: #000;
  font-size: 1.25rem;
  font-weight: bold;
`;
const DiaryBox = styled.div`
  width: 100%;
  gap: 0.3rem;
  align-items: center;
  display: flex;
  flex-direction: row;
`;
const NavigateText = styled.div`
  color: var(--gray1, #8c8c8c);
  font-size: 0.75rem;
  font-weight: bold;
`;
const ExplainText = styled.div`
  color: #000;
  font-size: 0.875rem;
  font-weight: 400;
  width: 100%;
  height: auto;
  line-height: 1.375rem;
`;
const SwiperContainer = styled.div`
  width: 100%;
  height: 15.5625rem;
`;
const Thumbnail = styled.img`
  width: 1.875rem;
  height: 1.875rem;
`;

const TradeGeneralDetail = () => {
  const navigate = useNavigate();
  const BtnClick = () => {
    navigate("/trade/chat");
  };
  const [like, setLike] = useState<boolean>(false);
  const handleLike = () => {
    setLike(!like);
  };

  const { mutate: handleLikeClick } = useLike();
  const { postId } = useParams<{ postId?: string }>();
  const postNumber = Number(postId);
  const accessToken = sessionStorage.getItem("accessToken");
  const { isLoading, data, error } = useQuery({
    queryKey: ["tradeDetail", postNumber],
    queryFn: accessToken
      ? () => getTradeDetail(accessToken, postNumber)
      : undefined,
  });
  const {
    isLoading: isLoadingUserDetail,
    data: userData,
    error: userDetailError,
  } = useQuery({
    queryKey: ["userDetail"],
    queryFn: accessToken ? () => userCheck(accessToken) : undefined,
  });
  const userId = userData?.id;

  const DiaryId = data?.packDiaryResponse?.packDiaryId;
  const handleDiary = (DiaryId: number) => {
    navigate(`/diary/${DiaryId}`);
    console.log("나 눌리고 있어!!!", 111);
  };
  const formatDateAndTime = (dateString: string) => {
    if (!dateString) return "";
    const [date, time] = dateString.split("T");
    const [hours, minutes, seconds] = time.split(":");
    return `${date} ${hours}:${minutes}:${seconds}`;
  };

  return (
    <>
      <TradeTopBar title="작물거래" showBack={true} showEdit={true} />
      <LayoutMainBox>
        <SwiperContainer>
          <Swiper
            slidesPerView={1}
            pagination={true}
            onSlideChange={() => console.log("slide change")}
            onSwiper={(swiper) => console.log(swiper)}
          >
            {data?.imageResponses.map((image: ImageResponse, index: number) => (
              <SwiperSlide key={index}>
                <ImgBox src={image.imgStoreUrl} alt={"img"} />
              </SwiperSlide>
            ))}
          </Swiper>
        </SwiperContainer>
        <LayoutInnerBox>
          <InfoBox>
            <Profile>
              <Thumbnail src={data?.userResponse.thumbnail} alt="profile" />
              <Name>
                <NameText>{data?.userResponse.nickname}</NameText>
                <ClassesText>
                  {data?.userResponse.rank}
                  {/* <img src={Sprout} alt="Sprout" /> */}
                </ClassesText>
              </Name>
            </Profile>
            <Date>{formatDateAndTime(data?.exArticleResponse.time)}</Date>
          </InfoBox>
          <TitleBox>
            <Title>
              {data?.exArticleResponse.exArticleTitle}
              <img
                src={data?.favoriteResponse.islike === true ? Like : NotLike}
                alt="like"
                onClick={() => {
                  handleLikeClick(data?.exArticleResponse.exArticleId);
                }}
              />
            </Title>
            <Price>{data?.transResponse.price}원</Price>
            <DiaryBox>
              <img src={Tree} alt="tree" />
              <NavigateText
                onClick={() => {
                  DiaryId ? handleDiary(Number(DiaryId)) : null;
                }}
              >
                작물일지 이동하기
              </NavigateText>
            </DiaryBox>
            <ExplainText>
              심우석의 머리를 브로콜리에 비유하는 것은 그의 독특하고 특이한 헤어
              스타일을 묘사하기 위한 창의적인 방법입니다. 이 비유는 특히 그의
              머리카락이 풍성하고 볼륨감이 많으며, 위로 솟아 오른 모양이 마치
              브로콜리의 녹색 송이와 유사하다는 점에서 온 것일 수 있습니다.
              브로콜리의 작은 꽃송이들이 모여 있는 모양은, 심우석의 머리카락이
              여러 방향으로 풍성하게 서 있는 것과 비슷하다고 할 수 있습니다.
            </ExplainText>
          </TitleBox>
        </LayoutInnerBox>
        <BottomButton text="채팅하기" onClick={BtnClick} />
      </LayoutMainBox>
    </>
  );
};

export default TradeGeneralDetail;
