import styled from "styled-components";
import map from "/src/assets/svg/map.svg";
import { useNavigate } from "react-router-dom";

const MainBox = styled.div`
  display: flex;
  align-items: center;
  justify-content: center;
  gap: 0.8125rem;
  height: 3.5rem;
`;

const LogoContent = styled.div`
  color: #000;
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.75rem;
  font-style: normal;
  font-weight: 600;
  line-height: 1.125rem;
  display: flex;
  flex-direction: column;
  align-items: flex-start;
`;

const LogoText = styled.div`
  color: var(--sub0, #a0d8b3);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 1.3rem;
  font-style: normal;
  font-weight: 800;
  line-height: 1.125rem;
  margin-bottom: 0.5rem;
`;

const ImageWrapper = styled.div`
  position: relative;
  line-height: 0;
`;

const Button = styled.button`
  color: white;
  position: absolute;
  width: 4.5rem;
  height: 2rem;
  flex-shrink: 0;
  border-radius: 0.5rem;
  background: var(--sub0, #a0d8b3);
  top: 50%;
  left: 50%;
  font-size: 0.7rem;
  transform: translate(-50%, -50%);
`;

const MainMap = () => {
  const navigate = useNavigate();

  const handleButtonClick = () => {
    navigate("/join");
  };
  return (
    <MainBox>
      <LogoContent>
        <LogoText>전국</LogoText>
        텃밭정보 보러 가기
      </LogoContent>
      <ImageWrapper>
        <img src={map} alt="지도" />
        <Button onClick={handleButtonClick}>지도 보기</Button>
      </ImageWrapper>
    </MainBox>
  );
};

export default MainMap;
