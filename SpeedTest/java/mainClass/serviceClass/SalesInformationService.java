package mainClass.serviceClass;

import static com.example.mapper.DynamicSqlSupport.UsedCarsDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.apache.ibatis.exceptions.PersistenceException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.Entity.UsedCars;
import com.example.mapper.DynamicSqlSupport.UsedCarsDynamicSqlSupport;
import com.example.mapper.autoCreateMapper.UsedCarsMapper;

/**
 * 要求に対して要件に沿ってSQLを生成するクラス(販売情報)
 */
@Service
public class SalesInformationService extends GeneralService {

	@Autowired
	private com.example.mapper.selfCreateMapper.SalesInfoMapper SalesInfoMapper;

	@Autowired
	private UsedCarsMapper UsedCarsMapper;

	/**
	 * 指定された条件で中古車情報を検索する
	 *
	 * @param prefCode 都道府県コード
	 * @param branchCode 支店コード
	 * @param makerCode メーカーコード
	 * @param typeCode 車種コード
	 * @param modelName モデル名（部分一致）
	 * @param page ページ番号（1始まり）
	 * @param size 1ページあたりの件数
	 * @return 検索結果リストと総件数を含むMap
	 */
	public Map<String, Object> refineSearch(
			String prefCode, String branchCode, String makerCode, String typeCode,
			String modelName, int page, int size) {

		// オフセットを計算（0始まり）
		int offset = (page - 1) * size;

		// 注入されたSalesInfoMapperインスタンスを使ってメソッドを呼び出す
		List<Map<String, Object>> resultList = SalesInfoMapper.searchUsedCars(
				prefCode, branchCode, makerCode, typeCode, modelName, offset, size);

		// 総件数を取得
		int totalCount = SalesInfoMapper.countUsedCars(
				prefCode, branchCode, makerCode, typeCode, modelName);

		// 結果をマップに格納して返す
		Map<String, Object> result = new HashMap<>();
		result.put("resultList", resultList);
		result.put("totalCount", totalCount);
		return result;
	}

	/**
	 * 新しい中古車データを追加する
	 *
	 * @param branchCode 支店コード
	 * @param modelCode モデルコード
	 * @param ownerCode 担当者コード
	 * @param salesAmount 販売額
	 * @return 処理結果メッセージ
	 */
	public String addData(Integer branchCode, String modelCode, Integer ownerCode, Integer salesAmount) {
		// salesAmountがnullまたは0以下かチェックする
		if (salesAmount == null || salesAmount <= 0) {
			return "販売額は0より大きい数値を入力してください。";
		}

		//引数の値をsetする
		UsedCars record = new UsedCars();
		record.setBranchCode(branchCode);
		record.setModelCode(modelCode);
		record.setOwnerCode(ownerCode);
		record.setSalesAmount(salesAmount);
		record.setParchaseDate(null);

		//データを追加する
		try {
			UsedCarsMapper.insert(record);
		} catch (PersistenceException e) {
			return "データ追加に失敗しました。" + e.getMessage();
		}
		return "データ追加に成功しました。";
	}
	

	/**
	 * 指定された中古車を販売済みにする
	 *
	 * @param carIds 販売対象の車両IDの配列
	 * @return 処理結果メッセージ
	 */
	public String salesData(int[] carIds) {
		if (carIds == null || carIds.length == 0) {
			return "販売対象が選択されていません。";
		}

		// int配列をList<Integer>に変換
		List<Integer> carIdList = Arrays.stream(carIds).boxed().collect(Collectors.toList());

		// 更新対象のUsedCarsエンティティを作成
		UsedCars car = new UsedCars();
		// 販売日を現在日付に設定
		car.setParchaseDate(Date.valueOf(LocalDate.now()));

		try {
			// Mybatis Dynamic SQLを使って一括更新
			UsedCarsMapper.update(c -> c.set(UsedCarsDynamicSqlSupport.parchaseDate).equalTo(car::getParchaseDate)
					.where(carId, isIn(carIdList)));
		} catch (PersistenceException e) {
			return "販売に失敗しました : " + e.getMessage();
		}

		return "販売に成功しました。";
	}

	//---

	/**
	 * 指定された中古車データを編集する
	 *
	 * @param carId 編集対象の車両ID
	 * @param branchCode 支店コード
	 * @param modelCode モデルコード
	 * @param ownerCode 担当者コード
	 * @param salesAmount 販売額
	 * @return 処理結果メッセージ
	 */
	public String editData(int carId, String branchCode, String modelCode, String ownerCode, String salesAmount) {
		// 入力バリデーション
		if (salesAmount == null || salesAmount.trim().isEmpty()) {
			return "編集内容が入力されていません。";
		}

		Integer parsedBranchCode = null;
		if (branchCode != null && !branchCode.trim().isEmpty()) {
			try {
				parsedBranchCode = Integer.parseInt(branchCode);
			} catch (NumberFormatException e) {
				return "支店コードは数値で入力してください。";
			}
		}

		Integer parsedOwnerCode = null;
		if (ownerCode != null && !ownerCode.trim().isEmpty()) {
			try {
				parsedOwnerCode = Integer.parseInt(ownerCode);
			} catch (NumberFormatException e) {
				return "担当者コードは数値で入力してください。";
			}
		}

		Integer parsedSalesAmount;
		try {
			parsedSalesAmount = Integer.parseInt(salesAmount);
			if (parsedSalesAmount <= 0)
				return "販売額は0より大きい値を入力してください。";
		} catch (NumberFormatException e) {
			return "販売額は数値で入力してください。";
		}

		// UsedCarsエンティティに編集対象データをセット
		UsedCars car = new UsedCars();
		car.setCarId(carId);
		car.setBranchCode(parsedBranchCode);
		car.setModelCode(modelCode);
		car.setOwnerCode(parsedOwnerCode);
		car.setSalesAmount(parsedSalesAmount);

		try {
			UsedCarsMapper.updateByPrimaryKeySelective(car);
		} catch (PersistenceException e) {
			return "編集に失敗しました : " + e.getMessage();
		}
		return "編集に成功しました";
	}

	/**
	 * 指定された中古車データを削除する
	 *
	 * @param carIds 削除対象の車両IDの配列
	 * @return 処理結果メッセージ
	 */
	public String deleteData(int[] carIds) {
		if (carIds == null || carIds.length == 0) {
			return "削除対象が選択されていません。";
		}

		// int配列をList<Integer>に変換
		List<Integer> carIdList = Arrays.stream(carIds).boxed().collect(Collectors.toList());

		try {
			// MyBatis Dynamic SQLのisInを使って一括削除
			UsedCarsMapper.delete(c -> c.where(carId, isIn(carIdList)));
		} catch (PersistenceException e) {
			return "削除に失敗しました : " + e.getMessage();
		}

		return "削除に成功しました。";
	}
}