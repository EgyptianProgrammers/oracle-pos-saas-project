package com.css.pos.view.mb.admin;

import com.css.pos.view.utils.ADFUtils;
import com.css.pos.view.utils.ReportsUtils;

import java.io.ByteArrayInputStream;
import java.io.IOException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;

import oracle.jbo.Row;
import oracle.jbo.ViewObject;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import org.xml.sax.InputSource;
import org.xml.sax.SAXException;

public class CountriesBean {

    public String sync_action() {
        // Add event code here...
        //Call run report method from Reports Util
        byte[] bytes = ReportsUtils.runCountriesReport();
        String reportBytes = new String(bytes);

        System.out.println("reportBytes is " + reportBytes);
        //Arry of byts to XML
        DocumentBuilderFactory docBuilderFactory = DocumentBuilderFactory.newInstance();
        DocumentBuilder docBuilder;
        Document doc = null;

        try {
            docBuilder = docBuilderFactory.newDocumentBuilder();
            //Pares the XML to retive the Data
            doc = docBuilder.parse(new InputSource(new ByteArrayInputStream(reportBytes.getBytes("utf-8"))));
            NodeList listOfNodes = doc.getElementsByTagName("COUNTRIESSET");
            //For loop over the XML Data
            ViewObject countriesVO = ADFUtils.findIterator("PosIntCountriesUPViewIterator").getViewObject();
            ;
            for (int i = 0; i < listOfNodes.getLength(); i++) {
                Element currElement = (Element) listOfNodes.item(i);
                String currencyCode = currElement.getElementsByTagName("CURRENCY_CODE")
                                                 .item(0)
                                                 .getTextContent();
                String countryCode = currElement.getElementsByTagName("COUNTRY_CODE")
                                                .item(0)
                                                .getTextContent();
                String countryName = currElement.getElementsByTagName("COUNTRY_NAME")
                                                .item(0)
                                                .getTextContent();
                Row newRow = countriesVO.createRow();
                newRow.setAttribute("CurrencyCode", currencyCode);
                newRow.setAttribute("CountryCode", countryCode);
                newRow.setAttribute("CountryName", countryName);
                countriesVO.insertRow(newRow);
            }
            ADFUtils.findOperation("Commit").execute();

        } catch (ParserConfigurationException e) {
            e.printStackTrace();
        } catch (SAXException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }

        //For loop over the XML Data
        //Create Row in Country table
        //Commit
        return null;
    }

    public String clearData_action() {
        // Add event code here...
        ADFUtils.findOperation("clearCountriesData").execute();
        return null;
    }
}
