import { api } from "./Base";
interface CommentType {
  communityId?: string;
  commentContent?: string;
}


export const getallcommunities = async () => {
  const accessToken = sessionStorage.getItem("accessToken");
  try {
    const response = await api.get("/articles/all", {
      headers: { Authorization: `Bearer ${accessToken}` },
    });

    return response.data.data_body;
  } catch (error) {
    console.error("Error : ", error);
    throw error;
  }
};

export const getDetailCommunities = async ( communityId: string) => {
  const accessToken = sessionStorage.getItem("accessToken");
  try {
    const response = await api.get(`/articles/${communityId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });

    return response.data.data_body;
  } catch (error) {
    console.error("Error : ", error);
    throw error;
  }
};
 
  
export const create = async (formdata:FormData) => {
  try {
    const accessToken = sessionStorage.getItem("accessToken");
    const response = await api.post(
      `articles`,
      formdata,
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


export const userCheck = async ( communityId: string) => {
  const accessToken = sessionStorage.getItem("accessToken");
  try {
    const response = await api.get(`/users/info/${communityId}`, {
      headers: { Authorization: `Bearer ${accessToken}` },
    });

    return response.data.data_body;
  } catch (error) {
    console.error("Error : ", error);
    throw error;
  }
};
 


export const toggleLike = async (communityId:string) => {
  const accessToken = sessionStorage.getItem("accessToken");
  try {
    const response = await api.post(`/articles/${communityId}/like`,{},
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

export const createComment = async (commentData: CommentType)=> {
  const accessToken = sessionStorage.getItem("accessToken");
  try {
    const response = await api.post(
      `/articles/${commentData.communityId}/comments`, 
     {commentcontent:commentData.commentContent} ,
      {
        headers: { Authorization: `Bearer ${accessToken}` },
      }
    );
    return response.data.data_body;
  } catch (error) {
    console.error("Error createComment: ", error);
    throw error;
  }
};

