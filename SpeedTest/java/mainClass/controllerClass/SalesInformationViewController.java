package mainClass.controllerClass;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import com.example.Entity.SalesInfoEntity;

import mainClass.serviceClass.GeneralService;

/**
 * 販売情報管理機能用表示クラス
 */
@Controller
@RequestMapping("/sales-information-menu")
public class SalesInformationViewController extends GeneralDisplay {

	@Autowired
	private GeneralService service; // 汎用サービスをインジェクション
	@Autowired
	private com.example.mapper.selfCreateMapper.SalesInfoMapper SalesInfoMapper;

	/**
	 * 共通リストをモデルに追加
	 * @param model モデル
	 * @throws SQLException SQL例外
	 */
	public void addCommonListsToModel(Model model) throws SQLException {
		List<Map<String, Object>> prefList = service.selectAllFromTable("prefs_list"); // 都道府県リストを取得
		List<Map<String, Object>> branchList = service.selectAllFromTable("branches_list"); // 支店リストを取得
		List<Map<String, Object>> ownerList = service.selectAllFromTable("owners_list"); // オーナーリストを取得
		List<Map<String, Object>> modelList = service.selectAllFromTable("models_list"); // モデルリストを取得
		List<Map<String, Object>> makerList = service.selectAllFromTable("makers_list"); // メーカーリストを取得
		List<Map<String, Object>> typeList = service.selectAllFromTable("types_list"); // タイプリストを取得
		model.addAttribute("prefList", prefList); // モデルに都道府県リストを追加
		model.addAttribute("branchList", branchList); // モデルに支店リストを追加
		model.addAttribute("ownerList", ownerList); // モデルにオーナーリストを追加
		model.addAttribute("modelList", modelList); // モデルにモデルリストを追加
		model.addAttribute("makerList", makerList); // モデルにメーカーリストを追加
		model.addAttribute("typeList", typeList); // モデルにタイプリストを追加
	}

	/**
	 * 販売情報管理画面表示
	 * @param prefCode 都道府県コード
	 * @param branchCode 支店コード
	 * @param makerCode メーカーコード
	 * @param typeCode タイプコード
	 * @param modelName モデル名
	 * @param model モデル
	 * @return 販売情報管理画面
	 * @throws SQLException SQL例外
	 */
	@GetMapping("")
	public String salesInformationMenu(
			@RequestParam(name = "prefCode", required = false) String prefCode, // 都道府県コードを取得
			@RequestParam(name = "branchCode", required = false) String branchCode, // 支店コードを取得
			@RequestParam(name = "makerCode", required = false) String makerCode, // メーカーコードを取得
			@RequestParam(name = "typeCode", required = false) String typeCode, // タイプコードを取得
			@RequestParam(name = "modelName", required = false) String modelName, // モデル名を取得
			Model model) throws SQLException {

		addCommonListsToModel(model); // 共通リストをモデルに追加

		return "salesInformationMenu"; // 販売情報管理画面のビューを返す
	}

	/**
	 * 編集画面表示
	 * @param car_id 車両ID
	 * @param model モデル
	 * @return 編集画面またはエラー画面
	 * @throws SQLException SQL例外
	 */
	@GetMapping("/display-edit")
	public String displayEdit(@RequestParam("car_id") Integer carId, Model model) throws SQLException {
		addCommonListsToModel(model); // 共通リストをモデルに追加

		try {

			// 最初の要素、または唯一の要素を単一のオブジェクトとして渡す
			SalesInfoEntity resultData = SalesInfoMapper.findById(carId); // findByIdが単一オブジェクトを返すように

			model.addAttribute("salesInfoToEdit", resultData);

			return "SalesInfoDisplayEdit"; // 最終的な戻り先のページを返す

		} catch (PersistenceException e) {

			model.addAttribute("resultCountMessage", "車両データを取得できませんでした。"); // 車両IDをモデルに追加

			return "salesInformationMenu";

		}

	}

	/**
	 * 新規追加画面表示
	 * @param model モデル
	 * @return 新規追加画面
	 * @throws SQLException SQL例外
	 */
	@GetMapping("/display-add")
	public String displayAdd(Model model) throws SQLException {
		addCommonListsToModel(model); // 共通リストをモデルに追加
		return "SalesInfoDisplayAdd"; // 新規追加画面のビューを返す
	}
}