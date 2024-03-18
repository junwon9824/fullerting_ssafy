import styled from "styled-components";

const MainContainer = styled.div`
  justify-content: center;
  flex-direction: column;
  height: 15rem;
  background-color: #a0d8b3;
  padding: 1rem 0.8rem;
`;
const TextBox = styled.div`
  border-radius: 0.78125rem;
  background: var(--sub1, #e5f9db);
  font-size: 0.75rem;
  width: 12rem;
  height: 1.5rem;
  display: flex;
  font-weight: 600;
  justify-content: center;
  align-items: center;
  text-align: center;
  margin: 1rem 0 0 0;
`;
const MainText = styled.div`
  color: #fffefe;
  font-size: 1.7rem;
  font-style: normal;
  font-weight: 600;
  line-height: normal;
`;

const DiaryBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  text-align: center;
  margin: 0.7rem 0;
  padding: 1rem;
  width: 21rem;
  height: 7.7rem;
  border-radius: 0.9375rem;
  background: rgba(255, 255, 255, 0.26);
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
`;
const Maintop = () => {
  return (
    <MainContainer>
      <MainText>풀러팅</MainText>
      <TextBox>"2개의 작물을 가꾸고 계시군요"</TextBox>
      <DiaryBox>토마토</DiaryBox>
    </MainContainer>
  );
};

export default Maintop;
