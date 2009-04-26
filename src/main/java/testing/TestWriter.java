package testing;

import com.googlecode.pinthura.io.FileTextWriterImpl;
import com.googlecode.pinthura.io.FileWriterFactoryImpl;

import java.util.Arrays;

public final class TestWriter {

    public static void main(String[] args) {
        final FileTextWriterImpl writer = new FileTextWriterImpl(new FileWriterFactoryImpl());
        writer.write("/home/sanjiv/ziptemp/testing.txt", Arrays.asList("Testing 1 2 3"));
    }
}
