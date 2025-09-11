import React, { useMemo, useEffect } from "react";
import useDataFetching from "/src/.jsx/useDataFetching.jsx";
import useDropdownData from "/src/.jsx/useDropdownData.jsx";
import { useCrud } from "/src/.jsx/useCrud.jsx";
import useUiState from "/src/.jsx/useUiState.jsx";
import PaginationSection from "/src/.jsx/PaginationSection.jsx";
import useCheckboxSelection from "/src/.jsx/useCheckboxSelection.jsx";
import SearchSection from "./StoreSearchSection.jsx";
import ResultListSection from "./StoreResultListSection.jsx";
import StoreAddEditModal from "./StoreAddEditModal.jsx";
import usePagination from "/src/.jsx/usePagination.jsx";

const BranchesInformationMenu = () => {
  const initialFormState = useMemo(
    () => ({
      prefCode: "",
      storeCode: "",
      branchName: "",
    }),
    []
  );

  const {
    execute: executeCrud,
    loading: crudLoading,
    error: crudError,
    successMessage,
    setSuccessMessage,
  } = useCrud();

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
    "http://localhost:8080/api/branch-info/search",
    setSuccessMessage
  );

  const {
    data: prefList,
    loading: prefsLoading,
    error: prefsError,
  } = useDropdownData("http://localhost:8080/api/prefs");
  const {
    data: stores,
    loading: storesLoading,
    error: storesError,
  } = useDropdownData("http://localhost:8080/api/stores");

  const {
    isModalOpen,
    currentEditData,
    isFixedButtonsVisible,
    setIsModalOpen,
    setIsFixedButtonsVisible,
    handleAddClick,
    handleEditClick,
  } = useUiState();

  const {
    selectedItems,
    handleItemSelect,
    handleAllItemsSelect,
    isAllSelected,
    resetSelection,
  } = useCheckboxSelection(resultList, "branchCode");

  const { currentPage, totalPages, handlePageChange, resetPagination } =
    usePagination(totalCount, 10);

  const handleSearch = () => {
    fetchResults(formData, currentPage - 1, 10);
    resetSelection();
  };

  useEffect(() => {
    if (isSearched) {
      handleSearch();
    }
  }, [currentPage]);

  useEffect(() => {
    if (successMessage && isSearched) {
      handleSearch();
    }
  }, [successMessage]);

  useEffect(() => {
    if (!isModalOpen) {
      resetSelection();
    }
  }, [isModalOpen]);

  const handleAddButtonClick = () => {
    handleAddClick();
  };

  const handleEditButtonClick = () => {
    const editItem = resultList.find((item) =>
      selectedItems.has(item.branchCode)
    );
    handleEditClick(editItem);
  };

  const handleSave = async (data) => {
    const isEditMode = !!data.branchCode;
    const method = isEditMode ? "put" : "post";
    const response = await executeCrud(
      method,
      "branches",
      data
    );
    if (response) {
      setIsModalOpen(false);
    }
  };

  const handleDelete = async () => {
    const branchCodes = Array.from(selectedItems);
    const response = await executeCrud(
      "delete",
      "branches",
      { branchCodes }
    );
    if (response) {
      resetSelection();
    }
  };

  if (prefsLoading || storesLoading) {
    return <div>Loading...</div>;
  }

  if (prefsError || storesError) {
    return <div>データの取得に失敗しました</div>;
  }

  return (
    <main>
      <section>
        <h1 className="title">支店情報管理</h1>
        <div className="menu-container">
          <SearchSection
            formData={formData}
            prefList={prefList}
            stores={stores}
            handleChange={handleChange}
            handleSearch={handleSearch}
            onAddClick={handleAddButtonClick}
          />

          {loading && <div>検索中...</div>}
          {crudLoading && <div>処理中...</div>}
          {crudError && <div className="message error">{crudError}</div>}
          {successMessage && (
            <div className="message success">{successMessage}</div>
          )}

          {isSearched && !loading && !searchError && (
            <>
              <ResultListSection
                resultList={resultList}
                resultCountMessage={resultCountMessage}
                selectedItems={selectedItems}
                onItemSelect={handleItemSelect}
                onAllItemsSelect={handleAllItemsSelect}
                isAllSelected={isAllSelected}
              />
              <PaginationSection
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
              />
            </>
          )}
          {isSearched && !loading && searchError && (
            <div className="message-error">{searchError}</div>
          )}
        </div>

        <div
          className={`fixed-bottom-container ${
            !isFixedButtonsVisible ? "hidden" : ""
          }`}
        >
          <div className="button-group button-spacing">
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

        <button
          className="toggle-button"
          onClick={() => setIsFixedButtonsVisible(!isFixedButtonsVisible)}
        >
          {isFixedButtonsVisible ? "▼" : "▲"}
        </button>

        <StoreAddEditModal
          isOpen={isModalOpen}
          onClose={() => setIsModalOpen(false)}
          onSave={handleSave}
          initialData={currentEditData}
          dropdownData={{
            prefs: prefList,
            stores: stores,
          }}
        />
      </section>
    </main>
  );
};

export default BranchesInformationMenu;