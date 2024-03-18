import styled from "styled-components";

const Line = styled.hr`
  width: 19.86rem;
  height: 0.0625rem;
  background: ${({ theme }) => theme.colors.gray1};
`;

const HalfLine = styled.hr`
  width: 9.93rem;
  height: 0.0625rem;
  background: ${({ theme }) => theme.colors.gray1};
`;

const HalfColoredLine = styled.hr`
  width: 9.93rem;
  height: 0.1875rem;
  background: ${({ theme }) => theme.colors.primary};
`;
export { HalfLine, Line, HalfColoredLine };
