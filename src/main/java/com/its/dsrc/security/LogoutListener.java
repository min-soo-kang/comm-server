package com.its.dsrc.security;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.ApplicationListener;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.session.SessionDestroyedEvent;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@Slf4j
public class LogoutListener implements ApplicationListener<SessionDestroyedEvent> {

    @Override
    public void onApplicationEvent(SessionDestroyedEvent  event) {
        log.info("====================");
        log.info("Application Started");
        List<SecurityContext> securityContexts = event.getSecurityContexts();

        for (SecurityContext securityContext : securityContexts) {

        }



    }
}
