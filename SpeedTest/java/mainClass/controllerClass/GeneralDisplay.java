package mainClass.controllerClass;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * 全般の表示機能用クラス
 *
 * @author 高橋輝
 */
@Controller // HTMLテンプレートを返すためのコントローラー
public class GeneralDisplay {

	/**
	 * メイン画面表示
	 * @return メイン画面
	 * @author 高橋輝
	 */
	@GetMapping("/main-menu") // main-menuへのGETリクエストを処理
	public String MainMenu() {
		return "mainMenu.html"; // メイン画面を返す
	}
}