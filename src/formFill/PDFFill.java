package formFill;

import java.io.FileOutputStream;
import java.io.IOException;

import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

public class PDFFill {

    private static final String SELECTION_MARK = "X";

    private PdfReader reader;
    private PdfStamper stamper;
    private AcroFields form;

    public PDFFill(String src, String dest) throws IOException, DocumentException {
        // Get the form to get ready for field-setting.
        reader = new PdfReader(src);
        stamper = new PdfStamper(reader, new FileOutputStream(dest));
        form = stamper.getAcroFields();
    }

    public void setField(String field, String value) throws IOException, DocumentException {
        form.setField(field, value);
    }

    public void setField(String field, int value) throws IOException, DocumentException {
        form.setField(field, Integer.toString(value));
    }

    public void checkField(String field) throws IOException, DocumentException {
        form.setField(field, SELECTION_MARK);
    }

    public void close() throws DocumentException, IOException {
        stamper.close();
        reader.close();
    }

}
