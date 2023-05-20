package ninyo.questionone;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ninyo.questionone.model.Digit;
import ninyo.questionone.model.Invoice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import static com.google.common.base.Splitter.fixedLength;

@Slf4j
public class InvoiceReader {

    public List<Invoice> read(String fileName) {
        List<Invoice> invoices = new ArrayList<>();

        String line = "";
        try (InputStream in = getClass().getResourceAsStream("/" + fileName);
             BufferedReader br = new BufferedReader(new InputStreamReader(in))) {

            while ((line = br.readLine()) != null) {
                Invoice invoice = createInvoice(line, br);
                invoices.add(invoice);
            }
        } catch (IOException e) {
            log.error(e.getMessage());
            throw new RuntimeException(e);
        }

        return invoices;
    }

    private Invoice createInvoice(String line, BufferedReader br) throws IOException {
        Invoice invoice = new Invoice();

        int digitPart = 0;
        while (!StringUtils.isBlank(line)) {
            if (line.length() != 27) {
                invoice.setIllegal(true);
            } else {
                int digitIndex = 0;
                for (final String digitXToken : fixedLength(3).split(line)) { // 9 digitXTokens because a none blank line contain 27 characters

                    Digit digit = invoice.getDigit(digitIndex);
                    if (digit == null) {
                        digit = new Digit();
                        invoice.addDigit(digitIndex, digit);
                    }

                    switch (digitPart) {
                        case 0:
                            if (!StringUtils.isBlank(digitXToken) && !" _ ".equals(digitXToken)) {
                                digit.setIllegal(true);
                                continue;
                            }
                            if (" _ ".equals(digitXToken)) {
                                digit.setBit(0);
                            }
                            break;
                        case 1:
                            if (digitXToken.charAt(0) == '|') {
                                digit.setBit(5);
                            }
                            if (digitXToken.charAt(1) == '_') {
                                digit.setBit(6);
                            }
                            if (digitXToken.charAt(2) == '|') {
                                digit.setBit(1);
                            }
                            break;
                        case 2:
                            if (digitXToken.charAt(0) == '|') {
                                digit.setBit(4);
                            }
                            if (digitXToken.charAt(1) == '_') {
                                digit.setBit(3);
                            }
                            if (digitXToken.charAt(2) == '|') {
                                digit.setBit(2);
                            }
                            break;
                    }
                    digitIndex = (digitIndex + 1) % 9;
                }
            }
            line = br.readLine();
            digitPart++;
        }

        return invoice;
    }

}