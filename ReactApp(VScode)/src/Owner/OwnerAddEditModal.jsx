import React, { useState, useEffect } from "react";
import Modal from "react-modal";

// React-Modalのアクセシビリティ設定
if (typeof document !== "undefined") {
  Modal.setAppElement("#root");
}

/**
 * 元所有者情報の追加・編集用モーダルコンポーネント
 * @param {{
 * isOpen: boolean,
 * onClose: Function,
 * onSave: Function,
 * initialData: object|null
 * }} props
 */
const OwnerAddEditModal = ({ isOpen, onClose, onSave, initialData }) => {
  // 編集モードか新規追加モードかを判定
  const isEditMode = !!initialData?.ownerCode;

  // 性別の静的データを定義
  const genders = ["男", "女"];

  // フォームデータの状態を管理
  const [formData, setFormData] = useState({
    ownerCode: null,
    ownerName: "",
    gender: "",
    age: "",
  });

  // モーダルが開かれたとき、または初期データが変更されたときにフォームデータをリセットまたは設定
  useEffect(() => {
    if (isOpen) {
      if (isEditMode) {
        // 編集モードの場合、初期データをセット
        setFormData({
          ownerCode: initialData.ownerCode,
          ownerName: initialData.ownerName,
          gender: initialData.sex,
          age: initialData.age,
        });
      } else {
        // 新規追加モードの場合、フォームをリセット
        setFormData({
          ownerCode: null,
          ownerName: "",
          gender: "",
          age: "",
        });
      }
    }
  }, [isOpen, isEditMode, initialData]);

  /**
   * フォーム入力の変更を処理する
   * @param {Event} e - 変更イベント
   */
  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

  /**
   * フォーム送信を処理する
   * @param {Event} e - 送信イベント
   */
  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(formData);
  };

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={onClose}
      contentLabel={isEditMode ? "元所有者情報編集" : "元所有者情報追加"}
      className="modal-content"
      overlayClassName="modal-overlay"
    >
      <div className="modal-header">
        <h2 className="modal-title">
          {isEditMode ? "元所有者情報編集" : "元所有者情報追加"}
        </h2>
        <button className="modal-close-button" onClick={onClose}>
          &times;
        </button>
      </div>
      <div className="modal-body">
        <form onSubmit={handleSubmit}>
          {/* 名前入力欄 */}
          <div className="form-group">
            <label htmlFor="ownerName">名前:</label>
            <input
              type="text"
              id="ownerName"
              name="ownerName"
              value={formData.ownerName}
              onChange={handleChange}
              required
            />
          </div>

          {/* 性別選択ドロップダウン */}
          <div className="form-group">
            <label htmlFor="gender">性別:</label>
            <select
              id="gender"
              name="gender"
              value={formData.gender}
              onChange={handleChange}
              required
            >
              <option value="">選択してください</option>
              {genders.map((genderName) => (
                <option key={genderName} value={genderName}>
                  {genderName}
                </option>
              ))}
            </select>
          </div>

          {/* 年齢入力欄 */}
          <div className="form-group">
            <label htmlFor="age">年齢:</label>
            <input
              type="number"
              id="age"
              name="age"
              value={formData.age}
              onChange={handleChange}
              min="0"
              required
            />
          </div>

          {/* 送信・キャンセルボタン */}
          <div className="button-group button-spacing">
            <button type="submit" className="btn">
              {isEditMode ? "更新" : "追加"}
            </button>
            <button type="button" className="btn" onClick={onClose}>
              キャンセル
            </button>
          </div>
        </form>
      </div>
    </Modal>
  );
};

export default OwnerAddEditModal;