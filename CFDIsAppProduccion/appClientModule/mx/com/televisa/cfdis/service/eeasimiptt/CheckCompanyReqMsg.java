
package mx.com.televisa.cfdis.service.eeasimiptt;

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
 *         &lt;element name="CheckCompany" type="{http://xmlns.mx.com.televisa/BusinessServices/CheckCompanyExistence}CheckCompanyType"/>
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
    "checkCompany"
})
@XmlRootElement(name = "CheckCompanyReqMsg")
public class CheckCompanyReqMsg {

    @XmlElement(name = "CheckCompany", required = true)
    protected CheckCompanyType checkCompany;

    /**
     * Obtiene el valor de la propiedad checkCompany.
     * 
     * @return
     *     possible object is
     *     {@link CheckCompanyType }
     *     
     */
    public CheckCompanyType getCheckCompany() {
        return checkCompany;
    }

    /**
     * Define el valor de la propiedad checkCompany.
     * 
     * @param value
     *     allowed object is
     *     {@link CheckCompanyType }
     *     
     */
    public void setCheckCompany(CheckCompanyType value) {
        this.checkCompany = value;
    }

}
