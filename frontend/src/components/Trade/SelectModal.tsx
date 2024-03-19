import styled from "styled-components";
import CropList from "../diary/CropList";

const Modal = styled.div`
  width: 100%;
  height: 21.1875rem;
  background-color: #ffffff;
`;
const SelectModal = () => {
  return (
    <Modal>
      <CropList />
    </Modal>
  );
};

export default SelectModal;
