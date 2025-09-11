import React, { useMemo, useState } from "react";
import useDataFetching from "/src/.jsx/useDataFetching.jsx";
import useDropdownData from "/src/.jsx/useDropdownData.jsx";
import { useCrud } from "/src/.jsx/useCrud.jsx";
import PaginationSection from "/src/.jsx/PaginationSection.jsx";
import SearchSection from "./HistorySearchSection.jsx";
import ResultListSection from "./HistoryResultListSection.jsx";
import usePagination from "/src/.jsx/usePagination.jsx";
import ChartSection from "./HistoryChartSection.jsx";
import axios from "axios";

const SalesHistoryMenu = () => {
  const [viewMode, setViewMode] = useState("list");
  const [chartResultList, setChartResultList] = useState([]);
  const [chartLoading, setChartLoading] = useState(false);
  const [chartSearchError, setChartSearchError] = useState(null);

  const yearList = useMemo(() => {
    const currentYear = new Date().getFullYear();
    const years = [];
    for (let i = 0; i < 5; i++) {
      years.push({ yearNumber: `${currentYear - i}` });
    }
    return years;
  }, []);

  const monthList = useMemo(() => {
    const months = [];
    for (let i = 1; i <= 12; i++) {
      months.push({ monthNumber: String(i).padStart(2, "0") });
    }
    return months;
  }, []);

  const initialFormState = useMemo(
    () => ({
      storeCode: "",
      yearNumber: "",
      monthNumber: "",
    }),
    []
  );

  const { execute: executeCrud, crudError, successMessage, setSuccessMessage } = useCrud();

  const {
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
  } = useDataFetching(
    initialFormState,
    "http://localhost:8080/api/sales-history/search-result",
    setSuccessMessage
  );

  const { currentPage, totalPages, handlePageChange, resetPagination } =
    usePagination(totalCount, 100);

  const { data: storeList } = useDropdownData("http://localhost:8080/api/stores");

  const handleSearch = async (mode) => {
    setSuccessMessage(null);
    setChartSearchError(null);
    setIsSearched(true);
    resetPagination();
    if (mode === "list") {
      fetchResults(formData, 1, 100);
    } else if (mode === "graph") {
      setChartLoading(true);
      try {
        const response = await axios.get("http://localhost:8080/api/sales-history/search-gragh", { params: formData });
        setChartResultList(response.data.resultList || []);
      } catch (error) {
        setChartSearchError("グラフデータの取得に失敗しました。");
        setChartResultList([]);
      } finally {
        setChartLoading(false);
      }
    }
  };

  const handleImportCsv = async (file) => {
    const data = new FormData();
    data.append("file", file);
    await executeCrud('post', 'sales-history/import-csv', data);
    handleSearch(viewMode);
  };

  const handleExportCsv = async () => {
    try {
      const response = await axios.get("http://localhost:8080/api/sales-history/export-csv", {
        params: formData,
        responseType: "blob",
      });
      const url = window.URL.createObjectURL(new Blob([response.data]));
      const link = document.createElement("a");
      link.href = url;
      link.setAttribute("download", "sales_history.csv");
      document.body.appendChild(link);
      link.click();
      link.remove();
      setSuccessMessage("CSVファイルが正常にエクスポートされました。");
    } catch (error) {
      console.error("CSVエクスポート中にエラーが発生しました:", error);
      setSuccessMessage("CSVエクスポート中にエラーが発生しました。");
    }
  };

  return (
    <main className="sales-history-menu">
      <section className="sales-history-container">
        <h1>販売履歴管理</h1>
        <SearchSection
          formData={formData}
          storeList={storeList}
          yearList={yearList}
          monthList={monthList}
          handleChange={handleChange}
          handleSearch={() => handleSearch(viewMode)}
          onImportCsv={handleImportCsv}
          onExportCsv={handleExportCsv}
          onToggleView={setViewMode}
          isSearched={isSearched}
        />
        <section className="result-area">
          {(loading || chartLoading) && (
            <div className="loading-message">検索中...</div>
          )}
          {crudError && <div className="message error">{crudError}</div>}
          {successMessage && (
            <div className="message success">{successMessage}</div>
          )}

          {isSearched && !searchError && viewMode === "list" && (
            <>
              <ResultListSection
                resultList={resultList}
                resultCountMessage={resultCountMessage}
              />
              <PaginationSection
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
              />
            </>
          )}
          {isSearched &&
            !chartSearchError &&
            viewMode === "graph" &&
            storeList && (
              <ChartSection
                chartResultList={chartResultList}
                chartLoading={chartLoading}
                chartSearchError={chartSearchError}
                formData={formData}
                storeList={storeList}
                chartTitle={"販売履歴グラフ"}
              />
            )}
          {isSearched && searchError && viewMode === "list" && (
            <div className="message error">{searchError}</div>
          )}
          {isSearched && chartSearchError && viewMode === "graph" && (
            <div className="message error">{chartSearchError}</div>
          )}
        </section>
      </section>
    </main>
  );
};

export default SalesHistoryMenu;