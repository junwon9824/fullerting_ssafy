import { atom } from "jotai";

export const markerAtom = atom<FarmType | null>(null);

export const regionAtom = atom<string>("0");
