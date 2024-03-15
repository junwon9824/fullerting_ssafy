import styled from "styled-components";
import MainTop from "../components/main/MainTop";
import MainExchange from "../components/main/MainExchange";
import MainTip from "../components/main/MainTip";
import MainMap from "../components/main/MainMap";

const MainBox = styled.main`
  display: flex;
  flex-direction: column;
  margin: 8px;
`;

const MainPage = () => {
  return (
    <>
      <MainTop />
      <MainBox>
        <MainExchange />
        <MainTip />
        <MainMap />
      </MainBox>
    </>
  );
};

export default MainPage;
