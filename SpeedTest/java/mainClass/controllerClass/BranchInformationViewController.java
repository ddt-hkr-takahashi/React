package mainClass.controllerClass;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mainClass.serviceClass.BranchInformationService;
import mainClass.serviceClass.GeneralService;

/**
 * 店舗情報管理機能用制御クラス
 * @author 高橋輝
 */
@Controller
@RequestMapping("/branch-information-menu") // リクエストパスのベースURLを設定
public class BranchInformationViewController {

	// BranchInformationServiceのインスタンスを自動でインジェクション
	@Autowired
	private BranchInformationService BIService;
	@Autowired
	private GeneralService service; // GeneralServiceのインスタンスを自動でインジェクション

	/**
	 * 各種共通リストをデータベースから取得し、Modelに追加
	 * @param model リストを追加するSpringのModelオブジェクト
	 * @throws SQLException
	 */
	public void addCommonListsToModel(Model model) throws SQLException {
		// データベースから各種リストを取得
		List<Map<String, Object>> prefList = service.selectAllFromTable("prefs_list");
		List<Map<String, Object>> storeList = service.selectAllFromTable("stores_list");

		// 取得したリストをModelに追加し、ビューから参照できるようにする
		model.addAttribute("prefList", prefList);
		model.addAttribute("storeList", storeList);
	}

	/**
	 * 店舗情報管理画面表示
	 * @return 店舗情報管理画面
	 * @author 高橋輝
	 * @throws SQLException 
	 */
	@GetMapping("") // branch-information-menuへのGETリクエストを処理
	public String branchInformationMenu(Model model) throws SQLException {

		// 共通リストをモデルに追加
		addCommonListsToModel(model);

		return "branchInformationMenu"; // 店舗情報管理画面を返す
	}

	/**
	 * 編集画面表示
	 * @return 編集画面
	 * @author 高橋輝
	 * @throws SQLException 
	 */
	@GetMapping("/display-edit") // このメソッドがdisplay-editへのGETリクエストを処理
	public String displayEdit(@RequestParam("branchCode") String branchCode, Model model) throws SQLException {

		// 共通リストをモデルに追加
		addCommonListsToModel(model);
		String returnPage; // 戻り先のページを格納

		// データベースから支店コードに紐づく支店情報を取得
		Map<String, Object> branchToEdit = BIService.getBranchByCode(branchCode);

		// 該当する支店が見つからない場合
		if (branchToEdit == null) {
			// エラーメッセージをモデルに追加
			model.addAttribute("errorMessage", "指定された支店コードの店舗情報が見つかりませんでした。");
			// 店舗情報メニュー画面に戻る
			returnPage = "branchInformationMenu";
		} else {

			// 取得した支店情報をモデルに追加
			model.addAttribute("branchToEdit", branchToEdit);
			// 支店コードをモデルに追加
			model.addAttribute("branchCode", branchCode);

			// 編集画面を返す
			returnPage = "BranchInfoDisplayEdit";
		}
		return returnPage;
	}

	/**
	 * 追加画面表示
	 * @return 追加画面
	 * @author 高橋輝
	 * @throws SQLException 
	 */
	@GetMapping("/display-add") // このメソッドがdisplay-addへのGETリクエストを処理
	public String displayAdd(Model model) throws SQLException {

		addCommonListsToModel(model);
		// 追加画面を返す
		return "BranchInfoDisplayAdd";
	}
}