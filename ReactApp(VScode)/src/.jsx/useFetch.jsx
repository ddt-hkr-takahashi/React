import { useState, useEffect, useCallback } from "react";
import axios from "axios";

const useFetch = (searchUrl, dropdownUrls) => {
  const [data, setData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  const fetchData = useCallback(async () => {
    setLoading(true);
    try {
      if (!dropdownUrls) {
        throw new Error("dropdownUrls is not defined.");
      }

      const dropdownResponses = await Promise.all(
        dropdownUrls.map((url) => axios.get(url))
      );

      const dropdownData = {
        prefs: dropdownResponses[0].data,
        branches: dropdownResponses[1].data,
        makers: dropdownResponses[2].data,
        types: dropdownResponses[3].data,
      };

      const searchResponse = await axios.get(searchUrl);

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
    if (dropdownUrls && dropdownUrls.length > 0) {
      fetchData();
    }
  }, [fetchData, dropdownUrls]);

  return { data, loading, error, fetchData };
};

export default useFetch;