// /src/Cars/SalesInformationMenu.jsx
import React, { useMemo, useEffect } from "react";
import useDataFetching from "/src/.jsx/useDataFetching.jsx";
import useDropdownData from "/src/.jsx/useDropdownData.jsx";
import { useCrud } from "/src/.jsx/useCrud.jsx";
import useUiState from "/src/.jsx/useUiState.jsx";
import PaginationSection from "/src/.jsx/PaginationSection.jsx";
import useCheckboxSelection from "/src/.jsx/useCheckboxSelection.jsx";
import SearchSection from "./CarsSearchSection.jsx";
import ResultListSection from "./CarsResultListSection.jsx";
import AddEditModal from "./CarsAddEditModal.jsx";
import usePagination from "/src/.jsx/usePagination.jsx";

/**
 * 販売情報管理画面のメインコンポーネント
 * 検索、リスト表示、CRUD操作、ページネーション、UI状態の管理を統合する
 */
const SalesInformationMenu = () => {
  // フォームの初期状態をメモ化
  const initialFormState = useMemo(
    () => ({
      prefCode: "",
      branchCode: "",
      makerCode: "",
      typeCode: "",
      modelName: "",
      ownerCode: "",
      ownerless: false,
    }),
    []
  );

  // CRUD操作用のカスタムフック
  const {
    execute: executeCrud,
    loading: crudLoading,
    error: crudError,
    successMessage,
    setSuccessMessage,
  } = useCrud();

  // データ取得用のカスタムフック
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
    "http://localhost:8080/api/sales-information/search-result",
    setSuccessMessage
  );

  // ページネーション用のカスタムフック
  const { currentPage, totalPages, handlePageChange, resetPagination } =
    usePagination(totalCount, 100);

  // ページネーションの変更時に検索結果を再取得
  useEffect(() => {
    if (isSearched) {
      fetchResults(formData, currentPage, 100);
    }
  }, [currentPage, isSearched, fetchResults, formData]);

  /**
   * 検索ボタンクリック時の処理
   * ページネーションをリセットして、検索を実行する
   * @param {Event} e - イベントオブジェクト
   */
  const handleSearch = async (e) => {
    if (e) e.preventDefault();
    resetPagination();
    setIsSearched(true);
    fetchResults(formData, 1, 100);
  };

  // ドロップダウンデータ取得用のカスタムフック
  const { data: prefList } = useDropdownData(
    "http://localhost:8080/api/prefs"
  );
  const { data: makerList } = useDropdownData(
    "http://localhost:8080/api/makers"
  );
  const { data: typeList } = useDropdownData(
    "http://localhost:8080/api/types"
  );
  const { data: ownerList } = useDropdownData(
    "http://localhost:8080/api/owners"
  );
  const { data: modelList } = useDropdownData(
    "http://localhost:8080/api/models"
  );
  // 都道府県コードに基づいて支店データを動的に取得
  const branchUrl = formData.prefCode ? `http://localhost:8080/api/branches?prefCode=${formData.prefCode}` : `http://localhost:8080/api/branches`;
  const { data: branchList } = useDropdownData(
    branchUrl, [formData.prefCode]
  );
  
  // UI状態管理用のカスタムフック
  const {
    isModalOpen,
    currentEditData,
    isFixedButtonsVisible,
    setIsModalOpen,
    setIsFixedButtonsVisible,
    handleAddClick: useUiStateHandleAddClick,
    handleEditClick: useUiStateHandleEditClick,
  } = useUiState();

  // チェックボックス選択管理用のカスタムフック
  const { selectedItems, handleItemSelect, handleAllItemsSelect, resetSelection, isAllSelected } = useCheckboxSelection(resultList, "carId");

  /**
   * 親コンポーネントからのフォーム入力変更ハンドラー
   * @param {Event} e - 変更イベント
   */
  const handleInputChange = (e) => {
    const { name, value, type, checked } = e.target;
    handleChange({
      target: {
        name,
        value: type === "checkbox" ? checked : value,
      },
    });
    // 都道府県が変更されたら支店コードをリセット
    if (name === "prefCode") {
      handleChange({ target: { name: "branchCode", value: "" } });
    }
  };

  /**
   * 新規追加ボタンクリック時の処理
   * 選択状態をリセットし、UIフックのhandleAddClickを呼び出す
   */
  const handleAddClick = () => {
    resetSelection();
    useUiStateHandleAddClick();
  };

  /**
   * 編集ボタンクリック時の処理
   * 選択されたアイテムの編集をUIフックに委譲
   */
  const handleEditButtonClick = () => {
    const carId = selectedItems.values().next().value;
    const itemToEdit = resultList.find(item => item.carId === carId);
    if (itemToEdit) {
      useUiStateHandleEditClick(itemToEdit);
    }
  };

  /**
   * 販売済み操作時の処理
   * @param {Event} e - イベントオブジェクト
   */
  const handleSales = async (e) => {
    e.preventDefault();
    const carIds = Array.from(selectedItems);
    await executeCrud('put', 'sales-information/sold-out', { carIds });
    resetSelection();
    handleSearch();
  };

  /**
   * 削除操作時の処理
   * @param {Event} e - イベントオブジェクト
   */
  const handleDelete = async (e) => {
    e.preventDefault();
    const carIds = Array.from(selectedItems);
    await executeCrud('delete', 'sales-information/delete', { carIds });
    resetSelection();
    handleSearch();
  };

  /**
   * モーダルの保存ボタンクリック時の処理
   * @param {object} data - 保存データ
   */
  const handleSave = async (data) => {
    const method = data.carId ? 'put' : 'post';
    const endpoint = data.carId ? `sales-information/update` : 'sales-information/add';
    const result = await executeCrud(method, endpoint, data);
    if (result) {
      setIsModalOpen(false);
      resetSelection();
      handleSearch();
    }
  };
  
  return (
    <div className="container">
      <h1>販売情報管理</h1>

      {/* 成功メッセージ */}
      {successMessage && <div className="message success">{successMessage}</div>}
      
      {/* 検索フォーム */}
      <SearchSection
        formData={formData}
        prefList={prefList}
        branchList={branchList}
        makerList={makerList}
        typeList={typeList}
        handleChange={handleInputChange}
        handleSearch={handleSearch}
        onAddClick={handleAddClick}
      />

      {/* 検索結果 */}
      <section className="result-area">
        {loading && <div className="loading-message">検索中...</div>}
        {searchError && <div className="message error">{searchError}</div>}
        {/* 検索結果リストとメッセージ */}
        {isSearched && !loading && !searchError && (
          <ResultListSection
            resultList={resultList}
            resultCountMessage={resultCountMessage}
            selectedItems={selectedItems}
            onItemSelect={handleItemSelect}
            onAllItemsSelect={handleAllItemsSelect}
            isAllSelected={isAllSelected}
          />
        )}
        
        {/* ページネーション */}
        {isSearched && !loading && resultList.length > 0 && totalPages > 1 && (
          <div className="pagination-container">
            <PaginationSection
              currentPage={currentPage}
              totalPages={totalPages}
              onPageChange={handlePageChange}
            />
          </div>
        )}
      </section>

      {/* ページ下部に固定されるボタン */}
      <div
        className={`fixed-bottom-container ${!isFixedButtonsVisible ? "hidden" : ""}`}
      >
        <div className="button-group button-spacing">
          <button
            className="btn"
            onClick={handleSales}
            disabled={selectedItems.size === 0 || crudLoading}
          >
            販売済み
          </button>
          <button
            className="btn"
            onClick={handleEditButtonClick}
            disabled={selectedItems.size !== 1}
          >
            編集
          </button>
          <button
            className="btn"
            onClick={handleDelete}
            disabled={selectedItems.size === 0 || crudLoading}
          >
            削除
          </button>
        </div>
      </div>

      {/* 固定ボタンの表示切替ボタン */}
      <button
        className="toggle-button"
        onClick={() => setIsFixedButtonsVisible(!isFixedButtonsVisible)}
      >
        {isFixedButtonsVisible ? "▼" : "▲"}
      </button>

      {/* 新規追加・編集モーダル */}
      <AddEditModal
        isOpen={isModalOpen}
        onClose={() => setIsModalOpen(false)}
        onSave={handleSave}
        initialData={currentEditData}
        dropdownData={{
          prefs: prefList,
          branches: branchList,
          makers: makerList,
          types: typeList,
          owners: ownerList,
          models: modelList,
        }}
      />
    </div>
  );
};

export default SalesInformationMenu;