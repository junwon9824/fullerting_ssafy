import styled from "styled-components";
import StyledInput from "../../components/common/Input/StyledInput";
import useInput from "../../hooks/useInput";
import StyledTextArea from "../../components/common/Input/StyledTextArea";
import MultiFileUploadInput from "../../components/common/Input/MultiFileUploadInput";
import RadioButton from "../../components/common/Button/radioButton";
import { TopBar } from "../../components/common/Navigator/navigator";
import { BottomButton } from "../../components/common/Button/LargeButton";
import { useNavigate } from "react-router-dom";
import { create } from "../../apis/CommunityApi";
import { useAtom } from "jotai";
import { imageFilesAtom } from "../../stores/trade";
import { selectedTypeAtom } from "../../stores/community";

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
  const [title, setTitle] = useInput("");
  const [content, setContent] = useInput("");

  const navigate = useNavigate();

  const [selectedFiles, setSelectedFiles] = useAtom(imageFilesAtom);
  const [tradeType, setTradeType] = useAtom(selectedTypeAtom);

  const handleConfirmClick = async () => {
    const formData = new FormData();

    selectedFiles.forEach((file) => {
      formData.append("images", file);
    });

    if (selectedFiles.length === 0) {
      formData.append("images", new Blob([], { type: "application/json" }));
    }
    const updateInfo = {
      title: title,
      content: content,
      type: tradeType,
    };
    console.log(updateInfo.type);
    console.log(updateInfo.title);
    formData.append(
      "RegistArticleRequest",
      new Blob([JSON.stringify(updateInfo)], { type: "application/json" })
    );

    try {
      await create(formData);

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
