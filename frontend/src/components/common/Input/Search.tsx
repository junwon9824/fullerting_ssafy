import { ChangeEvent } from "react";
import styled from "styled-components";

interface StyledInputType {
  type: string;
  id: string;
  name: string;
  placeholder: string;
  value?: string;
  onChange: (event: ChangeEvent<HTMLInputElement>) => void;
}

const Input = styled.input`
  /* border: 2px solid ${({ theme }) => theme.colors.gray2}; */
  /* border-radius: 0.5rem; */
  /* width: 19.875rem; */
  width: 16.5rem;
  height: 3rem;
  &::placeholder {
    color: ${({ theme }) => theme.colors.gray1};
  }
  font-size: 0.875rem;
  font-weight: bold;
  padding: 0.75rem 1rem;
  background-color: ${({ theme }) => theme.colors.gray2};
  color: ${({ theme }) => theme.colors.gray1};
`;

const InputContainer = styled.div`
  padding: 0.75rem 1rem;
  display: flex;
  justify-content: flex-start;
  align-items: center;
  width: 19.875rem;
  height: 3rem;
  background-color: ${({ theme }) => theme.colors.gray2};
  color: ${({ theme }) => theme.colors.gray1};
  border-radius: 0.5rem;
`;

const StyledInput = ({
  type,
  id,
  name,
  placeholder,
  value,
  onChange,
}: StyledInputType) => {
  return (
    <InputContainer>
      <svg
        width="24"
        height="24"
        viewBox="0 0 24 24"
        fill="none"
        xmlns="http://www.w3.org/2000/svg"
      >
        <path
          d="M21.0002 21L16.7002 16.7M19 11C19 15.4183 15.4183 19 11 19C6.58172 19 3 15.4183 3 11C3 6.58172 6.58172 3 11 3C15.4183 3 19 6.58172 19 11Z"
          stroke="#8C8C8C"
          stroke-width="2"
          stroke-linecap="round"
          stroke-linejoin="round"
        />
      </svg>
      <Input
        type={type}
        id={id}
        name={name}
        placeholder={placeholder}
        onChange={onChange}
        value={value}
      />
    </InputContainer>
  );
};

export default StyledInput;
