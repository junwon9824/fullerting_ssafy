import styled from "styled-components";

interface ButtonProps {
  btnColor: string;
}
const ModalBox = styled.div`
  width: 16.375rem;
  height: 9.0625rem;
  border-radius: 0.75rem;
  background: #fff;
`;

const checkButton = styled.button<ButtonProps>`
  width: 4.125rem;
  height: 1.875rem;
  background-color: ${(props) => props.btnColor};
  border-radius: 0.625rem;
`;
