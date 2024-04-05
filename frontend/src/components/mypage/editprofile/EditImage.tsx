import React, { useState } from "react";
import styled from "styled-components";
import { getUsersInfo, useChange } from "../../../apis/MyPage";
import { useQuery } from "@tanstack/react-query";

interface ProfileImageProps {
  image: string;
}

const ProfileContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

const ProfileImageContainer = styled.div<ProfileImageProps>`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 7rem;
  height: 7rem;
  border-radius: 50%;
  overflow: hidden;
  background-image: url(${(props) => props.image});
  background-color: gray;
  background-size: cover;
  background-position: center;
  cursor: pointer;
`;

const HiddenFileInput = styled.input`
  display: none;
`;

const EditImage: React.FC = () => {
  const [profileImage, setProfileImage] = useState<string>("");

  const { mutate: ChangeImg } = useChange();

  const handleImageChange = (event: React.ChangeEvent<HTMLInputElement>) => {
    const file = event.target.files?.[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        const newDataUrl = e.target?.result;
        setProfileImage(newDataUrl as string);
        ChangeImg(file);
      };
      reader.readAsDataURL(file);
    }
  };

  const { data: Img } = useQuery({
    queryKey: ["profileImg"],
    queryFn: getUsersInfo,
  });

  return (
    <ProfileContainer>
      <ProfileImageContainer
        image={profileImage || Img?.data.data_body.thumbnail}
        onClick={() => document.getElementById("fileInput")?.click()}
      >
        <HiddenFileInput
          type="file"
          id="fileInput"
          accept="image/*"
          onChange={handleImageChange}
        />
      </ProfileImageContainer>
    </ProfileContainer>
  );
};

export default EditImage;
