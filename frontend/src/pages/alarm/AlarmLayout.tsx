import { Outlet } from "react-router-dom";
import { useSSEConnection } from "../../hooks/useSSEConnection";

const AlarmLayout = () => {
  useSSEConnection();
  return <Outlet />;
};

export default AlarmLayout;
