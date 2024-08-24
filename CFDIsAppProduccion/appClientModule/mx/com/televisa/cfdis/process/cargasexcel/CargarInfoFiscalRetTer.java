/*      */ package mx.com.televisa.cfdis.process.cargasexcel;
/*      */ 
/*      */ import java.io.File;
/*      */ import java.io.FileInputStream;
/*      */ import java.io.FileNotFoundException;
/*      */ import java.io.FileWriter;
/*      */ import java.io.IOException;
import java.io.StringReader;
import java.io.StringWriter;
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

/*      */ import mx.com.televisa.cfdis.data.ConnectionWrapper;
import mx.com.televisa.cfdis.service.eeptt.ExecutePttConsultarExistenciaEmpresaREQUEST;
import mx.com.televisa.cfdis.service.eeptt.InputParameters;
import mx.com.televisa.cfdis.service.eeptt.OutputParameters;
import mx.com.televisa.cfdis.service.eeptt.ValidarExistenciaEmpresa;
import mx.com.televisa.cfdis.service.sendptt.ExecuteBindQSService;
import mx.com.televisa.cfdis.service.sendptt.ExecutePtt;
/*      */ import mx.com.televisa.cfdis.util.FilesHelper;
/*      */ import mx.com.televisa.cfdis.util.LeerProperties;
/*      */ import mx.com.televisa.cfdis.util.SFTPHelper;
/*      */ import org.apache.commons.io.FileUtils;
/*      */ import org.apache.poi.xssf.usermodel.XSSFCell;
/*      */ import org.apache.poi.xssf.usermodel.XSSFRow;
/*      */ import org.apache.poi.xssf.usermodel.XSSFSheet;
/*      */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.xml.sax.InputSource;

