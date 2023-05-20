package ninyo.questionone;

import lombok.extern.slf4j.Slf4j;
import ninyo.questionone.model.Invoice;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

@Slf4j
public class InvoiceWriter {

    public void write(List<Invoice> invoices, String fileName) {
        Path path = Paths.get(fileName);
        try {
            Files.deleteIfExists(path);
            invoices.forEach(invoice -> writeToFile(invoice.toString() + "\n", path));
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void writeToFile(String output, Path path) {
        try {
            Files.write(path, output.getBytes(), StandardOpenOption.CREATE, StandardOpenOption.APPEND);
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }

}