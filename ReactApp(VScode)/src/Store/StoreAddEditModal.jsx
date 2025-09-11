/**
 * 支店情報の追加・編集用モーダルコンポーネント
 * フォームの入力とバリデーションを管理し、親コンポーネントに保存を通知する
 * @param {object} props
 * @param {boolean} props.isOpen - モーダルが開いているか
 * @param {function} props.onClose - モーダルを閉じる関数
 * @param {function} props.onSave - フォームデータを保存する関数
 * @param {object} props.initialData - 編集時の初期データ
 * @param {object} props.dropdownData - ドロップダウンリストのデータ
 */
import React, { useState, useEffect } from "react";
import Modal from "react-modal";

if (typeof document !== "undefined") {
  Modal.setAppElement("#root");
}

const AddEditModal = ({ isOpen, onClose, onSave, initialData, dropdownData }) => {
  // 編集モードかどうかを判定
  const isEditMode = !!initialData?.branchCode;
  // ドロップダウンデータを分割代入
  const { prefs = [], stores = [] } = dropdownData;

  // フォームの状態を管理
  const [formData, setFormData] = useState({
    prefCode: "",
    storeCode: "",
    branchName: "",
    branchCode: null,
  });

  // モーダル表示時の処理
  useEffect(() => {
    // モーダルが閉じた場合、フォームをリセット
    if (!isOpen) {
      setFormData({
        prefCode: "",
        storeCode: "",
        branchName: "",
        branchCode: null,
      });
      return;
    }

    // 編集モードの場合、初期データをフォームにセット
    if (isEditMode) {
      setFormData({
        prefCode: String(initialData.prefCode ?? ""),
        storeCode: String(initialData.storeCode ?? ""),
        branchName: initialData.branchName ?? "",
        branchCode: initialData.branchCode ?? null,
      });
    } else {
      // 新規追加モードの場合、フォームを初期化
      setFormData({
        prefCode: "",
        storeCode: "",
        branchName: "",
        branchCode: null,
      });
    }
  }, [isOpen, isEditMode, initialData]);

  // 入力値変更時の処理
  const handleChange = (e) => {
    const { name, value } = e.target;
    // フォームデータを更新
    setFormData({ ...formData, [name]: value });
  };

  // フォーム送信時の処理
  const handleSubmit = (e) => {
    e.preventDefault();
    // 親コンポーネントのonSave関数を呼び出す
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
        <h2 className="modal-title">
          {/* タイトルを編集モードに応じて変更 */}
          {isEditMode ? "支店情報編集" : "支店情報追加"}
        </h2>
        <button onClick={onClose} className="modal-close-button">
          ×
        </button>
      </div>
      <div className="modal-body">
        {/* フォームを定義 */}
        <form onSubmit={handleSubmit}>
          {/* 都道府県ドロップダウン */}
          <div className="form-group">
            <label htmlFor="prefCode">都道府県:</label>
            <select
              id="prefCode"
              name="prefCode"
              value={formData.prefCode}
              onChange={handleChange}
              required
            >
              <option value="">選択してください</option>
              {/* ドロップダウンデータをマッピング */}
              {prefs.map(pref => (
                <option key={pref.prefCode} value={pref.prefCode}>{pref.prefName}</option>
              ))}
            </select>
          </div>
          {/* 店舗ドロップダウン */}
          <div className="form-group">
            <label htmlFor="storeCode">店舗:</label>
            <select
              id="storeCode"
              name="storeCode"
              value={formData.storeCode}
              onChange={handleChange}
              required
            >
              <option value="">選択してください</option>
              {/* ドロップダウンデータをマッピング */}
              {stores.map(store => (
                <option key={store.storeCode} value={store.storeCode}>{store.storeName}</option>
              ))}
            </select>
          </div>
          {/* 支店名入力フィールド */}
          <div className="form-group">
            <label htmlFor="branchName">支店名:</label>
            <input
              type="text"
              id="branchName"
              name="branchName"
              value={formData.branchName}
              onChange={handleChange}
              required
            />
          </div>
          {/* ボタン群 */}
          <div className="button-group button-spacing">
            <button type="submit" className="btn">
              {/* ボタンテキストを編集モードに応じて変更 */}
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

export default AddEditModal;