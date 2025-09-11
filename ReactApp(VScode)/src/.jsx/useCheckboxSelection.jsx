// src/.jsx/useCheckboxSelection.jsx を修正
import { useState, useMemo } from 'react';

/**
 * リストアイテムのチェックボックス選択を管理するカスタムフック
 * @param {Array<object>} dataList - 選択対象のデータリスト
 * @param {string} keyName - 各アイテムの一意なキーとなるプロパティ名
 * @returns {{
 * selectedItems: Set,
 * isAllSelected: boolean,
 * handleItemSelect: Function,
 * handleAllItemsSelect: Function,
 * resetSelection: Function
 * }} - 選択状態と操作関数
 */
const useCheckboxSelection = (dataList, keyName = 'id') => {
  // 選択されたアイテムのキーを保持するSet
  const [selectedItems, setSelectedItems] = useState(new Set());

  // dataListからキーのリストを生成
  const dataListKeys = useMemo(() => {
    if (!Array.isArray(dataList)) {
      return [];
    }
    // 汎用的なkeyNameを使ってIDのリストを作成
    return dataList.map(item => item[keyName]);
  }, [dataList, keyName]);

  // 全て選択されているかどうかの状態
  const isAllSelected = useMemo(() => {
    if (dataListKeys.length === 0) return false;
    return selectedItems.size === dataListKeys.length;
  }, [selectedItems, dataListKeys]);

  /**
   * 特定のアイテムの選択/非選択を切り替える
   * @param {*} itemKey - アイテムの一意なキー
   */
  const handleItemSelect = (itemKey) => {
    const newSelectedItems = new Set(selectedItems);
    if (newSelectedItems.has(itemKey)) {
      newSelectedItems.delete(itemKey);
    } else {
      newSelectedItems.add(itemKey);
    }
    setSelectedItems(newSelectedItems);
  };
  
  /**
   * 全てのアイテムの選択状態を切り替える
   */
  const handleAllItemsSelect = () => {
    if (isAllSelected) {
      setSelectedItems(new Set());
    } else {
      const allItems = new Set(dataListKeys);
      setSelectedItems(allItems);
    }
  };
  
  /**
   * 全ての選択状態をリセットする
   */
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