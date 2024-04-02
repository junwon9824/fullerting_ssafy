import { atom } from 'jotai';

export const selectedTypeAtom = atom('자유게시판');

export const title = atom<string>("");
export const content = atom<string>("");
export const oldfiles = atom<number[]>([]);


