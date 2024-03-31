import { Outlet } from "react-router-dom";
import { useSSEConnection } from "../../hooks/useSSEConnection";

const CommunityLayout = () => {
  useSSEConnection();
  return <Outlet />;
};

export default CommunityLayout;
