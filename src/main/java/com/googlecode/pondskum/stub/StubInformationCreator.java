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
package com.googlecode.pondskum.stub;

import com.googlecode.pondskum.client.BigpondUsage;
import com.googlecode.pondskum.client.BigpondUsageImpl;

public final class StubInformationCreator {

    public BigpondUsage createUsage(final String downloadUsage, final String uploadUsage, final String unmeteredUsage,
                                     final String totalUsage) {
        BigpondUsage bigpondUsage = new BigpondUsageImpl();
        bigpondUsage.setDownloadUsage(downloadUsage);
        bigpondUsage.setUploadUsage(uploadUsage);
        bigpondUsage.setUnmeteredUsage(unmeteredUsage);
        bigpondUsage.setTotalUsage(totalUsage);
        return bigpondUsage;
    }
}
