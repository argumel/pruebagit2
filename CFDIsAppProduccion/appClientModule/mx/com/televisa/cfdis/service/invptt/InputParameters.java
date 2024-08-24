
package mx.com.televisa.cfdis.service.invptt;

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
 *         &lt;element name="NumeroFactura" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RfcProveedor" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RfcEmpresa" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "numeroFactura",
    "rfcProveedor",
    "rfcEmpresa"
})
@XmlRootElement(name = "InputParameters")
public class InputParameters {

    @XmlElement(name = "NumeroFactura", required = true)
    protected String numeroFactura;
    @XmlElement(name = "RfcProveedor", required = true)
    protected String rfcProveedor;
    @XmlElement(name = "RfcEmpresa", required = true)
    protected String rfcEmpresa;

    /**
     * Obtiene el valor de la propiedad numeroFactura.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNumeroFactura() {
        return numeroFactura;
    }

    /**
     * Define el valor de la propiedad numeroFactura.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNumeroFactura(String value) {
        this.numeroFactura = value;
    }

    /**
     * Obtiene el valor de la propiedad rfcProveedor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRfcProveedor() {
        return rfcProveedor;
    }

    /**
     * Define el valor de la propiedad rfcProveedor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRfcProveedor(String value) {
        this.rfcProveedor = value;
    }

    /**
     * Obtiene el valor de la propiedad rfcEmpresa.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRfcEmpresa() {
        return rfcEmpresa;
    }

    /**
     * Define el valor de la propiedad rfcEmpresa.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRfcEmpresa(String value) {
        this.rfcEmpresa = value;
    }

}
