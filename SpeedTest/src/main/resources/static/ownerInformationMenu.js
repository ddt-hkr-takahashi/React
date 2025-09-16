// ownerInformationMenu.js

import { handleAction } from './common.js'; // 共通のイベントハンドラをインポート
import { ROUTES } from './constants.js'; // ルーティングの定数をインポート

// DOMContentLoadedイベント（HTMLドキュメントの読み込み完了）を待ってから処理を開始
document.addEventListener('DOMContentLoaded', function() {
    setupOwnerInformationMenu(); // 所有者情報メニューのセットアップ関数を呼び出し
});

/**
 * 所有者情報メニューページのボタンアクションを設定します。
 */
function setupOwnerInformationMenu() {
    const selectAllCheckbox = document.getElementById('selectAllOwners'); // 全選択チェックボックスを取得
    if (selectAllCheckbox) {
        // 全選択チェックボックスのchangeイベントリスナーを設定
        selectAllCheckbox.addEventListener('change', function() {
            // "owner_codes"という名前の全てのチェックボックスを取得
            const checkboxes = document.querySelectorAll('input[name="owner_codes"]');
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
            form.action = ROUTES.OWNER_INFO_SEARCH_RESULT; // フォームの送信先URLを設定
            form.submit(); // フォームを送信
        });
    }

    const addButton = document.querySelector('.add-button'); // 追加ボタンを取得
    if (addButton) {
        // 追加ボタンのclickイベントリスナーを設定
        addButton.addEventListener('click', function() {
            window.location.href = ROUTES.OWNER_INFO_ADD; // 所有者情報追加ページへ遷移
        });
    }

    // 編集ボタンのアクションを設定（common.jsのhandleAction関数を使用）
    handleAction('.edit-button', 'input[name="owner_codes"]', {
        min: 1, // 選択必須数
        max: 1, // 選択最大数
        minMessage: '編集する項目を選択してください。', // 最小数未満の場合の警告メッセージ
        maxMessage: '編集できる項目は1つだけです。複数選択されています。', // 最大数を超える場合の警告メッセージ
        action: (checkedCheckboxes) => {
            const ownerId = checkedCheckboxes[0].value; // 選択されたチェックボックスから所有者IDを取得
            // 編集ページへ所有者IDをクエリパラメータとして渡して遷移
            window.location.href = `${ROUTES.OWNER_INFO_EDIT}?owner_code=${ownerId}`;
        }
    });

    // 削除ボタンのアクションを設定（common.jsのhandleAction関数を使用）
    handleAction('.delete-button', 'input[name="owner_codes"]', {
        min: 1, // 選択必須数
        minMessage: '削除する項目を選択してください。', // 最小数未満の場合の警告メッセージ
        confirmMessage: '選択された項目を本当に削除しますか？', // 実行前の確認メッセージ
        action: (checkedCheckboxes) => {
            // 選択されたチェックボックスの値を配列に変換し、カンマ区切りの文字列に結合
            const ownerIds = Array.from(checkedCheckboxes).map(cb => cb.value).join(',');
            const deleteForm = document.getElementById('deleteFormOwner'); // 削除フォームを取得
            if (deleteForm) {
                deleteForm.action = ROUTES.OWNER_INFO_DELETE_DATA; // フォームの送信先URLを設定
                const input = document.createElement('input'); // 隠し入力フィールドを生成
                input.type = 'hidden'; // タイプを非表示に設定
                input.name = 'ownerIds'; // フィールド名を指定
                input.value = ownerIds; // 選択されたID文字列を値に設定
                deleteForm.appendChild(input); // フォームに隠しフィールドを追加
                deleteForm.submit(); // フォームを送信
            }
        }
    });
    
    // フォームの参照を取得
    const addForm = document.getElementById('addForm');
    const updateForm = document.getElementById('editForm');
    
    // 追加フォームが存在する場合
    if (addForm) {
        // 追加ボタンのclickイベントリスナーを設定
        document.getElementById('addButton').addEventListener('click', function() {
            addForm.action = ROUTES.OWNER_INFO_ADD_DATA; // フォームの送信先URLを設定
            addForm.submit(); // フォームを送信
        });
    }
    
    // 編集フォームが存在する場合
    if (updateForm) {
        // 更新ボタンのclickイベントリスナーを設定
        document.getElementById('updateButton').addEventListener('click', function() {
            updateForm.action = ROUTES.OWNER_INFO_EDIT_DATA; // フォームの送信先URLを設定
            updateForm.submit(); // フォームを送信
        });
    }

    const cancelButton = document.getElementById('cancelButton'); // キャンセルボタンを取得
    if (cancelButton) {
        // キャンセルボタンのclickイベントリスナーを設定
        cancelButton.addEventListener('click', function() {
            window.location.href = ROUTES.OWNER_INFO_MENU; // メニューページへ遷移
        });
    }
}