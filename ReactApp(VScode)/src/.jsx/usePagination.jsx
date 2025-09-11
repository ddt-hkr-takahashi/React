import { useState, useMemo } from 'react';

/**
 * ページネーションの状態を管理するカスタムフック
 * @param {number} totalItems - 総アイテム数
 * @param {number} itemsPerPage - 1ページあたりのアイテム数
 * @returns {{
 * currentPage: number,
 * totalPages: number,
 * handlePageChange: Function,
 * resetPagination: Function
 * }}
 */
const usePagination = (totalItems, itemsPerPage) => {
    // 現在のページ番号
    const [currentPage, setCurrentPage] = useState(1);

    // 総ページ数を計算
    const totalPages = useMemo(() => {
        // 総アイテム数を1ページあたりのアイテム数で割って切り上げ
        return Math.ceil(totalItems / itemsPerPage);
    }, [totalItems, itemsPerPage]);

    /**
     * ページ番号を変更する
     * @param {number} page - 変更後のページ番号
     */
    const handlePageChange = (page) => {
        // 有効なページ番号かチェック
        if (page >= 1 && page <= totalPages) {
            setCurrentPage(page);
        }
    };

    /**
     * ページネーションを最初のページにリセットする
     */
    const resetPagination = () => {
        setCurrentPage(1);
    };

    return {
        currentPage,
        totalPages,
        handlePageChange,
        resetPagination,
    };
};

export default usePagination;