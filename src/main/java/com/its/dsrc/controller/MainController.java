package com.its.dsrc.controller;

import com.its.dsrc.config.DsrcServerConfig;
import com.its.dsrc.repository.DsrcRepository;
import com.its.dsrc.service.FileService;
import com.its.dsrc.vo.voDsrcCtlr;
import com.its.dsrc.vo.voFileInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
@Controller
public class MainController {
    @Autowired
    ResourceLoader resourceLoader;
    @Autowired
    private DsrcServerConfig dsrcServerConfig;

    @Autowired
    private FileService fileService;

    @RequestMapping({"/", "index"})
    public String index(Model model, HttpServletRequest request){
        //로그파일 가져오기
        List<voFileInfo> fileArr = fileService.getLogList();

        model.addAttribute("ServerConfig",dsrcServerConfig);
        model.addAttribute("bootTime",new SimpleDateFormat("yyyy-MM-dd hh:mm:ss").format(DsrcRepository.getInstance().bootingDate));
        model.addAttribute("fileList",fileArr);
        model.addAttribute("session",request.getSession());
        log.info("만료시간 : {}",request.getSession().getMaxInactiveInterval());
        return "index";
    }



    @RequestMapping("login")
    public String login() {

        return "login";
    }
    @GetMapping("/logout")
    public void logout() {

    }
    @RequestMapping("/getServerDate")
    @ResponseBody
    public Map<String,Object> getServerDate(){
        Map<String,Object> map = new HashMap<>();
        map.put("serverDate",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return map;
    }

    @RequestMapping("/getControllerInfo")
    @ResponseBody
    public ConcurrentHashMap<String, voDsrcCtlr> getControllerInfo(){
        List<Map<String,voDsrcCtlr>> infoList = new ArrayList<>();

        for (Map.Entry<String, voDsrcCtlr> obj : DsrcRepository.getInstance().dsrcCtlrMap.entrySet()) {
            log.info(obj.toString());
        }
        return DsrcRepository.getInstance().dsrcCtlrMap;
    }

    @RequestMapping(value = "/getFileView",method = {RequestMethod.POST},produces = {"application/json"})
    @ResponseBody
    public String getFileView(@RequestParam("fileName") String fileName,@RequestParam("filePath") String filePath,HttpServletRequest request){

        return fileService.getView(request,fileName,filePath);

    }
    @RequestMapping("/getFileDownload")
    public ResponseEntity<Resource> getFileDownload(@RequestParam("fileName") String fileName , @RequestParam("filePath") String filePath
            , HttpSession session
            , HttpServletRequest req
            , HttpServletResponse res
            , ModelAndView mav) throws UnsupportedEncodingException {

        return fileService.fileDownload(fileName,filePath);

    }
    @RequestMapping(value = ("/getFileDelete"))
    @ResponseBody
    public List<voFileInfo> getFileDelete(@RequestParam("fileName") String fileName,@RequestParam("filePath") String filePath){

        //파일 지우기
        fileService.fileDelete(fileName,filePath);

        //파일 리스트 가져오기
        List<voFileInfo> fileArr =fileService.getLogList();

        log.info(fileArr.toString());
        return fileArr;
    }





    @RequestMapping(value = ("/disConn"))
    public void disConn(@RequestParam("id") String id){

    }
    @RequestMapping(value = ("/dsrcReset"))
    public void dsrcReset(@RequestParam("id") String id){

    }
    @RequestMapping(value = ("/countReset"))
    public void countReset(@RequestParam("id") String id){

    }
}
