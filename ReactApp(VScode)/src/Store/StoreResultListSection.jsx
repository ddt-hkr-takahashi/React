import React from 'react';

const ResultListSection = ({
  resultList,
  resultCountMessage,
  selectedItems,
  onItemSelect,
  onAllItemsSelect,
  isAllSelected,
}) => {
  return (
    <div className="result-container">
      <p className="message success">{resultCountMessage}</p>
      
      {resultList && resultList.length > 0 ? (
        <div className="table-container">
          <table className="data-table">
            <thead>
              <tr>
                <th className="column-checkbox">
                  <label>
                    <input
                      type="checkbox"
                      onChange={onAllItemsSelect}
                      checked={isAllSelected}
                    />
                  </label>
                </th>
                <th>都道府県名</th>
                <th>店舗名</th>
                <th>支店名</th>
              </tr>
            </thead>
            <tbody>
              {resultList.map((item) => (
                <tr key={item.branchCode}>
                  <td className="column-checkbox">
                    <label>
                      <input
                        type="checkbox"
                        checked={selectedItems.has(item.branchCode)}
                        onChange={() => onItemSelect(item.branchCode)}
                      />
                    </label>
                  </td>
                  <td>{item.prefName}</td>
                  <td>{item.storeName}</td>
                  <td>{item.branchName}</td>
                </tr>
              ))}
            </tbody>
          </table>
        </div>
      ) : (
        <div className="no-results-message">
          <p>検索条件に一致するデータがありません。</p>
        </div>
      )}
    </div>
  );
};

export default ResultListSection;