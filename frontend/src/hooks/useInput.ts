import { useState, ChangeEvent } from "react";

const useInput = (initialValue: string) => {
  const [value, setValue] = useState(initialValue);

  const handleChange = (
    e: ChangeEvent<HTMLInputElement | HTMLTextAreaElement>
  ) => {
    setValue(e.target.value);
  };

  return [value, handleChange] as const;
};

export default useInput;
