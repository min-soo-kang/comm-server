package com.its.web.security;


import lombok.extern.slf4j.Slf4j;
import org.springframework.security.web.session.HttpSessionEventPublisher;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;

@Slf4j
public class SessionListener extends HttpSessionEventPublisher {
    
    //세션이 생성될때 호출
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        HttpSession session = se.getSession();

        if (session != null) {
            session.setMaxInactiveInterval(60*5);
            //log.info("sessionCreated sessionid: {}, setMaxInactiveInterval: {}, isNew: {} time:{}", session.getId(), session.getMaxInactiveInterval(), session.isNew(),session.getAttribute("time"));
        }
        super.sessionCreated(se);
    }
    //세션이 만료될때 호출
    @Override
    public  void sessionDestroyed(HttpSessionEvent se) {
        HttpSession session = se.getSession();
        if (session != null) {
            //log.info("dissconn sessionid: {}, setMaxInactiveInterval: {}, isNew: {}", session.getId(), session.getMaxInactiveInterval(), session.isNew());

        }

        super.sessionDestroyed(se);
    }
}

/*

    @Bean // Http Listener
    public HttpSessionListener httpSessionListener() {
        return new HttpSessionListener() {

            @Override
            public void sessionCreated(HttpSessionEvent se) {

            }
        };
    }

@Slf4j
public class SessionListener implements HttpSessionListener {

    public static SessionListener sessionListener = null;
    private static Hashtable loginSessionList = new Hashtable();

    */
/**
     * 싱글톤 생성
     * @return
     *//*

    public static synchronized SessionListener getInstance() {
        if(sessionListener == null) {
            sessionListener = new SessionListener();
        }
        return sessionListener;
    }

    */
/**
     * session.setAttribute 실행 되는 순간 같은 sessionId 일경우 1회만 실행
     * @param httpSessionEvent
     *//*

    @Override
    public void sessionCreated(HttpSessionEvent httpSessionEvent) {
        log.info("sessionCreated -> {}", httpSessionEvent.getSession().getAttribute("userId"));
    }

    */
/**
     * session 이 소멸되는 시점에 실행, 기본 단위는 초(1분 미만은 설정할 수 없음)
     * @param httpSessionEvent
     *//*

    @Override
    public void sessionDestroyed(HttpSessionEvent httpSessionEvent) {
        log.info("sessionDestroyed 실행");
        HttpSession session = httpSessionEvent.getSession();

        String userId = (String) session.getAttribute("userId");

        //로그아웃 유저 삭제
        synchronized(loginSessionList){
            loginSessionList.remove(httpSessionEvent.getSession().getId());
        }

        if(userId != null){
            this.updateUserCloseTime(userId);
        }

        currentSessionList();
    }

    */
/**
     * 현제 HashTable에 담겨 있는 유저 리스트, 즉 session list
     *//*

    private void currentSessionList(){
        Enumeration elements = loginSessionList.elements();
        HttpSession session = null;
        while (elements.hasMoreElements()){
            session = (HttpSession)elements.nextElement();

            String userId = (String)session.getAttribute("userId");
            log.info("currentSessionUserList -> userId {} ", userId);
            //log.info("currentSessionUserList -> sessionId {} ", session.getId());
            //log.info("currentSessionUserList -> hashtable SessionList {} ", loginSessionList.get(session.getId()));
        }
    }

    */
/**
     * session 생성
     * @param request
     * @param value
     *//*

    public void setSession(HttpServletRequest request, String value){
        log.info("setSession 실행");
        HttpSession session = request.getSession();
        session.setAttribute("userId", value);
        session.setMaxInactiveInterval(2);

        //HashMap에 Login에 성공한 유저 담기
        synchronized(loginSessionList){
            loginSessionList.put(session.getId(), session);
        }
        currentSessionList();
    }

    */
/**
     * session 삭제
     * 세션이 remove 되면 destroyed 함수 실행
     * @param request
     *//*

    public void removeSession(HttpServletRequest request){
        log.info("removeSession 실행");

        HttpSession session = request.getSession();
        String userId = (String)session.getAttribute("userId");

        session.removeAttribute("userId");
        session.invalidate();

        if(userId != null){
            this.updateUserCloseTime(userId);
        }
    }

    */
/**
     * 유저 나간 시간 업데이트
     * @param userId
     *//*

    private void updateUserCloseTime(String userId) {
        log.info("updateUserCloseTime {} ", userId);
        //호출부에서 NULL 검사
        //업데이트 로직
    }

    */
/**
     * 현재 로그인한 유저가 이미 존재 하는지 확인
     * @param request
     * @param loginUserId
     * @return boolean
     *//*

    public boolean isLoginUser(HttpServletRequest request, String loginUserId){
        Enumeration elements = loginSessionList.elements();
        HttpSession session = null;
        while (elements.hasMoreElements()){
            session = (HttpSession)elements.nextElement();
            String userId = (String)session.getAttribute("userId");
            if(loginUserId.equals(userId) && (!session.getId().equals(request.getSession().getId()))){
                return true;
            }
        }
        return false;
    }

}
*/
