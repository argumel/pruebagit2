
package mx.com.televisa.cfdis.service.sendasimiptt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for SendFileEDICOMType complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType name="SendFileEDICOMType">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="CABFAC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CNOMINA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CNOMINADP" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "SendFileEDICOMType", propOrder = { "cabfac", "cnomina", "cnominadp" })
public class SendFileEDICOMType {

    @XmlElement(name = "CABFAC", required = true)
    protected String cabfac;
    @XmlElement(name = "CNOMINA", required = true)
    protected String cnomina;
    @XmlElement(name = "CNOMINADP", required = true)
    protected String cnominadp;

    /**
     * Gets the value of the cabfac property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCABFAC() {
        return cabfac;
    }

    /**
     * Sets the value of the cabfac property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCABFAC(String value) {
        this.cabfac = value;
    }

    /**
     * Gets the value of the cnomina property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCNOMINA() {
        return cnomina;
    }

    /**
     * Sets the value of the cnomina property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCNOMINA(String value) {
        this.cnomina = value;
    }

    /**
     * Gets the value of the cnominadp property.
     *
     * @return
     *     possible object is
     *     {@link String }
     *
     */
    public String getCNOMINADP() {
        return cnominadp;
    }

    /**
     * Sets the value of the cnominadp property.
     *
     * @param value
     *     allowed object is
     *     {@link String }
     *
     */
    public void setCNOMINADP(String value) {
        this.cnominadp = value;
    }

}
