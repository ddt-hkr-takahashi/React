import { useState, useEffect, useCallback } from "react";

/**
 * 特定のURLからドロップダウンデータを取得するカスタムフック
 * @param {string | null} url - データを取得するAPIエンドポイント。nullの場合はフェッチを行わない。
 * @returns {{
 * data: Array,
 * loading: boolean,
 * error: string|null,
 * refetch: Function
 * }} - 取得データ、ローディング状態、エラーメッセージ、再フェッチ関数
 */
const useDropdownData = (url) => {
    // 取得したデータ
    const [data, setData] = useState([]);
    // ローディング状態
    const [loading, setLoading] = useState(false);
    // エラーメッセージ
    const [error, setError] = useState(null);

    /**
     * APIからデータを取得する
     */
    const fetchData = useCallback(async () => {
        // URLが提供されていない場合、フェッチを行わない
        if (!url) {
            setData([]);
            setLoading(false);
            return;
        }

        setLoading(true);
        setError(null);
        try {
            const response = await fetch(url);
            if (!response.ok) {
                throw new Error("Failed to fetch data.");
            }
            const result = await response.json();
            setData(result);
        } catch (err) {
            setError(err.message);
            setData([]);
        } finally {
            setLoading(false);
        }
    }, [url]);

    useEffect(() => {
        // fetchDataを呼び出すことで、urlが変更されたときにデータを再フェッチ
        fetchData();
    }, [fetchData]);

    return { data, loading, error, refetch: fetchData };
};

export default useDropdownData;