package org.jeecg.modules.demo.zmexpress.utils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.CellReference;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.jeecg.modules.demo.zmexpress.entity.ZmGoodCase;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.io.InputStream;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

public class ExcelReadHelper {
    private Workbook workbook;
    private Sheet sheet;
    //Sheet 总数
    private int sheetCount;
    //当前行
    private Row row;

    public ExcelReadHelper(MultipartFile is) {
        try {
            // 只支持 xlsx，如果要支持 xls，自行修改下一行代码
           if (getFileExtName(is.getOriginalFilename()).toLowerCase().equals("xlsx"))
                workbook = new XSSFWorkbook(is.getInputStream());
           else{
               workbook = new HSSFWorkbook(is.getInputStream());
           }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        sheetCount = workbook.getNumberOfSheets();
        setSheetAt(0);
    }

    /**
     * 获取文件的后缀名
     *
     * @param fileName
     * @return
     */
    public  String getFileExtName(String fileName) {

        String prefix = fileName.substring(fileName.lastIndexOf(".") + 1);
        return prefix;
    }
    /**
     * 关闭工作簿
     *
     * @throws IOException
     */
    public void close() {
        if (workbook != null) {
            try {
                workbook.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    /**
     * 获取单元格真实位置
     *
     * @param row 行索引
     * @param col 列索引
     * @return [行, 列 ]
     */
    public String getCellLoc(Integer row, Integer col) {
        return String.format("[%s,%s]", row + 1, CellReference.convertNumToColString(col));
    }

    /**
     * 根据标签设置 Sheet
     *
     * @param labels
     */
    public void setSheetByLabel(String... labels) {
        Sheet sheet = null;
        for (String label : labels) {
            sheet = workbook.getSheet(label);
            if (sheet != null) {
                break;
            }
        }
        if (sheet == null) {
            StringBuilder sheetStr = new StringBuilder();
            for (String label : labels) {
                sheetStr.append(label).append(",");
            }
            sheetStr.deleteCharAt(sheetStr.lastIndexOf(","));
            throw new RuntimeException(sheetStr.toString() + "Sheet does not exist");
        }
        this.sheet = sheet;
    }

    /**
     * 根据索引设置 Sheet
     *
     * @param index
     */
    public void setSheetAt(Integer index) {
        Sheet sheet = workbook.getSheetAt(index);
        if (sheet == null) {
            throw new RuntimeException(index + "Sheet does not exist");
        }
        this.sheet = sheet;
    }

    /**
     * 获取单元格内容并转为 String 类型
     *
     * @param row     行号，从 1 开始
     * @param colName 列号
     * @return
     */
    @SuppressWarnings("deprecation")
    public String getValueAt(String colName, int row) {
        int colIdx = CellReference.convertColStringToIndex(colName);
        int rowIdx = row < 0 ? 0 : row - 1;
        Cell cell = sheet.getRow(rowIdx).getCell(colIdx);
        String value = null;
        if (cell != null) {
            switch (cell.getCellType()) {
                case STRING:
                    value = cell.getStringCellValue() + "";
                    break;
                case NUMERIC:
                    if (DateUtil.isCellDateFormatted(cell)) {
                        value = cell.getDateCellValue().getTime() + "";
                    } else {
                        DecimalFormat df = new DecimalFormat("0");
                        return df.format(cell.getNumericCellValue());
                    }
                    break;
                case FORMULA:
                    value = cell.getNumericCellValue() + "";
                    break;
                case BOOLEAN:
                    value = cell.getBooleanCellValue() + "";
                    break;
                case BLANK:
                    value="";
                    break;
            }
        }
        return (value == null || value.isEmpty()) ? "" : value.trim();
    }

    /**
     * 获取当前行指定列内容
     *
     * @param col 列号，从 1 开始
     * @return
     */
    public String getValue(Integer col) {
        return getValueAt(CellReference.convertNumToColString(col + 1), col);
    }

    /**
     * 获取 Sheet 名称
     *
     * @return
     */
    public String getSheetLabel() {
        String label = null;
        if (sheet != null) {
            label = sheet.getSheetName();
        }
        return label;
    }
    /**
     * 获取 Sheet 行数
     *
     * @return
     */
    public int getSheetRows() {
        int label = 0;
        if (sheet != null) {
            label = sheet.getLastRowNum();
        }
        return label;
    }

    /**
     * 行偏移
     *
     * @param offset 偏移量
     * @return
     */
    public Boolean offsetRow(Integer offset) {
        boolean state = true;
        if (row == null) {
            row = sheet.getRow(offset - 1);
        } else {
            row = sheet.getRow(row.getRowNum() + offset);
            if (row == null) {
                state = false;
            }
        }
        return state;
    }

    /**
     * 设置行
     *
     * @param index 索引
     * @return
     */
    public Boolean setRow(Integer index) {
        row = sheet.getRow(index);
        return row != null;
    }

    /**
     * 偏移一行
     *
     * @return
     */
    public Boolean nextRow() {
        return offsetRow(1);
    }

    /**
     * 偏移到下一个 Sheet
     *
     * @return
     */
    public Boolean nextSheet() {
        boolean state = true;
        if (sheet == null) {
            sheet = workbook.getSheetAt(0);
        } else {
            int index = workbook.getSheetIndex(sheet) + 1;
            if (index >= sheetCount) {
                sheet = null;
            } else {
                sheet = workbook.getSheetAt(index);
            }

            if (sheet == null) {
                state = false;
            }
        }
        row = null;
        return state;
    }

    public  int getVaildRows()  {

        Sheet sheet = workbook.getSheetAt(0);
        CellReference cellReference = new CellReference("A4");
        boolean flag = false;
        for (int i = cellReference.getRow(); i <= sheet.getLastRowNum(); ) {
            Row r = sheet.getRow(i);
            if (r == null) {
                // 如果是空行（即没有任何数据、格式），直接把它以下的数据往上移动
                sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
                continue;
            }
            flag = false;
            for (Cell c : r) {
                if (c.getCellType() != CellType.BLANK) {
                    flag = true;
                    break;
                }
            }
            if (flag) {
                i++;
                continue;
            } else {//如果是空白行（即可能没有数据，但是有一定格式）
                if (i == sheet.getLastRowNum())//如果到了最后一行，直接将那一行remove掉
                    sheet.removeRow(r);
                else//如果还没到最后一行，则数据往上移一行
                    sheet.shiftRows(i + 1, sheet.getLastRowNum(), -1);
            }
        }
        return sheet.getLastRowNum() + 1;
    }

}