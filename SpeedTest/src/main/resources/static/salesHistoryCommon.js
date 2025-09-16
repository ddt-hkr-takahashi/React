// salesHistoryCommon.js

import { ROUTES } from './constants.js'; // ルーティング定数をインポート

let myChartInstance = null; // グラフインスタンスを保持するグローバル変数。これにより、グラフの再描画時に古いインスタンスを破棄できる。

// HTMLドキュメントの読み込みが完了した後に実行
document.addEventListener('DOMContentLoaded', function() {
    const importButton = document.getElementById('importCsvButton'); // CSVインポートボタンを取得
    if (importButton) {
        importButton.addEventListener('click', function(event) { // クリックイベントリスナーを設定
            event.preventDefault(); // デフォルトのフォーム送信をキャンセル
            document.getElementById('fileInput').click(); // 非表示のファイル入力要素をクリックしてダイアログを開く
        });
    }

    const fileInput = document.getElementById('fileInput'); // ファイル入力要素を取得
    if (fileInput) {
        fileInput.addEventListener('change', function() { // ファイルが選択されたら実行
            const importForm = document.getElementById('importForm'); // インポートフォームを取得
            importForm.action = ROUTES.SALES_HISTORY_IMPORT; // フォームの送信先URLを設定
            importForm.submit(); // フォームを送信
        });
    }
    
    const listSearchButton = document.getElementById('listSearchButton'); // 一覧検索ボタンを取得
    if (listSearchButton) {
        listSearchButton.addEventListener('click', function() { // クリックイベントリスナーを設定
            const pageInput = document.getElementById('pageInput'); // ページ番号入力要素を取得
            if (pageInput) {
                pageInput.value = 1; // ページを1に設定
            }
            const form = document.getElementById('searchForm'); // 検索フォームを取得
            form.action = ROUTES.SALES_HISTORY_SEARCH_RESULT; // フォームの送信先URLを設定
            form.submit(); // フォームを送信
        });
    }
    
    const pagination = document.querySelector('.pagination'); // ページネーション要素を取得
    if (pagination) {
        pagination.addEventListener('click', function(event) { // クリックイベントリスナーを設定
            const target = event.target;
            if (target.tagName === 'A') { // クリックされたのがaタグ（リンク）か確認
                event.preventDefault(); // リンクのデフォルト動作をキャンセル
                const href = target.getAttribute('href'); // href属性を取得
                if (href) {
                    const form = document.getElementById('searchForm'); // 検索フォームを取得
                    const pageInput = document.getElementById('pageInput'); // ページ番号入力要素を取得
                    
                    const url = new URL(href, window.location.origin); // URLオブジェクトを作成
                    const page = url.searchParams.get('page'); // pageパラメータを取得

                    if (pageInput && page) {
                        pageInput.value = page; // ページ番号を設定
                        form.action = ROUTES.SALES_HISTORY_SEARCH_RESULT; // フォームの送信先URLを設定
                        form.submit(); // フォームを送信
                    }
                }
            }
        });
    }

    const exportCsvButton = document.getElementById('exportCsvButton'); // CSVエクスポートボタンを取得
    if (exportCsvButton) {
        exportCsvButton.addEventListener('click', function(event) { // クリックイベントリスナーを設定
            event.preventDefault(); // デフォルトのフォーム送信をキャンセル
            
            // フォームの入力値を取得
            const storeCode = document.getElementById('storeCode').value;
const year = document.getElementById('yearNumber').value;
const month = document.getElementById('monthNumber').value;

            // 動的に新しいフォームを作成
            const form = document.createElement('form');
            form.setAttribute('method', 'post');
            form.setAttribute('action', ROUTES.SALES_HISTORY_EXPORT);
            
            // 隠し入力フィールドに値を設定してフォームに追加
            const storeCodeInput = document.createElement('input');
            storeCodeInput.setAttribute('type', 'hidden');
            storeCodeInput.setAttribute('name', 'storeCode');
            storeCodeInput.setAttribute('value', storeCode);
            form.appendChild(storeCodeInput);

            const yearInput = document.createElement('input');
            yearInput.setAttribute('type', 'hidden');
            yearInput.setAttribute('name', 'YEAR');
            yearInput.setAttribute('value', year);
            form.appendChild(yearInput);

            const monthInput = document.createElement('input');
            monthInput.setAttribute('type', 'hidden');
            monthInput.setAttribute('name', 'MONTH');
            monthInput.setAttribute('value', month);
            form.appendChild(monthInput);

            document.body.appendChild(form); // 作成したフォームを<body>に追加
            form.submit(); // フォームを送信してダウンロードをトリガー
        });
    }
    
    const chartSearchButton = document.getElementById('chartSearchButton'); // グラフ表示ボタンを取得
    if (chartSearchButton) {
        chartSearchButton.addEventListener('click', function(event) { // クリックイベントリスナーを設定
            event.preventDefault(); // デフォルトのフォーム送信をキャンセル

            const form = document.getElementById('searchForm'); // 検索フォームを取得
            const formData = new FormData(form); // フォームデータを取得

            fetch(ROUTES.SALES_HISTORY_SEARCH_RESULT_GRAPH, { // APIエンドポイントにデータを送信
                method: 'POST',
                body: new URLSearchParams(formData)
            })
            .then(response => {
                if (!response.ok) { // レスポンスが正常かチェック
                    throw new Error('Network response was not ok');
                }
                return response.json(); // レスポンスをJSONとしてパース
            })
            .then(data => {
                // 既存の要素を非表示にする
                const resultCountMessage = document.querySelector('.result-count-message');
                if (resultCountMessage) resultCountMessage.style.display = 'none';
                const errorMessage = document.querySelector('.error-message');
                if (errorMessage) errorMessage.style.display = 'none';

                const dataTable = document.getElementById('data-table');
                if (dataTable) {
                    dataTable.style.display = 'none';
                }
                const pagination = document.querySelector('.pagination');
                if (pagination) {
                    pagination.style.display = 'none';
                }

                document.getElementById('graph-area').style.display = 'block'; // グラフ表示エリアを表示
                drawChart(data); // グラフを描画
            })
            .catch(error => {
                console.error('グラフデータの取得中にエラーが発生しました:', error); // エラーをコンソールに出力
                alert('グラフデータの取得に失敗しました。'); // ユーザーにアラートで通知
            });
        });
    }

    const backToListButton = document.getElementById('backToListButton'); // 一覧に戻るボタンを取得
    if (backToListButton) {
        backToListButton.addEventListener('click', function() { // クリックイベントリスナーを設定
            document.getElementById('graph-area').style.display = 'none'; // グラフ表示エリアを非表示にする

            // テーブルとページネーションを再表示
            const dataTable = document.getElementById('data-table');
            if (dataTable) {
                dataTable.style.display = 'table';
            }
            const pagination = document.querySelector('.pagination');
            if (pagination) {
                pagination.style.display = 'flex';
            }
            
            const form = document.getElementById('searchForm'); // 検索フォームを取得
            form.action = ROUTES.SALES_HISTORY_SEARCH_RESULT; // 送信先を検索結果に戻す
            form.submit(); // フォームを送信
        });
    }
    
    function drawChart(data) {
        // 新しいグラフを描画する前に、既存のインスタンスを破棄
        if (myChartInstance) {
            myChartInstance.destroy();
        }

        // グラフを描画するcanvas要素を取得
        const ctx = document.getElementById('salesChart').getContext('2d');
        const labels = data.labels; // データからラベルを取得
        const salesAmounts = data.salesAmounts; // データから販売額を取得
        const salesNumbers = data.salesNumbers; // データから販売台数を取得

        myChartInstance = new Chart(ctx, { // 新しいChartインスタンスを作成
            type: 'bar', // グラフの種類を棒グラフに設定
            data: {
                labels: labels,
                datasets: [{
                    label: '販売額',
                    data: salesAmounts,
                    backgroundColor: 'rgba(54, 162, 235, 0.5)',
                    borderColor: 'rgba(54, 162, 235, 1)',
                    borderWidth: 1,
                    yAxisID: 'y' // Y軸を左に設定
                }, {
                    label: '台数',
                    data: salesNumbers,
                    backgroundColor: 'rgba(255, 159, 64, 0.5)',
                    borderColor: 'rgba(255, 159, 64, 1)',
                    borderWidth: 1,
                    yAxisID: 'y1' // Y軸を右に設定
                }]
            },
            options: {
                responsive: true, // レスポンシブを有効にする
                scales: {
                    y: { // 左側のY軸の設定
                        type: 'linear',
                        display: true,
                        position: 'left',
                        title: {
                            display: true,
                            text: '販売額'
                        }
                    },
                    y1: { // 右側のY軸の設定
                        type: 'linear',
                        display: true,
                        position: 'right',
                        title: {
                            display: true,
                            text: '台数'
                        }
                    }
                }
            }
        });
    }

    window.changePage = function(page) { // ページネーションリンクのクリック時に呼び出されるグローバル関数
        const pageInput = document.getElementById('pageInput');
        if (pageInput) {
            pageInput.value = page; // ページ番号を設定
            const form = document.getElementById('searchForm'); // 検索フォームを取得
            form.action = ROUTES.SALES_HISTORY_SEARCH_RESULT; // 送信先を設定
            form.submit(); // フォームを送信
        }
    }
});