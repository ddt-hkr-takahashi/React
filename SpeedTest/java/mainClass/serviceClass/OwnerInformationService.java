package mainClass.serviceClass;

import static com.example.mapper.DynamicSqlSupport.OwnersListDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.sql.SQLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entity.OwnersList;
import com.example.mapper.autoCreateMapper.OwnersListMapper;
import com.example.mapper.selfCreateMapper.OwnerInfoMapper;

/**
 * 要求に対して要件に沿ってSQLを生成するクラス(元所有者情報)
 * @author 高橋輝
 */
@Service
public class OwnerInformationService extends GeneralService {

	@Autowired
	private OwnerInfoMapper OwnerInfoMapper;
	@Autowired
	private OwnersListMapper OwnersListMapper;

	/**
	 * 絞り込み検索
	 * @param ownerName 元所有者名
	 * @param sex 性別
	 * @param minAge 年齢（下限）
	 * @param maxAge 年齢（上限）
	 * @return 検索結果のリスト
	 */
	public Map<String, Object> refineSearch(String ownerName, String sex, String minAge, String maxAge) {

		// MyBatisのrefineSearchマッパーを1回だけ呼び出す
		List<Map<String, Object>> resultList = OwnerInfoMapper.refineSearch(
				ownerName, sex, minAge, maxAge);

		// SQLで件数を取得しているため、Java側でリストのサイズをカウント
		int totalCount = resultList.size();

		// 結果をマップに格納して返す
		Map<String, Object> result = new HashMap<>();
		result.put("resultList", resultList);
		result.put("totalCount", totalCount);
		return result;
	}

	/**
	 * 指定された所有者コードに紐づく単一の所有者情報を取得します。
	 * @param ownerCode 取得する所有者のコード
	 * @return 所有者情報を含むMap。見つからない場合はnull。
	 * @throws SQLException
	 */
	public Map<String, Object> getOwnerByCode(String ownerCode) {

		return OwnerInfoMapper.getOwnerByCode(ownerCode); // 最初の結果（単一レコード）を返す
	}

	/**
	 * データを追加
	 * @param ownerName 元所有者名
	 * @param sex 性別
	 * @param age 年齢
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String addData(String ownerName, String sex, String age) {
		String errorMessage = null;
		int parsedAge;

		// 未入力を検知
		if ((ownerName == null || ownerName.trim().isEmpty()) && (age == null || age.trim().isEmpty())) {
			errorMessage = "所有者名、年齢が入力されていません。";
		} else if (ownerName == null || ownerName.trim().isEmpty()) {
			errorMessage = "所有者名が入力されていません。";
		} else if (age == null || age.trim().isEmpty()) {
			errorMessage = "年齢が入力されていません。";
		} else {
			try {
				parsedAge = Integer.parseInt(age);
				if (parsedAge < 0) {
					errorMessage = "年齢は0以上の値を入力してください。";
				} else {
					// INSERT文を構築
					OwnersList record = new OwnersList();
					record.setOwnerName(ownerName);
					record.setSex(sex);
					record.setAge(parsedAge);
					OwnersListMapper.insert(record);

					errorMessage = "データ追加に成功しました。";
				}
			} catch (NumberFormatException e) {
				errorMessage = "データ追加に失敗しました。";
			} catch (PersistenceException e) {
				errorMessage = "データベースエラー: " + e.getMessage();
			}
		}
		return errorMessage;
	}

	/**
	 * データを編集
	 * @param ownerName 元所有者名
	 * @param sex 性別
	 * @param age 年齢
	 * @param ownerCode 識別番号
	 * @return エラーメッセージ（成功時はnull）
	 */
	public String editData(String ownerName, String sex, String age, String ownerCode) {
		String errorMessage = null;
		Integer parsedAge = null;

		// どの項目も空文字でないかチェックする
		if ((ownerName == null || ownerName.trim().isEmpty())
				&& (sex == null || sex.trim().isEmpty())
				&& (age == null || age.trim().isEmpty())) {
			return "編集内容が入力されていません。";
		}

		// ageが入力されている場合、数値に変換し、検証を行う
		if (age != null && !age.trim().isEmpty()) {
			try {
				parsedAge = Integer.parseInt(age);
				//0以下の場合
				if (parsedAge < 0) {
					return "年齢は0以上の値を入力してください。";
				}
			}
			//数字以外の場合
			catch (NumberFormatException e) {
				return "年齢は数値で入力してください。";
			}
		}

		// 既にエラーメッセージが出ていたら処理を行わない
		if (errorMessage == null) {
			// UsedCarsエンティティに編集対象データをセット
			OwnersList Owner = new OwnersList();
			Owner.setOwnerName(ownerName);
			Owner.setSex(sex);
			Owner.setAge(parsedAge);
			Owner.setOwnerCode(Integer.parseInt(ownerCode));

			try {
				OwnersListMapper.updateByPrimaryKey(Owner); // データベース更新を実行
			} catch (PersistenceException e) {
				return "編集に失敗しました : " + e.getMessage();
			}

		}
		return "編集に成功しました";
	}

	/**
	 * データを削除
	 * @param ownerCodes 削除対象の識別番号の配列
	 */
	public String deleteData(int[] ownerCodes) {
		if (ownerCodes == null || ownerCodes.length == 0) {
			return "削除対象が選択されていません。";
		}

		// int配列をList<Integer>に変換
		List<Integer> ownerCodesList = Arrays.stream(ownerCodes).boxed().collect(Collectors.toList());

		try {
			// MyBatis Dynamic SQLのisInを使って一括削除
			OwnersListMapper.delete(c -> c.where(ownerCode, isIn(ownerCodesList)));
		} catch (PersistenceException e) {
			return "削除に失敗しました : " + e.getMessage();
		}

		return "削除に成功しました。";
	}
}