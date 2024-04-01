import styled from "styled-components";
import StyledInput from "../../components/common/Input/StyledInput";
import useInput from "../../hooks/useInput";
import StyledTextArea from "../../components/common/Input/StyledTextArea";
import MultiFileUploadInput from "../../components/common/Input/MultiFileUploadInput";
import RadioButton from "../../components/common/Button/radioButton";
import { TopBar } from "../../components/common/Navigator/navigator";
import { BottomButton } from "../../components/common/Button/LargeButton";
import { create, update } from "../../apis/CommunityApi";
import { useAtom } from "jotai";
import { imageFilesAtom } from "../../stores/trade";
import { selectedTypeAtom } from "../../stores/community";
import { useParams } from "react-router-dom";
import { useLocation, useNavigate } from "react-router-dom";
import { useEffect, useState } from "react";

interface ImageInfo {
  id: number;
  imgStoreUrl: string;
}
const DiaryBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  width: 19.875rem;
  gap: 0.6rem;
`;

const Create = styled.div`
  display: flex;
  align-items: center;
  flex-direction: column;
  height: 100%;
  width: 100%;
  padding-top: 5rem;
`;

const Nav = styled.div`
  margin-bottom: 1rem;
`;

const NavVlaue = styled.div`
  color: var(--gray1, #8c8c8c);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.8rem;
  font-style: normal;
  font-weight: 600;
  margin-bottom: 0.5rem;
`;
const TradePost = () => {

  const location = useLocation();
  const [title, setTitle] = useInput(location.state?.title || "");

  const [content, setContent] = useInput(location.state?.content || "");


  const navigate = useNavigate();
  const { communityId } = useParams();

  const [selectedFiles, setSelectedFiles] = useAtom(imageFilesAtom);
  const [tradeType, setTradeType] = useAtom(selectedTypeAtom);
  // 문자열을 Blob으로 변환하는 함수

  function downloadImageFromS3(url: string) {
    fetch(url)
      .then(response => response.blob())
      .then(blob => {
        // Blob을 사용하여 파일 생성
        const file = new File([blob], "image.jpg", { type: "image/jpeg" });
        
        // 생성된 파일을 저장하거나 다른 작업 수행
        // 예를 들어, 다운로드 링크 생성
        const downloadLink = document.createElement("a");
        downloadLink.href = URL.createObjectURL(file);
        downloadLink.download = "image.jpg";
        downloadLink.click();
      })
      .catch(error => {
        console.error("이미지 다운로드 실패:", error);
      });
  }

  useEffect(() => {
    if (location.state?.imgurls) {
      const initialImages = location.state.imgurls
      console.log(initialImages);

      const blob = downloadImageFromS3(initialImages);
      // 이후에는 필요한 작업을 수행하면 됩니다.
      // console.log(blob);

      setSelectedFiles(initialImages);
      // setSelectedFiles(blob);

    }


    // if (location.state?.imageResponse) {
    //   // imageResponse 배열에서 필요한 정보만 추출하여 setImageFiles 호출
    //   const initialImages = location.state.imageResponse.map(
    //     (img: ImageInfo) => img.imgStoreUrl
    //   ); // 예시 코드, 실제 구조에 맞게 조정 필요
    //   setSelectedFiles(initialImages);
    // }

  }, [location.state, setSelectedFiles]);

  const handleConfirmClick = async () => {
    const formData = new FormData();

    console.log('image등록등록등록등록' + selectedFiles.length)

    selectedFiles.forEach((file) => {
      console.log(file)

      formData.append("images", file);
    });

    if (selectedFiles.length === 0) {
      console.log("00000000000000")
      formData.append("images", new Blob([], { type: "application/json" }));
    }
    const updateInfo = {
      title: title,
      content: content,
      type: tradeType,
      images: null,
    };
    
    console.log(updateInfo.type);
    console.log(updateInfo.title);
    formData.append(
      "RegistArticleRequest",
      new Blob([JSON.stringify(updateInfo)], { type: "application/json" })
    );

    try {
      console.log(communityId)
      await update(formData, communityId);

      setSelectedFiles([]);
      navigate(-1);
    } catch (error) {
      console.error("업로드 실패:", error);
    }

    navigate("/community");
  };

  const handleRadioButtonChange = (value: string) => {
    setTradeType(value);
  };

  return (
    <>
      <TopBar title="커뮤니티" />
      <Create>
        <Nav>
          <NavVlaue>카테고리</NavVlaue>

          <RadioButton
            name="exampleRadioGroup"
            value="자유게시판"
            checked={tradeType === "자유게시판"}
            onChange={() => handleRadioButtonChange("자유게시판")}
          />
          <RadioButton
            name="exampleRadioGroup"
            value="작물소개"
            checked={tradeType === "작물소개"}
            onChange={() => handleRadioButtonChange("작물소개")}
          />
          <RadioButton
            name="exampleRadioGroup"
            value="텃밭요리"
            checked={tradeType === "텃밭요리"}
            onChange={() => handleRadioButtonChange("텃밭요리")}
          />
          <RadioButton
            name="exampleRadioGroup"
            value="꿀팁공유"
            checked={tradeType === "꿀팁공유"}
            onChange={() => handleRadioButtonChange("꿀팁공유")}
          />
        </Nav>
        <Nav>
          <StyledInput
            label="제목"
            type="text"
            id="title"
            name="title"
            placeholder="제목을 입력해주세요"
            onChange={setTitle}
            isRequired={false}
            value={title}

          />
        </Nav>
        <Nav>
          <StyledTextArea
            label="본문"
            name="content"
            placeholder="내용을 입력해주세요."
            value={content}
            onChange={setContent}
            maxLength={500}
            isRequired={false}
          />
        </Nav>
        <DiaryBox>
          <MultiFileUploadInput />
        </DiaryBox>
        
        <BottomButton onClick={handleConfirmClick} text="확인" />
      </Create>
    </>
  );
};

export default TradePost;
