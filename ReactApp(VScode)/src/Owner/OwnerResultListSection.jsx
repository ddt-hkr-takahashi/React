import React from "react";

const OwnerResultListSection = ({
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
                <th>元所有者名</th>
                <th>性別</th>
                <th>年齢</th>
                <th>売却台数</th>
              </tr>
            </thead>
            <tbody>
              {resultList.map((item) => (
                <tr key={item.ownerCode}>
                  <td className="column-checkbox">
                    <label>
                      <input
                        type="checkbox"
                        checked={selectedItems.has(item.ownerCode)}
                        onChange={() => onItemSelect(item.ownerCode)}
                      />
                    </label>
                  </td>
                  <td>{item.ownerName}</td>
                  <td>{item.sex}</td>
                  <td>{item.age}</td>
                  <td>{item.salesCount}</td>
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

export default OwnerResultListSection;