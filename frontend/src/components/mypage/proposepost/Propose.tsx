import styled from "styled-components";
import { useQuery } from "@tanstack/react-query";
import PostBoxComponent from "./PostBoxComponent";
import { getPropose } from "../../../apis/MyPage";

interface DataItem {
  exLocation: string;
  exArticleTitle: string;
  price: number;
  isLikeCnt: number;
  imageResponses: ImageResponse[];
  exArticleType: "DEAL" | "SHARING" | "GENERAL_TRANSACTION" | "OTHER";
}
interface ImageResponse {
  id: number;
  img_store_url: string;
}
const ContentBox = styled.div`
  width: 100%;
  margin-bottom: 1.38rem;
  flex-wrap: wrap;
  overflow-y: hidden;
  display: flex;
  justify-content: space-between;
  position: relative;
`;

const Propose = () => {
  const { isLoading, data, error } = useQuery({
    queryKey: ["ProposeList"],
    queryFn: () => getPropose(),
  });
  console.log("페이지 들어옴", data);

  if (isLoading) {
    return <div>Loading...</div>;
  }

  if (error) {
    return (
      <div>
        Error: {error instanceof Error ? error.message : "An error occurred"}
      </div>
    );
  }

  return (
    <ContentBox>
      {data?.map((item: DataItem, index: number) => (
        <PostBoxComponent key={index} item={item} />
      ))}
    </ContentBox>
  );
};

export default Propose;
