import styled from "styled-components";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import { LayoutMainBox } from "../../components/common/Layout/Box";
import AllAlarm from "./AllAlarm";

const LayoutInnerBox = styled.div`
  display: flex;
  width: 19.875rem;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  padding: 1.12rem 0;
  gap: 0.5rem;
`;

const Alarm = () => {
  return (
    <>
      <TopBar title="알림" showBack={false} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <AllAlarm />
        </LayoutInnerBox>
      </LayoutMainBox>
      <NavBar />
    </>
  );
};

export default Alarm;
