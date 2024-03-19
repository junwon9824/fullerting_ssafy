import { createBrowserRouter } from "react-router-dom";
import LoginPage from "../pages/user/LoginPage";
import JoinPage from "../pages/user/JoinPage";
import CropPage from "../pages/diary/CropPage";
import MainPage from "../pages/MainPage";
import TradePage from "../pages/trade/trade";
import MyPage from "../pages/profile/MyPage";
import AllBadge from "../pages/profile/AllBadge";
import EditProfile from "../pages/profile/EditProfile";
import LikedPost from "../pages/profile/LikedPost";
import Logout from "../pages/profile/Logout";
import ProposePost from "../pages/profile/ProposePost";
import TransPost from "../pages/profile/TransPost";
import DiaryPage from "../pages/diary/DiaryPage";
import TradePost from "../pages/trade/TradePost";

const router = createBrowserRouter([
  {
    path: "/diary/detail",
    element: <DiaryPage />,
  },
  {
    path: "/",
    element: <MainPage />,
  },
  {
    path: "/login",
    element: <LoginPage />,
  },
  {
    path: "/join",
    element: <JoinPage />,
  },
  {
    path: "/diary",
    element: <CropPage />,
  },
  {
    path: "/trade",
    element: <TradePage />,
  },
  {
    path: "/mypage",
    element: <MyPage />,
  },
  {
    path: "/editprofile",
    element: <EditProfile />,
  },
  {
    path: "/allbadge",
    element: <AllBadge />,
  },
  {
    path: "/proposepost",
    element: <ProposePost />,
  },
  {
    path: "/likedpost",
    element: <LikedPost />,
  },
  {
    path: "/transpost",
    element: <TransPost />,
  },
  {
    path: "/logout",
    element: <Logout />,
  },
  {
    path: "trade/post",
    element: <TradePost />,
  },
]);

export default router;
