package com.aash.uploadfile.repositories;

import com.aash.uploadfile.entities.UploadedFile;
import org.springframework.data.repository.CrudRepository;

/**
 * Репозиторий для работы с объектом файла
 */
public interface UploadedFileRepository extends CrudRepository<UploadedFile, String> {

}
