import { atom } from "jotai";

export const menuAtom = atom("다이어리");

export const cropAtom = atom<CropType | null>(null);
