package com.clinique.soap.handlers;

import javax.xml.namespace.QName;
import javax.xml.soap.SOAPMessage;
import javax.xml.ws.handler.MessageContext;
import javax.xml.ws.handler.soap.SOAPHandler;
import javax.xml.ws.handler.soap.SOAPMessageContext;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
import java.util.List;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class CORSHandler implements SOAPHandler<SOAPMessageContext> {

    private static final String FRONTEND_ORIGIN = "http://localhost:3000"; // URL de notre frontend

    @Override
    public boolean handleMessage(SOAPMessageContext context) {
        Boolean isOutbound = (Boolean) context.get(MessageContext.MESSAGE_OUTBOUND_PROPERTY);
        if (isOutbound) {
            @SuppressWarnings("unchecked")
            Map<String, List<String>> headers = (Map<String, List<String>>) context.get(MessageContext.HTTP_RESPONSE_HEADERS);
            if (headers == null) {
                headers = new HashMap<>();
                context.put(MessageContext.HTTP_RESPONSE_HEADERS, headers);
            }
            headers.put("Access-Control-Allow-Origin", Collections.singletonList(FRONTEND_ORIGIN));
            headers.put("Access-Control-Allow-Methods", Collections.singletonList("POST, GET, OPTIONS, DELETE, PUT"));
            headers.put("Access-Control-Max-Age", Collections.singletonList("3600"));
            headers.put("Access-Control-Allow-Headers", Collections.singletonList("Content-Type, Authorization, Content-Length, X-Requested-With"));
        }
        return true;
    }

    @Override
    public boolean handleFault(SOAPMessageContext context) {
        // Vous pouvez gérer les fautes ici si nécessaire
        return true;
    }

    @Override
    public void close(MessageContext context) {
        // Nettoyage si nécessaire
    }

    @Override
    public Set<QName> getHeaders() {
        return Collections.emptySet();
    }
}

