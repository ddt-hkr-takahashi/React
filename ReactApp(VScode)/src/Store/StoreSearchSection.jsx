import React from "react";

const SearchSection = ({
  formData,
  prefList,
  stores,
  handleChange,
  handleSearch,
  onAddClick,
}) => {
  const availableStores = stores || [];

  return (
    <form onSubmit={handleSearch} className="search-form-container">
      <div className="form-grid">
        <div className="form-group">
          <label htmlFor="prefCode">都道府県:</label>
          <select
            id="prefCode"
            name="prefCode"
            value={formData.prefCode}
            onChange={handleChange}
          >
            <option value="">すべて</option>
            {prefList.map((pref) => (
              <option key={pref.prefCode} value={pref.prefCode}>
                {pref.prefName}
              </option>
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
          >
            <option value="">すべて</option>
            {availableStores.map((store) => (
              <option key={store.storeCode} value={store.storeCode}>
                {store.storeName}
              </option>
            ))}
          </select>
        </div>
      </div>

      <div className="search-button-group button-spacing">
        <button
          type="submit"
          className="btn btn-primary"
          onClick={handleSearch}
        >
          検索
        </button>
        <button type="button" className="btn" onClick={onAddClick}>
          新規追加
        </button>
      </div>
    </form>
  );
};

export default SearchSection;