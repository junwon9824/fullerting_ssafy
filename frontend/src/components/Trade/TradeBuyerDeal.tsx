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
`;
const SituationBox = styled.div`
  width: 100%;
  justify-content: flex-start;
  display: flex;
  flex-direction: column;
  gap: 0.69rem;
  padding-bottom: 0.5rem;
  border-top: 1px solid #f4f4f4;
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
const Wall = styled.div`
  display: flex;
  gap: 2rem;
  flex-direction: row;
`;
const LayoutMainBox = styled.main`
  width: 100%;
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 100vh;
  justify-content: flex-start;
  padding-top: 3.125rem;
  /* padding-bottom: 4rem; */
  /* gap: 0.3rem; */
  /* height: 100vh; */
  overflow: hidden;
`;

const LayoutInnerBox = styled.div`
  display: flex;
  width: 19.875rem;
  flex-direction: column;
  justify-content: center;
  align-items: flex-start;
  margin-top: 3rem;
  gap: 1rem;
  padding: 1.12rem 0;
  flex-grow: 1;
  /* height: 100%; */
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
  max-height: 12rem;
  overflow-y: auto;
  flex-direction: column;
  gap: 1rem;
  display: flex;

  flex-grow: 1;
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

const SendButton = styled.img`
  width: 2.1875rem;
  height: 2.1875rem;
`;
const DealChatBox = styled.div`
  display: flex;
  width: 100%;
  justify-content: center;
  align-items: center;
  /* padding-left: 2rem;
  padding-right: 0.5rem; */
  gap: 0.8rem;
  margin-top: 0%.5;
