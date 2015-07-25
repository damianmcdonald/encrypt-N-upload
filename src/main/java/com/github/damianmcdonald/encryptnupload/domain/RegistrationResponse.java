package com.github.damianmcdonald.encryptnupload.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class RegistrationResponse {

  private String userName;
  private String password;
  private String hash;

  public RegistrationResponse() {

  }

  public RegistrationResponse(String userName, String password, String hash) {
    this.userName = userName;
    this.password = password;
    this.hash = hash;
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

  public String getHash() {
    return hash;
  }

  public void setHash(String hash) {
    this.hash = hash;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
               .append(userName)
               .append(password)
               .append(hash)
               .toHashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof RegistrationResponse) {
      final RegistrationResponse other = (RegistrationResponse) obj;
      return new EqualsBuilder()
                 .append(userName, other.userName)
                 .append(password, other.password)
                 .append(hash, other.hash)
                 .isEquals();
    } else {
      return false;
    }
  }

}
