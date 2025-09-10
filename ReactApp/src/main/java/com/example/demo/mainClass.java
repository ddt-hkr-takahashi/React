package com.example.demo;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.servlet.config.annotation.CorsRegistry;

@SpringBootApplication
@MapperScan("com.example.mapper") // この行を追加
public class mainClass {

	public static void main(String[] args) {
		SpringApplication.run(mainClass.class, args);
	}

    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/api/**") // APIへのすべてのリクエストを対象
                .allowedOrigins("http://localhost:5173") // フロントエンドのURLを許可
                .allowedMethods("GET", "POST", "PUT", "DELETE"); // 許可するHTTPメソッドを指定
    }
}