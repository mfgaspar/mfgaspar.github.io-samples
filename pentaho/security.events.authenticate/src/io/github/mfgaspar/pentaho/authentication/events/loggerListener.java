package io.github.mfgaspar.pentaho.authentication.events;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import org.springframework.context.ApplicationListener;
import org.springframework.context.ApplicationEvent;
import org.springframework.security.access.event.AuthorizationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationFailureBadCredentialsEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.web.authentication.WebAuthenticationDetails;
import org.springframework.web.context.support.ServletRequestHandledEvent;
import org.springframework.security.web.session.HttpSessionDestroyedEvent;
import org.springframework.security.authentication.event.LoggerListener;

import org.pentaho.platform.engine.security.event.PentahoLoggerListener;

public class loggerListener extends PentahoLoggerListener implements ApplicationListener {

    private static String messageTemplate = "(%s|%s|%s|%s)";

    private static Boolean enablePentahoLoggerListener = false;

    private static Boolean enableNoAuthenticationEvents = true;
    private static String noAuthenticationEventsId = "NO-AUTHENTICATION";

    private static Boolean enableBadCredentialEvents = true;
    private static String badCredentialEventsId = "AUTHENTICATION-FAILED";

    private static Boolean enableSessionCreatedEvents = true;
    private static String sessionCreatedEventsId = "SESSION-START";

    private static Boolean enableSessionDestroyedEvents = true;
    private static String sessionDestroyedEventsId = "SESSION-END";

    private static Boolean enableTraceServletEvents = false;
    private static Boolean enableTraceOtherEvents = false;

    private static Log LOG = LogFactory.getLog(loggerListener.class);

    public loggerListener() {
        super();
    }

    public loggerListener( LoggerListener loggerListener ) {
        super(loggerListener);
    }

    @Override
    public void onApplicationEvent(ApplicationEvent e) {

        if (enablePentahoLoggerListener) {
            super.onApplicationEvent(e);
        }

        if (enableNoAuthenticationEvents && e instanceof AuthorizationFailureEvent) {
            AuthorizationFailureEvent event = (AuthorizationFailureEvent) e;
            info(event.getAuthentication(), noAuthenticationEventsId);

        } else if (enableBadCredentialEvents && e instanceof AuthenticationFailureBadCredentialsEvent) {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) e;
            info(event.getAuthentication(), badCredentialEventsId);

        } else if (enableSessionCreatedEvents && e instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent event = (AuthenticationSuccessEvent) e;
            info(event.getAuthentication(), sessionCreatedEventsId);

        } else if (enableSessionDestroyedEvents && e instanceof HttpSessionDestroyedEvent) {
            HttpSessionDestroyedEvent event = (HttpSessionDestroyedEvent) e;
            SecurityContext context = event.getSecurityContexts().get(0);
            info(context.getAuthentication(), sessionDestroyedEventsId);

        } else if (enableTraceServletEvents && e instanceof ServletRequestHandledEvent) {
            ServletRequestHandledEvent event = (ServletRequestHandledEvent) e;
            LOG.trace("request[ USR=" + event.getUserName() + ", " + event.getMethod() + ", URL=" + event.getRequestUrl() + ", client=" + event.getClientAddress() + "]");

        } else if (enableTraceOtherEvents) {
            LOG.trace("Event Received: " + e.getClass().getCanonicalName());
            LOG.trace("event: " + e);
        }

    }

    private void info(Authentication auth, String eventType) {
        WebAuthenticationDetails details = (WebAuthenticationDetails) auth.getDetails();
        String message = String.format(messageTemplate, eventType, details.getRemoteAddress(), auth.getName(), ((WebAuthenticationDetails) auth.getDetails()).getSessionId());
        LOG.info(message);
    }

    public static String getMessageTemplate() {
        return messageTemplate;
    }

    public static void setMessageTemplate(String messageTemplate) {
        loggerListener.messageTemplate = messageTemplate;
    }

    public static Boolean getEnablePentahoLoggerListener() {
        return enablePentahoLoggerListener;
    }

    public static void setEnablePentahoLoggerListener(Boolean enablePentahoLoggerListener) {
        loggerListener.enablePentahoLoggerListener = enablePentahoLoggerListener;
    }

    public static Boolean getEnableNoAuthenticationEvents() {
        return enableNoAuthenticationEvents;
    }

    public static void setEnableNoAuthenticationEvents(Boolean enableNoAuthenticationEvents) {
        loggerListener.enableNoAuthenticationEvents = enableNoAuthenticationEvents;
    }

    public static Boolean getEnableBadCredentialEvents() {
        return enableBadCredentialEvents;
    }

    public static void setEnableBadCredentialEvents(Boolean enableBadCredentialEvents) {
        loggerListener.enableBadCredentialEvents = enableBadCredentialEvents;
    }

    public static Boolean getEnableSessionCreatedEvents() {
        return enableSessionCreatedEvents;
    }

    public static void setEnableSessionCreatedEvents(Boolean enableSessionCreatedEvents) {
        loggerListener.enableSessionCreatedEvents = enableSessionCreatedEvents;
    }

    public static Boolean getEnableSessionDestroyedEvents() {
        return enableSessionDestroyedEvents;
    }

    public static void setEnableSessionDestroyedEvents(Boolean enableSessionDestroyedEvents) {
        loggerListener.enableSessionDestroyedEvents = enableSessionDestroyedEvents;
    }

    public static Boolean getEnableTraceServletEvents() {
        return enableTraceServletEvents;
    }

    public static void setEnableTraceServletEvents(Boolean enableTraceServletEvents) {
        loggerListener.enableTraceServletEvents = enableTraceServletEvents;
    }

    public static Boolean getEnableTraceOtherEvents() {
        return enableTraceOtherEvents;
    }

    public static void setEnableTraceOtherEvents(Boolean enableTraceOtherEvents) {
        loggerListener.enableTraceOtherEvents = enableTraceOtherEvents;
    }

    public static String getNoAuthenticationEventsId() {
        return noAuthenticationEventsId;
    }

    public static void setNoAuthenticationEventsId(String noAuthenticationEventsId) {
        loggerListener.noAuthenticationEventsId = noAuthenticationEventsId;
    }

    public static String getBadCredentialEventsId() {
        return badCredentialEventsId;
    }

    public static void setBadCredentialEventsId(String badCredentialEventsId) {
        loggerListener.badCredentialEventsId = badCredentialEventsId;
    }

    public static String getSessionCreatedEventsId() {
        return sessionCreatedEventsId;
    }

    public static void setSessionCreatedEventsId(String sessionCreatedEventsId) {
        loggerListener.sessionCreatedEventsId = sessionCreatedEventsId;
    }

    public static String getSessionDestroyedEventsId() {
        return sessionDestroyedEventsId;
    }

    public static void setSessionDestroyedEventsId(String sessionDestroyedEventsId) {
        loggerListener.sessionDestroyedEventsId = sessionDestroyedEventsId;
    }
}
