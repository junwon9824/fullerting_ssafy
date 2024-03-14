import styled from "styled-components";
import Button from "./primaryButton";

const ButtonBox = styled.div`
  display: flex;
  justify-content: center;
  position: fixed;
  bottom: 0;
  padding: 1rem 0;
  width: 22.5rem;
  background-color: ${({ theme }) => theme.colors.white};
`;

interface LargeButtonType {
  onClick: () => void;
  children: string;
}

const LargeButton = ({ onClick, children }: LargeButtonType) => {
  return (
    <Button
      width={19.875}
      height={3.125}
      color="#ffffff"
      backgroundColor="#A0D8B3"
      onClick={onClick}
      fontWeight="bold"
      children={children}
    />
  );
};

const BottomButton = ({ onClick, children }: LargeButtonType) => {
  return (
    <ButtonBox>
      <Button
        width={19.875}
        height={3.125}
        color="#ffffff"
        backgroundColor="#A0D8B3"
        onClick={onClick}
        fontWeight="bold"
        children={children}
      />
    </ButtonBox>
  );
};

export { LargeButton, BottomButton };
