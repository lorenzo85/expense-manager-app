package org.cms.data.app.rest.reports;

import org.cms.data.app.rest.reports.styles.StyleFactory;
import org.cms.data.app.rest.reports.templates.ExcelTemplate;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.view.document.AbstractExcelView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;

import static java.lang.String.format;


@Component("excelBuilder")
@Scope("prototype")
public class ExcelView extends AbstractExcelView {

    private static final int DEFAULT_COLUMN_WIDTH = 20;
    private static final String HEADER_CONTENT_DISPOSITION = "Content-Disposition";
    private static final String HEADER_CONTENT_DISPOSITION_FILE = "attachment; filename=\"%s\"";


    @Autowired
    private ApplicationContext context;

    private ExcelTemplate template;

    public ExcelView(ExcelTemplate template) {
        this.template = template;
    }

    @Override
    protected void buildExcelDocument(Map<String, Object> map, HSSFWorkbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        HSSFSheet sheet = createSheet(workbook);

        StyleFactory headerStyleFactory = (StyleFactory) context.getBean("headerStyleFactory", workbook);
        StyleFactory rowStyleFactory = (StyleFactory) context.getBean("rowStyleFactory", workbook);

        template.setSheet(sheet)
                .setRowStyle(rowStyleFactory)
                .setHeaderStyle(headerStyleFactory)
                .build();

        response.setHeader(HEADER_CONTENT_DISPOSITION,
                format(HEADER_CONTENT_DISPOSITION_FILE, template.getFilename()));
    }

    private HSSFSheet createSheet(HSSFWorkbook workbook) {
        HSSFSheet sheet = workbook.createSheet(template.getFilename());
        sheet.setDefaultColumnWidth(DEFAULT_COLUMN_WIDTH);
        return sheet;
    }
}