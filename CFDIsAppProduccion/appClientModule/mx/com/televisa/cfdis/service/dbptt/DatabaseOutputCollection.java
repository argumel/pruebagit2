
package mx.com.televisa.cfdis.service.dbptt;

import java.util.ArrayList;
import java.util.List;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlType;


/**
 * <p>Clase Java para DatabaseOutputCollection complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DatabaseOutputCollection">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="DatabaseOutput" type="{http://xmlns.oracle.com/pcbpel/adapter/db/Database}DatabaseOutput" maxOccurs="unbounded" minOccurs="0"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatabaseOutputCollection", propOrder = {
    "databaseOutput"
})
public class DatabaseOutputCollection {

    @XmlElement(name = "DatabaseOutput")
    protected List<DatabaseOutput> databaseOutput;

    /**
     * Gets the value of the databaseOutput property.
     * 
     * <p>
     * This accessor method returns a reference to the live list,
     * not a snapshot. Therefore any modification you make to the
     * returned list will be present inside the JAXB object.
     * This is why there is not a <CODE>set</CODE> method for the databaseOutput property.
     * 
     * <p>
     * For example, to add a new item, do as follows:
     * <pre>
     *    getDatabaseOutput().add(newItem);
     * </pre>
     * 
     * 
     * <p>
     * Objects of the following type(s) are allowed in the list
     * {@link DatabaseOutput }
     * 
     * 
     */
    public List<DatabaseOutput> getDatabaseOutput() {
        if (databaseOutput == null) {
            databaseOutput = new ArrayList<DatabaseOutput>();
        }
        return this.databaseOutput;
    }

}
