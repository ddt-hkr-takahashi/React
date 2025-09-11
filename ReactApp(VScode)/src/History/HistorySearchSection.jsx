import React, { useRef } from "react";
import FastDownloadButton from "/src/.jsx/FastDownloadButton.jsx";

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
  const fileInputRef = useRef(null);

  const handleFileChange = (e) => {
    if (e.target.files.length > 0) {
      onImportCsv(e.target.files[0]);
    }
  };

  const handleImportClick = () => {
    fileInputRef.current.click();
  };

  return (
    <div className="search-form-container">
      <div className="form-grid">
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

      <div className="search-button-group button-spacing">
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
        <FastDownloadButton
          apiUrl="http://localhost:8080/api/sales-history/download-report"
          fileName="used_car_report_fast.csv"
        />
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