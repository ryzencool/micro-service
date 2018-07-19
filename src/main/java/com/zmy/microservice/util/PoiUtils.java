package com.zmy.microservice.util;



import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;

/**
 * @author: zmy
 * @create: 2018/6/27
 */
//@Slf4j
public class PoiUtils {

    public static final String XLSX_SUFFIX = ".xlsx";
    public static final String XLS_SUFFIX = ".xls";

    /**
     * NOTE: properties in tClass must be not primitive, use bundled type such as Boolean, Double, String...
     *
     * @param sheetNo serial number sheet to be read
     * @param ignore  ignore the header num; if not exist, ignore = 0
     * @param tClass  target object class type
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> readExcel(Workbook wb, int sheetNo, int ignore, Class<T> tClass) throws IOException {
        Sheet hs = wb.getSheetAt(sheetNo);
        final List<T> list = new ArrayList<>(200);
        // 通过缓存来提高性能
        Field[] fields = tClass.getDeclaredFields();
        int rowNo = hs.getPhysicalNumberOfRows();
        try {
            for (int rowi = ignore; rowi < rowNo; rowi++) {
                Row row = hs.getRow(rowi);
                if (row == null) {
                    break;
                }
                int cellIndex = row.getPhysicalNumberOfCells();
                T t = tClass.newInstance();
                for (int i = 0; i < cellIndex; i++) {
                    Field curField = fields[i];
                    curField.setAccessible(true);
                    Cell cell = row.getCell(i);
                    if (cell != null) {
                        switch (cell.getCellTypeEnum()) {
                            case NUMERIC:
                                // 这里判断是否有小数
                                double tmp = cell.getNumericCellValue();
                                if ((tmp % 1) == 0) {
                                    curField.set(t, String.valueOf((long) tmp));
                                } else {
                                    curField.set(t, String.valueOf(tmp));
                                }
                                break;
                            case STRING:
                                curField.set(t, cell.getStringCellValue());
                                break;
                            case BOOLEAN:
                                curField.set(t, cell.getBooleanCellValue());
                                break;
                            case FORMULA:
                                curField.set(t, cell.getCellFormula());
                                break;
                            default:
//                                log.error("unsupported sell type");
                                break;
                        }
                    }
                }
                list.add(t);
            }
        } catch (Exception e) {
//            log.error("read excel to object {} failed", tClass.getName());
            System.out.println("java");
        }
        return list;
    }

    public static <T> String writeExcel(Workbook workbook, List<T> src, String path, String filename, String suffix, Class<T> tClass, List<String> header) throws IOException, IllegalAccessException {

        assert workbook != null : "workbook not be null";

        assert src != null : "src not be null";

        Field[] fields = tClass.getDeclaredFields();

        if (header != null && header.size() != fields.length) {
            throw new IllegalArgumentException("the header length must be equals with the num of properties T");
        }

        for (int fi = 0; fi < fields.length; fi++) {
            fields[fi].setAccessible(true);
        }
        Sheet sheet = workbook.createSheet();
        Row rHeader = sheet.createRow(0);
        if (header != null) {
            for (int i = 0; i < header.size(); i++) {
                rHeader.createCell(i).setCellValue(header.get(i));
            }
        } else {
            for (int i = 0; i < fields.length; i++) {
                rHeader.createCell(i).setCellValue(tClass.getDeclaredFields()[i].getName());
            }
        }
        // set content
        for (int i = 0; i < src.size(); i++) {
            Row curRow = sheet.createRow(i + 1);
            for (int f = 0; f < fields.length; f++) {
                Object ob = fields[f].get(src.get(i));
                if (ob == null || "".equals(String.valueOf(ob))) {
                    curRow.createCell(f).setCellValue("");
                } else {
                    curRow.createCell(f).setCellValue(String.valueOf(ob));
                }
            }
        }
        String fullName = filename + suffix;
        OutputStream outputStream = new FileOutputStream(path + fullName);
        workbook.write(outputStream);
        return path + fullName;
    }

    /**
     * generate xls excel
     *
     * @param src
     * @param path
     * @param filename
     * @param tClass
     * @param header
     * @param <T>
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     */
    public static <T> String writeXLS(List<T> src, String path, String filename, Class<T> tClass, List<String> header) throws IOException, IllegalAccessException {
        Workbook workbook = new HSSFWorkbook();
        return writeExcel(workbook, src, path, filename, XLS_SUFFIX, tClass, header);
    }

    /**
     * generate xlsx excel
     *
     * @param src
     * @param path
     * @param filename
     * @param tClass
     * @param header
     * @param <T>
     * @return
     * @throws IOException
     * @throws IllegalAccessException
     */
    public static <T> String writeXLSX(List<T> src, String path, String filename, Class<T> tClass, List<String> header) throws IOException, IllegalAccessException {
        Workbook workbook = new XSSFWorkbook();
        return writeExcel(workbook, src, path, filename, XLSX_SUFFIX, tClass, header);
    }

    /**
     * read xls
     *
     * @param src
     * @param sheetNo
     * @param ignore
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> readXLS(InputStream src, int sheetNo, int ignore, Class<T> tClass) throws IOException {
        Workbook workbook = new HSSFWorkbook(src);
        return readExcel(workbook, sheetNo, ignore, tClass);
    }

    /**
     * read xlsx
     *
     * @param src
     * @param sheetNo
     * @param ignore
     * @param tClass
     * @param <T>
     * @return
     * @throws IOException
     */
    public static <T> List<T> readXLSX(InputStream src, int sheetNo, int ignore, Class<T> tClass) throws IOException {
        Workbook workbook = new XSSFWorkbook(src);
        return readExcel(workbook, sheetNo, ignore, tClass);
    }


    public static void main(String[] args) {
//        Jedis jedis = new Jedis("redis://:bitmap@10.1.3.76:6380/3");
//        String s = jedis.get("SENDMAIL_10.2.20.142_meiyong_zhou@163.com_6");
//        System.out.println("连接成功" + s);
//        System.out.println(s.indexOf("code"));
//        System.out.println(s.substring(188, 194));
    }
}


