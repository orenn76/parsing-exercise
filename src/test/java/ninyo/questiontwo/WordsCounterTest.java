package ninyo.questiontwo;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(MockitoExtension.class)
public class WordsCounterTest {

    @Test
    public void testRead() {
        WordsCounter wc = new WordsCounter();
        ByteArrayOutputStream outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        wc.load("file1.txt", "file2.txt", "file3.txt");
        wc.displayStatus();

        String display = """
                and 1
                file 3
                first 1
                is 3
                one 1
                second 1
                the 3
                third 1
                this 3
                ** total: 17""";
        display = display.replaceAll("\n", System.lineSeparator());
        assertEquals(display, outContent.toString());
    }

}