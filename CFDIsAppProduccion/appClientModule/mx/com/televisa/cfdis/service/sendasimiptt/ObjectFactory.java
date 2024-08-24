
package mx.com.televisa.cfdis.service.sendasimiptt;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each
 * Java content interface and Java element interface
 * generated in the televisa.com.mx.xmlns.businessobjects.sendedicom package.
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


    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: televisa.com.mx.xmlns.businessobjects.sendedicom
     *
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link SendFileEDICOMReqMsg }
     *
     */
    public SendFileEDICOMReqMsg createSendFileEDICOMReqMsg() {
        return new SendFileEDICOMReqMsg();
    }

    /**
     * Create an instance of {@link SendFileEDICOMType }
     *
     */
    public SendFileEDICOMType createSendFileEDICOMType() {
        return new SendFileEDICOMType();
    }

    /**
     * Create an instance of {@link Responses }
     *
     */
    public Responses createResponses() {
        return new Responses();
    }

    /**
     * Create an instance of {@link ResponseType }
     *
     */
    public Responses.ResponseType createResponseType() {
        return new Responses.ResponseType();
    }

}
