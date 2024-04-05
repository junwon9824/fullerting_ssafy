import TradeBuyerDetail from "../../components/Trade/TradeBuyerDeal";
import { useSSEConnection } from "../../hooks/useSSEConnection";

const TradeBuyerPage = () => {
  useSSEConnection();
  return <TradeBuyerDetail />;
};

export default TradeBuyerPage;
