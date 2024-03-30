import TradeModify from "../../components/Trade/TradeModify";
import { useSSEConnection } from "../../hooks/useSSEConnection";

const TradeModifyPage = () => {
  useSSEConnection();
  return <TradeModify />;
};

export default TradeModifyPage;
