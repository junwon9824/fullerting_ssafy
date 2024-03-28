import styled from "styled-components";
import StyledInput from "../../components/common/Input/StyledInput";
import TradePostLayout from "../../components/common/Layout/TradePostLayout";
import useInput from "../../hooks/useInput";
import NonCheck from "/src/assets/svg/noncheck.svg";
import Check from "/src/assets/svg/check.svg";
import { useEffect, useState } from "react";
import StyledTextArea from "../../components/common/Input/StyledTextArea";
import CheckModal from "../../components/Trade/finishModal";
import SelectModal from "../../components/Trade/SelectModal";
import Diary from "/src/assets/svg/plus-diary.svg";
import Camera from "/src/assets/svg/camera.svg";
import MultiFileUploadInput from "../../components/common/Input/MultiFileUploadInput";
import { useMutation } from "@tanstack/react-query";
import { usePost } from "../../apis/TradeApi";
import { useAtom } from "jotai";
import { imageFilesAtom, selectedDiaryIdAtom } from "../../stores/trade";
import { BottomButton } from "../common/Button/LargeButton";
import { useNavigate } from "react-router-dom";

interface BackGround {
  backgroundColor?: string;
  zIndex?: number;
}
const RadioBox = styled.div`
  width: 100%;
  height: auto;
  justify-content: space-around;
  gap: 0.5rem;
  display: flex;
  flex-direction: column;
`;
const RadioBoxContainer = styled.div`
  width: auto;
  justify-content: flex-start;
  gap: 0.5rem;
  align-items: center;
  display: flex;
  flex-direction: row;
`;
const SelectContainer = styled.div`
  width: auto;
  justify-content: space-between;
  gap: 0.12rem;
  align-items: center;
  display: flex;
  flex-direction: row;
`;
const TitleText = styled.span`
  width: auto;
  font-size: 0.875rem; // 제목의 글씨 크기 조정
  color: #8c8c8c; // 제목의 글씨 색상
  font-weight: bold;
  /* align-items: center; */
  display: flex;
  flex-direction: row;
`;

const BiddingBox = styled.div`
  background-color: #eee;
  width: 100%;
  padding: 0.625rem 0.9375rem;
  border: 1px solid #d3d3d3;
  height: 3rem;
  border-radius: 0.5rem;
  align-items: center;
  display: flex;
`;
const CashText = styled.div`
  width: auto;
  font-size: 0.875rem;
  font-style: normal;
  font-weight: 400;
  color: #000000;
`;
const DiaryBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  width: 19.875rem;
  gap: 0.6rem;
`;
const Label = styled.label`
  display: flex;
  color: ${({ theme }) => theme.colors.gray0};
  text-align: center;
  font-size: 0.875rem;
  font-weight: bold;
`;
const SelectBackGround = styled.div<BackGround>`
  position: fixed; // 화면 전체에 고정되도록 설정
  top: 0;
  left: 0;
  right: 0;
  bottom: 0;
  background-color: ${(props) =>
    props.backgroundColor || "rgba(0, 0, 0, 0.5)"}; // 기본 배경색 설정
  z-index: ${(props) =>
    props.zIndex || 1000}; // 다른 컨텐츠 위에 오도록 z-index 설정
  display: flex;
  justify-content: center; // 자식 요소들을 가운데 정렬
  align-items: center; // 자식 요소들을 수직 중앙 정렬
`;
const DiarySquare = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  flex-direction: row;
  width: 4.2rem;
  height: 4.2rem;
  border-radius: 1rem;
  border: 2px solid ${({ theme }) => theme.colors.gray1};
  background: ${({ theme }) => theme.colors.white};
`;
const DiarySelectedText = styled.span`
  display: flex;
  font-size: 0.875rem;
  color: #2a7f00;
  margin-left: 0.5rem;
`;
const RedCircle = styled.div`
  width: 0.25rem;
  height: 0.25rem;
  background-color: ${({ theme }) => theme.colors.red0};
  margin: 0 0.2rem;
  border-radius: 50%;
`;

