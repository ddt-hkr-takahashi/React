package mainClass.controllerClass;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import mainClass.serviceClass.OwnerInformationService;

/**
 * 元所有者情報管理機能用制御クラス
 * @author 高橋輝
 */
@Controller
@RequestMapping("/owner-information-menu") // リクエストパスのベースURLを設定
public class OwnerInformationController extends GeneralControl {

	@Autowired
	private OwnerInformationService OIService; // OwnerInformationServiceのインスタンスを自動でインジェクション

	/**
	 * データ検索機能
	 * @param ownerName 元所有者名
	 * @param sex 性別
	 * @param minAge 年齢（下限）
	 * @param maxAge 年齢（上限）
	 * @param model MVC(モデル)
	 * @return "ownerInformationMenu" 検索結果一覧
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/search-result") // search-resultへのPOSTリクエストを処理
	public String searchFunction(
			@RequestParam("owner_name") String ownerName,
			@RequestParam("sex") String sex,
			@RequestParam(value = "age_from", required = false) String minAge,
			@RequestParam(value = "age_to", required = false) String maxAge,
			Model model) {

		List<Map<String, Object>> resultList;
		String resultCountMessage = null; // 検索結果件数メッセージを初期化

		try {
			// 絞り込み検索を実行
			Map<String, Object> serviceResult = OIService.refineSearch(ownerName, sex, minAge, maxAge);
			resultList = (List<Map<String, Object>>) serviceResult.get("resultList");

			// 検索結果の件数をチェックし、メッセージを設定
			if (resultList != null && !resultList.isEmpty()) {
				resultCountMessage = "検索結果: " + resultList.size() + "件";
			} else {
				resultCountMessage = "検索結果: 0件"; // 0件の場合のメッセージ
			}

			// Thymeleafに検索結果リストとメッセージを渡す
			model.addAttribute("resultList", resultList);
			model.addAttribute("resultCountMessage", resultCountMessage);

		} catch (Exception e) {
			// エラー時は結果リストをnullにし、エラーメッセージを追加
			model.addAttribute("resultList", null);
			model.addAttribute("errorMessage", "検索中にエラーが発生しました。エラー詳細: " + e.getMessage());
			model.addAttribute("resultCountMessage", null); // エラー時は件数メッセージをクリア
		}
		return "ownerInformationMenu";
	}

	/**
	 * データ追加機能
	 * @param ownerName 元所有者名
	 * @param sex 性別
	 * @param age 年齢
	 * @param model MVC(モデル)
	 * @return "OwnerInformationMenu" 実行結果
	 * @throws SQLException 
	 */
	@PostMapping("/add-data")
	public String addFunction(
			@RequestParam("ownerName") String ownerName,
			@RequestParam("sex") String sex,
			@RequestParam("age") String age,
			Model model) throws SQLException {

		// addDataメソッドを呼び出し、返り値（エラーメッセージ）を取得
		String returnMessage = OIService.addData(ownerName, sex, age);

		model.addAttribute("resultCountMessage", returnMessage);

		return "OwnerInformationMenu";
	}

	/**
	 * データ編集機能
	 * @param ownerName 元所有者名
	 * @param sex 性別
	 * @param age 年齢
	 * @param ownerCode 識別番号
	 * @param redirectAttributes リダイレクト時の属性
	 * @return 元所有者情報管理画面へリダイレクト
	 * @throws SQLException 
	 */
	@PostMapping("/edit-data") // edit-dataへのPOSTリクエストを処理
	public String editFunction(
			@RequestParam("ownerName") String ownerName,
			@RequestParam("sex") String sex,
			@RequestParam("age") String age,
			@RequestParam(value = "ownerCode", required = false) String ownerCode,
			RedirectAttributes redirectAttributes) throws SQLException {

		if (ownerCode == null || ownerCode.isEmpty()) {
			redirectAttributes.addFlashAttribute("errorMessage", "編集対象の元所有者コードが不明です。");

		} else {

			// OIServiceに編集処理を呼び出し、エラーメッセージを受け取る
			String returnMessage = OIService.editData(ownerName, sex, age, ownerCode);

			redirectAttributes.addFlashAttribute("resultCountMessage", returnMessage); // 成功メッセージをリダイレクト先に渡す

		}
		return "redirect:/owner-information-menu"; // リダイレクト
	}

	/**
	 * データ削除機能
	 * @param owner_codes 一つ以上の識別番号
	 * @param model MVC(モデル)
	 * @return "OwnerInformationMenu" 実行結果
	 * @throws SQLException 
	 */
	@PostMapping("/delete-data") // delete-dataへのPOSTリクエストを処理
	public String deleteFunction(
			@RequestParam("owner_codes") int[] owner_codes,
			Model model) throws SQLException {

		// データを削除する
		String Message = OIService.deleteData(owner_codes);
		model.addAttribute("resultCountMessage", Message); // 成功メッセージをモデルに追加

		return "OwnerInformationMenu";
	}
}