package com.css.pos.view.utils;

import com.oracle.xmlns.oxp.service.v2.AccessDeniedException_Exception;
import com.oracle.xmlns.oxp.service.v2.InvalidParametersException_Exception;
import com.oracle.xmlns.oxp.service.v2.OperationFailedException_Exception;
import com.oracle.xmlns.oxp.service.v2.ReportRequest;
import com.oracle.xmlns.oxp.service.v2.ReportResponse;
import com.oracle.xmlns.oxp.service.v2.ReportService;
import com.oracle.xmlns.oxp.service.v2.ReportService_Service;

import java.util.Map;

import javax.xml.ws.BindingProvider;

public class ReportsUtils {
    public static byte[] runCountriesReport() {
        byte[] bytes = null;
        ReportService_Service reportService_Service = new ReportService_Service();
        ReportService reportService = reportService_Service.getReportService();
        //passing the user name and password to request
        Map requestContext = ((BindingProvider) reportService).getRequestContext();
        requestContext.put(BindingProvider.USERNAME_PROPERTY, "RCOM.CLD");
        requestContext.put(BindingProvider.PASSWORD_PROPERTY, "Fusion@123");

        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setAttributeFormat("xml");
        reportRequest.setAttributeLocale("en-us");
        reportRequest.setReportAbsolutePath("/Sample/CountriesDM.xdm");
        reportRequest.setSizeOfDataChunkDownload(-1);

        try {
            ReportResponse dataModel = reportService.runDataModel(reportRequest, "RCOM.CLD", "Fusion@123");
            bytes = dataModel.getReportBytes();
        } catch (AccessDeniedException_Exception | InvalidParametersException_Exception |
                 OperationFailedException_Exception e) {
        }

        return bytes;
    }
}
