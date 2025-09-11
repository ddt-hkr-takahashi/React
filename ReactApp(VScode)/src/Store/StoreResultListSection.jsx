/**
 * 支店情報検索結果一覧表示コンポーネント
 * 検索結果をテーブル形式で表示し、チェックボックス操作を処理する
 * @param {object} props
 * @param {Array} props.resultList - 表示するデータリスト
 * @param {string} props.resultCountMessage - 結果件数メッセージ
 * @param {Set} props.selectedItems - 選択されたアイテムのSet
 * @param {function} props.onItemSelect - 個別アイテム選択時のハンドラ
 * @param {function} props.onAllItemsSelect - 全アイテム選択時のハンドラ
 * @param {boolean} props.isAllSelected - 全てが選択されているか
 */
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
      {/* 検索結果メッセージを表示 */}
      <p className="message success">{resultCountMessage}</p>
      
      {/* resultListが空でない場合のみテーブルをレンダリング */}
      {resultList && resultList.length > 0 ? (
        <div className="table-container">
          {/* データテーブル */}
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
                <th>都道府県名</th>
                <th>店舗名</th>
                <th>支店名</th>
              </tr>
            </thead>
            <tbody>
              {/* 結果リストをマッピングして行を生成 */}
              {resultList.map((item) => (
                <tr key={item.branchCode}>
                  {/* 個別選択チェックボックス */}
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
        // 結果が0件の場合はメッセージを表示
        <div className="no-results-message">
          <p>検索条件に一致するデータがありません。</p>
        </div>
      )}
    </div>
  );
};

export default ResultListSection;