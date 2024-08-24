package mx.com.televisa.cfdis.process.cargasexcel;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.StringReader;
import java.io.StringWriter;
import java.nio.file.Files;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

import mx.com.televisa.cfdis.data.ConnectionWrapper;
import mx.com.televisa.cfdis.service.eeasimiptt.CheckCompanyExistenceProcess;
import mx.com.televisa.cfdis.service.eeasimiptt.CheckCompanyExistenceService;
import mx.com.televisa.cfdis.service.eeasimiptt.CheckCompanyReqMsg;
import mx.com.televisa.cfdis.service.eeasimiptt.CheckCompanyRespMsg;
import mx.com.televisa.cfdis.service.eeasimiptt.CheckCompanyType;
import mx.com.televisa.cfdis.service.eeptt.ExecutePttConsultarExistenciaEmpresaREQUEST;
import mx.com.televisa.cfdis.service.eeptt.InputParameters;
import mx.com.televisa.cfdis.service.eeptt.OutputParameters;
import mx.com.televisa.cfdis.service.eeptt.ValidarExistenciaEmpresa;
import mx.com.televisa.cfdis.service.invptt.ValidarInformacionFactura;
import mx.com.televisa.cfdis.service.invptt.ValidarInformacionFacturaValidarInformacionFacturaREQUEST;

import mx.com.televisa.cfdis.service.sendasimiptt.SendEDICOMProcess;
import mx.com.televisa.cfdis.service.sendasimiptt.SendEdicomService;
import mx.com.televisa.cfdis.service.sendasimiptt.SendFileEDICOMReqMsg;
import mx.com.televisa.cfdis.service.sendasimiptt.Responses;
import mx.com.televisa.cfdis.service.sendasimiptt.SendFileEDICOMType;
import mx.com.televisa.cfdis.util.LeerProperties;
import mx.com.televisa.cfdis.util.SFTPHelper;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

public class CargarInfoHonoAsimiNomi {
  static ArrayList<String> 	alNuevaLinea 	= null;
  static boolean 			lbempOIC = true;
  static File 				lfCABFAC = null;
  static File 				lfCNOMINA = null;
  static File 				lfCNOMINADP = null;
  public static boolean 	lbempEBS;
  static String 			version	=	"";
  static String				vRegimenFiscal 	= "";
  static String				vUsoCFDI 		= "";
  
  static ArrayList<String> lierror = new ArrayList<String>();
  
  public static ArrayList<String> doCargarInfoHonoAsimiNomi(File[] pfNombreArchivos) throws Exception {
	   lfCABFAC = null;
	   lfCNOMINA = null;
	   lfCNOMINADP = null;
	   lbempEBS = false;
	   version	=	"";
	  
	  lierror = new ArrayList<String>();
	  if (pfNombreArchivos == null)
    	lierror.add( "El sistema espera tres archivos de texto para procesar."); 
    	if (lierror.size() > 0)
    	{return lierror;}
    	
    validarArchivos(pfNombreArchivos);
    lierror = cargarArchivoEnObjeto(pfNombreArchivos);
    
    
    return lierror;
  }
  
