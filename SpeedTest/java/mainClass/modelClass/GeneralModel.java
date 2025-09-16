package mainClass.modelClass;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

@Component // Springがこのクラスを管理するコンポーネントとして認識
public class GeneralModel {

	// 車と支店のキャッシュを保持する静的マップ
	public static Map<String, String> carCacheMap = new HashMap<>();
	public static Map<String, String> branchCacheMap = new HashMap<>();

	// Springによって注入されるDataSource
	private final DataSource dataSource;

	// コンストラクタでDataSourceを注入
	public GeneralModel(DataSource dataSource) {
		this.dataSource = dataSource;
	}

	/**
	 * DataSourceのためのSpring Beanを定義
	 * @return 設定済みのDataSourceインスタンス
	 */
	@Bean
	// @Component/@Configurationと同じクラス内にあるため、staticにする
	public static DataSource dataSource() {
		DriverManagerDataSource dataSource = new DriverManagerDataSource();
		// MariaDBの接続情報を設定
		dataSource.setDriverClassName("org.mariadb.jdbc.Driver");
		dataSource.setUrl("jdbc:mariadb://localhost:3306/used_cars_sales_system");
		dataSource.setUsername("workusr");
		dataSource.setPassword("workpass");
		return dataSource;
	}

	/**
	 * DataSourceからデータベース接続を取得
	 * @return データベース接続
	 * @throws SQLException データベースアクセスエラーが発生した場合
	 */
	public Connection getConnection() throws SQLException {
		// 注入されたdataSourceを使用して接続を取得
		return dataSource.getConnection();
	}

	/**
	 * SQL SELECTクエリを実行し、結果をリスト形式で取得します。
	 * @param sqlQuery 実行するSQL SELECT文。
	 * @return 検索結果の各行を表すMapのリスト。
	 * @throws SQLException SQL実行時にエラーが発生した場合。
	 */
	public List<Map<String, Object>> searchData(String sqlQuery) throws SQLException {
		List<Map<String, Object>> results = new ArrayList<>();
		// try-with-resources を使用し、Connection, Statement, ResultSetを自動でクローズする
		try (Connection connection = getConnection();
				Statement statement = connection.createStatement();
				ResultSet resultSet = statement.executeQuery(sqlQuery)) {
			java.sql.ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			// 結果セットの各行をループ処理
			while (resultSet.next()) {
				Map<String, Object> row = new HashMap<>();
				// 1から始まる列インデックスで結果セットをループ
				for (int i = 1; i <= columnCount; i++) {
					// 列名と対応する値を取得し、Mapに格納
					row.put(metaData.getColumnName(i), resultSet.getObject(i));
				}
				// 処理した行を結果リストに追加
				results.add(row);
			}
		}
		return results;
	}

	/**
	 * SQLのINSERT, UPDATE, DELETE操作を実行し、更新件数を取得します。
	 * @param sqlQuery 実行するSQL文。
	 * @throws SQLException SQL実行時にエラーが発生した場合。
	 */
	public void dataUpdate(String sqlQuery) throws SQLException {
		// try-with-resources を使用し、ConnectionとStatementを自動でクローズする
		try (Connection connection = getConnection();
				Statement statement = connection.createStatement()) {
			// SQL文を実行
			statement.executeUpdate(sqlQuery);
		}
	}

