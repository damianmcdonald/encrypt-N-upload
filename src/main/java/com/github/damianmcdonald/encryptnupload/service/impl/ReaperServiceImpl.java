package com.github.damianmcdonald.encryptnupload.service.impl;

import com.github.damianmcdonald.encryptnupload.service.ReaperService;
import com.github.damianmcdonald.encryptnupload.service.RegistrationService;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.nio.file.attribute.FileTime;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ReaperServiceImpl implements ReaperService {

	private final Logger log = LoggerFactory.getLogger(this.getClass());

	@Value("${fileupload.directory}")
	private String uploadDir;
	
	@Value("${document.validity}")
	private long documentValidity;
	
	@Value("${document.delete}")
	private boolean isDelete;

	@Autowired
	private RegistrationService registrationService;

	@Override
	@Scheduled(cron = "0 0/30 * * * ?")
	public void removeExpiredSessions() {
		log.debug("Executing removeExpiredSessions");
		registrationService.unregister();
	}

	@Override
	@Scheduled(cron = "0 0/30 * * * ?")
	public void removeExpiredDocuments() {
		log.debug("Executing removeExpiredDocuments");
		if (!isDelete) {
			log.debug("Expired file deletion is disabled.");
			return;
		}
		File dir = new File(uploadDir);
		File[] dirListing = dir.listFiles();
		if (dirListing != null) {
			for (File child: dirListing) {
				Path file = Paths.get(child.getAbsolutePath());
				try {
					BasicFileAttributes attrs = Files.readAttributes(file, BasicFileAttributes.class);
					FileTime time = attrs.creationTime();
					if (System.currentTimeMillis() - time.toMillis() > documentValidity) {
						log.debug("File: " + child.getAbsolutePath() + " has expired and will be deleted.");
						child.delete();
					}	
				} catch (IOException e) {
					e.printStackTrace();
					log.error("Unable to get creation time for file: " + child.getAbsolutePath());
				}
			}
		}
	}

}
