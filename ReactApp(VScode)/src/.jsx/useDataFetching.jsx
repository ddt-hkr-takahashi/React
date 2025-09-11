import { useState, useEffect, useCallback } from "react";
import axios from "axios";

const useDataFetching = (initialFormState, url, setSuccessMessage) => {
    const [formData, setFormData] = useState(initialFormState);
    const [resultList, setResultList] = useState([]);
    const [resultCountMessage, setResultCountMessage] = useState("");
    const [loading, setLoading] = useState(false);
    const [isSearched, setIsSearched] = useState(false);
    const [searchError, setSearchError] = useState(null);
    const [totalCount, setTotalCount] = useState(0);

    const handleChange = (e) => {
        const { name, value, type, checked } = e.target;
        setFormData(prevData => ({
            ...prevData,
            [name]: type === "checkbox" ? checked : value
        }));
    };

    const fetchResults = useCallback(async (searchParams, page, size) => {
        setLoading(true);
        setSearchError(null);
        try {
            const params = new URLSearchParams({ ...searchParams, page, size }).toString();
            const response = await axios.get(`${url}?${params}`);
            
            const { resultList, resultCountMessage } = response.data;
            setResultList(resultList || []);
            setResultCountMessage(resultCountMessage);
            
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