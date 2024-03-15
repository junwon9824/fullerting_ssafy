import { ChangeEvent } from "react";
import styled from "styled-components";

interface StyledTextAreaType {
  label?: string;
  isRequired?: boolean;
  name: string;
  placeholder: string;
  value?: string;
  onChange: (event: ChangeEvent<HTMLTextAreaElement>) => void;
  disabled?: boolean;
  cols?: number;
  rows?: number;
  maxLength?: number;
}

const TextArea = styled.textarea`
  border: 2px solid ${({ theme }) => theme.colors.gray1};
  border-radius: 0.5rem;
  width: 19.875rem;
  height: 8.125rem;
  &:focus {
    border: 2px solid ${({ theme }) => theme.colors.primary};
  }
  &::placeholder {
    color: ${({ theme }) => theme.colors.gray1};
  }
  font-size: 0.875rem;
  font-weight: bold;
  padding: 0.75rem 1rem;
  resize: none;
`;

const Label = styled.label`
  display: flex;
  color: ${({ theme }) => theme.colors.gray0};
  text-align: center;
  font-size: 0.875rem;
  font-weight: bold;
`;

const RedCircle = styled.div`
  width: 0.25rem;
  height: 0.25rem;
  background-color: ${({ theme }) => theme.colors.red0};
  margin: 0 0.2rem;
  border-radius: 50%;
`;

const TextAreaBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 19.875rem;
  gap: 0.6rem;
`;

const ValueCnt = styled.div`
  display: flex;
  margin-left: auto;
`;

const Span = styled.span`
  text-align: right;
  font-size: 0.75rem;
  color: ${({ theme }) => theme.colors.gray0};
  font-weight: bold;
`;

const ColoredSpan = styled.span`
  text-align: right;
  font-size: 0.75rem;
  color: ${({ theme }) => theme.colors.sub1};
  font-weight: bold;
`;

const StyledTextArea = ({
  label,
  isRequired = true,
  name,
  placeholder,
  value,
  onChange,
  disabled,
  cols,
  rows,
  maxLength,
}: StyledTextAreaType) => {
  return (
    <TextAreaBox>
      <Label>
        <p>{label}</p>
        {isRequired && <RedCircle />}
      </Label>
      <TextArea
        name={name}
        placeholder={placeholder}
        onChange={onChange}
        value={value}
        disabled={disabled}
        cols={cols}
        rows={rows}
        maxLength={maxLength}
      />
      {maxLength && (
        <ValueCnt>
          <ColoredSpan>{value?.length}</ColoredSpan>
          <Span>/{maxLength}</Span>
        </ValueCnt>
      )}
    </TextAreaBox>
  );
};

export default StyledTextArea;