import com.sun.org.apache.xerces.internal.impl.dv.util.Base64;
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ public class CargarInfoFiscalRetTer
/*      */ {
/*      */   private static final int NUM_COLUMNS = 60;
/*      */   private static final int NUM_COLUMNS_LINES = 6;
/*      */   private static final String PIPE = "|";
			 private static File fileBs64 = null; 
			 private static boolean lovalemp = false;
			 public static boolean lbempEBS;
			 private static String  lsnombrearchivo = null;
			 private static ArrayList<String> lierror = new ArrayList<String>();
/*      */   
/*      */   public static ArrayList<String> doCargarInfoFiscalRetTer(String pstNombreArchivo) throws Exception {
/*   34 */     lierror = new ArrayList<String>();
				lovalemp = false;
				lbempEBS = false;
				String lstLeerProperties = LeerProperties.forName;
/*   35 */     if (lstLeerProperties == null) {
/*   36 */       throw new IllegalArgumentException("Error al leer archivo DataConnection.");
/*      */     }
/*   38 */     if (pstNombreArchivo.trim().equalsIgnoreCase("")) {
/*   39 */       lierror.add( "Debe seleccionar un archivo de excel para procesar.");
					return lierror;
/*      */     }
/*   41 */     String localFolder = "";
/*      */     
/*   43 */     String remoteDirectory = "";
/*      */     
					System.out.println("inicio proceso ");
/*   45 */     validarLayout(pstNombreArchivo);
/*   46 */     
/*      */     
/*   48 */     ArrayList<ExcelRecord> alCargarCabeceros 		= cargarCabeceros(pstNombreArchivo);
/*   49 */     ArrayList<ExcelRecordLines> alCargarLineas 		= cargarLineas(pstNombreArchivo);
			   ArrayList<ExcelRecordLines> 	alCargarCFDIREl		= cargarLineasCFIRel(pstNombreArchivo); //kaz cfdi 4.0
/*      */     
					lsnombrearchivo = pstNombreArchivo;
/*   51 */     validarCampoNombreArchivo(alCargarCabeceros);
/*      */     System.out.println("valido folio ");
/*   53 */     validarFolioUnico(alCargarCabeceros);
/*   54 */     validarFolioUnicoEnTabla(alCargarCabeceros);
/*      */     
				System.out.println("valido campos ");
/*   56 */     String lstError = validarCamposRequeridos(alCargarCabeceros, alCargarLineas);
/*      */     
/*   58 */     localFolder = FilesHelper.getDetaultPath();
/*   59 */     String lstArchivoTexto = escribirArchivo(alCargarCabeceros, alCargarLineas,alCargarCFDIREl, localFolder);
				System.out.println("localFolder " + localFolder);
				System.out.println("RESP "+ isLbempEBS() +"  "+lovalemp );
/*      */     if(isLbempEBS() && lovalemp) {
							throw new IllegalArgumentException("No se pueden tener folios de EBS y de OIC en el mismo archivo.");
	
				}else {
				
				if(!lovalemp) {
/*   61 */     String lstTipoOperacion = "Retenciones";
			   SFTPHelper.sendFile(lstArchivoTexto, localFolder, lstTipoOperacion, 0);
/*   63 */     remoteDirectory = LeerProperties.sftpRemoteDirectory;

				System.out.println("envie sftp "+ lstArchivoTexto+ "folder" +localFolder + "op "+lstTipoOperacion );
/*      */     
/*   65 */     cargarTablaConLayout(alCargarCabeceros, alCargarLineas,alCargarCFDIREl);
/*   66 */     if (lstError.equals("OK")) {
/*   67 */        lierror.add( "Se ha generado el archivo: " + pstNombreArchivo + "\nen la ruta local de: " + localFolder + "\ny se ha enviado correctamente al servidor SFTP: " + remoteDirectory);
/*      */     			return lierror;
				}
			}else {
					String encodedBase64 = null;
	
					if(fileBs64 != null) {
					 File originalFile = fileBs64;
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
				     	}
				     }
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
		 System.out.println("respondio "+ lorespxml.getElementsByTagName("Descripcion").item(0).getTextContent());
			System.out.println("respondio length "+ lorespxml.getElementsByTagName("Descripcion").getLength());
			int lilength = lorespxml.getElementsByTagName("Descripcion").getLength();
			
			for (int i = 0; i < lilength; i++) { 
				
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
				
/*   69 */     return lierror;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void cargarTablaConLayout(ArrayList<ExcelRecord> palCabeceros, 
													   ArrayList<ExcelRecordLines> palLineas,
													   ArrayList<ExcelRecordLines> paLineasCfdiRel) throws Exception, SQLException {
/*   75 */     for (ExcelRecord erCabeceros : palCabeceros) {
/*      */       
/*   77 */       String lstNombreArchivo = (String)erCabeceros.getCampos().get(1);
/*      */       
/*   79 */       String lstNoFacturaAP = (String)erCabeceros.getCampos().get(2);
/*   80 */       String lstRFCProvAP = (String)erCabeceros.getCampos().get(3);
/*   81 */       String lstFolioFiscAnt = (String)erCabeceros.getCampos().get(4);
/*      */       
/*   83 */       String lstFolio = (String)erCabeceros.getCampos().get(5);
/*   84 */       String lstFecHorExp = (String)erCabeceros.getCampos().get(6);
/*   85 */       String lstClaveRet = (String)erCabeceros.getCampos().get(7);
/*   86 */       String lstDesRet = (String)erCabeceros.getCampos().get(8);
/*   87 */       String lstRFC_Emp_Emi = (String)erCabeceros.getCampos().get(9);
/*   88 */       String lstRazSocEmi = (String)erCabeceros.getCampos().get(10);
/*   89 */       String lstCURP_Emi = (String)erCabeceros.getCampos().get(11);
/*   90 */       String lstNacRec = (String)erCabeceros.getCampos().get(12);
/*   91 */       String lstRFC_Recep_Nac = (String)erCabeceros.getCampos().get(13);
/*   92 */       String lstRazSocRecNac = (String)erCabeceros.getCampos().get(14);
/*   93 */       String lstCURP_Rec_Nac = (String)erCabeceros.getCampos().get(15);
/*   94 */       String lstRegIdeFisExt = (String)erCabeceros.getCampos().get(16);
/*   95 */       String lstRazSocExt = (String)erCabeceros.getCampos().get(17);
/*   96 */       String lstMesInicial = (String)erCabeceros.getCampos().get(20);
/*   97 */       String lstMesFinal = (String)erCabeceros.getCampos().get(21);
/*   98 */       String lstEjerFisc = (String)erCabeceros.getCampos().get(22);
/*   99 */       String lstMontoTotOpe = (String)erCabeceros.getCampos().get(23);
/*  100 */       String lstTotMonGra = (String)erCabeceros.getCampos().get(24);
/*  101 */       String lstMonTotExe = (String)erCabeceros.getCampos().get(25);
/*  102 */       String lstTotRet = (String)erCabeceros.getCampos().get(26);
/*  103 */       String lstContraInter = (String)erCabeceros.getCampos().get(27);
/*  104 */       String lstGanancia = (String)erCabeceros.getCampos().get(28);
/*  105 */       String lstPerdida = (String)erCabeceros.getCampos().get(29);
/*  106 */       String lstTipoDividendo = (String)erCabeceros.getCampos().get(30);
/*  107 */       String lstRetTerrNac = (String)erCabeceros.getCampos().get(31);
/*  108 */       String lstRetTerrExt = (String)erCabeceros.getCampos().get(32);
/*  109 */       String lstRetTerrExtDiviExt = (String)erCabeceros.getCampos().get(33);
/*  110 */       String lstTipoDistri = (String)erCabeceros.getCampos().get(34);
/*  111 */       String lstISR_AcrediNac = (String)erCabeceros.getCampos().get(35);
/*  112 */       String lstDivAcumNac = (String)erCabeceros.getCampos().get(36);
/*  113 */       String lstDivAcumExt = (String)erCabeceros.getCampos().get(37);
/*  114 */       String lstRemanente = (String)erCabeceros.getCampos().get(38);
/*  115 */       String lstSisFinan = (String)erCabeceros.getCampos().get(39);
/*  116 */       String lstRetPer = (String)erCabeceros.getCampos().get(40);
/*  117 */       String lstOpeFinanDeriv = (String)erCabeceros.getCampos().get(41);
/*  118 */       String lstCompInteresNominal = (String)erCabeceros.getCampos().get(42);
/*  119 */       String lstCompInteresReal = (String)erCabeceros.getCampos().get(43);
/*  120 */       String lstCompPerdida = (String)erCabeceros.getCampos().get(44);
/*  121 */       String lstCompEsBeneCobro = (String)erCabeceros.getCampos().get(45);
/*  122 */       String lstCompPaisResidencia = (String)erCabeceros.getCampos().get(46);
/*  123 */       String lstCompPE_CVE_Tipo_Contri = (String)erCabeceros.getCampos().get(47);
/*  124 */       String lstCompPEDesConcepto = (String)erCabeceros.getCampos().get(48);
/*  125 */       String lstCompPE_RFC = (String)erCabeceros.getCampos().get(49);
/*  126 */       String lstCompPE_CURP = (String)erCabeceros.getCampos().get(50);
/*  127 */       String lstCompPE_Raz_Soc_Con = (String)erCabeceros.getCampos().get(51);
/*  128 */       String lstCompPE_IND_Tipo_Contri = (String)erCabeceros.getCampos().get(52);
/*  129 */       String lstCompPE_DesConcep = (String)erCabeceros.getCampos().get(53);
/*  130 */       String lstComp_Ent_Fed = (String)erCabeceros.getCampos().get(54);
/*  131 */       String lstComp_Monto_Tot_Pag = (String)erCabeceros.getCampos().get(55);
/*  132 */       String lstComp_Monto_Grav = (String)erCabeceros.getCampos().get(56);
/*  133 */       String lstComp_Monto_Exento = (String)erCabeceros.getCampos().get(57);
/*      */       
/*  135 */       String lstCom_OD_MonGanAcu = (String)erCabeceros.getCampos().get(58);
/*  136 */       String lstCom_OD_MonPerDed = (String)erCabeceros.getCampos().get(59);
/*      */       
/*  138 */       String lstEmailRecep = (String)erCabeceros.getCampos().get(18);
/*  139 */       String lstIdVendor = (String)erCabeceros.getCampos().get(19);

				String lstLugarExpE 	= (String)erCabeceros.getCampos().get(60);
				String lstRegFiscalE	= (String)erCabeceros.getCampos().get(61);
				String lstRegFiscalR	= (String)erCabeceros.getCampos().get(62);
				String version			= (String)erCabeceros.getCampos().get(63);

/*      */       
/*  141 */       int liIndex = lstMesInicial.indexOf(".");
/*  142 */       lstMesInicial = lstMesInicial.substring(0, liIndex);
/*      */       
/*  144 */       liIndex = lstMesFinal.indexOf(".");
/*  145 */       lstMesFinal = lstMesFinal.substring(0, liIndex);
/*      */       
/*  147 */       liIndex = lstEjerFisc.indexOf(".");
/*  148 */       lstEjerFisc = lstEjerFisc.substring(0, liIndex);
/*      */       
/*      */       try {
					//kaz se agregan dos columnas al final
/*  151 */         CargasCommon.insertarCabeceros(lstNombreArchivo, lstNoFacturaAP, lstRFCProvAP, lstFolioFiscAnt, 
													lstFolio, lstFecHorExp, lstClaveRet, lstDesRet, lstRFC_Emp_Emi,
													lstRazSocEmi, lstCURP_Emi, lstNacRec, lstRFC_Recep_Nac, lstRazSocRecNac,
													lstCURP_Rec_Nac, lstRegIdeFisExt, lstRazSocExt, lstMesInicial, lstMesFinal, 
													lstEjerFisc, lstMontoTotOpe, lstTotMonGra, lstMonTotExe, lstTotRet, 
													lstContraInter, lstGanancia, lstPerdida, lstTipoDividendo, lstRetTerrNac, 
													lstRetTerrExt, lstRetTerrExtDiviExt, lstTipoDistri, lstISR_AcrediNac, 
													lstDivAcumNac, lstDivAcumExt, lstRemanente, lstSisFinan, lstRetPer, 
													lstOpeFinanDeriv, lstCompInteresNominal, lstCompInteresReal, 
													lstCompPerdida, lstCompEsBeneCobro, lstCompPaisResidencia, lstCompPE_CVE_Tipo_Contri, 
													lstCompPEDesConcepto, lstCompPE_RFC, lstCompPE_CURP, lstCompPE_Raz_Soc_Con, 
													lstCompPE_IND_Tipo_Contri, lstCompPE_DesConcep, lstComp_Ent_Fed, lstComp_Monto_Tot_Pag, 
													lstComp_Monto_Grav, lstComp_Monto_Exento, lstCom_OD_MonGanAcu, lstCom_OD_MonPerDed, 
													lstEmailRecep, lstIdVendor,lstLugarExpE,lstRegFiscalE,lstRegFiscalR,version);
/*      */       }
/*  153 */       catch (Exception e) {
/*      */         
/*  155 */         throw new IllegalArgumentException(e.getMessage());
/*      */       } 
/*      */     } 
/*  158 */     for (ExcelRecordLines erl : palLineas) {
/*      */       
/*  160 */       String lstIdFolio = (String)erl.getCampos().get(1);
/*  161 */       String lstBaseImp = (String)erl.getCampos().get(2);
/*  162 */       String lstCveTipoImp = (String)erl.getCampos().get(3);
/*  163 */       String lstValMontoRete = (String)erl.getCampos().get(4);
/*  164 */       String lstTipoPagoRete = (String)erl.getCampos().get(5);
/*      */       
/*      */       try {
/*  167 */         CargasCommon.insertarLineas(lstIdFolio, lstBaseImp, lstCveTipoImp, lstValMontoRete, lstTipoPagoRete);
/*      */       }
/*  169 */       catch (Exception e) {
/*      */         
/*  171 */         throw new IllegalArgumentException(e.getMessage());
/*      */       } 
/*      */     }
			/**
			 * KAZ 01-06-2022 itera cfdi Relacionados para insertar en las tabla
			 */
				String lstVersion = palCabeceros.get(0).getCampos().get(63);
				if(lstVersion.equals("2.0") && paLineasCfdiRel.size() > 0) {
					
					for (ExcelRecordLines erl : paLineasCfdiRel) {
						
						String lstFolioLinea	= (String)erl.getCampos().get(1);	
						String lstTipoRelacion 	= (String)erl.getCampos().get(2);
						String lstUUID			= (String)erl.getCampos().get(3);
							
						try {
							        CargasCommon.insertarCfdiRelacionados(lstFolioLinea, lstTipoRelacion, lstUUID);
							      }
							       catch (Exception e) {
							         
							        throw new IllegalArgumentException(e.getMessage());
							     } 
							
					
					}
				}
				 

			}	




/*      */   public static void main(String[] s) {}
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validarLayout(String pstNombreArchivo) throws IllegalArgumentException {
/*      */     try {
/*  193 */       FileInputStream fis = new FileInputStream(new File(pstNombreArchivo));
/*  194 */       XSSFWorkbook wb = new XSSFWorkbook(fis);
/*  195 */       XSSFSheet sheet = wb.getSheetAt(0);
/*      */       
/*  197 */       int HEADERS_ROW = 1;
/*  198 */       int TOTALES = 23;
/*      */       
/*  200 */       XSSFRow row = sheet.getRow(HEADERS_ROW);
/*  201 */       XSSFCell cell = row.getCell(TOTALES);
/*      */       
/*  203 */       String value = cell.getStringCellValue();
/*  204 */       if (!value.equals("TOTALES")) {
/*  205 */         throw new IllegalArgumentException("El layout seleccionado no es correcto");
/*      */       }
/*      */     }
/*  208 */     catch (Exception e) {
/*      */       
/*  210 */       throw new IllegalArgumentException("El layout seleccionado no es correcto");
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static ArrayList<ExcelRecord> cargarCabeceros(String pstNombreArchivo) throws IOException, FileNotFoundException {
/*  217 */     FileInputStream fis = new FileInputStream(new File(pstNombreArchivo));
/*  218 */     XSSFWorkbook wb = new XSSFWorkbook(fis);
/*  219 */     XSSFSheet sheet = wb.getSheetAt(0);
/*  220 */     int START_ROW = 3;
/*  221 */     int rowIndex = START_ROW;
/*  222 */     XSSFRow row = sheet.getRow(rowIndex);
/*  223 */     ArrayList<ExcelRecord> excelTab = new ArrayList<ExcelRecord>();
/*  224 */     int i = 0;
/*  225 */     while (row != null) {
/*      */       
/*  227 */       if (row.getCell(0) != null && 
/*  228 */         !row.getCell(0).equals("")) {
/*      */         
/*  230 */         ExcelRecord registro = new ExcelRecord();
/*  231 */         for (int colIndex = 0; colIndex < 64; colIndex++) {
/*      */           
/*  233 */           XSSFCell cell = row.getCell(colIndex);
/*  234 */           String value = "";
/*  235 */           if (cell != null) {
/*  236 */             if (cell.getCellType() == 0) {
/*  237 */               value = Double.toString(cell.getNumericCellValue());
/*      */             } else {
/*  239 */               value = cell.getRichStringCellValue().getString();
/*      */             } 
/*      */           }
/*  242 */           registro.addCampo(value);
/*      */         } 
/*  244 */         excelTab.add(registro);
/*      */       } 
/*  246 */       rowIndex++;
/*  247 */       row = sheet.getRow(rowIndex);
/*  248 */       System.out.println(i);
/*  249 */       i++;
/*      */     } 
/*  251 */     return excelTab;
/*      */   }


		
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



/*      */   private static ArrayList<ExcelRecordLines> cargarLineas(String pstNombreArchivo) throws IOException, FileNotFoundException {
/*  257 */     FileInputStream fis = new FileInputStream(new File(pstNombreArchivo));
/*  258 */     XSSFWorkbook wb = new XSSFWorkbook(fis);
/*  259 */     XSSFSheet sheet = wb.getSheetAt(1);
/*  260 */     int START_ROW = 2;
/*  261 */     int rowIndex = START_ROW;
/*  262 */     XSSFRow row = sheet.getRow(rowIndex);
/*  263 */     ArrayList<ExcelRecordLines> excelTab = new ArrayList<ExcelRecordLines>();
/*  264 */     while (row != null) {
/*      */       
/*  266 */       if (row.getCell(0) != null && 
/*  267 */         !row.getCell(0).equals("")) {
/*      */         
/*  269 */         ExcelRecordLines registro = new ExcelRecordLines();
/*  270 */         for (int colIndex = 0; colIndex < 6; colIndex++) {
/*      */           
/*  272 */           XSSFCell cell = row.getCell(colIndex);
/*  273 */           String value = "";
/*  274 */           if (cell != null) {
/*  275 */             if (cell.getCellType() == 0) {
/*  276 */               value = Double.toString(cell.getNumericCellValue());
/*      */             } else {
/*  278 */               value = cell.getRichStringCellValue().getString();
/*      */             } 
/*      */           }
/*  281 */           registro.addCampo(value);
/*      */         } 
/*  283 */         excelTab.add(registro);
/*      */       } 
/*  285 */       rowIndex++;
/*  286 */       row = sheet.getRow(rowIndex);
/*      */     } 
/*  288 */     return excelTab;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String escribirArchivo(ArrayList<ExcelRecord> palCabeceros, ArrayList<ExcelRecordLines> palLineas,ArrayList<ExcelRecordLines> palCfdiRel, String path) throws Exception, IOException {
/*  294 */     String lstCargaManual = "M";
/*  295 */     System.setProperty("line.separator", "\n");
/*      */     
/*  297 */     File dir = new File(path);
/*  298 */     FileUtils.forceMkdir(dir);
/*      */     
/*  300 */     String fileName = (String)((ExcelRecord)palCabeceros.get(0)).getCampos().get(1);
/*      */     
/*  302 */     File file = new File(dir, fileName);
/*  303 */     FileWriter writer = new FileWriter(file);
/*  304 */     String endLine = System.getProperty("line.separator");
/*  305 */     String lstNombreArchivo = null;
/*      */     
/*  307 */     DecimalFormat dfDosDecimales = new DecimalFormat("#.##");
/*  308 */     for (ExcelRecord erCabeceros : palCabeceros) {
/*      */       String lstCom_OD_MonPerDed, lstCom_OD_MonGanAcu, lstComp_Monto_Exento, lstComp_Monto_Grav, lstComp_Monto_Tot_Pag, lstCompPerdida, lstCompInteresReal, lstCompInteresNominal, lstRemanente, lstDivAcumExt, lstDivAcumNac, lstISR_AcrediNac, lstRetTerrExtDiviExt, lstRetTerrExt, lstRetTerrNac, lstPerdida, lstGanancia, lstTotRet, lstMonTotExe, lstTotMonGra, lstMontoTotOpe;
/*  310 */       lstNombreArchivo = (String)erCabeceros.getCampos().get(1);
/*      */       
/*  312 */       String lstNoFacturaAP = (String)erCabeceros.getCampos().get(2);
/*  313 */       String lstRFCProvAP = (String)erCabeceros.getCampos().get(3);
/*  314 */       String lstFolioFiscAnt = (String)erCabeceros.getCampos().get(4);
/*      */       
/*  316 */       String lstFolio = (String)erCabeceros.getCampos().get(5);
/*  317 */       String lstFecHorExp = (String)erCabeceros.getCampos().get(6);
/*  318 */       String lstClaveRet = (String)erCabeceros.getCampos().get(7);
/*  319 */       String lstDesRet = (String)erCabeceros.getCampos().get(8);
/*  320 */       String lstRFC_Emp_Emi = (String)erCabeceros.getCampos().get(9);
/*  321 */       String lstRazSocEmi = (String)erCabeceros.getCampos().get(10);
/*  322 */       String lstCURP_Emi = (String)erCabeceros.getCampos().get(11);
/*  323 */       String lstNacRec = (String)erCabeceros.getCampos().get(12);
/*  324 */       String lstRFC_Recep_Nac = (String)erCabeceros.getCampos().get(13);
/*  325 */       String lstRazSocRecNac = (String)erCabeceros.getCampos().get(14);
/*  326 */       String lstCURP_Rec_Nac = (String)erCabeceros.getCampos().get(15);
/*  327 */       String lstRegIdeFisExt = (String)erCabeceros.getCampos().get(16);
/*  328 */       String lstRazSocExt = (String)erCabeceros.getCampos().get(17);
			     String lstVendorId = (String)erCabeceros.getCampos().get(19);
/*  329 */       String lstMesInicial = (String)erCabeceros.getCampos().get(20);
/*  330 */       String lstMesFinal = (String)erCabeceros.getCampos().get(21);
/*  331 */       String lstEjerFisc = (String)erCabeceros.getCampos().get(22);


				/**
				 * KAZ - se agregan 3 atribtos CFDI 4.0 CP emisor y Regimen Fiscal E y DomFiscalRecepector
				 */
				String lstLugExpE 		= (String)erCabeceros.getCampos().get(60);
				String lstRegFiscalE 	= (String)erCabeceros.getCampos().get(61);
				String lstRegFiscalR 	= (String)erCabeceros.getCampos().get(62);
				String version		 	= (String)erCabeceros.getCampos().get(63);
				
				lstLugExpE				=	lstLugExpE.length() > 0 ? lstLugExpE : "|";
				lstRegFiscalE			=	lstRegFiscalE.length() > 0 ? lstRegFiscalE : "|";
				lstRegFiscalR			=	lstRegFiscalR.length() > 0 ? lstRegFiscalR : "|";
				//TERMINA KAZ



/*      */       try {
/*  335 */         lstMontoTotOpe = (String)erCabeceros.getCampos().get(23);
/*  336 */         lstMontoTotOpe = dfDosDecimales.format(Double.parseDouble(lstMontoTotOpe));
/*      */       }
/*  338 */       catch (Exception e) {
/*      */         
/*  340 */         lstMontoTotOpe = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  345 */         lstTotMonGra = (String)erCabeceros.getCampos().get(24);
/*  346 */         lstTotMonGra = dfDosDecimales.format(Double.parseDouble(lstTotMonGra));
/*      */       }
/*  348 */       catch (Exception e) {
/*      */         
/*  350 */         lstTotMonGra = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  355 */         lstMonTotExe = (String)erCabeceros.getCampos().get(25);
/*  356 */         lstMonTotExe = dfDosDecimales.format(Double.parseDouble(lstMonTotExe));
/*      */       }
/*  358 */       catch (Exception e) {
/*      */         
/*  360 */         lstMonTotExe = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  365 */         lstTotRet = (String)erCabeceros.getCampos().get(26);
/*  366 */         lstTotRet = dfDosDecimales.format(Double.parseDouble(lstTotRet));
/*      */       }
/*  368 */       catch (Exception e) {
/*      */         
/*  370 */         lstTotRet = "";
/*      */       } 
/*  372 */       String lstContraInter = (String)erCabeceros.getCampos().get(27);
/*      */ 
/*      */       
/*      */       try {
/*  376 */         lstGanancia = (String)erCabeceros.getCampos().get(28);
/*  377 */         lstGanancia = dfDosDecimales.format(Double.parseDouble(lstGanancia));
/*      */       }
/*  379 */       catch (Exception e) {
/*      */         
/*  381 */         lstGanancia = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  386 */         lstPerdida = (String)erCabeceros.getCampos().get(29);
/*  387 */         lstPerdida = dfDosDecimales.format(Double.parseDouble(lstPerdida));
/*      */       }
/*  389 */       catch (Exception e) {
/*      */         
/*  391 */         lstPerdida = "";
/*      */       } 
/*  393 */       String lstTipoDividendo = (String)erCabeceros.getCampos().get(30);
/*      */ 
/*      */       
/*      */       try {
/*  397 */         lstRetTerrNac = (String)erCabeceros.getCampos().get(31);
/*  398 */         lstRetTerrNac = dfDosDecimales.format(Double.parseDouble(lstRetTerrNac));
/*      */       }
/*  400 */       catch (Exception e) {
/*      */         
/*  402 */         lstRetTerrNac = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  407 */         lstRetTerrExt = (String)erCabeceros.getCampos().get(32);
/*  408 */         lstRetTerrExt = dfDosDecimales.format(Double.parseDouble(lstRetTerrExt));
/*      */       }
/*  410 */       catch (Exception e) {
/*      */         
/*  412 */         lstRetTerrExt = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  417 */         lstRetTerrExtDiviExt = (String)erCabeceros.getCampos().get(33);
/*  418 */         lstRetTerrExtDiviExt = dfDosDecimales.format(Double.parseDouble(lstRetTerrExtDiviExt));
/*      */       }
/*  420 */       catch (Exception e) {
/*      */         
/*  422 */         lstRetTerrExtDiviExt = "";
/*      */       } 
/*  424 */       String lstTipoDistri = (String)erCabeceros.getCampos().get(34);
/*      */ 
/*      */       
/*      */       try {
/*  428 */         lstISR_AcrediNac = (String)erCabeceros.getCampos().get(35);
/*  429 */         lstISR_AcrediNac = dfDosDecimales.format(Double.parseDouble(lstISR_AcrediNac));
/*      */       }
/*  431 */       catch (Exception e) {
/*      */         
/*  433 */         lstISR_AcrediNac = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  438 */         lstDivAcumNac = (String)erCabeceros.getCampos().get(36);
/*  439 */         lstDivAcumNac = dfDosDecimales.format(Double.parseDouble(lstDivAcumNac));
/*      */       }
/*  441 */       catch (Exception e) {
/*      */         
/*  443 */         lstDivAcumNac = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  448 */         lstDivAcumExt = (String)erCabeceros.getCampos().get(37);
/*  449 */         lstDivAcumExt = dfDosDecimales.format(Double.parseDouble(lstDivAcumExt));
/*      */       }
/*  451 */       catch (Exception e) {
/*      */         
/*  453 */         lstDivAcumExt = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  458 */         lstRemanente = (String)erCabeceros.getCampos().get(38);
/*  459 */         lstRemanente = dfDosDecimales.format(Double.parseDouble(lstRemanente));
/*      */       }
/*  461 */       catch (Exception e) {
/*      */         
/*  463 */         lstRemanente = "";
/*      */       } 
/*  465 */       String lstSisFinan = (String)erCabeceros.getCampos().get(39);
/*  466 */       String lstRetPer = (String)erCabeceros.getCampos().get(40);
/*  467 */       String lstOpeFinanDeriv = (String)erCabeceros.getCampos().get(41);
/*      */ 
/*      */       
/*      */       try {
/*  471 */         lstCompInteresNominal = (String)erCabeceros.getCampos().get(42);
/*  472 */         lstCompInteresNominal = dfDosDecimales.format(Double.parseDouble(lstCompInteresNominal));
/*      */       }
/*  474 */       catch (Exception e) {
/*      */         
/*  476 */         lstCompInteresNominal = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  481 */         lstCompInteresReal = (String)erCabeceros.getCampos().get(43);
/*  482 */         lstCompInteresReal = dfDosDecimales.format(Double.parseDouble(lstCompInteresReal));
/*      */       }
/*  484 */       catch (Exception e) {
/*      */         
/*  486 */         lstCompInteresReal = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  491 */         lstCompPerdida = (String)erCabeceros.getCampos().get(44);
/*  492 */         lstCompPerdida = dfDosDecimales.format(Double.parseDouble(lstCompPerdida));
/*      */       }
/*  494 */       catch (Exception e) {
/*      */         
/*  496 */         lstCompPerdida = "";
/*      */       } 
/*  498 */       String lstCompEsBeneCobro = (String)erCabeceros.getCampos().get(45);
/*  499 */       String lstCompPaisResidencia = (String)erCabeceros.getCampos().get(46);
/*  500 */       String lstCompPE_CVE_Tipo_Contri = (String)erCabeceros.getCampos().get(47);
/*  501 */       String lstCompPEDesConcepto = (String)erCabeceros.getCampos().get(48);
/*  502 */       String lstCompPE_RFC = (String)erCabeceros.getCampos().get(49);
/*  503 */       String lstCompPE_CURP = (String)erCabeceros.getCampos().get(50);
/*  504 */       String lstCompPE_Raz_Soc_Con = (String)erCabeceros.getCampos().get(51);
/*  505 */       String lstCompPE_IND_Tipo_Contri = (String)erCabeceros.getCampos().get(52);
/*  506 */       String lstCompPE_DesConcep = (String)erCabeceros.getCampos().get(53);
/*  507 */       String lstComp_Ent_Fed = (String)erCabeceros.getCampos().get(54);
/*      */ 
/*      */       
/*      */       try {
/*  511 */         lstComp_Monto_Tot_Pag = (String)erCabeceros.getCampos().get(55);
/*  512 */         lstComp_Monto_Tot_Pag = dfDosDecimales.format(Double.parseDouble(lstComp_Monto_Tot_Pag));
/*      */       }
/*  514 */       catch (Exception e) {
/*      */         
/*  516 */         lstComp_Monto_Tot_Pag = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  521 */         lstComp_Monto_Grav = (String)erCabeceros.getCampos().get(56);
/*  522 */         lstComp_Monto_Grav = dfDosDecimales.format(Double.parseDouble(lstComp_Monto_Grav));
/*      */       }
/*  524 */       catch (Exception e) {
/*      */         
/*  526 */         lstComp_Monto_Grav = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  531 */         lstComp_Monto_Exento = (String)erCabeceros.getCampos().get(57);
/*  532 */         lstComp_Monto_Exento = dfDosDecimales.format(Double.parseDouble(lstComp_Monto_Exento));
/*      */       }
/*  534 */       catch (Exception e) {
/*      */         
/*  536 */         lstComp_Monto_Exento = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  541 */         lstCom_OD_MonGanAcu = (String)erCabeceros.getCampos().get(58);
/*  542 */         lstCom_OD_MonGanAcu = dfDosDecimales.format(Double.parseDouble(lstCom_OD_MonGanAcu));
/*      */       }
/*  544 */       catch (Exception e) {
/*      */         
/*  546 */         lstCom_OD_MonGanAcu = "";
/*      */       } 
/*      */ 
/*      */       
/*      */       try {
/*  551 */         lstCom_OD_MonPerDed = (String)erCabeceros.getCampos().get(59);
/*  552 */         lstCom_OD_MonPerDed = dfDosDecimales.format(Double.parseDouble(lstCom_OD_MonPerDed));
/*      */       }
/*  554 */       catch (Exception e) {
/*      */         
/*  556 */         lstCom_OD_MonPerDed = "";
/*      */       } 
/*  558 */       String lstEmailRecep = (String)erCabeceros.getCampos().get(18);
/*  559 */       String lstIdVendor = (String)erCabeceros.getCampos().get(19);
/*      */ 
/*      */       
/*      */       try {
/*  563 */         int liIndex = lstMesInicial.indexOf(".");
/*  564 */         lstMesInicial = lstMesInicial.substring(0, liIndex);
/*      */       }
/*  566 */       catch (Exception exception) {}
/*      */       
/*      */       try {
/*  569 */         int liIndex = lstMesFinal.indexOf(".");
/*  570 */         lstMesFinal = lstMesFinal.substring(0, liIndex);
/*      */       }
/*  572 */       catch (Exception exception) {}
/*      */       
/*      */       try {
/*  575 */         int liIndex = lstEjerFisc.indexOf(".");
/*  576 */         lstEjerFisc = lstEjerFisc.substring(0, liIndex);
/*      */       }
/*  578 */       catch (Exception exception) {}
/*      */       
/*      */       try {
/*  581 */         int liIndex = lstCompPE_IND_Tipo_Contri.indexOf(".");
/*  582 */         lstCompPE_IND_Tipo_Contri = lstCompPE_IND_Tipo_Contri.substring(0, liIndex);
/*      */       }
/*  584 */       catch (Exception exception) {}
/*      */       
/*      */       try {
/*  587 */         int liIndex = lstCompPE_CVE_Tipo_Contri.indexOf(".");
/*  588 */         lstCompPE_CVE_Tipo_Contri = lstCompPE_CVE_Tipo_Contri.substring(0, liIndex);
/*      */       }
/*  590 */       catch (Exception exception) {}
/*      */       
/*      */       try {
/*  593 */        if (lstIdVendor.contains(".")) { int liIdVendir = Integer.parseInt(lstIdVendor.substring(0, lstIdVendor.indexOf(".")));
/*  625 */         lstIdVendor = String.valueOf(liIdVendir);}
/*      */       }
/*  596 */       catch (Exception e) {
/*      */         
/*  598 */         throw new IllegalArgumentException("Error al convertir el IdVendor.");
/*      */       } 
/*  600 */       writer.write("00|" + lstNombreArchivo + "|" + lstCargaManual + endLine);//kaz se agrega la version para la dualidad
/*  601 */       writer.write("01|" + lstFolio + "|" + lstFecHorExp + "|" + lstClaveRet + "|" + lstDesRet + "|" + lstLugExpE +  "|" + version + endLine); // KAZ - Se adiciona cp lugar del emisor
/*      */       
/*  603 */       writer.write("02|" + lstRFC_Emp_Emi + "|" + lstRazSocEmi + "|" + lstCURP_Emi + "|" + lstRegFiscalE + endLine); // KAZ - Se adiciona RegimenFiscal del emisor
/*  604 */       writer.write("03|" + lstNacRec + "|" + lstRFC_Recep_Nac + "|" + lstRazSocRecNac + "|" + lstCURP_Rec_Nac + "|" + lstRegIdeFisExt + "|" + lstRazSocExt + "|" + lstEmailRecep + "|" + lstIdVendor + "|" + lstRegFiscalR + endLine);//Kaz se adiciona Domicilio fiscal cp del receptor
/*      */       
/*  606 */       writer.write("04|" + lstMesInicial + "|" + lstMesFinal + "|" + lstEjerFisc + endLine);
/*  607 */       writer.write("05|" + lstMontoTotOpe + "|" + lstTotMonGra + "|" + lstMonTotExe + "|" + lstTotRet + endLine);
/*  608 */       for (ExcelRecordLines erl : palLineas) {
/*      */         
/*  610 */         String lstMontoRet, lstBaseImp, lstFolioLinea = (String)erl.getCampos().get(1);
/*      */ 
/*      */         
/*      */         try {
/*  614 */           lstBaseImp = (String)erl.getCampos().get(2);
/*  615 */           lstBaseImp = dfDosDecimales.format(Double.parseDouble(lstBaseImp));
/*      */         }
/*  617 */         catch (Exception e) {
/*      */           
/*  619 */           lstBaseImp = "";
/*      */         } 
/*  621 */         String lstTipoImp = (String)erl.getCampos().get(3);
/*      */ 
/*      */         
/*      */         try {
/*  625 */           lstMontoRet = (String)erl.getCampos().get(4);
/*  626 */           lstMontoRet = dfDosDecimales.format(Double.parseDouble(lstMontoRet));
/*      */         }
/*  628 */         catch (Exception e) {
/*      */           
/*  630 */           lstMontoRet = "";
/*      */         } 
/*  632 */         String lstTipoPagoRete = (String)erl.getCampos().get(5);

/*
				 * KAZ - para anexar CFDI Relacionados solo para la ver. 4.0 - dualidad
				 */
						
				if(version.equals("2.0")) {
					
					if(lstTipoPagoRete.toUpperCase().equals("PAGO DEFINITIVO IVA")) {
						
						lstTipoPagoRete = "01";
						
					}else if(lstTipoPagoRete.toUpperCase().equals("PAGO DEFINITIVO IEPS")) {
						
						lstTipoPagoRete = "02";
						
					}if(lstTipoPagoRete.toUpperCase().equals("PAGO DEFINITIVO ISR PLATAFORMAS")) {
						
						lstTipoPagoRete = "03";
						
					}if(lstTipoPagoRete.toUpperCase().equals("PAGO PROVISIONAL ISR")) {
						lstTipoPagoRete = "04";
					}
					
				}
					
/*  633 */         if (lstFolio.equals(lstFolioLinea)) {
/*  634 */           writer.write("06|" + lstBaseImp + "|" + lstTipoImp + "|" + lstMontoRet + "|" + lstTipoPagoRete + endLine);
/*      */         }
/*      */       } 
/*  637 */       if (lstClaveRet.equals("06")) {
/*  638 */         writer.write("07|" + lstContraInter + "|" + lstGanancia + "|" + lstPerdida + endLine);
/*      */       }
/*  640 */       if (lstClaveRet.equals("14")) {
/*  641 */         writer.write("08|" + lstTipoDividendo + "|" + lstRetTerrNac + "|" + lstRetTerrExt + "|" + lstRetTerrExtDiviExt + "|" + lstTipoDistri + "|" + lstISR_AcrediNac + "|" + lstDivAcumNac + "|" + lstDivAcumExt + "|" + lstRemanente + endLine);
/*      */       }
/*  643 */       if (lstClaveRet.equals("16")) {
/*  644 */         writer.write("09|" + lstSisFinan + "|" + lstRetPer + "|" + lstOpeFinanDeriv + "|" + lstCompInteresNominal + "|" + lstCompInteresReal + "|" + lstCompPerdida + endLine);
/*      */       }
/*  646 */       if (lstClaveRet.equals("18")) {
/*  647 */         writer.write("10|" + lstCompEsBeneCobro + "|" + lstCompPaisResidencia + "|" + lstCompPE_CVE_Tipo_Contri + "|" + lstCompPEDesConcepto + "|" + lstCompPE_RFC + "|" + lstCompPE_CURP + "|" + lstCompPE_Raz_Soc_Con + "|" + lstCompPE_IND_Tipo_Contri + "|" + lstCompPE_DesConcep + endLine);
/*      */       }
/*  649 */       if (lstClaveRet.equals("20")) {
/*  650 */         writer.write("11|" + lstComp_Ent_Fed + "|" + lstComp_Monto_Tot_Pag + "|" + lstComp_Monto_Grav + "|" + lstComp_Monto_Exento + endLine);
/*      */       }
/*  652 */       if (lstClaveRet.equals("24")) {
/*  653 */         writer.write("12|" + lstCom_OD_MonGanAcu + "|" + lstCom_OD_MonPerDed + endLine);
/*      */       }


				/*
				 * KAZ - para anexar CFDI Relacionados solo para la ver. 4.0 - dualidad
				 */
						
				if(version.equals("2.0") && palCfdiRel.size() > 0) {
					
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
				}
				
				//TERMINA CAMBIO KAZ
				

				if(lovalemp) {
					writer.write("98|" + lstNoFacturaAP + "|" + lstVendorId + "|"+ endLine); 
				}
/*  655 */       writer.write("99" + endLine);
/*      */     } 
/*  657 */     writer.write("9999|");
/*  658 */     writer.close();
				fileBs64 = file;
/*  659 */     return lstNombreArchivo;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static String validarCamposRequeridos(ArrayList<ExcelRecord> palCabeceros, ArrayList<ExcelRecordLines> palLineas) throws IllegalArgumentException {
/*  665 */     String lstError = null;
/*      */     
/*  667 */     String lstsql = "SELECT /* FIRST_ROWS*/ COUNT (*) factvalida FROM   ap.ap_invoices_all inv WHERE  1 = 1 AND    invoice_num = ? AND    EXISTS (            SELECT 'Empresas Validas '            FROM   apps.hr_all_organization_units orgs            WHERE 0=0            AND    orgs.organization_id = inv.org_id            AND    org_id IN (SELECT orgs.org_id                             FROM   apps.xxap_cfdis_ret_emp_vw orgs                             WHERE  orgs.rfc IN (SELECT orgs2.rfc                                                 FROM   apps.xxap_cfdis_ret_emp_vw orgs2                                                 WHERE  SUBSTR(orgs2.name, 1, 3) = ?                                                 AND    orgs2.rfc IN (SELECT  NVL (description, meaning)                                                                     FROM   apps.fnd_lookup_values lv                                                                     WHERE  LANGUAGE = 'ESA'                                                                     AND    enabled_flag = 'Y'                                                                     AND    (meaning = ? OR description = ?)                                                                     AND    SYSDATE >= NVL (start_date_active, SYSDATE)                                                                     AND    SYSDATE <= NVL (end_date_active, SYSDATE)                                                                     AND    lv.lookup_type = 'XXAP_CARGA_RET_EMP')))) AND    EXISTS (            SELECT DISTINCT 'Proveedores Validos'            FROM   po.po_vendors ven                  ,po.po_vendor_sites_all vensit            WHERE  ven.vendor_id = vensit.vendor_id            AND    enabled_flag = 'Y'            AND    segment1 = ?            AND    inv.vendor_id = ven.vendor_id            AND    inv.org_id = vensit.org_id            )";
/*      */     
/*  669 */     String lstsql2 = "SELECT COUNT (*) ProvExists FROM   po.po_vendors WHERE  segment1 = ? AND    enabled_flag = 'Y' ";
/*      */     
/*  671 */     String lstsql3 = "SELECT LOOKUP_CODE   FROM Apps.FND_LOOKUP_VALUES LV  WHERE LANGUAGE = 'ESA'    AND ENABLED_FLAG = 'Y'    AND (description = ? OR meaning = ? )    AND SYSDATE >= NVL(START_DATE_ACTIVE, SYSDATE)    AND SYSDATE <= NVL(END_DATE_ACTIVE, SYSDATE)    AND LV.LOOKUP_TYPE = 'XXAP_CARGA_RET_EMP'    AND lookup_code = ?   AND ROWNUM = 1 ";
/*      */     
/*  673 */     ConnectionWrapper luCW = null;
/*  674 */     ResultSet luResulset = null;
/*  675 */     String lstNumReg = "";
/*  676 */     int linNumReg = 0;
/*  677 */     String lstNumEpresa = "";
/*      */     
/*      */     try {
				
			//KAZ argumel 16-06-22 debe ser una version por archivo
			String lstVersion = palCabeceros.get(0).getCampos().get(63);
			for (ExcelRecord erCabeceros : palCabeceros) {
				
				if(!lstVersion.equals(erCabeceros.getCampos().get(63))) {
					throw new IllegalArgumentException("Error: La versión debe coincidir en todos los registros");
				}
			}
				
/*  680 */       for (ExcelRecord erCabeceros : palCabeceros) {
/*      */         
/*  682 */         String lstNombreArchivo = (String)erCabeceros.getCampos().get(1);
/*      */         
/*  684 */         String lstNoFacturaAP = (String)erCabeceros.getCampos().get(2);
/*  685 */         String lstRFCProvAP = (String)erCabeceros.getCampos().get(3);
/*  686 */         String lstFolioFiscAnt = (String)erCabeceros.getCampos().get(4);
/*      */         
/*  688 */         String lstFolio = (String)erCabeceros.getCampos().get(5);
/*  689 */         String lstFecHorExp = (String)erCabeceros.getCampos().get(6);
/*  690 */         String lstClaveRet = (String)erCabeceros.getCampos().get(7);
/*  691 */         String lstDesRet = (String)erCabeceros.getCampos().get(8);
/*  692 */         String lstRFC_Emp_Emi = (String)erCabeceros.getCampos().get(9);
/*  693 */         String lstRazSocEmi = (String)erCabeceros.getCampos().get(10);
/*  694 */         String lstCURP_Emi = (String)erCabeceros.getCampos().get(11);
/*  695 */         String lstNacRec = (String)erCabeceros.getCampos().get(12);
/*  696 */         String lstRFC_Recep_Nac = (String)erCabeceros.getCampos().get(13);
/*  697 */         String lstRazSocRecNac = (String)erCabeceros.getCampos().get(14);
/*  698 */         String lstCURP_Rec_Nac = (String)erCabeceros.getCampos().get(15);
/*  699 */         String lstRegIdeFisExt = (String)erCabeceros.getCampos().get(16);
/*  700 */         String lstRazSocExt = (String)erCabeceros.getCampos().get(17);
/*  701 */         String lstMesInicial = (String)erCabeceros.getCampos().get(20);
/*  702 */         String lstMesFinal = (String)erCabeceros.getCampos().get(21);
/*  703 */         String lstEjerFisc = (String)erCabeceros.getCampos().get(22);
/*  704 */         String lstMontoTotOpe = (String)erCabeceros.getCampos().get(23);
/*  705 */         String lstTotMonGra = (String)erCabeceros.getCampos().get(24);
/*  706 */         String lstMonTotExe = (String)erCabeceros.getCampos().get(25);
/*  707 */         String lstTotRet = (String)erCabeceros.getCampos().get(26);
/*  708 */         String lstContraInter = (String)erCabeceros.getCampos().get(27);
/*  709 */         String lstGanancia = (String)erCabeceros.getCampos().get(28);
/*  710 */         String lstPerdida = (String)erCabeceros.getCampos().get(29);
/*  711 */         String lstTipoDividendo = (String)erCabeceros.getCampos().get(30);
/*  712 */         String lstRetTerrNac = (String)erCabeceros.getCampos().get(31);
/*  713 */         String lstRetTerrExt = (String)erCabeceros.getCampos().get(32);
/*  714 */         String lstRetTerrExtDiviExt = (String)erCabeceros.getCampos().get(33);
/*  715 */         String lstTipoDistri = (String)erCabeceros.getCampos().get(34);
/*  716 */         String lstISR_AcrediNac = (String)erCabeceros.getCampos().get(35);
/*  717 */         String lstDivAcumNac = (String)erCabeceros.getCampos().get(36);
/*  718 */         String lstDivAcumExt = (String)erCabeceros.getCampos().get(37);
/*  719 */         String lstRemanente = (String)erCabeceros.getCampos().get(38);
/*  720 */         String lstSisFinan = (String)erCabeceros.getCampos().get(39);
/*  721 */         String lstRetPer = (String)erCabeceros.getCampos().get(40);
/*  722 */         String lstOpeFinanDeriv = (String)erCabeceros.getCampos().get(41);
/*  723 */         String lstCompInteresNominal = (String)erCabeceros.getCampos().get(42);
/*  724 */         String lstCompInteresReal = (String)erCabeceros.getCampos().get(43);
/*  725 */         String lstCompPerdida = (String)erCabeceros.getCampos().get(44);
/*  726 */         String lstCompEsBeneCobro = (String)erCabeceros.getCampos().get(45);
/*  727 */         String lstCompPaisResidencia = (String)erCabeceros.getCampos().get(46);
/*  728 */         String lstCompPE_CVE_Tipo_Contri = (String)erCabeceros.getCampos().get(47);
/*  729 */         String lstCompPEDesConcepto = (String)erCabeceros.getCampos().get(48);
/*  730 */         String lstCompPE_RFC = (String)erCabeceros.getCampos().get(49);
/*  731 */         String lstCompPE_CURP = (String)erCabeceros.getCampos().get(50);
/*  732 */         String lstCompPE_Raz_Soc_Con = (String)erCabeceros.getCampos().get(51);
/*  733 */         String lstCompPE_IND_Tipo_Contri = (String)erCabeceros.getCampos().get(52);
/*  734 */         String lstCompPE_DesConcep = (String)erCabeceros.getCampos().get(53);
/*  735 */         String lstComp_Ent_Fed = (String)erCabeceros.getCampos().get(54);
/*  736 */         String lstComp_Monto_Tot_Pag = (String)erCabeceros.getCampos().get(55);
/*  737 */         String lstComp_Monto_Grav = (String)erCabeceros.getCampos().get(56);
/*  738 */         String lstComp_Monto_Exento = (String)erCabeceros.getCampos().get(57);
/*      */         
/*  740 */         String lstCom_OD_MonGanAcu = (String)erCabeceros.getCampos().get(58);
/*  741 */         String lstCom_OD_MonPerDed = (String)erCabeceros.getCampos().get(59);


				/**
				 *Kaz 23-05-2022 Argumel CAMBIOS A CFDI 4.0 . Se agrega lugar de Expedicion cp y regimen fiscal del emisor 
				 */
				
				   String lstLugarExpE 	= (String)erCabeceros.getCampos().get(60);
				   String lstRegFiscalE	= (String)erCabeceros.getCampos().get(61);
				   String lstRegFiscalR	= (String)erCabeceros.getCampos().get(62);
				   String version		= (String)erCabeceros.getCampos().get(63);
				/*TERMINA MODIFICACION CFDI 4.0*/


/*      */         
/*  743 */         String lstEmailRecep = (String)erCabeceros.getCampos().get(18);
/*  744 */         String lstIdVendor = (String)erCabeceros.getCampos().get(19);
/*  745 */         String lstValidNombreArchivo = lstNombreArchivo.substring(0, 9);

					InputParameters input = new InputParameters();
					System.out.println("aqui input empresa emisora :  " + lstRFC_Emp_Emi);
						input.setRfc(lstRFC_Emp_Emi);
						
						
					
						OutputParameters out = new OutputParameters();
						
						ValidarExistenciaEmpresa valemp = new ValidarExistenciaEmpresa();
						ExecutePttConsultarExistenciaEmpresaREQUEST valempi = valemp.getExecutePttConsultarExistenciaEmpresaREQUESTPt(); 
						out = valempi.validarExistenciaEmpresa(input);
						System.out.println("servicio valida empresa:  " + out.isExiste());
						System.out.println("servicio valida empresa:  " + out.getDescripcion());
						
						if(out.isExiste()) {
							
							lovalemp = true;
							lbempEBS=false;
							
							
						}
					
/*  746 */         if (lstNombreArchivo.equals("")) {
/*      */           
/*  748 */           lstError = "Error en el campo Nombre de Archivo.No puede ir vacio.\n";
/*  749 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  751 */         if (!lstValidNombreArchivo.equalsIgnoreCase("RETM-RET-")) {
/*      */           
/*  753 */           lstError = "Error en el 'campo Nombre de Archivo'. \nEl nombre debe de empezar de la siguiente forma: RETM-RET-XXX-XXXXXXXX-XXXX.txt";
/*      */           
/*  755 */           throw new IllegalArgumentException(lstError);
/*      */         } 
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
/*  769 */         lstNumEpresa = lstNombreArchivo.substring(lstNombreArchivo.indexOf("-", lstNombreArchivo.indexOf("-", 0) + 1) + 1, lstNombreArchivo.indexOf("-", lstNombreArchivo.indexOf("-", lstNombreArchivo.indexOf("-", lstNombreArchivo.indexOf("-", 0) + 1)) + 1));
        
/*  849 */         if (lstFecHorExp.equals("")) {
/*      */           
/*  851 */           lstError = "Error en el campo Fecha y Hora de Expedicion.\nNo puede ir vacio.";
/*  852 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  854 */         if (lstClaveRet.equals("")) {
/*      */           
/*  856 */           lstError = "Error en el campo Clave Retencion.\nNo puede ir vacio.\n";
/*  857 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  859 */         if (lstRFC_Emp_Emi.equals("")) {
/*      */           
/*  861 */           lstError = "Error en el campo RFC Empresa Emisora.\nNo puede ir vacio.\n";
/*  862 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  864 */         if (lstNacRec.equals("")) {
/*      */           
/*  866 */           lstError = "Error en el campo Nacionalidad.\nNo puede ir vacio.\n";
/*  867 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  869 */         if (lstNacRec.equals("Nacional") && 
/*  870 */           lstRFC_Recep_Nac.equals("")) {
/*      */           
/*  872 */           lstError = "Error en el campo RFC Receptor Nacional.\nNo puede ir vacio.\nPorque en el campo Nacionalidad es Nacional.";
/*      */           
/*  874 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  876 */         if (lstNacRec.equals("Extranjero") && 
/*  877 */           lstRazSocExt.equals("")) {
/*      */           
/*  879 */           lstError = "Error en el campo Razon Social Extranjero.\nNo puede ir vacio.\nPorque en el campo Nacionalidad es Extranjero.";
/*      */           
/*  881 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  883 */         if (lstMesInicial.equals("")) {
/*      */           
/*  885 */           lstError = "Error en el campo Mes Inicial.\nNo puede ir vacio.\n";
/*  886 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  888 */         if (lstMesFinal.equals("")) {
/*      */           
/*  890 */           lstError = "Error en el campo Mes Final.\nNo puede ir vacio.\n";
/*  891 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  893 */         if (lstEjerFisc.equals("")) {
/*      */           
/*  895 */           lstError = "Error en el campo Ejercicio Fiscal.\nNo puede ir vacio.\n";
/*  896 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  898 */         if (lstMontoTotOpe.equals("")) {
/*      */           
/*  900 */           lstError = "Error en el campo Monto Total de la Operacion.\nNo puede ir vacio.\n";
/*  901 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  903 */         if (lstTotMonGra.equals("")) {
/*      */           
/*  905 */           lstError = "Error en el campo Monto Gravado.\nNo puede ir vacio.\n";
/*  906 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/*  908 */         if (lstTotRet.equals("")) {
/*      */           
/*  910 */           lstError = "Error en el campo Total Retenciones.\nNo puede ir vacio.\n";
/*  911 */           throw new IllegalArgumentException(lstError);
/*      */         } 



				/*
				 * KAZ - MODIFICACION 4.0
				 */
				if (version.equals("")) {
					
					 lstError = "Error en el campo version \nNo puede ir vacio.\n";
					throw new IllegalArgumentException(lstError);
				}
				if(version.equals("2.0")) {
					
					if (lstLugarExpE.equals("")) {
						
						 lstError = "Error en el campo Lugar de Expedición E (CP)\nNo puede ir vacio.\n";
						throw new IllegalArgumentException(lstError);
						}
						
						if (lstRegFiscalE.equals("")) {
						
							 lstError = "Error en el campo RegimenFiscalE\nNo puede ir vacio.\n";
							throw new IllegalArgumentException(lstError);
						}
						if (lstRegFiscalR.equals("")) {
							
							 lstError = "Error en el campo DomicilioFiscalR (CP)\nNo puede ir vacio.\n";
							throw new IllegalArgumentException(lstError);
						}
						
				}else if(version.equals("1.0")) {
					
					if (lstLugarExpE.length() > 0) {
						
						 lstError = "Error en el campo Lugar de Expedición E (CP)\n Requiere ir vacio. Borra su contenido\n";
						throw new IllegalArgumentException(lstError);
						}
						
						if (lstRegFiscalE.length() > 0) {
						
							 lstError = "Error en el campo RegimenFiscalE\n Requiere ir vacio.  Borra su contenido \n";
							throw new IllegalArgumentException(lstError);
						}
						if (lstRegFiscalR.length() > 0) {
							
							 lstError = "Error en el campo DomicilioFiscalR (CP)\n Requiere ir vacio.  Borra su contenido \n";
							throw new IllegalArgumentException(lstError);
						}
						
						
				}
				
				
				/*KAZ - TERMINA CAMBIO CFDI 4.0 */




/*  913 */         for (ExcelRecordLines erl : palLineas) {
/*      */           
/*  915 */           String lstFolioLinea = (String)erl.getCampos().get(1);
/*  916 */           String lstMontoRet = (String)erl.getCampos().get(4);
/*  917 */           String lstTipoPagoRete = (String)erl.getCampos().get(5);
/*  918 */           if (lstFolioLinea.equals("")) {
/*      */             
/*  920 */             lstError = "Error en el campo Folio Linea.\nNo puede ir vacio.\nDe la Hoja de Trabajo de Retenciones.";
/*      */             
/*  922 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  924 */           if (lstMontoRet.equals("")) {
/*      */             
/*  926 */             lstError = "Error en el campo Monto retenido.\nNo puede ir vacio.\nDe la Hoja de Trabajo de Retenciones.";
/*      */             
/*  928 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  930 */           if (lstTipoPagoRete.equals("")) {
/*      */             
/*  932 */             lstError = "Error en el campo Tipo Pago Retención.\nNo puede ir vacio.\nDe la Hoja de Trabajo de Retenciones.";
/*      */             
/*  934 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*      */         } 
/*  937 */         if (lstClaveRet.equals("06")) {
/*      */           
/*  939 */           if (lstContraInter.equals("")) {
/*      */             
/*  941 */             lstError = "Error en el campo Contrato Intermediacion.\nNo puede ir vacio.\n";
/*  942 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  944 */           if (lstGanancia.equals("")) {
/*      */             
/*  946 */             lstError = "Error en el campo Ganancia.\nNo puede ir vacio.\n";
/*  947 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  949 */           if (lstPerdida.equals("")) {
/*      */             
/*  951 */             lstError = "Error en el campo Perdida.\nNo puede ir vacio.\n";
/*  952 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*      */         } 
/*  955 */         if (lstClaveRet.equals("14")) {
/*      */           
/*  957 */           if (lstTipoDividendo.equals("")) {
/*      */             
/*  959 */             lstError = "Error en el campo Tipo de Dividendo.\nNo puede ir vacio.\n";
/*  960 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  962 */           if (lstRetTerrNac.equals("")) {
/*      */             
/*  964 */             lstError = "Error en el campo Retencion Territorio Nacional.\nNo puede ir vacio.\n";
/*  965 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  967 */           if (lstRetTerrExt.equals("")) {
/*      */             
/*  969 */             lstError = "Error en el campo Retencion Territorio Extranjero.\nNo puede ir vacio.\n";
/*  970 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  972 */           if (lstTipoDistri.equals("")) {
/*      */             
/*  974 */             lstError = "Error en el campo Tipo Distribucion.\nNo puede ir vacio.\n";
/*  975 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*      */         } 
/*  978 */         if (lstClaveRet.equals("16")) {
/*      */           
/*  980 */           if (lstSisFinan.equals("")) {
/*      */             
/*  982 */             lstError = "Error en el campo Sistema Financiero.\nNo puede ir vacio.\n";
/*  983 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  985 */           if (lstRetPer.equals("")) {
/*      */             
/*  987 */             lstError = "Error en el campo Retiro en el Periodo.\nNo puede ir vacio.\n";
/*  988 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  990 */           if (lstOpeFinanDeriv.equals("")) {
/*      */             
/*  992 */             lstError = "Error en el campo Operaciones Financieras Derivadas.\nNo puede ir vacio.\n";
/*  993 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*  995 */           if (lstCompInteresNominal.equals("")) {
/*      */             
/*  997 */             lstError = "Error en el campo Interes Nominal.\nNo puede ir vacio.\n";
/*  998 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1000 */           if (lstCompInteresReal.equals("")) {
/*      */             
/* 1002 */             lstError = "Error en el campo Interes Real.\nNo puede ir vacio.\n";
/* 1003 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1005 */           if (lstCompPerdida.equals("")) {
/*      */             
/* 1007 */             lstError = "Error en el campo Perdida.\nNo puede ir vacio.\n";
/* 1008 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*      */         } 
/* 1011 */         if (lstClaveRet.equals("18") && 
/* 1012 */           lstCompEsBeneCobro.equals("")) {
/*      */           
/* 1014 */           lstError = "Error en el campo Es Beneficiario del Cobro.\nNo puede ir vacio.\n";
/* 1015 */           throw new IllegalArgumentException(lstError);
/*      */         } 
/* 1017 */         if (lstClaveRet.equals("20")) {
/*      */           
/* 1019 */           if (lstComp_Ent_Fed.equals("")) {
/*      */             
/* 1021 */             lstError = "Error en el campo Entidad Federativa.\nNo puede ir vacio.\n";
/* 1022 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1024 */           if (lstComp_Monto_Tot_Pag.equals("")) {
/*      */             
/* 1026 */             lstError = "Error en el campo Monto Total Pago.\nNo puede ir vacio.\n";
/* 1027 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1029 */           if (lstComp_Monto_Grav.equals("")) {
/*      */             
/* 1031 */             lstError = "Error en el campo Monto Gravado.\nNo puede ir vacio.\n";
/* 1032 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1034 */           if (lstComp_Monto_Exento.equals("")) {
/*      */             
/* 1036 */             lstError = "Error en el campo Monto Excento.\nNo puede ir vacio.\n";
/* 1037 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/*      */         } 
/* 1040 */         if (lstClaveRet.equals("24")) {
/*      */           
/* 1042 */           if (lstCom_OD_MonGanAcu.equals("")) {
/*      */             
/* 1044 */             lstError = "Error en el campo Monto Ganancia Acumulable.\nNo puede ir vacio.\n";
/* 1045 */             throw new IllegalArgumentException(lstError);
/*      */           } 
/* 1047 */           if (lstCom_OD_MonPerDed.equals(""))
/*      */           {
/* 1049 */             lstError = "Error en el campo Monto Perdida Deducible.\nNo puede ir vacio.\n";
/* 1050 */             throw new IllegalArgumentException(lstError);
/*      */           }

					
/*      */         
/*      */         } 
/*      */       } 
/* 1055 */     } catch (Exception e) {
/*      */       
/* 1057 */       if (lstError == null) {
/* 1058 */         throw new IllegalArgumentException(e.toString());
/*      */       }
/* 1060 */       throw new IllegalArgumentException(lstError);
/*      */     } 
/* 1062 */     return "OK";
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validarNombreArchivo(String pstNombreArchivo) throws IllegalArgumentException {
/* 1068 */     int linCountFiles = 0;
/* 1069 */     String valueFile = null;
/*      */     
/*      */     try {
/* 1072 */       FileInputStream fis = new FileInputStream(new File(pstNombreArchivo));
/* 1073 */       XSSFWorkbook wb = new XSSFWorkbook(fis);
/* 1074 */       XSSFSheet sheet = wb.getSheetAt(0);
/* 1075 */       int liRow = 3;
/* 1076 */       int liCol = 1;
/* 1077 */       XSSFRow row = sheet.getRow(liRow);
/* 1078 */       XSSFCell cell = row.getCell(liCol);
/* 1079 */       valueFile = cell.getStringCellValue();
/*      */       
/* 1081 */       linCountFiles = CargasCommon.validarNombreArchivo(valueFile);
/* 1082 */       if (linCountFiles > 0) {
/* 1083 */         throw new IllegalArgumentException("El archivo: " + valueFile + " ya ha sido generado previamente.");
/*      */       }
/* 1085 */       System.out.println("ECM23 VNA   " + linCountFiles);
/*      */     }
/* 1087 */     catch (SQLException ex) {
/*      */       
/* 1089 */       throw new IllegalArgumentException("No se ha podido conectar a la Base de Datos:  " + ex.getMessage() + "\n" + ex.getSQLState());
/*      */     }
/* 1091 */     catch (Exception ex) {
/*      */       
/* 1093 */       throw new IllegalArgumentException(ex.getMessage());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validarFolioUnico(ArrayList<ExcelRecord> palCabeceros) throws IllegalArgumentException {
/* 1100 */     ArrayList<String> alComparar = new ArrayList<String>();
/* 1101 */     String lstError = null;
/* 1102 */     int liComparar = 0;
/*      */     
/*      */     try {
/* 1105 */       for (ExcelRecord erCabeceros : palCabeceros)
/*      */       {
/* 1107 */         String lstFolio = (String)erCabeceros.getCampos().get(5);
/* 1108 */         if (liComparar == 0) {
/*      */           
/* 1110 */           alComparar.add(liComparar, lstFolio);
/*      */         }
/*      */         else {
/*      */           
/* 1114 */           for (int liConta = 0; liConta < alComparar.size(); liConta++) {
/* 1115 */             if (lstFolio.equals(alComparar.get(liConta))) {
/*      */               
/* 1117 */               lstError = "El Archivo: " + lstFolio + " ya existe en EBS.";
/* 1118 */               throw new IllegalArgumentException(lstError);
/*      */             } 
/*      */           } 
/* 1121 */           alComparar.add(liComparar, lstFolio);
/*      */         } 
/* 1123 */         liComparar++;
/*      */       }
/*      */     
/* 1126 */     } catch (Exception e) {
/*      */       
/* 1128 */       throw new IllegalArgumentException(lstError);
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validarFolioUnicoEnTabla(ArrayList<ExcelRecord> palCabeceros) throws IllegalArgumentException {
	/* 1160 */     String lstError = null;
	 lovalemp = false;
	ArrayList<String> loEmpressa =new ArrayList<String>();
/* 1161 */     for (ExcelRecord er : palCabeceros) {
/*      */       
/* 1163 */       String lstFolio = (String)er.getCampos().get(5);
	
if(!loEmpressa.contains((String)er.getCampos().get(9))) {
/*      */        lovalemp = validarEmpresaOIC((String)er.getCampos().get(9));


	}
	System.out.println("VAL EMP  "+lovalemp);
/*      */       try {
	
	if(!lovalemp) {		
					validarNombreArchivo(lsnombrearchivo);
/* 1166 */         CargasCommon.validarFolioUnicoEnTablaCC(lstFolio);
	}else {
			lbempEBS= false;
			}
					lbempEBS = true;       
	}
/* 1168 */       catch (SQLException ex) {
/*      */         
/* 1170 */         lstError = "No se ha podido conectar a la Base de Datos:\n" + ex.getMessage() + "\n" + ex.getSQLState();
/*      */         
/* 1172 */         throw new IllegalArgumentException(lstError);
/*      */       }
/* 1174 */       catch (Exception ex) {
/*      */         //kaz 26-07-2022 Se corrije por gusto
					if(ex.getMessage().length()>0) {
						lstError = ex.getMessage();
					}else {
						lstError = "El Folio " + lstFolio + " ya existe en la Base de Datos.";
					}

/* 1177 */         throw new IllegalArgumentException(lstError);
/*      */       } 
				lbempEBS = true;
		loEmpressa.add((String)er.getCampos().get(9));
		System.out.println("tamaño lista: "+loEmpressa.size());
		System.out.println("lista: "+loEmpressa.toString());
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   private static void validarCampoNombreArchivo(ArrayList<ExcelRecord> palCabeceros) throws IllegalArgumentException {
     		String lstFileNameAnt = "";
     		String lstFileNameAct = "";
     		boolean bFileNameFlag = true;

     		lstFileNameAnt = (String)((ExcelRecord)palCabeceros.get(0)).getCampos().get(1);
		     for (ExcelRecord record : palCabeceros) {
		      
		      lstFileNameAct = (String)record.getCampos().get(1);
		     if (!lstFileNameAnt.equals(lstFileNameAct)) {
		        bFileNameFlag = false;
		       }
		    } 
		     if (!bFileNameFlag)
		       throw new IllegalArgumentException("El campo Nombre de Archivo debe ser el mismo en todos los registros."); 
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

private static boolean validarEmpresaOIC(String lstRFC_Emp_Emi) throws IllegalArgumentException{
	
	InputParameters input = new InputParameters();
	input.setRfc(lstRFC_Emp_Emi);
	OutputParameters out = new OutputParameters();
	System.out.println("valido antes de folio");
	ValidarExistenciaEmpresa valemp = new ValidarExistenciaEmpresa();
	ExecutePttConsultarExistenciaEmpresaREQUEST valempi = valemp.getExecutePttConsultarExistenciaEmpresaREQUESTPt(); 
	out = valempi.validarExistenciaEmpresa(input);
	System.out.println("empresa existe: "+out.isExiste());
	return out.isExiste();
	
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
/*      */
public static boolean isLbempEBS() {
	return lbempEBS;
}
public static void setLbempEBS(boolean lbempEBS) {
	CargarInfoFiscalRetTer.lbempEBS = lbempEBS;
} }

