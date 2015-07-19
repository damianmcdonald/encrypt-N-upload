package com.github.damianmcdonald.encryptnupload.service;

public interface ReaperService {
	
	public void removeExpiredSessions();
	
	public void removeExpiredDocuments();
	
}
