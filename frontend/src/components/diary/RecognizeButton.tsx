import Button from "../common/Button/primaryButton";

const RecognizeButton = () => {
  const handleClick = () => {};

  return (
    <Button
      onClick={handleClick}
      width={9.5}
      height={2.5625}
      borderRadius={1.28125}
      backgroundColor="#A0D8B3"
      color="white"
      fontSize="1"
      fontWeight="bold"
      text="작물 인식하기"
    />
  );
};

export default RecognizeButton;
