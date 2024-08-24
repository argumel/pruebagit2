
package mx.com.televisa.cfdis.service.dbptt;

import java.math.BigDecimal;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlSchemaType;
import javax.xml.bind.annotation.XmlType;
import javax.xml.datatype.XMLGregorianCalendar;


/**
 * <p>Clase Java para DatabaseOutput complex type.
 * 
 * <p>El siguiente fragmento de esquema especifica el contenido que se espera que haya en esta clase.
 * 
 * <pre>
 * &lt;complexType name="DatabaseOutput">
 *   &lt;complexContent>
 *     &lt;restriction base="{http://www.w3.org/2001/XMLSchema}anyType">
 *       &lt;sequence>
 *         &lt;element name="SERIEFOLIO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ARCHIVOORIGINAL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONSTANTE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="FECHAEXPEDICION" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="CLAVERETENCION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RETENCIONDESC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EMPRESARFC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RAZONSOCIAL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CURPEMISOR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="NACIONALIDAD" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RFCRECEPNACIONAL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RAZONSOCIALRECEPNACIONAL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CURPRECEPNACIONAL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RIFEXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RAZONSOCIALEXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RECEPMAIL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RECEPVENDORID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MESINICIAL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MESFINAL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="EJERCICIOFISCAL" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MONTOTOTALOPERACION" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="TOTALMONTOGRABADO" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="TOTLAMONTOEXCENTO" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="TOTALRETENCIONES" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="BASEIMPUESTO" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="IDCABECERO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MONTORETENIDO" type="{http://www.w3.org/2001/XMLSchema}decimal"/>
 *         &lt;element name="TIPOPAGORETENCION" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="BENEFICIARIOCOBRO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="PAISRESIDENCIA" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TIPOCONTRIBUYENTE" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONCEPTODESC" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RFCPAGOEXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CURPPAGOEXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="RAZONSOCIALCONTRPAGOEXT" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="TIPOCONTRPAGOEXTR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CONCEPTODESCPAGOEXTR" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ESTATUS" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="MOTIVORECHAZO" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="UUID" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE1" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE2" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE3" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE4" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE5" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE6" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE7" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE8" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE9" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE10" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE11" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="ATTRIBUTE12" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATEDBY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="CREATIONDATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *         &lt;element name="LASTUPDATEBY" type="{http://www.w3.org/2001/XMLSchema}string"/>
 *         &lt;element name="LASTUPDATEDATE" type="{http://www.w3.org/2001/XMLSchema}dateTime"/>
 *       &lt;/sequence>
 *     &lt;/restriction>
 *   &lt;/complexContent>
 * &lt;/complexType>
 * </pre>
 * 
 * 
 */
@XmlAccessorType(XmlAccessType.FIELD)
@XmlType(name = "DatabaseOutput", propOrder = {
    "seriefolio",
    "archivooriginal",
    "constante",
    "fechaexpedicion",
    "claveretencion",
    "retenciondesc",
    "empresarfc",
    "razonsocial",
    "curpemisor",
    "nacionalidad",
    "rfcrecepnacional",
    "razonsocialrecepnacional",
    "curprecepnacional",
    "rifext",
    "razonsocialext",
    "recepmail",
    "recepvendorid",
    "mesinicial",
    "mesfinal",
    "ejerciciofiscal",
    "montototaloperacion",
    "totalmontograbado",
    "totlamontoexcento",
    "totalretenciones",
    "baseimpuesto",
    "idcabecero",
    "montoretenido",
    "tipopagoretencion",
    "beneficiariocobro",
    "paisresidencia",
    "tipocontribuyente",
    "conceptodesc",
    "rfcpagoext",
    "curppagoext",
    "razonsocialcontrpagoext",
    "tipocontrpagoextr",
    "conceptodescpagoextr",
    "estatus",
    "motivorechazo",
    "uuid",
    "attribute1",
    "attribute2",
    "attribute3",
    "attribute4",
    "attribute5",
    "attribute6",
    "attribute7",
    "attribute8",
    "attribute9",
    "attribute10",
    "attribute11",
    "attribute12",
    "createdby",
    "creationdate",
    "lastupdateby",
    "lastupdatedate"
})
public class DatabaseOutput {

