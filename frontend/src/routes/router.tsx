import { createBrowserRouter } from "react-router-dom";
import LoginPage from "../pages/user/LoginPage";
import JoinPage from "../pages/user/JoinPage";
import CropPage from "../pages/diary/CropPage";

const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <div style={{ margin: "0 auto", textAlign: "center" }}>
        <div
          style={{
            fontSize: "1.875rem",
            fontFamily: "seolleimcool",
            margin: "5rem",
          }}
        >
          풀러팅
        </div>

        <div style={{ fontSize: "0.875rem", fontWeight: "700" }}>
          Bold 메인임!!!!
        </div>
        <div style={{ fontSize: "0.875rem", fontWeight: "400" }}>
          Regular 메인임!!!!
        </div>
      </div>
    ),
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
]);

export default router;
