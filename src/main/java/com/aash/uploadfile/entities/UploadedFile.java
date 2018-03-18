package com.aash.uploadfile.entities;

import javax.persistence.*;

/**
 * Сущность для хранения файла в БД
 */
@Entity
public class UploadedFile {

	@Id
	private String id;
	@Lob
	private byte[] fileContent;

	/**
	 * Возвращает имя файла
	 * @return имя файла
	 */
	public String getId() {
		return id;
	}

	/**
	 * Устанавливает имя файла (имя файла должно быть уникальным)
	 * @param fileName имя файла
	 */
	public void setId(String fileName) {
		this.id = fileName;
	}

	/**
	 * Возвращает содержимое файла
	 * @return содержимое файла
	 */
	public byte[] getFileContent() {
		return fileContent;
	}

	/**
	 * Устанавливает содержимое файла
	 * @param fileContent содержимое файла
	 */
	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

}
