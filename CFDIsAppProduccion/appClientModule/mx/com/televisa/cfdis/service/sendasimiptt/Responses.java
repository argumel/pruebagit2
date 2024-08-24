
package mx.com.televisa.cfdis.service.sendasimiptt;

import java.util.ArrayList;
import java.util.List;

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
 *         &lt;element name="Response" type="{http://xmlns.mx.com.televisa/BusinessObjects/SendEDICOM}ResponseType" maxOccurs="unbounded"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 *
 *
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "", propOrder = { "response" })
@XmlRootElement(name = "Responses")
public class Responses {

    @XmlElement(name = "Response", required = true)
    protected List<ResponseType> response;

    /**
     * Gets the value of the response property.
     *
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the response property.
     *
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getResponse().add(newItem);
     * </pre>
     *
     *
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link ResponseType }
     *
     *
     */
    public List<ResponseType> getResponse() {
        
        return this.response;
    }

	public void setResponse(List<ResponseType> response) {
		this.response = response;
	}
	@XmlAccessorType(XmlAccessType.FIELD)
	@XmlType(name = "", propOrder = { "error", "descripcion" })
	public static class ResponseType {

	    @XmlElement(name = "error",required = true)
	    protected List<String> error;
	    @XmlElement(name = "descripcion",required = true)
	    protected List<String> descripcion;
		public List<String> getError() {
			return error;
		}
		public void setError(List<String> error) {
			this.error = error;
		}
		public List<String> getDescripcion() {
			return descripcion;
		}
		public void setDescripcion(List<String> descripcion) {
			this.descripcion = descripcion;
		}

	    /**
	     * Gets the value of the error property.
	     *
	     * @return
	     *     possible object is
	     *     {@link String }
	     *
	     */
	  

	}

}
