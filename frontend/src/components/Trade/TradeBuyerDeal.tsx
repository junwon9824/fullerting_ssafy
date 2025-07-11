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
  localDateTime: string | null | undefined;
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

interface WebSocketMessage {
  type: string;
  bidLogId: number;
  exArticleId: number;
  userResponse: UserResponse;
  dealCurPrice: number;
  bidderCount: number;
  localDateTime: string;
}

// 날짜 문자열("YYYY-MM-DD HH:mm:ss")을 안전하게 Date 객체로 변환
function parseDateTime(dateTime: string): Date | null {
  if (!dateTime || typeof dateTime !== 'string') return null;

  // 1. 직접 Date 생성 시도
  let date = new window.Date(dateTime);
  if (!isNaN(date.getTime())) return date;

  // 2. "YYYY-MM-DD HH:mm:ss" → "YYYY-MM-DDTHH:mm:ssZ" (UTC로 해석)
  let isoString = dateTime.replace(' ', 'T') + 'Z';
  date = new window.Date(isoString);
  if (!isNaN(date.getTime())) return date;

  // 3. 수동 파싱 (UTC 기준)
  const [datePart, timePart] = dateTime.split(' ');
  if (datePart && timePart) {
    const [year, month, day] = datePart.split('-').map(Number);
    const [hours, minutes, seconds] = timePart.split(':').map(Number);
    date = new window.Date(window.Date.UTC(year, month - 1, day, hours, minutes, seconds));
    if (!isNaN(date.getTime())) return date;
  }

  // 4. 실패 시 null 반환
  return null;
}

