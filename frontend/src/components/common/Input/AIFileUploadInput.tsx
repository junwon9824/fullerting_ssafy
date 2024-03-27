import { useAtom } from "jotai";
import { ChangeEvent, useState } from "react";
import styled from "styled-components";
import { fileAtom } from "../../../stores/diary";

const FileUploadBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 19rem;
  height: 38rem;
  border-radius: 1rem;
  border: 2px solid ${({ theme }) => theme.colors.gray1};
  background: ${({ theme }) => theme.colors.white};
`;

const RegisterBox = styled.div`
  display: flex;
  gap: 0.8rem;
  flex-wrap: wrap;
`;

const FileUploadIcon = styled.svg`
  width: 2rem;
  height: 2rem;
`;

const InputBox = styled.input`
  display: none;
`;

const PreviewImage = styled.img`
  width: 19rem;
  height: 38rem;
  border-radius: 1rem;
  background: ${({ theme }) => theme.colors.white};
`;

const DeleteImageButton = styled.div`
  position: absolute;
  top: -0.5rem;
  right: -0.5rem;
  width: 1.5rem;
  height: 1.5rem;
  background-color: rgba(59, 59, 59, 0.804);
  display: flex;
  align-items: center;
  justify-content: center;
  border-radius: 50%;
  cursor: pointer;

  svg {
    width: 0.7rem;
    height: 0.7rem;
    fill: #ffffff;
  }
`;

const AIFileUploadInput = () => {
  const [selectedFile, setSelectedFile] = useAtom(fileAtom);
  const [previewURL, setPreviewURL] = useState<string>("");

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    const file = e.target.files && e.target.files[0];
    if (file) {
      setSelectedFile([file]);
      setPreviewURL(URL.createObjectURL(file));
    }
  };

  const handleFileUpload = async () => {
    if (!selectedFile) return;
  };

  const handleDeleteImage = () => {
    setSelectedFile([]);
    setPreviewURL("");
  };

  return (
    <RegisterBox>
      {previewURL ? (
        <div style={{ position: "relative" }}>
          <PreviewImage src={previewURL} alt={`Preview`} />
          <DeleteImageButton onClick={handleDeleteImage}>
            <svg
              xmlns="http://www.w3.org/2000/svg"
              width="24"
              height="24"
              viewBox="0 0 24 24"
              fill="none"
            >
              <path
                d="M3 3L21 21M21 3L3 21"
                stroke="white"
                strokeWidth="5"
                strokeLinecap="round"
                strokeLinejoin="round"
              />
            </svg>
          </DeleteImageButton>
        </div>
      ) : (
        <label htmlFor="file" style={{ width: "100%" }}>
          <FileUploadBox>
            <FileUploadIcon
              width="28"
              height="28"
              viewBox="0 0 28 28"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M5.83337 14.0002H22.1667M14 5.8335V22.1668"
                stroke="#8C8C8C"
                stroke-width="2"
                stroke-linecap="round"
                stroke-linejoin="round"
              />
            </FileUploadIcon>
          </FileUploadBox>
        </label>
      )}

      <InputBox
        id="file"
        type="file"
        onChange={handleFileChange}
        onClick={handleFileUpload}
        accept="image/*"
        capture="environment"
      />
    </RegisterBox>
    // </FlexColumn>
  );
};

export default AIFileUploadInput;
