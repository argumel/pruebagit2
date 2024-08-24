/*      */ package mx.com.televisa.cfdis.process.cargasexcel;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;

import javax.xml.bind.JAXB;
import javax.xml.bind.JAXBContext;
import javax.xml.bind.JAXBException;
import javax.xml.bind.Marshaller;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;

/*      */ import org.apache.commons.io.FileUtils;
/*      */ import org.apache.poi.xssf.usermodel.XSSFCell;
/*      */ import org.apache.poi.xssf.usermodel.XSSFRow;
/*      */ import org.apache.poi.xssf.usermodel.XSSFSheet;
/*      */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;

/*      */ import mx.com.televisa.cfdis.data.ConnectionWrapper;
import mx.com.televisa.cfdis.service.eeptt.ExecutePttConsultarExistenciaEmpresaREQUEST;
import mx.com.televisa.cfdis.service.eeptt.InputParameters;
import mx.com.televisa.cfdis.service.eeptt.OutputParameters;
import mx.com.televisa.cfdis.service.eeptt.ValidarExistenciaEmpresa;
import mx.com.televisa.cfdis.service.invptt.ValidarInformacionFactura;
import mx.com.televisa.cfdis.service.invptt.ValidarInformacionFacturaValidarInformacionFacturaREQUEST;
import mx.com.televisa.cfdis.service.sendptt.ExecuteBindQSService;
import mx.com.televisa.cfdis.service.sendptt.ExecutePtt;
import mx.com.televisa.cfdis.service.sendptt.Responses;
import mx.com.televisa.cfdis.service.sendptt.Responses.Response;
/*      */ import mx.com.televisa.cfdis.util.FilesHelper;
/*      */ import mx.com.televisa.cfdis.util.LeerProperties;
/*      */ import mx.com.televisa.cfdis.util.SFTPHelper;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CargarInfoRetTer
/*      */ {
/*      */   private static final int NUM_COLUMNS = 60;
/*      */   private static final int NUM_COLUMNS_LINES = 6;
/*      */   private static final String PIPE = "|";
/*      */   public static String forName;
/*      */   public static String diverManager;
/*      */   public static String usuario;
/*      */   public static String password;
/*      */   public static String sftpServer;
/*      */   public static String sftpPort;
/*      */   public static String sftpUser;
/*      */   public static String sftpPassword;
/*      */   public static String sftpRemoteDirectory;
/*      */   public static String sftpRemoteDirectoryNomiAsimi;
			 public static boolean lbemp;
			 public static boolean lbempEBS;
			 public static File sendEdiComfile;
			 public static String lsnombrearchivo;
			 public static ArrayList<String> lierror = new ArrayList<String>();
/*      */   
/*      */   public static ArrayList<String> doCargarInfoRetTer(String pstNombreArchivo) throws Exception {
				lbempEBS = false;
				lbemp = false;
/*   51 */     lierror = new ArrayList<String>();

				String lstLeerProperties = LeerProperties.forName;
				if (lstLeerProperties == null) {
					
					throw new IllegalArgumentException("Error al leer archivo DataConnection.");
				}
				
/*   55 */     if (pstNombreArchivo.trim().equalsIgnoreCase("")) {
	
	
/*   56 */       lierror.add( "Debe seleccionar un archivo de excel para procesar.");
/*      */     }
/*   58 */     String localFolder = "";
/*      */     
/*   60 */     String remoteDirectory = "";
/*      */     
/*   62 */     validarLayout(pstNombreArchivo);
					lsnombrearchivo= pstNombreArchivo;
/*   63 */     
/*      */     
/*   65 */     ArrayList<ExcelRecord> 		alCargarCabeceros 	= cargarCabeceros(pstNombreArchivo);
/*   66 */     ArrayList<ExcelRecordLines> 	alCargarLineas 		= cargarLineas(pstNombreArchivo);
/*   66 */     ArrayList<ExcelRecordLines> 	alCargarCFDIREl		= cargarLineasCFIRel(pstNombreArchivo); //kaz cfdi 4.0
/*      */     
/*   68 */     validarCampoNombreArchivo(alCargarCabeceros);
/*      */     
/*   70 */     validarFolioUnico(alCargarCabeceros);
/*   71 */     validarFolioUnicoEnTabla(alCargarCabeceros);
/*      */     
/*   73 */     String lstError = validarCamposRequeridos(alCargarCabeceros, alCargarLineas,alCargarCFDIREl);
/*      */     
/*   75 */     localFolder = FilesHelper.getDetaultPath();
/*   76 */     
				String lstArchivoTexto = escribirArchivo(alCargarCabeceros, alCargarLineas,alCargarCFDIREl, localFolder);
				System.out.println("RESP emp  }}}}" + lbemp + " "+ lbempEBS);
				if(isLbempEBS() && lbemp) {
					throw new IllegalArgumentException("No se pueden tener folios de EBS y de OIC en el mismo archivo.");
					
				}else {
/*      */     if (!lbemp) {
/*   78 */     String lstTipoOperacion = "Retenciones";
/*   79 */     SFTPHelper.sendFile(lstArchivoTexto, localFolder, lstTipoOperacion, 0);
				remoteDirectory = LeerProperties.sftpRemoteDirectory;
/*      */     
/*   82 */     forName = LeerProperties.forName;
/*   83 */     diverManager = LeerProperties.diverManager;
/*   84 */     usuario = LeerProperties.usuario;
/*   85 */     password = LeerProperties.password;
/*   86 */     sftpServer = LeerProperties.sftpServer;
/*   87 */     sftpPort = LeerProperties.sftpPort;
/*   88 */     sftpUser = LeerProperties.sftpUser;
/*   89 */     sftpPassword = LeerProperties.sftpPassword;
/*   90 */     sftpRemoteDirectory = LeerProperties.sftpRemoteDirectory;
/*   91 */     sftpRemoteDirectoryNomiAsimi = LeerProperties.sftpRemoteDirectoryNomiAsimi;
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*   96 */     cargarTablaConLayout(alCargarCabeceros, alCargarLineas);
/*   97 */     if (lstError.equals("OK")) {
/*   98 */       lierror.add("Se ha generado el archivo: Server:" + sftpServer + pstNombreArchivo + "\nen la ruta local de: " + localFolder + "\ny se ha enviado correctamente al servidor SFTP: " + sftpRemoteDirectory);
/*      */     }
/*   80 */     }else {
					String encodedBase64 = null;
					if(sendEdiComfile != null) {
					 File originalFile = sendEdiComfile;
				      encodedBase64 = null;
				     try {
				         FileInputStream fileInputStreamReader = new FileInputStream(originalFile);
				         byte[] bytes = new byte[(int)originalFile.length()];
				         fileInputStreamReader.read(bytes);
				         encodedBase64 = new String(Base64.encode(bytes));
				     } catch (FileNotFoundException e) {
				         e.printStackTrace();
				     } catch (IOException e) {
				         e.printStackTrace();
				     }}
					//mx.com.televisa.cfdis.service.sendptt.Responses loresp = new Responses();
					System.out.println("envio a EDICOM "+ encodedBase64);
					ExecuteBindQSService executeBindQSService = new ExecuteBindQSService();
			        ExecutePtt executePtt = executeBindQSService.getExecuteBindQSPort();
			        // Add your code to call the desired methods.
					System.out.println("genero port");
					
					
					
					mx.com.televisa.cfdis.service.sendptt.Responses loresp = executePtt.sendToEdicom(encodedBase64);
					
					
					System.out.println("RESP");
					
					String lsresp = convertResponse(loresp);
					System.out.println("TO XML");
					Document lorespxml = null;
					try {
						 lorespxml = convertStringToXMLDocument(lsresp);
						 //System.out.println("respondio "+ lorespxml.getElementsByTagName("Descripcion").item(0).getTextContent());
							//System.out.println("respondio length "+ lorespxml.getElementsByTagName("Descripcion").getLength());
							int lilength = lorespxml.getElementsByTagName("Descripcion").getLength();
							
							for (int i = 0; i < lilength; i++) { 
								
								System.out.println("long "+lorespxml.getElementsByTagName("Descripcion").item(i).getTextContent().length());
								
								if(lorespxml.getElementsByTagName("Descripcion").item(i).getTextContent().length() < 56) {
									lierror.add( lorespxml.getElementsByTagName("Descripcion").item(i).getTextContent());
								}
								else {lierror.add( lorespxml.getElementsByTagName("Descripcion").item(i).getTextContent().substring(0,60));
								}
					
					}	
					
					}
						catch(Exception e) {
							lierror.add("Fallo el envío a timbrado, favor de consultar al administrador");
							return lierror;
						}
					
					
					
					
						
					
					
					
				
					}}
					
					
					
				
				
/*  100 */     return lierror;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void cargarTablaConLayout(ArrayList<ExcelRecord> palCabeceros, ArrayList<ExcelRecordLines> palLineas) throws Exception, SQLException {
/*  106 */     for (ExcelRecord erCabeceros : palCabeceros) {
/*      */       
/*  108 */       String lstNombreArchivo = (String)erCabeceros.getCampos().get(1);
/*      */       
/*  110 */       String lstNoFacturaAP = (String)erCabeceros.getCampos().get(2);
/*  111 */       String lstRFCProvAP = (String)erCabeceros.getCampos().get(3);
/*  112 */       String lstFolioFiscAnt = (String)erCabeceros.getCampos().get(4);
/*      */       
/*  114 */       String lstFolio = (String)erCabeceros.getCampos().get(5);
/*  115 */       String lstFecHorExp = (String)erCabeceros.getCampos().get(6);
/*  116 */       String lstClaveRet = (String)erCabeceros.getCampos().get(7);
/*  117 */       String lstDesRet = (String)erCabeceros.getCampos().get(8);
/*  118 */       String lstRFC_Emp_Emi = (String)erCabeceros.getCampos().get(9);
/*  119 */       String lstRazSocEmi = (String)erCabeceros.getCampos().get(10);
/*  120 */       String lstCURP_Emi = (String)erCabeceros.getCampos().get(11);
/*  121 */       String lstNacRec = (String)erCabeceros.getCampos().get(12);
/*  122 */       String lstRFC_Recep_Nac = (String)erCabeceros.getCampos().get(13);
/*  123 */       String lstRazSocRecNac = (String)erCabeceros.getCampos().get(14);
/*  124 */       String lstCURP_Rec_Nac = (String)erCabeceros.getCampos().get(15);
/*  125 */       String lstRegIdeFisExt = (String)erCabeceros.getCampos().get(16);
/*  126 */       String lstRazSocExt = (String)erCabeceros.getCampos().get(17);
/*  127 */       String lstMesInicial = (String)erCabeceros.getCampos().get(20);
/*  128 */       String lstMesFinal = (String)erCabeceros.getCampos().get(21);
/*  129 */       String lstEjerFisc = (String)erCabeceros.getCampos().get(22);
/*  130 */       String lstMontoTotOpe = (String)erCabeceros.getCampos().get(23);
/*  131 */       String lstTotMonGra = (String)erCabeceros.getCampos().get(24);
/*  132 */       String lstMonTotExe = (String)erCabeceros.getCampos().get(25);
/*  133 */       String lstTotRet = (String)erCabeceros.getCampos().get(26);
/*  134 */       String lstContraInter = (String)erCabeceros.getCampos().get(27);
/*  135 */       String lstGanancia = (String)erCabeceros.getCampos().get(28);
/*  136 */       String lstPerdida = (String)erCabeceros.getCampos().get(29);
/*  137 */       String lstTipoDividendo = (String)erCabeceros.getCampos().get(30);
/*  138 */       String lstRetTerrNac = (String)erCabeceros.getCampos().get(31);
/*  139 */       String lstRetTerrExt = (String)erCabeceros.getCampos().get(32);
/*  140 */       String lstRetTerrExtDiviExt = (String)erCabeceros.getCampos().get(33);
/*  141 */       String lstTipoDistri = (String)erCabeceros.getCampos().get(34);
/*  142 */       String lstISR_AcrediNac = (String)erCabeceros.getCampos().get(35);
/*  143 */       String lstDivAcumNac = (String)erCabeceros.getCampos().get(36);
/*  144 */       String lstDivAcumExt = (String)erCabeceros.getCampos().get(37);
/*  145 */       String lstRemanente = (String)erCabeceros.getCampos().get(38);
/*  146 */       String lstSisFinan = (String)erCabeceros.getCampos().get(39);
/*  147 */       String lstRetPer = (String)erCabeceros.getCampos().get(40);
/*  148 */       String lstOpeFinanDeriv = (String)erCabeceros.getCampos().get(41);
/*  149 */       String lstCompInteresNominal = (String)erCabeceros.getCampos().get(42);
/*  150 */       String lstCompInteresReal = (String)erCabeceros.getCampos().get(43);
/*  151 */       String lstCompPerdida = (String)erCabeceros.getCampos().get(44);
/*  152 */       String lstCompEsBeneCobro = (String)erCabeceros.getCampos().get(45);
/*  153 */       String lstCompPaisResidencia = (String)erCabeceros.getCampos().get(46);
/*  154 */       String lstCompPE_CVE_Tipo_Contri = (String)erCabeceros.getCampos().get(47);
/*  155 */       String lstCompPEDesConcepto = (String)erCabeceros.getCampos().get(48);
/*  156 */       String lstCompPE_RFC = (String)erCabeceros.getCampos().get(49);
/*  157 */       String lstCompPE_CURP = (String)erCabeceros.getCampos().get(50);
/*  158 */       String lstCompPE_Raz_Soc_Con = (String)erCabeceros.getCampos().get(51);
/*  159 */       String lstCompPE_IND_Tipo_Contri = (String)erCabeceros.getCampos().get(52);
/*  160 */       String lstCompPE_DesConcep = (String)erCabeceros.getCampos().get(53);
/*  161 */       String lstComp_Ent_Fed = (String)erCabeceros.getCampos().get(54);
/*  162 */       String lstComp_Monto_Tot_Pag = (String)erCabeceros.getCampos().get(55);
/*  163 */       String lstComp_Monto_Grav = (String)erCabeceros.getCampos().get(56);
/*  164 */       String lstComp_Monto_Exento = (String)erCabeceros.getCampos().get(57);
/*      */       
/*  166 */       String lstCom_OD_MonGanAcu = (String)erCabeceros.getCampos().get(58);
/*  167 */       String lstCom_OD_MonPerDed = (String)erCabeceros.getCampos().get(59);
/*      */       
/*  169 */       String lstEmailRecep = (String)erCabeceros.getCampos().get(18);
/*  170 */       String lstIdVendor = (String)erCabeceros.getCampos().get(19);
/*      */       
/*  172 */       int liIndex = lstMesInicial.indexOf(".");
/*  173 */       lstMesInicial = lstMesInicial.substring(0, liIndex);
/*      */       
/*  175 */       liIndex = lstMesFinal.indexOf(".");
/*  176 */       lstMesFinal = lstMesFinal.substring(0, liIndex);
/*      */       
/*  178 */       liIndex = lstEjerFisc.indexOf(".");
/*  179 */       lstEjerFisc = lstEjerFisc.substring(0, liIndex);
/*      */       
/*      */       try {
				//kaz 01-06-2022 se pone null al final de los parametros porque no se usa retencion a terceros
/*  182 */         CargasCommon.insertarCabeceros(lstNombreArchivo, lstNoFacturaAP, lstRFCProvAP, lstFolioFiscAnt, lstFolio, lstFecHorExp, lstClaveRet, lstDesRet, lstRFC_Emp_Emi, lstRazSocEmi, lstCURP_Emi, lstNacRec, lstRFC_Recep_Nac, lstRazSocRecNac, lstCURP_Rec_Nac, lstRegIdeFisExt, lstRazSocExt, lstMesInicial, lstMesFinal, lstEjerFisc, lstMontoTotOpe, lstTotMonGra, lstMonTotExe, lstTotRet, lstContraInter, lstGanancia, lstPerdida, lstTipoDividendo, lstRetTerrNac, lstRetTerrExt, lstRetTerrExtDiviExt, lstTipoDistri, lstISR_AcrediNac, lstDivAcumNac, lstDivAcumExt, lstRemanente, lstSisFinan, lstRetPer, lstOpeFinanDeriv, lstCompInteresNominal, lstCompInteresReal, lstCompPerdida, lstCompEsBeneCobro, lstCompPaisResidencia, lstCompPE_CVE_Tipo_Contri, lstCompPEDesConcepto, lstCompPE_RFC, lstCompPE_CURP, lstCompPE_Raz_Soc_Con, lstCompPE_IND_Tipo_Contri, lstCompPE_DesConcep, lstComp_Ent_Fed, lstComp_Monto_Tot_Pag, lstComp_Monto_Grav, lstComp_Monto_Exento, lstCom_OD_MonGanAcu, lstCom_OD_MonPerDed, lstEmailRecep, lstIdVendor,null,null,null,null);
/*      */       }
/*  184 */       catch (Exception e) {
/*      */         
/*  186 */         throw new IllegalArgumentException(e.getMessage());
/*      */       } 
/*      */     } 
/*  189 */     for (ExcelRecordLines erl : palLineas) {
/*      */       
/*  191 */       String lstIdFolio = (String)erl.getCampos().get(1);
/*  192 */       String lstBaseImp = (String)erl.getCampos().get(2);
/*  193 */       String lstCveTipoImp = (String)erl.getCampos().get(3);
/*  194 */       String lstValMontoRete = (String)erl.getCampos().get(4);
/*  195 */       String lstTipoPagoRete = (String)erl.getCampos().get(5);
/*      */       
/*      */       try {
/*  198 */         CargasCommon.insertarLineas(lstIdFolio, lstBaseImp, lstCveTipoImp, lstValMontoRete, lstTipoPagoRete);
/*      */       }
/*  200 */       catch (Exception e) {
/*      */         
/*  202 */         throw new IllegalArgumentException(e.getMessage());
/*      */       } 
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void main(String[] s) {
/*      */     try {
/*  211 */       doCargarInfoRetTer("C:\\Users\\SVF15N29S\\Documents\\Layout de Carga Manual CFDI Retenciones Completo.xlsx");
/*      */     }
/*  213 */     catch (Exception e) {
/*      */       
/*  215 */       e.printStackTrace();
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validarLayout(String pstNombreArchivo) throws IllegalArgumentException {
/*      */     try {
/*  224 */       FileInputStream fis = new FileInputStream(new File(pstNombreArchivo));
/*  225 */       XSSFWorkbook wb = new XSSFWorkbook(fis);
/*  226 */       XSSFSheet sheet = wb.getSheetAt(0);
/*      */       
/*  228 */       int HEADERS_ROW = 1;
/*  229 */       int TOTALES = 23;
/*      */       
/*  231 */       XSSFRow row = sheet.getRow(HEADERS_ROW);
/*  232 */       XSSFCell cell = row.getCell(TOTALES);
/*      */       
/*  234 */       String value = cell.getStringCellValue();
/*  235 */       if (!value.equals("TOTALES")) {
/*  236 */         throw new IllegalArgumentException("El layout seleccionado no es correcto");
/*      */       }
/*      */     }
/*  239 */     catch (Exception e) {
/*      */       
/*  241 */       throw new IllegalArgumentException("El layout seleccionado no es correcto");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ArrayList<ExcelRecord> cargarCabeceros(String pstNombreArchivo) throws IOException, FileNotFoundException {
/*  248 */     FileInputStream fis = new FileInputStream(new File(pstNombreArchivo));
/*  249 */     XSSFWorkbook wb = new XSSFWorkbook(fis);
/*  250 */     XSSFSheet sheet = wb.getSheetAt(0);
/*  251 */     int START_ROW = 3;
/*  252 */     int rowIndex = START_ROW;
/*  253 */     XSSFRow row = sheet.getRow(rowIndex);
/*  254 */     ArrayList<ExcelRecord> excelTab = new ArrayList<ExcelRecord>();
/*  255 */     int i = 0;
/*  256 */     while (row != null) {
/*      */       
/*  258 */       if (row.getCell(0) != null && 
/*  259 */         !row.getCell(0).equals("")) {
/*      */         
/*  261 */         ExcelRecord registro = new ExcelRecord();
/*  262 */         for (int colIndex = 0; colIndex < 63; colIndex++) {
/*      */           
/*  264 */           XSSFCell cell = row.getCell(colIndex);
/*  265 */           String value = "";
/*  266 */           if (cell != null) {
/*  267 */             if (cell.getCellType() == 0) {
/*  268 */               value = Double.toString(cell.getNumericCellValue());
/*      */             } else {
/*  270 */               value = cell.getRichStringCellValue().getString();
/*      */             } 
/*      */           }
/*  273 */           registro.addCampo(value);
/*      */         } 
/*  275 */         excelTab.add(registro);
/*      */       } 
/*  277 */       rowIndex++;
/*  278 */       row = sheet.getRow(rowIndex);
/*  279 */       System.out.println(i);
/*  280 */       i++;
/*      */     } 
/*  282 */     return excelTab;
/*      */   }
/*      */ 
/*      */ 
/*      */   

	/**
	 * KAZ 23-05-2022 carga lineas de la tercer pestana de cfdis relacionados
	 * @param pstNombreArchivo
	 * @return
	 * @throws IOException
	 * @throws FileNotFoundException
	 */
	private static ArrayList<ExcelRecordLines> cargarLineasCFIRel(String pstNombreArchivo) throws IOException, FileNotFoundException {
		
					FileInputStream 	fis			= new FileInputStream(new File(pstNombreArchivo));
					XSSFWorkbook 		wb 			= new XSSFWorkbook(fis);
					XSSFSheet 			sheet 		= wb.getSheetAt(2);
					int 				START_ROW 	= 2;
					int					rowIndex	= START_ROW;
					XSSFRow 			row 		= sheet.getRow(rowIndex);
					ArrayList<ExcelRecordLines> excelTab = new ArrayList<ExcelRecordLines>();
					while (row != null) {
				
						if (row.getCell(0) != null && !row.getCell(0).equals("")) {
				
							ExcelRecordLines registro = new ExcelRecordLines();
							for (int colIndex = 0; colIndex < 5; colIndex++) {
				
								XSSFCell cell = row.getCell(colIndex);
								String value = "";
								if (cell != null) {
									if (cell.getCellType() == 0) {
										value = Double.toString(cell.getNumericCellValue());
									} else {
										value = cell.getRichStringCellValue().getString();
									}
								}
								registro.addCampo(value);
							}
							excelTab.add(registro);
						}
						rowIndex++;
						row = sheet.getRow(rowIndex);
					}
					return excelTab;
	}

private static ArrayList<ExcelRecordLines> cargarLineas(String pstNombreArchivo) throws IOException, FileNotFoundException {
/*  288 */     FileInputStream fis = new FileInputStream(new File(pstNombreArchivo));
/*  289 */     XSSFWorkbook wb = new XSSFWorkbook(fis);
/*  290 */     XSSFSheet sheet = wb.getSheetAt(1);
/*  291 */     int START_ROW = 2;
/*  292 */     int rowIndex = START_ROW;
/*  293 */     XSSFRow row = sheet.getRow(rowIndex);
/*  294 */     ArrayList<ExcelRecordLines> excelTab = new ArrayList<ExcelRecordLines>();
    while (row != null) {
   
    if (row.getCell(0) != null && 
   !row.getCell(0).equals("")) {
    
      ExcelRecordLines registro = new ExcelRecordLines();
     for (int colIndex = 0; colIndex < 6; colIndex++) {
       
        XSSFCell cell = row.getCell(colIndex);
        String value = "";
         if (cell != null) {
           if (cell.getCellType() == 0) {
             value = Double.toString(cell.getNumericCellValue());
           } else {
             value = cell.getRichStringCellValue().getString();
           } 
      }
         registro.addCampo(value);
} 
excelTab.add(registro);
} 
rowIndex++;
row = sheet.getRow(rowIndex);
} 
return excelTab;
   }




/*      */ 
/*      */ 
/*      */   
/*      */   private static String escribirArchivo(ArrayList<ExcelRecord> palCabeceros, ArrayList<ExcelRecordLines> palLineas,ArrayList<ExcelRecordLines> palCfdiRel, String path) throws Exception, IOException {
/*  325 */     String lstCargaManual = "M";
/*  326 */     System.setProperty("line.separator", "\n");
/*      */     
/*  328 */     File dir = new File(path);
/*  329 */     FileUtils.forceMkdir(dir);
/*      */     
/*  331 */     String fileName = (String)((ExcelRecord)palCabeceros.get(0)).getCampos().get(1);
/*      */     
/*  333 */     File file = new File(dir, fileName);
/*  334 */     FileWriter writer = new FileWriter(file);
/*  335 */     String endLine = System.getProperty("line.separator");
/*  336 */     String lstNombreArchivo = null;
/*      */     
/*  338 */     DecimalFormat dfDosDecimales = new DecimalFormat("#.##");
/*  339 */     for (ExcelRecord erCabeceros : palCabeceros) {
/*      */       String lstCom_OD_MonPerDed, lstCom_OD_MonGanAcu, lstComp_Monto_Exento, lstComp_Monto_Grav, lstComp_Monto_Tot_Pag, lstCompPerdida, lstCompInteresReal, lstCompInteresNominal, lstRemanente, lstDivAcumExt, lstDivAcumNac, lstISR_AcrediNac, lstRetTerrExtDiviExt, lstRetTerrExt, lstRetTerrNac, lstPerdida, lstGanancia, lstTotRet, lstMonTotExe, lstTotMonGra, lstMontoTotOpe;
/*  341 */       lstNombreArchivo = (String)erCabeceros.getCampos().get(1);
/*      */       
/*  343 */       String lstNoFacturaAP = (String)erCabeceros.getCampos().get(2);
/*  344 */       String lstRFCProvAP = (String)erCabeceros.getCampos().get(3);
/*  345 */       String lstFolioFiscAnt = (String)erCabeceros.getCampos().get(4);
/*      */       
/*  347 */       String lstFolio = (String)erCabeceros.getCampos().get(5);
/*  348 */       String lstFecHorExp = (String)erCabeceros.getCampos().get(6);
/*  349 */       String lstClaveRet = (String)erCabeceros.getCampos().get(7);
/*  350 */       String lstDesRet = (String)erCabeceros.getCampos().get(8);
/*  351 */       String lstRFC_Emp_Emi = (String)erCabeceros.getCampos().get(9);
/*  352 */       String lstRazSocEmi = (String)erCabeceros.getCampos().get(10);
/*  353 */       String lstCURP_Emi = (String)erCabeceros.getCampos().get(11);
/*  354 */       String lstNacRec = (String)erCabeceros.getCampos().get(12);
/*  355 */       String lstRFC_Recep_Nac = (String)erCabeceros.getCampos().get(13);
/*  356 */       String lstRazSocRecNac = (String)erCabeceros.getCampos().get(14);
/*  357 */       String lstCURP_Rec_Nac = (String)erCabeceros.getCampos().get(15);
/*  358 */       String lstRegIdeFisExt = (String)erCabeceros.getCampos().get(16);
/*  359 */       String lstRazSocExt = (String)erCabeceros.getCampos().get(17);
				 String lstVendorId =  String.valueOf(Double.valueOf(erCabeceros.getCampos().get(19)).intValue());
/*  360 */       String lstMesInicial = (String)erCabeceros.getCampos().get(20);
/*  361 */       String lstMesFinal = (String)erCabeceros.getCampos().get(21);
/*  362 */       String lstEjerFisc = (String)erCabeceros.getCampos().get(22);

				/**
				 * KAZ - se agregan 2 atribtos CFDI 4.0 CP emisor y Regimen Fiscal E
				 */
				String lstLugExpE 		= (String)erCabeceros.getCampos().get(60);
				String lstRegFiscalE 	= (String)erCabeceros.getCampos().get(61);
				//TERMINA KAZ
				
				try {
/*  366 */         lstMontoTotOpe = (String)erCabeceros.getCampos().get(23);
/*  367 */         lstMontoTotOpe = dfDosDecimales.format(Double.parseDouble(lstMontoTotOpe));
/*      */       }
/*  369 */       catch (Exception e) {
/*      */         
/*  371 */         lstMontoTotOpe = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  376 */         lstTotMonGra = (String)erCabeceros.getCampos().get(24);
/*  377 */         lstTotMonGra = dfDosDecimales.format(Double.parseDouble(lstTotMonGra));
/*      */       }
/*  379 */       catch (Exception e) {
/*      */         
/*  381 */         lstTotMonGra = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  386 */         lstMonTotExe = (String)erCabeceros.getCampos().get(25);
/*  387 */         lstMonTotExe = dfDosDecimales.format(Double.parseDouble(lstMonTotExe));
/*      */       }
/*  389 */       catch (Exception e) {
/*      */         
/*  391 */         lstMonTotExe = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  396 */         lstTotRet = (String)erCabeceros.getCampos().get(26);
/*  397 */         lstTotRet = dfDosDecimales.format(Double.parseDouble(lstTotRet));
/*      */       }
/*  399 */       catch (Exception e) {
/*      */         
/*  401 */         lstTotRet = "";
/*      */       } 
/*  403 */       String lstContraInter = (String)erCabeceros.getCampos().get(27);
/*      */ 
/*      */       
/*      */       try {
/*  407 */         lstGanancia = (String)erCabeceros.getCampos().get(28);
/*  408 */         lstGanancia = dfDosDecimales.format(Double.parseDouble(lstGanancia));
/*      */       }
/*  410 */       catch (Exception e) {
/*      */         
/*  412 */         lstGanancia = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  417 */         lstPerdida = (String)erCabeceros.getCampos().get(29);
/*  418 */         lstPerdida = dfDosDecimales.format(Double.parseDouble(lstPerdida));
/*      */       }
/*  420 */       catch (Exception e) {
/*      */         
/*  422 */         lstPerdida = "";
/*      */       } 
/*  424 */       String lstTipoDividendo = (String)erCabeceros.getCampos().get(30);
/*      */ 
/*      */       
/*      */       try {
/*  428 */         lstRetTerrNac = (String)erCabeceros.getCampos().get(31);
/*  429 */         lstRetTerrNac = dfDosDecimales.format(Double.parseDouble(lstRetTerrNac));
/*      */       }
/*  431 */       catch (Exception e) {
/*      */         
/*  433 */         lstRetTerrNac = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  438 */         lstRetTerrExt = (String)erCabeceros.getCampos().get(32);
/*  439 */         lstRetTerrExt = dfDosDecimales.format(Double.parseDouble(lstRetTerrExt));
/*      */       }
/*  441 */       catch (Exception e) {
/*      */         
/*  443 */         lstRetTerrExt = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  448 */         lstRetTerrExtDiviExt = (String)erCabeceros.getCampos().get(33);
/*  449 */         lstRetTerrExtDiviExt = dfDosDecimales.format(Double.parseDouble(lstRetTerrExtDiviExt));
/*      */       }
/*  451 */       catch (Exception e) {
/*      */         
/*  453 */         lstRetTerrExtDiviExt = "";
/*      */       } 
/*  455 */       String lstTipoDistri = (String)erCabeceros.getCampos().get(34);
/*      */ 
/*      */       
/*      */       try {
/*  459 */         lstISR_AcrediNac = (String)erCabeceros.getCampos().get(35);
/*  460 */         lstISR_AcrediNac = dfDosDecimales.format(Double.parseDouble(lstISR_AcrediNac));
/*      */       }
/*  462 */       catch (Exception e) {
/*      */         
/*  464 */         lstISR_AcrediNac = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  469 */         lstDivAcumNac = (String)erCabeceros.getCampos().get(36);
/*  470 */         lstDivAcumNac = dfDosDecimales.format(Double.parseDouble(lstDivAcumNac));
/*      */       }
/*  472 */       catch (Exception e) {
/*      */         
/*  474 */         lstDivAcumNac = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  479 */         lstDivAcumExt = (String)erCabeceros.getCampos().get(37);
/*  480 */         lstDivAcumExt = dfDosDecimales.format(Double.parseDouble(lstDivAcumExt));
/*      */       }
/*  482 */       catch (Exception e) {
/*      */         
/*  484 */         lstDivAcumExt = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  489 */         lstRemanente = (String)erCabeceros.getCampos().get(38);
/*  490 */         lstRemanente = dfDosDecimales.format(Double.parseDouble(lstRemanente));
/*      */       }
/*  492 */       catch (Exception e) {
/*      */         
/*  494 */         lstRemanente = "";
/*      */       } 
/*  496 */       String lstSisFinan = (String)erCabeceros.getCampos().get(39);
/*  497 */       String lstRetPer = (String)erCabeceros.getCampos().get(40);
/*  498 */       String lstOpeFinanDeriv = (String)erCabeceros.getCampos().get(41);
/*      */ 
/*      */       
/*      */       try {
/*  502 */         lstCompInteresNominal = (String)erCabeceros.getCampos().get(42);
/*  503 */         lstCompInteresNominal = dfDosDecimales.format(Double.parseDouble(lstCompInteresNominal));
/*      */       }
/*  505 */       catch (Exception e) {
/*      */         
/*  507 */         lstCompInteresNominal = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  512 */         lstCompInteresReal = (String)erCabeceros.getCampos().get(43);
/*  513 */         lstCompInteresReal = dfDosDecimales.format(Double.parseDouble(lstCompInteresReal));
/*      */       }
/*  515 */       catch (Exception e) {
/*      */         
/*  517 */         lstCompInteresReal = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  522 */         lstCompPerdida = (String)erCabeceros.getCampos().get(44);
/*  523 */         lstCompPerdida = dfDosDecimales.format(Double.parseDouble(lstCompPerdida));
/*      */       }
/*  525 */       catch (Exception e) {
/*      */         
/*  527 */         lstCompPerdida = "";
/*      */       } 
/*  529 */       String lstCompEsBeneCobro = (String)erCabeceros.getCampos().get(45);
/*  530 */       String lstCompPaisResidencia = (String)erCabeceros.getCampos().get(46);
/*  531 */       String lstCompPE_CVE_Tipo_Contri = (String)erCabeceros.getCampos().get(47);
/*  532 */       String lstCompPEDesConcepto = (String)erCabeceros.getCampos().get(48);
/*  533 */       String lstCompPE_RFC = (String)erCabeceros.getCampos().get(49);
/*  534 */       String lstCompPE_CURP = (String)erCabeceros.getCampos().get(50);
/*  535 */       String lstCompPE_Raz_Soc_Con = (String)erCabeceros.getCampos().get(51);
/*  536 */       String lstCompPE_IND_Tipo_Contri = (String)erCabeceros.getCampos().get(52);
/*  537 */       String lstCompPE_DesConcep = (String)erCabeceros.getCampos().get(53);
/*  538 */       String lstComp_Ent_Fed = (String)erCabeceros.getCampos().get(54);
/*      */ 
/*      */       
/*      */       try {
/*  542 */         lstComp_Monto_Tot_Pag = (String)erCabeceros.getCampos().get(55);
/*  543 */         lstComp_Monto_Tot_Pag = dfDosDecimales.format(Double.parseDouble(lstComp_Monto_Tot_Pag));
/*      */       }
/*  545 */       catch (Exception e) {
/*      */         
/*  547 */         lstComp_Monto_Tot_Pag = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  552 */         lstComp_Monto_Grav = (String)erCabeceros.getCampos().get(56);
/*  553 */         lstComp_Monto_Grav = dfDosDecimales.format(Double.parseDouble(lstComp_Monto_Grav));
/*      */       }
/*  555 */       catch (Exception e) {
/*      */         
/*  557 */         lstComp_Monto_Grav = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  562 */         lstComp_Monto_Exento = (String)erCabeceros.getCampos().get(57);
/*  563 */         lstComp_Monto_Exento = dfDosDecimales.format(Double.parseDouble(lstComp_Monto_Exento));
/*      */       }
/*  565 */       catch (Exception e) {
/*      */         
/*  567 */         lstComp_Monto_Exento = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  572 */         lstCom_OD_MonGanAcu = (String)erCabeceros.getCampos().get(58);
/*  573 */         lstCom_OD_MonGanAcu = dfDosDecimales.format(Double.parseDouble(lstCom_OD_MonGanAcu));
/*      */       }
/*  575 */       catch (Exception e) {
/*      */         
/*  577 */         lstCom_OD_MonGanAcu = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  582 */         lstCom_OD_MonPerDed = (String)erCabeceros.getCampos().get(59);
/*  583 */         lstCom_OD_MonPerDed = dfDosDecimales.format(Double.parseDouble(lstCom_OD_MonPerDed));
/*      */       }
/*  585 */       catch (Exception e) {
/*      */         
/*  587 */         lstCom_OD_MonPerDed = "";
/*      */       } 
/*  589 */       String lstEmailRecep = (String)erCabeceros.getCampos().get(18);
/*  590 */       String lstIdVendor = (String)erCabeceros.getCampos().get(19);
/*      */ 
/*      */       
/*      */       try {
/*  594 */         int liIndex = lstMesInicial.indexOf(".");
/*  595 */         lstMesInicial = lstMesInicial.substring(0, liIndex);
/*      */       }
/*  597 */       catch (Exception exception) {}
/*      */       
/*      */       try {
/*  600 */         int liIndex = lstMesFinal.indexOf(".");
/*  601 */         lstMesFinal = lstMesFinal.substring(0, liIndex);
/*      */       }
/*  603 */       catch (Exception exception) {}
/*      */       
/*      */       try {
/*  606 */         int liIndex = lstEjerFisc.indexOf(".");
/*  607 */         lstEjerFisc = lstEjerFisc.substring(0, liIndex);
/*      */       }
/*  609 */       catch (Exception exception) {}
/*      */       
/*      */       try {
/*  612 */         int liIndex = lstCompPE_IND_Tipo_Contri.indexOf(".");
/*  613 */         lstCompPE_IND_Tipo_Contri = lstCompPE_IND_Tipo_Contri.substring(0, liIndex);
/*      */       }
/*  615 */       catch (Exception exception) {}
/*      */       
/*      */       try {
/*  618 */         int liIndex = lstCompPE_CVE_Tipo_Contri.indexOf(".");
/*  619 */         lstCompPE_CVE_Tipo_Contri = lstCompPE_CVE_Tipo_Contri.substring(0, liIndex);
/*      */       }
/*  621 */       catch (Exception exception) {}
/*      */       
/*      */       try {
/*  624 */         if (lstIdVendor.contains(".")) { int liIdVendir = Integer.parseInt(lstIdVendor.substring(0, lstIdVendor.indexOf(".")));
/*  625 */         lstIdVendor = String.valueOf(liIdVendir);}
/*      */       }
/*  627 */       catch (Exception e) {
/*      */         
/*  629 */         throw new IllegalArgumentException("Error al convertir el IdVendor.");
/*      */       } 
/*  631 */       writer.write("00|" + lstNombreArchivo + "|" + lstCargaManual + endLine);
/*  632 */       writer.write("01|" + lstFolio + "|" + lstFecHorExp + "|" + lstClaveRet + "|" + lstDesRet + "|" + lstLugExpE + endLine); // KAZ - Se adiciona cp lugar del emisor
/*      */       
/*  634 */       writer.write("02|" + lstRFC_Emp_Emi + "|" + lstRazSocEmi + "|" + lstCURP_Emi + "|" + lstRegFiscalE + endLine); // KAZ - Se adiciona RegimenFiscal del emisor
/*  635 */       writer.write("03|" + lstNacRec + "|" + lstRFC_Recep_Nac + "|" + lstRazSocRecNac + "|" + lstCURP_Rec_Nac + "|" + lstRegIdeFisExt + "|" + lstRazSocExt + "|" + lstEmailRecep + "|" + lstIdVendor + endLine);
/*      */       
/*  637 */       writer.write("04|" + lstMesInicial + "|" + lstMesFinal + "|" + lstEjerFisc + endLine);
/*  638 */       writer.write("05|" + lstMontoTotOpe + "|" + lstTotMonGra + "|" + lstMonTotExe + "|" + lstTotRet + endLine);

				for (ExcelRecordLines erl : palLineas) {
/*      */         
/*  641 */         String lstMontoRet, lstBaseImp, lstFolioLinea = (String)erl.getCampos().get(1);
/*      */ 
/*      */         
/*      */         try {
/*  645 */           lstBaseImp = (String)erl.getCampos().get(2);
/*  646 */           lstBaseImp = dfDosDecimales.format(Double.parseDouble(lstBaseImp));
/*      */         }
/*  648 */         catch (Exception e) {
/*      */           
/*  650 */           lstBaseImp = "";
/*      */         } 
/*  652 */         String lstTipoImp = (String)erl.getCampos().get(3);
/*      */ 
/*      */         
/*      */         try {
/*  656 */           lstMontoRet = (String)erl.getCampos().get(4);
/*  657 */           lstMontoRet = dfDosDecimales.format(Double.parseDouble(lstMontoRet));
/*      */         }
/*  659 */         catch (Exception e) {
/*      */           
/*  661 */           lstMontoRet = "";
/*      */         } 
/*  663 */         String lstTipoPagoRete = (String)erl.getCampos().get(5);
/*  664 */         if (lstFolio.equals(lstFolioLinea)) {
/*  665 */           writer.write("06|" + lstBaseImp + "|" + lstTipoImp + "|" + lstMontoRet + "|" + lstTipoPagoRete + endLine);
/*      */         }

/*      */       } 


/*  668 */       if (lstClaveRet.equals("06")) {
/*  669 */         writer.write("07|" + lstContraInter + "|" + lstGanancia + "|" + lstPerdida + endLine);
/*      */       }
/*  671 */       if (lstClaveRet.equals("14")) {
/*  672 */         writer.write("08|" + lstTipoDividendo + "|" + lstRetTerrNac + "|" + lstRetTerrExt + "|" + lstRetTerrExtDiviExt + "|" + lstTipoDistri + "|" + lstISR_AcrediNac + "|" + lstDivAcumNac + "|" + lstDivAcumExt + "|" + lstRemanente + endLine);
/*      */       }
/*  674 */       if (lstClaveRet.equals("16")) {
/*  675 */         writer.write("09|" + lstSisFinan + "|" + lstRetPer + "|" + lstOpeFinanDeriv + "|" + lstCompInteresNominal + "|" + lstCompInteresReal + "|" + lstCompPerdida + endLine);
/*      */       }
/*  677 */       if (lstClaveRet.equals("18")) {
/*  678 */         writer.write("10|" + lstCompEsBeneCobro + "|" + lstCompPaisResidencia + "|" + lstCompPE_CVE_Tipo_Contri + "|" + lstCompPEDesConcepto + "|" + lstCompPE_RFC + "|" + lstCompPE_CURP + "|" + lstCompPE_Raz_Soc_Con + "|" + lstCompPE_IND_Tipo_Contri + "|" + lstCompPE_DesConcep + endLine);
/*      */       }
/*  680 */       if (lstClaveRet.equals("20")) {
/*  681 */         writer.write("11|" + lstComp_Ent_Fed + "|" + lstComp_Monto_Tot_Pag + "|" + lstComp_Monto_Grav + "|" + lstComp_Monto_Exento + endLine);
/*      */       }
/*  683 */       if (lstClaveRet.equals("24")) {
/*  684 */         writer.write("12|" + lstCom_OD_MonGanAcu + "|" + lstCom_OD_MonPerDed + endLine);
/*      */       }

			/*
			 * KAZ - para anexar CFDI Relacionados
			 */
					
			for (ExcelRecordLines erl : palCfdiRel) {
         
				String lstFolioLinea	= (String)erl.getCampos().get(1);	
				String lstTipoRelacion 	= (String)erl.getCampos().get(2);
				String lstUUID			= (String)erl.getCampos().get(3);
					
					//Solo valido que la columna tipo de relacion y uuid vengan llenos
					if( (lstTipoRelacion != null && !lstTipoRelacion.equals("")) && (lstUUID != null && !lstUUID.equals(""))) {
						if (lstFolio.equals(lstFolioLinea)) {
							writer.write("13|" + lstTipoRelacion + "|" + lstUUID  + endLine);
							writer.write("97" + endLine);
						}
					}
					

			} 
			
			//TERMINA CAMBIO KAZ


				if (lbemp) {
/*  684 */         writer.write("98|" + lstNoFacturaAP + "|" + lstVendorId + "|"+ endLine);
/*      */       }
				
/*  686 */       writer.write("99" + endLine);
/*      */     } 
/*  688 */     writer.write("9999|");
/*  689 */     writer.close();
				if(lbemp) {
				sendEdiComfile = file;
				
				}
///aqui envio a EDICOM
/*  690 */     return lstNombreArchivo;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String validarCamposRequeridos(ArrayList<ExcelRecord> palCabeceros, ArrayList<ExcelRecordLines> palLineas,ArrayList<ExcelRecordLines> palCfdiRel) throws IllegalArgumentException {
/*  696 */     String lstError = null;
/*      */     
/*  698 */     String lstsql = "SELECT /* FIRST_ROWS*/ COUNT (*) factvalida FROM   ap.ap_invoices_all inv WHERE  1 = 1 AND    invoice_num = ? AND    EXISTS (            SELECT 'Empresas Validas '            FROM   apps.hr_all_organization_units orgs            WHERE 0=0            AND    orgs.organization_id = inv.org_id            AND    org_id IN (SELECT orgs.org_id                             FROM   apps.xxap_cfdis_ret_emp_vw orgs                             WHERE  orgs.rfc IN (SELECT orgs2.rfc                                                 FROM   apps.xxap_cfdis_ret_emp_vw orgs2                                                 WHERE  SUBSTR(orgs2.name, 1, 3) = ?                                                 AND    orgs2.rfc IN (SELECT  NVL (description, meaning)                                                                     FROM   apps.fnd_lookup_values lv                                                                     WHERE  LANGUAGE = 'ESA'                                                                     AND    enabled_flag = 'Y'                                                                     AND    (meaning = ? OR description = ?)                                                                     AND    SYSDATE >= NVL (start_date_active, SYSDATE)                                                                     AND    SYSDATE <= NVL (end_date_active, SYSDATE)                                                                     AND    lv.lookup_type = 'XXAP_CARGA_RET_EMP')))) AND    EXISTS (            SELECT DISTINCT 'Proveedores Validos'            FROM   po.po_vendors ven                  ,po.po_vendor_sites_all vensit            WHERE  ven.vendor_id = vensit.vendor_id            AND    enabled_flag = 'Y'            AND    segment1 = ?            AND    inv.vendor_id = ven.vendor_id            AND    inv.org_id = vensit.org_id            )";
/*      */     
/*  700 */     String lstsql2 = "SELECT COUNT (*) ProvExists FROM   po.po_vendors WHERE  segment1 = ? AND    enabled_flag = 'Y' ";
/*      */     
/*  702 */     String lstsql3 = "SELECT LOOKUP_CODE   FROM Apps.FND_LOOKUP_VALUES LV  WHERE LANGUAGE = 'ESA'    AND ENABLED_FLAG = 'Y'    AND (description = ? OR meaning = ? )    AND SYSDATE >= NVL(START_DATE_ACTIVE, SYSDATE)    AND SYSDATE <= NVL(END_DATE_ACTIVE, SYSDATE)    AND LV.LOOKUP_TYPE = 'XXAP_CARGA_RET_EMP'    AND lookup_code = ?   AND ROWNUM = 1 ";
/*      */     
/*  704 */     ConnectionWrapper luCW = null;
/*  705 */     ResultSet luResulset = null;
/*  706 */     String lstNumReg = "";
/*  707 */     int linNumReg = 0;
/*  708 */     String lstNumEpresa = "";
/*      */     
/*      */     try {
/*  711 */       for (ExcelRecord erCabeceros : palCabeceros) {
/*      */         
/*  713 */         String lstNombreArchivo = (String)erCabeceros.getCampos().get(1);
/*      */         
/*  715 */         String lstNoFacturaAP = (String)erCabeceros.getCampos().get(2);
/*  716 */         String lstRFCProvAP = (String)erCabeceros.getCampos().get(3);
/*  717 */         String lstFolioFiscAnt = (String)erCabeceros.getCampos().get(4);
/*      */         
/*  719 */         String lstFolio = (String)erCabeceros.getCampos().get(5);
/*  720 */         String lstFecHorExp = (String)erCabeceros.getCampos().get(6);
/*  721 */         String lstClaveRet = (String)erCabeceros.getCampos().get(7);
/*  722 */         String lstDesRet = (String)erCabeceros.getCampos().get(8);
/*  723 */         String lstRFC_Emp_Emi = (String)erCabeceros.getCampos().get(9);
/*  724 */         String lstRazSocEmi = (String)erCabeceros.getCampos().get(10);
/*  725 */         String lstCURP_Emi = (String)erCabeceros.getCampos().get(11);
/*  726 */         String lstNacRec = (String)erCabeceros.getCampos().get(12);
/*  727 */         String lstRFC_Recep_Nac = (String)erCabeceros.getCampos().get(13);
/*  728 */         String lstRazSocRecNac = (String)erCabeceros.getCampos().get(14);
/*  729 */         String lstCURP_Rec_Nac = (String)erCabeceros.getCampos().get(15);
/*  730 */         String lstRegIdeFisExt = (String)erCabeceros.getCampos().get(16);
/*  731 */         String lstRazSocExt = (String)erCabeceros.getCampos().get(17);
				   String lstVendorId = String.valueOf(Double.valueOf(erCabeceros.getCampos().get(19)).intValue());
/*  732 */         String lstMesInicial = (String)erCabeceros.getCampos().get(20);
/*  733 */         String lstMesFinal = (String)erCabeceros.getCampos().get(21);
/*  734 */         String lstEjerFisc = (String)erCabeceros.getCampos().get(22);
/*  735 */         String lstMontoTotOpe = (String)erCabeceros.getCampos().get(23);
/*  736 */         String lstTotMonGra = (String)erCabeceros.getCampos().get(24);
/*  737 */         String lstMonTotExe = (String)erCabeceros.getCampos().get(25);
/*  738 */         String lstTotRet = (String)erCabeceros.getCampos().get(26);
/*  739 */         String lstContraInter = (String)erCabeceros.getCampos().get(27);
/*  740 */         String lstGanancia = (String)erCabeceros.getCampos().get(28);
/*  741 */         String lstPerdida = (String)erCabeceros.getCampos().get(29);
/*  742 */         String lstTipoDividendo = (String)erCabeceros.getCampos().get(30);
/*  743 */         String lstRetTerrNac = (String)erCabeceros.getCampos().get(31);
/*  744 */         String lstRetTerrExt = (String)erCabeceros.getCampos().get(32);
/*  745 */         String lstRetTerrExtDiviExt = (String)erCabeceros.getCampos().get(33);
/*  746 */         String lstTipoDistri = (String)erCabeceros.getCampos().get(34);
/*  747 */         String lstISR_AcrediNac = (String)erCabeceros.getCampos().get(35);
/*  748 */         String lstDivAcumNac = (String)erCabeceros.getCampos().get(36);
/*  749 */         String lstDivAcumExt = (String)erCabeceros.getCampos().get(37);
/*  750 */         String lstRemanente = (String)erCabeceros.getCampos().get(38);
/*  751 */         String lstSisFinan = (String)erCabeceros.getCampos().get(39);
/*  752 */         String lstRetPer = (String)erCabeceros.getCampos().get(40);
/*  753 */         String lstOpeFinanDeriv = (String)erCabeceros.getCampos().get(41);
/*  754 */         String lstCompInteresNominal = (String)erCabeceros.getCampos().get(42);
/*  755 */         String lstCompInteresReal = (String)erCabeceros.getCampos().get(43);
/*  756 */         String lstCompPerdida = (String)erCabeceros.getCampos().get(44);
/*  757 */         String lstCompEsBeneCobro = (String)erCabeceros.getCampos().get(45);
/*  758 */         String lstCompPaisResidencia = (String)erCabeceros.getCampos().get(46);
/*  759 */         String lstCompPE_CVE_Tipo_Contri = (String)erCabeceros.getCampos().get(47);
/*  760 */         String lstCompPEDesConcepto = (String)erCabeceros.getCampos().get(48);
/*  761 */         String lstCompPE_RFC = (String)erCabeceros.getCampos().get(49);
/*  762 */         String lstCompPE_CURP = (String)erCabeceros.getCampos().get(50);
/*  763 */         String lstCompPE_Raz_Soc_Con = (String)erCabeceros.getCampos().get(51);
/*  764 */         String lstCompPE_IND_Tipo_Contri = (String)erCabeceros.getCampos().get(52);
/*  765 */         String lstCompPE_DesConcep = (String)erCabeceros.getCampos().get(53);
/*  766 */         String lstComp_Ent_Fed = (String)erCabeceros.getCampos().get(54);
/*  767 */         String lstComp_Monto_Tot_Pag = (String)erCabeceros.getCampos().get(55);
/*  768 */         String lstComp_Monto_Grav = (String)erCabeceros.getCampos().get(56);
/*  769 */         String lstComp_Monto_Exento = (String)erCabeceros.getCampos().get(57);
/*      */         
/*  771 */         String lstCom_OD_MonGanAcu = (String)erCabeceros.getCampos().get(58);
/*  772 */         String lstCom_OD_MonPerDed = (String)erCabeceros.getCampos().get(59);
				/**
				 *Kaz 23-05-2022 Argumel CAMBIOS A CFDI 4.0 . Se agrega lugar de Expedicion cp y regimen fiscal del emisor 
				 */

				   String lstLugarExpE 	= (String)erCabeceros.getCampos().get(60);
				   String lstRegFiscalE	= (String)erCabeceros.getCampos().get(61);
				/*TERMINA MODIFICACION CFDI 4.0*/
				   
				   
/*      */         
/*  774 */         String lstEmailRecep = (String)erCabeceros.getCampos().get(18);
/*  775 */         String lstIdVendor = (String)erCabeceros.getCampos().get(19);
/*  776 */         String lstValidNombreArchivo = lstNombreArchivo.substring(0, 9);
/*  777 */         if (lstNombreArchivo.equals("")) {
/*      */           
/*  779 */           lstError = "Error en el campo Nombre de Archivo.No puede ir vacio.\n";
/*  780 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  782 */         if (!lstValidNombreArchivo.equalsIgnoreCase("RETM-RET-")) {
/*      */           
/*  784 */           lstError = "Error en el 'campo Nombre de Archivo'. \nEl nombre debe de empezar de la siguiente forma: RETM-RET-XXX-XXXXXXXX-XXXX.txt";
/*      */           
/*  786 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  788 */         if (lstNoFacturaAP.equals("")) {
/*      */           
/*  790 */           lstError = "Error en el campo Numero de Factura de AP No puede ir vacio.\n";
/*  791 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  793 */         if (lstRFCProvAP.equals("")) {
/*      */           
/*  795 */           lstError = "El campo RFC del proveedor de AP no puede ir vacio, por lo que este archivo no se procesara.\nFavor de revisarlo con el responsable de Egresos que lo atiende.";
/*  796 */           throw new IllegalArgumentException(lstError);
/*      */         } 

					System.out.println("despues leer excel");
/*  798 */         lstNumEpresa = lstNombreArchivo.substring(lstNombreArchivo.indexOf("-", lstNombreArchivo.indexOf("-", 0) + 1) + 1, lstNombreArchivo.indexOf("-", lstNombreArchivo.indexOf("-", lstNombreArchivo.indexOf("-", lstNombreArchivo.indexOf("-", 0) + 1)) + 1));
/*      */         
/*  800 */         
/*  803 */         
					
/*      */         //System.out.println("aqui voy :  " + lstsql2);
/*  805 */         linNumReg = 0;
/*  806 */         lstNumReg = "";

					InputParameters input = new InputParameters();
					System.out.println("aqui input empresa emisora :  " + lstRFC_Emp_Emi);
						input.setRfc(lstRFC_Emp_Emi);
						
						

						OutputParameters out = new OutputParameters();
						
						ValidarExistenciaEmpresa valemp = new ValidarExistenciaEmpresa();
						ExecutePttConsultarExistenciaEmpresaREQUEST valempi = valemp.getExecutePttConsultarExistenciaEmpresaREQUESTPt(); 
						out = valempi.validarExistenciaEmpresa(input);
						System.out.println("servicio valida empresa:  " + out.isExiste());
						System.out.println("servicio valida empresa:  " + out.getDescripcion());
						
						Boolean bandera = true;
					
					if(!out.isExiste()){
						System.out.println("validacion empresa OIC"+out.getDescripcion());
						luCW = new ConnectionWrapper();
						PreparedStatement psStmt = luCW.prepareStatement(lstsql2);
						psStmt.setObject(1, lstRFCProvAP);
						
						luResulset = psStmt.executeQuery();
/*  807 */         if (luResulset.next()) {
						
/*      */           System.out.println("aqui voy servicio ");
/*  809 */           lstNumReg = luResulset.getString("ProvExists");
/*  810 */           linNumReg = Integer.parseInt(lstNumReg);
/*  811 */           System.out.println("Num Reg Num:  " + linNumReg);
/*  812 */           if (linNumReg == 0 && lstRFCProvAP.length() > 0) {
/*      */             
/*  814 */             lstError = "No existe el proveedor  " + lstRFCProvAP + ".";
/*  815 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*      */         } 
				}//servicio valida empresa
/*  818 */        
/*  828 */         
					if(out.isExiste()) {
						System.out.println("****");
						System.out.println("voy a validar : "+lstNoFacturaAP+ " "+ lstRFC_Emp_Emi+" "+lstVendorId);	
						
						ValidarInformacionFactura valfact = new ValidarInformacionFactura();
						mx.com.televisa.cfdis.service.invptt.InputParameters factInput = new mx.com.televisa.cfdis.service.invptt.InputParameters();
						factInput.setNumeroFactura(lstNoFacturaAP);
						factInput.setRfcEmpresa(lstRFC_Emp_Emi);
						factInput.setRfcProveedor(lstVendorId);
						mx.com.televisa.cfdis.service.invptt.OutputParameters outfact = new mx.com.televisa.cfdis.service.invptt.OutputParameters ();
						ValidarInformacionFacturaValidarInformacionFacturaREQUEST valfacti = valfact.getValidarInformacionFacturaValidarInformacionFacturaREQUESTPt();
						
						outfact = valfacti.validarInformacionFactura(factInput);
						
						if(!outfact.isExisteFactura()) {
							lstError = outfact.getDescripcion();
							System.out.println("error val factura: "+lstError);	
							throw new IllegalArgumentException(lstError);
												 
/*      */         }
					
						}else {
							
							ConnectionWrapper.closeAll(new Object[] { luCW });

							luCW = new ConnectionWrapper();
							PreparedStatement psStmt = luCW.prepareStatement(lstsql);
							psStmt.setObject(1, lstNoFacturaAP);
							psStmt.setObject(2, lstNumEpresa);
							psStmt.setObject(3, lstRFC_Emp_Emi);
							psStmt.setObject(4, lstRFC_Emp_Emi);
							psStmt.setObject(5, lstRFCProvAP); 


							 					luResulset = psStmt.executeQuery();		
					if (luResulset.next()) {
/*      */           
/*  830 */           lstNumReg = luResulset.getString("FactValida");
/*  831 */           linNumReg = Integer.parseInt(lstNumReg);
/*  832 */           System.out.println("Num Reg Num:  " + linNumReg);
/*  833 */           if (linNumReg == 0) {
/*      */             
/*  835 */             lstError = "No coincide la factura " + lstNoFacturaAP + " con el proveedor " + lstRFCProvAP + " con la empresa emisora " + lstNumEpresa + ".";
/*  836 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  839 */         ConnectionWrapper.closeAll(new Object[] { luCW });
/*      */         
/*  841 */         luCW = new ConnectionWrapper();
/*  842 */          psStmt = luCW.prepareStatement(lstsql3);
/*  843 */         psStmt.setObject(1, lstRFC_Emp_Emi);
/*  844 */         psStmt.setObject(2, lstRFC_Emp_Emi);
/*  845 */         psStmt.setObject(3, lstNumEpresa);
/*  846 */         luResulset = psStmt.executeQuery();
/*  847 */         String lstEmpresa = "";
/*  848 */         if (luResulset.next()) {
/*      */           
/*  850 */           lstEmpresa = luResulset.getString("LOOKUP_CODE");
/*  851 */           System.out.println("Num Empresa:  " + lstEmpresa);
/*  852 */           if (lstEmpresa == null) {
/*      */             
/*  854 */             lstError = "La empresa emisora " + lstRFC_Emp_Emi + " No esta registrada para esta instancia.";
/*  855 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  857 */           if (lstEmpresa.equalsIgnoreCase(""))
/*      */           {
/*  859 */             lstError = "No coincide el RFC de la empresa emisora con el numero de empresa especificado en el nombre del archivo.";
/*  860 */             throw new IllegalArgumentException(lstError);
/*      */           }
/*      */         
/*      */         } else {
/*      */           
/*  865 */           lstError = "La empresa emisora " + lstRFC_Emp_Emi + " No esta registrada para esta instancia.";
/*  866 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  868 */         ConnectionWrapper.closeAll(new Object[] { luCW });
					lbempEBS = true;
						}//fin EBS
					
					
/*  869 */         if (lstFolio.equals("")) {
/*      */           
/*  871 */           lstError = "Error en el campo Folio.\nNo puede ir vacio.\n";
/*  872 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  874 */         if (lstFecHorExp.equals("")) {
/*      */           
/*  876 */           lstError = "Error en el campo Fecha y Hora de Expedicion.\nNo puede ir vacio.";
/*  877 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  879 */         if (lstClaveRet.equals("")) {
/*      */           
/*  881 */           lstError = "Error en el campo Clave Retencion.\nNo puede ir vacio.\n";
/*  882 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  884 */         if (lstRFC_Emp_Emi.equals("")) {
/*      */           
/*  886 */           lstError = "Error en el campo RFC Empresa Emisora.\nNo puede ir vacio.\n";
/*  887 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  889 */         if (lstNacRec.equals("")) {
/*      */           
/*  891 */           lstError = "Error en el campo Nacionalidad.\nNo puede ir vacio.\n";
/*  892 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  894 */         if (lstNacRec.equals("Nacional") && 
/*  895 */           lstRFC_Recep_Nac.equals("")) {
/*      */           
/*  897 */           lstError = "Error en el campo RFC Receptor Nacional.\nNo puede ir vacio.\nPorque en el campo Nacionalidad es Nacional.";
/*      */           
/*  899 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  901 */         if (lstNacRec.equals("Extranjero") && 
/*  902 */           lstRazSocExt.equals("")) {
/*      */           
/*  904 */           lstError = "Error en el campo Razon Social Extranjero.\nNo puede ir vacio.\nPorque en el campo Nacionalidad es Extranjero.";
/*      */           
/*  906 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  908 */         if (lstMesInicial.equals("")) {
/*      */           
/*  910 */           lstError = "Error en el campo Mes Inicial.\nNo puede ir vacio.\n";
/*  911 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  913 */         if (lstMesFinal.equals("")) {
/*      */           
/*  915 */           lstError = "Error en el campo Mes Final.\nNo puede ir vacio.\n";
/*  916 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  918 */         if (lstEjerFisc.equals("")) {
/*      */           
/*  920 */           lstError = "Error en el campo Ejercicio Fiscal.\nNo puede ir vacio.\n";
/*  921 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  923 */         if (lstMontoTotOpe.equals("")) {
/*      */           
/*  925 */           lstError = "Error en el campo Monto Total de la Operacion.\nNo puede ir vacio.\n";
/*  926 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  928 */         if (lstTotMonGra.equals("")) {
/*      */           
/*  930 */           lstError = "Error en el campo Monto Gravado.\nNo puede ir vacio.\n";
/*  931 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  933 */         if (lstTotRet.equals("")) {
/*      */           
/*  935 */           lstError = "Error en el campo Total Retenciones.\nNo puede ir vacio.\n";
/*  936 */           throw new IllegalArgumentException(lstError);
/*      */         }

					/*
					 * KAZ - MODIFICACION 4.0
					 */

					if (lstLugarExpE.equals("")) {

					 lstError = "Error en el campo Lugar de Expedición E (CP)\nNo puede ir vacio.\n";
					throw new IllegalArgumentException(lstError);
					}
					
					if (lstRegFiscalE.equals("")) {

						 lstError = "Error en el campo RegimenFiscalE\nNo puede ir vacio.\n";
						throw new IllegalArgumentException(lstError);
					}
					
					/*KAZ - TERMINA CAMBIO CFDI 4.0 */



/*  938 */         for (ExcelRecordLines erl : palLineas) {
/*      */           
/*  940 */           String lstFolioLinea = (String)erl.getCampos().get(1);
/*  941 */           String lstMontoRet = (String)erl.getCampos().get(4);
/*  942 */           String lstTipoPagoRete = (String)erl.getCampos().get(5);
/*  943 */           if (lstFolioLinea.equals("")) {
/*      */             
/*  945 */             lstError = "Error en el campo Folio Linea.\nNo puede ir vacio.\nDe la Hoja de Trabajo de Retenciones.";
/*      */             
/*  947 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  949 */           if (lstMontoRet.equals("")) {
/*      */             
/*  951 */             lstError = "Error en el campo Monto retenido.\nNo puede ir vacio.\nDe la Hoja de Trabajo de Retenciones.";
/*      */             
/*  953 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  955 */           if (lstTipoPagoRete.equals("")) {
/*      */             
/*  957 */             lstError = "Error en el campo Tipo Pago Retenci��?n.\nNo puede ir vacio.\nDe la Hoja de Trabajo de Retenciones.";
/*      */             
/*  959 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*      */         }

				
					/**
					 * KAZ - 22-05-2022
					 * Itera la pestana de CFDI Rel para que no traiga vacios
					 */
				/*	for (ExcelRecordLines erl : palCfdiRel) {
          
						String lstFolioLinea 	= (String) erl.getCampos().get(1);
						String lstTipoRel		= (String) erl.getCampos().get(2);
						String lstUUID 			= (String) erl.getCampos().get(3);
						
						if (lstFolioLinea.equals("")) {

							lstError = "Error en el campo Folio Linea.\nNo puede ir vacio.\nDe la Hoja de Trabajo de CFDI Rel.";

							throw new IllegalArgumentException(lstError);
						}
						if (lstTipoRel.equals("")) {

							lstError = "Error en el campo Tipo de relación.\nNo puede ir vacio.\nDe la Hoja de Trabajo de CFDI Rel.";

							throw new IllegalArgumentException(lstError);
						}
						if (lstUUID.equals("")) {

							lstError = "Error en el campo UUID.\nNo puede ir vacio.\nDe la Hoja de Trabajo de CFDI Rel.";

							throw new IllegalArgumentException(lstError);
						}
					}

					*/

/*  962 */         if (lstClaveRet.equals("06")) {
/*      */           
/*  964 */           if (lstContraInter.equals("")) {
/*      */             
/*  966 */             lstError = "Error en el campo Contrato Intermediacion.\nNo puede ir vacio.\n";
/*  967 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  969 */           if (lstGanancia.equals("")) {
/*      */             
/*  971 */             lstError = "Error en el campo Ganancia.\nNo puede ir vacio.\n";
/*  972 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  974 */           if (lstPerdida.equals("")) {
/*      */             
/*  976 */             lstError = "Error en el campo Perdida.\nNo puede ir vacio.\n";
/*  977 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*      */         } 
/*  980 */         if (lstClaveRet.equals("14")) {
/*      */           
/*  982 */           if (lstTipoDividendo.equals("")) {
/*      */             
/*  984 */             lstError = "Error en el campo Tipo de Dividendo.\nNo puede ir vacio.\n";
/*  985 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  987 */           if (lstRetTerrNac.equals("")) {
/*      */             
/*  989 */             lstError = "Error en el campo Retencion Territorio Nacional.\nNo puede ir vacio.\n";
/*  990 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  992 */           if (lstRetTerrExt.equals("")) {
/*      */             
/*  994 */             lstError = "Error en el campo Retencion Territorio Extranjero.\nNo puede ir vacio.\n";
/*  995 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  997 */           if (lstTipoDistri.equals("")) {
/*      */             
/*  999 */             lstError = "Error en el campo Tipo Distribucion.\nNo puede ir vacio.\n";
/* 1000 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*      */         } 
/* 1003 */         if (lstClaveRet.equals("16")) {
/*      */           
/* 1005 */           if (lstSisFinan.equals("")) {
/*      */             
/* 1007 */             lstError = "Error en el campo Sistema Financiero.\nNo puede ir vacio.\n";
/* 1008 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1010 */           if (lstRetPer.equals("")) {
/*      */             
/* 1012 */             lstError = "Error en el campo Retiro en el Periodo.\nNo puede ir vacio.\n";
/* 1013 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1015 */           if (lstOpeFinanDeriv.equals("")) {
/*      */             
/* 1017 */             lstError = "Error en el campo Operaciones Financieras Derivadas.\nNo puede ir vacio.\n";
/* 1018 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1020 */           if (lstCompInteresNominal.equals("")) {
/*      */             
/* 1022 */             lstError = "Error en el campo Interes Nominal.\nNo puede ir vacio.\n";
/* 1023 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1025 */           if (lstCompInteresReal.equals("")) {
/*      */             
/* 1027 */             lstError = "Error en el campo Interes Real.\nNo puede ir vacio.\n";
/* 1028 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1030 */           if (lstCompPerdida.equals("")) {
/*      */             
/* 1032 */             lstError = "Error en el campo Perdida.\nNo puede ir vacio.\n";
/* 1033 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*      */         } 
/* 1036 */         if (lstClaveRet.equals("18") && 
/* 1037 */           lstCompEsBeneCobro.equals("")) {
/*      */           
/* 1039 */           lstError = "Error en el campo Es Beneficiario del Cobro.\nNo puede ir vacio.\n";
/* 1040 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/* 1042 */         if (lstClaveRet.equals("20")) {
/*      */           
/* 1044 */           if (lstComp_Ent_Fed.equals("")) {
/*      */             
/* 1046 */             lstError = "Error en el campo Entidad Federativa.\nNo puede ir vacio.\n";
/* 1047 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1049 */           if (lstComp_Monto_Tot_Pag.equals("")) {
/*      */             
/* 1051 */             lstError = "Error en el campo Monto Total Pago.\nNo puede ir vacio.\n";
/* 1052 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1054 */           if (lstComp_Monto_Grav.equals("")) {
/*      */             
/* 1056 */             lstError = "Error en el campo Monto Gravado.\nNo puede ir vacio.\n";
/* 1057 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1059 */           if (lstComp_Monto_Exento.equals("")) {
/*      */             
/* 1061 */             lstError = "Error en el campo Monto Excento.\nNo puede ir vacio.\n";
/* 1062 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*      */         } 
/* 1065 */         if (lstClaveRet.equals("24")) {
/*      */           
/* 1067 */           if (lstCom_OD_MonGanAcu.equals("")) {
/*      */             
/* 1069 */             lstError = "Error en el campo Monto Ganancia Acumulable.\nNo puede ir vacio.\n";
/* 1070 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1072 */           if (lstCom_OD_MonPerDed.equals(""))
/*      */           {
/* 1074 */             lstError = "Error en el campo Monto Perdida Deducible.\nNo puede ir vacio.\n";
/* 1075 */             throw new IllegalArgumentException(lstError);
/*      */           }
/*      */         
/*      */         } 
/*      */       } 
/* 1080 */     } 
				}catch (Exception e) {
/*      */       
/* 1082 */       if (lstError == null) {
/* 1083 */         throw new IllegalArgumentException(e.toString());
/*      */       }
/* 1085 */       throw new IllegalArgumentException(lstError);
/*      */     } 
/* 1087 */     return "OK";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validarNombreArchivo(String pstNombreArchivo) throws IllegalArgumentException {
/* 1093 */     int linCountFiles = 0;
/* 1094 */     String valueFile = null;
/*      */     
/*      */     try {
/* 1097 */       FileInputStream fis = new FileInputStream(new File(pstNombreArchivo));
/* 1098 */       XSSFWorkbook wb = new XSSFWorkbook(fis);
/* 1099 */       XSSFSheet sheet = wb.getSheetAt(0);
/* 1100 */       int liRow = 3;
/* 1101 */       int liCol = 1;
/* 1102 */       XSSFRow row = sheet.getRow(liRow);
/* 1103 */       XSSFCell cell = row.getCell(liCol);
/* 1104 */       valueFile = cell.getStringCellValue();
/*      */       
/* 1106 */       linCountFiles = CargasCommon.validarNombreArchivo(valueFile);
/* 1107 */       if (linCountFiles > 0) {
/* 1108 */         throw new IllegalArgumentException("El archivo: " + valueFile + " ya ha sido generado previamente.");
/*      */       }
/* 1110 */       System.out.println("ECM23 VNA   " + linCountFiles);
/*      */     }
/* 1112 */     catch (SQLException ex) {
/*      */       
/* 1114 */       throw new IllegalArgumentException("No se ha podido conectar a la Base de Datos:  " + ex.getMessage() + "\n" + ex.getSQLState());
/*      */     }
/* 1116 */     catch (Exception ex) {
/*      */       
/* 1118 */       throw new IllegalArgumentException(ex.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validarFolioUnico(ArrayList<ExcelRecord> palCabeceros) throws IllegalArgumentException {
/* 1125 */     ArrayList<String> alComparar = new ArrayList<String>();
/* 1126 */     String lstError = null;
/* 1127 */     int liComparar = 0;
/*      */     
/*      */     try {
/* 1130 */       for (ExcelRecord erCabeceros : palCabeceros)
/*      */       {
/* 1132 */         String lstFolio = (String)erCabeceros.getCampos().get(5);
/* 1133 */         if (liComparar == 0) {
/*      */           
/* 1135 */           alComparar.add(liComparar, lstFolio);
/*      */         }
/*      */         else {
/*      */           
/* 1139 */           for (int liConta = 0; liConta < alComparar.size(); liConta++) {
/* 1140 */             if (lstFolio.equals(alComparar.get(liConta))) {
/*      */               
/* 1142 */               lstError = "El Archivo: " + lstFolio + " ya existe en EBS.";
/* 1143 */               throw new IllegalArgumentException(lstError);
/*      */             } 
/*      */           } 
/* 1146 */           alComparar.add(liComparar, lstFolio);
/*      */         } 
/* 1148 */         liComparar++;
/*      */       }
/*      */     
/* 1151 */     } catch (Exception e) {
/*      */       
/* 1153 */       throw new IllegalArgumentException(lstError);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validarFolioUnicoEnTabla(ArrayList<ExcelRecord> palCabeceros) throws IllegalArgumentException {
/* 1160 */     String lstError = null;
				boolean lovalemp = false;
				ArrayList<String> loEmpressa =new ArrayList<String>();
/* 1161 */     for (ExcelRecord er : palCabeceros) {
/*      */       
/* 1163 */       String lstFolio = (String)er.getCampos().get(5);
				if(!loEmpressa.contains((String)er.getCampos().get(9))) {
/*      */        lovalemp = validarEmpresaOIC((String)er.getCampos().get(9));
				}
				if(lovalemp) {
					lbemp = true;
				}
				else {
					lbemp = false;
					
				}
/*      */       try {
				
				if(!lovalemp) {	
					validarNombreArchivo(lsnombrearchivo);
/* 1166 */         CargasCommon.validarFolioUnicoEnTablaCC(lstFolio);}
/*      */       }
/* 1168 */       catch (SQLException ex) {
/*      */         
/* 1170 */         lstError = "No se ha podido conectar a la Base de Datos:\n" + ex.getMessage() + "\n" + ex.getSQLState();
/*      */         
/* 1172 */         throw new IllegalArgumentException(lstError);
/*      */       }
/* 1174 */       catch (Exception ex) {
/*      */          System.out.println("ERROR+++  ");
					ex.printStackTrace();
/* 1176 */         lstError = "El archivo " + lstFolio + " ya existe en EBS.";
/* 1177 */         throw new IllegalArgumentException(lstError);
/*      */       } 
					loEmpressa.add((String)er.getCampos().get(9));
					System.out.println("tamaño lista: "+loEmpressa.size());
					System.out.println("lista: "+loEmpressa.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validarCampoNombreArchivo(ArrayList<ExcelRecord> palCabeceros) throws IllegalArgumentException {
/* 1185 */     String lstFileNameAnt = "";
/* 1186 */     String lstFileNameAct = "";
/* 1187 */     boolean bFileNameFlag = true;
/*      */     
/* 1189 */     lstFileNameAnt = (String)((ExcelRecord)palCabeceros.get(0)).getCampos().get(1);
/* 1190 */     for (ExcelRecord record : palCabeceros) {
/*      */       
/* 1192 */       lstFileNameAct = (String)record.getCampos().get(1);
/* 1193 */       if (!lstFileNameAnt.equals(lstFileNameAct)) {
/* 1194 */         bFileNameFlag = false;
/*      */       }
/*      */     } 
/* 1197 */     if (!bFileNameFlag)
/* 1198 */       throw new IllegalArgumentException("El campo Nombre de Archivo debe ser el mismo en todos los registros."); 
/*      */   }

			private static boolean validarEmpresaOIC(String lstRFC_Emp_Emi) throws IllegalArgumentException{
				
				InputParameters input = new InputParameters();
				input.setRfc(lstRFC_Emp_Emi);
				OutputParameters out = new OutputParameters();
				System.out.println("valido antes de folio");
				ValidarExistenciaEmpresa valemp = new ValidarExistenciaEmpresa();
				ExecutePttConsultarExistenciaEmpresaREQUEST valempi = valemp.getExecutePttConsultarExistenciaEmpresaREQUESTPt(); 
				out = valempi.validarExistenciaEmpresa(input);
				System.out.println("empresa existe: "+out.isExiste());
				
				if(out.isExiste()) {lbemp = true;}else {
					lbemp = true;
					
				}
					
				return out.isExiste();
				
			}
			public static boolean isLbemp() {
				return lbemp;
			}
			public static void setLbemp(boolean lbemp) {
				CargarInfoRetTer.lbemp = lbemp;
			}
			private static String convertResponse(mx.com.televisa.cfdis.service.sendptt.Responses toResp) 
			        throws JAXBException {
					JAXBContext  loJAXBContext = 
					JAXBContext.newInstance(mx.com.televisa.cfdis.service.sendptt.Responses.class);
					Marshaller   loJAXBMarshaller = loJAXBContext.createMarshaller();
					loJAXBMarshaller.setProperty(Marshaller.JAXB_ENCODING, "UTF-8");         
					//loJAXBMarshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
					StringWriter loSW = new StringWriter();
					loJAXBMarshaller.marshal(toResp, loSW);//loSW puede ser un OutputStream o File
					System.out.println(loSW.toString());
					System.out.println("FIN RESPONSE");
					
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
				CargarInfoRetTer.lbempEBS = lbempEBS;
			}
			
			
			
/*      */ }

