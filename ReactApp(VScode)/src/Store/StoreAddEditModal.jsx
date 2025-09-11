import React, { useState, useEffect } from "react";
import Modal from "react-modal";

if (typeof document !== "undefined") {
  Modal.setAppElement("#root");
}

const AddEditModal = ({ isOpen, onClose, onSave, initialData, dropdownData }) => {
  const isEditMode = !!initialData?.branchCode;
  const { prefs = [], stores = [] } = dropdownData;

  const [formData, setFormData] = useState({
    prefCode: "",
    storeCode: "",
    branchName: "",
    branchCode: null,
  });

  useEffect(() => {
    if (!isOpen) {
      setFormData({
        prefCode: "",
        storeCode: "",
        branchName: "",
        branchCode: null,
      });
      return;
    }

    if (isEditMode) {
      setFormData({
        prefCode: String(initialData.prefCode ?? ""),
        storeCode: String(initialData.storeCode ?? ""),
        branchName: initialData.branchName ?? "",
        branchCode: initialData.branchCode ?? null,
      });
    } else {
      setFormData({
        prefCode: "",
        storeCode: "",
        branchName: "",
        branchCode: null,
      });
    }
  }, [isOpen, isEditMode, initialData]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData({ ...formData, [name]: value });
  };

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
        <h2 className="modal-title">
          {isEditMode ? "支店情報編集" : "支店情報追加"}
        </h2>
        <button onClick={onClose} className="modal-close-button">
          ×
        </button>
      </div>
      <div className="modal-body">
        <form onSubmit={handleSubmit}>
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
              {prefs.map(pref => (
                <option key={pref.prefCode} value={pref.prefCode}>{pref.prefName}</option>
              ))}
            </select>
          </div>
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
              {stores.map(store => (
                <option key={store.storeCode} value={store.storeCode}>{store.storeName}</option>
              ))}
            </select>
          </div>
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

export default AddEditModal;