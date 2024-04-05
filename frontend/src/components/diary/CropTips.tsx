import { useAtom } from "jotai";
import { cropAtom } from "../../stores/diary";
import styled from "styled-components";
import { useQuery } from "@tanstack/react-query";
import { getTipList } from "../../apis/DiaryApi";
import Fetching from "../common/Fetching";

const CropType = styled.div`
  font-size: 1.5rem;
  font-weight: bold;
`;

const TipsBox = styled.div`
  margin-top: 17px;
  display: flex;
  flex-direction: column;
  gap: 2rem;
`;

const TipBox = styled.div`
  display: flex;
  flex-direction: column;
  gap: 0.62rem;
`;

const GrowthStep = styled.span`
  font-size: 1rem;
  font-weight: bold;
  color: ${({ theme }) => theme.colors.gray0};
`;

const Content = styled.div`
  font-size: 1rem;
  line-height: 1.725rem;
`;

const CropTips = () => {
  const [crop] = useAtom(cropAtom);

  const { isFetching, data: tipList } = useQuery({
    queryKey: ["cropList"],
    queryFn: crop ? () => getTipList(crop.cropTypeId) : undefined,
  });

  if (isFetching) {
    return <Fetching />;
  }

  return (
    <TipsBox>
      {crop && <CropType>{crop.cropTypeName}</CropType>}
      {tipList.map((tip: TipType) => (
        <TipBox key={tip.cropTipId}>
          <GrowthStep>{tip.cropTipGrowthStep}단계</GrowthStep>
          <Content>
            <p>{tip.cropTipContent}</p>
          </Content>
        </TipBox>
      ))}
    </TipsBox>
  );
};

export default CropTips;
