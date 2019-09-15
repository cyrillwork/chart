package com.cyrillwork.chart.service;

import com.cyrillwork.chart.properties.MainProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.security.core.session.SessionInformation;
import org.springframework.security.core.session.SessionRegistry;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContext;
import java.util.ArrayList;
import java.util.List;

@Service
public class SessionService {
    @Autowired
    private ServletContext servletContext;

    @Autowired
    private MainProperties mainProperties;

    public Iterable<SessionInformation> getAllSessions(){
        List<SessionInformation> arraySessions = new ArrayList<>();
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        SessionRegistry registry = (SessionRegistry) context.getBean("sessionRegistry");

        for(Object i: registry.getAllPrincipals())
        {
            arraySessions.addAll(registry.getAllSessions(i, false));
        }

        return arraySessions;
    }

    public void killExpiredSession(String id) {
        try
        {
            HttpHeaders requestHeaders = new HttpHeaders();
            requestHeaders.add("Cookie", "JSESSIONID=" + id);
            HttpEntity requestEntity = new HttpEntity(null, requestHeaders);
            RestTemplate rt = new RestTemplate();
            rt.exchange(String.format("http://%s:%d", mainProperties.getHost(), mainProperties.getPort())
                    , HttpMethod.GET
                    , requestEntity, String.class);
        } catch (Exception ex) {} //для простоты не допустим никаких исключений
    }

    public boolean exitUser(String id){
        boolean result = false;
        WebApplicationContext context = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        SessionRegistry registry = (SessionRegistry) context.getBean("sessionRegistry");

        SessionInformation information = registry.getSessionInformation(id);
        if(information != null)
        {
            information.expireNow();
            killExpiredSession(id);
            result = true;
        }

        return result;
    }

}

