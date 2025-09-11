import React from 'react';

/**
 * 検索結果リストのコンポーネント
 * @param {{
 * resultList: Array,
 * resultCountMessage: string,
 * selectedItems: Set,
 * onItemSelect: Function,
 * onAllItemsSelect: Function,
 * isAllSelected: boolean
 * }} props
 */
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
      {/* 検索結果件数メッセージ */}
      <p className="message success">{resultCountMessage}</p>
      
      {/* resultListが空でない場合のみテーブルをレンダリング */}
      {resultList && resultList.length > 0 ? (
        <div className="table-container">
          <table className="data-table">
            <thead>
              <tr>
                {/* 全選択用チェックボックス */}
                <th className="column-checkbox">
                  <label>
                  <input
                    type="checkbox"
                    onChange={onAllItemsSelect}
                    checked={isAllSelected}
                  />
                  </label>
                </th>
                {/* テーブルヘッダー */}
                <th className="column-prefecture">都道府県</th>
                <th className="column-branch">支店</th>
                <th className="column-maker">メーカー</th>
                <th className="column-type">タイプ</th>
                <th className="column-model">モデル名</th>
                <th className="column-salesAmount">販売額</th>
              </tr>
            </thead>
            <tbody>
              {/* 各行のデータをマッピングして表示 */}
              {resultList.map((item) => (
                <tr key={item.carId}>
                  {/* 個別選択用チェックボックス */}
                  <td className="column-checkbox">
                    <label>
                    <input
                      type="checkbox"
                      checked={selectedItems.has(item.carId)}
                      onChange={() => onItemSelect(item.carId)}
                    />
                    </label>
                  </td>
                  <td>{item.prefName}</td>
                  <td>{item.branchName}</td>
                  <td>{item.makerName}</td>
                  <td>{item.typeName}</td>
                  <td>{item.modelName}</td>
                  <td>{item.salesAmount}</td>
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

export default ResultListSection;