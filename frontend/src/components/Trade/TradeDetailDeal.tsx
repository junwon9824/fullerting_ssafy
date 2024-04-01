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
import { useMutation, useQuery } from "@tanstack/react-query";
import { deletePost, getTradeDetail, useLike } from "../../apis/TradeApi";
import { useParams } from "react-router-dom";
import { Swiper, SwiperSlide } from "swiper/react";
import "swiper/css";
import "swiper/css/navigation";
import SwiperCore, { Navigation, Pagination } from "swiper/modules";
import { userCheck, userIndividualCheck } from "../../apis/UserApi";
import TradePostLayout from "../common/Layout/TradePostLayout";
interface ImageResponse {
  imgStoreUrl: string;
}
interface Icon {
  width?: number;
  height: number;
  backgroundColor: string;
  color: string;
  text?: string;
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
const PriceBox = styled.div`
  width: auto;
  height: 1.375rem;

  gap: 0.37rem;
  display: flex;
  font-size: 1.25rem;
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
const TradeDetailDeal = () => {
  const navigate = useNavigate();

  const [like, setLike] = useState<boolean>(false);
  const handleLike = () => {
    setLike(!like);
  };

  const { postId } = useParams<{ postId: string }>();
  const postNumber = Number(postId);
  const accessToken = sessionStorage.getItem("accessToken");
  const { isLoading, data, error } = useQuery({
    queryKey: ["tradeDetail", postNumber],
    queryFn: accessToken
      ? () => getTradeDetail(accessToken, postNumber)
      : undefined,
  });

  const { mutate: handleLikeClick } = useLike({ queryKeys: ["tradeDetail"] });
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
    navigate(`/crop/${DiaryId}/otherview`);
    console.log("나 눌리고 있어!!!", 111);
  };
  const formatDateAndTime = (dateString: string) => {
    if (!dateString) return "";
    const [date, time] = dateString.split("T");
    const [hours, minutes, seconds] = time.split(":");
    return `${date} ${hours}:${minutes}:${seconds}`;
  };

  const {
    isLoading: isIndividualUserDetail,
    data: IndividualUserData,
    error: IndividualUserDetailError,
  } = useQuery({
    queryKey: ["individualUserDetail"],
    queryFn: () =>
      userIndividualCheck(
        accessToken as string,
        data?.exArticleResponse.userId
      ),
    enabled: !!accessToken && !!data?.exArticleResponse.userId, // 여기에 조건 추가
  });
  const BtnClick = (postId: number) => {
    if (data?.exArticleResponse?.userId === userData?.id) {
      navigate(`/trade/${postId}/seller`);
    } else {
      navigate(`/trade/${postId}/buyer`);
    }
    // console.log("저를 클릭했나요?");
  };

  const handleEdit = () => {
    console.log("저 클릭됐어요");
    navigate(`/trade/${postId}/modify`, {
      state: {
        exArticleTitle: data?.exArticleResponse?.exArticleTitle,
        exArticleContent: data?.exArticleResponse?.content,
        exArticleType: data?.exArticleResponse?.exArticleType,
        ex_article_location: data?.exArticleResponse?.exLocation,
        packdiaryid: data?.packDiaryResponse?.packDiaryId.toString(),
        deal_cur_price: data?.dealResponse?.price.toString(),
        imageResponse: data?.exArticleResponse.imageResponses,
        postId: data?.exArticleResponse.exArticleId,
      },
    });
    // console.log('gettradedetail'+response.data.data_body.exArticleResponse.imageResponses[0].imgStoreUrl)
  };
  const { mutate: deleteMutation } = useMutation({
    mutationFn: deletePost,
    onSuccess: () => {
      navigate(-1);
      //   navigate(`/crop/${packDiaryId}`);
    },
    onError: (err) => {
      console.log(err);
    },
  });
  return (
    <>
      <TradeTopBar
        title="작물거래"
        showBack={true}
        showEdit={true}
        onEdit={handleEdit}
        onDelete={() => {
          deleteMutation(data?.exArticleResponse.exArticleId);
        }}
      />
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
              <Thumbnail src={IndividualUserData?.thumbnail} alt="profile" />
              <Name>
                <NameText>{IndividualUserData?.nickname}</NameText>
                <ClassesText>
                  {IndividualUserData?.rank}
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
            <PriceBox>
              <StateIcon
                width={1.5}
                height={0.9375}
                backgroundColor="#A0D8B3"
                color="#ffffff"
              >
                현재
              </StateIcon>
              <Price>{data?.dealResponse?.price}원</Price>
            </PriceBox>

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
            <ExplainText>{data?.exArticleResponse.content}</ExplainText>
          </TitleBox>
        </LayoutInnerBox>
        <BottomButton
          text="가격 제안하기"
          onClick={() => BtnClick(postNumber)}
        />
      </LayoutMainBox>
    </>
  );
};

export default TradeDetailDeal;
