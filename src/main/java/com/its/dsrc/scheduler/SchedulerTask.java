package com.its.dsrc.scheduler;

import com.its.dsrc.repository.DsrcRepository;
import com.its.dsrc.service.DsrcCtlrService;
import com.its.dsrc.service.DsrcSectService;
import com.its.dsrc.service.UnitSystService;
import com.its.dsrc.vo.voDsrcCtlr;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.PreDestroy;
import java.text.ParseException;
import java.util.Calendar;
import java.util.Map;

@Slf4j
@EnableScheduling
@Component
public class SchedulerTask {

    private int sttsMin;

    //@Autowired
    //private StandardEnvironment environment;

    @Autowired
    private UnitSystService unitSystService;
    @Autowired
    private DsrcCtlrService rseCtlrService;
    @Autowired
    private DsrcSectService dsrcSectService;

    //10초마다 properties 읽어와서 배치 설정 바꿈
    /*@Scheduled(fixedDelayString = "10000")
    public void ReloadConfigValue() {
        MutablePropertySources propertySources = environment.getPropertySources();
        log.info("{}", environment.toString());
        *//*
        PropertySource<?> resourcePropertySource = propertySources.get("class path resource [config.properties]");
        Properties properties = new Properties();
        InputStream inputStream = getClass().getResourceAsStream("classpath:/application.properties");
        try {
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        }
        try {
            inputStream.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        propertySources.replace("class path resource [config.properties]", new PropertiesPropertySource("class path resource [config.properties]", properties));
        log.info("{}", properties.toString());*//*
    }
*/
    //@Scheduled(cron = "*/10 * * * * *") //==> 10초마다 실행
    //@Scheduled(cron = "*/20 * * * * *") //==> 20초마다 실행
    @Scheduled(cron = "2 * * * * ?")  // 1분주기 작업 실행
    public void OneMinuteJobSchedule() throws ParseException {
        log.info("OneMinuteJobSchedule :: start. {}", Thread.currentThread().getName());

        //DsrcServerConfig serverConfig = (DsrcServerConfig) BeanUtils.getBeans(DsrcServerConfig.class);
        for (Map.Entry<String, voDsrcCtlr> obj : DsrcRepository.getInstance().dsrcCtlrMap.entrySet()) {

            //log.info(obj.toString());
        }
        log.info("OneMinuteJobSchedule :: ..end. {}", Thread.currentThread().getName());
    }

    @PreDestroy
    public void onShutDown() {
        Calendar cal = Calendar.getInstance();
/*
        boolean insHs = false;
        int min = cal.get(Calendar.MINUTE);
        if ((min % 5) == 0 && this.sttsMin != min) {
            insHs = true;
        }
*/
        this.unitSystService.updateUnitSyst(false);
        log.info("onShutDown updateUnitSyst");
    }
}
