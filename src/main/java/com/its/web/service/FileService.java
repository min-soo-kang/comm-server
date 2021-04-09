package com.its.web.service;


import com.its.web.vo.voFileInfo;
import lombok.extern.slf4j.Slf4j;
import net.sf.json.JSONObject;
import org.springframework.core.io.InputStreamResource;
import org.springframework.core.io.Resource;
import org.springframework.http.ContentDisposition;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class FileService {
    final private String logDir= "/logs/";
    final private String sysDir= System.getProperty("user.dir");
    final private String[] exceptDir ={"backup"};
    private int id= 1;

    public String getView(HttpServletRequest request,String fileName,String filepath){
        long preEndPoint = request.getParameter("preEndPoint") == null ? 0 : Long.parseLong(request.getParameter("preEndPoint") + "");
        StringBuilder sb = new StringBuilder();
        long startPoint = 0;
        long endPoint = 0;
        RandomAccessFile file = null;
        FileReader rw =null;
        try{

            file = new RandomAccessFile(System.getProperty("user.dir")+filepath+fileName, "r");
            rw = new FileReader(System.getProperty("user.dir")+filepath+fileName);
            BufferedReader br = new BufferedReader( rw );

            endPoint = file.length();
            startPoint = preEndPoint > 0 ? preEndPoint : endPoint < 2000 ? 0 : endPoint - 2000;
            file.seek(startPoint);

            String str;
            while ((str = file.readLine()) != null) {
                byte[] b = str.getBytes("iso-8859-1");
                str = new String(b, "UTF-8");
                sb.append(str);
                sb.append("<br>");
                endPoint = file.getFilePointer();
                file.seek(endPoint);
            }


            JSONObject json = new JSONObject();
            json.put("endPoint",endPoint);
            json.put("log",sb.toString());
            return json.toString();


        }catch(FileNotFoundException fnf){
            //System.out.println(fnf);
            log.error(fnf.getMessage());
        }catch (IOException e) {
            log.error(e.getMessage());
            //System.out.println(e);
        }finally {
            try{
                file.close();
            }catch (Exception e){}
        }

        return null;

    }
    public ResponseEntity<Resource> fileDownload(String fileName, String filePath){
        try {
            Path path = Paths.get(System.getProperty("user.dir")+filePath+ fileName);

            String contentType = "application/download";
            HttpHeaders headers = new HttpHeaders();
            headers.add(HttpHeaders.CONTENT_TYPE, contentType);
            headers.setContentDisposition(ContentDisposition.parse("attachment;" + " filename=\"" + fileName + "\";"));
            Resource resource = null;

            resource = new InputStreamResource(Files.newInputStream(path));

            return new ResponseEntity<>(resource, headers, HttpStatus.OK);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void fileDelete(String fileName,String filePath) {
        log.info(filePath+fileName+" Delete..");
        File file = new File(this.sysDir+filePath+fileName);
        if( file.exists() ){
            if(file.delete()){
                log.info(filePath+fileName+" Delete Success...");
            }else{
                log.info(filePath+fileName+" Delete Fail");
            }
        }else{
            log.info(filePath+fileName+" Not exists...");
        }
    }

    public List<voFileInfo> getLogFiles() {
        id=1;
        voFileInfo rootvo = new voFileInfo();
        rootvo.setId(id);
        rootvo.setType("dir");
        id++;
        rootvo.setFileName("logs");
        rootvo.setFileInfos(getFiles(sysDir,logDir));
        List<voFileInfo> fileArr =new ArrayList<>();
        fileArr.add(rootvo);
        return fileArr;
    }



    private List<voFileInfo> getFiles(String sysDir, String logDir) {
        int rootId=id-1;
        log.info(sysDir +" " + logDir);
        List<voFileInfo> subArr = new ArrayList<>();

        File dirFile = new File(sysDir,logDir);
        File[] fileList = dirFile.listFiles();


        for(File file: fileList) {

            voFileInfo info = new voFileInfo();

            if (file.isDirectory()) {
                for (String dir : exceptDir) {
                    if (dir.equals(file.getName())) {
                        break;
                    } else {
                        String subDir = file.getPath().substring(file.getPath().indexOf(logDir.replaceAll("/","")),file.getPath().length());
                        info.setId(id);
                        id++;
                        info.setFilePath(File.separator + subDir);

                        info.setFileName(file.getName());
                        info.setFileSize(file.length());
                        info.setType("dir");


                        info.setFileInfos(getFiles(sysDir,  subDir));
                        info.setParent(rootId);
                        log.info(info.toString());
                        subArr.add(info);

                    }
                }
            }
        }
        for(File file: fileList) {
            voFileInfo info = new voFileInfo();

            if (file.isFile() && file.getName().contains(".log")) {
                info.setId(id);
                id++;
                info.setFileName(file.getName());
                info.setFileSize(file.length());
                String subDir = file.getPath().substring(file.getPath().indexOf(logDir.replaceAll("/","")),file.getPath().length()-file.getName().length()).replaceAll("\\\\","/");

                if(File.separator.equals("\\")){
                    subDir = "/"+subDir;
                }

                info.setFilePath( subDir);
                info.setType("log");
                info.setParent(rootId);
                //log.info(info.toString());
                subArr.add(info);
            }
        }
        return subArr;
    }



}
