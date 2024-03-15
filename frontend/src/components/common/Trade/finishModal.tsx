import styled from "styled-components";
import Button from "../Button/primaryButton";

interface LargeButtonType {
  onClick: () => void;
  children: string;
}

const ModalBox = styled.div`
  width: 16.375rem;
  height: 9.0625rem;
  border-radius: 0.75rem;
  background: #fff;
  justify-content: center;
  align-items: center;
  display: flex;
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
  border: 3px solid #000000;
  display: flex;
  position: absolute;
`;

const TextContainer = styled.div`
  width: 100%;
  height: auto;
  text-align: center;
  top: 0;
`;
const CheckButton = ({ onClick, children }: LargeButtonType) => {
  return (
    <Button
      width={4.125}
      height={1.875}
      color="#ffffff"
      backgroundColor="#A0D8B3"
      onClick={onClick}
      fontWeight="bold"
      children={children}
    />
  );
};
const CancleButton = ({ onClick, children }: LargeButtonType) => {
  return (
    <Button
      width={4.125}
      height={1.875}
      color="#ffffff"
      backgroundColor="#8C8C8C"
      onClick={onClick}
      fontWeight="bold"
      children={children}
    />
  );
};
const handleCancleClick = () => {};
const handleCheckClick = () => {};
const CheckModal = () => {
  return (
    <ModalBox>
      <Container>
        <TextContainer />
        <BtnContainer>
          <CancleButton onClick={handleCancleClick} children="취소" />
          <CheckButton onClick={handleCheckClick} children="확인" />
        </BtnContainer>
      </Container>
    </ModalBox>
  );
};

export default CheckModal;
