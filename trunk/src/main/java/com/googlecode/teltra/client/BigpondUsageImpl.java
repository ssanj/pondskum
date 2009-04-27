/*
 * Copyright 2008 Sanjiv Sahayam
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.googlecode.teltra.client;

public final class BigpondUsageImpl implements BigpondUsage {

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
        return "BigpondUsageImpl{" +
                "downloadUsage='" + downloadUsage + '\'' +
                ", uploadUsage='" + uploadUsage + '\'' +
                ", totalUsage='" + totalUsage + '\'' +
                ", unmeteredUsage='" + unmeteredUsage + '\'' +
                '}';
    }
}
