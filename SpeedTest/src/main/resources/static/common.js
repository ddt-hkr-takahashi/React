// common.js

// DOM（Document Object Model）の読み込みが完了した後に実行されるイベントリスナーを設定
document.addEventListener('DOMContentLoaded', function() {
	// ページネーションリンク（クラスが 'pagination' の中の a タグ）をすべて取得
	document.querySelectorAll('.pagination a').forEach(link => {
		// ページネーションの各番号にクリックイベントリスナーを追加
		link.addEventListener('click', function(event) {
			event.preventDefault(); // デフォルトのリンク遷移を無効化
			// クリックされた番号の属性からページ番号を取得
			const page = this.getAttribute('data-page');
			// クリックしたページに応じてページネーションのページ番号を更新
			document.getElementById('pageInput').value = page;
			// 一覧検索の機能から送信
			document.getElementById('searchForm').submit();
		});
	});

	// 新しいCSV出力ロジックを追記
	const exportCsvButton = document.getElementById('testExportCsvButton');

	if (exportCsvButton) {
		exportCsvButton.addEventListener('click', async () => {
			try {
				// APIエンドポイントを呼び出す
				const response = await fetch('/sales-history/download-report', {
					method: 'GET',
					headers: {
						'Accept': 'text/csv'
					}
				});

				if (!response.ok) {
					throw new Error(`HTTP error! Status: ${response.status}`);
				}

				// Blobとしてレスポンスボディを取得
				const blob = await response.blob();
				const url = window.URL.createObjectURL(blob);

				// ダウンロードリンクを作成
				const link = document.createElement('a');
				link.href = url;
				link.download = 'sales_report.csv';
				document.body.appendChild(link);
				link.click();

				// 一時的なURLとリンクをクリーンアップ
				window.URL.revokeObjectURL(url);
				link.remove();
				console.log("CSVファイルが正常にダウンロードされました。");

				// ダウンロード成功メッセージを表示
				alert("CSVファイルが正常にダウンロードされました。");

			} catch (error) {
				console.error("CSVダウンロード中にエラーが発生しました:", error);
				// ダウンロード失敗メッセージを表示
				alert("CSVダウンロード中にエラーが発生しました。");
			}
		});
	}
});


/**
 * 汎用的なアクションハンドラー
 * 指定されたボタンのクリックイベントを監視し、チェックボックスの選択状態を検証後、アクションを実行する。
 * メッセージはHTMLのデータ属性から取得するため、この関数は特定の画面に依存しない。
 * @param {string} buttonSelector - イベントリスナーを紐づけるボタンのCSSセレクタ（例: '.edit-button'）。
 * @param {string} checkboxSelector - 処理対象となるチェックボックスのCSSセレクタ（例: 'input[name="car_id"]'）。
 * @param {object} options - アクションを定義するためのオプションオブジェクト。
 * @param {number} [options.min] - 選択が必須なチェックボックスの最小数。
 * @param {number} [options.max] - 選択できるチェックボックスの最大数。
 * @param {function} options.action - 全ての条件を満たした場合に実行するコールバック関数。引数として選択されたチェックボックスのNodeListが渡される。
 */
export function handleAction(buttonSelector, checkboxSelector, options) {
	// 指定されたセレクタに一致する最初のボタン要素を取得
	const button = document.querySelector(buttonSelector);

	// ボタンが存在しない場合は処理を終了
	if (!button) {
		return;
	}

	// ボタンにクリックイベントリスナーを設定
	button.addEventListener('click', function() {
		// 指定されたセレクタに一致し、かつチェックされているすべてのチェックボックスを取得
		const checkedCheckboxes = document.querySelectorAll(checkboxSelector + ':checked');

		// ボタンのデータ属性からメッセージを取得
		const minMessage = button.dataset.minMessage; // 最小選択数未満の場合のメッセージ
		const maxMessage = button.dataset.maxMessage; // 最大選択数を超える場合の場合のメッセージ
		const confirmMessage = button.dataset.confirmMessage; // 実行前の確認メッセージ

		// 最小選択数が設定されており、選択数がそれを下回る場合
		if (options.min && checkedCheckboxes.length < options.min) {
			alert(minMessage); // 警告メッセージを表示
			return; // 処理を中断
		}

		// 最大選択数が設定されており、選択数がそれを上回る場合
		if (options.max && checkedCheckboxes.length > options.max) {
			alert(maxMessage); // 警告メッセージを表示
			return; // 処理を中断
		}

		// 確認メッセージが設定されている場合
		if (confirmMessage) {
			// ユーザーが確認ダイアログで「キャンセル」を選択した場合
			if (!confirm(confirmMessage)) {
				return; // 処理を中断
			}
		}

		// 全ての条件を満たした場合、optionsオブジェクトに定義されたアクションを実行
		options.action(checkedCheckboxes);
	});
}
