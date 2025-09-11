/**
 * 支店情報管理画面のメインコンポーネント
 * 検索、追加、編集、削除、ページネーション、UI状態管理を行う
 * @param なし
 */
// BranchesInformationMenu.jsx
import React, { useMemo, useEffect } from "react";
// データ取得フックをインポート
import useDataFetching from "/src/.jsx/useDataFetching.jsx";
// ドロップダウンデータ取得フックをインポート
import useDropdownData from "/src/.jsx/useDropdownData.jsx";
// CRUD操作フックをインポート
import { useCrud } from "/src/.jsx/useCrud.jsx";
// UI状態管理フックをインポート
import useUiState from "/src/.jsx/useUiState.jsx";
// ページネーションコンポーネントをインポート
import PaginationSection from "/src/.jsx/PaginationSection.jsx";
// チェックボックス選択フックをインポート
import useCheckboxSelection from "/src/.jsx/useCheckboxSelection.jsx";
// 検索セクションコンポーネントをインポート
import SearchSection from "./StoreSearchSection.jsx";
// 結果リストセクションコンポーネントをインポート
import ResultListSection from "./StoreResultListSection.jsx";
// モーダルコンポーネントをインポート
import StoreAddEditModal from "./StoreAddEditModal.jsx";
// ページネーションロジックフックをインポート
import usePagination from "/src/.jsx/usePagination.jsx";

const BranchesInformationMenu = () => {
  // 初期フォーム状態を定義
  const initialFormState = useMemo(
    () => ({
      prefCode: "",
      storeCode: "",
      branchName: "",
    }),
    []
  );

  // CRUD操作フックを初期化
  const {
    execute: executeCrud,
    loading: crudLoading,
    error: crudError,
    successMessage,
    setSuccessMessage,
  } = useCrud();

  // データ取得フックを初期化
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
    "http://localhost:8080/api/branches/search",
    setSuccessMessage
  );

  // ドロップダウンデータ取得フックを初期化
  const {
    data: prefList,
    loading: prefsLoading,
    error: prefsError,
  } = useDropdownData("http://localhost:8080/api/branches/prefs");
  const {
    data: stores,
    loading: storesLoading,
    error: storesError,
  } = useDropdownData("http://localhost:8080/api/branches/stores");

  // UI状態管理フックを初期化
  const {
    isModalOpen,
    currentEditData,
    isFixedButtonsVisible,
    setIsModalOpen,
    setIsFixedButtonsVisible,
    handleAddClick,
    handleEditClick,
  } = useUiState();

  // チェックボックス選択フックを初期化
  const {
    selectedItems,
    handleItemSelect,
    handleAllItemsSelect,
    isAllSelected,
    resetSelection,
  } = useCheckboxSelection(resultList, "branchCode");

  // ページネーションフックを初期化
  const { currentPage, totalPages, handlePageChange, resetPagination } =
    usePagination(totalCount, 10);

  // 検索処理を実行
  const handleSearch = () => {
    fetchResults(formData, currentPage - 1, 10);
    resetSelection();
  };

  // ページ変更時に検索を再実行
  useEffect(() => {
    if (isSearched) {
      handleSearch();
    }
  }, [currentPage]);

  // CRUD操作成功時に再検索
  useEffect(() => {
    if (successMessage && isSearched) {
      handleSearch();
    }
  }, [successMessage]);

  // モーダルが閉じられた時に選択状態をリセット
  useEffect(() => {
    if (!isModalOpen) {
      resetSelection();
    }
  }, [isModalOpen]);

  // 新規追加ボタンクリック時の処理
  const handleAddButtonClick = () => {
    handleAddClick();
  };

  // 編集ボタンクリック時の処理
  const handleEditButtonClick = () => {
    const editItem = resultList.find((item) =>
      selectedItems.has(item.branchCode)
    );
    handleEditClick(editItem);
  };

  // 保存処理
  const handleSave = async (data) => {
    // 編集モード判定
    const isEditMode = !!data.branchCode;
    const method = isEditMode ? "put" : "post";
    // CRUD操作を実行
    const response = await executeCrud(
      method,
      "branches",
      data
    );
    // レスポンスがあればモーダルを閉じる
    if (response) {
      setIsModalOpen(false);
    }
  };

  // 削除処理
  const handleDelete = async () => {
    // 選択されたアイテムのコードを抽出
    const branchCodes = Array.from(selectedItems);
    // CRUD操作を実行
    const response = await executeCrud(
      "delete",
      "branches",
      { branchCodes }
    );
    // レスポンスがあれば選択状態をリセット
    if (response) {
      resetSelection();
    }
  };

  // ロード中表示
  if (prefsLoading || storesLoading) {
    return <div>Loading...</div>;
  }

  // エラー表示
  if (prefsError || storesError) {
    return <div>データの取得に失敗しました</div>;
  }

  return (
    <main>
      <section>
        <h1 className="title">支店情報管理</h1>
        <div className="menu-container">
          {/* 検索セクション */}
          <SearchSection
            formData={formData}
            prefList={prefList}
            stores={stores}
            handleChange={handleChange}
            handleSearch={handleSearch}
            onAddClick={handleAddButtonClick}
          />

          {/* ロード中とエラーメッセージ表示 */}
          {loading && <div>検索中...</div>}
          {crudLoading && <div>処理中...</div>}
          {crudError && <div className="message error">{crudError}</div>}
          {successMessage && (
            <div className="message success">{successMessage}</div>
          )}

          {/* 検索結果セクション */}
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
              {/* ページネーションセクション */}
              <PaginationSection
                currentPage={currentPage}
                totalPages={totalPages}
                onPageChange={handlePageChange}
              />
            </>
          )}
          {/* 検索エラーメッセージ表示 */}
          {isSearched && !loading && searchError && (
            <div className="message-error">{searchError}</div>
          )}
        </div>

        {/* 固定フッターボタン */}
        <div
          className={`fixed-bottom-container ${
            !isFixedButtonsVisible ? "hidden" : ""
          }`}
        >
          <div className="button-group button-spacing">
            {/* 編集ボタン */}
            <button
              className="btn"
              onClick={handleEditButtonClick}
              disabled={selectedItems.size !== 1}
            >
              編集
            </button>
            {/* 削除ボタン */}
            <button
              className="btn"
              onClick={handleDelete}
              disabled={selectedItems.size === 0 || crudLoading}
            >
              削除
            </button>
          </div>
        </div>

        {/* ボタン表示/非表示トグル */}
        <button
          className="toggle-button"
          onClick={() => setIsFixedButtonsVisible(!isFixedButtonsVisible)}
        >
          {isFixedButtonsVisible ? "▼" : "▲"}
        </button>

        {/* 追加/編集モーダル */}
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