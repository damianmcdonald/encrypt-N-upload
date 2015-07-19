package com.github.damianmcdonald.encryptnupload.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RegisteredUser {
	
	private String userName;
	private String password;
	private long lastAccessTime;
	
	public RegisteredUser(final String userName, final String password, final long lastAccessTime) {
		this.userName = userName;
		this.password = password;
		this.lastAccessTime = lastAccessTime;
	}
	
	@Override
	public int hashCode(){
	    return new HashCodeBuilder()
	        .append(userName)
	        .append(password)
	        .append(lastAccessTime)
	        .toHashCode();
	}

	@Override
	public boolean equals(final Object obj){
	    if(obj instanceof RegisteredUser){
	        final RegisteredUser other = (RegisteredUser) obj;
	        return new EqualsBuilder()
	            .append(userName, other.userName)
	            .append(password, other.password)
	            .append(lastAccessTime, other.lastAccessTime)
	            .isEquals();
	    } else{
	        return false;
	    }
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public long getLastAccessTime() {
		return lastAccessTime;
	}

	public void setLastAccessTime(long lastAccessTime) {
		this.lastAccessTime = lastAccessTime;
	}

}
