import "./App.css";
import { QueryClient, QueryClientProvider } from "@tanstack/react-query";
import { ReactQueryDevtools } from "@tanstack/react-query-devtools";
import { Provider } from "jotai";
import { ThemeProvider } from "styled-components";

import "./assets/fonts/font.css";
import router from "./routes/router";
import GlobalStyle from "./styles/globalStyle";
import { theme } from "./styles/theme";
import { RouterProvider } from "react-router-dom";

const queryClient = new QueryClient();

function App() {
  return (
    <>
      <QueryClientProvider client={queryClient}>
        <ReactQueryDevtools />
        <Provider>
          <ThemeProvider theme={theme}>
            <GlobalStyle />
            <RouterProvider router={router} />
          </ThemeProvider>
        </Provider>
      </QueryClientProvider>
    </>
  );
}

export default App;
