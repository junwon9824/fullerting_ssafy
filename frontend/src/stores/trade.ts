import { atom } from "jotai";

export const imageFilesAtom = atom<File[]>([]);
export const oldImagesAtom = atom<number[]>([]);
export const selectedDiaryIdAtom = atom<null | number>(null);
export const selectedCategory = atom<number>(0);
export const likeAtom = atom<number[]>([]);