// 날짜를 "YYYY-MM-DD HH:mm" 형식의 한글로 반환
function formatDateTime(dateTime: string | Date | null | undefined): string {
  let date: Date | null | undefined = null;

  if (!dateTime) return '날짜 정보 없음';

  if (typeof dateTime === 'string') {
    date = parseDateTime(dateTime);
  } else if (dateTime instanceof Date) {
    date = dateTime;
  } else {
    return '날짜 형식 오류';
  }

  if (!date || isNaN(date.getTime())) return '날짜 오류';

  return date.toLocaleString('ko-KR', {
    year: 'numeric',
    month: '2-digit',
    day: '2-digit',
    hour: '2-digit',
    minute: '2-digit',
    hour12: false
  });
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
const ProfileImage = styled.img`
  width: 1.875rem;
  height: 1.875rem;
  border-radius: 50%;
  object-fit: cover;
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
  height: auto;
  align-items: center;
  justify-content: flex-start;
  gap: 1rem;
  padding-right: 1rem;
  display: flex;
  flex-direction: row;
  margin: 1rem 0;
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

const DealBox = styled.div`
  width: 100%;
  max-height: 15rem;
  overflow-y: auto;
  flex-direction: column;
  gap: 0.8rem;
  display: flex;
  padding: 0.5rem 0;
  margin: 0.5rem 0;
  border-top: 1px solid #f0f0f0;
  border-bottom: 1px solid #f0f0f0;
`;

const DealList = styled.div`
  padding: 0.8rem;
  width: 100%;
  border-radius: 0.8rem;
  background: #f8f8f8;
  display: flex;
  align-items: center;
  justify-content: space-between;
  box-shadow: 0 1px 3px rgba(0,0,0,0.1);
  transition: all 0.2s ease;
  
  &:hover {
    transform: translateY(-1px);
    box-shadow: 0 2px 5px rgba(0,0,0,0.15);
  }
`;

const DealChatBox = styled.div`
  display: flex;
  width: 100%;
  justify-content: space-between;
  align-items: center;
  gap: 0.8rem;
  padding: 0.5rem 0;
  position: sticky;
  bottom: 0;
  background: white;
  padding: 1rem 0;
  border-top: 1px solid #eee;
`;

const DealInput = styled.input`
  flex: 1;
  padding: 0.8rem 1.2rem;
  height: 3rem;
  border-radius: 1.5rem;
  border: 2px solid #e0e0e0;
  font-size: 0.9rem;
  transition: all 0.2s ease;
  
  &:focus {
    outline: none;
    border-color: #4CAF50;
    box-shadow: 0 0 0 2px rgba(76, 175, 80, 0.2);
  }
  
  &::placeholder {
    color: #aaa;
  }
`;

const SendButton = styled.button<{ disabled?: boolean }>`
  width: 3rem;
  height: 3rem;
  border: none;
  border-radius: 50%;
  background: ${props => props.disabled ? '#ccc' : '#4CAF50'};
  color: white;
  display: flex;
  align-items: center;
  justify-content: center;
  cursor: ${props => props.disabled ? 'not-allowed' : 'pointer'};
  transition: all 0.2s ease;
  
  &:hover:not(:disabled) {
    background: #45a049;
    transform: scale(1.05);
  }
  
  &:active:not(:disabled) {
    transform: scale(0.98);
  }
`;

const PriceHighlight = styled.div`
  font-size: 1.3rem;
  font-weight: bold;
  color: #2E7D32;
  display: flex;
  align-items: center;
  gap: 0.3rem;
  
  &::before {
    content: '₩';
    font-size: 1rem;
    color: #2E7D32;
  }
`;

const ParticipantCount = styled.div`
  font-size: 1.1rem;
  font-weight: bold;
  color: #1565C0;
  display: flex;
  align-items: center;
  gap: 0.3rem;
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

const TradeBuyerDetail = () => {
  const navigate = useNavigate();
  const [newMessage, setNewMessage] = useState("");
  const [like, setLike] = useState<boolean>(false);
  const [currentHighestBid, setCurrentHighestBid] = useState<number>(0);
  const [bidCount, setBidCount] = useState<number>(0);
  const [messages, setMessages] = useState<WebSocketMessage[]>([]);
  const [stompClient, setStompClient] = useState<Stomp.Client | null>(null);
  const [isConnected, setIsConnected] = useState<boolean>(false);
  const [bidHistory, setBidHistory] = useState<Deal[]>([]);

  const handleLike = () => {
    setLike(!like);
  };

  const { postId } = useParams<{ postId?: string }>();
  const postNumber = Number(postId);
  const accessToken = sessionStorage.getItem("accessToken");

  const {
    isLoading,
    data: tradeDetailData,
    error: tradeDetailError
  } = useQuery({
    queryKey: ["tradeDetail", postNumber],
    queryFn: accessToken && postNumber
      ? () => getTradeDetail(accessToken, postNumber)
      : undefined,
    enabled: !!(accessToken && postNumber)
  });

  // Handle trade detail query success
  useEffect(() => {
    if (tradeDetailData?.dealResponse) {
      console.log("상품 상세 정보 로드 성공, 최고 입찰가 설정:", tradeDetailData.dealResponse);
      setCurrentHighestBid(tradeDetailData.dealResponse.price);
      // Initialize bid count from trade detail if available
      if (tradeDetailData.dealResponse.bidCount !== undefined) {
        setBidCount(tradeDetailData.dealResponse.bidCount);
      }
    }
  }, [tradeDetailData]);

  // Handle trade detail query error
  useEffect(() => {
    if (tradeDetailError) {
      console.error("상품 상세 정보 조회 중 오류 발생:", tradeDetailError);
    }
  }, [tradeDetailError]);

  console.log("useQuery 선언 직전: accessToken", accessToken, "postNumber", postNumber);

  const { data: _dealData, status, error: queryError } = useQuery<Deal[]>({
    queryKey: ["dealDetail", postNumber],
    queryFn: async () => {
      console.log("1. 쿼리 함수 실행 시작");

      if (!accessToken || !postNumber) {
        const error = new Error(`필수 파라미터 누락: ${!accessToken ? 'accessToken' : ''} ${!postNumber ? 'postNumber' : ''}`);
        console.error("1a. 쿼리 실행 불가:", error.message);
        throw error;
      }

      try {
        console.log("2. getDealList 호출 전");
        const result = await getDealList(accessToken, postNumber);
        console.log("3. getDealList 결과:", {
          isArray: Array.isArray(result),
          length: Array.isArray(result) ? result.length : 'N/A'
        });

        if (!result) {
          console.log("3a. 빈 응답");
          return [];
        }

        if (!Array.isArray(result)) {
          console.error("3b. 배열이 아닌 응답:", result);
          return [];
        }

        console.log("3c. Mapping items:", result);
        return result.map(item => {
          console.log("3d. Processing item:", item);
          return {
            ...item,
            id: item.id || 0,
            exarticleid: item.exarticleid || postNumber,
            bidLogPrice: item.bidLogPrice || 0,
            nickname: item.nickname || '알 수 없음',
            thumbnail: item.thumbnail || '/src/assets/images/default.png',
            localDateTime: formatDateTime(item.localDateTime),
            bidcount: item.bidcount || 0
          };
        });

      } catch (error) {
        console.error("4. 쿼리 실행 오류:", error);
        throw error;
      }
    },
    enabled: !!(accessToken && postNumber),
    retry: 1
  });

  // Handle query callbacks using useEffect
  useEffect(() => {
    if (status === 'success' && _dealData) {
      console.group("5. 쿼리 성공");
      console.log("5a. 데이터 수신:", _dealData);
      console.log("5b. 데이터 타입:", Array.isArray(_dealData) ? '배열' : typeof _dealData);
      console.log("5c. 데이터 길이:", Array.isArray(_dealData) ? _dealData.length : 'N/A');

      if (_dealData.length > 0) {
        console.log("5d. 입찰 내역 설정");
        setBidHistory(_dealData);
        const uniqueBidders = new Set(_dealData.map(bid => bid.nickname).filter(Boolean));
        setBidCount(uniqueBidders.size);
        const highestBid = Math.max(..._dealData.map(bid => bid.bidLogPrice || 0));
        setCurrentHighestBid(highestBid);
      } else {
        console.log("5e. 입찰 내역 없음");
        setBidHistory([]);
        setBidCount(0);
      }
      console.groupEnd();
    }
  }, [status, _dealData]);

  // Handle errors
  useEffect(() => {
    if (queryError) {
      console.group("6. 쿼리 오류");
      console.error("6a. 오류 발생:", queryError);
      if ('response' in queryError) {
        console.error("6b. 오류 응답:", (queryError as any).response?.data);
      }
      console.groupEnd();
    }
  }, [queryError]);

  // Log when query is settled (success or error)
  useEffect(() => {
    if (status === 'success' || status === 'error') {
      console.log("7. 쿼리 완료 - 상태:", status);
    }
  }, [status]);

  const queryClient = useQueryClient();
  const wssURL = import.meta.env.VITE_REACT_APP_WSS_URL;

  // WebSocket connection
  useEffect(() => {
    if (!accessToken || !postNumber) {
      console.log('Missing accessToken or postNumber:', { accessToken, postNumber });
      return;
    }

    const wsUrl = import.meta.env.VITE_REACT_APP_WSS_URL;
    console.log('WebSocket URL:', wsUrl);

    const socket = new WebSocket(wsUrl);
    const client = Stomp.over(socket);

    // Enable debug logging in development
    if (import.meta.env.DEV) {
      client.debug = (str) => console.log(str);
    } else {
      client.debug = () => { };
    }

    const connectHeaders = {
      'Authorization': `Bearer ${accessToken}`
    };

    const onConnect = () => {
      console.log('WebSocket connected successfully');
      setIsConnected(true);
      
      const topic = `/topic/bidding/${postNumber}`;
      console.log(`Subscribing to topic: ${topic}`);
      
      const subscription = client.subscribe(
        topic,
        (message) => {
          console.log('Raw message received:', message);
          
          try {
            const data = JSON.parse(message.body);
            console.log('Parsed message data:', data);

            if (data.type === 'BID_UPDATE') {
              // Update current highest bid
              setCurrentHighestBid(data.dealCurPrice);
              
              // Update bidder count
              if (data.bidderCount !== undefined) {
                console.log("Updating bidder count:", data.bidderCount);
                setBidCount(data.bidderCount);
              }
              
              // Add to bid history
              setBidHistory(prev => {
                const newBidEntry: Deal = {
                  id: data.bidLogId,
                  exarticleid: data.exArticleId,
                  bidLogPrice: data.dealCurPrice,
                  nickname: data.userResponse?.nickname || '알 수 없음',
                  thumbnail: data.userResponse?.thumbnail || '',
                  localDateTime: data.localDateTime || new Date().toISOString(),
                  bidcount: data.bidderCount || 0
                };
                return [newBidEntry, ...prev];
              });

              // Add to messages for notification
              setMessages(prev => [...prev, data]);
            }
            
            // Invalidate query to refetch latest data
            queryClient.invalidateQueries({ queryKey: ["dealDetail", postNumber] });
          } catch (error) {
            console.error('Error processing message:', error);
          }
        },
        { id: `sub-${postNumber}` }
      );

      console.log('Subscription active:', subscription);
    };

    const onError = (error: Error | string) => {
      console.error('WebSocket connection error:', error);
      setIsConnected(false);
    };

    console.log('Attempting to connect to WebSocket...');
    client.connect(
      {
        login: accessToken,  // Use 'login' field for the token
        passcode: '',        // Empty passcode if not required
        ...connectHeaders    // Spread any additional headers
      },
      onConnect,
      onError
    );

    // Save the client instance
    setStompClient(client);

    // Cleanup function
    return () => {
      if (client.connected) {
        client.unsubscribe(`sub-${postNumber}`);
        client.disconnect(() => {
          console.log('WebSocket 연결 해제됨');
        });
      }
    };
  }, [accessToken, postNumber]);

  // Handle sending a new bid
  const sendMessage = async () => {
    if (!stompClient || !stompClient.connected) {
      alert('연결 중입니다. 잠시 후 다시 시도해주세요.');
      return;
    }

    if (!newMessage) return;

    const bidAmount = parseInt(newMessage);
    if (isNaN(bidAmount) || bidAmount <= currentHighestBid) {
      alert(`입찰 금액은 현재 최고가(${currentHighestBid.toLocaleString()}원)보다 높아야 합니다.`);
      return;
    }

    try {
      // Optimistic update
      setCurrentHighestBid(bidAmount);
      // Don't update bid count here as it will be updated via WebSocket

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
      setNewMessage("");
    } catch (error) {
      console.error("Failed to send bid:", error);
      // Revert optimistic update on error
      queryClient.invalidateQueries({
        queryKey: ["dealDetail", postNumber],
      });
      alert("입찰에 실패했습니다. 다시 시도해주세요.");
    }
  };

  return (
    <>
      <TopBar title="작물거래" showBack={true} />
      <LayoutMainBox>
        <SwiperContainer>
          <Swiper slidesPerView={1} pagination={true}>
            {tradeDetailData?.imageResponses?.map((image: ImageResponse, index: number) => (
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
              <ProfileImage
                src={tradeDetailData?.exArticleResponse?.userResponse?.thumbnail || '/src/assets/images/default.png'}
                alt="profile"
                onError={(e) => {
                  const target = e.target as HTMLImageElement;
                  target.src = '/src/assets/images/default.png';
                }}
              />
              <Name>
                <NameText>{tradeDetailData?.exArticleResponse?.userResponse?.nickname}</NameText>
                <ClassesText>
                  {tradeDetailData?.exArticleResponse?.userResponse?.rank ?? ''}
                  {/* <img src={Sprout} alt="Sprout" /> */}
                </ClassesText>
              </Name>
            </Profile>
            <Date>
              {tradeDetailData?.exArticleResponse?.time
                ? formatDateTime(tradeDetailData.exArticleResponse.time)
                : '날짜 정보 없음'}
            </Date>
          </InfoBox>

          <SituationBox>
            <Situation border="2px solid var(--sub3, #FFBFBF)" color="#FFBFBF">
              최고가
            </Situation>
            <TextStyle>
              {currentHighestBid.toLocaleString()}원
            </TextStyle>
            <Situation border="2px solid var(--sub0, #A0D8B3)" color="#A0D8B3">
              참여자
            </Situation>
            <TextStyle>
              {bidCount}명
            </TextStyle>
          </SituationBox>

          <DealBox>
            {bidHistory.length > 0 ? (
              bidHistory.map((item: Deal, index: number) => (
                <DealList key={index}>
                  <ProfileBox>
                    <PhotoBox
                      src={item.thumbnail || '/src/assets/images/default.png'}
                      alt={item.nickname}
                      onError={(e) => {
                        const target = e.target as HTMLImageElement;
                        target.src = '/src/assets/images/default.png';
                      }}
                    />
                    <div>
                      <div>{item.nickname}</div>
                      <div style={{ fontSize: '0.8rem', color: '#666' }}>
                        {item.localDateTime
                          ? formatDateTime(item.localDateTime)
                          : '날짜 정보 없음'}
                      </div>
                    </div>
                  </ProfileBox>
                  <CostBox>{item?.bidLogPrice?.toLocaleString() ?? '0'}원</CostBox>
                </DealList>
              ))
            ) : (
              <div style={{ textAlign: 'center', width: '100%', padding: '1rem' }}>
                아직 입찰 내역이 없습니다.
              </div>
            )}
          </DealBox>

          <DealChatBox>
            <DealInput
              placeholder="최고가보다 높게 제안해주세요"
              value={newMessage}
              onChange={(e) => setNewMessage(e.target.value)}
              type="number"
            />

            <SendButton onClick={sendMessage}>
              <img src={Send} alt="send" width={24} height={24} />
            </SendButton>

          </DealChatBox>
        </LayoutInnerBox>
      </LayoutMainBox>
    </>
  );
};

export default TradeBuyerDetail;
