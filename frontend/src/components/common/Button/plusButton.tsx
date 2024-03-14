import React, { useState } from "react";
import styled from "styled-components";

interface PlusButtonProps {
  height?: number;
  width?: number;
  backgroundColor?: string;
  fontSize?: string;
  padding?: string;
  color?: string;
  children?: React.ReactNode;
  onClick?: () => void; // 부모 컴포넌트에서 전달받을 onClick 함수 추가
}

const StyledPlusButton = styled.button`
  width: 25px;
  height: 25px;
  border-radius: 50%;
  color: white;
  background-color: #a0d8b3;
  font-size: 18px;
`;

const AdditionalButtonsContainer = styled.div`
  display: flex;
  flex-direction: column;
  gap: 5px;
  position: absolute;

  transform: translateX(3px) translateY(-170%);
`;
const ButtonContainer = styled.div`
  position: relative;
  display: inline-block;
`;
const PlusButton: React.FC<PlusButtonProps> = ({ onClick, ...props }) => {
  const [isVisible, setIsVisible] = useState(false);

  const handleButtonClick = () => {
    setIsVisible(!isVisible);
    if (onClick) onClick();
  };

  return (
    <ButtonContainer>
      <StyledPlusButton onClick={handleButtonClick} {...props}>
        {props.children}
      </StyledPlusButton>
      {isVisible && (
        <AdditionalButtonsContainer>
          <StyledPlusButton
            onClick={() => console.log("1번")}
            style={{
              backgroundColor: "#E5F9DB",
              width: "20px",
              height: "20px",
            }} // 추가 버튼 색상 설정
          >
            1
          </StyledPlusButton>
          <StyledPlusButton
            onClick={() => console.log("2번")}
            style={{
              backgroundColor: "#E5F9DB",
              width: "20px",
              height: "20px",
            }} // 추가 버튼 색상 설정
          >
            2
          </StyledPlusButton>
        </AdditionalButtonsContainer>
      )}
    </ButtonContainer>
  );
};
export default PlusButton;
