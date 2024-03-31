import { createBrowserRouter } from "react-router-dom";
import LoginPage from "../pages/user/LoginPage";
import JoinPage from "../pages/user/JoinPage";
import CropPage from "../pages/diary/CropPage";
import MainPage from "../pages/MainPage";
import TradePage from "../pages/trade/trade";
import MyPage from "../pages/profile/MyPage";
import AllBadge from "../pages/profile/AllBadge";
import EditProfile from "../pages/profile/EditProfile";
import MyPost from "../pages/profile/MyPost";
import Logout from "../pages/profile/Logout";
import ProposePost from "../pages/profile/ProposePost";
import TransPost from "../pages/profile/TransPost";
import DiaryPage from "../pages/diary/DiaryPage";
import MyPageLayout from "../pages/profile/MyPageLayout";
import TradePostPage from "../pages/trade/TradePostPage";
import DiaryCreatePage from "../pages/diary/DiaryCreatePage";
import DiaryWaterPage from "../pages/diary/DiaryWaterPage";
import CropCreatePage from "../pages/diary/CropCreatePage";
import TestPage from "../pages/user/test";
import CommunityLayout from "../pages/community/CommunityLayout";
import Community from "../pages/community/Community";
import CreateCommunity from "../pages/community/CreateCommunity";
import CommunityDetail from "../pages/community/CommunityDetail";
import TradeGeneralDetailPage from "../pages/trade/TradeGeneralDetail";
import CropUpdatePage from "../pages/diary/CropUpdatePage";
import TradeSellerPage from "../pages/trade/TradeSellerPage";
import AuthCallbackPage from "../pages/user/AuthCallbackPage";
import TradeBuyerPage from "../pages/trade/TradeBuyerPage";
import TradeChatPage from "../pages/trade/TradeChatPage";
import DiaryDetailPage from "../pages/diary/DiaryDetailPage";
import TradeDealDetailPage from "../pages/trade/TradeDealDetailPage";
import AlarmLayout from "../pages/alarm/AlarmLayout";
import Alarm from "../pages/alarm/Alarm";
import RecognizePage from "../pages/diary/RecognizePage";
import TradeModifyPage from "../pages/trade/TradeModifyPage";
import DiaryUpdatePage from "../pages/diary/DiaryUpdatePage";
import GardenPage from "../pages/garden/GardenPage";
import ChatTestPage from "../pages/user/chattest";
import SSETest from "../pages/user/ssetest";
import TownCertifyPage from "../pages/user/TownCertifyPage";
import TradeChatRoomPage from "../pages/trade/TradeChatRoomPage";
import NotFoundPage from "../pages/NotFoundPage";
import SearchAddressPage from "../pages/user/SearchAddressPage";

const authRoutes = [
  { path: "/", element: <MainPage /> },
  { path: "/ssetest", element: <SSETest /> },
  { path: "/login", element: <LoginPage /> },
  { path: "/join", element: <JoinPage /> },
  { path: "/auth/callback", element: <AuthCallbackPage /> },
  { path: "/town", element: <TownCertifyPage /> },
  { path: "/address", element: <SearchAddressPage /> },
];

const gardenRoutes = [{ path: "/garden", element: <GardenPage /> }];

const diaryRoutes = [
  { path: "/crop", element: <CropPage /> },
  { path: "/crop/create", element: <CropCreatePage /> },
  { path: "/crop/:packDiaryId/update", element: <CropUpdatePage /> },
  { path: "/crop/:packDiaryId", element: <DiaryPage /> },
  { path: "/crop/:packDiaryId/ai", element: <RecognizePage /> },
  { path: "/diary/:diaryId", element: <DiaryDetailPage /> },
  { path: "/diary/create", element: <DiaryCreatePage /> },
  { path: "/diary/water", element: <DiaryWaterPage /> },
  { path: "/diary/:diaryId/update", element: <DiaryUpdatePage /> },
];

const tradeRoutes = [
  { path: "/trade", element: <TradePage /> },
  { path: "/trade/post", element: <TradePostPage /> },
  { path: "/trade/:postId/generaldetail", element: <TradeGeneralDetailPage /> },
  { path: "/trade/:postId/DealDetail", element: <TradeDealDetailPage /> },
  { path: "/trade/:postId/seller", element: <TradeSellerPage /> },
  { path: "/trade/:postId/buyer", element: <TradeBuyerPage /> },
  { path: "/trade/:chatId/Chat", element: <TradeChatPage /> },
  { path: "/trade/chatroom", element: <TradeChatRoomPage /> },
  { path: "/trade/:postId/modify", element: <TradeModifyPage /> },
  { path: "/trade/test", element: <TestPage /> },
  { path: "/trade/chattest", element: <ChatTestPage /> },
];

const mypageRoutes = [
  {
    path: "/mypage",
    element: <MyPageLayout />,
    children: [
      { index: true, element: <MyPage /> },
      { path: "editprofile", element: <EditProfile /> },
      { path: "allbadge", element: <AllBadge /> },
      { path: "likedpost", element: <MyPost /> },
      { path: "proposepost", element: <ProposePost /> },
      { path: "transpost", element: <TransPost /> },
      { path: "logout", element: <Logout /> },
    ],
  },
];

const communityRoutes = [
  {
    path: "/community",
    element: <CommunityLayout />,
    children: [
      { index: true, element: <Community /> },
      { path: "createcommunity", element: <CreateCommunity /> },
      { path: ":communityId", element: <CommunityDetail /> },
    ],
  },
];

const alarm = [
  {
    path: "/alarm",
    element: <AlarmLayout />,
    children: [
      { index: true, element: <Alarm /> },
      // { path: "allalarm", element: <AllAlarm /> },
    ],
  },
];

const notFound = [{ path: "*", element: <NotFoundPage /> }];

const routes = [
  ...authRoutes,
  ...diaryRoutes,
  ...tradeRoutes,
  ...mypageRoutes,
  ...communityRoutes,
  ...alarm,
  ...gardenRoutes,
  ...notFound,
];

const router = createBrowserRouter(routes);

export default router;
