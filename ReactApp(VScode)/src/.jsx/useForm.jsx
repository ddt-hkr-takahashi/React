// src/hooks/useForm.js
import { useState } from "react";

/**
 * フォームの状態を管理し、変更とリセットの機能を提供するカスタムフック
 * @param {object} initialState - フォームの初期状態
 * @returns {{
 * formData: object,
 * handleChange: Function,
 * resetForm: Function
 * }}
 */
export const useForm = (initialState) => {
  // フォームデータの状態
  const [formData, setFormData] = useState(initialState);

  /**
   * フォーム入力の変更を処理する
   * @param {Event} e - 変更イベント
   */
  const handleChange = (e) => {
    const { name, value } = e.target;
    // フォームデータを更新
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  /**
   * フォームの状態を初期状態にリセットする
   */
  const resetForm = () => {
    setFormData(initialState);
  };

  return { formData, handleChange, resetForm };
};