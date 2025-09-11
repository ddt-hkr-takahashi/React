import React from 'react';

/**
 * ページネーションコンポーネント
 * @param {{
 * currentPage: number,
 * totalPages: number,
 * onPageChange: Function
 * }} props
 */
const PaginationSection = ({ currentPage, totalPages, onPageChange }) => {
  // 総ページ数が1以下の場合、コンポーネントを非表示にする
  if (totalPages <= 1) return null;

  /**
   * 表示するページ番号の配列を生成する
   * @returns {number[]} ページ番号の配列
   */
  const getPageNumbers = () => {
    const pageNumbers = [];
    const maxPagesToShow = 11; // 中央のページ＋前後合計10ページ

    let startPage = Math.max(1, currentPage - 5);
    let endPage = Math.min(totalPages, currentPage + 5);

    // 表示するページ数が11ページ未満の場合は調整
    if (endPage - startPage < maxPagesToShow - 1) {
      if (currentPage - 5 <= 1) {
        endPage = Math.min(totalPages, startPage + maxPagesToShow - 1);
      } else if (currentPage + 5 >= totalPages) {
        startPage = Math.max(1, totalPages - maxPagesToShow + 1);
      }
    }

    for (let i = startPage; i <= endPage; i++) {
      pageNumbers.push(i);
    }
    return pageNumbers;
  };

  const pageNumbers = getPageNumbers();

  return (
    <div className="pagination">
      {/* 最初へ */}
      <button onClick={() => onPageChange(1)} disabled={currentPage === 1}>
        最初
      </button>
      {/* 前へ */}
      <button onClick={() => onPageChange(currentPage - 1)} disabled={currentPage === 1}>
        前へ
      </button>

      {/* 最初のページと省略記号 */}
      {pageNumbers[0] > 1 && (
        <>
          <button onClick={() => onPageChange(1)}>1</button>
          {pageNumbers[0] > 2 && <span>...</span>}
        </>
      )}

      {/* 表示するページ番号 */}
      {pageNumbers.map(page => (
        <button
          key={page}
          onClick={() => onPageChange(page)}
          className={currentPage === page ? 'active' : ''}
        >
          {page}
        </button>
      ))}

      {/* 最後のページと省略記号 */}
      {pageNumbers[pageNumbers.length - 1] < totalPages && (
        <>
          {pageNumbers[pageNumbers.length - 1] < totalPages - 1 && <span>...</span>}
          <button onClick={() => onPageChange(totalPages)}>{totalPages}</button>
        </>
      )}

      {/* 次へ */}
      <button onClick={() => onPageChange(currentPage + 1)} disabled={currentPage === totalPages}>
        次へ
      </button>
      {/* 最後へ */}
      <button onClick={() => onPageChange(totalPages)} disabled={currentPage === totalPages}>
        最後
      </button>
    </div>
  );
};

export default PaginationSection;