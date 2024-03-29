import { api } from "./Base";

export const getAlarms = async () => {
    const accessToken = sessionStorage.getItem("accessToken");
    try {
      const response = await api.get("/alarms", {
        headers: { Authorization: `Bearer ${accessToken}` },
      });
      return response.data.data_body;
    } catch (error) {
      console.error("Error alarms:", error);
      throw error;
    }
  };
  

  export const readAlarm = async (alarmId: number) => {
    try {
      const accessToken = sessionStorage.getItem("accessToken");
      const response = await api.post(
        `alarms/${alarmId}`,
        null,
        {
          headers: { Authorization: `Bearer ${accessToken}` },
        }
      );
      return response.data.data_body;
    } catch (error) {
      console.error("alarmId error: ", error);
      throw error;
    }
  };


