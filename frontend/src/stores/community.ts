import { atom } from 'jotai';

export const selectedTypeAtom = atom('전체');

export const title = atom<string>("");
export const content = atom<string>("");
export const oldfiles = atom<number[]>([]);


