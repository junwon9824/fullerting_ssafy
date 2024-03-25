import styled from "styled-components";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import { NavBar, TopBar } from "../../components/common/Navigator/navigator";
import CropProfile from "../../components/diary/CropProfile";
import Button from "../../components/common/Button/primaryButton";
import RecognizeButton from "../../components/diary/RecognizeButton";
import DiaryList from "../../components/diary/DiaryList";
import MenuBar from "../../components/diary/MenuBar";
import { useAtom } from "jotai";
import { cropAtom, menuAtom } from "../../stores/diary";
import CropTips from "../../components/diary/CropTips";
import { useMutation, useQuery } from "@tanstack/react-query";
import {
  deleteCrop,
  getCropData,
  getDiaryList,
  updateHarvest,
} from "../../apis/DiaryApi";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";

const TopBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 0.5rem;
`;

const MiddleBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 0.5rem;
  margin-top: 0.5rem;
`;

const ButtonBox = styled.div`
  display: flex;
  width: 100%;
  justify-content: space-between;
`;

const PlusButtonBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: fixed;
  right: 1.25rem;
  bottom: 1.25rem;
  margin-bottom: 3.625rem;
  gap: 0.56rem;
`;

const PlusButton = styled.button`
  border-radius: 50%;
  width: 3.125rem;
  height: 3.125rem;
  background-color: ${({ theme }) => theme.colors.primary};
`;

const AdditionalButtonsContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  gap: 0.3rem;
`;

const StyledPlusButton = styled.button`
  width: 3rem;
  height: 3rem;
  border-radius: 50%;
  background-color: ${({ theme }) => theme.colors.sub0};
`;

const SVGBox = styled.svg`
  display: flex;
  justify-content: center;
  align-items: center;
  width: 100%;
  height: 45%;
`;

const FixedContainer = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  position: fixed;
  top: 3.125rem;
  width: 100%;
  background-color: white;
  z-index: 3;

  width: 19.875rem;
  gap: 1.5rem;
  padding: 1.12rem 0;
