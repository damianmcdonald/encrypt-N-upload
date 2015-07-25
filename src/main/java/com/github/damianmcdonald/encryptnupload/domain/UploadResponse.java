package com.github.damianmcdonald.encryptnupload.domain;

import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;

public class UploadResponse {

  private String uploadedFile;

  public UploadResponse() {

  }

  public UploadResponse(String uploadedFile) {
    this.uploadedFile = uploadedFile;
  }

  public String getUploadedFile() {
    return uploadedFile;
  }

  public void setUploadedFile(String uploadedFile) {
    this.uploadedFile = uploadedFile;
  }

  @Override
  public int hashCode() {
    return new HashCodeBuilder()
               .append(uploadedFile)
               .toHashCode();
  }

  @Override
  public boolean equals(final Object obj) {
    if (obj instanceof UploadResponse) {
      final UploadResponse other = (UploadResponse) obj;
      return new EqualsBuilder()
                 .append(uploadedFile, other.uploadedFile)
                 .isEquals();
    } else {
      return false;
    }
  }

}
