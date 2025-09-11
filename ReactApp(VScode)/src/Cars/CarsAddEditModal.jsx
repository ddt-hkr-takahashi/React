import React, { useState, useEffect } from "react";
import Modal from "react-modal";

// React-Modalのルート要素を設定
if (typeof document !== "undefined") {
  Modal.setAppElement("#root");
}

/**
 * 新規追加・編集用のモーダルコンポーネント
 * @param {{
 * isOpen: boolean,
 * onClose: Function,
 * onSave: Function,
 * initialData: object,
 * dropdownData: object
 * }} props
 */
const AddEditModal = ({ isOpen, onClose, onSave, initialData, dropdownData }) => {
  // 編集モードか新規追加モードかを判定
  const isEditMode = !!initialData?.carId;

  // フォームデータの状態
  const [formData, setFormData] = useState({
    carId: null,
    branchCode: "",
    modelCode: "",
    ownerCode: "",
    salesAmount: null,
  });

  // モーダルの開閉と初期データに基づいてフォームデータを設定
  useEffect(() => {
    // モーダルが閉じられたらフォームをリセット
    if (!isOpen) {
      setFormData({
        carId: null,
        branchCode: "",
        modelCode: "",
        ownerCode: "",
        salesAmount: null,
      });
      return;
    }

    const { branches = [], models = [], owners = [] } = dropdownData;
    // ドロップダウンデータがロードされていることを確認
    if (branches.length && models.length && owners.length) {
      setFormData({
        carId: isEditMode ? initialData.carId : null,
        // 編集モードの場合は既存データを、新規の場合はデフォルト値を設定
        branchCode: isEditMode ? initialData.branchCode ?? "" : branches[0].branchCode,
        modelCode: isEditMode ? initialData.modelCode ?? "" : models[0].modelCode,
        ownerCode: isEditMode ? initialData.ownerCode ?? "" : "",
        salesAmount: isEditMode && initialData.salesAmount != null ? initialData.salesAmount : null,
      });
    }
  }, [isOpen, isEditMode, initialData, dropdownData]);

  /**
   * フォーム入力の変更を処理する
   * @param {Event} e - 変更イベント
   */
  const handleChange = (e) => {
    const { name, value } = e.target;
    let newValue;
    if (name === "salesAmount") {
      // 販売額は数値に変換
      newValue = value === "" ? null : Number(value);
    } else {
      newValue = value;
    }
    setFormData((prevData) => ({
      ...prevData,
      [name]: newValue,
    }));
  };

  /**
   * フォーム送信時の処理
   * @param {Event} e - イベントオブジェクト
   */
  const handleSubmit = (e) => {
    e.preventDefault();
    onSave(formData);
  };

  return (
    <Modal
      isOpen={isOpen}
      onRequestClose={onClose}
      className="modal-content"
      overlayClassName="modal-overlay"
      ariaHideApp={false}
    >
      <div className="modal-header">
        <h2>{isEditMode ? "販売情報編集" : "販売情報追加"}</h2>
      </div>
      <div className="modal-body">
        <form onSubmit={handleSubmit}>
          {/* 各フォーム入力フィールド */}
          <div className="form-group">
            <label htmlFor="branchCode">支店:</label>
            <select
              key={`branch-${formData.branchCode}`}
              id="branchCode"
              name="branchCode"
              value={formData.branchCode}
              onChange={handleChange}
              required
            >
              {dropdownData.branches?.map(branch => (
                <option key={branch.branchCode} value={branch.branchCode}>{branch.branchName}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="modelCode">モデル:</label>
            <select
              key={`model-${formData.modelCode}`}
              id="modelCode"
              name="modelCode"
              value={formData.modelCode}
              onChange={handleChange}
              required
            >
              {dropdownData.models?.map(model => (
                <option key={model.modelCode} value={model.modelCode}>{model.combinedName}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="ownerCode">元所有者:</label>
            <select
              key={`owner-${formData.ownerCode}`}
              id="ownerCode"
              name="ownerCode"
              value={formData.ownerCode}
              onChange={handleChange}
            >
              <option value="">未設定</option>
              {dropdownData.owners?.map(owner => (
                <option key={owner.ownerCode} value={owner.ownerCode}>{owner.ownerName}</option>
              ))}
            </select>
          </div>

          <div className="form-group">
            <label htmlFor="salesAmount">販売額:</label>
            <input
              type="number"
              id="salesAmount"
              name="salesAmount"
              value={formData.salesAmount == null ? "" : formData.salesAmount}
              onChange={handleChange}
              min="0"
              required
            />
          </div>

          {/* ボタン */}
          <div className="button-group button-spacing">
            <button type="submit" className="btn">{isEditMode ? "更新" : "追加"}</button>
            <button type="button" className="btn" onClick={onClose}>キャンセル</button>
          </div>
        </form>
      </div>
    </Modal>
  );
};

export default AddEditModal;