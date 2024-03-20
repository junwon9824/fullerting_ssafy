import styled from "styled-components";
import { TopBar } from "../../components/common/Navigator/navigator";
import Coli from "/src/assets/images/브로콜리.png";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
const ImgBox = styled.img`
  width: 100%;
  height: 15.5625rem;
  display: flex;
`;
const TradeGeneralDetail = () => {
  return (
    <>
      <TopBar title="작물거래" showBack={true} showEdit={true} />
      <LayoutMainBox>
        <ImgBox src={Coli} alt="coli" />
        <LayoutInnerBox></LayoutInnerBox>
      </LayoutMainBox>
    </>
  );
};

export default TradeGeneralDetail;
