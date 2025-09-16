// constants.js

// アプリケーション内のすべてのルーティングURLを定数として定義するオブジェクト
export const ROUTES = {
	
	// 販売情報メニューに関連するルーティング
    SALES_INFO_ADD: '/sales-information-menu/display-add', // 新規車両情報追加フォームの表示URL
    SALES_INFO_EDIT: '/sales-information-menu/display-edit', // 車両情報編集フォームの表示URL
    SALES_INFO_SALES_DATA: '/sales-information-menu/sales-data', // 車両を販売済みにする処理のURL
    SALES_INFO_MENU: '/sales-information-menu', // 販売情報メニューのトップページURL
    SALES_INFO_EDIT_DATA: '/sales-information-menu/edit-data', // 車両情報編集処理の実行URL
    SALES_INFO_DELETE_DATA: '/sales-information-menu/delete-data', // 車両情報削除処理の実行URL
    SALES_INFO_ADD_DATA: '/sales-information-menu/add-data', // 新規車両情報追加処理の実行URL
    SALES_INFO_SEARCH_RESULT: '/sales-information-menu/search-result', // 検索結果表示ページのURL
    
    
	// 販売履歴メニューに関連するルーティング
    SALES_HISTORY_INFO_MENU: '/sales-history-menu', // 販売履歴メニューのトップページURL
    SALES_HISTORY_SEARCH_RESULT: '/sales-history-menu/search-result', // 検索結果表示ページのURL
    SALES_HISTORY_SEARCH_RESULT_GRAPH: '/sales-history-menu/search-result-gragh', // 検索結果をグラフで表示するページのURL
    SALES_HISTORY_EXPORT: '/sales-history-menu/export-data', // 販売履歴データのエクスポート処理のURL
    SALES_HISTORY_IMPORT: '/sales-history-menu/import-data', // 販売履歴データのインポート処理のURL
    
    
	// 所有者情報メニューに関連するルーティング
    OWNER_INFO_ADD: '/owner-information-menu/display-add', // 所有者情報追加フォームの表示URL
    OWNER_INFO_EDIT: '/owner-information-menu/display-edit', // 所有者情報編集フォームの表示URL
    OWNER_INFO_MENU: '/owner-information-menu', // 所有者情報メニューのトップページURL
    OWNER_INFO_SEARCH_RESULT: '/owner-information-menu/search-result', // 検索結果表示ページのURL
    OWNER_INFO_ADD_DATA: '/owner-information-menu/add-data', // 所有者情報追加処理の実行URL
    OWNER_INFO_EDIT_DATA: '/owner-information-menu/edit-data', // 所有者情報編集処理の実行URL
    OWNER_INFO_DELETE_DATA: '/owner-information-menu/delete-data', // 所有者情報削除処理の実行URL
    
    
	// 支店情報メニューに関連するルーティング
    BRANCH_INFO_ADD:'/branch-information-menu/display-add', // 支店情報追加フォームの表示URL
    BRANCH_INFO_EDIT:'/branch-information-menu/display-edit', // 支店情報編集フォームの表示URL
    BRANCH_INFO_MENU: '/branch-information-menu', // 支店情報メニューのトップページURL
    BRANCH_INFO_SEARCH_RESULT: '/branch-information-menu/search-result', // 検索結果表示ページのURL
    BRANCH_INFO_ADD_DATA: '/branch-information-menu/add-data', // 支店情報追加処理の実行URL
    BRANCH_INFO_EDIT_DATA: '/branch-information-menu/edit-data', // 支店情報編集処理の実行URL
    BRANCH_INFO_DELETE_DATA: '/branch-information-menu/delete-data' // 支店情報削除処理の実行URL
};