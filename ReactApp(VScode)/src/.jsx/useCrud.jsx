import { useState } from 'react';
import axios from 'axios';

/**
 * CRUD (Create, Read, Update, Delete) 操作のためのカスタムフック
 * @returns {{
 * execute: Function,
 * loading: boolean,
 * error: string|null,
 * successMessage: string|null,
 * setSuccessMessage: Function
 * }}
 */
export const useCrud = () => {
  // ローディング状態
  const [loading, setLoading] = useState(false);
  // エラーメッセージ
  const [error, setError] = useState(null);
  // 成功メッセージ
  const [successMessage, setSuccessMessage] = useState(null);

  /**
   * HTTPリクエストを実行する
   * @param {'post' | 'put' | 'delete'} method - HTTPメソッド
   * @param {string} endpoint - APIエンドポイント
   * @param {object} data - 送信するデータ
   * @returns {Promise<object|null>} レスポンスデータまたはnull
   */
  const execute = async (method, endpoint, data) => {
    setLoading(true);
    setError(null);
    setSuccessMessage(null);
    try {
      // APIの完全なURLを構築
      const url = `http://localhost:8080/api/${endpoint}`;
      let response;

      // メソッドに応じてaxiosを呼び出し
      if (method === 'post') {
        response = await axios.post(url, data);
      } else if (method === 'put') {
        response = await axios.put(url, data);
      } else if (method === 'delete') {
        response = await axios.delete(url, { data });
      } else {
        throw new Error('サポートされていないHTTPメソッドです。');
      }

      // 成功メッセージを設定
      setSuccessMessage(response.data?.message || '操作が完了しました。');
      return response;
    } catch (err) {
      // エラーメッセージを設定
      setError(err.response?.data?.errorMessage || 'CRUD操作エラー: ' + err.message);
      return null;
    } finally {
      setLoading(false);
    }
  };

  return {
    execute,
    loading,
    error,
    successMessage,
    setSuccessMessage,
  };
};