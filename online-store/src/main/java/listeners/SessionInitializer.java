package listeners;

import servlets.Localizator;

import javax.servlet.annotation.WebListener;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

/**
 * Created by o_grigorev on 23.05.2016.
 */

@WebListener
public class SessionInitializer implements HttpSessionListener {
    @Override
    public void sessionCreated(HttpSessionEvent se) {
        Localizator.setLocale(se.getSession(), "en");
    }
}
