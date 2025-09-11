package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * アプリケーションのエントリーポイント
 */
@SpringBootApplication
// MyBatisのMapperインターフェースが定義されているパッケージを指定
@MapperScan("com.example.mapper")
public class mainClass implements WebMvcConfigurer {

	/**
	 * メインメソッド
	 * @param args コマンドライン引数
	 */
	public static void main(String[] args) {
		// アプリケーションを起動
		SpringApplication.run(mainClass.class, args);
	}

	/**
	 * CORS（Cross-Origin Resource Sharing）設定
	 * @param registry CORS設定を登録するためのCorsRegistry
	 */
	@Override
	public void addCorsMappings(CorsRegistry registry) {
		// APIへのすべてのリクエストを対象
		registry.addMapping("/api/**")
				// フロントエンドのURLを許可
				.allowedOrigins("http://localhost:5173")
				// 許可するHTTPメソッドを指定
				.allowedMethods("GET", "POST", "PUT", "DELETE");
	}
}