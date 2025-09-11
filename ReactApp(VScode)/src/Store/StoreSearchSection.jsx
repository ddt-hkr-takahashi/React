/**
 * 支店情報検索フォームコンポーネント
 * 都道府県、店舗、支店名で絞り込む検索フォームを提供する
 * @param {object} props
 * @param {object} props.formData - フォームの入力値
 * @param {Array} props.prefList - 都道府県のドロップダウンデータ
 * @param {Array} props.stores - 店舗のドロップダウンデータ
 * @param {function} props.handleChange - 入力値変更時のハンドラ
 * @param {function} props.handleSearch - 検索実行時のハンドラ
 * @param {function} props.onAddClick - 新規追加ボタンクリック時のハンドラ
 */
// SearchSection.jsx
import React from "react";

const SearchSection = ({
  formData,
  prefList,
  stores,
  handleChange,
  handleSearch,
  onAddClick,
}) => {
  // storesデータが存在しない場合は空の配列を使用
  const availableStores = stores || [];

  return (
    <form onSubmit={handleSearch} className="search-form-container">
      <div className="form-grid">
        {/* 都道府県ドロップダウン */}
        <div className="form-group">
          <label htmlFor="prefCode">都道府県:</label>
          <select
            id="prefCode"
            name="prefCode"
            value={formData.prefCode}
            onChange={handleChange}
          >
            <option value="">すべて</option>
            {/* 都道府県データをマッピング */}
            {prefList.map((pref) => (
              <option key={pref.prefCode} value={pref.prefCode}>
                {pref.prefName}
              </option>
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
          >
            <option value="">すべて</option>
            {/* 店舗データをマッピング */}
            {availableStores.map((store) => (
              <option key={store.storeCode} value={store.storeCode}>
                {store.storeName}
              </option>
            ))}
          </select>
        </div>
      </div>

      {/* 検索ボタンと新規追加ボタン */}
      <div className="search-button-group button-spacing">
        {/* 検索ボタン */}
        <button
          type="submit"
          className="btn btn-primary"
          onClick={handleSearch}
        >
          検索
        </button>
        {/* 新規追加ボタン */}
        <button type="button" className="btn" onClick={onAddClick}>
          新規追加
        </button>
      </div>
    </form>
  );
};

export default SearchSection;