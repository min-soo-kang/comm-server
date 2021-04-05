package com.its.dsrc;

import com.its.dsrc.app.BeanUtils;
import com.its.dsrc.config.DsrcServerConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Slf4j
@SpringBootApplication
@Configuration
//java -jar app.jar --spring.config.location=classpath:/another-location.properties
//java -jar app.jar --spring.config.location=config/*/
//java -jar app.jar --property="value"
//java -Dproperty.name="value" -jar app.jar
//export name=value
//java -jar app.jar
//JAVA_OPTS="$JAVA_OPTS -Dspring.profiles.active=prod"

//@PropertySource("classpath:foo.properties")
//@PropertySource("classpath:bar.properties")
//@PropertySources({
//        @PropertySource("classpath:foo.properties"),
//        @PropertySource("classpath:bar.properties")
//})
@EnableAsync
public class CommunicationServerApplication implements CommandLineRunner {

    public static void main(String[] args) {

        //System.out.println(System.getProperty("program.name"));

        // java -jar -Dlogback-config=xxx.xml ....
        // logback config file setting
        /*String userDir = System.getProperty("user.dir");
        String logbackConfigFileName = userDir + "/conf/dsrc-comm-server-log.xml";
        System.setProperty(ContextInitializer.CONFIG_FILE_PROPERTY, logbackConfigFileName);*/

        SpringApplication application = new SpringApplication(CommunicationServerApplication.class);
        //application.addListeners(new LogoutListener());

        application.run(args);


        //SpringApplication.run(CommunicationServerApplication.class, args);
    }

    @Override
    public void run(String... args) throws Exception {

        DsrcServerConfig serverConfig = (DsrcServerConfig) BeanUtils.getBean(DsrcServerConfig.class);
        log.info("");
        log.info("");
        log.info("************************************************************************************");
        log.info("**                                                                                **");
        log.info("**                         Intelligent Traffic System                             **");
        log.info("**                  DSRC(ASN.1) Communication Server Program.                     **");
        log.info("**                                                                                **");
        log.info("**                                                                   [ver.1.0]    **");
        log.info("**                                                                   {}       **", serverConfig.getProcessId());
        log.info("************************************************************************************");    }

    /*@Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {
            System.out.println("Let's inspect the beans provided by Spring Boot:");
            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                System.out.println(beanName);
            }
        };
    }*/

}
