import styled from "styled-components";
import MainTop from "../components/main/MainTop";
import MainExchange from "../components/main/MainExchange";
import MainTip from "../components/main/MainTip";
import MainMap from "../components/main/MainMap";
import {NavBar} from "../components/common/Navigator/navigator";
import {useSSEConnection} from "../hooks/useSSEConnection";
import {notificationAtom} from "../stores/notification.ts";
import {useAtom} from "jotai";
//
const MainBox = styled.main`
    display: flex;
    flex-direction: column;
    margin: 8px;
`;
const SvgBox = styled.svg`
    height: 1.3rem;
`;
const MainPage = () => {


    const [notification, setNotification] = useAtom(notificationAtom);
      useSSEConnection();

    const handleClose = () => {
        setNotification({...notification, show: false});
    };

    //
    return (
        <>
            <MainTop/>
            <MainBox>
                <MainExchange/>
                <MainTip/>
                <MainMap/>
            </MainBox>
            <NavBar/>
            {/*{notification.show && (*/}
            {/*    <SSENotificationPopup*/}
            {/*        notification={notification}*/}
            {/*        onClose={handleClose}*/}
            {/*    />*/}
            {/*)}*/}
        </>
    );
};

export default MainPage;
