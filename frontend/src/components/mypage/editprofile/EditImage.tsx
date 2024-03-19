import React, { useState, useEffect } from "react";
import styled from "styled-components";
import ProfileImage from "/src/assets/svg/profileimage.svg";

const ProfileContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
`;

const ProfileImageContainer = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 7rem;
  height: 7rem;
  border-radius: 50%;
  overflow: hidden;
  background-image: url(${(props) => props.image});
  background-size: cover;
  background-position: center;
  cursor: pointer;
`;

const HiddenFileInput = styled.input`
  display: none;
`;

const EditImage = () => {
  const [profileImage, setProfileImage] = useState(
    localStorage.getItem("profileImage") || ProfileImage
  );

  useEffect(() => {
    localStorage.setItem("profileImage", profileImage);
  }, [profileImage]);

  const handleImageChange = (event) => {
    const file = event.target.files[0];
    if (file) {
      const reader = new FileReader();
      reader.onload = function (e) {
        setProfileImage(e.target.result);
      };
      reader.readAsDataURL(file);
    }
  };

  return (
    <ProfileContainer>
      <ProfileImageContainer
        image={profileImage}
        onClick={() => document.getElementById("fileInput").click()}
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
