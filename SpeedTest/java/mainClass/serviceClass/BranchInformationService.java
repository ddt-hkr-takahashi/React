package mainClass.serviceClass;

import static com.example.mapper.DynamicSqlSupport.BranchesListDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entity.BranchesList;
import com.example.mapper.selfCreateMapper.BranchInfoMapper; // ユーザーの既存のインポートを維;

/**
 * 要求に対して要件に沿ってSQLを生成するクラス(店舗情報)
 */
@Service
public class BranchInformationService extends GeneralService {

	@Autowired
	private BranchInfoMapper BranchInfoMapper;
	@Autowired
	private com.example.mapper.autoCreateMapper.BranchesListMapper BranchesListMapper;

	/**
	 * 絞り込み検索
	 * @param prefCode 県コード
	 * @param storeCode 店舗コード
	 * @return 検索結果のリスト
	 */
	public Map<String, Object> refineSearch(String prefCode, String storeCode) {

		List<Map<String, Object>> resultList = BranchInfoMapper.refineSearch(prefCode, storeCode);

		// SQLで件数を取得しているため、Java側でリストのサイズをカウント
		int totalCount = resultList.size();

		// 結果をマップに格納して返す
		Map<String, Object> result = new HashMap<>();
		result.put("resultList", resultList);
		result.put("totalCount", totalCount);
		return result;
	}

	/**
	 * 指定された支店コードに紐づく単一の店舗情報を取得します。
	 * @param branchCode 取得する支店のコード
	 * @return 支店情報を含むMap。見つからない場合はnull。
	 */
	public Map<String, Object> getBranchByCode(String branchCode) {

		return BranchInfoMapper.getBranchByCode(branchCode); // 最初の結果（単一レコード）を返す
	}

	/**
	 * データの編集
	 * @param prefCode 変更後の県コード
	 * @param storeCode 変更後の店舗コード
	 * @param branchName 変更後の支店名
	 * @param branchCode 編集対象の支店コード
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String editData(String prefCode, String storeCode, String branchName, Integer branchCode) {
		if ((prefCode == null || prefCode.trim().isEmpty()) &&
				(storeCode == null || storeCode.trim().isEmpty()) &&
				(branchName == null || branchName.trim().isEmpty())) {
			return "編集内容が入力されていません。";
		}

		try {
			BranchesList branch = new BranchesList();
			branch.setPrefCode(prefCode);
			branch.setStoreCode(storeCode);
			branch.setBranchName(branchName);
			branch.setBranchCode(branchCode);

			BranchesListMapper.updateByPrimaryKey(branch); // データベース更新を実行
		} catch (PersistenceException e) {
			return "編集に失敗しました : " + e.getMessage();
		}
		return "編集に成功しました";
	}

	/**
	 * データを追加
	 * @param prefCode 追加する県コード
	 * @param storeCode 追加する店舗コード
	 * @param branchName 追加する支店名
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String addData(String prefCode, String storeCode, String branchName) {
		if (branchName == null || branchName.trim().isEmpty()) {
			return "支店名が入力されていません。";
		}
		try {
			BranchesList record = new BranchesList();
			record.setPrefCode(prefCode);
			record.setStoreCode(storeCode);
			record.setBranchName(branchName);

			BranchesListMapper.insert(record);
			return "データ追加に成功しました。";
		} catch (PersistenceException e) {
			return "データ追加に失敗しました。: " + e.getMessage();
		}
	}

	/**
	 * データを削除
	 * @param branchIds 削除対象の支店コードの配列
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String deleteData(int[] branchIds) {
		if (branchIds == null || branchIds.length == 0) {
			return "削除対象が選択されていません。";
		}

		List<Integer> branchIdsList = Arrays.stream(branchIds).boxed().collect(Collectors.toList());

		try {
			BranchesListMapper.delete(c -> c.where(branchCode, isIn(branchIdsList)));
		} catch (PersistenceException e) {
			return "削除に失敗しました : " + e.getMessage();
		}
		return "削除に成功しました。";
	}
}