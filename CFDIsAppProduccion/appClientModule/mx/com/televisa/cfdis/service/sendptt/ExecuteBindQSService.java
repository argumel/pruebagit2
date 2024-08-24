
package mx.com.televisa.cfdis.service.sendptt;

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
@WebServiceClient(name = "execute_bindQSService", targetNamespace = "http://xmlns.oracle.com/AN-DS_EDI06/SendToEDICOM/PS_SendToEdicom", wsdlLocation = "https://\"+LeerProperties.wsOIC+\"/Televisa/SendToEdicom?wsdl")
public class ExecuteBindQSService
    extends Service
{

    private final static URL EXECUTEBINDQSSERVICE_WSDL_LOCATION;
    private final static WebServiceException EXECUTEBINDQSSERVICE_EXCEPTION;
    private final static QName EXECUTEBINDQSSERVICE_QNAME = new QName("http://xmlns.oracle.com/AN-DS_EDI06/SendToEDICOM/PS_SendToEdicom", "execute_bindQSService");

    static {
        URL url = null;
        WebServiceException e = null;
        try {
            url = new URL("https://"+LeerProperties.wsOIC+"/Televisa/SendToEdicom?wsdl");
        } catch (MalformedURLException ex) {
            e = new WebServiceException(ex);
        }
        EXECUTEBINDQSSERVICE_WSDL_LOCATION = url;
        EXECUTEBINDQSSERVICE_EXCEPTION = e;
    }

    public ExecuteBindQSService() {
        super(__getWsdlLocation(), EXECUTEBINDQSSERVICE_QNAME);
    }

    public ExecuteBindQSService(WebServiceFeature... features) {
        super(__getWsdlLocation(), EXECUTEBINDQSSERVICE_QNAME, features);
    }

    public ExecuteBindQSService(URL wsdlLocation) {
        super(wsdlLocation, EXECUTEBINDQSSERVICE_QNAME);
    }

    public ExecuteBindQSService(URL wsdlLocation, WebServiceFeature... features) {
        super(wsdlLocation, EXECUTEBINDQSSERVICE_QNAME, features);
    }

    public ExecuteBindQSService(URL wsdlLocation, QName serviceName) {
        super(wsdlLocation, serviceName);
    }

    public ExecuteBindQSService(URL wsdlLocation, QName serviceName, WebServiceFeature... features) {
        super(wsdlLocation, serviceName, features);
    }

    /**
     * 
     * @return
     *     returns ExecutePtt
     */
    @WebEndpoint(name = "execute_bindQSPort")
    public ExecutePtt getExecuteBindQSPort() {
        return super.getPort(new QName("http://xmlns.oracle.com/AN-DS_EDI06/SendToEDICOM/PS_SendToEdicom", "execute_bindQSPort"), ExecutePtt.class);
    }

    /**
     * 
     * @param features
     *     A list of {@link javax.xml.ws.WebServiceFeature} to configure on the proxy.  Supported features not in the <code>features</code> parameter will have their default values.
     * @return
     *     returns ExecutePtt
     */
    @WebEndpoint(name = "execute_bindQSPort")
    public ExecutePtt getExecuteBindQSPort(WebServiceFeature... features) {
        return super.getPort(new QName("http://xmlns.oracle.com/AN-DS_EDI06/SendToEDICOM/PS_SendToEdicom", "execute_bindQSPort"), ExecutePtt.class, features);
    }

    private static URL __getWsdlLocation() {
        if (EXECUTEBINDQSSERVICE_EXCEPTION!= null) {
            throw EXECUTEBINDQSSERVICE_EXCEPTION;
        }
        return EXECUTEBINDQSSERVICE_WSDL_LOCATION;
    }

}
