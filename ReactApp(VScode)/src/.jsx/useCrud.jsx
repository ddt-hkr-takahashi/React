import { useState } from 'react';
import axios from 'axios';

export const useCrud = () => {
  const [loading, setLoading] = useState(false);
  const [error, setError] = useState(null);
  const [successMessage, setSuccessMessage] = useState(null);

  const execute = async (method, endpoint, data) => {
    setLoading(true);
    setError(null);
    setSuccessMessage(null);
    try {
      const url = `http://localhost:8080/api/${endpoint}`;
      let response;

      if (method === 'post') {
        response = await axios.post(url, data);
      } else if (method === 'put') {
        response = await axios.put(url, data);
      } else if (method === 'delete') {
        response = await axios.delete(url, { data });
      } else {
        throw new Error('サポートされていないHTTPメソッドです。');
      }

      setSuccessMessage(response.data?.message || '操作が完了しました。');
      return response;
    } catch (err) {
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