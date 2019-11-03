package com.exchangeratesnbu.serviсe;

import com.exchangeratesnbu.entity.Currency;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;

import javax.swing.*;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

public class ServiceForExportToExcel {
    public void createExcelFile(Currency currency) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("exchange");

        fillingCellsTitleRow(workbook, sheet);
        fillingCellsWithData(workbook, sheet, currency);
        setColumnWidth(sheet);
        createAndRecordFile(workbook, currency);
    }


    // Method for filling cells title row
    private void fillingCellsTitleRow(HSSFWorkbook workbook, HSSFSheet sheet) {
        Row row;
        Cell cell;
        HSSFCellStyle styleForTitle = createStyleForTitle(workbook);
        row = sheet.createRow(0);
        row.setHeightInPoints(60);

        // filling cells
        // Numeral code
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue("Код цифровий");
        cell.setCellStyle(styleForTitle);
        // Literal code
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue("Код літерний");
        cell.setCellStyle(styleForTitle);
        // Name
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue("Назва валюти");
        cell.setCellStyle(styleForTitle);
        // Rate
        cell = row.createCell(3, CellType.NUMERIC);
        cell.setCellValue("Офіційний курс");
        cell.setCellStyle(styleForTitle);
        // Total in UAH
        cell = row.createCell(4, CellType.NUMERIC);
        cell.setCellValue("Сума в гривні");
        cell.setCellStyle(styleForTitle);
        // Equivalent in currency
        cell = row.createCell(5, CellType.NUMERIC);
        cell.setCellValue("Еквівалент гривневої суми в валюті");
        cell.setCellStyle(styleForTitle);
        // Exchange date
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue("Дата");
        cell.setCellStyle(styleForTitle);
    }


    // Method for filling cells with data from collection
    private void fillingCellsWithData(HSSFWorkbook workbook, HSSFSheet sheet, Currency currency) {
        Row row;
        Cell cell;
        HSSFCellStyle styleForOtherRow = createStyleForOtherRow(workbook);
        row = sheet.createRow(1);
        row.setHeightInPoints(15);

        // Filling cells
        // Numeral code
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue(currency.getCurrencyNumeralCode());
        cell.setCellStyle(styleForOtherRow);
        // Literal code
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue(currency.getCurrencyLiteralCode().name());
        cell.setCellStyle(styleForOtherRow);
        // Name
        cell = row.createCell(2, CellType.STRING);
        cell.setCellValue(currency.getCurrencyName());
        cell.setCellStyle(styleForOtherRow);
        // Rate
        cell = row.createCell(3, CellType.NUMERIC);
        cell.setCellValue(currency.getCurrencyRate());
        cell.setCellStyle(styleForOtherRow);
        // Total in UAH
        cell = row.createCell(4, CellType.NUMERIC);
        cell.setCellValue(currency.getCurrencyTotalInUAH());
        cell.setCellStyle(styleForOtherRow);
        // Equivalent in currency
        cell = row.createCell(5, CellType.NUMERIC);
        cell.setCellValue(currency.getCurrencyEquivalentInCurrency());
        cell.setCellStyle(styleForOtherRow);
        // Exchange date
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue(currency.getCurrencyExchangeDate());
        cell.setCellStyle(styleForOtherRow);
    }


    // Method for setting style for title row
    private HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();

        // Setting wrapping
        style.setWrapText(true);

        // Setting alignment
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // Setting style for font
        HSSFFont font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 15);
        font.setBold(true);
        style.setFont(font);

        // Setting style for border
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }


    // Method for setting style for other row
    private HSSFCellStyle createStyleForOtherRow(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();

        // Setting alignment
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // Setting style for font
        HSSFFont font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);

        // Setting style for border
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }


    // Method for setting column width
    private void setColumnWidth(HSSFSheet sheet) {
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 6000);
        sheet.autoSizeColumn(6);
    }


    // Method for creating and record file
    private void createAndRecordFile(HSSFWorkbook workbook, Currency currency) throws IOException {
        String filePathName = chooseDirectory() + "/ExchangeRatesNBU_"
                + currency.getCurrencyExchangeDate() + "_"
                + currency.getCurrencyLiteralCode()
                + ".xls";
        File file = new File(filePathName);
        FileOutputStream outFile = new FileOutputStream(file);
        workbook.write(outFile);
        workbook.close();
        outFile.close();
        System.out.println("A file was created " + file.getAbsolutePath());
    }


    // Method for choosing directory
    private String chooseDirectory() {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(1);
        fileChooser.showDialog(null, "Вибрати катклог");
        return fileChooser.getSelectedFile().getPath();
    }
}