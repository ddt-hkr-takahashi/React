// src/Owner/OwnersInformationMenu.jsx
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

/**
 * 元所有者情報管理画面のメインコンポーネント
 * 検索、一覧表示、追加、編集、削除機能を管理する
 */
const OwnersInformationMenu = () => {
  // フォームの初期状態をメモ化
  const initialFormState = useMemo(
    () => ({
      ownerName: "",
      gender: "",
      ageMin: "",
      ageMax: "",
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

  // データ取得と検索用のカスタムフック
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

  // ページネーション用のカスタムフック
  const { currentPage, totalPages, handlePageChange, resetPagination } =
    usePagination(totalCount, 100);

  // UIの状態管理（モーダル、ボタン表示など）用のカスタムフック
  const {
    isModalOpen,
    currentEditData,
    isFixedButtonsVisible,
    setIsModalOpen,
    setIsFixedButtonsVisible,
    handleAddClick,
    handleEditClick,
  } = useUiState();

  // チェックボックス選択用のカスタムフック
  const { selectedItems, isAllSelected, handleItemSelect, handleAllItemsSelect, resetSelection } =
    useCheckboxSelection(resultList, "ownerCode");

  /**
   * 検索処理を実行する
   * ページングをリセットしてデータを再取得する
   */
  const handleSearch = () => {
    resetPagination();
    setSuccessMessage(null); // 成功メッセージをクリア
    setIsSearched(true); // 検索済み状態に設定
    fetchResults(formData, 1, 100); // 1ページ目から検索を実行
  };

  /**
   * 編集ボタンがクリックされたときの処理
   */
  const handleEditButtonClick = () => {
    // 選択された唯一のアイテムの ownerCode を見つける
    const selectedOwnerCode = selectedItems.values().next().value;
    // 選択されたアイテムのデータを取得
    const itemToEdit = resultList.find(item => item.ownerCode === selectedOwnerCode);
    if (itemToEdit) {
      handleEditClick(itemToEdit);
    }
  };

  /**
   * 追加または編集を保存する
   * @param {object} data - 保存するデータ
   */
  const handleSave = async (data) => {
    let method = data.ownerCode ? "put" : "post";
    let endpoint = data.ownerCode ? `owners/update/${data.ownerCode}` : "owners/add";
    const response = await executeCrud(method, endpoint, data);
    if (response) {
      setIsModalOpen(false); // モーダルを閉じる
      fetchResults(formData, currentPage, 100); // 現在のページを再検索してデータを更新
      resetSelection(); // チェックボックスの選択をリセット
    }
  };

  /**
   * 選択したアイテムを削除する
   */
  const handleDelete = async () => {
    if (window.confirm(`${selectedItems.size}件のデータを削除しますか？`)) {
      const data = { ownerCodes: Array.from(selectedItems) };
      const response = await executeCrud("delete", "owners/delete", data);
      if (response) {
        fetchResults(formData, currentPage, 100); // 現在のページを再検索してデータを更新
        resetSelection(); // チェックボックスの選択をリセット
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
          {/* ローディングメッセージ */}
          {loading && <div className="loading-message">検索中...</div>}
          {/* CRUD操作のエラーメッセージ */}
          {crudError && <div className="message error">{crudError}</div>}
          {/* 成功メッセージ */}
          {successMessage && <div className="message success">{successMessage}</div>}

          {/* 検索結果表示 */}
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
          {/* 検索エラーメッセージ */}
          {isSearched && !loading && searchError && (
            <div className="message error">{searchError}</div>
          )}
        </section>

        {/* 画面下部に固定されるボタン群 */}
        <div
          className={`fixed-bottom-container ${
            !isFixedButtonsVisible ? "hidden" : ""
          }`}
        >
          <div className="button-group button-spacing">
            <button
              className="btn"
              onClick={handleEditButtonClick}
              disabled={selectedItems.size !== 1} // 1件だけ選択されている場合のみ有効化
            >
              編集
            </button>
            <button
              className="btn"
              onClick={handleDelete}
              disabled={selectedItems.size === 0 || crudLoading} // 選択アイテムがない、またはローディング中は無効化
            >
              削除
            </button>
          </div>
        </div>

        {/* 固定ボタンの表示・非表示を切り替えるトグルボタン */}
        <button
          className="toggle-button"
          onClick={() => setIsFixedButtonsVisible(!isFixedButtonsVisible)}
        >
          {isFixedButtonsVisible ? "▼" : "▲"}
        </button>

        {/* 追加・編集用のモーダル */}
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