const TradePost = () => {
  const [title, setTitle] = useInput("");
  const [check, setCheck] = useState([true, false, false]);
  const [cashCheck, setCashCheck] = useState<boolean>(false);
  const [cash, setCash] = useInput("");
  const [place, setPlace] = useInput("");
  const [content, setContent] = useInput("");
  const [tradeType, setTradeType] = useState("");
  const [diary, setSelectedDiaryId] = useAtom(selectedDiaryIdAtom);

  const handleRadioClick = (index: number) => {
    setCheck(check.map((a, i) => !a));
  };
  const [modal, setModal] = useState<boolean>(false);
  const handleCashClick = () => {
    setCashCheck(!cashCheck);
  };
  const handleDiarySelect = (diaryId: number) => {
    setSelectedDiaryId(diaryId);
    setModal(false); // 모달 닫기
  };
  const openModal = () => {
    setModal(true);
  };
  useEffect(() => {
    console.log(document.body);
    // 모달이 열리면 body의 overflow를 hidden으로 설정
    if (modal) {
      document.body.style.overflow = "hidden";
    } else {
      document.body.style.overflow = "unset";
    }

    // 컴포넌트가 언마운트 될 때 원래 상태로 복구
    return () => {
      document.body.style.overflow = "unset";
    };
  }, [modal]);
  const tradeOptions = [
    { title: "제안", value: "DEAL" },
    { title: "일반 거래", value: "GENERAL_TRANSACTION" },
    { title: "나눔", value: "SHARING" },
  ];

  const [selectedFiles, setSelectedFiles] = useAtom(imageFilesAtom);
  // console.log("타입을 알려주세요", typeof selectedFiles);
  const { mutate: handlePost } = usePost();
  const navigate = useNavigate();

  const handleCheckClick = async () => {
    const formData = new FormData();

    selectedFiles.forEach((file, index) => {
      formData.append("files", file); // 'file'은 서버에서 파일을 받을 때 사용할 키입니다.
    });

    // 나머지 필요한 정보를 formData에 추가합니다.
    const exArticleRegisterRequest = JSON.stringify({
      exArticleTitle: title,
      exArticleContent: content,
      ex_article_location: place,
      exArticleType: tradeType,
      packdiaryid: diary,
      dealCurPrice: cash,
    });

    // formData.append("exArticleRegisterRequest", exArticleRegisterRequest);
    formData.append(
      "exArticleRegisterRequest",
      new Blob([exArticleRegisterRequest], { type: "application/json" })
    );

    try {
      for (var entries of formData) console.log(entries);

      await handlePost(formData);
      setSelectedFiles([]);
      setSelectedDiaryId(null);
      // navigate("/trade");
      // 요청 성공 후 페이지 이동 또는 상태 업데이트
      // navigate("/trade");
    } catch (error) {
      // 오류 처리
      console.error("업로드 실패:", error);
    }
  };
  console.log("전 다이어리에요", diary);
  return (
    <>
      {modal && (
        <SelectBackGround backgroundColor="rgba(4.87, 4.87, 4.87, 0.28)">
          <SelectModal
            closeModal={() => setModal(false)}
            onDiarySelect={handleDiarySelect}
          />
        </SelectBackGround>
      )}
      <TradePostLayout title="작물거래">
        <StyledInput
          label="제목"
          type="text"
          id="title"
          name="title"
          placeholder="제목을 입력해주세요"
          onChange={setTitle}
          isRequired={true}
        />
        <RadioBox>
          <TitleText>
            거래 방법 <RedCircle />
          </TitleText>
          <RadioBoxContainer>
            {tradeOptions.map((option, index) => (
              <SelectContainer
                key={index}
                onClick={() => setTradeType(option.value)}
              >
                <img
                  src={tradeType === option.value ? Check : NonCheck}
                  alt={option.title}
                  style={{ marginRight: "0.12rem" }}
                />
                <TitleText>{option.title}</TitleText>
              </SelectContainer>
            ))}
          </RadioBoxContainer>
        </RadioBox>
        <RadioBox>
          <TitleText>
            거래 단위
            <RedCircle />
          </TitleText>
          <RadioBoxContainer>
            {cashCheck === true ? (
              <SelectContainer>
                <img
                  src={Check}
                  alt="check"
                  onClick={handleCashClick}
                  style={{ marginRight: "0.12rem" }}
                />{" "}
                <TitleText>현금</TitleText>
              </SelectContainer>
            ) : (
              <SelectContainer>
                <img
                  src={NonCheck}
                  alt="check"
                  onClick={handleCashClick}
                  style={{ marginRight: "0.12rem" }}
                />{" "}
                <TitleText>현금</TitleText>
              </SelectContainer>
            )}
          </RadioBoxContainer>
        </RadioBox>
        <StyledInput
          label="시작가"
          type="text"
          id="startcash"
          name="startcash"
          placeholder="₩ 가격을 입력해주세요."
          onChange={setCash}
          isRequired={true}
        />
        <RadioBox>
          <TitleText>입찰 단위</TitleText>
          <BiddingBox>
            <CashText>100원</CashText>
          </BiddingBox>
        </RadioBox>
        <StyledInput
          label="거래 희망 장소"
          type="text"
          id="place"
          name="place"
          placeholder="거래 장소를 입력해주세요."
          onChange={setPlace}
          isRequired={true}
        />
        <StyledTextArea
          label="내용"
          name="content"
          placeholder="내용을 입력해주세요."
          value={content}
          onChange={setContent}
          maxLength={300}
          isRequired={true}
        />
        <DiaryBox>
          <Label>작물 일지</Label>
          <DiarySquare onClick={openModal}>
            <img src={Diary} alt="diary" />
          </DiarySquare>
          {diary && <DiarySelectedText>선택되었습니다</DiarySelectedText>}
        </DiaryBox>
        <DiaryBox>
          <MultiFileUploadInput />
        </DiaryBox>
      </TradePostLayout>
      <BottomButton onClick={handleCheckClick} text="등록하기" />
    </>
  );
};

export default TradePost;
