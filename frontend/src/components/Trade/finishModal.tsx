import styled from "styled-components";
import Button from "../common/Button/primaryButton";
import Tree from "/src/assets/images/tree.png";
interface ButtonType {
  onClick: () => void;
  text: string;
}

interface CheckTextType {
  CheckText: string;
}

const ModalBox = styled.div`
  width: 16.375rem;
  height: 9.0625rem;
  border-radius: 0.75rem;
  background: #fff;
  justify-content: center;
  align-items: center;
  display: flex;
  border: 3px solid #000000;
  position: relative;
`;

const Container = styled.div`
  width: 9.5rem;
  height: 5rem;
  display: flex;
  position: relative;
  flex-direction: column;
`;
const BtnContainer = styled.div`
  width: 100%;
  height: auto;
  justify-content: space-between;
  bottom: 0;
  z-index: 2;
  display: flex;
  position: absolute;
`;

const TextContainer = styled.div`
  width: 100%;
  height: auto;
  text-align: center;
  top: 0;
  color: #000;
  font-size: 0.8125rem;
  font-weight: 400;
`;
const ImageContainer = styled.img`
  width: 6.1875rem;
  height: 7.5rem;
  position: absolute;
  bottom: 0;
  right: 0;
  z-index: 1;
`;
const CheckButton = ({ onClick, text }: ButtonType) => {
  return (
    <Button
      width={4.125}
      height={1.875}
      color="#ffffff"
      backgroundColor="#A0D8B3"
      onClick={onClick}
      fontWeight="bold"
      text={text}
    />
  );
};
const CancleButton = ({ onClick, text }: ButtonType) => {
  return (
    <Button
      width={4.125}
      height={1.875}
      color="#ffffff"
      backgroundColor="#8C8C8C"
      onClick={onClick}
      fontWeight="bold"
      text={text}
    />
  );
};
const handleCancleClick = () => {};
const handleCheckClick = () => {};
const CheckModal = ({ CheckText }: CheckTextType) => {
  return (
    <ModalBox>
      <Container>
        <TextContainer>{CheckText}</TextContainer>
        <BtnContainer>
          <CancleButton onClick={handleCancleClick} text="취소" />
          <CheckButton onClick={handleCheckClick} text="확인" />
        </BtnContainer>
      </Container>
      <ImageContainer src={Tree} alt="tree" />
    </ModalBox>
  );
};

export default CheckModal;
