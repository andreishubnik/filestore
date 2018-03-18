package com.aash.uploadfile.storage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.aash.uploadfile.repositories.UploadedFileRepository;
import com.aash.uploadfile.entities.UploadedFile;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

/**
 * Сервис для работы с загружаемыми файлами
 */
@Service
public class UploadFileService {

	private static final String DOWNLOAD_FILE_ERROR = "Ошибка при скачивании файла.";
	private static final String SAVE_ERROR = "Ошибка сохранения в БД.";

	@Autowired
	private UploadedFileRepository repository;

	/**
	 * Сохранить загружаемый файл
	 * @param file загружаемый файл
	 */
	public void store(MultipartFile file) {
		try {
			UploadedFile uploadedFile = new UploadedFile();
			uploadedFile.setId(file.getOriginalFilename());
			uploadedFile.setFileContent(file.getBytes());
			repository.save(uploadedFile);
		} catch (IOException e) {
			throw new RuntimeException(SAVE_ERROR);
		}
	}

	/**
	 * Получить загружаемый файл по имени
	 * @param filename имя файла
	 * @return загружаемый файл
	 */
	public UploadedFile loadFile(String filename) {
		UploadedFile uploadedFile = repository.findOne(filename);
		if (uploadedFile != null) {
			return uploadedFile;
		} else {
			throw new RuntimeException(DOWNLOAD_FILE_ERROR);
		}
	}

	/**
	 * Удалить все записи загружаемых файлов
	 */
	public void deleteAll() {
		repository.deleteAll();
	}

	/**
	 * Получение списка имён загруженных файлов
	 * @return список имён
	 */
	public List<String> getAllFileNames() {
		List<String> fileNames = new ArrayList<>();
		repository.findAll().forEach(item -> fileNames.add(item.getId()));
		return fileNames;
	}

}