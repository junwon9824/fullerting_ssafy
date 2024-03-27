import styled from "styled-components";
import { TopBar } from "../common/Navigator/navigator";
import { LayoutInnerBox } from "../common/Layout/Box";

const ProductBox = styled.div`
  width: 100%;
  justify-content: space-between;
  height: 4rem;
  align-items: center;
  display: flex;
`;
const LayoutMainBox = styled.main`
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 100%;
  padding-top: 3.125rem;
  padding-bottom: 4rem;
`;
const TradeChat = () => {
  return (
    <>
      <TopBar title="채팅" showBack={true} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <ProductBox></ProductBox>
        </LayoutInnerBox>
      </LayoutMainBox>
    </>
  );
};

export default TradeChat;