  public static ArrayList<String> cargarArchivoEnObjeto(File[] pfNombreArchivos) throws FileNotFoundException, IOException, SQLException, Exception {
    ArrayList<RecordCABFAC> alArchivoCABFAC = null;
    ArrayList<RecordCNOMINA> alArchivoCNOMINA = null;
    ArrayList<RecordCNOMINADP> alArchivoCNOMINADP = null;
    String stDelimiter = "\\|";
    int liGetIndexValu = 0;
    String lstsql = "SELECT COUNT(*) FactValida FROM   Ap.Ap_Invoices_All  Inv WHERE  1 = 1 AND    Invoice_Num = ? AND    EXISTS (SELECT 'Empresas Validas' FROM Apps.Xxap_Cfdis_Ret_Emp_Vw Orgs WHERE Orgs.Rfc    = ? AND Orgs.Org_Id = Inv.Org_Id) AND    EXISTS (SELECT 'Proveedores Validos' FROM Po.Po_Vendors  Ven , Po.Po_Vendor_Sites_All VenSit WHERE Ven.Vendor_Id = VenSit.Vendor_Id AND Enabled_Flag  = 'Y' AND Segment1      = ? AND Inv.Vendor_Id = Ven.Vendor_Id AND Inv.Org_Id    = VenSit.Org_Id) ";
    String lstsql3 = "SELECT LOOKUP_CODE   FROM Apps.FND_LOOKUP_VALUES LV  WHERE LANGUAGE = 'ESA'    AND ENABLED_FLAG = 'Y'    AND MEANING = ?    AND SYSDATE >= NVL(START_DATE_ACTIVE, SYSDATE)    AND SYSDATE <= NVL(END_DATE_ACTIVE, SYSDATE)    AND LV.LOOKUP_TYPE = 'XXAP_CARGA_RET_EMP'    AND ROWNUM = 1 ";
    ConnectionWrapper luCW = null;
    ResultSet luResulset = null;
    String lstNumReg = "";
    int linNumReg = 0;
    File fCabFac = null;
    FileInputStream fis = null;
    InputStreamReader isr = null;
    byte b;
    int i;
    File[] arrayOfFile;
    for (i = (arrayOfFile = pfNombreArchivos).length, b = 0; b < i; ) {
      File f = arrayOfFile[b];
      FileReader fr = new FileReader(f);
      BufferedReader br = new BufferedReader(fr);
      String archivo = f.getName();
      fis = new FileInputStream(f);
      isr = new InputStreamReader(fis, "utf-8");
      BufferedReader leer = new BufferedReader(isr);
      String s = isr.getEncoding();
      System.out.print("Character Encoding: " + s);
      if (f.getName().equals("CABFAC.txt")) {
        fCabFac = f;
        alArchivoCABFAC = new ArrayList<>();
        int linNumLinea = 1;
        String lstLinea;

        
        while ((lstLinea = leer.readLine()) != null) {
        	 System.out.println(String.valueOf(lstLinea) + "(Â¬Â°oÂ°)Â¬");
          lstLinea = StringUtils.replace(lstLinea, "||", "| |");
          String[] lstCampos = lstLinea.split(stDelimiter);
          
        //kaz - para obtener la versión en la primer linea y comparar despues con los demas registros
          
          if(version.length() == 0) {
        	  version = lstCampos[26];
        	  
        	  if(version == null || version.equals("")) {
              	  throw new IllegalArgumentException("Error: Es requerido capturar la versión");
                }
          }
          
          
          
          
          RecordCABFAC rCABFAC = new RecordCABFAC();
          
      
  		  if(!version.equals(lstCampos[26]) ) {
      		  throw new IllegalArgumentException("Error: La versión debe coincidir en todos los registros");
            }
          	  
          	 
            
          
          
          for (int j = 0; j < lstCampos.length; j++) {
            String code = isr.getEncoding();
            System.out.print("Character Encoding: " + code);
            System.out.print("Character jaja");
            
            validarCamposArchivosCABFAC(linNumLinea, j, lstCampos[j]);
            
            
            rCABFAC.addCampo(lstCampos[j]);
          } 
          
          
          
          System.out.print("Valido oic Hono Asimi");
          
          
          CheckCompanyExistenceProcess valEmp = new CheckCompanyExistenceProcess();
          CheckCompanyExistenceService valEmpImpl = valEmp.getCheckCompanyExistenceService();
          CheckCompanyType companyCheck = new CheckCompanyType();
          CheckCompanyReqMsg checkCompanyReqMsg = new CheckCompanyReqMsg();
          
          companyCheck.setRfc(lstCampos[15]);
          checkCompanyReqMsg.setCheckCompany(companyCheck);
          CheckCompanyRespMsg checkResp = new CheckCompanyRespMsg();     
          checkResp = valEmpImpl.checkCompanyExistence(checkCompanyReqMsg);
          
          
          
			System.out.println("aqui input empresa emisora :  " + lstCampos[15]);
				
				
				
				
				
				System.out.println("servicio valida empresa:  " + checkResp.getResponse().isExiste());
				System.out.println("servicio valida empresa:  " + checkResp.getResponse().getDescripcion());
				
				Boolean bandera = true;
				
		/**
		 * KAZ 18-05-22 Argumel 
		 * Si la empresa existe en cloud valida:
		 * 1)  que la factura exista
		 * 2) Si la empresa o entidad existe
		 * 3) Si el proveedor esta activo
		 */
          if(checkResp.getResponse().isExiste()) {
          
        	  lbempOIC = true;
        	  
        	  mx.com.televisa.cfdis.service.invasimiptt.ValidarInformacionFactura valfact = new mx.com.televisa.cfdis.service.invasimiptt.ValidarInformacionFactura();
			mx.com.televisa.cfdis.service.invasimiptt.InputParameters factInput = new mx.com.televisa.cfdis.service.invasimiptt.InputParameters();
			factInput.setNumeroFactura(lstCampos[1]);
			factInput.setRfcEmpresa(lstCampos[15]);
			factInput.setRfcProveedor(lstCampos[2]);
			mx.com.televisa.cfdis.service.invasimiptt.OutputParameters outfact = new mx.com.televisa.cfdis.service.invasimiptt.OutputParameters ();
			mx.com.televisa.cfdis.service.invasimiptt.ValidarInformacionFacturaValidarInformacionFacturaREQUEST valfacti = valfact.getValidarInformacionFacturaValidarInformacionFacturaREQUESTPt();
			
			outfact = valfacti.validarInformacionFactura(factInput);
			System.out.println("servicio valida factura:  " + outfact.isExisteFactura());
			System.out.println("servicio valida entidad:  " + outfact.isEntidadActiva());
			System.out.println("servicio valida Proveedor:  " + outfact.isProveedorActivo());
			
			
			if(!outfact.isExisteFactura() || !outfact.isEntidadActiva() || !outfact.isProveedorActivo() ) {
				System.out.println("servicio valida factura error:  " + outfact.isExisteFactura());
				System.out.println("servicio valida entidad error:  " + outfact.isEntidadActiva());
				System.out.println("servicio valida Proveedor error:  " + outfact.isProveedorActivo());
				 throw new IllegalArgumentException(outfact.getDescripcion()); 
			}
          
          }else {
        	  /**
        	   * Kaz 18-05-2022 Argumel
        	   * Si no existe la empresa en cloud, revisa en la EBS normal las mismas validaciones que en cloud
        	   * 1) Revisa en Ap_Invoices_All
        	   * 2) Revisa en el lookup XXAP_CARGA_RET_EMP
        	   */
        	  luCW = new ConnectionWrapper();
        	  lbempOIC = false;
          PreparedStatement psStmt = luCW.prepareStatement("Alter Session Set NLS_Language='AMERICAN'");
          luResulset = psStmt.executeQuery();
          psStmt = luCW.prepareStatement(lstsql);
          psStmt.setObject(1, lstCampos[1]);
          psStmt.setObject(2, lstCampos[15]);
          psStmt.setObject(3, lstCampos[2]);
          luResulset = psStmt.executeQuery();
          if (luResulset.next()) {
            lstNumReg = luResulset.getString("FactValida");
            linNumReg = Integer.parseInt(lstNumReg);
            System.out.println("Num Reg Num:  " + linNumReg);
          } 
          ConnectionWrapper.closeAll(new Object[] { luCW });
          luCW = new ConnectionWrapper();
          psStmt = luCW.prepareStatement(lstsql3);
          psStmt.setObject(1, lstCampos[15]);
          luResulset = psStmt.executeQuery();
          String lstEmpresa = "";
          if (luResulset.next()) {
            lstEmpresa = luResulset.getString("LOOKUP_CODE");
            if (lstEmpresa == null)
              throw new IllegalArgumentException("La empresa emisora " + lstCampos[15] + " No esta registrada para esta instancia."); 
          } else {
            throw new IllegalArgumentException("La empresa emisora " + lstCampos[15] + " No esta registrada para esta instancia.");
          } 
          lbempEBS = true;
          ConnectionWrapper.closeAll(new Object[] { luCW });
          
          }        
          
          alArchivoCABFAC.add(rCABFAC);
          linNumLinea++;
        } 
      } else if (f.getName().equals("CNOMINA.txt")) {
        alArchivoCNOMINA = new ArrayList<>();
        int linNumLinea = 1;
        String lstLinea;
        while ((lstLinea = br.readLine()) != null) {
          lstLinea = StringUtils.replace(lstLinea, "||", "| |");
          String[] lstCampos = lstLinea.split(stDelimiter);
          RecordCNOMINA rCNOMINA = new RecordCNOMINA();
          for (int j = 0; j < lstCampos.length; j++) {
        	  System.out.println("validarCamposArchivosCNOMINA");
            validarCamposArchivosCNOMINA(linNumLinea, j, lstCampos[j]);
            rCNOMINA.addCampo(lstCampos[j]);
          } 
          alArchivoCNOMINA.add(rCNOMINA);
          linNumLinea++;
        } 
      } else if (f.getName().equals("CNOMINADP.txt")) {
        alArchivoCNOMINADP = new ArrayList<>();
        int linNumLinea = 1;
        String lstLinea;
        while ((lstLinea = br.readLine()) != null) {
          lstLinea = StringUtils.replace(lstLinea, "||", "| |");
          String[] lstCampos = lstLinea.split(stDelimiter);
          RecordCNOMINADP rCNOMINADP = new RecordCNOMINADP();
          System.out.println("total campos " + lstCampos.length);
          for (int j = 0; j < lstCampos.length; j++) {
        	  System.out.println("validarCamposArchivosCNOMINAdp");
            validarCamposArchivosCNOMINADP(linNumLinea, j, lstCampos[j]);
            rCNOMINADP.addCampo(lstCampos[j]);
          } 
          alArchivoCNOMINADP.add(rCNOMINADP);
          linNumLinea++;
        } 
      } 
      leer.close();
      b++;
    } 
    
    if (!lbempOIC) {
    System.out.println("validar_RFCE_Serie_Folio_En_Tabla");
    CargasCommon.validar_RFCE_Serie_Folio_En_Tabla(alArchivoCABFAC);
    System.out.println("validar_RFCE_Serie_Folio_En_Tabla");
    liGetIndexValu = CargasCommon.getIndexValue();
    System.out.println("cargar objetos en bd");
    CargasCommon.cargarObjetoEnBaseDatosCABFAC(alArchivoCABFAC, liGetIndexValu,version);
    CargasCommon.cargarObjetoEnBaseDatosCNOMINA(alArchivoCNOMINA, liGetIndexValu);
    CargasCommon.cargarObjetoEnBaseDatosCNOMINADP(alArchivoCNOMINADP, liGetIndexValu);
    }
    lierror = enviarArchivos(pfNombreArchivos, liGetIndexValu);
    return lierror;
  }
  
