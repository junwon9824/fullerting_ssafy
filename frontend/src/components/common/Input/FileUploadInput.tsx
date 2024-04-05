import { useAtom } from "jotai";
import { ChangeEvent, useState } from "react";
import styled from "styled-components";
import { fileAtom } from "../../../stores/diary";

const FlexColumn = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 0.6rem;
`;

const LabelSpan = styled.span`
  display: flex;
  color: ${({ theme }) => theme.colors.gray0};
  text-align: center;
  font-size: 0.875rem;
  font-weight: bold;
`;

const FileUploadBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  width: 4.2rem;
  height: 4.2rem;
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
  width: 4.2rem;
  height: 4.2rem;
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

const FileUploadInput: React.FC = () => {
  const [selectedFiles, setSelectedFiles] = useAtom(fileAtom);
  const [previewURLs, setPreviewURLs] = useState<string[]>([]);

  const handleFileChange = (e: ChangeEvent<HTMLInputElement>) => {
    const files = e.target.files;
    if (files && files.length > 0) {
      const newFiles = Array.from(files);
      setSelectedFiles([...selectedFiles, ...newFiles]);

      const newPreviewURLs = Array.from(files).map((file: File) =>
        URL.createObjectURL(file)
      );
      setPreviewURLs([...previewURLs, ...newPreviewURLs]);
    }
  };

  const handleFileUpload = async () => {
    if (!selectedFiles) return;
  };

  const handleDeleteImage = (index: number) => {
    const updatedFiles = [...selectedFiles];
    updatedFiles.splice(index, 1);
    setSelectedFiles(updatedFiles);

    const updatedPreviewURLs = [...previewURLs];
    updatedPreviewURLs.splice(index, 1);
    setPreviewURLs(updatedPreviewURLs);
  };

  return (
    <FlexColumn>
      <div>
        <LabelSpan>사진 등록</LabelSpan>
      </div>

      <RegisterBox>
        <label htmlFor="file">
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
          </FileUploadBox>
        </label>

        <InputBox
          id="file"
          type="file"
          onChange={handleFileChange}
          onClick={handleFileUpload}
          accept="image/*"
          multiple
        />

        {previewURLs.map((previewURL, index) => (
          <div key={index} style={{ position: "relative" }}>
            <PreviewImage src={previewURL} alt={`Preview ${index}`} />
            <DeleteImageButton onClick={() => handleDeleteImage(index)}>
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
        ))}
      </RegisterBox>
    </FlexColumn>
  );
};

export default FileUploadInput;
