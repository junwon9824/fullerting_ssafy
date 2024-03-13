export const FONT_STYLE_NAME = {
  logo: "logo",
};

export interface FontStyle {
  fontFamily: string;
}

export type FontStyleName =
  (typeof FONT_STYLE_NAME)[keyof typeof FONT_STYLE_NAME];

export const FONT_STYLE: Record<FontStyleName, FontStyle> = {
  [FONT_STYLE_NAME.logo]: {
    fontFamily: "seolleimcool",
  },
};
