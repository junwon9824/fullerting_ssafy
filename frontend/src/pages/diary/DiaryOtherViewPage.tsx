import styled from "styled-components";
import {
  LayoutInnerBox,
  LayoutMainBox,
} from "../../components/common/Layout/Box";
import {
  CommonTopBar,
  NavBar,
} from "../../components/common/Navigator/navigator";
import CropProfile from "../../components/diary/CropProfile";
import Button from "../../components/common/Button/primaryButton";
import DiaryList from "../../components/diary/DiaryList";
import MenuBar from "../../components/diary/MenuBar";
import { useAtom } from "jotai";
import { cropAtom, menuAtom } from "../../stores/diary";
import CropTips from "../../components/diary/CropTips";
import { useMutation, useQuery } from "@tanstack/react-query";
import { deleteCrop, getCropData, updateHarvest } from "../../apis/DiaryApi";
import { useNavigate, useParams } from "react-router-dom";
import { useEffect, useState } from "react";

const TopBox = styled.div`
  display: flex;
  flex-direction: column;
  width: 100%;
  gap: 0.2rem;
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
  gap: 1.3rem;
`;

const DiaryOtherViewPage = () => {
  const [isVisible, setIsVisible] = useState(false);
  const [, setCrop] = useAtom(cropAtom);
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

  if (isSuccess) {
    setCrop(cropData);
  }
  console.log("크롭데이터", cropData);

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
      navigate("/crop");
    },
    onError: (err) => {
      console.log(err);
    },
  });

  const handleHarvestClick = () => {
    if (cropData.packDiaryCulEndAt !== null) return;
    if (!packDiaryId) return;

    const isConfirmed = window.confirm("정말로 수확하시겠습니까?");
    if (isConfirmed) {
      updateMutate(packDiaryId);
    }
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
      <CommonTopBar title="작물일기" />
      <LayoutMainBox>
        <LayoutInnerBox>
          <FixedContainer>
            <MenuBar />
          </FixedContainer>
          <MiddleBox>
            {menu === "작물꿀팁" ? <CropTips /> : <DiaryList />}
          </MiddleBox>
        </LayoutInnerBox>
      </LayoutMainBox>

      <NavBar />
    </>
  );
};

export default DiaryOtherViewPage;
