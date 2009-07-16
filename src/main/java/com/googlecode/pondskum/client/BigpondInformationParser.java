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

/**
 * Parses the information retrieved from the Bigpond account information page (if successfully logged in). Detects failed logins and
 * throw an <code>InvalidLoginException</code>.
 */
public final class BigpondInformationParser {

    //token which identifies the usage section on the account usage page
    private static final String USAGE_SECTION       = "Usage";

    //The number of headings to skip
    private static final int LINES_OF_HEADINGS      = 4;

    //The token which indicates that a login attempt failed.
    private static final String LOGIN_ERROR_TOKEN   = "Log in with your existing email account details";

    private final String usageFilePath;
    private final String lineSeparator;

    /**
     * The path to the local file which stores the usage information data. This would have been downloaded from the bigpond site.
     * @param usageFilePath The path to the local file containing the account usage information.
     */
    public BigpondInformationParser(final String usageFilePath) {
        this.usageFilePath = usageFilePath;
        lineSeparator = new SystemPropertyRetrieverImpl().getLineSeparator();
    }

    /**
     * Parses the account information or the error page received after a login attempt.
     * @return BigpondUsageInformation if all the data was located as expected.
     * @throws BigpondInformationParserException Either if the login failed, in which case it would wrap an
     * <code>InvalidLoginException</code> or if the data on the account information page has changed.
     */
    public BigpondUsageInformation parse() throws BigpondInformationParserException {
        try {
            final InformationFiller filler = new BigpondInformationFiller();

            final String data = new StringExtractor(usageFilePath).extractStrings(false);
            final String[] strings = data.split(lineSeparator);

            //check if the user logged in correctly
            verifyLogin(strings);

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
        } catch (InvalidLoginException e) {
            throw new BigpondInformationParserException(e);
        } catch (Exception e) {
            throw new BigpondInformationParserException(
                    new StringBuilder().append("There was an error parsing your account usage information.").
                                        append(" The website may have changed since this client was written.").
                                        append(" Please contact the owners of this project with the contents of your tmp directory.").
                                        append(" The nested Exception was: ").append(e.getMessage()).
                                        toString());
        }
    }

    private void verifyLogin(final String[] htmlData) {
        for (String value : htmlData) {
            if (value.contains(LOGIN_ERROR_TOKEN)) {
                throw new InvalidLoginException(new StringBuilder().append("The username and/or password entered is incorrect.").
                                                    append("Please update your bigpond_config.properties file with the correct details.").
                                                toString());
            }
        }
    }
}
