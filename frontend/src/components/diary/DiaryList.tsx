import styled from "styled-components";

const DiaryBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  /* 스크롤 넣기 */
  /* background-color: aquamarine; */
  gap: 1rem;
`;

const DiaryCardBox = styled.div`
  display: flex;
  flex-direction: row;
  align-items: flex-start;
  justify-content: space-between;
`;

const DateBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: center;
  justify-content: center;
  text-align: center;
  color: ${({ theme }) => theme.colors.white};
  background-color: ${({ theme }) => theme.colors.primary};
  gap: 0.1rem;

  width: 2.35rem;
  height: 2.35rem;
  border-radius: 0.3125rem;
`;

const Month = styled.span`
  font-size: 0.75rem;
`;

const Day = styled.span`
  font-size: 1.1rem;
`;

const BorderBox = styled.div`
  width: 17.125rem;
  border-radius: 0rem 0.9375rem 0.9375rem 0.9375rem;
  border: 2px solid ${({ theme }) => theme.colors.primary};
`;

const ContentBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: center;
  flex-direction: column;
  padding: 0.8rem;
  gap: 0.8rem;
`;

const ImageBox = styled.div`
  width: 100%;
  height: 7.6875rem;
  background-color: #41acac;
`;

const InfoBox = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  gap: 0.3rem;
`;

const Title = styled.div`
  font-size: 0.8rem;
  font-weight: bold;
`;
const Content = styled.div`
  font-size: 0.8rem;
`;
const WaterContent = styled.div`
  padding: 0.75rem 0.8rem;
  display: flex;
  flex-direction: row;
  align-items: center;

  gap: 0.5rem;
  font-size: 0.8rem;
  font-weight: bold;
`;
const WaterIcon = styled.div`
  display: flex;
  justify-items: center;
  align-items: center;
  width: 1.2rem;
  height: 1.2rem;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.blue0};
`;
const WaterIconSVG = styled.svg`
  width: 100%;
  height: 80%;
`;
const DiaryCard = () => {
  return (
    <DiaryCardBox>
      <div>
        <DateBox>
          <Month>03월</Month>
          <Day>05</Day>
        </DateBox>
      </div>
      <BorderBox>
        <ContentBox>
          <ImageBox>
            <img src="" alt="" />
          </ImageBox>
          <InfoBox>
            <Title>
              <p>토마토 빨강색</p>
            </Title>
            <Content>
              <p>토마토가 무럭무럭 자랐다.</p>
            </Content>
          </InfoBox>
        </ContentBox>
      </BorderBox>
    </DiaryCardBox>
  );
};

const WaterCard = () => {
  return (
    <DiaryCardBox>
      <div>
        <DateBox>
          <Month>03월</Month>
          <Day>05</Day>
        </DateBox>
      </div>
      <BorderBox>
        <WaterContent>
          <WaterIcon>
            <WaterIconSVG
              width="14"
              height="14"
              viewBox="0 0 14 14"
              fill="none"
              xmlns="http://www.w3.org/2000/svg"
            >
              <path
                d="M7 1.66663C4.15734 4.09329 2.73334 6.18929 2.73334 7.95996C2.73334 10.616 4.76 12.3333 7 12.3333C9.24 12.3333 11.2667 10.616 11.2667 7.95996C11.2667 6.18929 9.84267 4.09329 7 1.66663ZM7 11.2666C5.21334 11.2666 3.8 9.89596 3.8 7.95996C3.8 6.71196 4.84 5.05863 7 3.08529C9.16 5.05863 10.2 6.70663 10.2 7.95996C10.2 9.89596 8.78667 11.2666 7 11.2666ZM4.776 8.06663C4.97334 8.06663 5.13334 8.20529 5.17067 8.39729C5.38934 9.58129 6.38667 9.98663 7.112 9.92796C7.34134 9.91729 7.53334 10.0986 7.53334 10.328C7.53334 10.5413 7.36267 10.7173 7.14934 10.728C6.01334 10.7973 4.68534 10.1466 4.38134 8.53063C4.33867 8.29063 4.53067 8.06663 4.776 8.06663Z"
                fill="white"
              />
            </WaterIconSVG>
          </WaterIcon>
          <p>물주기</p>
        </WaterContent>
      </BorderBox>
    </DiaryCardBox>
  );
};

const DiaryList = () => {
  return (
    <DiaryBox>
      <DiaryCard />
      <WaterCard />
    </DiaryBox>
  );
};

export default DiaryList;
