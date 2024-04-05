import TradeChat from "../../components/Trade/TradeChat";
import { useSSEConnection } from "../../hooks/useSSEConnection";

const TradeChatPage = () => {
  useSSEConnection();
  return <TradeChat />;
};

export default TradeChatPage;
