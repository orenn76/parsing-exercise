package ninyo.questionone;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import static org.junit.jupiter.api.Assertions.assertEquals;

@Slf4j
@ExtendWith(MockitoExtension.class)
public class InvoiceNumbersParserTest {

    private static final String INPUT_Q1A = "input_Q1a.txt";
    private static final String OUTPUT_EXPECTED_Q1A = "src/test/resources/output_Q1a.txt";
    private static final String OUTPUT_GENERATED_Q1A = "output_generated_Q1a.txt";
    private static final String INPUT_Q1_B = "input_Q1b.txt";
    private static final String OUTPUT_EXPECTED_Q1B = "src/test/resources/output_Q1b.txt";
    private static final String OUTPUT_GENERATED_Q1B = "output_generated_Q1b.txt";

    @Test
    public void testParseInvoiceNumbers() throws IOException {
        InvoiceNumbersParser invoiceNumbersParser = new InvoiceNumbersParser();

        invoiceNumbersParser.parse(INPUT_Q1A, OUTPUT_GENERATED_Q1A);

        assertEquals(FileUtils.readFileToString(new File(OUTPUT_EXPECTED_Q1A), "utf-8"),
                FileUtils.readFileToString(new File(OUTPUT_GENERATED_Q1A), "utf-8"));
    }

    @Test
    public void testParseInvoiceNumbersWithIllegals() throws IOException {
        InvoiceNumbersParser invoiceNumbersParser = new InvoiceNumbersParser();

        invoiceNumbersParser.parse(INPUT_Q1_B, OUTPUT_GENERATED_Q1B);

        assertEquals(FileUtils.readFileToString(new File(OUTPUT_EXPECTED_Q1B), "utf-8"),
                FileUtils.readFileToString(new File(OUTPUT_GENERATED_Q1B), "utf-8"));
    }

    @AfterAll
    private static void cleanup() {
        try {
            Files.deleteIfExists(Paths.get(OUTPUT_GENERATED_Q1A));
            Files.deleteIfExists(Paths.get(OUTPUT_GENERATED_Q1B));
        } catch (IOException e) {
            log.error(e.getMessage());
        }
    }

}