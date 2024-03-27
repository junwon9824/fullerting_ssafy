import styled from "styled-components";
import { TopBar } from "../common/Navigator/navigator";
import Coli from "/src/assets/images/브로콜리.png";
import { LayoutInnerBox, LayoutMainBox } from "../common/Layout/Box";
import { useNavigate } from "react-router-dom";
import { useState } from "react";
import { useQuery } from "@tanstack/react-query";
import { getTradeDetail, useLike } from "../../apis/TradeApi";
import { useParams } from "react-router-dom";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/navigation";
import Airplane from "/src/assets/images/airplane.png";
interface ImageResponse {
  imgStoreUrl: string;
}

interface SituationResponse {
  border: string;
  color: string;
}
const ImgBox = styled.img`
  width: 100%;
  height: 15.5625rem;
  object-fit: cover;
`;

const SwiperContainer = styled.div`
  width: 100%;
  height: 15.5625rem;
`;
const Title = styled.div`
  justify-content: flex-start;
  color: #000;
  width: 100%;
  height: 2.0625rem;
  font-size: 1.25rem;
  font-weight: bold;
  display: flex;
  align-items: center;
`;

const SituationBox = styled.div`
  width: 100%;
  height: auto;
  align-items: center;
  justify-content: flex-start;
  gap: 1rem;
  padding-right: 1rem;
  display: flex;
  flex-direction: row;
`;
const Situation = styled.div<SituationResponse>`
  text-align: center;
  display: flex;
  width: 3.5rem;
  height: 1.625rem;
  border-radius: 0.625rem;
  border: ${(props) => `${props.border}`};
  color: ${(props) => `${props.color}`};
  align-items: center;
  font-size: 0.75rem;
  font-weight: bold;
  justify-content: center;
`;
const TextStyle = styled.div`
  align-items: center;
  display: flex;
  color: #000;
  font-size: 0.8125rem;
  font-weight: 400;
`;
const DealList = styled.div`
  width: 100%;
  justify-content: space-between;
  height: 2.0625rem;
  border-radius: 0.625rem;
  background: var(--sub1, #e5f9db);
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-left: 0;
`;
const ProfileBox = styled.div`
  width: auto;
  justify-content: flex-start;
  display: flex;
  flex-direction: row;
  gap: 1.5rem;
  align-items: center;
  color: #000;
  font-size: 0.8125rem;
  font-weight: 400;
`;
const PhotoBox = styled.img`
  width: 2.0625rem;
  height: 2.0625rem;
  border-radius: 50%;
  object-fit: cover;
`;
const DealBox = styled.div`
  width: 100%;
  height: 14rem;
  overflow-y: scroll;
  flex-direction: column;
  gap: 1rem;
  display: flex;
`;
const BottomText = styled.div`
  color: var(--gray1, #8c8c8c);
  text-align: center;
  font-size: 0.75rem;
  font-style: normal;
  font-weight: 400;
`;
const TradeSellerDetail = () => {
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

  return (
    <>
      <TopBar title="작물거래" showBack={true} showEdit={true} />
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
          <Title>{data?.exArticleResponse.exArticleTitle}</Title>
          <SituationBox>
            <Situation border="2px solid var(--sub3, #FFBFBF)" color="#FFBFBF;">
              최고가
            </Situation>
            <TextStyle>800원</TextStyle>
            <Situation border="2px solid var(--sub0, #A0D8B3)" color="#A0D8B3;">
              참여자
            </Situation>
            <TextStyle>3명</TextStyle>
          </SituationBox>
          <DealBox>
            <DealList>
              <ProfileBox>
                <PhotoBox src={Coli} alt="coli" />
                사랑으로 키운게 느껴져요 300원 어떠심?
              </ProfileBox>
            </DealList>
            <DealList>
              <ProfileBox>
                <PhotoBox src={Coli} alt="coli" />
                사랑으로 키운게 느껴져요 300원 어떠심?
              </ProfileBox>
            </DealList>
          </DealBox>
          <BottomText>
            마음에 드는 제안을 선택하고 채팅을 보내보세요!
          </BottomText>
        </LayoutInnerBox>
      </LayoutMainBox>
    </>
  );
};

export default TradeSellerDetail;
