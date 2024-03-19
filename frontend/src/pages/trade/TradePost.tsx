import styled from "styled-components";
import StyledInput from "../../components/common/Input/StyledInput";
import BasicLayout from "../../components/common/Layout/BasicLayout";
import useInput from "../../hooks/useInput";
import NonCheck from "/src/assets/svg/noncheck.svg";
import Check from "/src/assets/svg/check.svg";
import { useState } from "react";
import StyledTextArea from "../../components/common/Input/StyledTextArea";

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
const TitleText = styled.div`
  width: auto;
  font-size: 0.875rem; // 제목의 글씨 크기 조정
  color: #8c8c8c; // 제목의 글씨 색상
  font-weight: bold;
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
  /* justify-content: flex-start; */
`;
const CashText = styled.div`
  width: auto;
  font-size: 0.875rem;
  font-style: normal;
  font-weight: 400;
  color: #000000;
`;
const TradePost = () => {
  const [title, setTitle] = useInput("");
  const [check, setCheck] = useState([true, false]);
  const [cashCheck, setCashCheck] = useState<boolean>(false);
  const [cash, setCash] = useInput("");
  const [content, setContent] = useInput("");
  const handleRadioClick = (index: number) => {
    setCheck(check.map((a, i) => !a));
  };
  const handleCashClick = () => {
    setCashCheck(!cashCheck);
  };
  return (
    <BasicLayout title="작물거래">
      <StyledInput
        label="제목"
        type="text"
        id="title"
        name="title"
        placeholder="제목을 입력해주세요"
        onChange={setTitle}
        isRequired={false}
      />
      <RadioBox>
        <TitleText>거래 방법</TitleText>
        <RadioBoxContainer>
          {check.map((isChecked, index) => (
            <SelectContainer
              key={index}
              onClick={() => handleRadioClick(index)}
            >
              <img
                src={isChecked ? Check : NonCheck}
                alt={isChecked ? "Checked" : "Not checked"}
                style={{ marginRight: "0.12rem" }}
              />
              <TitleText>{index === 0 ? "제안" : "일반 거래"}</TitleText>
            </SelectContainer>
          ))}
        </RadioBoxContainer>
      </RadioBox>
      <RadioBox>
        <TitleText>거래 단위</TitleText>
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
        isRequired={false}
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
        onChange={setCash}
        isRequired={false}
      />
      <StyledTextArea
        label="내용"
        name="content"
        placeholder="내용을 입력해주세요."
        value={content}
        onChange={setContent}
        maxLength={300}
      />
    </BasicLayout>
  );
};

export default TradePost;
