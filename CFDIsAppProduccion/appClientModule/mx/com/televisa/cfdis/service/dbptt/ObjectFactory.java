
package mx.com.televisa.cfdis.service.dbptt;

import javax.xml.bind.JAXBElement;
import javax.xml.bind.annotation.XmlElementDecl;
import javax.xml.bind.annotation.XmlRegistry;
import javax.xml.namespace.QName;


/**
 * This object contains factory methods for each 
 * Java content interface and Java element interface 
 * generated in the mx.com.televisa.cfdis.service.dbptt package. 
 * <p>An ObjectFactory allows you to programatically 
 * construct new instances of the Java representation 
 * for XML content. The Java representation of XML 
 * content can consist of schema derived interfaces 
 * and classes representing the binding of schema 
 * type definitions, element declarations and model 
 * groups.  Factory methods for each of these are 
 * provided in this class.
 * 
 */
@XmlRegistry
public class ObjectFactory {

    private final static QName _DatabaseInput_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/Database", "DatabaseInput");
    private final static QName _DatabaseOutputCollection_QNAME = new QName("http://xmlns.oracle.com/pcbpel/adapter/db/Database", "DatabaseOutputCollection");

    /**
     * Create a new ObjectFactory that can be used to create new instances of schema derived classes for package: mx.com.televisa.cfdis.service.dbptt
     * 
     */
    public ObjectFactory() {
    }

    /**
     * Create an instance of {@link DatabaseOutputCollection }
     * 
     */
    public DatabaseOutputCollection createDatabaseOutputCollection() {
        return new DatabaseOutputCollection();
    }

    /**
     * Create an instance of {@link DatabaseInput }
     * 
     */
    public DatabaseInput createDatabaseInput() {
        return new DatabaseInput();
    }

    /**
     * Create an instance of {@link DatabaseOutput }
     * 
     */
    public DatabaseOutput createDatabaseOutput() {
        return new DatabaseOutput();
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatabaseInput }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/Database", name = "DatabaseInput")
    public JAXBElement<DatabaseInput> createDatabaseInput(DatabaseInput value) {
        return new JAXBElement<DatabaseInput>(_DatabaseInput_QNAME, DatabaseInput.class, null, value);
    }

    /**
     * Create an instance of {@link JAXBElement }{@code <}{@link DatabaseOutputCollection }{@code >}}
     * 
     */
    @XmlElementDecl(namespace = "http://xmlns.oracle.com/pcbpel/adapter/db/Database", name = "DatabaseOutputCollection")
    public JAXBElement<DatabaseOutputCollection> createDatabaseOutputCollection(DatabaseOutputCollection value) {
        return new JAXBElement<DatabaseOutputCollection>(_DatabaseOutputCollection_QNAME, DatabaseOutputCollection.class, null, value);
    }

}
