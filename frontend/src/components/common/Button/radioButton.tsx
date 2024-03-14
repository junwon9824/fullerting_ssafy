import React from "react";
import styled from "styled-components";

interface RadioButtonProps {
  name: string;
  value: string;
  checked?: boolean;
  fontSize?: number;
  onChange: (event: React.ChangeEvent<HTMLInputElement>) => void;
}

const StyledRadioButton = styled.label`
  display: inline-block;
  border-radius: 100px;
  font-size: 12px;
  background-color: #a0d8b3;
  color: #c8c8c8;
  margin-right: 8px;
  cursor: pointer;

  div {
    background-color: #ffffff;
    color: #c8c8c8;
    padding: 8px 12px;
    border: 1px solid #eee;
    border-radius: 100px;
  }

  input[type="radio"] {
    display: none;

    &:checked + div {
      background-color: #a0d8b3;
      color: var(--gray2, #ffffff);
    }
  }
`;

const RadioButton: React.FC<RadioButtonProps> = ({
  name,
  value,
  checked,
  onChange,
}) => {
  return (
    <StyledRadioButton>
      <input
        type="radio"
        name={name}
        value={value}
        checked={checked}
        onChange={onChange}
      />
      <div>{value}</div>
    </StyledRadioButton>
  );
};

export default RadioButton;
