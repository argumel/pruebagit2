
package mx.com.televisa.cfdis.service.eeasimiptt;

import java.net.MalformedURLException;
import java.net.URL;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;
import javax.xml.ws.WebEndpoint;
import javax.xml.ws.WebServiceClient;
import javax.xml.ws.WebServiceException;
import javax.xml.ws.WebServiceFeature;

import mx.com.televisa.cfdis.util.LeerProperties;


/**
 * OSB Service
 * 
 * This class was generated by the JAX-WS RI.
 * JAX-WS RI 2.2.9-b130926.1035
 * Generated source version: 2.2
 * 
 */
@WebServiceClient(name = "CheckCompanyExistenceProcess", targetNamespace = "http://xmlns.mx.com.televisa/BusinessProcessService/CheckCompanyExistence/CheckCompany/V1", wsdlLocation = "https://\"+LeerProperties.wsOIC+\"/CheckCompanyRingingAssimilate?wsdl")
public class CheckCompanyExistenceProcess
    extends Service
{

    private final static URL CHECKCOMPANYEXISTENCEPROCESS_WSDL_LOCATION;
    private final static WebServiceException CHECKCOMPANYEXISTENCEPROCESS_EXCEPTION;
    private final static QName CHECKCOMPANYEXISTENCEPROCESS_QNAME = new QName("http://xmlns.mx.com.televisa/BusinessProcessService/CheckCompanyExistence/CheckCompany/V1", "CheckCompanyExistenceProcess");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://"+LeerProperties.wsOIC+"/CheckCompanyRingingAssimilate?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        CHECKCOMPANYEXISTENCEPROCESS_WSDL_LOCATION = url;
        CHECKCOMPANYEXISTENCEPROCESS_EXCEPTION = e;
    }

    public CheckCompanyExistenceProcess() {
        super(__getWsdlLocation(), CHECKCOMPANYEXISTENCEPROCESS_QNAME);
    }

    public CheckCompanyExistenceProcess(WebServiceFeature... features) {
        super(__getWsdlLocation(), CHECKCOMPANYEXISTENCEPROCESS_QNAME, features);
    }

    public CheckCompanyExistenceProcess(URL wsdlLocation) {
        super(wsdlLocation, CHECKCOMPANYEXISTENCEPROCESS_QNAME);
    }

    public CheckCompanyExistenceProcess(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, CHECKCOMPANYEXISTENCEPROCESS_QNAME, features);
    }

    public CheckCompanyExistenceProcess(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public CheckCompanyExistenceProcess(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns CheckCompanyExistenceService
     */
    @WebEndpoint(name = "CheckCompanyExistenceService")
    public CheckCompanyExistenceService getCheckCompanyExistenceService() {
        return super.getPort(new QName("http://xmlns.mx.com.televisa/BusinessProcessService/CheckCompanyExistence/CheckCompany/V1", "CheckCompanyExistenceService"), CheckCompanyExistenceService.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns CheckCompanyExistenceService
     */
    @WebEndpoint(name = "CheckCompanyExistenceService")
    public CheckCompanyExistenceService getCheckCompanyExistenceService(WebServiceFeature... features) {
        return super.getPort(new QName("http://xmlns.mx.com.televisa/BusinessProcessService/CheckCompanyExistence/CheckCompany/V1", "CheckCompanyExistenceService"), CheckCompanyExistenceService.class, features);
    }

    private static URL __getWsdlLocation() {
        if (CHECKCOMPANYEXISTENCEPROCESS_EXCEPTION!= null) {
            throw CHECKCOMPANYEXISTENCEPROCESS_EXCEPTION;
        }
        return CHECKCOMPANYEXISTENCEPROCESS_WSDL_LOCATION;
    }

}
