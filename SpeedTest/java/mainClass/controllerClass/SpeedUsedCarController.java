package mainClass.controllerClass;

import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.method.annotation.StreamingResponseBody;

import mainClass.serviceClass.SpeedUsedCarService;

@Controller
@RequestMapping("/sales-history")
public class SpeedUsedCarController {

	private final SpeedUsedCarService speedUsedCarService;

	public SpeedUsedCarController(SpeedUsedCarService speedUsedCarService) {
		this.speedUsedCarService = speedUsedCarService;
	}

	@GetMapping("/download-report")
	public ResponseEntity<StreamingResponseBody> downloadCsvReport() {
		HttpHeaders headers = new HttpHeaders();
		headers.add(HttpHeaders.CONTENT_TYPE, "text/csv;charset=UTF-8");
		headers.add(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"sales_report.csv\"");

		StreamingResponseBody body = outputStream -> {
			Writer writer = new OutputStreamWriter(outputStream, StandardCharsets.UTF_8);

			speedUsedCarService.streamCsvReport(writer);

		};

		return ResponseEntity.ok().headers(headers).body(body);
	}
}