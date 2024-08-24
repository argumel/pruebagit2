
package mx.com.televisa.cfdis.service.cveptt;

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
 *         &lt;element name="Error" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="Descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ExisteClave" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "error",
    "descripcion",
    "existeClave"
})
@XmlRootElement(name = "OutputElement")
public class OutputElement {

    @XmlElement(name = "Error")
    protected int error;
    @XmlElement(name = "Descripcion", required = true)
    protected String descripcion;
    @XmlElement(name = "ExisteClave")
    protected boolean existeClave;

    /**
     * Obtiene el valor de la propiedad error.
     * 
     */
    public int getError() {
        return error;
    }

    /**
     * Define el valor de la propiedad error.
     * 
     */
    public void setError(int value) {
        this.error = value;
    }

    /**
     * Obtiene el valor de la propiedad descripcion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getDescripcion() {
        return descripcion;
    }

    /**
     * Define el valor de la propiedad descripcion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setDescripcion(String value) {
        this.descripcion = value;
    }

    /**
     * Obtiene el valor de la propiedad existeClave.
     * 
     */
    public boolean isExisteClave() {
        return existeClave;
    }

    /**
     * Define el valor de la propiedad existeClave.
     * 
     */
    public void setExisteClave(boolean value) {
        this.existeClave = value;
    }

}
