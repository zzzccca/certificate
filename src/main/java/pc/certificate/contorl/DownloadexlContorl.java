package pc.certificate.contorl;

import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import pc.certificate.domain.Expressage;
import pc.certificate.domain.User;
import pc.certificate.reop.ExpressageRepository;
import pc.certificate.reop.UserRepository;
import pc.certificate.service.DesService;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by wu on 17-8-25.
 */
@Controller
@CrossOrigin(origins = {}, methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.OPTIONS})
public class DownloadexlContorl {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private ExpressageRepository expressageRepository;

    @Autowired
    private DesService desService;


    /***
     * 创建表头
     * @param workbook
     * @param sheet
     */
    private void createTitle(XSSFWorkbook workbook, XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);
        //设置列宽，setColumnWidth的第二个参数要乘以256，这个参数的单位是1/256个字符宽度
        sheet.setColumnWidth(1, 20 * 256);
        sheet.setColumnWidth(2, 12 * 256);
        sheet.setColumnWidth(3, 17 * 256);
        sheet.setColumnWidth(4, 20 * 256);

        //设置为居中加粗
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        XSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("身份证");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("手机");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("寄件地址");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("邮政编码");
        cell.setCellStyle(style);
    }


    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getexcel")
    public void getExcel(HttpServletResponse res) throws IOException {

        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=User.xlsx");//下载后的word名
        res.flushBuffer();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("统计表");
        createTitle(workbook, sheet);
        List<User> entities = this.userRepository.findAll();

        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        for (User user : entities) {

            XSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(user.getName());
            row.createCell(1).setCellValue(this.desService.decrypt(user.getCardid()));
            row.createCell(2).setCellValue(this.desService.decrypt(user.getPhone()));
            row.createCell(3).setCellValue(user.getAddress());
            row.createCell(4).setCellValue(user.getYoubian());
            XSSFCell cell = row.createCell(5);
            rowNum++;
        }
        workbook.write(res.getOutputStream());
    }

    @SuppressWarnings({"unchecked", "rawtypes"})
    @RequestMapping("/getexpressageexl")
    public void getexpressageexl(HttpServletResponse res) throws IOException {

        res.setContentType("application/octet-stream");
        res.setHeader("Content-Disposition", "attachment;filename=Expressage.xlsx");//下载后的word名
        res.flushBuffer();

        XSSFWorkbook workbook = new XSSFWorkbook();
        XSSFSheet sheet = workbook.createSheet("统计表");
        createTitle2(workbook, sheet);
        String type = "待寄";
        List<Expressage> entities = this.expressageRepository.findByType(type);

        //新增数据行，并且设置单元格数据
        int rowNum = 1;
        for (Expressage expressage : entities) {

            String c = this.userRepository.findOne(expressage.getUserid()).getCardid();

            XSSFRow row = sheet.createRow(rowNum);
            row.createCell(0).setCellValue(expressage.getName());
            row.createCell(1).setCellValue(this.desService.decrypt(c));
            row.createCell(2).setCellValue(expressage.getCertificatename());
            row.createCell(3).setCellValue(expressage.getCertificatenumber());
            row.createCell(4).setCellValue(expressage.getPhone());
            row.createCell(5).setCellValue(expressage.getAddress());
            row.createCell(6).setCellValue(expressage.getOddnumber());
            row.createCell(7).setCellValue(expressage.getType());
            XSSFCell cell = row.createCell(8);
            rowNum++;
        }
        workbook.write(res.getOutputStream());
    }

    /***
     * 创建表头
     * @param workbook
     * @param sheet
     */
    private void createTitle2(XSSFWorkbook workbook, XSSFSheet sheet) {
        XSSFRow row = sheet.createRow(0);

        //设置为居中加粗
        XSSFCellStyle style = workbook.createCellStyle();
        XSSFFont font = workbook.createFont();
        font.setBold(true);
        style.setAlignment(HorizontalAlignment.CENTER);
        style.setFont(font);

        XSSFCell cell;
        cell = row.createCell(0);
        cell.setCellValue("姓名");
        cell.setCellStyle(style);

        cell = row.createCell(1);
        cell.setCellValue("身份证");
        cell.setCellStyle(style);

        cell = row.createCell(2);
        cell.setCellValue("证书名称");
        cell.setCellStyle(style);

        cell = row.createCell(3);
        cell.setCellValue("证书编号");
        cell.setCellStyle(style);

        cell = row.createCell(4);
        cell.setCellValue("手机");
        cell.setCellStyle(style);

        cell = row.createCell(5);
        cell.setCellValue("寄件地址");
        cell.setCellStyle(style);

        cell = row.createCell(6);
        cell.setCellValue("快递单号");
        cell.setCellStyle(style);

        cell = row.createCell(7);
        cell.setCellValue("寄件状态");
        cell.setCellStyle(style);
    }
}