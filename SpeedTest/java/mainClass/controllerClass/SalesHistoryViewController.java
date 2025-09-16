package mainClass.controllerClass;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 
 * 販売履歴管理機能用制御クラス
 * 
 * @author 高橋輝
 */
@Controller
@RequestMapping("/sales-history-menu")
public class SalesHistoryViewController extends GeneralDisplay {
	
	/**
	 * 販売履歴管理画面表示
	 * @return 販売履歴管理画面
	 * @author 高橋輝
	 */
	@GetMapping("") // sales-history-menuへのGETリクエストを処理
	public String salesHistoryMenu() {
		return "salesHistoryListMenu"; // 販売履歴管理画面を返す
	}

}