  public static void validarArchivos(File[] pfNombreArchivos) throws Exception {
    byte b;
    int i;
    byte[] bytesencod = null; 
    File[] arrayOfFile;
    for (i = (arrayOfFile = pfNombreArchivos).length, b = 0; b < i; ) {
      File f = arrayOfFile[b];
      if (pfNombreArchivos.length == 3) {
        if (!f.getName().equals("CABFAC.txt") && !f.getName().equals("CNOMINA.txt") && !f.getName().equals("CNOMINADP.txt"))
          {throw new IllegalArgumentException("Ha seleccionado algun archivo incorrecto.\nDeben ser:\n\t\tCABFAC.txt\n\t\tCNOMINA.txt\n\t\tCNOMINADP.txt\n"); 
          }
        
        
        
      } else {
        throw new IllegalArgumentException("Deben seleccionar los tres archivos requeridos.\n\t\tCABFAC.txt\n\t\tCNOMINA.txt\n\t\tCNOMINADP.txt\n");
      } 
      
      
      b++;
    } 
  }
  
  public static void validarCamposArchivosCABFAC(int pinNumLinea, int pinNumCampo, String pstCampo) throws Exception {
    String lstsql2 = "SELECT COUNT (*) ProvExists FROM	   po.po_vendors WHERE  segment1 = ? AND    enabled_flag = 'Y' ";
    //KAZ 04-07-2022 QUERY PARA VALIDAR QUE EL USO DE CFDI CORRESPONDA CON EL REGIMEN FISCAL
    
    String sqlValRegUso = "SELECT COUNT(*)\n"
    		+ "				     FROM FND_LOOKUP_VALUES_VL flvv\n"
    		+ "                     WHERE flvv.LOOKUP_TYPE = 'XXAR_REL_REGIMEN_USO_CFDI_4_0'\n"
    		+ "				     AND TRIM(flvv.DESCRIPTION) = (trim(?)) AND TRIM(TAG)= trim(?)";
    
    ConnectionWrapper luCW = null;
    ResultSet luResulset = null;
    String lstNumReg = "";
    int linNumReg = 0;
    if (pinNumCampo == 0 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: ID/LABEL  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 1 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: No. Factura AP " + pinNumCampo + 
          " en la linea: " + pinNumLinea); 
    if (pinNumCampo == 2) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: RFC Proveedor AP  en la linea: " + 
            pinNumLinea); 
      
