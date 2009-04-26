package com.googlecode.teltra.client;

import org.htmlparser.parserapplications.StringExtractor;

public final class BigpondInformationParser {

    private static final String NEWLINE = "\n";
    private static final String USAGE_SECTION = "Usage";
    private final String usageFilePath;

    public BigpondInformationParser(final String usageFilePath) {
        this.usageFilePath = usageFilePath;
    }

    public BigpondUsageInformation parse() throws BigpondInformationParserException {

        try {
            final InformationFiller filler = new BigpondInformationFiller();

            final String data = new StringExtractor(usageFilePath).extractStrings(false);
            final String[] strings = data.split(NEWLINE);

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

            x += 4; //jump over the headings.
            filler.fillUsageInfo(strings, x);

            return filler.getFilledInformation();
        } catch (Exception e) {
            throw new BigpondInformationParserException(e);
        }
    }
}
