package ninyo.questionone;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import ninyo.questionone.model.Invoice;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
public class InvoiceReaderTest {

    @Test
    public void testRead() {
        InvoiceReader invoiceReader = new InvoiceReader();

        List<Invoice> invoices = invoiceReader.read("input_Q1.txt");

        assertEquals(3, invoices.size());
        Invoice invoice = invoices.get(0);
        assertEquals("600143155", invoice.toString());
        assertFalse(invoice.isIllegal());
        invoice = invoices.get(1);
        assertEquals("650408454", invoice.toString());
        assertFalse(invoice.isIllegal());
        invoice = invoices.get(2);
        assertEquals("3870570?1 ILLEGAL", invoice.toString());
        assertTrue(invoice.isIllegal());
    }

}