package ninyo.questionone;

import ninyo.questionone.model.Invoice;

import java.util.List;

public class InvoiceNumbersParser {

    public static void main(String args[]) {
        InvoiceNumbersParser invoiceNumbersParser = new InvoiceNumbersParser();
        invoiceNumbersParser.parse("input_Q1a.txt", "output_Q1a.txt");
        invoiceNumbersParser.parse("input_Q1b.txt", "output_Q1b.txt");
    }

    public void parse(String inputFileName, String outputFileName) {
        InvoiceReader invoiceReader = new InvoiceReader();
        List<Invoice> invoices = invoiceReader.read(inputFileName); // problem in case invoices list is too long, could write to file each invoice separately (and each writer could be a thread)
        InvoiceWriter invoiceWriter = new InvoiceWriter();
        invoiceWriter.write(invoices, outputFileName);
    }

}