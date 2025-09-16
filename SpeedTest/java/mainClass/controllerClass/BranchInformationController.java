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

import mainClass.serviceClass.BranchInformationService;

/**
 * 店舗情報管理機能用制御クラス
 * @author 高橋輝
 */
@Controller
@RequestMapping("/branch-information-menu") // リクエストパスのベースURLを設定
public class BranchInformationController extends GeneralControl {

	@Autowired
	private BranchInformationService BIService; // BranchInformationServiceのインスタンスを自動でインジェクション
	@Autowired
	private BranchInformationViewController BIcontroller;

	/**
	 * データ検索機能
	 * @param prefCode 県コード
	 * @param storeCode 店舗コード
	 * @param model MVC(モデル)
	 * @return "branchInformationMenu" 検索結果一覧
	 * @throws SQLException 
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/search-result") // search-resultへのPOSTリクエストを処理
	public String searchFunction(
			@RequestParam("prefCode") String prefCode,
			@RequestParam("storeCode") String storeCode,
			Model model) throws SQLException {

		BIcontroller.addCommonListsToModel(model); // 共通リストをモデルに追加

		//検索条件をモデルに追加
		model.addAttribute("prefCode", prefCode);
		model.addAttribute("storeCode", storeCode);

		List<Map<String, Object>> resultList;
		String resultCountMessage = null; // 検索結果件数メッセージを初期化

		try {
			
			// 絞り込み検索を実行
			Map<String, Object> serviceResult = BIService.refineSearch(prefCode, storeCode);
			
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
		return "branchInformationMenu";
	}

	/**
	 * データ追加機能
	 * @param prefCode 県コード
	 * @param storeCode 店舗コード
	 * @param branchName 支店名
	 * @param model MVC(モデル)
	 * @return "branchInformationMenu" 実行結果
	 * @throws SQLException
	 */
	@PostMapping("/add-data")
	public String addFunction(
			@RequestParam("prefCode") String prefCode,
			@RequestParam("storeCode") String storeCode,
			@RequestParam("branchName") String branchName,
			Model model) throws SQLException {

		BIcontroller.addCommonListsToModel(model);

		// addDataメソッドを呼び出し、返り値（エラーメッセージ）を取得
		String errorMessage = BIService.addData(prefCode, storeCode, branchName);

		if (errorMessage == null) {
			model.addAttribute("resultCountMessage", "追加に成功しました。");
		} else {
			model.addAttribute("resultCountMessage", "追加に失敗しました。詳細: " + errorMessage);
		}
		return "branchInformationMenu";
	}

	/**
	 * データ編集機能
	 * @param prefCode 都道府県コード
	 * @param storeCode 店舗コード
	 * @param branchName 支店名
	 * @param branchCode 編集対象の支店コード
	 * @param redirectAttributes リダイレクト時の属性
	 * @return 店舗情報管理画面へリダイレクト
	 * @throws SQLException 
	 */
	@PostMapping("/edit-data")
	public String editData(
			@RequestParam("prefCode") String prefCode,
			@RequestParam("storeCode") String storeCode,
			@RequestParam("branchName") String branchName,
			@RequestParam(value = "branchCode", required = false) Integer branchCode,
			RedirectAttributes redirectAttributes) throws SQLException {

		if (branchCode == null || branchCode <= 0) {
			redirectAttributes.addFlashAttribute("errorMessage", "編集対象の支店コードが不明です。");

		} else {

			String errorMessage = BIService.editData(prefCode, storeCode, branchName, branchCode); // BIServiceに編集処理を呼び出し、エラーメッセージを受け取る

			if (errorMessage == null) {
				redirectAttributes.addFlashAttribute("resultCountMessage", "店舗情報を更新しました。");
			} else {
				redirectAttributes.addFlashAttribute("errorMessage", "店舗情報の更新に失敗しました。詳細: " + errorMessage);
			}
		}
		return "redirect:/branch-information-menu";
	}

	/**
	 * データ削除機能
	 * @param branchIds 一つ以上の識別番号
	 * @param model MVC(モデル)
	 * @return "branchInformationMenu" 実行結果
	 * @throws SQLException 
	 */
	@PostMapping("/delete-data") // delete-dataへのPOSTリクエストを処理
	public String deleteFunction(
			@RequestParam("branch_codes") int[] branchIds,
			Model model) throws SQLException {

		BIcontroller.addCommonListsToModel(model); // 共通リストをモデルに追加

		try {
			// データを削除する
			String errorMassege = BIService.deleteData(branchIds);
			model.addAttribute("resultCountMessage", errorMassege); // 成功メッセージをモデルに追加
		} catch (Exception e) {
			model.addAttribute("resultCountMessage", "削除に失敗しました。" + e.getMessage()); // 失敗メッセージをモデルに追加
		}
		return "branchInformationMenu";
	}
}