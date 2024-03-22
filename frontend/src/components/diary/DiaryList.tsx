import styled from "styled-components";

const DiaryBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 100%;
  gap: 1.5rem;
`;

const DiaryCardBox = styled.div`
  display: flex;
  flex-direction: column;
  align-items: flex-start;
  justify-content: space-between;
  gap: 0.6rem;
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

  width: 2.2rem;
  height: 2.2rem;
  border-radius: 0.3125rem;
`;

const Month = styled.span`
  font-size: 0.7rem;
`;

const Day = styled.span`
  font-size: 0.9rem;
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
  height: 10rem;
  background-color: #41acac;
`;

const InfoBox = styled.div`
  width: 100%;
  display: flex;
  flex-direction: column;
  justify-content: flex-start;
  align-items: flex-start;
  gap: 0.5rem;
`;

const Title = styled.div`
  font-size: 0.9rem;
  font-weight: bold;
`;
const Content = styled.div`
  font-size: 0.9rem;
  line-height: 1.5rem;
`;
const WaterContent = styled.div`
  padding: 0.75rem 0.8rem;
  display: flex;
  flex-direction: row;
  align-items: center;

  gap: 0.5rem;
  font-size: 0.9rem;
  font-weight: bold;
`;
const WaterIcon = styled.div`
  display: flex;
  justify-items: center;
  align-items: center;
  width: 1.3rem;
  height: 1.3rem;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.blue0};
`;
const WaterIconSVG = styled.svg`
  width: 100%;
  height: 85%;
`;

const CalCardBox = styled.div`
  display: flex;
  justify-content: center;
  align-items: flex-start;
  gap: 0.5rem;
`;

const SpecialDate = styled.span`
  font-size: 0.65rem;
  color: ${({ theme }) => theme.colors.gray1};
`;

const SDateCalCardBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: flex-end;
  gap: 0.45rem;
`;

const DiaryCard = ({ diary }: { diary: DiaryEntry }) => {
  return (
    <BorderBox>
      <ContentBox>
        <ImageBox>
          <img src="" alt="" />
        </ImageBox>
        <InfoBox>
          <Title>
            <p>{diary.diaryTitle}</p>
          </Title>
          <Content>
            <p>{diary.diaryContent}</p>
          </Content>
        </InfoBox>
      </ContentBox>
    </BorderBox>
  );
};

const WaterCard = () => {
  return (
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
  );
};

const Calender = (date: { date: string }) => {
  const [, month, day] = date.date.split("-");

  return (
    <DateBox>
      <Month>{month}월</Month>
      <Day>{day}</Day>
    </DateBox>
  );
};

const DiaryList = ({ diaries }: { diaries: DiaryType[] }) => {
  const isSpecialDate = (dateString: string) => {
    const today = new Date();
    const date = new Date(dateString);
    const yesterday = new Date(today);
    yesterday.setDate(today.getDate() - 1);
    const weekAgo = new Date(today);
    weekAgo.setDate(today.getDate() - 7);

    if (date.toDateString() === today.toDateString()) {
      return "오늘";
    } else if (date.toDateString() === yesterday.toDateString()) {
      return "어제";
    } else {
      const diffTime = Math.abs(today.getTime() - date.getTime());
      const diffDays = Math.ceil(diffTime / (1000 * 60 * 60 * 24));
      return `${diffDays}일 전`;
    }
  };

  return (
    <>
      <div>calendar</div>

      <DiaryBox>
        {diaries.length === 0 ? (
          <div>다이어리를 작성해 주세요</div>
        ) : (
          diaries.map((item, index) => (
            <SDateCalCardBox>
              <SpecialDate>{isSpecialDate(item.diarySelectedAt)}</SpecialDate>
              <CalCardBox key={index}>
                <Calender date={item.diarySelectedAt} />
                <DiaryCardBox>
                  {item.getSelectedAtDiaryResponse.map((diary, idx) => (
                    <div key={idx}>
                      {diary.diaryBehavior === "다이어리" ? (
                        <DiaryCard diary={diary} />
                      ) : (
                        <WaterCard />
                      )}
                    </div>
                  ))}
                </DiaryCardBox>
              </CalCardBox>
            </SDateCalCardBox>
          ))
        )}
      </DiaryBox>
    </>
  );
};

export default DiaryList;
