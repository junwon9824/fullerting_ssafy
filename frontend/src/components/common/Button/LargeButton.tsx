import styled from "styled-components";
import Button from "./primaryButton";

const ButtonBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  position: fixed;
  bottom: 0;
  padding: 1rem 0;
  width: 100%;
  background-color: ${({ theme }) => theme.colors.white};
`;

interface LargeButtonType {
  onClick: () => void;
  text: string;
}

const LargeButton = ({ onClick, text }: LargeButtonType) => {
  return (
    <Button
      width={19.875}
      height={3.125}
      color="#ffffff"
      backgroundColor="#A0D8B3"
      onClick={onClick}
      fontWeight="bold"
      text={text}
      borderRadius={0.5}
      fontSize="1"
    />
  );
};

const BottomButton = ({ onClick, text }: LargeButtonType) => {
  return (
    <ButtonBox>
      <Button
        width={19.875}
        height={3.125}
        color="#ffffff"
        backgroundColor="#A0D8B3"
        onClick={onClick}
        fontWeight="bold"
        text={text}
        borderRadius={0.5}
        fontSize="1"
      />
    </ButtonBox>
  );
};

export { LargeButton, BottomButton };
