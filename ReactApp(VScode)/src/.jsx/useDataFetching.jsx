import { useState, useEffect, useCallback } from "react";
import axios from "axios";

/**
 * 検索フォームの状態管理とAPIからのデータ取得を行うカスタムフック
 * @param {object} initialFormState - フォームの初期状態
 * @param {string} url - 検索を行うAPIエンドポイントのURL
 * @param {Function} setSuccessMessage - 成功メッセージを更新する関数
 * @returns {{
 * formData: object,
 * resultList: Array,
 * resultCountMessage: string,
 * loading: boolean,
 * isSearched: boolean,
 * searchError: string|null,
 * handleChange: Function,
 * fetchResults: Function,
 * setIsSearched: Function,
 * totalCount: number
 * }}
 */
const useDataFetching = (initialFormState, url, setSuccessMessage) => {
    // フォームのデータ状態
    const [formData, setFormData] = useState(initialFormState);
    // 検索結果のリスト
    const [resultList, setResultList] = useState([]);
    // 検索結果件数メッセージ
    const [resultCountMessage, setResultCountMessage] = useState("");
    // ローディング状態
    const [loading, setLoading] = useState(false);
    // 検索が実行されたかどうか
    const [isSearched, setIsSearched] = useState(false);
    // 検索エラーメッセージ
    const [searchError, setSearchError] = useState(null);
    // 検索結果の総件数
    const [totalCount, setTotalCount] = useState(0);

    /**
     * フォーム入力の変更を処理する
     * @param {Event} e - 変更イベント
     */
    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: type === "checkbox" ? checked : value
        }));
    };

    /**
     * APIから検索結果を取得する
     * @param {object} searchParams - 検索条件
     * @param {number} page - 取得するページ番号
     * @param {number} size - 1ページあたりの件数
     */
    const fetchResults = useCallback(async (searchParams, page, size) => {
        setLoading(true);
        setSearchError(null);
        try {
            // クエリ文字列を構築
            const params = new URLSearchParams({ ...searchParams, page, size }).toString();
            // GETリクエストでデータを取得
            const response = await axios.get(`${url}?${params}`);
            
            const { resultList, resultCountMessage } = response.data;
            setResultList(resultList || []);
            setResultCountMessage(resultCountMessage);
            
            // 結果件数メッセージから総件数を抽出
            const match = resultCountMessage.match(/全 (\d+) 件/);
            const extractedTotalCount = match ? parseInt(match[1], 10) : 0;
            setTotalCount(extractedTotalCount);
            
            setIsSearched(true);
            setSuccessMessage("");
        } catch (error) {
            setSearchError("検索中にエラーが発生しました。");
            setResultList([]);
            setResultCountMessage("");
            setTotalCount(0);
        } finally {
            setLoading(false);
        }
    }, [url, setSuccessMessage]);

    return {
        formData,
        resultList,
        resultCountMessage,
        loading,
        isSearched,
        searchError,
        handleChange,
        fetchResults,
        setIsSearched,
        totalCount,
    };
};

export default useDataFetching;