import styled from "styled-components";
import Location from "/src/assets/svg/location.svg";
import { useQuery } from "@tanstack/react-query";
import { getExchanges } from "../../../apis/MyPage";
interface ImageResponse {
  id: number;
  imgStoreUrl: string;
}

interface DataItem {
  exLocation: string;
  exArticleTitle: string;
  price: number;
  imageResponses: ImageResponse[];
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
interface ImageResponse {
  id: number;
  imgStoreUrl: string;
}

interface ExArticleResponse {
  exLocation: string;
  exArticleId: number;
  exArticleTitle: string;
  exArticleType: string;
  imageResponses: ImageResponse[];
  price: number;
}

interface FavoriteResponse {
  islike: boolean;
  isLikeCnt: number;
}

interface DataItem {
  exArticleResponse: ExArticleResponse;
  packDiaryResponse: null | number;
  favoriteResponse: FavoriteResponse;
}

const ImgBox = styled.div`
  width: 9rem;
  height: 9rem;
  border-radius: 0.9375rem;
  top: 0;
  left: 0;
  position: relative;
  overflow: hidden;
  cursor: pointer;
`;
const ContentBox = styled.div`
  width: 100%;
  margin-bottom: 1.38rem;
  flex-wrap: wrap;
  overflow-y: hidden;
  display: flex;
  justify-content: space-between;
  position: relative;
`;

const StyledImg = styled.img`
  width: 9.5rem;
  height: 9.5rem;
  object-fit: cover;
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
  font-size: 0.5625rem;
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

const MyTrade = () => {
  const { isLoading, data, error } = useQuery<DataItem[]>({
    queryKey: ["Exchanges"],
    queryFn: () => getExchanges(),
  });
  if (isLoading) {
    return <div>Loading...</div>;
  }
  if (error) {
    return <div>{error.message}</div>;
  }
  console.log("작물거래 데이터", data);

  return (
    <ContentBox>
      {data?.map((item: DataItem, index: number) => (
        <PostBox key={index}>
          <ImgBox>
            <StyledImg
              src={item.imageResponses[0]?.imgStoreUrl}
              alt="image"
            />
          </ImgBox>
          <Town>
            <img src={Location} alt="location" />
            {item.exLocation}
          </Town>
          <Title>{item.exArticleTitle}</Title>
          <State gap={0.5}>
            {item.price === 0 ? (
              <StateIcon
                width={1.5}
                height={1}
                backgroundColor="#8e8e8e"
                color="#ffffff"
              >
                나눔완료
              </StateIcon>
            ) : (
              <StateIcon
                width={3}
                height={1}
                backgroundColor="#8e8e8e"
                color="#ffffff"
              >
                거래종료
              </StateIcon>
            )}
            {item.price}원
          </State>
        </PostBox>
      ))}
    </ContentBox>
  );
};

export default MyTrade;