      System.out.print("SQL EXECTUTE");
      
      if(!lbempOIC) {
      luCW = new ConnectionWrapper();
      PreparedStatement psStmt = luCW.prepareStatement(lstsql2);
      psStmt.setObject(1, pstCampo);
      luResulset = psStmt.executeQuery();
      linNumReg = 0;
      lstNumReg = "";
      if (luResulset.next()) {
        lstNumReg = luResulset.getString("ProvExists");
        linNumReg = Integer.parseInt(lstNumReg);
        System.out.println("Num Reg Num:  " + linNumReg);
        if (linNumReg == 0)
          throw new IllegalArgumentException("No existe el proveedor " + pstCampo + " en el sistema."); 
      } 
      
      ConnectionWrapper.closeAll(new Object[] { luCW });
      }
    } 
    if (pinNumCampo == 4 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: SERIE  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 5 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: FOLIO  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 6 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: FECHAC  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 7 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: MetodoPago  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 8 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: BASE PAGOS SUBTOTAL en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 9 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: DESCUENTO en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 10 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: PAGO NETO Total  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 11 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: FormaPago  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 12 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: DESCRIPCION  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 13 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: ValorUnitario  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 14 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: IMPORTE  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 15 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: RFC_E  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 16 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: NOMBRE_E  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 17 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: RegimenFiscal  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 18 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: LUGAREXPEDICION  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 19 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: RFC_R  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 20 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: NOMBRE_R  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 21 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: EMAIL " + pinNumCampo + 
          " en la linea: " + pinNumLinea); 
    if (pinNumCampo == 22 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0) ) {
      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: UsoCFDI " + pinNumCampo + 
          " en la linea: " + pinNumLinea); 
    }else if(pinNumCampo == 22 && pstCampo.trim().length() > 0) {
    	vUsoCFDI	=	pstCampo;
    }
    /*
     * Kaz Argumel: se agregan tres columnas de cfdi 4.0 (Version, Codigo postal receptor y regimen fiscal receptor)
     */
    
    if (pinNumCampo == 26 && (
    	      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0) ) {
    	      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: version " + pinNumCampo + 
    	          " en la linea: " + pinNumLinea); 
    }else {
    	if (pinNumCampo == 26) {
    		version = pstCampo;
    		if(version.equals("4")) {
    			throw new IllegalArgumentException("El campo versión debe ser en formato texto (No se registra 4.0)");
    	    }
    	}
    	
    }
    
    if(version.equals("4.0")) {
			if (pinNumCampo == 27 && (
				      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0) )
			  throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: cp_r " + pinNumCampo + 
			  " en la linea: " + pinNumLinea);
	        if (pinNumCampo == 28 && (
	      	      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0) ) {
	      	      throw new IllegalArgumentException("El archivo CABFAC no puede ir vacio el campo: RegimenFiscalR " + pinNumCampo + 
	      	          " en la linea: " + pinNumLinea);
	        }else if(pinNumCampo == 28 && pstCampo.trim().length() > 0){
	        	vRegimenFiscal = pstCampo;
	        }
	        /*
	        if (pinNumCampo == 28) {
	        	
	        	   //Validación de uso de cfdi con regimen fiscal
		        if (vUsoCFDI.trim().length() > 0 && vRegimenFiscal.trim().length() > 0) {
					luCW = new ConnectionWrapper();
					PreparedStatement psStmt = luCW.prepareStatement("Alter Session Set NLS_Language='AMERICAN'");
					luResulset 	  	= psStmt.executeQuery();
									  psStmt = luCW.prepareStatement(sqlValRegUso);
					psStmt.setString(1, vUsoCFDI);
					psStmt.setString(2, vRegimenFiscal);
					luResulset = psStmt.executeQuery();
					int	pasa	=	0;
					while(luResulset.next()) {
						pasa = luResulset.getInt(1);	
					}
					if(pasa == 0) {
						throw new IllegalArgumentException("El uso de CFDI debe corresponder con el regimen fiscal, revisa el catálogo del SAT 4.0 ");
					}
					ConnectionWrapper.closeAll(new Object[] { luCW });
		        }
	        }
	   */
	      	  
	        
	        
    }else if(version.equals("3.3")) {
        	if (pinNumCampo == 27 && pstCampo.trim().length() > 0 ) {
        		
        		throw new IllegalArgumentException("El archivo CABFAC  el campo: cp_r columna " + pinNumCampo + " en la linea: " + pinNumLinea + " debe ir vacío - borra su contenido"); 
        	}
  	        if (pinNumCampo == 28 && pstCampo.trim().length() > 0 ) {
  	        	
  	        	throw new IllegalArgumentException("El archivo CABFAC el campo: RegimenFiscalR columna " + pinNumCampo + " en la linea: " + pinNumLinea + " debe ir vacío - borra su contenido"); 
  	        }
		}
      //Termina mod Kaz
  
    
    /*KAZ - TERMINA MODIFICACION CFDI 4.0*/
  }
  
  public static void validarCamposArchivosCNOMINA(int pinNumLinea, int pinNumCampo, String pstCampo) throws Exception {
    if (pinNumCampo == 0 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: ID/LABEL  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 2 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: NumEmpleado  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 3 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: CURP  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 4 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: TipoRegimen  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 6 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: FechaPago  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 7 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: FechaInicialPago  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 8 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: FechaFinalPago  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 9 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: NumDiasPagados  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 13 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: TipoContrato  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 15 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: PeriodicidadPago  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 19 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: TotalISR  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 20) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: Percepciones_TotalGravado  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: Percepciones_TotalGravado  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 21) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: Percepciones_TotalExento  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: Percepciones_TotalExento  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 22) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: Deducciones_TotalGravado  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: Deducciones_TotalGravado  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 23) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: Deducciones_TotalExento  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: Deducciones_TotalExento  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 27) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: TipoNomina  en la linea: " + 
            pinNumLinea); 
      if (pstCampo.trim().length() > 1)
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: TipoNomina  no puede ser mayor a un caracter en la linea: " + 
            pinNumLinea); 
      for (int m = 0; m < pstCampo.length(); m++) {
        if (isNumeric(String.valueOf(pstCampo.charAt(m))))
          throw new IllegalArgumentException("El archivo CNOMINA en el campo: TipoNomina  no puede ser numerico en la linea: " + 
              pinNumLinea); 
      } 
    } 
    if (pinNumCampo == 28) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: TotalPercepciones  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: TotalPercepciones  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 29) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: TotalDeducciones  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: TotalDeducciones  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 36) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: ClaveEntFed  en la linea: " + 
            pinNumLinea); 
      if (pstCampo.trim().length() > 3)
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: ClaveEntFed  no puede ser mayor a tres caracteres en la linea: " + 
            pinNumLinea); 
      for (int m = 0; m < pstCampo.length(); m++) {
        if (isNumeric(String.valueOf(pstCampo.charAt(m))))
          throw new IllegalArgumentException("El archivo CNOMINA en el campo: ClaveEntFed  no puede ser numerico en la linea: " + 
              pinNumLinea); 
      } 
    } 
    if (pinNumCampo == 37) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: TotalSueldos  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: TotalSueldos  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 41) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: TotalImpuestosRetenidos  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: TotalImpuestosRetenidos  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 45) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: JPR_IngresoAcumulable  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: JPR_IngresoAcumulable  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 46) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: JPR_IngresoNoAcumulable  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: JPR_IngresoNoAcumulable  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 47) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: SI_TotalPagado  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: SI_TotalPagado  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 48) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: SI_NumA en la linea: " + 
            pinNumLinea); 
      if (pstCampo.trim().length() > 2)
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: SI_NumA no puede ser mayor a dos caracteres en la linea: " + 
            pinNumLinea); 
      for (int m = 0; m < pstCampo.length(); m++) {
        if (!isNumeric(String.valueOf(pstCampo.charAt(m))))
          throw new IllegalArgumentException("El archivo CNOMINA en el campo: SI_NumA debe ser numerico en la linea: " + 
              pinNumLinea); 
      } 
    } 
    if (pinNumCampo == 49) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: SI_UltimoSueldoMensOrd en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: SI_UltimoSueldoMensOrd no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 50) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: SI_IngresoAcumulable en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: SI_IngresoAcumulable no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 51) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: SI_IngresoNoAcumulable  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: SI_IngresoNoAcumulable  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
    if (pinNumCampo == 52) {
      if (pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0)
        throw new IllegalArgumentException("El archivo CNOMINA no puede ir vacio el campo: SI_Descuento  en la linea: " + 
            pinNumLinea); 
      if (!esNumeroConDecimal(pstCampo.trim(), 2))
        throw new IllegalArgumentException("El archivo CNOMINA en el campo: SI_Descuento  no es decimal o no tiene el formato correcto en la linea: " + 
            pinNumLinea); 
    } 
  }
  
  public static void validarCamposArchivosCNOMINADP(int pinNumLinea, int pinNumCampo, String pstCampo) throws Exception {
    if (pinNumCampo == 0 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINADP No puede ir vacio el campo: ID/LABEL  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 1 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINADP No puede ir vacio el campo: Calificador  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 2 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINADP No puede ir vacio el campo: Tipo  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 3 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINADP No puede ir vacio el campo: Clave  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 4 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINADP No puede ir vacio el campo: Concepto  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 5 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINADP No puede ir vacio el campo: ImporteGravado  en la linea: " + 
          pinNumLinea); 
    if (pinNumCampo == 6 && (
      pstCampo.equals("") || pstCampo == null || pstCampo.isEmpty() || pstCampo.trim().length() == 0))
      throw new IllegalArgumentException("El archivo CNOMINADP No puede ir vacio el campo: ImporteExento  en la linea: " + 
          pinNumLinea); 
  }
  
  public static ArrayList<String> enviarArchivos(File[] pfNombreArchivos, int piGetIndexValu) throws Exception {
    String localFolder = "";
    String remoteDirectoryNominaAsimi = null;
    remoteDirectoryNominaAsimi = LeerProperties.sftpRemoteDirectoryNomiAsimi;
    String lstTipoOperacion = "Asimilables";
    String lstNombreArchivos = "";
    SendEDICOMProcess senEdicom = new SendEDICOMProcess();
	  SendEdicomService sendEdicomImp = senEdicom.getSendEdicomService();
	  SendFileEDICOMReqMsg sendReq = new SendFileEDICOMReqMsg();
	  SendFileEDICOMType sendType = new SendFileEDICOMType();
    
    
    
    byte b;
    int i;
    File[] arrayOfFile;
    for (i = (arrayOfFile = pfNombreArchivos).length, b = 0; b < i; ) {
      File f = arrayOfFile[b];
      
      
      
      if (f.getName().equals("CABFAC.txt")) {
    	  
    	   lfCABFAC = f;
    	  
      }
      if (f.getName().equals("CNOMINA.txt")) {
    	  
    	 lfCNOMINA = f;
    	 
      }
      if (f.getName().equals("CNOMINADP.txt")) {
    	  
    	  lfCNOMINADP = f;
      }
      
      System.out.println("RESP emp  }}}}" + lbempOIC + " "+ lbempEBS);
		if(isLbempEBS() && lbempOIC) {
			throw new IllegalArgumentException("No se pueden tener folios de EBS y de OIC en el mismo archivo.");
			
		}else {
      if (!lbempOIC) {
    	  
    	  System.out.println("enviar por sftp");  
      localFolder = f.getPath();
      localFolder = localFolder.substring(0, localFolder.lastIndexOf("\\"));
      System.out.println("localFolder "+ localFolder);  
      SFTPHelper.sendFile(f.getName(), localFolder, lstTipoOperacion, piGetIndexValu);
      lstNombreArchivos = String.valueOf(lstNombreArchivos) + f.getName() + "\n";
      }
      b++;
    } 
    
    
    
    if (lbempOIC) {
    
	if(lfCABFAC != null && lfCNOMINA != null && lfCNOMINADP != null) {
    	
    	
		 String   lfCABFACBase64 = null;
		 String   lfCNOMINABase64 = null;
		 String   lfCNOMINADPBase64 = null;
	     try {
	         FileInputStream fileInputStreamReader1 = new FileInputStream(lfCABFAC);
	         byte[] byteslfCABFAC = new byte[(int)lfCABFAC.length()];
	         fileInputStreamReader1.read(byteslfCABFAC);
	         lfCABFACBase64 = new String(Base64.encode(byteslfCABFAC));
	         
	         FileInputStream fileInputStreamReader2 = new FileInputStream(lfCNOMINA);
	         byte[] byteslfCNOMINA = new byte[(int)lfCNOMINA.length()];
	         fileInputStreamReader2.read(byteslfCNOMINA);
	         lfCNOMINABase64 = new String(Base64.encode(byteslfCNOMINA));
	         
	         FileInputStream fileInputStreamReader3 = new FileInputStream(lfCNOMINADP);
	         byte[] byteslfCNOMINADP = new byte[(int)lfCNOMINADP.length()];
	         fileInputStreamReader3.read(byteslfCNOMINADP);
	         lfCNOMINADPBase64 = new String(Base64.encode(byteslfCNOMINADP));
	         
	         
	         
	     } catch (FileNotFoundException e) {
	         e.printStackTrace();
	     } catch (IOException e) {
	         e.printStackTrace();
	     }
	System.out.println("obj lfCABFAC "+lfCABFACBase64);
    System.out.println("obj lfCNOMINA "+lfCNOMINABase64);
    System.out.println("obj lfCNOMINADP "+lfCNOMINADPBase64);
    
    sendType.setCABFAC(lfCABFACBase64);
    sendType.setCNOMINA(lfCNOMINABase64);
    sendType.setCNOMINADP(lfCNOMINADPBase64);
    
	  
	  sendReq.setSendFileEDICOM(sendType);
	  
	  
	  //Responses loresp = sendEdicomImp.sendEdicomConsolidate(sendReq);
		
		
	  mx.com.televisa.cfdis.service.sendasimiptt.Responses loresp = sendEdicomImp.sendEdicomConsolidate(sendReq);
		
		
		System.out.println("RESP new");
		
		String lsresp = convertResponse(loresp);
		System.out.println("TO XML");
		Document lorespxml = null;
		try {
			 lorespxml = convertStringToXMLDocument(lsresp);
				System.out.println("respondio "+ lorespxml.getElementsByTagName("descripcion").item(0).getTextContent());
				System.out.println("respondio length "+ lorespxml.getElementsByTagName("descripcion").getLength());
				int lilength = lorespxml.getElementsByTagName("descripcion").getLength();
						
						for (int x = 0; x < lilength; x++) { 
							
							if(lorespxml.getElementsByTagName("descripcion").item(x).getTextContent().length() < 56) {
								lierror.add( lorespxml.getElementsByTagName("descripcion").item(x).getTextContent());
							}else {
							
							lierror.add( lorespxml.getElementsByTagName("descripcion").item(x).getTextContent().substring(0,60));}
				
				}
			}
			catch(Exception e) {
				lierror.add("Fallo el envío a timbrado, favor de consultar al administrador");
				return lierror;
			}
		
		
		
	
	}
	  
    }else {
		    String lstFileName = "centinela.txt";
		    File file = new File(localFolder, lstFileName);
    try {
      if (file.createNewFile()) {
        System.out.println("El archivo\\t" + lstFileName + "\tse ha creado correctamente");
      } else {
        System.out.println("No se creo el archivo.");
      } 
    } catch (IOException ioe) {
      throw new IllegalArgumentException(ioe.getMessage());
    } 
    SFTPHelper.sendFile(lstFileName, localFolder, lstTipoOperacion, piGetIndexValu);
    if (file.delete()) {
      System.out.println("El archivo\t" + lstFileName + "\tha sido borrado satisfactoriamente");
    } else {
      System.out.println("No se creo el archivo.");
    } 
    
    
     lierror.add("Los archivos:\n" + lstNombreArchivos + "\nse han generado correctamente en la ruta:\n" + 
      remoteDirectoryNominaAsimi);
     
    
    
    }
  }
    return lierror;
  }
  
  public static ArrayList<String> BorrarCampos(File pFile) {
    BufferedReader bufferedReader = null;
    int linOcurrencia = 0;
    int linNumLinea = 0;
    try {
      bufferedReader = new BufferedReader(new FileReader(pFile));
      alNuevaLinea = new ArrayList<>();
      String lstLinea;
      while ((lstLinea = bufferedReader.readLine()) != null) {
        linOcurrencia = StringUtils.ordinalIndexOf(lstLinea, "|", 4);
        alNuevaLinea.add(linNumLinea, StringUtils.substring(lstLinea, linOcurrencia));
        System.out.println("Ocurrencia " + linNumLinea + ":  " + linOcurrencia + ":  " + (String)alNuevaLinea.get(linNumLinea));
        linNumLinea++;
      } 
      System.out.println("alLineaNueva:  " + alNuevaLinea.size());
      bufferedReader.close();
    } catch (Exception ioex) {
      String lstLinea;	
      ioex.getMessage();
    } 
    return alNuevaLinea;
  }
  
  public static void crearNuevoArchivo(File pFile) {
    try {
      FileWriter fileWriter = new FileWriter(pFile);
      BufferedWriter bufferedWriter = new BufferedWriter(fileWriter);
      PrintWriter printWriter = new PrintWriter(bufferedWriter);
      System.out.println("alNuevaLinea_size:  " + alNuevaLinea.size());
      for (int i = 0; i < alNuevaLinea.size(); i++) {
        String lstNuevaLinea = String.valueOf(alNuevaLinea.get(i)) + "\n";
        System.out.println("Linea " + i + ": " + lstNuevaLinea);
        printWriter.write(String.valueOf(i + 1) + lstNuevaLinea);
      } 
      printWriter.close();
      bufferedWriter.close();
      fileWriter.close();
    } catch (IOException e) {
      e.getMessage();
    } 
  }
  
  private static boolean isNumeric(String cadena) {
    try {
      Integer.parseInt(cadena);
      return true;
    } catch (NumberFormatException nfe) {
      return false;
    } 
  }
  
  private static boolean esNumeroConDecimal(String cad, int dd) {
    boolean hayPunto = false;
    StringBuffer parteEntera = new StringBuffer();
    StringBuffer parteDecimal = new StringBuffer();
    int i = 0, posicionDelPunto = 0;
    for (i = 0; i < cad.length(); i++) {
      if (String.valueOf(cad.charAt(i)).equals("."))
        hayPunto = true; 
    } 
    if (hayPunto) {
      posicionDelPunto = cad.indexOf('.');
    } else {
      System.out.println("No hay punto decimal");
      return false;
    } 
    if (posicionDelPunto == cad.length() - 1 || posicionDelPunto == 0) {
      System.out.println("El punto decimal esta al inicio o al final");
      return false;
    } 
    for (i = 0; i < posicionDelPunto; i++)
      parteEntera.append(cad.charAt(i)); 
    for (i = 0; i < parteEntera.length(); i++) {
      if (!Character.isDigit(parteEntera.charAt(i))) {
        System.out.println("No hay numeros en la parte entera");
        return false;
      } 
    } 
    for (i = posicionDelPunto + 1; i < cad.length(); i++)
      parteDecimal.append(cad.charAt(i)); 
    for (i = 0; i < parteDecimal.length(); i++) {
      if (!Character.isDigit(parteDecimal.charAt(i))) {
        System.out.println("No hay numeros en la parte decimal");
        return false;
      } 
    } 
    if (parteDecimal.length() < dd || parteDecimal.length() > dd) {
      System.out.println("El numero no tiene " + dd + " decimales");
      return false;
    } 
    return true;
  }
  
  private static String convertResponse(mx.com.televisa.cfdis.service.sendasimiptt.Responses toResp) 
          throws JAXBException {
		JAXBContext  loJAXBContext = 
		JAXBContext.newInstance(mx.com.televisa.cfdis.service.sendasimiptt.Responses.class);
		Marshaller   loJAXBMarshaller = loJAXBContext.createMarshaller();
		loJAXBMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");         
		//loJAXBMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
		StringWriter loSW = new StringWriter();
		loJAXBMarshaller.marshal(toResp, loSW);//loSW puede ser un OutputStream o File
		System.out.println("salida: "+loSW.toString());
		
		return loSW.toString();
		
		}

private static Document convertStringToXMLDocument(String xmlString) 
  {
      //Parser that produces DOM object trees from XML content
      DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
       
      //API to obtain DOM Document instance
      DocumentBuilder builder = null;
      try
      {
          //Create DocumentBuilder with default configuration
          builder = factory.newDocumentBuilder();
           
          //Parse the content to Document object
          Document doc = builder.parse(new InputSource(new StringReader(xmlString)));
          return doc;
      } 
      catch (Exception e) 
      {
          e.printStackTrace();
      }
      return null;
  }

public static boolean isLbempEBS() {
	return lbempEBS;
}

public static void setLbempEBS(boolean lbempEBS) {
	CargarInfoHonoAsimiNomi.lbempEBS = lbempEBS;
}
  
}
