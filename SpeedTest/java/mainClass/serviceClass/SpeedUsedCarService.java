package mainClass.serviceClass;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.Writer;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class SpeedUsedCarService {

	// 設定ファイルからデータベースの接続情報を自動で取得
	@Value("${spring.datasource.url}")
	private String dbUrl; // データベースの場所

	@Value("${spring.datasource.username}")
	private String dbUsername; // データベースにアクセスする時のユーザー名

	@Value("${spring.datasource.password}")
	private String dbPassword; // ユーザーのパスワード

	// このメソッドはデータを読み込むだけなので、処理を速くする設定
	@Transactional(readOnly = true)
	public void streamCsvReport(Writer writer) throws IOException {

		// データベースから情報を取得するための命令文（SQL）
		String sqlQuery = "SELECT "
				+ "p.pref_Name AS prefName, "
				+ "s.store_name AS storeName, "
				+ "b.branch_name AS branchName, "
				+ "DATE_FORMAT(u.parchase_date, '%Y/%m/%d') AS PD, "
				+ "u.sales_amount AS salesAmount, "
				+ "m.list_price AS listPrice, "
				+ "mk.maker_name AS makerName, "
				+ "t.type_name AS typeName, "
				+ "m.model_name AS modelName, "
				// 販売額と定価を比べて、販売状況を分類する
				+ "CASE "
				+ "WHEN u.sales_amount >= m.list_price THEN 1 " // 販売額が定価以上なら1
				+ "WHEN b.store_code = '01' AND u.sales_amount < m.list_price AND u.sales_amount >= m.list_price * 0.8 THEN 0 " // 支店コード01で販売額が定価の80%以上なら0
				+ "WHEN b.store_code != '01' AND u.sales_amount < m.list_price AND u.sales_amount >= m.list_price * 0.7 THEN 0 " // 支店コード01以外で販売額が定価の70%以上なら0
				+ "ELSE -1 " // それ以外なら-1
				+ "END AS saleStatus " // この結果をsaleStatusという名前にする
				+ "FROM "
				+ "used_cars u "
				+ "JOIN models_list m ON u.model_code = m.model_code "
				+ "JOIN branches_list b ON u.branch_code = b.branch_code "
				+ "JOIN stores_list s ON b.store_code = s.store_code "
				+ "JOIN makers_list mk ON m.maker_code = mk.maker_code "
				+ "JOIN types_list t ON m.type_code = t.type_code "
				+ "JOIN prefs_list p ON b.pref_code = p.pref_code "
				+ "ORDER BY saleStatus DESC";

		// データベースへの接続と、命令を実行するための準備
		// try () の中に書くことで、処理が終わった時に自動で閉じられる
		try (
				Connection conn = DriverManager.getConnection(dbUrl, dbUsername, dbPassword); // データベースに接続
				Statement stmt = conn.createStatement(ResultSet.TYPE_FORWARD_ONLY, ResultSet.CONCUR_READ_ONLY); // 命令を実行する準備
				// データをまとめて書き出すことで、処理を速くする
				BufferedWriter bufferedWriter = new BufferedWriter(writer)) {

			// データベースから一度に取得するデータ量を1000行に設定
			stmt.setFetchSize(1000);
			// SQL命令を実行し、結果を受け取る
			ResultSet rs = stmt.executeQuery(sqlQuery);

			// 現在の販売状況を記録する変数。最初は存在しない値で初期化
			int currentStatus = Integer.MIN_VALUE;

			// 取得した結果がなくなるまで、1行ずつ処理を続ける
			while (rs.next()) {
				// 結果からsaleStatusの値を取得
				int dtoStatus = rs.getInt("saleStatus");

				// 販売状況が変わったら、区切りヘッダーを書き込む
				if (dtoStatus != currentStatus) {
					// 最初のヘッダーでなければ、「ここまで」を書き込む
					if (currentStatus != Integer.MIN_VALUE) {
						bufferedWriter.write("--- " + getStatusHeader(currentStatus) + "ここまで ---\n");
					}
					// 新しい状況の「ここから」とヘッダーを書き込む
					bufferedWriter.write(
							"--- " + getStatusHeader(dtoStatus) + "ここから ---\n都道府県,販売店,支店名,販売日,販売額,定価,メーカ,タイプ,車種\n");
					// 状況を更新
					currentStatus = dtoStatus;
				}

				// 結果の各列をカンマで区切って1つの文字列にまとめる
				String csvLine = rs.getString("prefName") + ","
						+ rs.getString("storeName") + ","
						+ rs.getString("branchName") + ","
						+ rs.getString("PD") + ","
						+ rs.getDouble("salesAmount") + ","
						+ rs.getDouble("listPrice") + ","
						+ rs.getString("makerName") + ","
						+ rs.getString("typeName") + ","
						+ rs.getString("modelName") + "\n";

				// 作成した文字列をファイルに書き出す
				bufferedWriter.write(csvLine);
			}

			// 最後のグループの「ここまで」を書き込む
			if (currentStatus != Integer.MIN_VALUE) {
				bufferedWriter.write("--- " + getStatusHeader(currentStatus) + "ここまで ---\n");
			}

		} catch (Exception e) {
			// 何か問題が起きたら、分かりやすいエラーメッセージに変換して伝える
			throw new RuntimeException("CSV書き込み中にエラーが発生しました", e);
		}
		// bufferedWriterは自動で閉じるので、ここで特別な処理は不要
	}

	// 販売状況のコード（-1, 0, 1）を、わかりやすい日本語に変換する
	private String getStatusHeader(int status) {
		if (status == 1)
			return "定価以上で販売";
		if (status == 0)
			return "定価程度で販売";
		if (status == -1)
			return "安い価格販売";
		return "";
	}
}