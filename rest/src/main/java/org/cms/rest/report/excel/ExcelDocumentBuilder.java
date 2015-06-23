package org.cms.rest.report.excel;

import java.io.IOException;
import java.util.Collection;

public interface ExcelDocumentBuilder<T> {

    String getFilename();

    ExcelDocument build() throws IOException;

    ExcelDocumentBuilder<T> entities(Collection<T> entities);
}
