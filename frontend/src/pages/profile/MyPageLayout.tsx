import { Outlet } from "react-router-dom";
import { useSSEConnection } from "../../hooks/useSSEConnection";

const MyPageMain = () => {
  useSSEConnection();
  return <Outlet />;
};

export default MyPageMain;
