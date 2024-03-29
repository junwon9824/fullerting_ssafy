declare module 'event-source-polyfill' {
    export class EventSourcePolyfill {
      constructor(url: string, config?: any);
      onmessage: (event: any) => void;
      onerror: (event: any) => void;
      close(): void;
    }
  }