import { useMutation, useQuery, useQueryClient } from "@tanstack/react-query";
import { useParams } from "react-router-dom";
import { getDetailCommunities, toggleLike } from "../../apis/CommunityApi";
import styled from "styled-components";
import grayheart from "../../assets/svg/grayheart.svg";
import like from "../../assets/svg/greenheart.svg";
import Speech from "../../assets/svg/Speech Bubble.svg";
import { Swiper, SwiperSlide } from "swiper/react";
import { Navigation } from "swiper/modules";
import { content, files, images, oldfiles } from "../../stores/community";
import { useAtom } from "jotai";
import { imageFilesAtom, oldImagesAtom } from "../../stores/trade";
import { useEffect } from "react";

interface ImgProps {
  backgroundImage: string;
}

const All = styled.div`
  width: 100%;
  font-family: "GamtanRoad Dotum TTF";
  height: 100%;
  padding-right: 2rem;
  padding-left: 2.5rem;
  align-items: center;
`;

const Content = styled.div`
  font-size: 1rem;
  font-weight: bold;
  font-size: 0.875rem;
  font-weight: 400;
  line-height: 1.25rem;
`;

const Img = styled.div<ImgProps>`
  width: 18rem;
  height: 19rem;
  background-image: ${(props) => `url(${props.backgroundImage})`};
  background-size: cover;
  background-position: center;
  border-radius: 0.875rem;
  margin-bottom: 1rem;
  margin-top: 1rem;
`;

const Heart = styled.div<ImgProps>`
  width: 0.9375rem;
  height: 0.85225rem;
  background-image: url(${(props) => props.backgroundImage});
  background-size: cover;
  background-position: center;
  flex-shrink: 0;
`;

const Num = styled.div`
  color: var(--gray2, #c8c8c8);
  font-family: "GamtanRoad Dotum TTF";
  font-size: 0.875rem;
  font-style: normal;
  font-weight: 400;
  line-height: normal;
`;

const HeartBox = styled.div`
  display: flex;
  width: 21rem;
  padding: 0rem 3rem 0rem 0;
  justify-content: flex-end;
  align-items: center;
  gap: 0.375rem;
  margin-top: 1rem;
  margin-bottom: 1rem;
  margin-right: 1rem;
`;
interface ImageInfo {
  id: number;
  imgStoreUrl: string;
  exArticle: number;
  diary: number;
}

const CommunityContent = () => {
  const [curcontent, setCurContent] = useAtom(content);
  const [curimgs, setCurImgs] = useAtom(oldfiles);
  const [imageFiles, setImageFiles] = useAtom(imageFilesAtom);
  const [images, setImages] = useAtom(oldImagesAtom);

  const queryClient = useQueryClient();

  const { communityId } = useParams<{ communityId: string }>();



  const { data: community, isLoading } = useQuery({
    queryKey: ["CommunityDetail"],
    queryFn: communityId ? () => getDetailCommunities(communityId) : undefined,
  });

  console.log(community);
  console.log(community?.content);
  console.log(community?.imgs);
  console.log(community?.imgs[0].imgStoreUrl);


  // if (community?.imgs) {
  //   //   // 실행할 작업들

  //   const initialImages = community?.imgs?.map(
  //     (img: ImageInfo) => img

  //   ); // 예시 코드, 실제 구조에 맞게 조정 필요
  //   console.log(initialImages)
  //   setImages(initialImages);

  // }


  // setImages(community?.imgs);

  setCurContent(community?.content)


  // // URL 파라미터가 변경될 때만 실행되도록 설정
  // if ( community.imgs) {
  //   // 실행할 작업들

  //   const initialImages = community?.imgs?.map(
  //     (img: ImageInfo) => img.imgStoreUrl

  //     ); // 예시 코드, 실제 구조에 맞게 조정 필요
  //     console.log(initialImages)
  //     setImageFiles(initialImages);

  // }

  useEffect(() => {
    // 컴포넌트가 마운트될 때와 community.imgs가 변경될 때만 실행
    if (community?.imgs) {
      const initialImages = community.imgs.map((img: ImageInfo) => img.imgStoreUrl);
      setImageFiles(initialImages);
      setImages (community?.imgs);
    }
  }, [community?.imgs]); // community.imgs가 변경될 때만 useEffect 실행


  const { mutate } = useMutation({
    mutationFn: () => toggleLike(communityId),
    onSuccess: () => {
      queryClient.invalidateQueries(["CommunityDetail", communityId]);
    },
    onError: (error) => {
      console.log(error);
    },
  });

  if (isLoading) {
    return <div>Loading..</div>;
  }

  const handleLikeClick = () => {
    mutate();
  };

  return (
    <All>
      <Swiper
        modules={[Navigation]}
        navigation
        spaceBetween={50}
        slidesPerView={1}
      >
        {community.imgs.map((imgObject) => (
          <SwiperSlide key={imgObject.id}>
            <Img backgroundImage={imgObject.imgStoreUrl} />
          </SwiperSlide>
        ))}

      </Swiper>
      <Content>{community.content}</Content>

      <HeartBox>
        <Heart
          backgroundImage={community.mylove ? like : grayheart}
          onClick={handleLikeClick}
        />
        <Num>{community.love}</Num>
        <img src={Speech} alt="" />
        <Num>{community.commentsize}</Num>
      </HeartBox>
    </All>
  );
};

export default CommunityContent;
