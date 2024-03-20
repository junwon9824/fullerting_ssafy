import styled from "styled-components";
import CropList from "../diary/CropList";
import Close from "/src/assets/svg/close.svg";
interface Close {
  closeModal: () => void;
}
const Modal = styled.div`
  width: 100%;
  height: 21.1875rem;
  background-color: #ffffff;
  justify-content: center;
  align-items: center;
  display: flex;
  position: relative;
`;
const ListBox = styled.div`
  width: 90%;
  height: 85%;
  overflow-y: scroll;
  /* border: 2px solid #000; */
  position: absolute;
  bottom: 0;
`;
const TextBox = styled.div`
  width: 100%;
  height: 15%;
  font-size: 1.5rem;
  text-align: center;
  position: absolute;
  font-weight: bold;
  top: 0;
  display: flex;
  justify-content: center;
  align-items: center;
`;
const SvgBox = styled.img`
  height: auto;
  display: flex;
  right: 1rem;
  justify-content: center;
  align-items: center;
  position: absolute;
`;
const SelectModal = ({ closeModal }: Close) => {
  return (
    <Modal>
      <TextBox>
        작물 일지 목록
        <SvgBox src={Close} onClick={closeModal} />
      </TextBox>
      <ListBox>
        <CropList />
      </ListBox>
    </Modal>
  );
};

export default SelectModal;
