import { useState } from "react";

const useUiState = () => {
  const [isModalOpen, setIsModalOpen] = useState(false);
  const [currentEditData, setCurrentEditData] = useState(null);
  const [isFixedButtonsVisible, setIsFixedButtonsVisible] = useState(true);

  const handleAddClick = () => {
    setCurrentEditData(null);
    setIsModalOpen(true);
  };

  const handleEditClick = (item) => {
    setCurrentEditData(item);
    setIsModalOpen(true);
  };

  return {
    isModalOpen,
    currentEditData,
    isFixedButtonsVisible,
    setIsModalOpen,
    setIsFixedButtonsVisible,
    handleAddClick,
    handleEditClick,
  };
};

export default useUiState;