import { atom } from "jotai";

export const imageFilesAtom = atom<File[]>([]);
export const selectedDiaryIdAtom = atom<null | number>(null);
export const selectedCategory = atom<number>(0);
