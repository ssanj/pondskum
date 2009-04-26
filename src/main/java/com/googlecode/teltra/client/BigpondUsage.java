package com.googlecode.teltra.client;

public final class BigpondUsage {

    private String downloadUsage;
    private String uploadUsage;
    private String totalUsage;
    private String unmeteredUsage;

    public String getDownloadUsage() {
        return downloadUsage;
    }

    public void setDownloadUsage(final String downloadUsage) {
        this.downloadUsage = downloadUsage;
    }

    public String getUploadUsage() {
        return uploadUsage;
    }

    public void setUploadUsage(final String uploadUsage) {
        this.uploadUsage = uploadUsage;
    }

    public String getTotalUsage() {
        return totalUsage;
    }

    public void setTotalUsage(final String totalUsage) {
        this.totalUsage = totalUsage;
    }

    public String getUnmeteredUsage() {
        return unmeteredUsage;
    }

    public void setUnmeteredUsage(final String unmeteredUsage) {
        this.unmeteredUsage = unmeteredUsage;
    }

    @Override
    public String toString() {
        return "BigpondUsage{" +
                "downloadUsage='" + downloadUsage + '\'' +
                ", uploadUsage='" + uploadUsage + '\'' +
                ", totalUsage='" + totalUsage + '\'' +
                ", unmeteredUsage='" + unmeteredUsage + '\'' +
                '}';
    }
}
