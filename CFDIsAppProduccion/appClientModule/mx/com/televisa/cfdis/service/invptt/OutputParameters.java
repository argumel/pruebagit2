
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
 *         &lt;element name="Error" type="{http://www.w3.org/2001/XMLSchema}int"/>
 *         &lt;element name="ExisteFactura" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="Descripcion" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EntidadActiva" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
 *         &lt;element name="ProveedorActivo" type="{http://www.w3.org/2001/XMLSchema}boolean"/>
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
    "existeFactura",
    "descripcion",
    "entidadActiva",
    "proveedorActivo"
})
@XmlRootElement(name = "OutputParameters")
public class OutputParameters {

    @XmlElement(name = "Error")
    protected int error;
    @XmlElement(name = "ExisteFactura")
    protected boolean existeFactura;
    @XmlElement(name = "Descripcion", required = true)
    protected String descripcion;
    @XmlElement(name = "EntidadActiva")
    protected boolean entidadActiva;
    @XmlElement(name = "ProveedorActivo")
    protected boolean proveedorActivo;

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
     * Obtiene el valor de la propiedad existeFactura.
     * 
     */
    public boolean isExisteFactura() {
        return existeFactura;
    }

    /**
     * Define el valor de la propiedad existeFactura.
     * 
     */
    public void setExisteFactura(boolean value) {
        this.existeFactura = value;
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
     * Obtiene el valor de la propiedad entidadActiva.
     * 
     */
    public boolean isEntidadActiva() {
        return entidadActiva;
    }

    /**
     * Define el valor de la propiedad entidadActiva.
     * 
     */
    public void setEntidadActiva(boolean value) {
        this.entidadActiva = value;
    }

    /**
     * Obtiene el valor de la propiedad proveedorActivo.
     * 
     */
    public boolean isProveedorActivo() {
        return proveedorActivo;
    }

    /**
     * Define el valor de la propiedad proveedorActivo.
     * 
     */
    public void setProveedorActivo(boolean value) {
        this.proveedorActivo = value;
    }

}
