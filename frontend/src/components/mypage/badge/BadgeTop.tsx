import styled from "styled-components";
import pullleft from "/src/assets/svg/pullleft.svg";
import pullright from "/src/assets/svg/pullright.svg";
import { Line } from "../../../components/common/Line";
import { fetchBadges } from "../../../apis/MyPage";
import { useQuery } from "@tanstack/react-query";
import load from "../../../assets/svg/loader.svg";

const Topcontainer = styled.div`
  display: flex;
  align-items: center;
  margin-top: 0.5rem;
  width: 100%;
`;

const Title = styled.div`
  color: var(--sub0, #a0d8b3);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 1.5rem;
  font-weight: bold;
  line-height: normal;
`;

const Badgecontainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: space-between;
  margin-top: 1rem;
  width: 100%;
`;

const Count = styled.div`
  color: var(--sub0, #a0d8b3);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 4rem;
  font-style: normal;
  font-weight: bold;
`;

const Allcount = styled.div`
  color: var(--sub0, #a0d8b3);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 1.5rem;
  font-style: normal;
  font-weight: bold;
  margin-left: 0.3rem;
`;

const Imagecontainer = styled.div`
  display: flex;
  gap: 10px;
`;

const AllBadgesPage = () => {
  const {
    data: badges,
    isLoading,
    error,
  } = useQuery({
    queryKey: ["topbadges"],
    queryFn: fetchBadges,
  });

  if (isLoading) {
    console.log("데이터 로딩 중...");
    return <img src={load} alt="" style={{ width: "80px", height: "80px" }} />;
  }

  if (error) {
    console.error("뱃지 데이터를 가져오는데 실패했습니다:", error);
    return <div>뱃지 데이터를 가져오는데 실패했습니다: {error.message}</div>;
  }

  console.log("로드된 데이터:", badges);

  return (
    <>
      <Topcontainer>
        <Title>전체 획득 뱃지</Title>
      </Topcontainer>
      <Badgecontainer>
        <div style={{ display: "flex", alignItems: "flex-end" }}>
          <Count>{badges.length}</Count>
          <Allcount>/10</Allcount>
        </div>
        <Imagecontainer>
          <img src={pullleft} alt="pull left" />
          <img src={pullright} alt="pull right" />
        </Imagecontainer>
      </Badgecontainer>
      <Line />
    </>
  );
};

export default AllBadgesPage;
