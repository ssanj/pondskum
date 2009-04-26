package testing;

import com.googlecode.teltra.client.BigpondInformationParser;
import com.googlecode.teltra.client.BigpondUsageInformation;
import org.htmlparser.util.ParserException;

public final class Client4 {

    public static void main(String[] args) throws ParserException {
        final BigpondUsageInformation usageInformation = new BigpondInformationParser("/home/sanjiv/ziptemp/usage_data").parse();
        System.out.println(usageInformation);
    }
}
