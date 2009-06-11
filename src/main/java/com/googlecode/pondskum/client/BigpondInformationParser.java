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
package com.googlecode.pondskum.client;

import com.googlecode.pinthura.util.SystemPropertyRetrieverImpl;
import org.htmlparser.parserapplications.StringExtractor;

public final class BigpondInformationParser {

    private static final String USAGE_SECTION   = "Usage";
    private static final int LINES_OF_HEADINGS  = 4;

    private final String usageFilePath;
    private final String lineSeparator;

    public BigpondInformationParser(final String usageFilePath) {
        this.usageFilePath = usageFilePath;
        lineSeparator = new SystemPropertyRetrieverImpl().getLineSeparator();
    }

    public BigpondUsageInformation parse() throws BigpondInformationParserException {


        try {
            final InformationFiller filler = new BigpondInformationFiller();

            final String data = new StringExtractor(usageFilePath).extractStrings(false);
            final String[] strings = data.split(lineSeparator);

            //We do a two pass parse. First check for the account information.
            for (int x = 0; x < strings.length; x++) {
                filler.fillAccountInfo(strings, x);
            }

            //This is the second pass. We only check for usage information starting from the USAGE_SECTION constant.
            int x = 0;
            for (; x < strings.length; x++) {
                if (strings[x].equals(USAGE_SECTION)) {
                    break;
                }
            }

            x += LINES_OF_HEADINGS; //jump over the headings.
            filler.fillUsageInfo(strings, x);

            return filler.getFilledInformation();
        } catch (Exception e) {
            throw new BigpondInformationParserException(e);
        }
    }
}
