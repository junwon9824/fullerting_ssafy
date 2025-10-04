import { createGlobalStyle } from "styled-components";
import { reset } from "styled-reset";

const GlobalStyle = createGlobalStyle`

${reset}

html,
  body {
    padding: 0;
    margin: 0;
    min-width: 360px;
    font-family: GamtanRoadDotum;
  }

  *,
  *:before,
  *:after {
    font-family: GamtanRoadDotum;
    box-sizing: border-box;
  }

  :focus {
    outline: none;
    border: none;
  }

  button {
    background: none;
    padding: 0;
    border: none;
    cursor: pointer;
  }
  
  input {
    border:none;
  }

  a {
    color: inherit;
    text-decoration: none;
    cursor: pointer;
  }

  hr {
    border:0;
  }
`;

export default GlobalStyle;
