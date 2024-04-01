import styled from "styled-components";
import { TopBar } from "../common/Navigator/navigator";
import Coli from "/src/assets/images/브로콜리.png";
import { useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";
import { useQuery, useQueryClient } from "@tanstack/react-query";
import { getDealList, getTradeDetail, useLike } from "../../apis/TradeApi";
import { useParams } from "react-router-dom";
import { Swiper, SwiperSlide } from "swiper/react";
import Send from "/src/assets/images/send.png";
import "swiper/css";
import "swiper/css/navigation";
import useInput from "../../hooks/useInput";
import Stomp from "stompjs";
import { userIndividualCheck } from "../../apis/UserApi";

interface ImageResponse {
  imgStoreUrl: string;
}
interface SituationResponse {
  border: string;
  color: string;
}
interface Icon {
  width?: number;
  height: number;
  backgroundColor: string;
  color: string;
  text?: string;
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
// socket
interface MessageRes {
  bidLogId: number; // 입찰제안 ID
  exArticleId: number; // 가격제안 게시물 id
  userResponse: UserResponse; // 입찰자 ID, 썸네일, 닉네임
  dealCurPrice: number; // 입찰자가 제안한 금액
  maxPrice: number; // 현재 이 경매글의 최고가
  bidderCount: number; //참여자수
}
interface UserResponse {
  id: number;
  email: string;
  role: string;
  nickname: string;
  thumbnail: string;
  rank: string;
  location: string;
  authProvider: string;
}
interface Response {
  id: number;
  exarticleid: number;
  userId: number;
  nickname: string;
  thumbnail: string;
  bidLogPrice: number;
}
const AppContainer = styled.div`
  display: flex;
  flex-direction: column;
  height: 100vh; /* 전체 화면 높이 */
`;

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

const SwiperContainer = styled.div`
  width: 100%;
  height: 12.5rem;
  /* display: flex; */
`;
const Thumbnail = styled.img`
  width: 1.875rem;
  height: 1.875rem;
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
  border-bottom: 1px solid #f4f4f4;
`;
const SituationBox = styled.div`
  width: 100%;
  justify-content: flex-start;
  display: flex;
  flex-direction: column;
  gap: 0.69rem;
  padding-bottom: 0.5rem;
  border-bottom: 1px solid #f4f4f4;
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
const LayoutMainBox = styled.main`
  width: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 100%;
  padding-top: 3.125rem;
  padding-bottom: 6rem;
  gap: 1rem;
  height: 100vh;
  overflow: hidden;
`;

const LayoutInnerBox = styled.div`
  display: flex;
  width: 19.875rem;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  margin-top: 3rem;
  gap: 1.5rem;
  padding: 1.12rem 0;
`;
const TextStyle = styled.div`
  align-items: center;
  display: flex;
  color: #000;
  font-size: 0.8125rem;
  font-weight: 400;
  margin-left: 0.5rem;
`;
const SituationGroup = styled.div`
  width: auto;
  height: auto;
  align-items: center;
  display: flex;
  flex-direction: row;
  gap: 0.5rem;
`;
const DealBox = styled.div`
  width: 100%;
  height: 8rem;
  overflow-y: scroll;
  flex-direction: column;
  gap: 1rem;
  display: flex;
`;
const DealList = styled.div`
  padding-right: 0.5rem;
  width: 100%;
  justify-content: space-between;
  height: 2.0625rem;
  border-radius: 0.625rem;
  background: var(--sub1, #e5f9db);
  display: flex;
  align-items: center;
`;
const ProfileBox = styled.div`
  width: auto;
  justify-content: space-between;
  gap: 0.6rem;
  align-items: center;
  color: #000;
  font-size: 0.8125rem;
  font-weight: bold;
  display: flex;
`;
const CostBox = styled.div`
  color: var(--a-0-d-8-b-3, #2a7f00);
  text-align: right;
  font-size: 0.8125rem;
  font-weight: bold;
  align-items: center;
  display: flex;
`;
const PhotoBox = styled.img`
  width: 2.0625rem;
  height: 2.0625rem;
  border-radius: 50%;
  object-fit: cover;
`;
const DealInput = styled.input`
  display: flex;
  justify-content: flex-start;
  padding-left: 1rem;
  width: 17rem;
  height: 2.1875rem;
  border-radius: 1rem;
  border: 1px solid var(--gray2, #c8c8c8);
  font-size: 0.875rem;
  &:focus {
    border: 1px solid var(--gray2, #c8c8c8);
  }
`;
const DealListBox = styled.div`
  width: 100%;
  height: 8rem;
  display: flex;
  overflow-y: scroll;
  justify-content: space-around;
  flex-direction: column;
`;
const SendButton = styled.img`
  width: 2.1875rem;
  height: 2.1875rem;
`;
const DealChatBox = styled.div`
  display: flex;
  position: fixed;
  width: 100%;
  justify-content: center;
  align-items: center;
  /* padding-left: 2rem;
  padding-right: 0.5rem; */
  gap: 1rem;
  bottom: 2rem;
`;

const TradeBuyerDetail = () => {
  const navigate = useNavigate();
  const [dealCash, setDealCash] = useInput("");
  const [like, setLike] = useState<boolean>(false);
  const handleLike = () => {
    setLike(!like);
  };

  // const { mutate: handleLikeClick } = useLike();
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
    isLoading: dealListLoading,
    data: dealListData,
    error: ealListError,
  } = useQuery({
    queryKey: ["dealDetail", postNumber],
    queryFn: accessToken
      ? () => getDealList(accessToken, postNumber)
      : undefined,
  });
  console.log("딜데이터", dealListData);
  //socket
  const queryClient = useQueryClient();
  const [messages, setMessages] = useState<MessageRes[]>([]);
  const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
  const [newMessage, setNewMessage] = useState<string>("");
  const [messageSubscribed, setMessageSubscribed] = useState<boolean>(false);
  const wssURL = import.meta.env.VITE_REACT_APP_WSS_URL;

  const [max, setMax] = useState<number>(0);
  const [bidCount, setBidCount] = useState<number>(0);
  useEffect(() => {
    console.log("데이터", dealListData);
    const socket = new WebSocket(wssURL);
    const transformedData = dealListData?.map((item: Response) => ({
      bidLogId: item.id,
      exArticleId: item.exarticleid,
      userResponse: {
        id: item.userId,
        nickname: item.nickname,
        thumbnail: item.thumbnail,
      },
      dealCurPrice: item.bidLogPrice,
    }));
    setMessages(transformedData);
    const client = Stomp.over(socket);

    console.log(socket);

    client.connect({ Authorization: `Bearer ${accessToken}` }, () => {
      client.subscribe(`/sub/bidding/${postNumber}`, (message) => {
        console.log("저는 메세지 입니다", message);

        const msg: MessageRes = JSON.parse(message.body);
        setMax(msg.maxPrice);
        setBidCount(msg.bidderCount);
        queryClient.invalidateQueries({
          queryKey: ["dealDetail", postNumber],
        });
        setMessageSubscribed(true);
      });
    });
    setStompClient(client);
    return () => {
      if (client.connected) {
        client.disconnect(() => {
          console.log("Disconnected from WebSocket server");
        });
      }
    };
  }, [accessToken]);

  const sendMessage = async () => {
    if (stompClient && newMessage.trim() !== "") {
      try {
        const messageReq = {
          dealCurPrice: newMessage,
        };

        const accessToken = sessionStorage.getItem("accessToken");
        if (!accessToken) {
          throw new Error("Access token is not available.");
        }

        const DealstartRequest = {
          exArticleId: postNumber,
          dealCurPrice: messageReq.dealCurPrice,
          redirectURL: window.location.pathname,
        };

        stompClient.send(
          `/pub/bidding/${postNumber}/messages`,
          {},
          JSON.stringify(DealstartRequest)
        );
        setNewMessage("");
      } catch (error) {
        console.error("메시지 전송 실패", error);
      }
    }
  };
  //socket 끝
  console.log(dealListData);
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
    queryFn: () => userIndividualCheck(accessToken, data?.exArticleResponse.userId),
    enabled: !!accessToken && !!data?.exArticleResponse.userId, // 여기에 조건 추가
  });
  return (
    // <AppContainer>
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

          <Title>{data?.exArticleResponse.exArticleTitle}</Title>
          <SituationBox>
            <SituationGroup>
              <Situation
                border="2px solid var(--sub3, #FFBFBF)"
                color="#FFBFBF;"
              >
                최고가
              </Situation>
              <TextStyle>
                {dealListData && dealListData.length > 0
                  ? `${dealListData[dealListData.length - 1].bidLogPrice}원`
                  : "0원"}
              </TextStyle>
            </SituationGroup>
            <SituationGroup>
              <Situation
                border="2px solid var(--sub0, #A0D8B3)"
                color="#A0D8B3;"
              >
                참여자
              </Situation>
              <TextStyle>
                {dealListData &&
                  dealListData[dealListData.length - 1]?.bidcount | 0}
                명
              </TextStyle>
            </SituationGroup>
          </SituationBox>
          <DealBox>
            {dealListData?.map((item: Deal, index: number) => (
              <DealList>
                <ProfileBox>
                  <PhotoBox src={item?.thumbnail} alt="thumbnail" />
                  {item?.nickname}
                </ProfileBox>
                <CostBox>{item?.bidLogPrice}원</CostBox>
              </DealList>
            ))}
          </DealBox>
        </LayoutInnerBox>
      </LayoutMainBox>
      <DealChatBox>
        <DealInput
          placeholder="최고가보다 높게 제안해주세요"
          onChange={(e) => setNewMessage(e.target.value)}
        />
        <SendButton src={Send} alt="send" onClick={sendMessage} />
      </DealChatBox>
      {/* </AppContainer> */}
    </>
  );
};

export default TradeBuyerDetail;
