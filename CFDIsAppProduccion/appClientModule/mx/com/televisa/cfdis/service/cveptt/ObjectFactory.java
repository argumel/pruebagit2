
package mx.com.televisa.cfdis.service.cveptt;

import javax.xml.bind.annotation.XmlRegistry;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.com.televisa.cfdis.service.cveptt package. 
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
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.com.televisa.cfdis.service.cveptt
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link IcsTrackingContext }
     * 
     */
    public IcsTrackingContext createIcsTrackingContext() {
        return new IcsTrackingContext();
    }

    /**
     * Create an instance of {@link TrackingPropertyType }
     * 
     */
    public TrackingPropertyType createTrackingPropertyType() {
        return new TrackingPropertyType();
    }

    /**
     * Create an instance of {@link IcsInsightContext }
     * 
     */
    public IcsInsightContext createIcsInsightContext() {
        return new IcsInsightContext();
    }

    /**
     * Create an instance of {@link InsightPropertyType }
     * 
     */
    public InsightPropertyType createInsightPropertyType() {
        return new InsightPropertyType();
    }

    /**
     * Create an instance of {@link OutputElement }
     * 
     */
    public OutputElement createOutputElement() {
        return new OutputElement();
    }

    /**
     * Create an instance of {@link InputElement }
     * 
     */
    public InputElement createInputElement() {
        return new InputElement();
    }

}
