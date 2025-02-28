package com.clinique.soap.publisher;

import com.clinique.soap.services.DossierMedicalServiceImpl;
import com.clinique.soap.services.MedecinServiceImpl;
import com.clinique.soap.services.PatientServiceImpl;
import com.clinique.soap.services.PrescriptionServiceImpl;
import com.clinique.soap.handlers.CORSHandler;

import javax.xml.ws.Endpoint;
import javax.xml.ws.handler.Handler;
import java.util.ArrayList;
import java.util.List;

public class SoapEndpointPublisher {
    public static void main(String[] args) {
        // Définir l'URL de publication
        String baseUrl = "http://localhost:8081/ws";

        // Créer une liste de gestionnaires contenant le CORSHandler
        List<Handler> handlerChain = new ArrayList<>();
        handlerChain.add(new CORSHandler());

        // Publier les services avec le handler CORS
        Endpoint dossiersEndpoint = Endpoint.create(new DossierMedicalServiceImpl());
        dossiersEndpoint.getBinding().setHandlerChain(handlerChain);
        dossiersEndpoint.publish(baseUrl + "/dossiers");

        Endpoint medecinsEndpoint = Endpoint.create(new MedecinServiceImpl());
        medecinsEndpoint.getBinding().setHandlerChain(handlerChain);
        medecinsEndpoint.publish(baseUrl + "/medecins");

        Endpoint prescriptionsEndpoint = Endpoint.create(new PrescriptionServiceImpl());
        prescriptionsEndpoint.getBinding().setHandlerChain(handlerChain);
        prescriptionsEndpoint.publish(baseUrl + "/prescriptions");

        Endpoint patientsEndpoint = Endpoint.create(new PatientServiceImpl());
        patientsEndpoint.getBinding().setHandlerChain(handlerChain);
        patientsEndpoint.publish(baseUrl + "/patients");

        System.out.println("SOAP Services running on " + baseUrl + "...");
    }
}