`;

const TradeBuyerDetail = () => {
  const navigate = useNavigate();
  const [dealCash, setDealCash] = useInput("");
  const [like, setLike] = useState<boolean>(false);
  const [currentHighestBid, setCurrentHighestBid] = useState<number>(0);
  const [bidCount, setBidCount] = useState<number>(0);
  const [messages, setMessages] = useState<MessageRes[]>([]);
  const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
  const [isConnected, setIsConnected] = useState<boolean>(false);
  
  const handleLike = () => {
    setLike(!like);
  };
  
  const [deals, setDeals] = useState<Deal[]>([]);
  const { postId } = useParams<{ postId?: string }>();
  const postNumber = Number(postId);
  const accessToken = sessionStorage.getItem("accessToken");
  
  const { isLoading, data, error } = useQuery({
    queryKey: ["tradeDetail", postNumber],
    queryFn: accessToken
      ? () => getTradeDetail(accessToken, postNumber)
      : undefined,
    onSuccess: (data) => {
      // Initialize highest bid from the article's current price
      if (data?.dealResponse?.price) {
        setCurrentHighestBid(data.dealResponse.price);
      }
    }
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
  
  const queryClient = useQueryClient();
  const wssURL = import.meta.env.VITE_REACT_APP_WSS_URL;

  // WebSocket connection
  useEffect(() => {
    if (!accessToken || !postNumber) return;

    // Convert http:// or https:// to ws:// or wss://
    const wsProtocol = window.location.protocol === 'https:' ? 'wss://' : 'ws://';
    const wsUrl = `${wsProtocol}${window.location.host}/ws`;
    
    const socket = new WebSocket(wsUrl);
    const client = Stomp.over(socket);
    
    // Set debug to null to prevent console logs
    client.debug = () => {};
    
    const connectHeaders = {
      'Authorization': `Bearer ${accessToken}`
    };

    client.connect(
      connectHeaders,
      () => {
        console.log('WebSocket connected');
        setIsConnected(true);
        setStompClient(client);
        
        // Subscribe to the topic
        client.subscribe(
          `/topic/bids/${postNumber}`,
          (message) => {
            try {
              const bidUpdate = JSON.parse(message.body);
              if (bidUpdate) {
                setCurrentHighestBid(prev => 
                  bidUpdate.dealCurPrice > prev ? bidUpdate.dealCurPrice : prev
                );
                setBidCount(bidUpdate.bidderCount || 0);
                
                // Add the new message to the list
                setMessages(prev => [...prev, bidUpdate]);
                
                // Invalidate queries to refresh data
                queryClient.invalidateQueries({
                  queryKey: ["dealDetail", postNumber],
                });
              }
            } catch (error) {
              console.error('Error processing WebSocket message:', error);
            }
          },
          { id: `sub-${postNumber}` }
        );
      },
      (error: any) => {
        console.error('WebSocket connection error:', error);
        // Attempt to reconnect after 5 seconds
        setTimeout(() => {
          if (socket.readyState === WebSocket.CLOSED) {
            console.log('Attempting to reconnect WebSocket...');
            socket.close();
            const newSocket = new WebSocket(wsUrl);
            socket.onopen = () => {
              console.log('WebSocket reconnected');
              const newClient = Stomp.over(newSocket);
              newClient.debug = () => {};
              newClient.connect(connectHeaders, () => {
                setStompClient(newClient);
              });
            };
          }
        }, 5000);
      }
    );

    return () => {
      if (client.connected) {
        client.disconnect(() => {
          console.log('WebSocket disconnected');
        });
      }
    };
  }, [accessToken, postNumber, queryClient]);

  // Handle sending a new bid
  const sendMessage = async () => {
    if (!stompClient || !stompClient.connected) {
      alert('연결 중입니다. 잠시 후 다시 시도해주세요.');
      return;
    }

    if (!dealCash) return;

    const bidAmount = parseInt(dealCash);
    if (isNaN(bidAmount) || bidAmount <= currentHighestBid) {
      alert(`입찰 금액은 현재 최고가(${currentHighestBid.toLocaleString()}원)보다 높아야 합니다.`);
      return;
    }

    try {
      // Optimistic update
      setCurrentHighestBid(bidAmount);
      setBidCount(prev => prev + 1);

      // Send the bid
      await stompClient.send(
        `/pub/bidding/${postNumber}/messages`,
        {},
        JSON.stringify({
          dealCurPrice: bidAmount,
          userId: 1, // Replace with actual user ID from auth
          redirectURL: window.location.href
        })
      );

      // Clear the input
      setDealCash("");
      
    } catch (error) {
      console.error("Failed to send bid:", error);
      // Revert optimistic update on error
      queryClient.invalidateQueries({
        queryKey: ["dealDetail", postNumber],
      });
      alert("입찰에 실패했습니다. 다시 시도해주세요.");
    }
  };

  // Update the JSX to show the current highest bid
  // Find the section where you display the current highest bid and update it to use currentHighestBid
  // For example:
  // <TextStyle>{currentHighestBid.toLocaleString()}원</TextStyle>

  return (
    //  <AppContainer>
    // <AppContainer>
    <>
      <TopBar title="작물거래" showBack={true} />
      <LayoutMainBox>
        <SwiperContainer>
          <Swiper slidesPerView={1} pagination={true}>
            {data?.imageResponses?.map((image: ImageResponse, index: number) => (
              <SwiperSlide key={index}>
                <ImgBox 
                  src={image?.imgStoreUrl} 
                  alt="img"
                  onError={(e) => {
                    const target = e.target as HTMLImageElement;
                    target.src = '/default-image.png';
                  }}
                />
              </SwiperSlide>
            ))}
          </Swiper>
        </SwiperContainer>
        <LayoutInnerBox>
          <InfoBox>
            <Profile>
              <Thumbnail src={data?.exArticleResponse?.userResponse?.thumbnail} alt="profile" />
              <Name>
                <NameText>{data?.exArticleResponse?.userResponse?.nickname}</NameText>
                <ClassesText>
                  {data?.exArticleResponse?.userResponse?.rank ?? ''}
                  {/* <img src={Sprout} alt="Sprout" /> */}
                </ClassesText>
              </Name>
            </Profile>
            <Date>{data?.exArticleResponse.time}</Date>
          </InfoBox>

          <SituationBox>
            <Wall>
              <SituationGroup>
                <Situation
                  border="2px solid var(--black, #000)"
                  background="var(--white, #FFF)"
                  color="var(--black, #000)"
                >
                  {data?.exArticleResponse?.deal?.dealCurPrice?.toLocaleString() || '0'}원
                </Situation>
                <Situation
                  border="2px solid var(--black, #000)"
                  background="var(--white, #FFF)"
                  color="var(--black, #000)"
                >
                  {data?.exArticleResponse?.deal?.dealCompletePrice?.toLocaleString() || '0'}원
                </Situation>
              </SituationGroup>
            </Wall>
            <Wall>
              <SituationGroup>
                <Situation
                  border="2px solid var(--black, #000)"
                  background="var(--white, #FFF)"
                  color="var(--black, #000)"
                >
                  {data?.exArticleResponse?.deal?.dealStartAt?.split('T')[0] || '시작 전'}
                </Situation>
                <Situation
                  border="2px solid var(--black, #000)"
                  background="var(--white, #FFF)"
                  color="var(--black, #000)"
                >
                  {data?.exArticleResponse?.deal?.dealEndAt?.split('T')[0] || '종료 전'}
                </Situation>
              </SituationGroup>
            </Wall>
          </SituationBox>
          <DealBox>
            {messages?.map((message) => (
              <DealList key={message?.bidLogId || Math.random().toString(36).substr(2, 9)}>
                <ProfileBox>
                  <PhotoBox 
                    src={message?.userResponse?.thumbnail || '/default-profile.png'} 
                    alt="profile"
                    onError={(e) => {
                      const target = e.target as HTMLImageElement;
                      target.src = '/default-profile.png';
                    }}
                  />
                  <div>
                    <div>{message?.userResponse?.nickname || '익명'}</div>
                    <div>{(message?.dealCurPrice || 0).toLocaleString()}원</div>
                  </div>
                </ProfileBox>
                <div>{message?.bidderCount || 0}명이 참여중</div>
              </DealList>
            ))}
          </DealBox>

          <DealChatBox>
            <DealInput
              placeholder="최고가보다 높게 제안해주세요"
              onChange={(e) => setDealCash(e.target.value)}
            />
            <SendButton src={Send} alt="send" onClick={sendMessage} />
          </DealChatBox>
        </LayoutInnerBox>
      </LayoutMainBox>
      {/* </AppContainer> */}
    </>
  );
};

export default TradeBuyerDetail;
