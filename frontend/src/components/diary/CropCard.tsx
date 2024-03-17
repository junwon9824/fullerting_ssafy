import styled from "styled-components";

const CardListBox = styled.div`
  display: flex;
  width: 19.875rem;
  align-items: center;
  align-content: center;
  gap: 1rem 1.125rem;
  flex-shrink: 0;
  flex-wrap: wrap;
  /* position: relative; */
`;

const CardItemBox = styled.div`
  display: flex;
  flex-direction: column;
  justify-content: center;
  align-items: center;
  width: 9.375rem;
  height: 13.3125rem;
  border-radius: 0.3125rem;
  border: 3px solid ${({ theme }) => theme.colors.primary};
  background: #fff;
  box-shadow: 0px 4px 4px 0px rgba(0, 0, 0, 0.25);
  gap: 0.3rem;
`;

const CardItemDecoBox = styled.div`
  width: 6.625rem;
  height: 1.6875rem;
  flex-shrink: 0;
`;

const CropImageBox = styled.div`
  width: 6.25rem;
  height: 6.25rem;
  flex-shrink: 0;
  border-radius: 50%;
  border: 2.5px solid #3d0c112c;
  opacity: 0.9;
  margin: 0.4rem;
`;

const CropTitle = styled.div`
  font-size: 1rem;
`;

const CropInfoBox = styled.div`
  color: ${({ theme }) => theme.colors.gray0};
  font-size: 0.6rem;
  font-weight: bold;
`;

const CropCard = () => {
  return (
    <CardListBox>
      {/* <CardItemDecoBox>
        <svg
          xmlns="http://www.w3.org/2000/svg"
          width="106"
          height="27"
          viewBox="0 0 106 27"
          fill="none"
        >
          <circle cx="5" cy="22" r="5" fill="#A8A9AD" />
          <circle cx="53" cy="22" r="5" fill="#A8A9AD" />
          <circle cx="77" cy="22" r="5" fill="#A8A9AD" />
          <circle cx="101" cy="22" r="5" fill="#A8A9AD" />
          <circle cx="29" cy="22" r="5" fill="#A8A9AD" />
          <rect x="2" width="6" height="22" rx="2" fill="#575759" />
          <rect x="26" width="6" height="22" rx="2" fill="#575759" />
          <rect x="50" width="6" height="22" rx="2" fill="#575759" />
          <rect x="74" width="6" height="22" rx="2" fill="#575759" />
          <rect x="98" width="6" height="22" rx="2" fill="#575759" />
        </svg>
      </CardItemDecoBox> */}
      <CardItemBox>
        <CropImageBox>
          <img src="" alt="" />
        </CropImageBox>
        <CropTitle>
          <p>똘똘한토마토</p>
        </CropTitle>
        <CropInfoBox>
          <span>토마토 2단계</span>
          <span> · </span>
          <span>D+21</span>
        </CropInfoBox>
      </CardItemBox>
      <CardItemBox></CardItemBox>
    </CardListBox>
  );
};

export default CropCard;
