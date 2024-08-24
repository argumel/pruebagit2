
package mx.com.televisa.cfdis.service.sendasimiptt;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Java class for anonymous complex type.
 *
 * <p>The following schema fragment specifies the expected content contained within this class.
 *
 * <pre>
 * &lt;complexType>
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SendFileEDICOM" type="{http://xmlns.mx.com.televisa/BusinessObjects/SendEDICOM}SendFileEDICOMType"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "sendFileEDICOM" })
@XmlRootElement(name = "SendFileEDICOMReqMsg")
public class SendFileEDICOMReqMsg {

    @XmlElement(name = "SendFileEDICOM", required = true)
    protected SendFileEDICOMType sendFileEDICOM;

    /**
     * Gets the value of the sendFileEDICOM property.
     *
     * @return
     *     possible object is
     *     {@link SendFileEDICOMType }
     *
     */
    public SendFileEDICOMType getSendFileEDICOM() {
        return sendFileEDICOM;
    }

    /**
     * Sets the value of the sendFileEDICOM property.
     *
     * @param value
     *     allowed object is
     *     {@link SendFileEDICOMType }
     *
     */
    public void setSendFileEDICOM(SendFileEDICOMType value) {
        this.sendFileEDICOM = value;
    }

}
