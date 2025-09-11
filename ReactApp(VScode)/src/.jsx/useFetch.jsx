// useFetch.jsx
import { useState, useEffect, useCallback } from "react";
import axios from "axios";

/**
 * 複数のURLからデータを一括取得するカスタムフック
 * @param {string} searchUrl - 検索データを取得するURL
 * @param {string[]} dropdownUrls - ドロップダウンデータを取得するURLの配列
 * @returns {{
 * data: { dropdownData: object, searchData: Array },
 * loading: boolean,
 * error: string|null,
 * fetchData: Function
 * }}
 */
const useFetch = (searchUrl, dropdownUrls) => {
  // 取得したデータ
  const [data, setData] = useState(null);
  // ローディング状態
  const [loading, setLoading] = useState(true);
  // エラーメッセージ
  const [error, setError] = useState(null);

  /**
   * APIからデータを非同期で取得する
   */
  const fetchData = useCallback(async () => {
    setLoading(true);
    try {
      // dropdownUrlsがundefinedでないことを確認
      if (!dropdownUrls) {
        throw new Error("dropdownUrls is not defined.");
      }

      // ドロップダウンデータを並行して取得
      const dropdownResponses = await Promise.all(
        dropdownUrls.map((url) => axios.get(url))
      );

      // ドロップダウンデータをオブジェクトに整理
      const dropdownData = {
        prefs: dropdownResponses[0].data,
        branches: dropdownResponses[1].data,
        makers: dropdownResponses[2].data,
        types: dropdownResponses[3].data,
      };

      // 検索データを取得
      const searchResponse = await axios.get(searchUrl);

      // 取得したデータをまとめて設定
      setData({
        dropdownData,
        searchData: searchResponse.data,
      });
    } catch (err) {
      console.error("データの取得中にエラーが発生しました:", err);
      setError("データの取得に失敗しました。サーバーとの接続を確認してください。");
    } finally {
      setLoading(false);
    }
  }, [searchUrl, dropdownUrls]);

  useEffect(() => {
    // dropdownUrlsが存在する場合にのみfetchDataを実行
    if (dropdownUrls && dropdownUrls.length > 0) {
      fetchData();
    }
  }, [fetchData, dropdownUrls]);

  return { data, loading, error, fetchData };
};

export default useFetch;