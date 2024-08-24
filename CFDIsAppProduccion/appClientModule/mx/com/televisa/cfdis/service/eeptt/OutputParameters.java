
package mx.com.televisa.cfdis.service.eeptt;

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
 *         &lt;element name="Existe" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
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
    "existe",
    "descripcion"
})
@XmlRootElement(name = "OutputParameters", namespace = "http://xmlns.mx.com.televisa/BusinessObjects/Core/CBO/ValidateExistCompany")
public class OutputParameters {

    @XmlElement(name = "Error", namespace = "http://xmlns.mx.com.televisa/BusinessObjects/Core/CBO/ValidateExistCompany")
    protected int error;
    @XmlElement(name = "Existe", namespace = "http://xmlns.mx.com.televisa/BusinessObjects/Core/CBO/ValidateExistCompany")
    protected boolean existe;
    @XmlElement(name = "Descripcion", namespace = "http://xmlns.mx.com.televisa/BusinessObjects/Core/CBO/ValidateExistCompany", required = true)
    protected String descripcion;

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
     * Obtiene el valor de la propiedad existe.
     * 
     */
    public boolean isExiste() {
        return existe;
    }

    /**
     * Define el valor de la propiedad existe.
     * 
     */
    public void setExiste(boolean value) {
        this.existe = value;
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

}
