// branchInformationMenu.js

import { handleAction } from './common.js'; // 共通処理をインポート
import { ROUTES } from './constants.js'; // ルーティング定数をインポート

// DOMContentLoadedイベント（HTMLドキュメントの読み込み完了）を待ってから処理を開始
document.addEventListener('DOMContentLoaded', function() {
    // 現在のページのパスを取得
    const path = window.location.pathname;

    // パスに応じて適切な初期化関数を呼び出す
    if (path.includes(ROUTES.BRANCH_INFO_ADD)) {
        setupAddFormActions(); // 追加ページの場合の処理
    } else if (path.includes(ROUTES.BRANCH_INFO_EDIT)) {
        setupEditFormActions(); // 編集ページの場合の処理
    } else {
        setupBranchInfoMenu(); // メニューページ（一覧、検索）の場合の処理
    }
});

/**
 * 支店情報メニューページのボタンアクションを設定します。
 */
function setupBranchInfoMenu() {
    const selectAllCheckbox = document.getElementById('selectAll'); // 全選択チェックボックスを取得
    if (selectAllCheckbox) {
        // 全選択チェックボックスのchangeイベントリスナーを設定
        selectAllCheckbox.addEventListener('change', function() {
            // "branch_codes"という名前の全てのチェックボックスを取得
            const checkboxes = document.querySelectorAll('input[name="branch_codes"]');
            checkboxes.forEach(checkbox => {
                // 全選択チェックボックスの状態に合わせて、他のチェックボックスの状態を設定
                checkbox.checked = this.checked;
            });
        });
    }

    const searchButton = document.getElementById('searchButton'); // 検索ボタンを取得
    if (searchButton) {
        // 検索ボタンのclickイベントリスナーを設定
        searchButton.addEventListener('click', function() {
            const form = document.getElementById('searchForm'); // 検索フォームを取得
            form.action = ROUTES.BRANCH_INFO_SEARCH_RESULT; // フォームの送信先URLを設定
            form.submit(); // フォームを送信
        });
    }

    const addButton = document.querySelector('.add-button'); // 追加ボタンを取得
    if (addButton) {
        // 追加ボタンのclickイベントリスナーを設定
        addButton.addEventListener('click', function() {
            window.location.href = ROUTES.BRANCH_INFO_ADD; // 追加ページへ遷移
        });
    }

    // 編集ボタンのアクションを設定（common.jsのhandleAction関数を使用）
    handleAction('.edit-button', 'input[name="branch_codes"]', {
        min: 1, // 選択必須数
        max: 1, // 選択最大数
        action: (checkedCheckboxes) => {
            const branchCode = checkedCheckboxes[0].value; // 選択されたチェックボックスから支店コードを取得
            // 編集ページへ支店コードをクエリパラメータとして渡して遷移
            window.location.href = `${ROUTES.BRANCH_INFO_EDIT}?branchCode=${branchCode}`;
        }
    });

    // 削除ボタンのアクションを設定（common.jsのhandleAction関数を使用）
    handleAction('.delete-button', 'input[name="branch_codes"]', {
        min: 1, // 選択必須数
        action: () => {
            const deleteForm = document.getElementById('deleteForm'); // 削除フォームを取得
            deleteForm.action = ROUTES.BRANCH_INFO_DELETE_DATA; // フォームの送信先URLを設定
            deleteForm.submit(); // フォームを送信
        }
    });

    const cancelButton = document.getElementById('cancelButton'); // キャンセルボタンを取得
    if (cancelButton) {
        // キャンセルボタンのclickイベントリスナーを設定
        cancelButton.addEventListener('click', function() {
            window.location.href = ROUTES.BRANCH_INFO_MENU; // メニューページへ遷移
        });
    }
}

/**
 * 支店情報追加フォームページのボタンアクションを設定します。
 */
function setupAddFormActions() {
    const addButton = document.getElementById('addButton'); // 追加フォームの追加ボタンを取得
    if (addButton) {
        // 追加ボタンのclickイベントリスナーを設定
        addButton.addEventListener('click', function() {
            const form = document.getElementById('addForm'); // 追加フォームを取得
            if (form) {
                form.action = ROUTES.BRANCH_INFO_ADD_DATA; // フォームの送信先URLを設定
                form.submit(); // フォームを送信
            }
        });
    }

    const cancelButton = document.querySelector('.cancel-button'); // キャンセルボタンを取得
    if (cancelButton) {
        // キャンセルボタンのclickイベントリスナーを設定
        cancelButton.addEventListener('click', function() {
            window.location.href = ROUTES.BRANCH_INFO_MENU; // メニューページへ遷移
        });
    }
}

/**
 * 支店情報編集フォームページのボタンアクションを設定します。
 */
function setupEditFormActions() {
    const updateButton = document.getElementById('updateButton'); // 編集フォームの更新ボタンを取得
    if (updateButton) {
        // 更新ボタンのclickイベントリスナーを設定
        updateButton.addEventListener('click', function() {
            const form = document.getElementById('editForm'); // 編集フォームを取得
            if (form) {
                form.action = ROUTES.BRANCH_INFO_EDIT_DATA; // フォームの送信先URLを設定
                form.submit(); // フォームを送信
            }
        });
    }

    const cancelButton = document.querySelector('.cancel-button'); // キャンセルボタンを取得
    if (cancelButton) {
        // キャンセルボタンのclickイベントリスナーを設定
        cancelButton.addEventListener('click', function() {
            window.location.href = ROUTES.BRANCH_INFO_MENU; // メニューページへ遷移
        });
    }
}