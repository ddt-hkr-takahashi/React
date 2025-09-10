package com.example.demo.service;

import static com.example.mapper.UsedCarsDynamicSqlSupport.*;
import static org.mybatis.dynamic.sql.SqlBuilder.*;

import java.sql.Date;
import java.time.LocalDate;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.apache.ibatis.exceptions.PersistenceException;
import org.mybatis.dynamic.sql.where.condition.IsIn;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.dto.SearchResult;
import com.example.mapper.SalesInfoMapper;
import com.example.mapper.UsedCarsDynamicSqlSupport;
import com.example.mapper.UsedCarsMapper;

/**
 * 要求に対して要件に沿ってSQLを生成するクラス(販売情報)
 */
@Service
public class SalesInformationService {

	@Autowired
	private SalesInfoMapper SalesInfoMapper;
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
	 * @param size 
	 * @param page 
	 * @return 検索結果リストと総件数を含むMap
	 */
	public SearchResult refineSearch(
			String prefCode, String branchCode, String makerCode, String typeCode,
			String modelName, int page, int size) {

		SearchResult searchResult = new SearchResult();

		// オフセットを計算（0始まり）
		int offset = (page - 1) * size;

		try {
			searchResult.setResultList(SalesInfoMapper.searchUsedCars(
					prefCode, branchCode, makerCode, typeCode, modelName, offset, size));

			int totalCount = SalesInfoMapper.countUsedCars(prefCode, branchCode, makerCode, typeCode, modelName);

			searchResult.setResultCountMessage("全 " + totalCount + " 件中 " + ((page - 1) * size + 1) + " 件から "
					+ Math.min(page * size, totalCount) + " 件までを表示しています。");

		} catch (PersistenceException e) {

			searchResult.setResultList(null);
			searchResult.setResultCountMessage("検索中にエラーが発生しました : " + e.getMessage());

		}

		return searchResult;
	}

	/**
	 * 新しい中古車データを追加する
	 *
	 * @param newCar 追加する中古車データを含むUsedCarsオブジェクト
	 * @return 処理結果メッセージ (成功時は null)
	 */
	public String addData(com.example.model.UsedCars newCar) {

	    try {
	        UsedCarsMapper.insert(newCar);
	        return null; // 成功時は null を返す
	    } catch (PersistenceException e) {
	        // 例外発生時の処理
	        return "データの追加中にエラーが発生しました : " + e.getMessage();
	    }
	}

    /**
     * 指定された中古車を販売済みにする
     *
     * @param carIds 販売対象の車両IDの配列
     * @return 処理結果メッセージ (成功時は null)
     */
    public String salesData(Integer[] carIds) {
        if (carIds == null || carIds.length == 0) {
            return "販売対象が選択されていません。";
        }
    
        // 配列をListに変換
        List<Integer> carIdList = Arrays.asList(carIds);
    
        // 更新対象のUsedCarsエンティティを作成し、販売日を設定
        com.example.model.UsedCars car = new com.example.model.UsedCars();
        car.setParchaseDate(Date.valueOf(LocalDate.now()));
    
        try {
            // MyBatis Dynamic SQLを使って一括更新
            UsedCarsMapper.update(c -> c.set(UsedCarsDynamicSqlSupport.parchaseDate).equalTo(car.getParchaseDate())
                    .where(UsedCarsDynamicSqlSupport.carId, IsIn.of(carIdList)));
            return null;
        } catch (PersistenceException e) {
            return "販売に失敗しました : " + e.getMessage();
        }
    }

    /**
     * 指定された中古車データを編集する
     *
     * @param updateCar 編集対象の車両データ
     * @return 処理結果メッセージ (成功時はnull)
     */
    public String editData(com.example.model.UsedCars updateCar) {
        // salesAmountのバリデーションのみに絞る
        if (updateCar.getSalesAmount() == null || updateCar.getSalesAmount() <= 0) {
            return "販売額は1以上の値を入力してください。"; // nullではなく具体的なメッセージを返す
        }
        
        // updateCarオブジェクトをそのまま利用
        int result = UsedCarsMapper.updateByPrimaryKey(updateCar);
        
        if (result == 1) {
            return null; // 成功
        } else {
            return "更新に失敗しました。";
        }
    }

	/**
	 * 指定された中古車データを削除する
	 *
	 * @param carIds 削除対象の車両IDの配列
	 * @return 処理結果メッセージ
	 */
	public String deleteData(int[] carIds) {

		// int配列をList<Integer>に変換
		List<Integer> carIdList = Arrays.stream(carIds).boxed().collect(Collectors.toList());

		try {
			// MyBatis Dynamic SQLのisInを使って一括削除
			UsedCarsMapper.delete(c -> c.where(carId, isIn(carIdList)));
			return null;
		} catch (PersistenceException e) {

			return "削除に失敗しました : " + e.getMessage();
		}

	}
}