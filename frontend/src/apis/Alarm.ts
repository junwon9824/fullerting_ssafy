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
  
