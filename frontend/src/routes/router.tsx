import { createBrowserRouter } from "react-router-dom";
import styled from "styled-components";

const router = createBrowserRouter([
  {
    path: "/",
    element: (
      <div>
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
    path: "/test",
    element: <div>test</div>,
  },
]);

export default router;
