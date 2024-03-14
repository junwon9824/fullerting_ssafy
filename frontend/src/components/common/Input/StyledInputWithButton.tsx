import { ChangeEvent, MouseEvent } from "react";
import styled from "styled-components";

interface StyledInputWithButtonType {
  label: string;
  type: string;
  id: string;
  name: string;
  placeholder: string;
  onChange: (event: ChangeEvent<HTMLInputElement>) => void;
  onClick: (event: MouseEvent<HTMLButtonElement>) => void;
  disabled?: boolean;
}

const Input = styled.input`
  border: 2px solid ${({ theme }) => theme.colors.gray1};
  border-radius: 0.5rem;
  width: 15.25rem;
  height: 3rem;
  &:focus {
    border-color: ${({ theme }) => theme.colors.primary};
  }
  &::placeholder {
    color: ${({ theme }) => theme.colors.gray1};
  }
  font-size: 0.875rem;
  font-weight: bold;
  padding: 0.75rem 1.25rem;
  gap: 0.625rem;
`;

const FlexBox = styled.div`
  display: flex;
  justify-content: space-between;
  align-items: center;
  width: 19.875rem;
`;
const ConfirmButton = styled.button`
  width: 8.3125rem;
  height: 5.125rem;
  background-color: ${({ theme }) => theme.colors.primary};
  color: ${({ theme }) => theme.colors.white};
  text-align: center;
  width: 3.75rem;
  height: 3rem;
  font-weight: 600;
  border-radius: 0.5rem;
`;

const Label = styled.label`
  color: ${({ theme }) => theme.colors.gray0};
  text-align: center;
  font-size: 0.875rem;
  font-weight: bold;
`;

const InputBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: space-between;
  align-items: flex-start;
  width: 19.875rem;
  height: 4.5rem;
`;

const StyledInputWithButton = ({
  label,
  type,
  id,
  name,
  placeholder,
  onChange,
  onClick,
  disabled,
}: StyledInputWithButtonType) => {
  return (
    <InputBox>
      <Label>{label}</Label>
      <FlexBox>
        <Input
          type={type}
          id={id}
          name={name}
          placeholder={placeholder}
          onChange={onChange}
          disabled={disabled}
        />
        <ConfirmButton onClick={onClick} disabled={disabled}>
          확인
        </ConfirmButton>
      </FlexBox>
    </InputBox>
  );
};

export default StyledInputWithButton;
