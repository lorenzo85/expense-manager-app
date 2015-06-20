package org.cms.rest.report.excel;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

import static org.apache.commons.codec.binary.Base64.encodeBase64;

public class ExcelDocument {

    private String name;
    private HSSFWorkbook workbook;

    public ExcelDocument(String name, HSSFWorkbook workbook) {
        this.name = name;
        this.workbook = workbook;
    }

    public String getName() {
        return name;
    }

    public byte[] asBase64BytesEncoded() throws IOException {
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workbook.write(bos);
        } finally {
            bos.close();
            workbook.close();
        }
        return encodeBase64(bos.toByteArray());
    }
}
