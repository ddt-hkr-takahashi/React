package mainClass.controllerClass;

import java.sql.SQLException;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import mainClass.serviceClass.OwnerInformationService;

@Controller
@RequestMapping("/owner-information-menu")
public class OwnerInformationViewController {

	@Autowired
	OwnerInformationService OIService; // オーナー情報サービスをインジェクション

	/**
	 * 元所有者情報画面表示
	 * @param owner_name 所有者名
	 * @param sex 性別
	 * @param age_from 年齢下限
	 * @param age_to 年齢上限
	 * @return 元所有者情報管理画面
	 */
	@GetMapping("")
	public String ownerInformationMenu(
			@RequestParam(name = "owner_name", required = false) String owner_name, // 所有者名を取得
			@RequestParam(name = "sex", required = false) String sex, // 性別を取得
			@RequestParam(name = "age_from", required = false) Integer age_from, // 年齢下限を取得
			@RequestParam(name = "age_to", required = false) Integer age_to, // 年齢上限を取得
			Model model) {

		return "ownerInformationMenu"; // 管理画面のビューを返す
	}

	/**
	 * 新規追加画面表示
	 * @return 新規追加画面
	 */
	@GetMapping("/display-add")
	public String displayAdd() {
		return "OwnerInformationDisplayAdd"; // 新規追加画面のビューを返す
	}

	/**
	 * 編集画面表示
	 * @param ownerCode 編集対象のオーナーコード
	 * @param model モデル
	 * @return 編集画面またはエラー画面
	 * @throws SQLException SQL例外
	 */
	@GetMapping("/display-edit")
	public String displayEdit(@RequestParam("owner_code") String ownerCode, Model model) throws SQLException {
		String returnPage; // 戻り先のページを格納

		Map<String, Object> ownerToEdit = OIService.getOwnerByCode(ownerCode); // オーナー情報を取得
		if (ownerToEdit == null) { // 取得情報がnullか判定
			model.addAttribute("errorMessage", "指定されたオーナーコードの店舗情報が見つかりませんでした。"); // エラーメッセージを追加
			returnPage = "OwnerInformationMenu"; // メニュー画面を返す
		} else {
			model.addAttribute("ownerToEdit", ownerToEdit); // 取得したデータをモデルに追加
			model.addAttribute("ownerCode", ownerCode); // オーナーコードをモデルに追加
			returnPage = "OwnerInformationDisplayEdit"; // 編集画面を返す
		}
		return returnPage; // 最終的な戻り先のページを返す
	}
}