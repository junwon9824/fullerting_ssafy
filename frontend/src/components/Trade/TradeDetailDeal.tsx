import styled from "styled-components";
import { TopBar } from "../common/Navigator/navigator";
import Coli from "/src/assets/images/브로콜리.png";
import { LayoutInnerBox, LayoutMainBox } from "../common/Layout/Box";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import {
  createChatRoom,
  createDealChatRoom,
  getDealList,
  getTradeDetail,
  useLike,
} from "../../apis/TradeApi";
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
interface DealListResponse {
  id: number;
  nickname: string;
  thumbnail: string;
  userId: number;
  localDateTime: string;
  bidLogPrice: number;
  exarticleid: number;
}
interface Deal {
  bidLogPrice: number;
  exarticleid: number;
  id: number;
  localDateTime: string;
  thumbnail: string;
  nickname: string;
  bidcount: number;
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
  padding: 1rem;
  justify-content: space-between;
  height: auto;
  border-radius: 0.625rem;
  background: var(--sub1, #e5f9db);
  display: flex;
  flex-direction: row;
  align-items: center;
  margin-left: 0;
`;
const ProfileBox = styled.div`
  word-break: break-word;
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
  flex-shrink: 0;
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
const DealContainer = styled.div`
  display: flex;
  flex-direction: row;
  gap: 0.2rem;
`;
const PriceText = styled.span<{ color: string }>`
  color: ${(props) => props.color};
`;
const Sentense = styled.div`
  color: #000;
  font-size: 0.8125rem;
  font-weight: 400;
  line-height: 150%;
`;
const TradeSellerDetail = () => {
  const navigate = useNavigate();
  const [userClick, setUserClick] = useState<number>(0);
  const [like, setLike] = useState<boolean>(false);
  const queryClient = useQueryClient();
  const handleLike = () => {
    setLike(!like);
  };

  const { postId } = useParams<{ postId?: string }>();
  const exArticleId = Number(postId);
  const accessToken = sessionStorage.getItem("accessToken");
  const { isLoading, data, error } = useQuery({
    queryKey: ["tradeDetail", exArticleId],
    queryFn: accessToken
      ? () => getTradeDetail(accessToken, exArticleId)
      : undefined,
  });
  const {
    isLoading: dealListLoading,
    data: dealListData,
    error: dealListError,
  } = useQuery({
    queryKey: ["dealDetail", exArticleId],
    queryFn: accessToken
      ? () => getDealList(accessToken, exArticleId)
      : undefined,
  });
  useEffect(() => {
    if (data) {
      // 데이터가 성공적으로 로드되었을 때 실행할 로직을 여기에 작성하세요.
      // 예: 특정 쿼리의 캐시를 무효화하고 싶은 경우
      queryClient.invalidateQueries({ queryKey: ["tradeDetail", exArticleId] });
    }
  }, [data, queryClient]);

  // const { mutate: handleLikeClick } = useLike({ queryKeys: ["tradeDetail"] });
  //가격 제안
  const sentences = [
    "사랑으로 키운게 느껴져요 ${price}원 어떠심?",
    "${price}원에 이런 품질이라니, 믿을 수 없어요!",
    "정성이 가득 담긴 가격, ${price}원에 어떠세요?",
    "이건 정말 훔쳐오는 거나 다름없는 가격이네요, ${price}원이라니!",
    "${price}원이라면 저도 바로 결정했을 거예요!",
    "지금이 바로 그 기회! ${price}원에 가져가세요.",
    "우리 이웃님들, ${price}원에 이런 기회 놓치지 마세요.",
    "아직도 고민하시나요? ${price}원이라는 걸 기억하세요!",
    "가격보고 깜짝 놀랐어요, ${price}원이라니!",
    "오늘의 행운이 ${price}원에 달려있어요.",
    "${price}원에 이걸 안 산다면 후회할 거예요.",
    "장바구니가 외로워하네요, ${price}원으로 채워주세요.",
    "${price}원, 이 가격에 이걸 살 수 있다니 말도 안 돼요.",
    "집에 가져가면 가족들이 좋아할 거예요, ${price}원에요.",
    "제가 본 중에 ${price}원으로 최고의 선택이에요.",
    "이 가격 실화인가요? ${price}원이라니, 놀랍습니다.",
    "모든 것이 완벽해요, 가격도 ${price}원이라니까요.",
    "이런 건 두 번 다시 없어요, ${price}원에 당장 잡으세요.",
    "세상에, ${price}원이라면 저도 고민 없이 샀을 거예요.",
    "친구에게도 추천해줘야겠어요, ${price}원에 이런 좋은 거라니!",
  ];

  // 랜덤 문장 선택 함수
  const getRandomSentence = (price: string) => {
    const randomIndex = Math.floor(Math.random() * sentences.length);
    return sentences[randomIndex].replace("${price}", price);
  };
  //채팅 연결
  const { mutate: clickChat } = createDealChatRoom();
  const handleChatClick = (buyerId: number) => {
    clickChat({ exArticleId, buyerId });
    console.log(exArticleId);
  };
  // 배열 역순 정렬
  const [deals, setDeals] = useState<DealListResponse[]>([]);
  useEffect(() => {
    if (dealListData) {
      const sortedDeals: DealListResponse[] = [...dealListData].sort(
        (a: DealListResponse, b: DealListResponse) => {
          return b.localDateTime.localeCompare(a.localDateTime);
        }
      );
      setDeals(sortedDeals);
    }
  }, [dealListData]);
  console.log("데이터jjjjjjjj", dealListData);
  return (
    <>
      <TopBar title="가격제안목록" showBack={true} />
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
            <TextStyle>
              {dealListData && dealListData.length > 0
                ? `${dealListData[dealListData.length - 1].bidLogPrice}원`
                : data?.dealResponse.price}
              {/* {data?.dealResponse.price} */}
            </TextStyle>
            <Situation border="2px solid var(--sub0, #A0D8B3)" color="#A0D8B3;">
              참여자
            </Situation>
            <TextStyle>
              {dealListData &&
                dealListData[dealListData.length - 1]?.bidcount | 0}
              명
            </TextStyle>
          </SituationBox>

          <DealBox>
            {dealListData && dealListData.length > 0 ? (
              deals.map((item: DealListResponse, index: number) => (
                <DealContainer>
                  <PhotoBox src={item.thumbnail} alt="img" />
                  <DealList key={index}>
                    <ProfileBox onClick={() => handleChatClick(item.userId)}>
                      <Sentense>
                        {getRandomSentence(String(item.bidLogPrice))}
                      </Sentense>
                    </ProfileBox>
                  </DealList>
                </DealContainer>
              ))
            ) : (
              <div>제안된 가격이 없습니다.</div>
            )}
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
