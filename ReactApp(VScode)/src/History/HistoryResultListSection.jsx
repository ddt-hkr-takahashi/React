import React from 'react';

/**
 * 検索結果をリスト表示するコンポーネント
 * @param {{
 * resultList: Array,
 * resultCountMessage: string
 * }} props
 */
const ResultListSection = ({ resultList, resultCountMessage }) => {
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
                <th className="column-prefecture">都道府県</th>
                <th className="column-store">販売店</th>
                <th className="column-branch">支店名</th>
                <th className="column-date">販売日</th>
                <th className="column-amount">販売額</th>
                <th className="column-maker">メーカー</th>
                <th className="column-type">タイプ</th>
                <th className="column-model">車種</th>
              </tr>
            </thead>
            <tbody>
              {/* 各行のデータをマッピングして表示 */}
              {resultList.map((item, index) => (
                <tr key={index}>
                  <td>{item.prefName}</td>
                  <td>{item.storeName}</td>
                  <td>{item.branchName}</td>
                  <td>{item.PD}</td>
                  <td>{item.salesAmount}</td>
                  <td>{item.makerName}</td>
                  <td>{item.typeName}</td>
                  <td>{item.modelName}</td>
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