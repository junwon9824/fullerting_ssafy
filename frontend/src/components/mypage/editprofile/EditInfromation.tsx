import styled from "styled-components";
import StyledInput from "../../common/Input/StyledInput";
import { BottomButton } from "../../common/Button/LargeButton";

const BiddingBox = styled.div`
  background-color: #eee;
  width: 100%;
  padding: 0.625rem 0.9375rem;
  border: 2px solid #d3d3d3;
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

const ProfileContent = styled.div`
  align-items: center;
  width: 100%;
  margin-top: 3rem;
`;

const Name = styled.div`
  color: var(--gray1, #8c8c8c);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.875rem;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
  letter-spacing: 0.07rem;
  margin: 0.5rem 0;
`;

const Maintop = () => {
  const setPassword = () => {
    console.log("비밀번호변경");
  };

  const editInformation = () => {
    console.log("정보수정");
  };
  return (
    <>
      <ProfileContent>
        <Name>닉네임</Name>
        <StyledInput
          type="nickname"
          id="nickname"
          name="nickname"
          placeholder="닉네임"
          onChange={setPassword}
        />
        <Name>이메일</Name>
        <BiddingBox>
          <CashText>ssafy@ssafy.com</CashText>
        </BiddingBox>
        <Name>비밀번호</Name>
        <BiddingBox>
          <CashText>******</CashText>
        </BiddingBox>
      </ProfileContent>
      <BottomButton onClick={editInformation} text="수정" />
    </>
  );
};
export default Maintop;
