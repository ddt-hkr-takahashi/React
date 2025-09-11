import React, { useRef } from "react";

/**
 * 販売履歴の検索フォームコンポーネント
 * @param {{
 * formData: object,
 * storeList: Array,
 * yearList: Array,
 * monthList: Array,
 * handleChange: Function,
 * handleSearch: Function,
 * onImportCsv: Function,
 * onExportCsv: Function,
 * onToggleView: Function,
 * isSearched: boolean
 * }} props
 */
const SearchSection = ({
  formData,
  storeList,
  yearList,
  monthList,
  handleChange,
  handleSearch,
  onImportCsv,
  onExportCsv,
  onToggleView,
  isSearched,
}) => {
  // ファイル入力要素への参照
  const fileInputRef = useRef(null);

  /**
   * ファイルが選択されたときの処理
   * @param {Event} e - 変更イベント
   */
  const handleFileChange = (e) => {
    if (e.target.files.length > 0) {
      onImportCsv(e.target.files[0]);
    }
  };

  /**
   * インポートボタンクリック時の処理
   * 隠されたファイル入力要素をクリック
   */
  const handleImportClick = () => {
    fileInputRef.current.click();
  };

  return (
    <div className="search-form-container">
      <div className="form-grid">
        {/* 店舗コードの選択 */}
        <div className="form-group">
          <label htmlFor="storeCode">店舗</label>
          <select
            id="storeCode"
            name="storeCode"
            value={formData.storeCode}
            onChange={handleChange}
          >
            <option value="">すべて</option>
            {storeList &&
              storeList.map((item) => (
                <option key={item.storeCode} value={item.storeCode}>
                  {item.storeName}
                </option>
              ))}
          </select>
        </div>

        {/* 年の選択 */}
        <div className="form-group">
          <label htmlFor="yearNumber">年</label>
          <select
            id="yearNumber"
            name="yearNumber"
            value={formData.yearNumber}
            onChange={handleChange}
          >
            <option value="">すべて</option>
            {yearList &&
              yearList.map((item) => (
                <option key={item.yearNumber} value={item.yearNumber}>
                  {item.yearNumber}
                </option>
              ))}
          </select>
        </div>

        {/* 月の選択 */}
        <div className="form-group">
          <label htmlFor="monthNumber">月</label>
          <select
            id="monthNumber"
            name="monthNumber"
            value={formData.monthNumber}
            onChange={handleChange}
          >
            <option value="">すべて</option>
            {monthList &&
              monthList.map((item) => (
                <option key={item.monthNumber} value={item.monthNumber}>
                  {item.monthNumber}
                </option>
              ))}
          </select>
        </div>
      </div>

      {/* 検索・CSV操作ボタン */}
      <div className="search-button-group button-spacing">
        {/* 一覧表示ボタンとグラフ表示ボタンに検索機能を持たせる */}
        <button
          type="button"
          className="btn btn-primary"
          onClick={() => {
            handleSearch("list");
            onToggleView("list");
          }}
        >
          一覧表示
        </button>
        <button
          type="button"
          className="btn btn-primary"
          onClick={() => {
            handleSearch("graph");
            onToggleView("graph");
          }}
        >
          グラフ表示
        </button>
        <button type="button" className="btn" onClick={onExportCsv}>
          CSV出力
        </button>
        {!isSearched && (
          <button type="button" className="btn" onClick={handleImportClick}>
            CSVインポート
          </button>
        )}
        <input
          type="file"
          ref={fileInputRef}
          onChange={handleFileChange}
          style={{ display: "none" }}
          accept=".csv"
        />
      </div>
    </div>
  );
};

export default SearchSection;