import TradeGeneralDetail from "../../components/Trade/TradeDetailGeneral";
import { useSSEConnection } from "../../hooks/useSSEConnection";

const TradeGeneralDetailPage = () => {
  useSSEConnection();
  return <TradeGeneralDetail />;
};

export default TradeGeneralDetailPage;
