package mainClass.serviceClass;

import java.sql.SQLException;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import mainClass.modelClass.GeneralModel;

@Primary
@Service
public class GeneralService {

	@Autowired
	private GeneralModel Model;

	/**
	 * 指定されたテーブルからすべての列のデータを取得します。
	 *
	 * @param tableName データを取得するテーブルの名前
	 * @return テーブルの全行・全列のデータを格納したMapのリスト
	 * @throws SQLException 
	 */
	public List<Map<String, Object>> selectAllFromTable(String tableName) throws SQLException {

		String sqlQuery;

		if (tableName.equals("models_list")) {
			sqlQuery = "SELECT mol.model_name, mol.model_code, CONCAT(ml.maker_name, '-', tl.type_name, '-', mol.model_name) AS combined_name FROM models_list mol INNER JOIN makers_list ml ON ml.maker_code = mol.maker_code INNER JOIN types_list tl ON tl.type_code = mol.type_code ";

		} else {
			// SQLクエリの構築
			sqlQuery = "SELECT * FROM " + tableName;
		}
		// クエリを実行し、結果をList<Map<String, Object>>形式で返す
		return Model.searchData(sqlQuery);
	}

}
