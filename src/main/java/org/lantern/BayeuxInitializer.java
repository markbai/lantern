package org.lantern;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.UnavailableException;

import org.cometd.annotation.ServerAnnotationProcessor;
import org.cometd.bayeux.server.BayeuxServer;
import org.lantern.state.SyncService;
//import org.cometd.annotation.ServerAnnotationProcessor;

public class BayeuxInitializer extends GenericServlet {
    
    private static final long serialVersionUID = -6884888598201660314L;
    private final SyncService syncer;

    public BayeuxInitializer(final SyncService syncer) {
        this.syncer = syncer;
    }
    
    @Override
    public void init() throws ServletException {
        super.init();
        final BayeuxServer bayeux = 
            (BayeuxServer) getServletContext().getAttribute(
                BayeuxServer.ATTRIBUTE);
        if (bayeux==null) {
            throw new UnavailableException("No BayeuxServer!");
        }

        // Create extensions
        //bayeux.addExtension(new TimesyncExtension());
        //bayeux.addExtension(new AcknowledgedMessagesExtension());
        final ServerAnnotationProcessor processor = 
            new ServerAnnotationProcessor(bayeux);
        processor.process(this.syncer);
    }

    @Override
    public void service(ServletRequest request, ServletResponse response)
            throws ServletException, IOException {
        throw new ServletException();
    }
}
