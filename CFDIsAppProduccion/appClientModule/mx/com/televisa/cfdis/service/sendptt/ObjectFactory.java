
package mx.com.televisa.cfdis.service.sendptt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.com.televisa.cfdis.service.sendptt package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _OpaqueElement_QNAME = new QName("http://xmlns.mx.com.televisa/BusinessObjects/Core/CBO/SendToEdicom", "opaqueElement");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.com.televisa.cfdis.service.sendptt
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link Responses }
     * 
     */
    public Responses createResponses() {
        return new Responses();
    }

    /**
     * Create an instance of {@link Responses.Response }
     * 
     */
    public Responses.Response createResponsesResponse() {
        return new Responses.Response();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link String }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.mx.com.televisa/BusinessObjects/Core/CBO/SendToEdicom", name = "opaqueElement")
    public JAXBElement<String> createOpaqueElement(String value) {
        return new JAXBElement<String>(_OpaqueElement_QNAME, String.class, null, value);
    }

}
