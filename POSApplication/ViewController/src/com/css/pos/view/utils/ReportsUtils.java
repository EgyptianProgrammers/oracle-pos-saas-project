package com.css.pos.view.utils;

import com.oracle.xmlns.oxp.service.v2.ReportRequest;
import com.oracle.xmlns.oxp.service.v2.ReportResponse;
import com.oracle.xmlns.oxp.service.v2.ReportService;
import com.oracle.xmlns.oxp.service.v2.ReportService_Service;

import java.net.URL;

import java.util.Map;

import javax.xml.ws.BindingProvider;

public class ReportsUtils {
    public static byte[] runCountriesReport() {
        byte[] bytes = null;
        URL url = null;
        try {
            url = new URL(AppConfig.getProperty("reportservice"));
//        } catch (Exception ex) {
//
//        }
            System.out.println("url is " + url.getPath());
        ReportService_Service reportService_Service = new ReportService_Service(url);
        ReportService reportService = reportService_Service.getReportService();
        //passing the user name and password to request
        Map requestContext = ((BindingProvider) reportService).getRequestContext();
        requestContext.put(BindingProvider.USERNAME_PROPERTY, AppConfig.getProperty("biusername"));
        requestContext.put(BindingProvider.PASSWORD_PROPERTY, AppConfig.getProperty("bipassword"));

        ReportRequest reportRequest = new ReportRequest();
        reportRequest.setAttributeFormat("xml");
        reportRequest.setAttributeLocale("en-us");
        reportRequest.setReportAbsolutePath(AppConfig.getProperty("countryreportpath"));
        reportRequest.setSizeOfDataChunkDownload(-1);

     
            ReportResponse dataModel = reportService.runDataModel(reportRequest,  AppConfig.getProperty("biusername"), AppConfig.getProperty("bipassword"));
            bytes = dataModel.getReportBytes();
        } catch (Exception ex) {
        }

        return bytes;
    }
}
