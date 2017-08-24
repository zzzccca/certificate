package pc.certificate.service;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import pc.certificate.domain.Certificate;

import java.io.FileInputStream;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by wu on 17-8-23.
 */
@Service
public class UploadexlService {

    @Autowired
    private CertificateService certificateService;

    //总行数
    private int totalRows = 0;
    //总条数
    private int totalCells = 0;
    //错误信息接收器
    private String errorMsg;
    //获取总行数
    public int getTotalRows()  { return totalRows;}
    //获取总列数
    public int getTotalCells() {  return totalCells;}
    //获取错误信息
    public String getErrorInfo() { return errorMsg; }

    public void getexl(MultipartFile exl,String name)throws Exception{

        String file="./exl/"+exl.getOriginalFilename();//设置文件保存的路劲

        Files.copy(exl.getInputStream(), Paths.get(file));//将文件流copy到设置路径下

        InputStream is=new FileInputStream(file);//读取文件到输入流

        boolean a=name.matches("^.+\\.(?i)(xls)$");//正则匹配文件后缀

        Workbook wb=null;
        if(a){
            wb = new HSSFWorkbook(is);
        }
        else{//当excel是2007时
            wb = new XSSFWorkbook(is);
        }



        //得到第一个shell
        Sheet sheet=wb.getSheetAt(0);

        //得到Excel的行数
        this.totalRows=sheet.getPhysicalNumberOfRows();

        //得到Excel的列数(前提是有行数)
        if(totalRows>=1 && sheet.getRow(0) != null){
            this.totalCells=sheet.getRow(0).getPhysicalNumberOfCells();
        }

        Certificate certificate;
        //循环Excel行数,从第二行开始。标题不入库
        for(int r=1;r<totalRows;r++){
            Row row = sheet.getRow(r);
            if (row == null) continue;
            certificate = new Certificate();

            //循环Excel的列
            for(int c = 0; c <this.totalCells; c++){
                Cell cell = row.getCell(c);
                if (null != cell){
                    if(c==0){//第一行证书主键不取
                    }else if(c==1){
                        certificate.setCardid(cell.getStringCellValue());//身份证号
                    }else if(c==2){
                        certificate.setCertificatenumber(cell.getStringCellValue());//证书编号
                    }else if(c==3){
                        certificate.setBirthdate(cell.getStringCellValue());//出生年月
                    }else if(c==4){
                        certificate.setCertificatename(cell.getStringCellValue());//证书名称
                    }else if(c==5){
                        certificate.setName(cell.getStringCellValue());//姓名
                    }else if(c==6){
                        certificate.setGender(cell.getStringCellValue());//性别
                    }else if (c==7){
                        certificate.setApprovalofdate(cell.getStringCellValue());//批准日期
                    }else if (c==8){
                        certificate.setIssuanceoftime(cell.getStringCellValue());//签发时间
                    }else if (c==9){
                        certificate.setIssuanceagencies(cell.getStringCellValue());//签发单位
                    }else if (c==10){
                        certificate.setReviewcommittee(cell.getStringCellValue());//评审委员会
                    }else if (c==11){
                        certificate.setMajor(cell.getStringCellValue());//专业类别
                    }else if (c==12){
                        certificate.setLevel(cell.getStringCellValue());//级别
                    }else if (c==13){
                        certificate.setReferencenumber(cell.getStringCellValue());//文号
                    }else if (c==14){
                        certificate.setBindingtype(cell.getStringCellValue());//绑定状态
                    }else if (c==15){
                        certificate.setBindingimage(cell.getStringCellValue());//绑定申请图片
                    }else if (c==16){
                        certificate.setBindingphoto(cell.getStringCellValue());//持证人照片
                    }
                }
            }
            //添加客户
            this.certificateService.addcertificate(certificate);
        }

    }

}
