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
import org.pentaho.platform.engine.security.event.PentahoLoggerListener;

public class loggerListener implements ApplicationListener<ApplicationEvent> {

    private static Boolean enablePentahoLoggerListener = false;

    private static Boolean enableNoAuthenticationEvents = true;
    private static Boolean enableBadCredentialEvents = true;
    private static Boolean enableSessionCreatedEvents = true;
    private static Boolean enableSessionDestroyedEvents = true;

    private static Boolean enableTraceServletEvents = false;
    private static Boolean enableTraceOtherEvents = false;

    private static Log LOG = LogFactory.getLog(loggerListener.class);

    @Override
    public void onApplicationEvent(ApplicationEvent e) {

        if (enablePentahoLoggerListener) {
            PentahoLoggerListener pll = new PentahoLoggerListener();
            pll.onApplicationEvent(e);
        }

        if (enableNoAuthenticationEvents && e instanceof AuthorizationFailureEvent) {
            AuthorizationFailureEvent event = (AuthorizationFailureEvent) e;
            Authentication userAuth = event.getAuthentication();
            WebAuthenticationDetails details = (WebAuthenticationDetails) userAuth.getDetails();
            LOG.info("NO-AUTHENTICATION [" + details.getRemoteAddress() + "|" + userAuth.getName() + "|" + ((WebAuthenticationDetails) userAuth.getDetails()).getSessionId() + "]");

        } else if (enableBadCredentialEvents && e instanceof AuthenticationFailureBadCredentialsEvent) {
            AuthenticationFailureBadCredentialsEvent event = (AuthenticationFailureBadCredentialsEvent) e;
            Authentication userAuth = event.getAuthentication();
            WebAuthenticationDetails details = (WebAuthenticationDetails) userAuth.getDetails();
            LOG.info("BAD-CREDENTIALS [" + details.getRemoteAddress() + "|" + userAuth.getName() + "|" + ((WebAuthenticationDetails) userAuth.getDetails()).getSessionId() + "]");

        } else if (enableSessionCreatedEvents && e instanceof AuthenticationSuccessEvent) {
            AuthenticationSuccessEvent s = (AuthenticationSuccessEvent) e;
            Authentication userAuth = s.getAuthentication();
            WebAuthenticationDetails details = (WebAuthenticationDetails) userAuth.getDetails();
            LOG.info("SESSION-CREATED [" + details.getRemoteAddress() + "|" + userAuth.getName() + "|" + ((WebAuthenticationDetails) userAuth.getDetails()).getSessionId() + "]");

        } else if (enableSessionDestroyedEvents && e instanceof HttpSessionDestroyedEvent) {
            HttpSessionDestroyedEvent event = (HttpSessionDestroyedEvent) e;
            SecurityContext c = event.getSecurityContexts().get(0);
            Authentication userAuth = c.getAuthentication();
            WebAuthenticationDetails details = (WebAuthenticationDetails) userAuth.getDetails();
            LOG.info("SESSION-DESTROYED [" + details.getRemoteAddress() + "|" + userAuth.getName() + "|" + ((WebAuthenticationDetails) userAuth.getDetails()).getSessionId() + "]");

        } else if (enableTraceServletEvents && e instanceof ServletRequestHandledEvent) {
            ServletRequestHandledEvent event = (ServletRequestHandledEvent) e;
            LOG.trace("request[ USR=" + event.getUserName() + ", " + event.getMethod() + ", URL=" + event.getRequestUrl() + ", client=" + event.getClientAddress() + "]");

        } else if (enableTraceOtherEvents) {
            LOG.trace("Event Received ricevuto: " + e.getClass().getCanonicalName());
            LOG.trace("event: " + e);
        }

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
}
