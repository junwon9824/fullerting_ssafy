import React, { useState } from "react";
import styled from "styled-components";
import book from "/src/assets/svg/book-open.svg";
import Vector from "/src/assets/svg/Vector.svg";
import { useNavigate } from "react-router-dom";

interface PlusButtonProps {
  height?: number;
  width?: number;
  backgroundColor?: string;
  fontSize?: string;
  padding?: string;
  color?: string;
  children?: React.ReactNode;
  onClick?: () => void;
}

const StyledPlusButton = styled.button`
  width: 3.125rem;
  height: 3.125rem;
  border-radius: 50%;
  color: white;
  background-color: #a0d8b3;
  font-size: 2rem;
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
  const navigate = useNavigate();
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
            onClick={() => navigate("/diary")}
            style={{
              backgroundColor: "#E5F9DB",
              width: "2.5rem",
              height: "2.5rem",
            }}
          >
            <img src={Vector} alt="" />
          </StyledPlusButton>
          <StyledPlusButton
            onClick={() => console.log("2번")}
            style={{
              backgroundColor: "#E5F9DB",
              width: "2.5rem",
              height: "2.5rem",
            }}
          >
            <img src={book} alt="" />
          </StyledPlusButton>
        </AdditionalButtonsContainer>
      )}
    </ButtonContainer>
  );
};
export default PlusButton;
