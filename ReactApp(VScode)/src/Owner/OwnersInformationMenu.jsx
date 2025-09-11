import React, { useMemo } from "react";
import useDataFetching from "/src/.jsx/useDataFetching.jsx";
import { useCrud } from "/src/.jsx/useCrud.jsx";
import useUiState from "/src/.jsx/useUiState.jsx";
import PaginationSection from "/src/.jsx/PaginationSection.jsx";
import useCheckboxSelection from "/src/.jsx/useCheckboxSelection.jsx";
import OwnerSearchSection from "./OwnerSearchSection.jsx";
import OwnerResultListSection from "./OwnerResultListSection.jsx";
import OwnerAddEditModal from "./OwnerAddEditModal.jsx";
import usePagination from "/src/.jsx/usePagination.jsx";

const OwnersInformationMenu = () => {
  const initialFormState = useMemo(
    () => ({
      ownerName: "",
      gender: "",
      ageMin: "",
      ageMax: "",
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
    "http://localhost:8080/api/owners/search",
    setSuccessMessage
  );

  const { currentPage, totalPages, handlePageChange, resetPagination } =
    usePagination(totalCount, 100);

  const {
    isModalOpen,
    currentEditData,
    isFixedButtonsVisible,
    setIsModalOpen,
    setIsFixedButtonsVisible,
    handleAddClick,
    handleEditClick,
  } = useUiState();

  const { selectedItems, isAllSelected, handleItemSelect, handleAllItemsSelect, resetSelection } =
    useCheckboxSelection(resultList, "ownerCode");

  const handleSearch = () => {
    resetPagination();
    setSuccessMessage(null);
    setIsSearched(true);
    fetchResults(formData, 1, 100);
  };

  const handleEditButtonClick = () => {
    const selectedOwnerCode = selectedItems.values().next().value;
    const itemToEdit = resultList.find(item => item.ownerCode === selectedOwnerCode);
    if (itemToEdit) {
      handleEditClick(itemToEdit);
    }
  };

  const handleSave = async (data) => {
    let method = data.ownerCode ? "put" : "post";
    let endpoint = data.ownerCode ? `owners/update/${data.ownerCode}` : "owners/add";
    const response = await executeCrud(method, endpoint, data);
    if (response) {
      setIsModalOpen(false);
      fetchResults(formData, currentPage, 100);
      resetSelection();
    }
  };

  const handleDelete = async () => {
    if (window.confirm(`${selectedItems.size}件のデータを削除しますか？`)) {
      const data = { ownerCodes: Array.from(selectedItems) };
      const response = await executeCrud("delete", "owners/delete", data);
      if (response) {
        fetchResults(formData, currentPage, 100);
        resetSelection();
      }
    }
  };

  return (
    <main className="owner-information-menu">
      <section className="owner-container">
        <h1>元所有者情報管理</h1>
        <OwnerSearchSection
          formData={formData}
          handleChange={handleChange}
          handleSearch={handleSearch}
          onAddClick={handleAddClick}
        />
        <section className="result-area">
          {loading && <div className="loading-message">検索中...</div>}
          {crudError && <div className="message error">{crudError}</div>}
          {successMessage && <div className="message success">{successMessage}</div>}

          {isSearched && !loading && !searchError && (
            <>
              <OwnerResultListSection
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
            <div className="message error">{searchError}</div>
          )}
        </section>

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

        <OwnerAddEditModal
          isOpen={isModalOpen}
          onClose={() => setIsModalOpen(false)}
          onSave={handleSave}
          initialData={currentEditData}
        />
      </section>
    </main>
  );
};

export default OwnersInformationMenu;