    @XmlElement(name = "SERIEFOLIO", required = true, nillable = true)
    protected String seriefolio;
    @XmlElement(name = "ARCHIVOORIGINAL", required = true, nillable = true)
    protected String archivooriginal;
    @XmlElement(name = "CONSTANTE", required = true, nillable = true)
    protected String constante;
    @XmlElement(name = "FECHAEXPEDICION", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar fechaexpedicion;
    @XmlElement(name = "CLAVERETENCION", required = true, nillable = true)
    protected String claveretencion;
    @XmlElement(name = "RETENCIONDESC", required = true, nillable = true)
    protected String retenciondesc;
    @XmlElement(name = "EMPRESARFC", required = true, nillable = true)
    protected String empresarfc;
    @XmlElement(name = "RAZONSOCIAL", required = true, nillable = true)
    protected String razonsocial;
    @XmlElement(name = "CURPEMISOR", required = true, nillable = true)
    protected String curpemisor;
    @XmlElement(name = "NACIONALIDAD", required = true, nillable = true)
    protected String nacionalidad;
    @XmlElement(name = "RFCRECEPNACIONAL", required = true, nillable = true)
    protected String rfcrecepnacional;
    @XmlElement(name = "RAZONSOCIALRECEPNACIONAL", required = true, nillable = true)
    protected String razonsocialrecepnacional;
    @XmlElement(name = "CURPRECEPNACIONAL", required = true, nillable = true)
    protected String curprecepnacional;
    @XmlElement(name = "RIFEXT", required = true, nillable = true)
    protected String rifext;
    @XmlElement(name = "RAZONSOCIALEXT", required = true, nillable = true)
    protected String razonsocialext;
    @XmlElement(name = "RECEPMAIL", required = true, nillable = true)
    protected String recepmail;
    @XmlElement(name = "RECEPVENDORID", required = true, nillable = true)
    protected String recepvendorid;
    @XmlElement(name = "MESINICIAL", required = true, nillable = true)
    protected String mesinicial;
    @XmlElement(name = "MESFINAL", required = true, nillable = true)
    protected String mesfinal;
    @XmlElement(name = "EJERCICIOFISCAL", required = true, nillable = true)
    protected String ejerciciofiscal;
    @XmlElement(name = "MONTOTOTALOPERACION", required = true, nillable = true)
    protected BigDecimal montototaloperacion;
    @XmlElement(name = "TOTALMONTOGRABADO", required = true, nillable = true)
    protected BigDecimal totalmontograbado;
    @XmlElement(name = "TOTLAMONTOEXCENTO", required = true, nillable = true)
    protected BigDecimal totlamontoexcento;
    @XmlElement(name = "TOTALRETENCIONES", required = true, nillable = true)
    protected BigDecimal totalretenciones;
    @XmlElement(name = "BASEIMPUESTO", required = true, nillable = true)
    protected BigDecimal baseimpuesto;
    @XmlElement(name = "IDCABECERO", required = true, nillable = true)
    protected String idcabecero;
    @XmlElement(name = "MONTORETENIDO", required = true, nillable = true)
    protected BigDecimal montoretenido;
    @XmlElement(name = "TIPOPAGORETENCION", required = true, nillable = true)
    protected String tipopagoretencion;
    @XmlElement(name = "BENEFICIARIOCOBRO", required = true, nillable = true)
    protected String beneficiariocobro;
    @XmlElement(name = "PAISRESIDENCIA", required = true, nillable = true)
    protected String paisresidencia;
    @XmlElement(name = "TIPOCONTRIBUYENTE", required = true, nillable = true)
    protected String tipocontribuyente;
    @XmlElement(name = "CONCEPTODESC", required = true, nillable = true)
    protected String conceptodesc;
    @XmlElement(name = "RFCPAGOEXT", required = true, nillable = true)
    protected String rfcpagoext;
    @XmlElement(name = "CURPPAGOEXT", required = true, nillable = true)
    protected String curppagoext;
    @XmlElement(name = "RAZONSOCIALCONTRPAGOEXT", required = true, nillable = true)
    protected String razonsocialcontrpagoext;
    @XmlElement(name = "TIPOCONTRPAGOEXTR", required = true, nillable = true)
    protected String tipocontrpagoextr;
    @XmlElement(name = "CONCEPTODESCPAGOEXTR", required = true, nillable = true)
    protected String conceptodescpagoextr;
    @XmlElement(name = "ESTATUS", required = true, nillable = true)
    protected String estatus;
    @XmlElement(name = "MOTIVORECHAZO", required = true, nillable = true)
    protected String motivorechazo;
    @XmlElement(name = "UUID", required = true, nillable = true)
    protected String uuid;
    @XmlElement(name = "ATTRIBUTE1", required = true, nillable = true)
    protected String attribute1;
    @XmlElement(name = "ATTRIBUTE2", required = true, nillable = true)
    protected String attribute2;
    @XmlElement(name = "ATTRIBUTE3", required = true, nillable = true)
    protected String attribute3;
    @XmlElement(name = "ATTRIBUTE4", required = true, nillable = true)
    protected String attribute4;
    @XmlElement(name = "ATTRIBUTE5", required = true, nillable = true)
    protected String attribute5;
    @XmlElement(name = "ATTRIBUTE6", required = true, nillable = true)
    protected String attribute6;
    @XmlElement(name = "ATTRIBUTE7", required = true, nillable = true)
    protected String attribute7;
    @XmlElement(name = "ATTRIBUTE8", required = true, nillable = true)
    protected String attribute8;
    @XmlElement(name = "ATTRIBUTE9", required = true, nillable = true)
    protected String attribute9;
    @XmlElement(name = "ATTRIBUTE10", required = true, nillable = true)
    protected String attribute10;
    @XmlElement(name = "ATTRIBUTE11", required = true, nillable = true)
    protected String attribute11;
    @XmlElement(name = "ATTRIBUTE12", required = true, nillable = true)
    protected String attribute12;
    @XmlElement(name = "CREATEDBY", required = true, nillable = true)
    protected String createdby;
    @XmlElement(name = "CREATIONDATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar creationdate;
    @XmlElement(name = "LASTUPDATEBY", required = true, nillable = true)
    protected String lastupdateby;
    @XmlElement(name = "LASTUPDATEDATE", required = true, nillable = true)
    @XmlSchemaType(name = "dateTime")
    protected XMLGregorianCalendar lastupdatedate;

    /**
     * Obtiene el valor de la propiedad seriefolio.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getSERIEFOLIO() {
        return seriefolio;
    }

    /**
     * Define el valor de la propiedad seriefolio.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setSERIEFOLIO(String value) {
        this.seriefolio = value;
    }

    /**
     * Obtiene el valor de la propiedad archivooriginal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getARCHIVOORIGINAL() {
        return archivooriginal;
    }

    /**
     * Define el valor de la propiedad archivooriginal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setARCHIVOORIGINAL(String value) {
        this.archivooriginal = value;
    }

    /**
     * Obtiene el valor de la propiedad constante.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONSTANTE() {
        return constante;
    }

    /**
     * Define el valor de la propiedad constante.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONSTANTE(String value) {
        this.constante = value;
    }

    /**
     * Obtiene el valor de la propiedad fechaexpedicion.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getFECHAEXPEDICION() {
        return fechaexpedicion;
    }

    /**
     * Define el valor de la propiedad fechaexpedicion.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setFECHAEXPEDICION(XMLGregorianCalendar value) {
        this.fechaexpedicion = value;
    }

    /**
     * Obtiene el valor de la propiedad claveretencion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCLAVERETENCION() {
        return claveretencion;
    }

    /**
     * Define el valor de la propiedad claveretencion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCLAVERETENCION(String value) {
        this.claveretencion = value;
    }

    /**
     * Obtiene el valor de la propiedad retenciondesc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRETENCIONDESC() {
        return retenciondesc;
    }

    /**
     * Define el valor de la propiedad retenciondesc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRETENCIONDESC(String value) {
        this.retenciondesc = value;
    }

    /**
     * Obtiene el valor de la propiedad empresarfc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEMPRESARFC() {
        return empresarfc;
    }

    /**
     * Define el valor de la propiedad empresarfc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEMPRESARFC(String value) {
        this.empresarfc = value;
    }

    /**
     * Obtiene el valor de la propiedad razonsocial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRAZONSOCIAL() {
        return razonsocial;
    }

    /**
     * Define el valor de la propiedad razonsocial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRAZONSOCIAL(String value) {
        this.razonsocial = value;
    }

    /**
     * Obtiene el valor de la propiedad curpemisor.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCURPEMISOR() {
        return curpemisor;
    }

    /**
     * Define el valor de la propiedad curpemisor.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCURPEMISOR(String value) {
        this.curpemisor = value;
    }

    /**
     * Obtiene el valor de la propiedad nacionalidad.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getNACIONALIDAD() {
        return nacionalidad;
    }

    /**
     * Define el valor de la propiedad nacionalidad.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setNACIONALIDAD(String value) {
        this.nacionalidad = value;
    }

    /**
     * Obtiene el valor de la propiedad rfcrecepnacional.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRFCRECEPNACIONAL() {
        return rfcrecepnacional;
    }

    /**
     * Define el valor de la propiedad rfcrecepnacional.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRFCRECEPNACIONAL(String value) {
        this.rfcrecepnacional = value;
    }

    /**
     * Obtiene el valor de la propiedad razonsocialrecepnacional.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRAZONSOCIALRECEPNACIONAL() {
        return razonsocialrecepnacional;
    }

    /**
     * Define el valor de la propiedad razonsocialrecepnacional.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRAZONSOCIALRECEPNACIONAL(String value) {
        this.razonsocialrecepnacional = value;
    }

    /**
     * Obtiene el valor de la propiedad curprecepnacional.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCURPRECEPNACIONAL() {
        return curprecepnacional;
    }

    /**
     * Define el valor de la propiedad curprecepnacional.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCURPRECEPNACIONAL(String value) {
        this.curprecepnacional = value;
    }

    /**
     * Obtiene el valor de la propiedad rifext.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRIFEXT() {
        return rifext;
    }

    /**
     * Define el valor de la propiedad rifext.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRIFEXT(String value) {
        this.rifext = value;
    }

    /**
     * Obtiene el valor de la propiedad razonsocialext.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRAZONSOCIALEXT() {
        return razonsocialext;
    }

    /**
     * Define el valor de la propiedad razonsocialext.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRAZONSOCIALEXT(String value) {
        this.razonsocialext = value;
    }

    /**
     * Obtiene el valor de la propiedad recepmail.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRECEPMAIL() {
        return recepmail;
    }

    /**
     * Define el valor de la propiedad recepmail.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRECEPMAIL(String value) {
        this.recepmail = value;
    }

    /**
     * Obtiene el valor de la propiedad recepvendorid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRECEPVENDORID() {
        return recepvendorid;
    }

    /**
     * Define el valor de la propiedad recepvendorid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRECEPVENDORID(String value) {
        this.recepvendorid = value;
    }

    /**
     * Obtiene el valor de la propiedad mesinicial.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESINICIAL() {
        return mesinicial;
    }

    /**
     * Define el valor de la propiedad mesinicial.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESINICIAL(String value) {
        this.mesinicial = value;
    }

    /**
     * Obtiene el valor de la propiedad mesfinal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMESFINAL() {
        return mesfinal;
    }

    /**
     * Define el valor de la propiedad mesfinal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMESFINAL(String value) {
        this.mesfinal = value;
    }

    /**
     * Obtiene el valor de la propiedad ejerciciofiscal.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getEJERCICIOFISCAL() {
        return ejerciciofiscal;
    }

    /**
     * Define el valor de la propiedad ejerciciofiscal.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setEJERCICIOFISCAL(String value) {
        this.ejerciciofiscal = value;
    }

    /**
     * Obtiene el valor de la propiedad montototaloperacion.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMONTOTOTALOPERACION() {
        return montototaloperacion;
    }

    /**
     * Define el valor de la propiedad montototaloperacion.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMONTOTOTALOPERACION(BigDecimal value) {
        this.montototaloperacion = value;
    }

    /**
     * Obtiene el valor de la propiedad totalmontograbado.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTOTALMONTOGRABADO() {
        return totalmontograbado;
    }

    /**
     * Define el valor de la propiedad totalmontograbado.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTOTALMONTOGRABADO(BigDecimal value) {
        this.totalmontograbado = value;
    }

    /**
     * Obtiene el valor de la propiedad totlamontoexcento.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTOTLAMONTOEXCENTO() {
        return totlamontoexcento;
    }

    /**
     * Define el valor de la propiedad totlamontoexcento.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTOTLAMONTOEXCENTO(BigDecimal value) {
        this.totlamontoexcento = value;
    }

    /**
     * Obtiene el valor de la propiedad totalretenciones.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getTOTALRETENCIONES() {
        return totalretenciones;
    }

    /**
     * Define el valor de la propiedad totalretenciones.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setTOTALRETENCIONES(BigDecimal value) {
        this.totalretenciones = value;
    }

    /**
     * Obtiene el valor de la propiedad baseimpuesto.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getBASEIMPUESTO() {
        return baseimpuesto;
    }

    /**
     * Define el valor de la propiedad baseimpuesto.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setBASEIMPUESTO(BigDecimal value) {
        this.baseimpuesto = value;
    }

    /**
     * Obtiene el valor de la propiedad idcabecero.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getIDCABECERO() {
        return idcabecero;
    }

    /**
     * Define el valor de la propiedad idcabecero.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setIDCABECERO(String value) {
        this.idcabecero = value;
    }

    /**
     * Obtiene el valor de la propiedad montoretenido.
     * 
     * @return
     *     possible object is
     *     {@link BigDecimal }
     *     
     */
    public BigDecimal getMONTORETENIDO() {
        return montoretenido;
    }

    /**
     * Define el valor de la propiedad montoretenido.
     * 
     * @param value
     *     allowed object is
     *     {@link BigDecimal }
     *     
     */
    public void setMONTORETENIDO(BigDecimal value) {
        this.montoretenido = value;
    }

    /**
     * Obtiene el valor de la propiedad tipopagoretencion.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOPAGORETENCION() {
        return tipopagoretencion;
    }

    /**
     * Define el valor de la propiedad tipopagoretencion.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOPAGORETENCION(String value) {
        this.tipopagoretencion = value;
    }

    /**
     * Obtiene el valor de la propiedad beneficiariocobro.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getBENEFICIARIOCOBRO() {
        return beneficiariocobro;
    }

    /**
     * Define el valor de la propiedad beneficiariocobro.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setBENEFICIARIOCOBRO(String value) {
        this.beneficiariocobro = value;
    }

    /**
     * Obtiene el valor de la propiedad paisresidencia.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getPAISRESIDENCIA() {
        return paisresidencia;
    }

    /**
     * Define el valor de la propiedad paisresidencia.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setPAISRESIDENCIA(String value) {
        this.paisresidencia = value;
    }

    /**
     * Obtiene el valor de la propiedad tipocontribuyente.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOCONTRIBUYENTE() {
        return tipocontribuyente;
    }

    /**
     * Define el valor de la propiedad tipocontribuyente.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOCONTRIBUYENTE(String value) {
        this.tipocontribuyente = value;
    }

    /**
     * Obtiene el valor de la propiedad conceptodesc.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONCEPTODESC() {
        return conceptodesc;
    }

    /**
     * Define el valor de la propiedad conceptodesc.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONCEPTODESC(String value) {
        this.conceptodesc = value;
    }

    /**
     * Obtiene el valor de la propiedad rfcpagoext.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRFCPAGOEXT() {
        return rfcpagoext;
    }

    /**
     * Define el valor de la propiedad rfcpagoext.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRFCPAGOEXT(String value) {
        this.rfcpagoext = value;
    }

    /**
     * Obtiene el valor de la propiedad curppagoext.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCURPPAGOEXT() {
        return curppagoext;
    }

    /**
     * Define el valor de la propiedad curppagoext.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCURPPAGOEXT(String value) {
        this.curppagoext = value;
    }

    /**
     * Obtiene el valor de la propiedad razonsocialcontrpagoext.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getRAZONSOCIALCONTRPAGOEXT() {
        return razonsocialcontrpagoext;
    }

    /**
     * Define el valor de la propiedad razonsocialcontrpagoext.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setRAZONSOCIALCONTRPAGOEXT(String value) {
        this.razonsocialcontrpagoext = value;
    }

    /**
     * Obtiene el valor de la propiedad tipocontrpagoextr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getTIPOCONTRPAGOEXTR() {
        return tipocontrpagoextr;
    }

    /**
     * Define el valor de la propiedad tipocontrpagoextr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setTIPOCONTRPAGOEXTR(String value) {
        this.tipocontrpagoextr = value;
    }

    /**
     * Obtiene el valor de la propiedad conceptodescpagoextr.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCONCEPTODESCPAGOEXTR() {
        return conceptodescpagoextr;
    }

    /**
     * Define el valor de la propiedad conceptodescpagoextr.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCONCEPTODESCPAGOEXTR(String value) {
        this.conceptodescpagoextr = value;
    }

    /**
     * Obtiene el valor de la propiedad estatus.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getESTATUS() {
        return estatus;
    }

    /**
     * Define el valor de la propiedad estatus.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setESTATUS(String value) {
        this.estatus = value;
    }

    /**
     * Obtiene el valor de la propiedad motivorechazo.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getMOTIVORECHAZO() {
        return motivorechazo;
    }

    /**
     * Define el valor de la propiedad motivorechazo.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setMOTIVORECHAZO(String value) {
        this.motivorechazo = value;
    }

    /**
     * Obtiene el valor de la propiedad uuid.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getUUID() {
        return uuid;
    }

    /**
     * Define el valor de la propiedad uuid.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setUUID(String value) {
        this.uuid = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute1.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE1() {
        return attribute1;
    }

    /**
     * Define el valor de la propiedad attribute1.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE1(String value) {
        this.attribute1 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute2.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE2() {
        return attribute2;
    }

    /**
     * Define el valor de la propiedad attribute2.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE2(String value) {
        this.attribute2 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute3.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE3() {
        return attribute3;
    }

    /**
     * Define el valor de la propiedad attribute3.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE3(String value) {
        this.attribute3 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute4.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE4() {
        return attribute4;
    }

    /**
     * Define el valor de la propiedad attribute4.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE4(String value) {
        this.attribute4 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute5.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE5() {
        return attribute5;
    }

    /**
     * Define el valor de la propiedad attribute5.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE5(String value) {
        this.attribute5 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute6.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE6() {
        return attribute6;
    }

    /**
     * Define el valor de la propiedad attribute6.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE6(String value) {
        this.attribute6 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute7.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE7() {
        return attribute7;
    }

    /**
     * Define el valor de la propiedad attribute7.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE7(String value) {
        this.attribute7 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute8.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE8() {
        return attribute8;
    }

    /**
     * Define el valor de la propiedad attribute8.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE8(String value) {
        this.attribute8 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute9.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE9() {
        return attribute9;
    }

    /**
     * Define el valor de la propiedad attribute9.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE9(String value) {
        this.attribute9 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute10.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE10() {
        return attribute10;
    }

    /**
     * Define el valor de la propiedad attribute10.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE10(String value) {
        this.attribute10 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute11.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE11() {
        return attribute11;
    }

    /**
     * Define el valor de la propiedad attribute11.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE11(String value) {
        this.attribute11 = value;
    }

    /**
     * Obtiene el valor de la propiedad attribute12.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getATTRIBUTE12() {
        return attribute12;
    }

    /**
     * Define el valor de la propiedad attribute12.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setATTRIBUTE12(String value) {
        this.attribute12 = value;
    }

    /**
     * Obtiene el valor de la propiedad createdby.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getCREATEDBY() {
        return createdby;
    }

    /**
     * Define el valor de la propiedad createdby.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setCREATEDBY(String value) {
        this.createdby = value;
    }

    /**
     * Obtiene el valor de la propiedad creationdate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getCREATIONDATE() {
        return creationdate;
    }

    /**
     * Define el valor de la propiedad creationdate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setCREATIONDATE(XMLGregorianCalendar value) {
        this.creationdate = value;
    }

    /**
     * Obtiene el valor de la propiedad lastupdateby.
     * 
     * @return
     *     possible object is
     *     {@link String }
     *     
     */
    public String getLASTUPDATEBY() {
        return lastupdateby;
    }

    /**
     * Define el valor de la propiedad lastupdateby.
     * 
     * @param value
     *     allowed object is
     *     {@link String }
     *     
     */
    public void setLASTUPDATEBY(String value) {
        this.lastupdateby = value;
    }

    /**
     * Obtiene el valor de la propiedad lastupdatedate.
     * 
     * @return
     *     possible object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public XMLGregorianCalendar getLASTUPDATEDATE() {
        return lastupdatedate;
    }

    /**
     * Define el valor de la propiedad lastupdatedate.
     * 
     * @param value
     *     allowed object is
     *     {@link XMLGregorianCalendar }
     *     
     */
    public void setLASTUPDATEDATE(XMLGregorianCalendar value) {
        this.lastupdatedate = value;
    }

}
