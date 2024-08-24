/*     */ package mx.com.televisa.cfdis.process.cargasexcel;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import mx.com.televisa.cfdis.data.ConnectionWrapper;
/*     */ import mx.com.televisa.cfdis.util.FilesHelper;
/*     */ import mx.com.televisa.cfdis.util.LeerProperties;
/*     */ import org.apache.commons.io.FileUtils;
/*     */ import org.apache.poi.xssf.usermodel.XSSFCell;
/*     */ import org.apache.poi.xssf.usermodel.XSSFRow;
/*     */ import org.apache.poi.xssf.usermodel.XSSFSheet;
/*     */ import org.apache.poi.xssf.usermodel.XSSFWorkbook;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class CargaHonorariosProcess
/*     */ {
/*     */   private static final int NUM_COLUMNS = 83;
/*     */   private static final String PIPE = "|";
/*     */   private static final int SERIE_INDEX = 27;
/*     */   private static final int FOLIO_INDEX = 28;
/*     */   private static final int NOMBRE_ARCHIVO_INDEX = 3;
/*     */   
/*     */   public static String doCargaHonorarios(String xlsFileName) throws Exception {
/*  54 */     validateLayout(xlsFileName);
/*  55 */     validateFileName(xlsFileName);
/*     */     
/*  57 */     ArrayList<ExcelRecord> excelTab = loadExcelTable(xlsFileName);
/*     */     
/*  59 */     ArrayList<ExcelRecordGroup> groups = groupRecords(excelTab);
/*     */     
/*  61 */     String localFolder = FilesHelper.getDetaultPath();
/*     */     
/*  63 */     String txtFileName = writeFile(groups, localFolder);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  70 */     String remoteDirectory = LeerProperties.sftpRemoteDirectory;
/*     */     
/*  72 */     return "Se ha generado el archivo: " + txtFileName + "\nen la ruta local: " + localFolder + 
/*  73 */       "\ny se ha enviado correctamente al servidor SFTP: " + remoteDirectory;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static void validateLayout(String fileName) throws IllegalArgumentException {
/*     */     try {
/*  80 */       FileInputStream fis = new FileInputStream(new File(fileName));
/*     */       
/*  82 */       XSSFWorkbook wb = new XSSFWorkbook(fis);
/*  83 */       XSSFSheet sheet = wb.getSheetAt(0);
/*     */       
/*  85 */       int HEADERS_ROW = 1;
/*  86 */       int DATOS_TRANSFERENCIA_BANCARIA_COL = 81;
/*     */ 
/*     */       
/*  89 */       XSSFRow row = sheet.getRow(HEADERS_ROW);
/*  90 */       XSSFCell cell = row.getCell(DATOS_TRANSFERENCIA_BANCARIA_COL);
/*     */ 
/*     */       
/*  93 */       String value = cell.getStringCellValue();
/*     */ 
/*     */       
/*  96 */       if (!value.equals("DATOS DE LA TRANSFERENCIA BANCARIA"))
/*     */       {
/*  98 */         throw new IllegalArgumentException("El layout seleccionado no es correcto");
/*     */       }
/*     */     }
/* 101 */     catch (Exception ex) {
/* 102 */       throw new IllegalArgumentException("El layout seleccionado no es correcto");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArrayList<ExcelRecord> loadExcelTable(String fileName) throws IOException, FileNotFoundException {
/* 115 */     boolean fileNameFlag = true;
/* 116 */     String fileNameAnt = "";
/* 117 */     String fileNameAct = "";
/* 118 */     String lstsql = "SELECT COUNT(*) FactValida FROM   Ap.Ap_Invoices_All  Inv WHERE  1 = 1 AND    Invoice_Num = ? AND    EXISTS (SELECT 'Empresas Validas 'FROM Apps.Hr_All_Organization_Units Orgs WHERE SUBSTR(Name, 1, 3) = ? AND Orgs.Organization_Id = Inv.Org_Id )AND    EXISTS (SELECT 'Proveedores Validos' FROM Po.Po_Vendors          Ven , Po.Po_Vendor_Sites_All VenSit WHERE Ven.Vendor_Id = VenSit.Vendor_Id AND Enabled_Flag  = 'Y' AND Segment1      = ? AND Inv.Vendor_Id = Ven.Vendor_Id AND Inv.Org_Id    = VenSit.Org_Id)";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 138 */     ConnectionWrapper luCW = null;
/* 139 */     ResultSet luResulset = null;
/* 140 */     String lstNumReg = "";
/* 141 */     int linNumReg = 0;
/*     */     
/* 143 */     FileInputStream fis = new FileInputStream(new File(fileName));
/*     */     
/* 145 */     XSSFWorkbook wb = new XSSFWorkbook(fis);
/* 146 */     XSSFSheet sheet = wb.getSheetAt(0);
/* 147 */     int START_ROW = 3;
/*     */     
/* 149 */     int rowIndex = START_ROW;
/* 150 */     XSSFRow row = sheet.getRow(rowIndex);
/*     */ 
/*     */     
/* 153 */     ArrayList<ExcelRecord> excelTab = new ArrayList<ExcelRecord>();
/*     */     
/* 155 */     while (row != null) {
/*     */       
/* 157 */       ExcelRecord registro = new ExcelRecord();
/*     */       
/* 159 */       for (int colIndex = 0; colIndex < 83; colIndex++) {
/*     */         
/* 161 */         XSSFCell cell = row.getCell(colIndex);
/* 162 */         String value = "";
/*     */         
/* 164 */         if (cell != null)
/*     */         {
/* 166 */           if (cell.getCellType() == 0) {
/* 167 */             value = Double.toString(cell.getNumericCellValue());
/*     */           } else {
/*     */             
/* 170 */             value = cell.getRichStringCellValue().getString();
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 178 */         registro.addCampo(value);
/*     */       } 
/*     */       
/* 181 */       excelTab.add(registro);
/*     */       
/* 183 */       rowIndex++;
/*     */       
/* 185 */       row = sheet.getRow(rowIndex);
/*     */     } 
/*     */ 
/*     */     
/* 189 */     fileNameAnt = (String)((ExcelRecord)excelTab.get(1)).getCampos().get(3);
/* 190 */     for (ExcelRecord record : excelTab) {
/* 191 */       fileNameAct = (String)record.getCampos().get(3);
/* 192 */       if (!fileNameAnt.equals(fileNameAct)) {
/* 193 */         fileNameFlag = false;
/*     */       }
/*     */     } 
/* 196 */     if (!fileNameFlag) {
/* 197 */       throw new IllegalArgumentException("El campo Nombre de Archivo debe ser el mismo en todos los registros");
/*     */     }
/*     */ 
/*     */     
/* 201 */     String fechaHoraFacturacion = "";
/* 202 */     String rfcEmisora = "";
/* 203 */     String tipoLayout = "";
/*     */     
/* 205 */     String nombreArchivo = "";
/* 206 */     String tipoDocumento = "";
/* 207 */     String rfcEmisora2 = "";
/* 208 */     String razonSocial = "";
/* 209 */     String calle = "";
/* 210 */     String municipio = "";
/* 211 */     String estado = "";
/* 212 */     String pais = "";
/* 213 */     String codPostal = "";
/*     */     
/* 215 */     String rfcEmisora3 = "";
/* 216 */     String serie = "";
/* 217 */     String folio = "";
/* 218 */     String fechaHoraFacturacion2 = "";
/*     */     
/* 220 */     String formaPago = "";
/* 221 */     String metodoPago = "";
/*     */     
/* 223 */     String totalISR = "";
/* 224 */     String percepcionesTotalGravado = "";
/* 225 */     String percepcionesTotalExento = "";
/* 226 */     String deduccionesTotalGravado = "";
/* 227 */     String deduccionesTotalExento = "";
/*     */     
/* 229 */     String numeroEmpleado = "";
/* 230 */     String rfcEmpleado = "";
/* 231 */     String nombreEmpleado = "";
/* 232 */     String calleEmpleado = "";
/*     */     
/* 234 */     String municipioEmpleado = "";
/* 235 */     String estadoEmpleado = "";
/* 236 */     String paisEmpleado = "";
/* 237 */     String codigoPostal = "";
/*     */     
/* 239 */     String curp = "";
/*     */     
/* 241 */     String concepto = "";
/* 242 */     String importeGravado = "";
/* 243 */     String importeExento = "";
/*     */     
/* 245 */     String facturaAP = "";
/* 246 */     String rfcProveedorAP = "";
/* 247 */     String folioAnt = "";
/*     */     
/* 249 */     int liConReg = 1;
/* 250 */     String lstMessage = "";
/*     */ 
/*     */     
/* 253 */     for (ExcelRecord record : excelTab) {
/* 254 */       lstMessage = "En la linea " + liConReg + " ,es obligatorio el campo: ";
/*     */       
/* 256 */       fechaHoraFacturacion = (String)record.getCampos().get(0);
/* 257 */       if (fechaHoraFacturacion.length() == 0 || fechaHoraFacturacion.equals("")) {
/* 258 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Fecha y Hora Facturacion.");
/*     */       }
/* 260 */       rfcEmisora = (String)record.getCampos().get(1);
/* 261 */       if (rfcEmisora.length() == 0 || rfcEmisora.equals("")) {
/* 262 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "RFC Emisora.");
/*     */       }
/* 264 */       tipoLayout = (String)record.getCampos().get(2);
/* 265 */       if (tipoLayout.length() == 0 || tipoLayout.equals("")) {
/* 266 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Tipo Layout.");
/*     */       }
/* 268 */       nombreArchivo = (String)record.getCampos().get(3);
/* 269 */       if (nombreArchivo.length() == 0 || nombreArchivo.equals("")) {
/* 270 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Nombre Archivo.");
/*     */       }
/* 272 */       tipoDocumento = (String)record.getCampos().get(4);
/* 273 */       if (tipoDocumento.length() == 0 || tipoDocumento.equals("")) {
/* 274 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Tipo Documento.");
/*     */       }
/* 276 */       razonSocial = (String)record.getCampos().get(5);
/* 277 */       if (razonSocial.length() == 0 || razonSocial.equals("")) {
/* 278 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Razon Social.");
/*     */       }
/* 280 */       calle = (String)record.getCampos().get(6);
/* 281 */       if (calle.length() == 0 || calle.equals("")) {
/* 282 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Calle Empresa.");
/*     */       }
/* 284 */       municipio = (String)record.getCampos().get(12);
/* 285 */       if (municipio.length() == 0 || municipio.equals("")) {
/* 286 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Municipio Empresa.");
/*     */       }
/* 288 */       estado = (String)record.getCampos().get(13);
/* 289 */       if (estado.length() == 0 || estado.equals("")) {
/* 290 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Estado Empresa.");
/*     */       }
/* 292 */       pais = (String)record.getCampos().get(14);
/* 293 */       if (pais.length() == 0 || pais.equals("")) {
/* 294 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Pais Empresa.");
/*     */       }
/* 296 */       codPostal = (String)record.getCampos().get(15);
/* 297 */       if (codPostal.length() == 0 || codPostal.equals("")) {
/* 298 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Codigo Postal Empresa.");
/*     */       }
/* 300 */       serie = (String)record.getCampos().get(27);
/* 301 */       if (serie.length() == 0 || serie.equals("")) {
/* 302 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Serie.");
/*     */       }
/* 304 */       folio = (String)record.getCampos().get(28);
/* 305 */       if (folio.length() == 0 || folio.equals("")) {
/* 306 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Folio.");
/*     */       }
/* 308 */       formaPago = (String)record.getCampos().get(31);
/* 309 */       if (formaPago.length() == 0 || formaPago.equals("")) {
/* 310 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Forma Pago.");
/*     */       }
/* 312 */       metodoPago = (String)record.getCampos().get(32);
/* 313 */       if (metodoPago.length() == 0 || metodoPago.equals("")) {
/* 314 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Método Pago.");
/*     */       }
/* 316 */       totalISR = (String)record.getCampos().get(42);
/* 317 */       if (totalISR.length() == 0 || totalISR.equals("")) {
/* 318 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Total ISR.");
/*     */       }
/* 320 */       percepcionesTotalGravado = (String)record.getCampos().get(43);
/* 321 */       if (percepcionesTotalGravado.length() == 0 || percepcionesTotalGravado.equals("")) {
/* 322 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Percepciones Total Gravado.");
/*     */       }
/* 324 */       percepcionesTotalExento = (String)record.getCampos().get(44);
/* 325 */       if (percepcionesTotalExento.length() == 0 || percepcionesTotalExento.equals("")) {
/* 326 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Percepciones Total Exento.");
/*     */       }
/* 328 */       deduccionesTotalGravado = (String)record.getCampos().get(45);
/* 329 */       if (deduccionesTotalGravado.length() == 0 || deduccionesTotalGravado.equals("")) {
/* 330 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Deducciones Total Gravado.");
/*     */       }
/* 332 */       deduccionesTotalExento = (String)record.getCampos().get(46);
/* 333 */       if (deduccionesTotalExento.length() == 0 || deduccionesTotalExento.equals("")) {
/* 334 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Deducciones Total Exento.");
/*     */       }
/* 336 */       numeroEmpleado = (String)record.getCampos().get(47);
/* 337 */       if (numeroEmpleado.length() == 0 || numeroEmpleado.equals("")) {
/* 338 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Numero Empleado.");
/*     */       }
/* 340 */       rfcEmpleado = (String)record.getCampos().get(48);
/* 341 */       if (rfcEmpleado.length() == 0 || rfcEmpleado.equals("")) {
/* 342 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Rfc Empleado.");
/*     */       }
/* 344 */       nombreEmpleado = (String)record.getCampos().get(49);
/* 345 */       if (nombreEmpleado.length() == 0 || nombreEmpleado.equals("")) {
/* 346 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Nombre Empleado.");
/*     */       }
/* 348 */       calleEmpleado = (String)record.getCampos().get(50);
/* 349 */       if (calleEmpleado.length() == 0 || calleEmpleado.equals("")) {
/* 350 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Calle Empleado.");
/*     */       }
/* 352 */       municipioEmpleado = (String)record.getCampos().get(56);
/* 353 */       if (municipioEmpleado.length() == 0 || municipioEmpleado.equals("")) {
/* 354 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Municipio Empleado.");
/*     */       }
/* 356 */       estadoEmpleado = (String)record.getCampos().get(57);
/* 357 */       if (estadoEmpleado.length() == 0 || estadoEmpleado.equals("")) {
/* 358 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Estado Empleado.");
/*     */       }
/* 360 */       paisEmpleado = (String)record.getCampos().get(58);
/* 361 */       if (paisEmpleado.length() == 0 || paisEmpleado.equals("")) {
/* 362 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Pais Empleado.");
/*     */       }
/* 364 */       codigoPostal = (String)record.getCampos().get(59);
/* 365 */       if (codigoPostal.length() == 0 || codigoPostal.equals("")) {
/* 366 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Codigo Postal Empleado.");
/*     */       }
/* 368 */       curp = (String)record.getCampos().get(62);
/* 369 */       if (curp.length() == 0 || curp.equals("")) {
/* 370 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "CURP.");
/*     */       }
/* 372 */       concepto = (String)record.getCampos().get(78);
/* 373 */       if (concepto.length() == 0 || concepto.equals("")) {
/* 374 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Concepto.");
/*     */       }
/* 376 */       importeGravado = (String)record.getCampos().get(79);
/* 377 */       if (importeGravado.length() == 0 || importeGravado.equals("")) {
/* 378 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Importe Gravado.");
/*     */       }
/* 380 */       importeExento = (String)record.getCampos().get(80);
/* 381 */       if (importeExento.length() == 0 || importeExento.equals("")) {
/* 382 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Importe Exento.");
/*     */       }
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 406 */     return excelTab;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArrayList<ExcelRecordGroup> groupRecords(ArrayList<ExcelRecord> excelTab) {
/* 416 */     ArrayList<ExcelRecordGroup> groups = new ArrayList<ExcelRecordGroup>();
/*     */     
/* 418 */     for (ExcelRecord record : excelTab) {
/*     */ 
/*     */       
/* 421 */       String serie = (String)record.getCampos().get(27);
/* 422 */       String folio = (String)record.getCampos().get(28);
/*     */       
/* 424 */       String gId = String.valueOf(serie) + "-" + folio;
/*     */       
/* 426 */       ExcelRecordGroup grupo = new ExcelRecordGroup(gId);
/*     */       
/* 428 */       if (!groups.contains(grupo))
/*     */       {
/* 430 */         groups.add(grupo);
/*     */       }
/*     */       
/* 433 */       ExcelRecordGroup group = (ExcelRecordGroup)groups.get(groups.indexOf(grupo));
/*     */       
/* 435 */       group.getRegistros().add(record);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 446 */     return groups;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static String writeFile(ArrayList<ExcelRecordGroup> groups, String path) throws Exception, IOException {
/* 454 */     int liIndexValue = 0;
/* 455 */     String lstCargaManual = "HA";
/*     */ 
/*     */ 
/*     */     
/* 459 */     System.setProperty("line.separator", "\n");
/*     */     
/* 461 */     File dir = new File(path);
/* 462 */     FileUtils.forceMkdir(dir);
/*     */     
/* 464 */     String fileName = (String)((ExcelRecord)((ExcelRecordGroup)groups.get(0)).getRegistros().get(0)).getCampos().get(3);
/*     */     
/* 466 */     File file = new File(dir, fileName);
/*     */     
/* 468 */     FileWriter writer = new FileWriter(file);
/*     */     
/* 470 */     String endLine = System.getProperty("line.separator");
/*     */     
/* 472 */     liIndexValue = CargasCommon.getIndexValue();
/*     */ 
/*     */     
/*     */     try {
/* 476 */       for (ExcelRecordGroup grupo : groups) {
/*     */         
/* 478 */         ExcelRecord primerRelgistro = (ExcelRecord)grupo.getRegistros().get(0);
/*     */         
/* 480 */         String fechaHoraFacturacion = (String)primerRelgistro.getCampos().get(0);
/* 481 */         String rfcEmisora = (String)primerRelgistro.getCampos().get(1);
/* 482 */         String tipoLayout = (String)primerRelgistro.getCampos().get(2);
/*     */         
/* 484 */         String nombreArchivo = (String)primerRelgistro.getCampos().get(3);
/* 485 */         String tipoDocumento = (String)primerRelgistro.getCampos().get(4);
/*     */         
/* 487 */         String razonSocial = (String)primerRelgistro.getCampos().get(5);
/* 488 */         String calle = (String)primerRelgistro.getCampos().get(6);
/* 489 */         String numExt = (String)primerRelgistro.getCampos().get(7);
/* 490 */         String numInt = (String)primerRelgistro.getCampos().get(8);
/* 491 */         String colonia = (String)primerRelgistro.getCampos().get(9);
/* 492 */         String localidad = (String)primerRelgistro.getCampos().get(10);
/* 493 */         String referencia = (String)primerRelgistro.getCampos().get(11);
/* 494 */         String municipio = (String)primerRelgistro.getCampos().get(12);
/* 495 */         String estado = (String)primerRelgistro.getCampos().get(13);
/* 496 */         String pais = (String)primerRelgistro.getCampos().get(14);
/* 497 */         String codPostal = (String)primerRelgistro.getCampos().get(15);
/* 498 */         String calleReferencia = (String)primerRelgistro.getCampos().get(16);
/* 499 */         String numExtReferencia = (String)primerRelgistro.getCampos().get(17);
/* 500 */         String numIntReferenca = (String)primerRelgistro.getCampos().get(18);
/* 501 */         String coloniaReferencia = (String)primerRelgistro.getCampos().get(19);
/* 502 */         String localidadReferencia = (String)primerRelgistro.getCampos().get(20);
/* 503 */         String referenciaReferencia = (String)primerRelgistro.getCampos().get(21);
/* 504 */         String municipioReferencia = (String)primerRelgistro.getCampos().get(22);
/* 505 */         String estadoReferencia = (String)primerRelgistro.getCampos().get(23);
/* 506 */         String paisReferencia = (String)primerRelgistro.getCampos().get(24);
/* 507 */         String codPostalReferencia = (String)primerRelgistro.getCampos().get(25);
/*     */ 
/*     */         
/* 510 */         String numeroEmpresa = (String)primerRelgistro.getCampos().get(26);
/*     */         
/* 512 */         String serie = (String)primerRelgistro.getCampos().get(27);
/* 513 */         String folio = (String)primerRelgistro.getCampos().get(28);
/*     */         
/* 515 */         String numFactura = (String)primerRelgistro.getCampos().get(28);
/* 516 */         String rfcProv = (String)primerRelgistro.getCampos().get(28);
/* 517 */         String folioFiscAnt = (String)primerRelgistro.getCampos().get(28);
/*     */         
/* 519 */         String regimen = (String)primerRelgistro.getCampos().get(29);
/* 520 */         String lugarExpedicion = (String)primerRelgistro.getCampos().get(30);
/*     */         
/* 522 */         String formaPago = (String)primerRelgistro.getCampos().get(31);
/* 523 */         String metodoPago = (String)primerRelgistro.getCampos().get(32);
/* 524 */         String subtotal = (String)primerRelgistro.getCampos().get(33);
/* 525 */         String descuento = (String)primerRelgistro.getCampos().get(34);
/* 526 */         String totalFactura = (String)primerRelgistro.getCampos().get(35);
/* 527 */         String descripcion = (String)primerRelgistro.getCampos().get(36);
/* 528 */         String precioUnitario = (String)primerRelgistro.getCampos().get(37);
/* 529 */         String fechaPago = (String)primerRelgistro.getCampos().get(38);
/* 530 */         String fechaInicialPago = (String)primerRelgistro.getCampos().get(39);
/* 531 */         String fechaFinalPago = (String)primerRelgistro.getCampos().get(40);
/* 532 */         String numeroDiasPagados = (String)primerRelgistro.getCampos().get(41);
/* 533 */         String totalISR = (String)primerRelgistro.getCampos().get(42);
/* 534 */         String percepcionesTotalGravado = (String)primerRelgistro.getCampos().get(43);
/* 535 */         String percepcionesTotalExento = (String)primerRelgistro.getCampos().get(44);
/* 536 */         String deduccionesTotalGravado = (String)primerRelgistro.getCampos().get(45);
/* 537 */         String deduccionesTotalExento = (String)primerRelgistro.getCampos().get(46);
/*     */         
/* 539 */         String numeroEmpleado = (String)primerRelgistro.getCampos().get(47);
/* 540 */         String rfcEmpleado = (String)primerRelgistro.getCampos().get(48);
/* 541 */         String nombreEmpleado = (String)primerRelgistro.getCampos().get(49);
/* 542 */         String calleEmpleado = (String)primerRelgistro.getCampos().get(50);
/* 543 */         String numeroExteriorEmpleado = (String)primerRelgistro.getCampos().get(51);
/* 544 */         String numeroInteriorEmpleado = (String)primerRelgistro.getCampos().get(52);
/* 545 */         String coloniaEmpleado = (String)primerRelgistro.getCampos().get(53);
/* 546 */         String localidadEmpleado = (String)primerRelgistro.getCampos().get(54);
/* 547 */         String referenciaEmpleado = (String)primerRelgistro.getCampos().get(55);
/* 548 */         String municipioEmpleado = (String)primerRelgistro.getCampos().get(56);
/* 549 */         String estadoEmpleado = (String)primerRelgistro.getCampos().get(57);
/* 550 */         String paisEmpleado = (String)primerRelgistro.getCampos().get(58);
/* 551 */         String codigoPostal = (String)primerRelgistro.getCampos().get(59);
/* 552 */         String email = (String)primerRelgistro.getCampos().get(60);
/* 553 */         String registroPatronal = (String)primerRelgistro.getCampos().get(61);
/* 554 */         String curp = (String)primerRelgistro.getCampos().get(62);
/* 555 */         String tipoRegimen = (String)primerRelgistro.getCampos().get(63);
/* 556 */         String numSeguridadSocial = (String)primerRelgistro.getCampos().get(64);
/* 557 */         String fechaInicioRelacionLaboral = (String)primerRelgistro.getCampos().get(65);
/* 558 */         String antiguedad = (String)primerRelgistro.getCampos().get(66);
/* 559 */         String puesto = (String)primerRelgistro.getCampos().get(67);
/* 560 */         String tipoContrato = (String)primerRelgistro.getCampos().get(68);
/* 561 */         String tipoJornada = (String)primerRelgistro.getCampos().get(69);
/* 562 */         String periodicidadPago = (String)primerRelgistro.getCampos().get(70);
/* 563 */         String salarioBase = (String)primerRelgistro.getCampos().get(71);
/* 564 */         String riesgoPuesto = (String)primerRelgistro.getCampos().get(72);
/* 565 */         String salarioDiarioIntegrado = (String)primerRelgistro.getCampos().get(73);
/* 566 */         String departamento = (String)primerRelgistro.getCampos().get(74);
/*     */         
/* 568 */         String banco = (String)primerRelgistro.getCampos().get(81);
/* 569 */         String clabe = (String)primerRelgistro.getCampos().get(82);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 575 */         writer.write("0000|" + fechaHoraFacturacion + "|" + rfcEmisora + "|" + tipoLayout + endLine);
/*     */         
/* 577 */         writer.write("00|" + nombreArchivo + "|" + tipoDocumento + "|" + rfcEmisora + "|" + 
/* 578 */             razonSocial + "|" + calle + "|" + numExt + "|" + numInt + "|" + colonia + "|" + localidad + "|" + 
/* 579 */             referencia + "|" + municipio + "|" + estado + "|" + pais + "|" + codPostal + "|" + 
/* 580 */             calleReferencia + "|" + numExtReferencia + "|" + numIntReferenca + "|" + coloniaReferencia + "|" + 
/* 581 */             localidadReferencia + "|" + referenciaReferencia + "|" + municipioReferencia + "|" + estadoReferencia + "|" + 
/* 582 */             paisReferencia + "|" + codPostalReferencia + "|" + endLine);
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 587 */         writer.write("01|" + rfcEmisora + "|" + numeroEmpresa + "|" + nombreArchivo + "|" + 
/* 588 */             serie + "|" + folio + "|" + fechaHoraFacturacion + "|" + regimen + "|" + 
/* 589 */             lugarExpedicion + "|" + numFactura + "|" + rfcProv + "|" + folioFiscAnt + endLine);
/*     */ 
/*     */ 
/*     */         
/* 593 */         writer.write("02|" + formaPago + "|" + metodoPago + "|" + subtotal + "|" + descuento + "|" + 
/* 594 */             totalFactura + "|" + descripcion + "|" + precioUnitario + "|" + fechaPago + "|" + 
/* 595 */             fechaInicialPago + "|" + fechaFinalPago + "|" + numeroDiasPagados + "|" + 
/* 596 */             totalISR + "|" + percepcionesTotalGravado + "|" + percepcionesTotalExento + "|" + 
/* 597 */             deduccionesTotalGravado + "|" + deduccionesTotalExento + endLine);
/*     */ 
/*     */         
/* 600 */         writer.write("03|" + numeroEmpleado + "|" + 
/* 601 */             rfcEmpleado + "|" + 
/* 602 */             nombreEmpleado + "|" + 
/* 603 */             calleEmpleado + "|" + 
/* 604 */             numeroExteriorEmpleado + "|" + 
/* 605 */             numeroInteriorEmpleado + "|" + 
/* 606 */             coloniaEmpleado + "|" + 
/* 607 */             localidadEmpleado + "|" + 
/* 608 */             referenciaEmpleado + "|" + 
/* 609 */             municipioEmpleado + "|" + 
/* 610 */             estadoEmpleado + "|" + 
/* 611 */             paisEmpleado + "|" + 
/* 612 */             codigoPostal + "|" + 
/* 613 */             email + "|" + 
/* 614 */             registroPatronal + "|" + 
/* 615 */             curp + "|" + 
/* 616 */             tipoRegimen + "|" + 
/* 617 */             numSeguridadSocial + "|" + 
/* 618 */             fechaInicioRelacionLaboral + "|" + 
/* 619 */             antiguedad + "|" + 
/* 620 */             puesto + "|" + 
/* 621 */             tipoContrato + "|" + 
/* 622 */             tipoJornada + "|" + 
/* 623 */             periodicidadPago + "|" + 
/* 624 */             salarioBase + "|" + 
/* 625 */             riesgoPuesto + "|" + 
/* 626 */             salarioDiarioIntegrado + "|" + 
/* 627 */             departamento + endLine);
/*     */ 
/*     */         
/* 630 */         for (ExcelRecord registro : grupo) {
/*     */           
/* 632 */           String calficador = (String)registro.getCampos().get(75);
/* 633 */           String tipo = (String)registro.getCampos().get(76);
/* 634 */           String clave = (String)registro.getCampos().get(77);
/* 635 */           String concepto = (String)registro.getCampos().get(78);
/* 636 */           String importeGravado = (String)registro.getCampos().get(79);
/* 637 */           String importeExento = (String)registro.getCampos().get(80);
/*     */ 
/*     */           
/* 640 */           writer.write("04|" + calficador + "|" + tipo + "|" + clave + "|" + 
/* 641 */               concepto + "|" + importeGravado + "|" + importeExento + "|" + endLine);
/*     */           try {
/* 643 */             CargasCommon.insertFileLog(fileName, 
/* 644 */                 serie, 
/* 645 */                 folio, 
/* 646 */                 rfcEmisora, 
/* 647 */                 razonSocial, 
/* 648 */                 calle, 
/* 649 */                 numExt, 
/* 650 */                 colonia, 
/* 651 */                 localidad, 
/* 652 */                 municipio, 
/* 653 */                 estado, 
/* 654 */                 pais, 
/* 655 */                 codPostal, 
/* 656 */                 tipoDocumento, 
/* 657 */                 serie, 
/* 658 */                 folio, 
/* 659 */                 fechaHoraFacturacion, 
/* 660 */                 lugarExpedicion, 
/* 661 */                 formaPago, 
/* 662 */                 metodoPago, 
/* 663 */                 fechaHoraFacturacion, 
/* 664 */                 formaPago, 
/* 665 */                 metodoPago, 
/*     */                 
/* 667 */                 rfcEmpleado, 
/* 668 */                 nombreEmpleado, 
/* 669 */                 curp, 
/* 670 */                 calleEmpleado, 
/* 671 */                 numeroExteriorEmpleado, 
/* 672 */                 coloniaEmpleado, 
/* 673 */                 localidadEmpleado, 
/* 674 */                 municipioEmpleado, 
/* 675 */                 estadoEmpleado, 
/* 676 */                 paisEmpleado, 
/* 677 */                 codigoPostal, 
/*     */                 
/* 679 */                 concepto, 
/* 680 */                 importeGravado, 
/* 681 */                 importeExento, 
/*     */                 
/* 683 */                 banco, 
/* 684 */                 clabe, 
/* 685 */                 liIndexValue, 
/* 686 */                 lstCargaManual, 
/* 687 */                 numeroEmpresa);
/*     */           }
/* 689 */           catch (Exception e) {
/* 690 */             throw new IllegalArgumentException("Error al escribir en la tabla log");
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 695 */         writer.write("05|" + banco + "|" + clabe + endLine);
/* 696 */         writer.write("99" + endLine);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 707 */       return fileName;
/*     */     } finally {
/*     */       
/* 710 */       writer.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void validateFileName(String fileName) throws IllegalArgumentException {
/* 715 */     int linCountFiles = 0;
/* 716 */     String valueFile = null;
/*     */     try {
/* 718 */       FileInputStream fis = new FileInputStream(new File(fileName));
/*     */       
/* 720 */       XSSFWorkbook wb = new XSSFWorkbook(fis);
/* 721 */       XSSFSheet sheet = wb.getSheetAt(0);
/*     */       
/* 723 */       int TEST_ROW = 3;
/* 724 */       int NOMBRE_ARCHIVO = 3;
/*     */ 
/*     */       
/* 727 */       XSSFRow row = sheet.getRow(TEST_ROW);
/* 728 */       XSSFCell cell = row.getCell(NOMBRE_ARCHIVO);
/*     */ 
/*     */       
/* 731 */       valueFile = cell.getStringCellValue();
/* 732 */       linCountFiles = CargasCommon.checkFileName(valueFile);
/*     */       
/* 734 */       if (linCountFiles > 0)
/*     */       {
/* 736 */         throw new IllegalArgumentException("El archivo:" + valueFile + " ya ha sido generado previamente.");
/*     */       }
/*     */     }
/* 739 */     catch (SQLException ex) {
/* 740 */       throw new IllegalArgumentException("No se ha podido conectar a la Base de Datos:  " + ex.getMessage() + "  " + ex.getSQLState());
/*     */     }
/* 742 */     catch (Exception ex) {
/* 743 */       throw new IllegalArgumentException("El archivo:" + valueFile + " ya ha sido generado previamente.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 759 */   public static void main(String[] args) throws Exception { doCargaHonorarios("/Users/Nava/Layout de Carga Manual CFDI Retenciones Honorarios Asimilables.xlsx"); }
/*     */ }

