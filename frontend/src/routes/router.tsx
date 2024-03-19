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
import MyPageLayout from "../pages/profile/MyPageLayout";
import TradePost from "../pages/trade/TradePost";

const authRoutes = [
  { path: "/", element: <MainPage /> },
  { path: "/login", element: <LoginPage /> },
  { path: "/join", element: <JoinPage /> },
];

// 다이어리 관련 경로
const diaryRoutes = [
  { path: "/diary", element: <CropPage /> },
  { path: "/diary/detail", element: <DiaryPage /> },
  // { path: "/diary/detail/create", element: <DiaryCreatePage />},
  // { path: "/diary/detail/water", element: <DiaryWaterPage /> },
  // { path: "/diary/create", element: <CropCreatePage /> },
];

// 거래 관련 경로
const tradeRoutes = [
  { path: "/trade", element: <TradePage /> },
  { path: "/trade/post", element: <TradePost /> },
];

const mypageRoutes = [
  {
    path: "/mypage",
    element: <MyPageLayout />,
    children: [
      { index: true, element: <MyPage /> },
      { path: "editprofile", element: <EditProfile /> },
      { path: "allbadge", element: <AllBadge /> },
      { path: "likedpost", element: <LikedPost /> },
      { path: "proposepost", element: <ProposePost /> },
      { path: "transpost", element: <TransPost /> },
      { path: "logout", element: <Logout /> },
    ],
  },
];

// 모든 경로를 하나의 배열로 결합
const routes = [...authRoutes, ...diaryRoutes, ...tradeRoutes, ...mypageRoutes];

// 라우터 생성
const router = createBrowserRouter(routes);

export default router;
