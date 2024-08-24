/*     */ package mx.com.televisa.cfdis.process.cargasexcel;
/*     */ 
/*     */ import java.io.File;
/*     */ import java.io.FileInputStream;
/*     */ import java.io.FileNotFoundException;
/*     */ import java.io.FileWriter;
/*     */ import java.io.IOException;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
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
/*     */ public class CargaTercerosProcess
/*     */ {
/*     */   private static final int NUM_COLUMNS = 38;
/*     */   private static final int NUM_COLUMNS_AT_LINEAS = 5;
/*     */   private static final String PIPE = "|";
/*     */   private static final int SERIE_INDEX = 15;
/*     */   private static final int FOLIO_INDEX = 16;
/*     */   private static final int NOMBRE_ARCHIVO_INDEX = 3;
/*     */   
/*     */   public static String doCargaTerceros(String xlsFileName) throws Exception {
/*  50 */     validateLayout(xlsFileName);
/*  51 */     validateFileName(xlsFileName);
/*     */     
/*  53 */     ArrayList<ExcelRecord> excelTab = loadExcelTable(xlsFileName);
/*     */     
/*  55 */     ArrayList<ExcelRecordGroup> groups = groupRecords(excelTab);
/*     */ 
/*     */     
/*  58 */     ArrayList<ExcelRecordLines> alLoadExcelLines = loadExcelLines(xlsFileName);
/*     */     
/*  60 */     String localFolder = FilesHelper.getDetaultPath();
/*     */ 
/*     */ 
/*     */     
/*  64 */     String txtFileName = writeFile(groups, localFolder, alLoadExcelLines);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*  71 */     String remoteDirectory = LeerProperties.sftpRemoteDirectory;
/*     */     
/*  73 */     return "Se ha generado el archivo: " + txtFileName + "\nen la ruta local de: " + localFolder + 
/*  74 */       "\ny se ha enviado correctamente al servidor SFTP: " + remoteDirectory;
/*     */   }
/*     */   
/*     */   private static void validateLayout(String fileName) throws IllegalArgumentException {
/*     */     try {
/*  79 */       FileInputStream fis = new FileInputStream(new File(fileName));
/*     */       
/*  81 */       XSSFWorkbook wb = new XSSFWorkbook(fis);
/*  82 */       XSSFSheet sheet = wb.getSheetAt(0);
/*     */       
/*  84 */       int HEADERS_ROW = 1;
/*     */       
/*  86 */       int DATOS_TRANSFERENCIA_BANCARIA_COL = 36;
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
/*     */   private static ArrayList<ExcelRecord> loadExcelTable(String fileName) throws IOException, FileNotFoundException {
/* 113 */     boolean fileNameFlag = true;
/* 114 */     String fileNameAnt = "";
/* 115 */     String fileNameAct = "";
/* 116 */     FileInputStream fis = new FileInputStream(new File(fileName));
/*     */     
/* 118 */     XSSFWorkbook wb = new XSSFWorkbook(fis);
/* 119 */     XSSFSheet sheet = wb.getSheetAt(0);
/* 120 */     int START_ROW = 3;
/*     */     
/* 122 */     int rowIndex = START_ROW;
/* 123 */     XSSFRow row = sheet.getRow(rowIndex);
/*     */     
/* 125 */     ArrayList<ExcelRecord> excelTab = new ArrayList<ExcelRecord>();
/*     */ 
/*     */     
/* 128 */     while (row != null) {
/*     */       
/* 130 */       ExcelRecord registro = new ExcelRecord();
/*     */       
/* 132 */       for (int colIndex = 0; colIndex < 38; colIndex++) {
/*     */         
/* 134 */         XSSFCell cell = row.getCell(colIndex);
/*     */         
/* 136 */         String value = "";
/*     */         
/* 138 */         if (cell != null)
/*     */         {
/* 140 */           if (cell.getCellType() == 0) {
/* 141 */             value = Double.toString(cell.getNumericCellValue());
/*     */           } else {
/*     */             
/* 144 */             value = cell.getRichStringCellValue().getString();
/*     */           } 
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 152 */         registro.addCampo(value);
/*     */       } 
/*     */       
/* 155 */       excelTab.add(registro);
/*     */       
/* 157 */       rowIndex++;
/*     */       
/* 159 */       row = sheet.getRow(rowIndex);
/*     */     } 
/*     */     
/* 162 */     fileNameAnt = (String)((ExcelRecord)excelTab.get(1)).getCampos().get(3);
/* 163 */     for (ExcelRecord record : excelTab) {
/* 164 */       fileNameAct = (String)record.getCampos().get(3);
/* 165 */       if (!fileNameAnt.equals(fileNameAct)) {
/* 166 */         fileNameFlag = false;
/*     */       }
/*     */     } 
/* 169 */     if (!fileNameFlag) {
/* 170 */       throw new IllegalArgumentException("El campo Nombre de Archivo debe ser el mismo en todos los registros");
/*     */     }
/*     */ 
/*     */     
/* 174 */     String rfcEmprresa = "";
/* 175 */     String rfcProveedor = "";
/* 176 */     String fechaFacturacion0 = "";
/* 177 */     String serie = "";
/* 178 */     String folio = "";
/* 179 */     String nombreArchivo = "";
/* 180 */     String formaPago = "";
/* 181 */     String metodoPago = "";
/* 182 */     String calleEmp = "";
/* 183 */     String municioEmp = "";
/* 184 */     String estadoEmp = "";
/* 185 */     String paisEmp = "";
/* 186 */     String CodPosEmp = "";
/* 187 */     String callePro = "";
/* 188 */     String municioPro = "";
/* 189 */     String estadoPro = "";
/* 190 */     String paisPro = "";
/* 191 */     String CodPosPro = "";
/*     */ 
/*     */ 
/*     */     
/* 195 */     String nombreProveedor = "";
/* 196 */     String tipoLayout = "";
/* 197 */     String razonSocial = "";
/*     */     
/* 199 */     int liConReg = 1;
/* 200 */     String lstMessage = "";
/*     */     
/* 202 */     for (ExcelRecord record : excelTab) {
/* 203 */       lstMessage = "En la linea " + liConReg + " ,es obligatorio el campo: ";
/*     */       
/* 205 */       tipoLayout = (String)record.getCampos().get(2);
/* 206 */       if (tipoLayout.length() == 0 || tipoLayout.equals("")) {
/* 207 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Tipo Layout.");
/*     */       }
/* 209 */       rfcEmprresa = (String)record.getCampos().get(1);
/* 210 */       if (rfcEmprresa.length() == 0 || rfcEmprresa.equals("")) {
/* 211 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "RFC Empresa.");
/*     */       }
/* 213 */       razonSocial = (String)record.getCampos().get(5);
/* 214 */       if (razonSocial.length() == 0 || razonSocial.equals("")) {
/* 215 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Razon Social.");
/*     */       }
/* 217 */       rfcProveedor = (String)record.getCampos().get(25);
/* 218 */       if (rfcProveedor.length() == 0 || rfcProveedor.equals("")) {
/* 219 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "RFC Proveedor.");
/*     */       }
/* 221 */       fechaFacturacion0 = (String)record.getCampos().get(0);
/* 222 */       if (fechaFacturacion0.length() == 0 || fechaFacturacion0.equals("")) {
/* 223 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Fecha Facturacion.");
/*     */       }
/* 225 */       serie = (String)record.getCampos().get(15);
/* 226 */       if (serie.length() == 0 || serie.equals("")) {
/* 227 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Serie.");
/*     */       }
/* 229 */       folio = (String)record.getCampos().get(16);
/* 230 */       if (folio.length() == 0 || folio.equals("")) {
/* 231 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Folio");
/*     */       }
/* 233 */       nombreArchivo = (String)record.getCampos().get(3);
/* 234 */       if (nombreArchivo.length() == 0 || nombreArchivo.equals("")) {
/* 235 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Nombre Archivo.");
/*     */       }
/* 237 */       formaPago = (String)record.getCampos().get(18);
/* 238 */       if (formaPago.length() == 0 || formaPago.equals("")) {
/* 239 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Forma Pago.");
/*     */       }
/* 241 */       metodoPago = (String)record.getCampos().get(19);
/* 242 */       if (metodoPago.length() == 0 || metodoPago.equals("")) {
/* 243 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Metodo pago.");
/*     */       }
/* 245 */       calleEmp = (String)record.getCampos().get(6);
/* 246 */       if (calleEmp.length() == 0 || calleEmp.equals("")) {
/* 247 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Calle Empresa.");
/*     */       }
/* 249 */       municioEmp = (String)record.getCampos().get(10);
/* 250 */       if (municioEmp.length() == 0 || municioEmp.equals("")) {
/* 251 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Municipio Empresa.");
/*     */       }
/* 253 */       estadoEmp = (String)record.getCampos().get(11);
/* 254 */       if (estadoEmp.length() == 0 || estadoEmp.equals("")) {
/* 255 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Estado Empresa.");
/*     */       }
/* 257 */       paisEmp = (String)record.getCampos().get(12);
/* 258 */       if (paisEmp.length() == 0 || paisEmp.equals("")) {
/* 259 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Pais Empresa.");
/*     */       }
/* 261 */       CodPosEmp = (String)record.getCampos().get(13);
/* 262 */       if (CodPosEmp.length() == 0 || CodPosEmp.equals("")) {
/* 263 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Codigo Postal Empresa.");
/*     */       }
/* 265 */       callePro = (String)record.getCampos().get(28);
/* 266 */       if (callePro.length() == 0 || callePro.equals("")) {
/* 267 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Calle Proveedor.");
/*     */       }
/* 269 */       municioPro = (String)record.getCampos().get(32);
/* 270 */       if (municioPro.length() == 0 || municioPro.equals("")) {
/* 271 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Municipio Proveedor.");
/*     */       }
/* 273 */       estadoPro = (String)record.getCampos().get(33);
/* 274 */       if (estadoPro.length() == 0 || estadoPro.equals("")) {
/* 275 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Estado Proveedor.");
/*     */       }
/* 277 */       paisPro = (String)record.getCampos().get(34);
/* 278 */       if (paisPro.length() == 0 || paisPro.equals("")) {
/* 279 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Pais Proveedor.");
/*     */       }
/* 281 */       CodPosPro = (String)record.getCampos().get(35);
/* 282 */       if (CodPosPro.length() == 0 || CodPosPro.equals("")) {
/* 283 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Codigo Postal Proveedor.");
/*     */       }
/* 285 */       nombreProveedor = (String)record.getCampos().get(26);
/* 286 */       if (nombreProveedor.length() == 0 || nombreProveedor.equals("")) {
/* 287 */         throw new IllegalArgumentException(String.valueOf(lstMessage) + "Nombre Proveedor.");
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
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 303 */       liConReg++;
/*     */     } 
/*     */     
/* 306 */     return excelTab;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArrayList<ExcelRecordGroup> groupRecords(ArrayList<ExcelRecord> excelTab) {
/* 314 */     ArrayList<ExcelRecordGroup> groups = new ArrayList<ExcelRecordGroup>();
/*     */     
/* 316 */     for (ExcelRecord record : excelTab) {
/* 317 */       String serie = (String)record.getCampos().get(15);
/* 318 */       String folio = (String)record.getCampos().get(16);
/* 319 */       String gId = String.valueOf(serie) + "-" + folio;
/* 320 */       ExcelRecordGroup grupo = new ExcelRecordGroup(gId);
/*     */       
/* 322 */       if (!groups.contains(grupo))
/*     */       {
/* 324 */         groups.add(grupo);
/*     */       }
/*     */       
/* 327 */       ExcelRecordGroup group = (ExcelRecordGroup)groups.get(groups.indexOf(grupo));
/* 328 */       group.getRegistros().add(record);
/*     */     } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 338 */     return groups;
/*     */   }
/*     */   
/*     */   private static void validateFileName(String fileName) throws IllegalArgumentException {
/* 342 */     int linCountFiles = 0;
/* 343 */     String valueFile = null;
/*     */     try {
/* 345 */       FileInputStream fis = new FileInputStream(new File(fileName));
/*     */       
/* 347 */       XSSFWorkbook wb = new XSSFWorkbook(fis);
/* 348 */       XSSFSheet sheet = wb.getSheetAt(0);
/*     */       
/* 350 */       int TEST_ROW = 3;
/* 351 */       int NOMBRE_ARCHIVO = 3;
/*     */ 
/*     */       
/* 354 */       XSSFRow row = sheet.getRow(TEST_ROW);
/* 355 */       XSSFCell cell = row.getCell(NOMBRE_ARCHIVO);
/*     */ 
/*     */       
/* 358 */       valueFile = cell.getStringCellValue();
/* 359 */       linCountFiles = CargasCommon.checkFileName(valueFile);
/*     */       
/* 361 */       if (linCountFiles > 0)
/*     */       {
/* 363 */         throw new IllegalArgumentException("El archivo:" + valueFile + " ya ha sido generado previamente.");
/*     */       }
/*     */     }
/* 366 */     catch (SQLException ex) {
/* 367 */       throw new IllegalArgumentException("No se ha podido conectar a la Base de Datos:  " + ex.getMessage() + "  " + ex.getSQLState());
/* 368 */     } catch (Exception ex) {
/* 369 */       throw new IllegalArgumentException("El archivo:" + valueFile + " ya ha sido generado previamente.");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private static ArrayList<ExcelRecordLines> loadExcelLines(String fileName) throws IOException, FileNotFoundException {
/* 376 */     FileInputStream fis = new FileInputStream(new File(fileName));
/* 377 */     XSSFWorkbook wb = new XSSFWorkbook(fis);
/* 378 */     XSSFSheet sheet = wb.getSheetAt(1);
/* 379 */     int START_ROW = 1;
/* 380 */     int rowIndex = START_ROW;
/* 381 */     XSSFRow row = sheet.getRow(rowIndex);
/* 382 */     ArrayList<ExcelRecordLines> excelTab = new ArrayList<ExcelRecordLines>();
/*     */     
/* 384 */     while (row != null) {
/* 385 */       ExcelRecordLines registro = new ExcelRecordLines();
/* 386 */       for (int colIndex = 0; colIndex < 5; colIndex++) {
/* 387 */         XSSFCell cell = row.getCell(colIndex);
/* 388 */         String value = "";
/* 389 */         if (cell != null) {
/* 390 */           if (cell.getCellType() == 0) {
/* 391 */             value = Double.toString(cell.getNumericCellValue());
/*     */           } else {
/* 393 */             value = cell.getRichStringCellValue().getString();
/*     */           } 
/*     */         }
/* 396 */         registro.addCampo(value);
/*     */       } 
/* 398 */       excelTab.add(registro);
/* 399 */       rowIndex++;
/* 400 */       row = sheet.getRow(rowIndex);
/*     */     } 
/*     */     
/* 403 */     return excelTab;
/*     */   }
/*     */ 
/*     */   
/*     */   private static String writeFile(ArrayList<ExcelRecordGroup> groups, String path, ArrayList<ExcelRecordLines> loadExcelLines) throws Exception, IOException {
/* 408 */     int liIndexValue = 0;
/* 409 */     String lstCargaManual = "PT";
/*     */     
/* 411 */     System.setProperty("line.separator", "\n");
/*     */ 
/*     */ 
/*     */     
/* 415 */     File dir = new File(path);
/* 416 */     FileUtils.forceMkdir(dir);
/*     */     
/* 418 */     String fileName = (String)((ExcelRecord)((ExcelRecordGroup)groups.get(0)).getRegistros().get(0)).getCampos().get(3);
/*     */     
/* 420 */     File file = new File(dir, fileName);
/*     */     
/* 422 */     FileWriter writer = new FileWriter(file);
/*     */     
/* 424 */     String endLine = System.getProperty("line.separator");
/*     */     
/* 426 */     liIndexValue = CargasCommon.getIndexValue();
/*     */     
/*     */     try {
/* 429 */       for (ExcelRecordGroup grupo : groups) {
/*     */         
/* 431 */         ExcelRecord primerRelgistro = (ExcelRecord)grupo.getRegistros().get(0);
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
/* 444 */         String fecha = (String)primerRelgistro.getCampos().get(0);
/* 445 */         String rfc = (String)primerRelgistro.getCampos().get(1);
/* 446 */         String tipo_layout = (String)primerRelgistro.getCampos().get(2);
/* 447 */         String nombreArchivo = (String)primerRelgistro.getCampos().get(3);
/* 448 */         String tipoDocumento = (String)primerRelgistro.getCampos().get(4);
/* 449 */         String razonSocial = (String)primerRelgistro.getCampos().get(5);
/* 450 */         String calle = (String)primerRelgistro.getCampos().get(6);
/* 451 */         String numero = (String)primerRelgistro.getCampos().get(7);
/* 452 */         String colonia = (String)primerRelgistro.getCampos().get(8);
/* 453 */         String localidad = (String)primerRelgistro.getCampos().get(9);
/* 454 */         String municipio = (String)primerRelgistro.getCampos().get(10);
/* 455 */         String estado = (String)primerRelgistro.getCampos().get(11);
/* 456 */         String pais = (String)primerRelgistro.getCampos().get(12);
/* 457 */         String cod_post = (String)primerRelgistro.getCampos().get(13);
/* 458 */         String numEmpresa = (String)primerRelgistro.getCampos().get(14);
/* 459 */         String serie = (String)primerRelgistro.getCampos().get(15);
/* 460 */         String folio = (String)primerRelgistro.getCampos().get(16);
/* 461 */         String lugarExpedicion = (String)primerRelgistro.getCampos().get(17);
/* 462 */         String formaPago = (String)primerRelgistro.getCampos().get(18);
/* 463 */         String metodoPago = (String)primerRelgistro.getCampos().get(19);
/* 464 */         String fechaVencimiento = (String)primerRelgistro.getCampos().get(20);
/* 465 */         String cuentaPago = (String)primerRelgistro.getCampos().get(21);
/* 466 */         String fechaCompensada = (String)primerRelgistro.getCampos().get(22);
/* 467 */         String clavePago = (String)primerRelgistro.getCampos().get(23);
/* 468 */         String numProveedor = (String)primerRelgistro.getCampos().get(24);
/* 469 */         String rfcReceptor = (String)primerRelgistro.getCampos().get(25);
/* 470 */         String nombreReceptor = (String)primerRelgistro.getCampos().get(26);
/* 471 */         String curpReceptor = (String)primerRelgistro.getCampos().get(27);
/* 472 */         String calleReceptor = (String)primerRelgistro.getCampos().get(28);
/* 473 */         String numExtReceptor = (String)primerRelgistro.getCampos().get(29);
/* 474 */         String coloniaReceptor = (String)primerRelgistro.getCampos().get(30);
/* 475 */         String localidadReceptor = (String)primerRelgistro.getCampos().get(31);
/* 476 */         String municipioReceptor = (String)primerRelgistro.getCampos().get(32);
/* 477 */         String estadoReceptor = (String)primerRelgistro.getCampos().get(33);
/* 478 */         String paisReceptor = (String)primerRelgistro.getCampos().get(34);
/* 479 */         String codPostalReceptor = (String)primerRelgistro.getCampos().get(35);
/*     */ 
/*     */         
/* 482 */         String banco = (String)primerRelgistro.getCampos().get(36);
/* 483 */         String cuenta = (String)primerRelgistro.getCampos().get(37);
/*     */ 
/*     */         
/* 486 */         writer.write("0000|" + fecha + "|" + rfc + "|" + tipo_layout + endLine);
/*     */         
/* 488 */         writer.write("00|" + nombreArchivo + "|" + tipoDocumento + "|" + rfc + "|" + 
/* 489 */             razonSocial + "|" + calle + "|" + numero + "|" + colonia + "|" + localidad + "|" + 
/* 490 */             municipio + "|" + estado + "|" + pais + "|" + cod_post + endLine);
/*     */         
/* 492 */         writer.write("01|" + rfc + "|" + numEmpresa + "|" + nombreArchivo + "|" + 
/* 493 */             serie + "|" + folio + "|" + fecha + "|" + lugarExpedicion + endLine);
/*     */         
/* 495 */         writer.write("02|" + formaPago + "|" + metodoPago + "|" + fechaVencimiento + "|" + 
/* 496 */             cuentaPago + "|" + fechaCompensada + "|" + clavePago + endLine);
/*     */         
/* 498 */         writer.write("03|" + numProveedor + "|" + rfcReceptor + "|" + nombreReceptor + "|" + 
/* 499 */             curpReceptor + "|" + calleReceptor + "|" + numExtReceptor + "|" + coloniaReceptor + "|" + 
/* 500 */             localidadReceptor + "|" + municipioReceptor + "|" + estadoReceptor + "|" + paisReceptor + "|" + 
/* 501 */             codPostalReceptor + endLine);
/*     */         
/* 503 */         for (ExcelRecord registro : grupo) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 512 */           String impuesto = "";
/* 513 */           String montoBanse = "";
/* 514 */           String importe = "";
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 519 */             CargasCommon.insertFileLog(fileName, 
/* 520 */                 serie, 
/* 521 */                 folio, 
/* 522 */                 rfc, 
/* 523 */                 razonSocial, 
/* 524 */                 calle, 
/* 525 */                 numero, 
/* 526 */                 colonia, 
/* 527 */                 localidad, 
/* 528 */                 municipio, 
/* 529 */                 estado, 
/* 530 */                 pais, 
/* 531 */                 cod_post, 
/* 532 */                 tipoDocumento, 
/* 533 */                 serie, 
/* 534 */                 folio, 
/* 535 */                 fecha, 
/* 536 */                 lugarExpedicion, 
/* 537 */                 formaPago, 
/* 538 */                 metodoPago, 
/* 539 */                 fechaVencimiento, 
/* 540 */                 clavePago, 
/* 541 */                 cuentaPago, 
/*     */                 
/* 543 */                 rfcReceptor, 
/* 544 */                 nombreReceptor, 
/* 545 */                 curpReceptor, 
/* 546 */                 calleReceptor, 
/* 547 */                 numExtReceptor, 
/* 548 */                 coloniaReceptor, 
/* 549 */                 localidadReceptor, 
/* 550 */                 municipioReceptor, 
/* 551 */                 estadoReceptor, 
/* 552 */                 paisReceptor, 
/* 553 */                 codPostalReceptor, 
/*     */                 
/* 555 */                 impuesto, 
/* 556 */                 montoBanse, 
/* 557 */                 importe, 
/*     */                 
/* 559 */                 banco, 
/* 560 */                 cuenta, 
/* 561 */                 liIndexValue, 
/* 562 */                 lstCargaManual, 
/* 563 */                 numEmpresa);
/*     */           }
/* 565 */           catch (Exception e) {
/* 566 */             System.out.println(String.valueOf(e.getMessage()) + e.getStackTrace());
/* 567 */             throw new IllegalArgumentException("Error al escribir en la tabla log");
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 572 */         writer.write("05|" + banco + "|" + cuenta + endLine);
/* 573 */         writer.write("99" + endLine);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         try {
/* 584 */           writeLines(loadExcelLines);
/* 585 */         } catch (Exception e) {
/* 586 */           e.getStackTrace();
/*     */         } 
/*     */       } 
/*     */ 
/*     */       
/* 591 */       return fileName;
/*     */     } finally {
/*     */       
/* 594 */       writer.close();
/*     */     } 
/*     */   }
/*     */   
/*     */   private static void writeLines(ArrayList<ExcelRecordLines> loadExcelLines) throws Exception, IOException {
/*     */     try {
/* 600 */       for (ExcelRecordLines rl : loadExcelLines) {
/* 601 */         String stImpuesto = (String)rl.getCampos().get(2);
/* 602 */         String stMontoBase = (String)rl.getCampos().get(3);
/* 603 */         String str = (String)rl.getCampos().get(4);
/*     */       }
/*     */     
/*     */     }
/* 607 */     catch (Exception e) {
/* 608 */       e.getStackTrace();
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
/* 619 */   public static void main(String[] args) throws Exception { doCargaTerceros("/Users/Nava/Layout de Carga Manual CFDI Retenciones V2.xlsx"); }
/*     */ 
/*     */   
/*     */   private static String writeFile(ArrayList<ExcelRecordGroup> groups, String path) throws Exception, IOException {
/* 623 */     int liIndexValue = 0;
/* 624 */     String lstCargaManual = "PT";
/*     */     
/* 626 */     System.setProperty("line.separator", "\n");
/*     */ 
/*     */ 
/*     */     
/* 630 */     File dir = new File(path);
/* 631 */     FileUtils.forceMkdir(dir);
/*     */     
/* 633 */     String fileName = (String)((ExcelRecord)((ExcelRecordGroup)groups.get(0)).getRegistros().get(0)).getCampos().get(3);
/*     */     
/* 635 */     File file = new File(dir, fileName);
/*     */     
/* 637 */     FileWriter writer = new FileWriter(file);
/*     */     
/* 639 */     String endLine = System.getProperty("line.separator");
/*     */     
/* 641 */     liIndexValue = CargasCommon.getIndexValue();
/*     */     
/*     */     try {
/* 644 */       for (ExcelRecordGroup grupo : groups) {
/*     */         
/* 646 */         ExcelRecord primerRelgistro = (ExcelRecord)grupo.getRegistros().get(0);
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
/* 659 */         String fecha = (String)primerRelgistro.getCampos().get(0);
/* 660 */         String rfc = (String)primerRelgistro.getCampos().get(1);
/* 661 */         String tipo_layout = (String)primerRelgistro.getCampos().get(2);
/* 662 */         String nombreArchivo = (String)primerRelgistro.getCampos().get(3);
/* 663 */         String tipoDocumento = (String)primerRelgistro.getCampos().get(4);
/* 664 */         String razonSocial = (String)primerRelgistro.getCampos().get(5);
/* 665 */         String calle = (String)primerRelgistro.getCampos().get(6);
/* 666 */         String numero = (String)primerRelgistro.getCampos().get(7);
/* 667 */         String colonia = (String)primerRelgistro.getCampos().get(8);
/* 668 */         String localidad = (String)primerRelgistro.getCampos().get(9);
/* 669 */         String municipio = (String)primerRelgistro.getCampos().get(10);
/* 670 */         String estado = (String)primerRelgistro.getCampos().get(11);
/* 671 */         String pais = (String)primerRelgistro.getCampos().get(12);
/* 672 */         String cod_post = (String)primerRelgistro.getCampos().get(13);
/* 673 */         String numEmpresa = (String)primerRelgistro.getCampos().get(14);
/* 674 */         String serie = (String)primerRelgistro.getCampos().get(15);
/* 675 */         String folio = (String)primerRelgistro.getCampos().get(16);
/* 676 */         String lugarExpedicion = (String)primerRelgistro.getCampos().get(17);
/* 677 */         String formaPago = (String)primerRelgistro.getCampos().get(18);
/* 678 */         String metodoPago = (String)primerRelgistro.getCampos().get(19);
/* 679 */         String fechaVencimiento = (String)primerRelgistro.getCampos().get(20);
/* 680 */         String cuentaPago = (String)primerRelgistro.getCampos().get(21);
/* 681 */         String fechaCompensada = (String)primerRelgistro.getCampos().get(22);
/* 682 */         String clavePago = (String)primerRelgistro.getCampos().get(23);
/* 683 */         String numProveedor = (String)primerRelgistro.getCampos().get(24);
/* 684 */         String rfcReceptor = (String)primerRelgistro.getCampos().get(25);
/* 685 */         String nombreReceptor = (String)primerRelgistro.getCampos().get(26);
/* 686 */         String curpReceptor = (String)primerRelgistro.getCampos().get(27);
/* 687 */         String calleReceptor = (String)primerRelgistro.getCampos().get(28);
/* 688 */         String numExtReceptor = (String)primerRelgistro.getCampos().get(29);
/* 689 */         String coloniaReceptor = (String)primerRelgistro.getCampos().get(30);
/* 690 */         String localidadReceptor = (String)primerRelgistro.getCampos().get(31);
/* 691 */         String municipioReceptor = (String)primerRelgistro.getCampos().get(32);
/* 692 */         String estadoReceptor = (String)primerRelgistro.getCampos().get(33);
/* 693 */         String paisReceptor = (String)primerRelgistro.getCampos().get(34);
/* 694 */         String codPostalReceptor = (String)primerRelgistro.getCampos().get(35);
/*     */ 
/*     */         
/* 697 */         String banco = (String)primerRelgistro.getCampos().get(36);
/* 698 */         String cuenta = (String)primerRelgistro.getCampos().get(37);
/*     */ 
/*     */         
/* 701 */         writer.write("0000|" + fecha + "|" + rfc + "|" + tipo_layout + endLine);
/*     */         
/* 703 */         writer.write("00|" + nombreArchivo + "|" + tipoDocumento + "|" + rfc + "|" + 
/* 704 */             razonSocial + "|" + calle + "|" + numero + "|" + colonia + "|" + localidad + "|" + 
/* 705 */             municipio + "|" + estado + "|" + pais + "|" + cod_post + endLine);
/*     */         
/* 707 */         writer.write("01|" + rfc + "|" + numEmpresa + "|" + nombreArchivo + "|" + 
/* 708 */             serie + "|" + folio + "|" + fecha + "|" + lugarExpedicion + endLine);
/*     */         
/* 710 */         writer.write("02|" + formaPago + "|" + metodoPago + "|" + fechaVencimiento + "|" + 
/* 711 */             cuentaPago + "|" + fechaCompensada + "|" + clavePago + endLine);
/*     */         
/* 713 */         writer.write("03|" + numProveedor + "|" + rfcReceptor + "|" + nombreReceptor + "|" + 
/* 714 */             curpReceptor + "|" + calleReceptor + "|" + numExtReceptor + "|" + coloniaReceptor + "|" + 
/* 715 */             localidadReceptor + "|" + municipioReceptor + "|" + estadoReceptor + "|" + paisReceptor + "|" + 
/* 716 */             codPostalReceptor + endLine);
/*     */         
/* 718 */         for (ExcelRecord registro : grupo) {
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */           
/* 727 */           String impuesto = "";
/* 728 */           String montoBanse = "";
/* 729 */           String importe = "";
/*     */ 
/*     */ 
/*     */           
/*     */           try {
/* 734 */             CargasCommon.insertFileLog(fileName, 
/* 735 */                 serie, 
/* 736 */                 folio, 
/* 737 */                 rfc, 
/* 738 */                 razonSocial, 
/* 739 */                 calle, 
/* 740 */                 numero, 
/* 741 */                 colonia, 
/* 742 */                 localidad, 
/* 743 */                 municipio, 
/* 744 */                 estado, 
/* 745 */                 pais, 
/* 746 */                 cod_post, 
/* 747 */                 tipoDocumento, 
/* 748 */                 serie, 
/* 749 */                 folio, 
/* 750 */                 fecha, 
/* 751 */                 lugarExpedicion, 
/* 752 */                 formaPago, 
/* 753 */                 metodoPago, 
/* 754 */                 fechaVencimiento, 
/* 755 */                 clavePago, 
/* 756 */                 cuentaPago, 
/*     */                 
/* 758 */                 rfcReceptor, 
/* 759 */                 nombreReceptor, 
/* 760 */                 curpReceptor, 
/* 761 */                 calleReceptor, 
/* 762 */                 numExtReceptor, 
/* 763 */                 coloniaReceptor, 
/* 764 */                 localidadReceptor, 
/* 765 */                 municipioReceptor, 
/* 766 */                 estadoReceptor, 
/* 767 */                 paisReceptor, 
/* 768 */                 codPostalReceptor, 
/*     */                 
/* 770 */                 impuesto, 
/* 771 */                 montoBanse, 
/* 772 */                 importe, 
/*     */                 
/* 774 */                 banco, 
/* 775 */                 cuenta, 
/* 776 */                 liIndexValue, 
/* 777 */                 lstCargaManual, 
/* 778 */                 numEmpresa);
/*     */           }
/* 780 */           catch (Exception e) {
/* 781 */             System.out.println(String.valueOf(e.getMessage()) + e.getStackTrace());
/* 782 */             throw new IllegalArgumentException("Error al escribir en la tabla log");
/*     */           } 
/*     */         } 
/*     */ 
/*     */         
/* 787 */         writer.write("05|" + banco + "|" + cuenta + endLine);
/* 788 */         writer.write("99" + endLine);
/*     */       } 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 798 */       return fileName;
/*     */     } finally {
/*     */       
/* 801 */       writer.close();
/*     */     } 
/*     */   }
/*     */ }

