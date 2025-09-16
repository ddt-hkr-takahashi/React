// salesInformationMenu.js

import { handleAction } from './common.js';
import { ROUTES } from './constants.js';

/**
 * 販売情報追加フォームページのボタンアクションを設定します。
 */
function setupAddFormActions() {
    const addButton = document.getElementById('addButton');
    if (addButton) {
        addButton.addEventListener('click', function() {
            const form = document.getElementById('addForm');
            if (form) {
                form.action = ROUTES.SALES_INFO_ADD_DATA;
                form.submit();
            }
        });
    }

    const cancelButton = document.querySelector('.cancel-button');
    if (cancelButton) {
        cancelButton.addEventListener('click', function() {
            window.location.href = ROUTES.SALES_INFO_MENU;
        });
    }
}

/**
 * 販売情報編集フォームページのボタンアクションを設定します。
 */
function setupEditFormActions() {
    const updateButton = document.getElementById('updateButton');
    if (updateButton) {
        updateButton.addEventListener('click', function() {
            const form = document.getElementById('editForm');
            if (form) {
                form.action = ROUTES.SALES_INFO_EDIT_DATA;
                form.submit();
            }
        });
    }

    const cancelButton = document.querySelector('.cancel-button');
    if (cancelButton) {
        cancelButton.addEventListener('click', function() {
            window.location.href = ROUTES.SALES_INFO_MENU;
        });
    }
}

document.addEventListener('DOMContentLoaded', function() {

    // 検索フォームの要素を取得
    const searchForm = document.getElementById('searchForm');
    const searchButton = document.getElementById('searchButton');
    const paginationLinks = document.querySelectorAll('.pagination a');

    // 全選択チェックボックスの機能
    const selectAllCheckbox = document.getElementById('selectAll');
    if (selectAllCheckbox) {
        selectAllCheckbox.addEventListener('change', function() {
            const checkboxes = document.querySelectorAll('input[name="car_id"]');
            checkboxes.forEach(checkbox => {
                checkbox.checked = this.checked;
            });
        });
    }
    
    // 検索ボタンのクリックイベント
    if (searchButton) {
        searchButton.addEventListener('click', function(event) {
            event.preventDefault();
            const pageInput = document.getElementById('pageInput');
            if (pageInput) {
                pageInput.value = 1;
            }
            const form = document.getElementById('searchForm');
            form.action = ROUTES.SALES_INFO_SEARCH_RESULT;
            form.submit();
        });
    }

    // ページネーションリンクのクリックイベント
    if (paginationLinks.length > 0) {
        paginationLinks.forEach(link => {
            link.addEventListener('click', function(event) {
                event.preventDefault();
                const form = document.getElementById('searchForm');
                const pageInput = document.getElementById('pageInput');
                
                const url = new URL(this.href, window.location.origin);
                const page = url.searchParams.get('page');

                if (pageInput && page) {
                    pageInput.value = page;
                    form.action = ROUTES.SALES_INFO_SEARCH_RESULT;
                    form.submit();
                }
            });
        });
    }

    // handleAction() を使用する各種ボタンのイベント設定
    handleAction('.edit-button', 'input[name="car_id"]', {
        min: 1,
        max: 1,
        action: (checkedCheckboxes) => {
            const carId = checkedCheckboxes[0].value;
            window.location.href = `${ROUTES.SALES_INFO_EDIT}?car_id=${carId}`;
        }
    });

    handleAction('.delete-button', 'input[name="car_id"]', {
        min: 1,
        action: (checkedCheckboxes) => {
            const carIds = Array.from(checkedCheckboxes).map(cb => cb.value).join(',');
            const deleteForm = document.getElementById('deleteForm');
            if (deleteForm) {
                deleteForm.action = ROUTES.SALES_INFO_DELETE_DATA;
                const input = document.createElement('input');
                input.type = 'hidden';
                input.name = 'carIds';
                input.value = carIds;
                deleteForm.appendChild(input);
                deleteForm.submit();
            }
        }
    });

    handleAction('.sale-button', 'input[name="car_id"]', {
        min: 1,
        action: (checkedCheckboxes) => {
            const form = document.createElement('form');
            form.setAttribute('method', 'post');
            form.setAttribute('action', ROUTES.SALES_INFO_SALES_DATA);

            checkedCheckboxes.forEach(checkbox => {
                const input = document.createElement('input');
                input.setAttribute('type', 'hidden');
                input.setAttribute('name', 'carIds');
                input.setAttribute('value', checkbox.value);
                form.appendChild(input);
            });
            document.body.appendChild(form);
            form.submit();
        }
    });

    // リスト画面の「追加」ボタンが押された時の処理
    const addButton = document.querySelector('.add-button');
    if (addButton) {
        addButton.addEventListener('click', function() {
            window.location.href = ROUTES.SALES_INFO_ADD;
        });
    }
    
    // 追加フォームのボタンアクションを設定
    setupAddFormActions();

    // 編集フォームのボタンアクションを設定
    setupEditFormActions();
});