package org.semisoft.findmp.parsing;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileNotFoundException;
import java.util.Iterator;

public class Parser {
    public static void read (InputStream excelFile) throws IOException {
        try {

            Workbook workbook = new XSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> iterator = datatypeSheet.iterator();
            int i=0;

            while (iterator.hasNext()) {

                Row currentRow = iterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {

                    Cell currentCell = cellIterator.next();
                    if (currentCell.getCellTypeEnum() == CellType.STRING) {
                        System.out.print(currentCell.getStringCellValue() + "      ");
                    } else if (currentCell.getCellTypeEnum() == CellType.NUMERIC) {
                        System.out.print(currentCell.getNumericCellValue() + "      ");
                    }


                }
                System.out.println("\n");

            }
            workbook.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    public static void strona () {
        try {
            int i=0;
            Document doc = Jsoup.parse(new URL("http://kolejki.nfz.gov.pl/Informator/PobierzDane/Index/"), 2000);
            Elements resultLinks = doc.select("div.ownfz span");
            //Elements resultLinks = doc.select("span[class]");
            System.out.println("number of links: " + resultLinks.size());
            for (Element link : resultLinks) {
                i+=1;
                System.out.println();
                System.out.println("Title: " + link.text());
                System.out.println(i);
                URL url=new URL("http://kolejki.nfz.gov.pl/Informator/PobierzPlikXLS?term="+link.text());
                String file ="C:/Users/E540/Documents/gs/"+i+".zip";
                ZipInputStream zin = new ZipInputStream(url.openStream());
                ZipEntry entry=zin.getNextEntry();
                read(zin);
            }
        }
        catch (MalformedURLException e){
            e.printStackTrace();
        }
        catch(IOException e)
        {
            e.printStackTrace();
        }
    }
}
