import Button from "./primaryButton";

interface LargeButtonType {
  onClick: () => void;
  children: string;
}

const LargeButton = ({ onClick, children }: LargeButtonType) => {
  return (
    <Button
      width={19.875}
      height={3.125}
      color="#ffffff"
      backgroundColor="#A0D8B3"
      onClick={onClick}
      fontWeight="bold"
      children={children}
    />
  );
};

export default LargeButton;
