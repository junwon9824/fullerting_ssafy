import { ChangeEvent } from "react";
import styled from "styled-components";

interface StyledInputType {
  label?: string;
  type: string;
  id: string;
  name: string;
  placeholder: string;
  value?: string;
  onChange: (event: ChangeEvent<HTMLInputElement>) => void;
  disabled?: boolean;
}

const Input = styled.input`
  display: inline-flex;
  border: 2px solid ${({ theme }) => theme.colors.gray1};
  border-radius: 0.5rem;
  width: 19.875rem;
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
  height: 4.5625rem;
`;

const StyledInput = ({
  label,
  type,
  id,
  name,
  placeholder,
  value,
  onChange,
  disabled,
}: StyledInputType) => {
  return (
    <>
      {!label && (
        <Input
          type={type}
          id={id}
          name={name}
          placeholder={placeholder}
          onChange={onChange}
          value={value}
          disabled={disabled}
        />
      )}
      {label && (
        <InputBox>
          <Label>{label}</Label>
          <Input
            type={type}
            id={id}
            name={name}
            placeholder={placeholder}
            onChange={onChange}
            value={value}
            disabled={disabled}
          />
        </InputBox>
      )}
    </>
  );
};

export default StyledInput;
