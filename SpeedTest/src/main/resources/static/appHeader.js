// DOMContentLoaded: このイベントは、ブラウザがHTMLドキュメントの読み込みとDOMツリーの構築を完了した時点で発生する。
// 画像やCSSなどのリソースの読み込み完了を待たずに実行されるため、素早く初期化処理を行える。
document.addEventListener('DOMContentLoaded', function() {
    
    // ヘッダーの読み込み
    // fetch APIを使用して、非同期でサーバーから'appHeader.html'の内容を取得する。
    fetch('/appHeader.html')
        .then(response => {
            // ステータスコードが200番台（成功）でない場合にエラーを投げる
            if (!response.ok) {
                // 例: 404 Not Foundの場合、ここでエラーがスローされる
                throw new Error('HTTP error! status: ' + response.status);
            }
            // 正常なレスポンスの場合、内容をテキスト形式で取得
            return response.text();
        })
        .then(html => {
            // 取得したHTML文字列を、IDが'header-placeholder'の要素の内部に挿入
            // これにより、ヘッダーのコンテンツが動的にページに表示される
            document.getElementById('header-placeholder').innerHTML = html;
            
            // ナビゲーションリンクのアクティブ状態を設定
            // 挿入されたヘッダー内の全てのナビゲーションリンク（<a>タグ）を取得
            const navLinks = document.querySelectorAll('.main-nav a');
            
            // 取得した各リンクに対して処理を実行
            navLinks.forEach(link => {
                // 'active'クラスを追加する。このクラスは、CSSでアクティブなリンクをハイライトするために使用される
                link.classList.add('active');
            });
            // 備考: 実際には、現在のページのURLとリンクのhrefを比較し、一致した場合のみ'active'クラスを付与するロジックの方がより適切です。
        })
        .catch(error => {
            // fetch処理中にエラーが発生した場合（例: ネットワークの問題、上記でスローされたHTTPエラーなど）
            // エラーをコンソールに出力し、デバッグを助ける
            console.error('ヘッダーの読み込みに失敗しました:', error);
        });
});