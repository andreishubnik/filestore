package com.aash.uploadfile;

import javax.annotation.Resource;

import com.aash.uploadfile.storage.UploadFileService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FileStoreApplication implements CommandLineRunner {

	@Resource
	private UploadFileService uploadFileService;

	public static void main(String[] args) throws Exception {
		SpringApplication.run(FileStoreApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		uploadFileService.deleteAll();
	}

}