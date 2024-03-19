import React, { ChangeEvent, useState } from "react";
import styled from "styled-components";

const FileUploadBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 3.5rem;
  height: 3.5rem;
  border-radius: 1.25rem;
  border: 2px solid ${({ theme }) => theme.colors.gray1};
  background: ${({ theme }) => theme.colors.white};
`;

const RegisterBox = styled.div`
  width: 3.5rem;
  height: 3.5rem;
  display: flex;
  gap: 0.8rem;
`;

const FileUploadIcon = styled.svg`
  width: 24px;
  height: 24px;
`;

const InputBox = styled.input``;

const PreviewImage = styled.img`
  width: 3.5rem;
  height: 3.5rem;
  border-radius: 1.25rem;
  background: ${({ theme }) => theme.colors.white};
`;

const FileUploadInput: React.FC = () => {
  const [selectedFile, setSelectedFile] = useState<File | null>(null);
  const [previewURL, setPreviewURL] = useState<string | null>(null);

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;
    if (files && files.length > 0) {
      const file = files[0];
      setSelectedFile(file);

      const reader = new FileReader();
      reader.onload = () => {
        setPreviewURL(reader.result as string);
      };
      reader.readAsDataURL(file);
    }
  };

  const handleFileUpload = async () => {
    if (!selectedFile) return;

    const formData = new FormData();
    formData.append("file", selectedFile);
  };

  return (
    <RegisterBox>
      <FileUploadBox>
        <FileUploadIcon
          xmlns="http://www.w3.org/2000/svg"
          width="24"
          height="24"
          viewBox="0 0 24 24"
          fill="none"
        >
          <path
            d="M14.4231 4.52307H9.57697L7.15389 7.43076H4.2462C3.73209 7.43076 3.23903 7.63499 2.8755 7.99853C2.51197 8.36206 2.30774 8.85511 2.30774 9.36923V18.0923C2.30774 18.6064 2.51197 19.0995 2.8755 19.463C3.23903 19.8265 3.73209 20.0308 4.2462 20.0308H19.7539C20.268 20.0308 20.7611 19.8265 21.1246 19.463C21.4881 19.0995 21.6924 18.6064 21.6924 18.0923V9.36923C21.6924 8.85511 21.4881 8.36206 21.1246 7.99853C20.7611 7.63499 20.268 7.43076 19.7539 7.43076H16.8462L14.4231 4.52307Z"
            stroke="#BEBEBE"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
          <path
            d="M12 16.1538C13.6059 16.1538 14.9077 14.852 14.9077 13.2461C14.9077 11.6403 13.6059 10.3385 12 10.3385C10.3942 10.3385 9.09235 11.6403 9.09235 13.2461C9.09235 14.852 10.3942 16.1538 12 16.1538Z"
            stroke="#BEBEBE"
            stroke-width="2"
            stroke-linecap="round"
            stroke-linejoin="round"
          />
        </FileUploadIcon>
        <InputBox
          type="file"
          onChange={handleFileChange}
          onClick={handleFileUpload}
        />
      </FileUploadBox>

      {previewURL && <PreviewImage src={previewURL} alt="Preview" />}
    </RegisterBox>
  );
};

export default FileUploadInput;
