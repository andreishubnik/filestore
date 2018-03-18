package com.aash.uploadfile.controller;

import com.aash.uploadfile.entities.UploadedFile;
import com.aash.uploadfile.storage.UploadFileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.method.annotation.MvcUriComponentsBuilder;

import java.io.ByteArrayInputStream;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Контроллер для API загрузки файла
 */
@RestController
@RequestMapping("/api")
public class RestUploadController {

	/**
	 * Значение максимального размера файла указано в application.properties
	 */
	private static final String FILE_SIZE_ERROR = "Ошибка загрузки файла. Файл не может быть больше 500KB";
	private static final String SUCCESS_UPLOAD_MESSAGE = "Успешно загружен файл: ";

	@Autowired
	private UploadFileService uploadFileService;

	/**
	 * ИСпользуется для загрузки файла
	 * @param file загружаемый файл
	 * @return сообщение о результате загрузки
	 * @throws Exception выбрасывается при ошибке загрузк файла
	 */
	@PostMapping("/uploadfile")
	public String uploadFileMulti(
			@RequestParam("uploadfile") MultipartFile file) throws Exception {

		try {
			uploadFileService.store(file);
			return SUCCESS_UPLOAD_MESSAGE + file.getOriginalFilename();
		} catch (Exception e) {
			throw new Exception(FILE_SIZE_ERROR);
		}
	}

	/**
	 * Используется для получения всех имён файлов
	 * для отображения и последующей загрузки
	 * @return список имен файлов
	 */
	@GetMapping("/getallfiles")
	public List<String> getListFiles() {
		return uploadFileService.getAllFileNames().stream()
				.map(fileName -> MvcUriComponentsBuilder
						.fromMethodName(RestUploadController.class, "getFile", fileName).build().toString())
				.collect(Collectors.toList());
	}

	/**
	 * Используется дял скачивания файла по имени
	 * @param filename имя файла
	 * @return скачиваемый файл
	 */
	@GetMapping("/files/{filename:.+}")
	public ResponseEntity<Resource> getFile(@PathVariable String filename) {
		UploadedFile file = uploadFileService.loadFile(filename);
		InputStreamResource inputStreamResource = new InputStreamResource(new ByteArrayInputStream(file.getFileContent()));
		return ResponseEntity.ok()
				.header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + file.getId() + "\"")
				.contentLength(file.getFileContent().length)
				.contentType(MediaType.APPLICATION_OCTET_STREAM)
				.body(inputStreamResource);
	}

}
