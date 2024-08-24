
package mx.com.televisa.cfdis.service.invasimiptt;

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
 *         &lt;element name="insight_property" type="{http://xmlns.oracle.com/procmon}InsightPropertyType" maxOccurs="unbounded" minOccurs="0"/>
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
    "insightProperty"
})
@XmlRootElement(name = "ics_insight_context", namespace = "http://xmlns.oracle.com/procmon")
public class IcsInsightContext {

    @XmlElement(name = "insight_property", namespace = "http://xmlns.oracle.com/procmon")
    protected List<InsightPropertyType> insightProperty;

    /**
     * Gets the value of the insightProperty property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the insightProperty property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getInsightProperty().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link InsightPropertyType }
     * 
     * 
     */
    public List<InsightPropertyType> getInsightProperty() {
        if (insightProperty == null) {
            insightProperty = new ArrayList<InsightPropertyType>();
        }
        return this.insightProperty;
    }

}
