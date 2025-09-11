import React, { useState, useEffect } from "react";
import Modal from "react-modal";

if (typeof document !== "undefined") {
  Modal.setAppElement("#root");
}

const OwnerAddEditModal = ({ isOpen, onClose, onSave, initialData }) => {
  const isEditMode = !!initialData?.ownerCode;

  const genders = ["男", "女"];

  const [formData, setFormData] = useState({
    ownerCode: null,
    ownerName: "",
    gender: "",
    age: "",
  });

  useEffect(() => {
    if (isOpen) {
      if (isEditMode) {
        setFormData({
          ownerCode: initialData.ownerCode,
          ownerName: initialData.ownerName,
          gender: initialData.sex,
          age: initialData.age,
        });
      } else {
        setFormData({
          ownerCode: null,
          ownerName: "",
          gender: "",
          age: "",
        });
      }
    }
  }, [isOpen, isEditMode, initialData]);

  const handleChange = (e) => {
    const { name, value } = e.target;
    setFormData((prev) => ({ ...prev, [name]: value }));
  };

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