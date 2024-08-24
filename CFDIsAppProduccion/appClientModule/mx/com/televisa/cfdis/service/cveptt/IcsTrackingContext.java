
package mx.com.televisa.cfdis.service.cveptt;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para anonymous complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="tracking_property" type="{http://xmlns.oracle.com/ics/tracking/ics_tracking_context.xsd}TrackingPropertyType" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = {
    "trackingProperty"
})
@XmlRootElement(name = "ics_tracking_context", namespace = "http://xmlns.oracle.com/ics/tracking/ics_tracking_context.xsd")
public class IcsTrackingContext {

    @XmlElement(name = "tracking_property", namespace = "http://xmlns.oracle.com/ics/tracking/ics_tracking_context.xsd")
    protected List<TrackingPropertyType> trackingProperty;

    /**
     * Gets the value of the trackingProperty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the trackingProperty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getTrackingProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link TrackingPropertyType }
     * 
     * 
     */
    public List<TrackingPropertyType> getTrackingProperty() {
        if (trackingProperty == null) {
            trackingProperty = new ArrayList<TrackingPropertyType>();
        }
        return this.trackingProperty;
    }

}
