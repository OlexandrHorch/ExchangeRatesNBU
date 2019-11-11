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
    private String directoryToSaveFile;
    private ServiceForDate serviceForDate = new ServiceForDate();


    public void createExcelFile(Currency currency, String language) throws IOException {
        HSSFWorkbook workbook = new HSSFWorkbook();
        HSSFSheet sheet = workbook.createSheet("exchange");

        fillingCellsTitleRow(workbook, sheet, language);
        fillingCellsWithData(workbook, sheet, currency, language);
        setColumnWidth(sheet);
        createAndRecordFile(workbook, currency, language);
    }


    // Method for filling cells title row.
    private void fillingCellsTitleRow(HSSFWorkbook workbook, HSSFSheet sheet, String language) {
        Row row;
        Cell cell;
        HSSFCellStyle styleForTitle = createStyleForTitle(workbook);
        row = sheet.createRow(0);
        row.setHeightInPoints(60);

        // Filling cell numeral code.
        cell = row.createCell(0, CellType.STRING);
        if (language.equals("ukr")) {
            cell.setCellValue("Код цифровий");
        } else if (language.equals("eng")) {
            cell.setCellValue("Numeral code");
        }
        cell.setCellStyle(styleForTitle);

        // Filling cell literal code.
        cell = row.createCell(1, CellType.STRING);
        if (language.equals("ukr")) {
            cell.setCellValue("Код літерний");
        } else if (language.equals("eng")) {
            cell.setCellValue("Numeral code");
        }
        cell.setCellStyle(styleForTitle);

        // Filling cell currency name.
        cell = row.createCell(2, CellType.STRING);
        if (language.equals("ukr")) {
            cell.setCellValue("Назва валюти");
        } else if (language.equals("eng")) {
            cell.setCellValue("Currency name");
        }
        cell.setCellStyle(styleForTitle);

        // Filling cell currency rate.
        cell = row.createCell(3, CellType.NUMERIC);
        if (language.equals("ukr")) {
            cell.setCellValue("Курс валюти");
        } else if (language.equals("eng")) {
            cell.setCellValue("Currency rate");
        }
        cell.setCellStyle(styleForTitle);

        // Filling cell total in UAH.
        cell = row.createCell(4, CellType.NUMERIC);
        if (language.equals("ukr")) {
            cell.setCellValue("Сума в гривні");
        } else if (language.equals("eng")) {
            cell.setCellValue("Total in UAH");
        }
        cell.setCellStyle(styleForTitle);

        // Filling cell equivalent in currency.
        cell = row.createCell(5, CellType.NUMERIC);
        if (language.equals("ukr")) {
            cell.setCellValue("Еквівалент в валюті");
        } else if (language.equals("eng")) {
            cell.setCellValue("Equivalent in currency");
        }
        cell.setCellStyle(styleForTitle);

        // Filling cell exchange date.
        cell = row.createCell(6, CellType.STRING);
        if (language.equals("ukr")) {
            cell.setCellValue("Дата");
        } else if (language.equals("eng")) {
            cell.setCellValue("Date");
        }
        cell.setCellStyle(styleForTitle);
    }


    // Method for filling cells with data.
    private void fillingCellsWithData(HSSFWorkbook workbook, HSSFSheet sheet, Currency currency, String language) {
        Row row;
        Cell cell;
        HSSFCellStyle styleForOtherRow = createStyleForOtherRow(workbook);
        row = sheet.createRow(1);
        row.setHeightInPoints(15);

        // Filling cell numeral code.
        cell = row.createCell(0, CellType.STRING);
        cell.setCellValue(currency.getCurrencyNumeralCode());
        cell.setCellStyle(styleForOtherRow);

        // Filling cell literal code.
        cell = row.createCell(1, CellType.STRING);
        cell.setCellValue(currency.getCurrencyLiteralCode().name());
        cell.setCellStyle(styleForOtherRow);

        // Filling cell currency name.
        cell = row.createCell(2, CellType.STRING);
        if (language.equals("ukr")) {
            cell.setCellValue(currency.getCurrencyLiteralCode().getDescription());
        } else if (language.equals("eng")) {
            cell.setCellValue(currency.getCurrencyLiteralCode().getDescriptionEng());
        }
        cell.setCellStyle(styleForOtherRow);

        // Filling cell currency rate.
        cell = row.createCell(3, CellType.NUMERIC);
        cell.setCellValue(currency.getCurrencyRate());
        cell.setCellStyle(styleForOtherRow);

        // Filling cell total in UAH.
        cell = row.createCell(4, CellType.NUMERIC);
        cell.setCellValue(currency.getCurrencyTotalInUAH());
        cell.setCellStyle(styleForOtherRow);

        // Filling cell equivalent in currency.
        cell = row.createCell(5, CellType.NUMERIC);
        cell.setCellValue(currency.getCurrencyEquivalentInCurrency());
        cell.setCellStyle(styleForOtherRow);

        // Filling cell exchange date.
        cell = row.createCell(6, CellType.STRING);
        cell.setCellValue(serviceForDate.transformDateFormat(currency.getCurrencyExchangeDate()));
        cell.setCellStyle(styleForOtherRow);
    }


    // Method for setting style for title row.
    private HSSFCellStyle createStyleForTitle(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();

        // Setting wrapping.
        style.setWrapText(true);

        // Setting alignment.
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // Setting style for font.
        HSSFFont font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 15);
        font.setBold(true);
        style.setFont(font);

        // Setting style for border.
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }


    // Method for setting style for other row.
    private HSSFCellStyle createStyleForOtherRow(HSSFWorkbook workbook) {
        HSSFCellStyle style = workbook.createCellStyle();

        // Setting alignment.
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setVerticalAlignment(VerticalAlignment.CENTER);

        // Setting style for font.
        HSSFFont font = workbook.createFont();
        font.setFontName("Times New Roman");
        font.setFontHeightInPoints((short) 10);
        style.setFont(font);

        // Setting style for border.
        style.setBorderBottom(BorderStyle.THIN);
        style.setBorderTop(BorderStyle.THIN);
        style.setBorderLeft(BorderStyle.THIN);
        style.setBorderRight(BorderStyle.THIN);

        return style;
    }


    // Method for setting column width.
    private void setColumnWidth(HSSFSheet sheet) {
        sheet.setColumnWidth(0, 4000);
        sheet.setColumnWidth(1, 4000);
        sheet.setColumnWidth(2, 4000);
        sheet.setColumnWidth(3, 4000);
        sheet.setColumnWidth(4, 6000);
        sheet.setColumnWidth(5, 6000);
        sheet.autoSizeColumn(6);
    }


    // Method for creating and record file.
    private void createAndRecordFile(HSSFWorkbook workbook, Currency currency, String language) throws IOException {
        chooseDirectory(language);

        if (directoryToSaveFile != null) {
            String filePathName = directoryToSaveFile
                    + "/ExchangeRatesNBU_"
                    + currency.getCurrencyExchangeDate() + "_"
                    + currency.getCurrencyLiteralCode()
                    + ".xls";
            File file = new File(filePathName);
            FileOutputStream outFile = new FileOutputStream(file);
            workbook.write(outFile);
            outFile.close();
        }

        workbook.close();
    }


    // Method for choosing directory.
    private void chooseDirectory(String language) {
        JFileChooser fileChooser = new JFileChooser();
        fileChooser.setFileSelectionMode(1);
        if (language.equals("ukr")) {
            fileChooser.showDialog(null, "Вибрати каталог");
        } else if (language.equals("eng")) {
            fileChooser.showDialog(null, "Choose directory");
        }

        try {
            directoryToSaveFile = fileChooser.getSelectedFile().getPath();
        } catch (NullPointerException e) {
            System.out.println("Directory don't chosen!");
        }
    }
}