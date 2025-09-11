import React from "react";

/**
 * 検索結果をリスト表示するコンポーネント
 * @param {{
 * resultList: Array,
 * resultCountMessage: string,
 * selectedItems: Set,
 * onItemSelect: Function,
 * onAllItemsSelect: Function,
 * isAllSelected: boolean
 * }} props
 */
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
      {/* 検索結果件数メッセージ */}
      <p className="message success">{resultCountMessage}</p>

      {/* 結果リストが空でない場合のみテーブルを表示 */}
      {resultList && resultList.length > 0 ? (
        <div className="table-container">
          <table className="data-table">
            <thead>
              <tr>
                {/* 全選択チェックボックス */}
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
              {/* 各行のデータをマッピングして表示 */}
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
        // 結果が0件の場合はメッセージを表示
        <div className="no-results-message">
          <p>検索条件に一致するデータがありません。</p>
        </div>
      )}
    </div>
  );
};

export default OwnerResultListSection;