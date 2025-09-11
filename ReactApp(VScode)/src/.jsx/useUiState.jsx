// src/hooks/useUiState.jsx
import { useState } from "react";

/**
 * UI関連の状態（モーダル、編集データ、ボタン表示）を管理するカスタムフック
 * @returns {{
 * isModalOpen: boolean,
 * currentEditData: object|null,
 * isFixedButtonsVisible: boolean,
 * setIsModalOpen: Function,
 * setIsFixedButtonsVisible: Function,
 * handleAddClick: Function,
 * handleEditClick: Function
 * }}
 */
const useUiState = () => {
  // モーダルの開閉状態
  const [isModalOpen, setIsModalOpen] = useState(false);
  // 現在編集中のデータ
  const [currentEditData, setCurrentEditData] = useState(null);
  // 固定ボタンの表示状態
  const [isFixedButtonsVisible, setIsFixedButtonsVisible] = useState(true);

  /**
   * 新規追加ボタンクリック時の処理
   * 編集データをリセットし、モーダルを開く
   */
  const handleAddClick = () => {
    setCurrentEditData(null);
    setIsModalOpen(true);
  };

  /**
   * 編集ボタンクリック時の処理
   * 編集するデータを設定し、モーダルを開く
   * @param {object} item - 編集対象のデータ
   */
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