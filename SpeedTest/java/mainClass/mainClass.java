package mainClass;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan(basePackages = {"com.example.mapper.selfCreateMapper", "com.example.mapper.autoCreateMapper"})// Mapperインターフェースが存在するパッケージを指定
public class mainClass {

	public static void main(String[] args) {
		SpringApplication.run(mainClass.class, args);
	}

}
