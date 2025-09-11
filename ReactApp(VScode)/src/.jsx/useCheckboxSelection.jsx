import { useState, useMemo } from 'react';

const useCheckboxSelection = (dataList, keyName = 'id') => {
  const [selectedItems, setSelectedItems] = useState(new Set());

  const dataListKeys = useMemo(() => {
    if (!Array.isArray(dataList)) {
      return [];
    }
    return dataList.map(item => item[keyName]);
  }, [dataList, keyName]);

  const isAllSelected = useMemo(() => {
    if (dataListKeys.length === 0) return false;
    return selectedItems.size === dataListKeys.length;
  }, [selectedItems, dataListKeys]);

  const handleItemSelect = (itemKey) => {
    const newSelectedItems = new Set(selectedItems);
    if (newSelectedItems.has(itemKey)) {
      newSelectedItems.delete(itemKey);
    } else {
      newSelectedItems.add(itemKey);
    }
    setSelectedItems(newSelectedItems);
  };
  
  const handleAllItemsSelect = () => {
    if (isAllSelected) {
      setSelectedItems(new Set());
    } else {
      const allItems = new Set(dataListKeys);
      setSelectedItems(allItems);
    }
  };
  
  const resetSelection = () => {
    setSelectedItems(new Set());
  };

  return {
    selectedItems,
    isAllSelected,
    handleItemSelect,
    handleAllItemsSelect,
    resetSelection,
  };
};

export default useCheckboxSelection;