import styled from "styled-components";
import pullleft from "/src/assets/svg/pullleft.svg";
import pullright from "/src/assets/svg/pullright.svg";
import { Line } from "../../../components/common/Line";

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
  return (
    <>
      <Topcontainer>
        <Title>전체 획득 뱃지</Title>
      </Topcontainer>
      <Badgecontainer>
        <div style={{ display: "flex", alignItems: "flex-end" }}>
          <Count>5</Count>
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
