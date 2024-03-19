import { useAtom } from "jotai";
import { cropAtom } from "../../stores/diary";
import styled from "styled-components";

const CropType = styled.div`
  font-size: 1.5rem;
  font-weight: bold;
`;

const TipsBox = styled.div`
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
  const [crop, setCrop] = useAtom(cropAtom);

  const tips: TipType[] = [
    {
      cropTipId: 1,
      cropTipGrowthStep: 1,
      cropTipContent:
        "씨앗 선택: 품종 선택은 성공에 중요합니다. 잘 알려진 토마토 품종 중 하나를 선택하십시오. 발아 환경: 씨앗을 발아시키기 위해 따뜻하고 습한 환경이 필요합니다. 20-25°C 정도의 온도가 이상적입니다.토양: 씨앗을 심기 위해 재배용 토양 혼합물을 사용하고, 씨앗을 1-2cm 깊이에 심어주세요.유지 관리: 토양을 충분히 촉촉하게 유지하고, 발아를 도와주기 위해 빛을 제공하는 것이 중요합니다. 발아한 후에는 강한 빛을 제공해주세요.",
    },
    {
      cropTipId: 2,
      cropTipGrowthStep: 2,
      cropTipContent:
        "식물 이동: 식물이 충분히 성장한 후, 토양을 이동시켜 공간을 확보해주세요. 각 식물 간의 간격을 충분히 확보하여 공기순환을 유지합니다. 물 주기: 토마토는 균일한 습도를 유지해야 합니다. 토양이 건조해지지 않도록 꾸준히 물을 주세요. 물이 토양에 침투할 수 있도록 흠씬 관리해주세요. 건강 관리: 잎의 출혈이나 병해충에 대비하여 정기적인 검사와 케어를 진행하세요. 필요시 병해충 방제제를 사용하세요.",
    },
    {
      cropTipId: 3,
      cropTipGrowthStep: 3,
      cropTipContent:
        "수확 시기: 토마토가 충분히 익어서 색이 진하고 윤기가 있을 때 수확하세요. 씨앗에서 열매가 붉게 변하는 것을 확인할 수 있습니다. 수확 방법: 토마토를 햇볕에 말리지 않도록 산란에 주의하여 열매를 수확하세요. 살짝 닳도록 손질하여 저장해주세요. 재배 지속: 토마토를 수확한 후에도 식물을 계속 관리하여 재배를 유지하세요. 필요한 경우 쓸개를 주고, 영양분을 보충해주세요.",
    },
  ];

  return (
    <TipsBox>
      <CropType>{crop.cropTypeName}</CropType>
      {tips.map((tip) => (
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
