package com.its.web.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.its.dsrc.config.DsrcServerConfig;
import com.its.dsrc.repository.DsrcRepository;
import com.its.dsrc.vo.voDsrcCtlr;
import com.its.web.service.FileService;
import com.its.web.vo.voFileInfo;
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
import java.io.IOException;
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

    @RequestMapping({"/", "/index"})
    public String index(Model model, HttpServletRequest request){

        model.addAttribute("ServerConfig",this.dsrcServerConfig);
        model.addAttribute("ServerTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return "index";
    }
    @RequestMapping(value = {"/controller"})
    public String controller(Model model, HttpServletRequest request){
        model.addAttribute("ServerConfig",this.dsrcServerConfig);
        model.addAttribute("list",DsrcRepository.getInstance().dsrcCtlrMap.entrySet());
        model.addAttribute("ServerTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return "controller";
    }

    @RequestMapping("/log")
    public String logtest(Model model, HttpServletRequest request){

        List<voFileInfo> info = fileService.getLogFiles();
        //JSON형식으로 바꿔주기
        ObjectMapper mapper = new ObjectMapper();
        String jsonList="";
        try { jsonList = mapper.writeValueAsString(info); } catch (IOException e) { e.printStackTrace(); }

        model.addAttribute("ServerConfig",this.dsrcServerConfig);
        model.addAttribute("fileList",jsonList);
        model.addAttribute("ServerTime",new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(new Date()));
        return "log";
    }


    @RequestMapping("/login")
    public String login(Model model) {
        log.info("컨트롤러 로그인");
        model.addAttribute("ServerConfig",this.dsrcServerConfig);
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
        log.info("getFileView");
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
    public List<voFileInfo> getFileDelete(@RequestParam("fileName") String fileName, @RequestParam("filePath") String filePath){

        //파일 지우기
        fileService.fileDelete(fileName,filePath);

        //파일 리스트 가져오기
        List<voFileInfo> fileArr =fileService.getLogFiles();

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
    @RequestMapping(value = ("/allDisConn"))
    public void allDisConn(){

    }
    @RequestMapping(value = ("/allConnCountReset"))
    public void allConnCountReset(){

    }
}
