import React from 'react';

/**
 * 元所有者情報の検索フォームコンポーネント
 * @param {{
 * formData: object,
 * handleChange: Function,
 * handleSearch: Function,
 * onAddClick: Function
 * }} props
 */
const OwnerSearchSection = ({ formData, handleChange, handleSearch, onAddClick }) => {
    // 性別の静的データを定義
    const genders = [
        { genderCode: '男', genderName: '男' },
        { genderCode: '女', genderName: '女' },
    ];

    return (
        <div className="search-form-container">
            <div className="form-grid">
                {/* オーナー名検索入力欄 */}
                <div className="form-group">
                    <label htmlFor="ownerName">元所有者名</label>
                    <input
                        type="text"
                        id="ownerName"
                        name="ownerName"
                        value={formData.ownerName}
                        onChange={handleChange}
                        placeholder="元所有者名を入力"
                    />
                </div>
                {/* 性別選択ドロップダウン */}
                <div className="form-group">
                    <label htmlFor="gender">性別</label>
                    <select
                        id="gender"
                        name="gender"
                        value={formData.gender}
                        onChange={handleChange}
                    >
                        <option value="">選択してください</option>
                        {genders.map(gender => (
                            <option key={gender.genderCode} value={gender.genderCode}>
                                {gender.genderName}
                            </option>
                        ))}
                    </select>
                </div>
                {/* 年齢下限入力欄 */}
                <div className="form-group">
                    <label htmlFor="ageMin">年齢（下限）</label>
                    <input
                        type="number"
                        id="ageMin"
                        name="ageMin"
                        value={formData.ageMin}
                        onChange={handleChange}
                        min="0"
                    />
                </div>
                {/* 年齢上限入力欄 */}
                <div className="form-group">
                    <label htmlFor="ageMax">年齢（上限）</label>
                    <input
                        type="number"
                        id="ageMax"
                        name="ageMax"
                        value={formData.ageMax}
                        onChange={handleChange}
                        min="0"
                    />
                </div>
            </div>

            {/* 検索・追加ボタン群 */}
            <div className="search-button-group button-spacing">
                <button
                    type="submit"
                    className="btn btn-primary"
                    onClick={handleSearch}
                >
                    検索
                </button>
                <button
                    type="button"
                    className="btn"
                    onClick={onAddClick}
                >
                    新規追加
                </button>
            </div>
        </div>
    );
};

export default OwnerSearchSection;