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

import mainClass.serviceClass.SalesInformationService;

/**
 * 販売情報管理機能用制御クラス
 * @author 高橋輝
 */
@Controller
@RequestMapping("/sales-information-menu") // リクエストパスのベースURLを設定
public class SalesInformationController {

	@Autowired
	private SalesInformationService SIService; // SalesInformationServiceのインスタンスを自動でインジェクション
	@Autowired
	private SalesInformationViewController SIcontroller; // SalesInformationViewControllerのインスタンスを自動でインジェクション

	/**
	 * データ検索機能
	 * @param prefCode 県コード
	 * @param branchCode 支店コード
	 * @param makerCode メーカーコード
	 * @param typeCode タイプコード
	 * @param modelName モデル名
	 * @param page ページ番号
	 * @param size 1ページあたりの表示件数
	 * @param model MVC(モデル)
	 * @return "salesInformationMenu" 完全なHTMLページ
	 * @throws SQLException
	 */
	@SuppressWarnings("unchecked")
	@PostMapping("/search-result") // search-resultへのPOSTリクエストを処理
	public String searchFunction(
			@RequestParam(value = "prefCode", required = false) String prefCode,
			@RequestParam(value = "branchCode", required = false) String branchCode,
			@RequestParam(value = "makerCode", required = false) String makerCode,
			@RequestParam(value = "typeCode", required = false) String typeCode,
			@RequestParam(value = "modelName", required = false) String modelName,
			@RequestParam(value = "page", defaultValue = "1") int page,
			@RequestParam(value = "size", defaultValue = "100") int size,
			Model model) throws SQLException {

		SIcontroller.addCommonListsToModel(model); // 共通リストをモデルに追加

		List<Map<String, Object>> resultList;
		String resultCountMessage = null; // 検索結果件数メッセージを初期化

		try {
			// 絞り込み検索を実行
			Map<String, Object> serviceResult = SIService.refineSearch(prefCode, branchCode, makerCode, typeCode,
					modelName, page, size);
			resultList = (List<Map<String, Object>>) serviceResult.get("resultList");
			int totalCount = (int) serviceResult.get("totalCount");

			// 総ページ数を計算
			int totalPages = (int) Math.ceil((double) totalCount / size);

			// 検索結果の件数をチェックし、メッセージを設定
			if (resultList != null && !resultList.isEmpty()) {
				// 検索結果件数メッセージを生成
				resultCountMessage = "全 " + totalCount + " 件中 " + ((page - 1) * size + 1) + " 件から "
						+ Math.min(page * size, totalCount) + " 件までを表示しています。";
			} else {
				resultCountMessage = "検索結果: 0件"; // 0件の場合のメッセージ
			}

			// Thymeleafに検索結果リストとメッセージを渡す
			model.addAttribute("resultList", resultList);
			model.addAttribute("resultCountMessage", resultCountMessage);
			model.addAttribute("currentPage", page);
			model.addAttribute("totalPages", totalPages);

			// 検索条件をモデルに追加してページネーションリンクで再利用できるようにする
			model.addAttribute("prefCode", prefCode);
			model.addAttribute("branchCode", branchCode);
			model.addAttribute("makerCode", makerCode);
			model.addAttribute("typeCode", typeCode);
			model.addAttribute("modelName", modelName);

		} catch (Exception e) {
			// エラー時は結果リストをnullにし、エラーメッセージを追加
			model.addAttribute("resultList", null);
			model.addAttribute("errorMessage", "検索中にエラーが発生しました。エラー詳細: " + e.getMessage());
			model.addAttribute("resultCountMessage", null); // エラー時は件数メッセージをクリア

		}

		return "salesInformationMenu";

	}

	/**
	 * データ追加機能
	 * @param branchCode 支店コード
	 * @param modelCode モデルコード
	 * @param ownerCode 元所有者コード
	 * @param salesAmount 販売額
	 * @param model MVC(モデル)
	 * @return "salesInformationMenu" 実行結果
	 * @throws SQLException
	 */
	@PostMapping("/add-data")
	public String addFunction(
			@RequestParam("branchCode") Integer branchCode,
			@RequestParam("modelCode") String modelCode,
			@RequestParam("ownerCode") Integer ownerCode,
			@RequestParam("salesAmount") Integer salesAmount,
			Model model) throws SQLException {

		//検索条件をThymeleafに渡す
		SIcontroller.addCommonListsToModel(model);

		//追加結果を文字列で受け取る
		String returnMessage = SIService.addData(branchCode, modelCode, ownerCode, salesAmount);

		model.addAttribute("resultCountMessage", returnMessage);

		return "salesInformationMenu";
	}

	/**
	 * データ購入機能
	 * @param carIds 一つ以上の識別番号
	 * @param model MVC(モデル)
	 * @return "salesInformationMenu" 実行結果
	 * @throws SQLException
	 */
	@PostMapping("/sales-data") // sales-dataへのPOSTリクエストを処理
	public String salesFunction(
			@RequestParam("carIds") int[] carIds,
			Model model) throws SQLException {

		SIcontroller.addCommonListsToModel(model); // 共通リストをモデルに追加

		// データ購入処理を実行
		String returnMessage = SIService.salesData(carIds);
		model.addAttribute("resultCountMessage", returnMessage); // 成功メッセージをモデルに追加

		return "salesInformationMenu";
	}

	/**
	 * データ編集機能
	 * @param carId 車両ID
	 * @param branchCode 支店コード
	 * @param modelCode モデルコード
	 * @param ownerCode 元所有者コード
	 * @param salesAmount 販売額
	 * @param redirectAttributes リダイレクト時の属性
	 * @return 販売情報管理画面へリダイレクト
	 * @throws SQLException
	 */
	@PostMapping("/edit-data")
	public String editFunction(
			@RequestParam("car_id") int carId,
			@RequestParam(value = "branchCode", required = false) String branchCode,
			@RequestParam("modelCode") String modelCode,
			@RequestParam("ownerCode") String ownerCode,
			@RequestParam("salesAmount") String salesAmount,
			RedirectAttributes redirectAttributes) throws SQLException {

		String errorMessage = null;

		// Serviceに編集処理を呼び出し、エラーメッセージを受け取る
		errorMessage = SIService.editData(carId, branchCode, modelCode, ownerCode, salesAmount);

		if (errorMessage == null) {
			redirectAttributes.addFlashAttribute("resultCountMessage", "販売情報を更新しました。");
		} else {
			redirectAttributes.addFlashAttribute("errorMessage", errorMessage);
		}

		return "redirect:/sales-information-menu"; // リダイレクト
	}

	/**
	 * データ削除機能
	 * @param carIds 一つ以上の識別番号
	 * @param model MVC(モデル)
	 * @return "salesInformationMenu" 実行結果
	 * @throws SQLException
	 */
	@PostMapping("/delete-data") // delete-dataへのPOSTリクエストを処理
	public String deleteFunction(
			@RequestParam("carIds") int[] carIds,
			Model model) throws SQLException {

		SIcontroller.addCommonListsToModel(model); // 共通リストをモデルに追加

		// データを削除する
		String errorMessage = SIService.deleteData(carIds);
		model.addAttribute("resultCountMessage", errorMessage); // 成功メッセージをモデルに追加

		return "salesInformationMenu";
	}
}