`;

const DiaryPage = () => {
  const [isVisible, setIsVisible] = useState(false);
  const [crop, setCrop] = useAtom(cropAtom);
  const [menu, setMenu] = useAtom(menuAtom);
  const { packDiaryId } = useParams();
  const navigate = useNavigate();

  useEffect(() => {
    setMenu("다이어리");
  }, []);

  const {
    data: cropData,
    isSuccess,
    refetch: refetchCropData,
  } = useQuery({
    queryKey: ["cropData"],
    queryFn: packDiaryId ? () => getCropData(packDiaryId) : undefined,
  });

  const { data: diaryList } = useQuery({
    queryKey: ["diaryList"],
    queryFn: packDiaryId ? () => getDiaryList(packDiaryId) : undefined,
  });

  if (isSuccess) {
    setCrop(cropData);
  }

  const { mutate: updateMutate } = useMutation({
    mutationFn: updateHarvest,
    onSuccess: () => {
      refetchCropData();
    },
    onError: (err) => {
      console.log(err);
    },
  });

  const { mutate: deleteMutate } = useMutation({
    mutationFn: deleteCrop,
    onSuccess: () => {
      navigate("/diary");
    },
    onError: (err) => {
      console.log(err);
    },
  });

  const handleHarvestClick = () => {
    if (!packDiaryId) return;
    updateMutate(packDiaryId);
  };

  const handleDeleteCrop = () => {
    if (!packDiaryId) return;
    deleteMutate(packDiaryId);
  };

  const handleButtonClick = () => {
    setIsVisible(!isVisible);
  };

  return (
    <>
      <TopBar title="작물일기" showEdit={true} deleteFunc={handleDeleteCrop} />
      <LayoutMainBox>
        <LayoutInnerBox>
          <FixedContainer>
            <TopBox>
              {cropData && <CropProfile crop={cropData} />}
              <ButtonBox>
                <RecognizeButton />
                <Button
                  onClick={handleHarvestClick}
                  width={9.5}
                  height={2.5625}
                  borderRadius={1.28125}
                  backgroundColor={
                    cropData?.packDiaryCulEndAt !== null ? "#c8c8c8" : "#A0D8B3"
                  }
                  color="white"
                  fontSize="1"
                  fontWeight="bold"
                  text="수확하기"
                />
              </ButtonBox>
            </TopBox>
            <MenuBar />
          </FixedContainer>
          <MiddleBox style={{ marginTop: "16.4rem" }}>
            {menu === "작물꿀팁" ? (
              <CropTips />
            ) : (
              diaryList && <DiaryList diaries={diaryList} />
            )}
          </MiddleBox>
        </LayoutInnerBox>
      </LayoutMainBox>
      <PlusButtonBox>
        {isVisible && (
          <AdditionalButtonsContainer>
            <StyledPlusButton onClick={() => navigate(`water`)}>
              <SVGBox
                xmlns="http://www.w3.org/2000/svg"
                width="14"
                height="17"
                viewBox="0 0 14 17"
                fill="none"
              >
                <path
                  d="M6.66667 0C2.225 3.79167 0 7.06667 0 9.83333C0 13.9833 3.16667 16.6667 6.66667 16.6667C10.1667 16.6667 13.3333 13.9833 13.3333 9.83333C13.3333 7.06667 11.1083 3.79167 6.66667 0ZM6.66667 15C3.875 15 1.66667 12.8583 1.66667 9.83333C1.66667 7.88333 3.29167 5.3 6.66667 2.21667C10.0417 5.3 11.6667 7.875 11.6667 9.83333C11.6667 12.8583 9.45833 15 6.66667 15ZM3.19167 10C3.5 10 3.75 10.2167 3.80833 10.5167C4.15 12.3667 5.70833 13 6.84167 12.9083C7.2 12.8917 7.5 13.175 7.5 13.5333C7.5 13.8667 7.23333 14.1417 6.9 14.1583C5.125 14.2667 3.05 13.25 2.575 10.725C2.50833 10.35 2.80833 10 3.19167 10Z"
                  fill="black"
                />
              </SVGBox>
            </StyledPlusButton>
            <StyledPlusButton onClick={() => navigate(`create`)}>
              <SVGBox
                xmlns="http://www.w3.org/2000/svg"
                width="20"
                height="20"
                viewBox="0 0 20 20"
                fill="none"
              >
                <path
                  d="M10 5.83333C10 4.94928 9.64881 4.10143 9.02369 3.47631C8.39857 2.85119 7.55072 2.5 6.66667 2.5H1.66667V15H7.5C8.16304 15 8.79893 15.2634 9.26777 15.7322C9.73661 16.2011 10 16.837 10 17.5M10 5.83333V17.5M10 5.83333C10 4.94928 10.3512 4.10143 10.9763 3.47631C11.6014 2.85119 12.4493 2.5 13.3333 2.5H18.3333V15H12.5C11.837 15 11.2011 15.2634 10.7322 15.7322C10.2634 16.2011 10 16.837 10 17.5"
                  stroke="black"
                  stroke-width="1.5"
                  stroke-linecap="round"
                  stroke-linejoin="round"
                />
              </SVGBox>
            </StyledPlusButton>
          </AdditionalButtonsContainer>
        )}
        <PlusButton onClick={handleButtonClick}>
          <SVGBox
            xmlns="http://www.w3.org/2000/svg"
            width="26"
            height="26"
            viewBox="0 0 26 26"
            fill="none"
          >
            <path
              d="M5.70834 13H20.2917M13 5.70837V20.2917"
              stroke="white"
              stroke-width="2"
              stroke-linecap="round"
              stroke-linejoin="round"
            />
          </SVGBox>
        </PlusButton>
      </PlusButtonBox>
      <NavBar />
    </>
  );
};

export default DiaryPage;