	/**
	 * CSVファイルからデータを読み込み、データベースにバッチでインポートします。
	 *
	 * @param file          インポートするMultipartFile
	 * @param CSVDelimiter CSVの区切り文字
	 * @param batchSize     バッチ処理のサイズ
	 * @param sqlQuery      実行するSQLクエリ
	 * @return 処理結果を示すメッセージ（成功またはエラー詳細）
	 */
	public String importData(MultipartFile file, String CSVDelimiter, int batchSize, String sqlQuery) {
		long startTime = System.currentTimeMillis();

		try (
				// try-with-resources を使用し、リソースの自動クローズを保証
				Connection conn = getConnection();
				PreparedStatement ps = conn.prepareStatement(sqlQuery);
				BufferedReader br = new BufferedReader(new InputStreamReader(file.getInputStream()))) {

			// オートコミットを無効にし、手動でトランザクションを管理
			conn.setAutoCommit(false);

			String line;
			int count = 0;
			br.readLine(); // ヘッダー行を読み飛ばす

			while ((line = br.readLine()) != null) {
				String[] data = line.split(CSVDelimiter);

				// データの取得と変換
				String branchName = data[2];
				String parchaseDate = data[3];
				int salesAmount = Integer.parseInt(data[4]);
				String makerName = data[5];
				String typeName = data[6];
				String modelName = data[7];

				String modelCode = GeneralModel.carCacheMap
						.get(String.format("%s_%s_%s", makerName, typeName, modelName));
				String branchCode = GeneralModel.branchCacheMap.get(branchName);

				// パラメータを設定
				ps.setString(1, branchCode);
				ps.setString(2, modelCode);
				ps.setInt(3, salesAmount);
				ps.setString(4, parchaseDate);

				ps.addBatch();
				count++;

				if (count >= batchSize) {
					ps.executeBatch();
					ps.clearBatch();
					count = 0;
				}
			}

			// 残っているバッチを実行
			if (count > 0) {
				ps.executeBatch();
			}

			conn.commit();

			long endTime = System.currentTimeMillis();
			long duration = endTime - startTime;
			return "インポート処理にかかった時間: " + (duration / 1000.0) + "秒";

		} catch (SQLException | IOException | RuntimeException e) {

			// エラーメッセージを設定して返す
			return "データのインポート中にエラーが発生しました。詳細: " + e.getMessage();

		}
	}

	/**
	 * データベース接続を受け取り、キャッシュを構築
	 * @throws SQLException データベースアクセスエラーが発生した場合
	 */
	public void buildCache() throws SQLException {

		// ■車のキャッシュを構築

		// 既存のキャッシュをクリア
		carCacheMap.clear();
		// 車種情報を取得するためのSQLクエリ
		String sqlQueryCar = "SELECT model_code, maker_name, type_name, model_name FROM models_list ml "
				+ "INNER JOIN makers_list mk ON mk.maker_code = ml.maker_code "
				+ "INNER JOIN types_list tl ON tl.type_code = ml.type_code";
		// try-with-resources文でConnection, Statement, ResultSetを自動的に閉じる
		try (
				// データベース接続を取得
				Connection connection = getConnection();
				// SQL文を実行するためのStatementを作成
				Statement stmt = connection.createStatement();
				// クエリを実行し、結果セットを取得
				ResultSet rs = stmt.executeQuery(sqlQueryCar)) {
			// 結果セットの各行をループ処理
			while (rs.next()) {
				// モデルコードを取得
				String id = rs.getString("model_code");
				// メーカー名を取得
				String maker = rs.getString("maker_name");
				// タイプ名を取得
				String type = rs.getString("type_name");
				// モデル名を取得
				String model = rs.getString("model_name");
				// キャッシュのキーを作成（例: "メーカー名_タイプ名_モデル名"）
				String key = String.format("%s_%s_%s", maker, type, model);
				// キャッシュにキーとIDを追加
				carCacheMap.put(key, id);
			}
		}

		// ■支店のキャッシュを構築

		// 既存のキャッシュをクリア
		branchCacheMap.clear();
		// 支店情報を取得するSQLクエリ
		String sqlQueryBranch = "SELECT branch_code, branch_name FROM branches_list";
		// try-with-resources文でConnection, Statement, ResultSetを自動的に閉じる
		try (
				// データベース接続を取得
				Connection connection = getConnection();
				// SQL文を実行するためのStatementを作成
				Statement stmt = connection.createStatement();
				// クエリを実行し、結果セットを取得
				ResultSet rs = stmt.executeQuery(sqlQueryBranch)) {
			// 結果セットの各行をループ処理
			while (rs.next()) {
				// 支店コードを取得
				String id = rs.getString("branch_code");
				// 支店名を取得
				String branch = rs.getString("branch_name");
				// キャッシュのキーを作成（支店名）
				String key = String.format("%s", branch);
				// キャッシュにキーとIDを追加
				branchCacheMap.put(key, id);
			}
		}
	}
}