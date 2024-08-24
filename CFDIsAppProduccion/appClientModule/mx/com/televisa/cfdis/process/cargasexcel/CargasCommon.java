/*      */ package mx.com.televisa.cfdis.process.cargasexcel;
/*      */ 
/*      */ import java.sql.PreparedStatement;
/*      */ import java.sql.ResultSet;
/*      */ import java.sql.SQLException;
/*      */ import java.text.DecimalFormat;
/*      */ import java.util.ArrayList;
/*      */ import mx.com.televisa.cfdis.data.ConnectionWrapper;
/*      */ import mx.com.televisa.cfdis.gui.common.StaticContext;
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
/*      */ 
/*      */ 
/*      */ public class CargasCommon
/*      */ {
/*      */   public static int getIndexValue() throws Exception {
/*   27 */     int newId = 0;
/*   28 */     ConnectionWrapper luConnection = null;
/*      */     try {
/*   30 */       luConnection = new ConnectionWrapper();
/*   31 */       newId = luConnection.getSecuenceNextValue("XXAP.XXAP_CFDIS_RET_FILE_SQ");
/*      */     } finally {
/*   33 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*      */     
/*   36 */     return newId;
/*      */   }
/*      */ 
/*      */ 
/*      */   
/*      */   public static void insertFileLog(String fileName) throws Exception {
/*   42 */     int liResultado = 0;
/*   43 */     String psSql = null;
/*      */     
/*   45 */     ConnectionWrapper luConnection = null;
/*      */     
/*      */     try {
/*   48 */       psSql = "INSERT INTO XXAP.XXAP_CFDIS_RET_FILE_TAB (ID_FILE, FILE_NAME, ID_IS_GENERATED, ID_IS_CONFIRMED, FEC_CREATE_DATE, IND_CREATED_BY, FEC_LAST_UPDATE_DATE, IND_LAST_UPDATED_BY) VALUES (?,?,?,?,sysdate,?,sysdate,?)";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*   56 */       luConnection = new ConnectionWrapper();
/*      */       
/*   58 */       PreparedStatement psStmt = luConnection.prepareStatement(psSql);
/*      */ 
/*      */       
/*   61 */       int newId = luConnection.getSecuenceNextValue("XXAP.XXAP_CFDIS_RET_FILE_SQ");
/*      */       
/*   63 */       psStmt.setObject(1, Integer.valueOf(newId));
/*   64 */       psStmt.setObject(2, fileName);
/*   65 */       psStmt.setObject(3, Integer.valueOf(1));
/*   66 */       psStmt.setObject(4, Integer.valueOf(0));
/*   67 */       psStmt.setObject(5, Integer.valueOf(StaticContext.idUser));
/*   68 */       psStmt.setObject(6, Integer.valueOf(StaticContext.idUser));
/*      */       
/*   70 */       liResultado = psStmt.executeUpdate();
/*      */     } finally {
/*      */       
/*   73 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void insertFileLog(String fileName, String pstSerie, String pstFolio) throws Exception {
/*   82 */     int liResultado = 0;
/*   83 */     String psSql = null;
/*      */     
/*   85 */     ConnectionWrapper luConnection = null;
/*      */     
/*      */     try {
/*   88 */       psSql = "INSERT INTO XXAP.XXAP_CFDIS_RET_FILE_TAB (ID_FILE, FILE_NAME, ID_IS_GENERATED, ID_IS_CONFIRMED, FEC_CREATE_DATE, IND_CREATED_BY, FEC_LAST_UPDATE_DATE, IND_LAST_UPDATED_BY, ATTRIBUTE1,ATTRIBUTE2) VALUES (?,?,?,?,sysdate,?,sysdate,?,?,?)";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*   96 */       luConnection = new ConnectionWrapper();
/*      */       
/*   98 */       PreparedStatement psStmt = luConnection.prepareStatement(psSql);
/*      */ 
/*      */       
/*  101 */       int newId = luConnection.getSecuenceNextValue("XXAP.XXAP_CFDIS_RET_FILE_SQ");
/*      */       
/*  103 */       psStmt.setObject(1, Integer.valueOf(newId));
/*  104 */       psStmt.setObject(2, fileName);
/*  105 */       psStmt.setObject(3, Integer.valueOf(1));
/*  106 */       psStmt.setObject(4, Integer.valueOf(0));
/*  107 */       psStmt.setObject(5, Integer.valueOf(StaticContext.idUser));
/*  108 */       psStmt.setObject(6, Integer.valueOf(StaticContext.idUser));
/*  109 */       psStmt.setObject(7, pstSerie);
/*  110 */       psStmt.setObject(8, pstFolio);
/*      */       
/*  112 */       liResultado = psStmt.executeUpdate();
/*      */     } finally {
/*      */       
/*  115 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*      */   }
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
/*      */ 
/*      */ 
/*      */   
/*      */   public static void insertFileLog(String fileName, String pstSerie, String pstFolio, String rfcEmisora, String razonSocial, String calle, String numero, String colonia, String localidad, String municipio, String estado, String pais, String cod_post, String tipoDocumento, String serie, String folio, String fechaHoraFac, String lugarExpedicion, String formaPago, String metodoPago, String fechaVencimiento, String clavePago, String cuentaPago, String rfcReceptor, String nombreReceptor, String curpReceptor, String calleReceptor, String numExtReceptor, String coloniaReceptor, String localidadReceptor, String municipioReceptor, String estadoReceptor, String paisReceptor, String codPostalReceptor, String impuesto, String montoBanse, String importe, String banco, String cuenta, int tIndexValue, String tCargaManual, String numEmpresa) throws Exception {
/*  170 */     int liResultado = 0;
/*  171 */     String psSql = null;
/*      */     
/*  173 */     ConnectionWrapper luConnection = null;
/*      */     
/*      */     try {
/*  176 */       psSql = "INSERT INTO XXAP.XXAP_CFDIS_RET_FILE_TAB (ID_FILE, FILE_NAME,ID_IS_GENERATED,ID_IS_CONFIRMED,DES_RFC_EMP_EMI,DES_RAZON_SOCIAL,DES_CALLE,NUM_DIRECCION,DES_COLONIA,DES_LOCALIDAD,DES_MUNICIPIO,DES_ESTADO,DES_PAIS,COD_POSTAL,DES_TIPO_DOC,DES_SERIE,DES_FOLIO,FEC_HOR_FAC,DES_LUGAR_EXP,DES_FORMA_PAGO,DES_METO_PAG,FEC_VENCIMIENTO,CVE_PAGO,CTA_PAGO,DES_RFC_RECEPTOR,NOM_RECEPTOR,DES_CURP_RECEPTOR,DES_CALLE_RECEPTOR,NUM_DIREC_RECEPTOR,DES_COLONIA_RECEPTOR,DES_LOCALIDAD_RECEPTOR,DES_MUNICIPIO_RECEPTOR,DES_ESTADO_RECEPTOR,DES_PAIS_RECEPTOR,COD_POSTAL_RECEPTOR,VAL_IMPUESTO,VAL_MONTO_BASE,VAL_IMPORTE,DES_BANCO_PAGADOR,CTA_PAGADORA,FEC_CREATE_DATE, IND_CREATED_BY, FEC_LAST_UPDATE_DATE, IND_LAST_UPDATED_BY, ATTRIBUTE1,ATTRIBUTE2,ATTRIBUTE3,ATTRIBUTE4) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,sysdate,?,sysdate,?,?,?,?,?)";
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
/*      */ 
/*      */       
/*  239 */       luConnection = new ConnectionWrapper();
/*  240 */       PreparedStatement psStmt = luConnection.prepareStatement(psSql);
/*      */ 
/*      */ 
/*      */       
/*  244 */       psStmt.setObject(1, Integer.valueOf(tIndexValue));
/*  245 */       psStmt.setObject(2, fileName);
/*  246 */       psStmt.setObject(3, Integer.valueOf(1));
/*  247 */       psStmt.setObject(4, Integer.valueOf(0));
/*      */       
/*  249 */       psStmt.setObject(5, rfcEmisora);
/*  250 */       psStmt.setObject(6, razonSocial);
/*  251 */       psStmt.setObject(7, calle);
/*  252 */       psStmt.setObject(8, numero);
/*  253 */       psStmt.setObject(9, colonia);
/*  254 */       psStmt.setObject(10, localidad);
/*  255 */       psStmt.setObject(11, municipio);
/*  256 */       psStmt.setObject(12, estado);
/*  257 */       psStmt.setObject(13, pais);
/*  258 */       psStmt.setObject(14, cod_post);
/*      */       
/*  260 */       psStmt.setObject(15, tipoDocumento);
/*  261 */       psStmt.setObject(16, serie);
/*  262 */       psStmt.setObject(17, folio);
/*  263 */       psStmt.setObject(18, fechaHoraFac);
/*  264 */       psStmt.setObject(19, lugarExpedicion);
/*  265 */       psStmt.setObject(20, formaPago);
/*  266 */       psStmt.setObject(21, metodoPago);
/*  267 */       psStmt.setObject(22, fechaVencimiento);
/*  268 */       psStmt.setObject(23, clavePago);
/*  269 */       psStmt.setObject(24, cuentaPago);
/*      */       
/*  271 */       psStmt.setObject(25, rfcReceptor);
/*  272 */       psStmt.setObject(26, nombreReceptor);
/*  273 */       psStmt.setObject(27, curpReceptor);
/*  274 */       psStmt.setObject(28, calleReceptor);
/*  275 */       psStmt.setObject(29, numExtReceptor);
/*  276 */       psStmt.setObject(30, coloniaReceptor);
/*  277 */       psStmt.setObject(31, localidadReceptor);
/*  278 */       psStmt.setObject(32, municipioReceptor);
/*  279 */       psStmt.setObject(33, estadoReceptor);
/*  280 */       psStmt.setObject(34, paisReceptor);
/*  281 */       psStmt.setObject(35, codPostalReceptor);
/*      */       
/*  283 */       psStmt.setObject(36, impuesto);
/*  284 */       psStmt.setObject(37, montoBanse);
/*  285 */       psStmt.setObject(38, importe);
/*      */       
/*  287 */       psStmt.setObject(39, banco);
/*  288 */       psStmt.setObject(40, cuenta);
/*      */       
/*  290 */       psStmt.setObject(41, Integer.valueOf(StaticContext.idUser));
/*  291 */       psStmt.setObject(42, Integer.valueOf(StaticContext.idUser));
/*  292 */       psStmt.setObject(43, pstSerie);
/*  293 */       psStmt.setObject(44, pstFolio);
/*  294 */       psStmt.setObject(45, tCargaManual);
/*  295 */       psStmt.setObject(46, numEmpresa);
/*      */ 
/*      */       
/*  298 */       liResultado = psStmt.executeUpdate();
/*      */     } finally {
/*      */       
/*  301 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int checkFileName(String fileName) throws Exception {
/*  307 */     String psSql = null;
/*  308 */     ConnectionWrapper luConnection = null;
/*  309 */     int linCountFile = 0;
/*      */     try {
/*  311 */       luConnection = new ConnectionWrapper();
/*      */ 
/*      */       
/*  314 */       psSql = "SELECT COUNT(*) CUENTA FROM XXAP.XXAP_CFDIS_RET_FILE_TAB WHERE FILE_NAME = ?";
/*      */       
/*  316 */       PreparedStatement stmt = luConnection.prepareStatement(psSql);
/*  317 */       stmt.setString(1, fileName);
/*  318 */       ResultSet luResulset = stmt.executeQuery();
/*      */       
/*  320 */       if (luResulset.next()) {
/*  321 */         linCountFile = luResulset.getInt("CUENTA");
/*      */       }
/*      */     } finally {
/*      */       
/*  325 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*      */     
/*  328 */     return linCountFile;
/*      */   }
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
/*      */   public static void borrarFacturasAsimilables(int piGetIndexValu) throws Exception {
/*  336 */     int liResultadoCABFAC = 0;
/*  337 */     int liResultadoCNOMINA = 0;
/*  338 */     int liResultadoCNOMINADP = 0;
/*  339 */     String psSqlCABFAC = null;
/*  340 */     String psSqlCNOMINA = null;
/*  341 */     String psSqlCNOMINADP = null;
/*      */     
/*  343 */     ConnectionWrapper luConnection = null;
/*      */     try {
/*  345 */       luConnection = new ConnectionWrapper();
/*      */       
/*  347 */       psSqlCABFAC = "DELETE FROM XXAP.XXAP_CFDI_RET_HA_CABFAC_TAB WHERE ATTRIBUTE1 = ?";
/*  348 */       PreparedStatement psStmtCABFAC = luConnection.prepareStatement(psSqlCABFAC);
/*  349 */       psStmtCABFAC.setObject(1, Integer.valueOf(piGetIndexValu));
/*  350 */       liResultadoCABFAC = psStmtCABFAC.executeUpdate();
/*      */       
/*  352 */       psSqlCNOMINA = "DELETE FROM XXAP.XXAP_CFDI_RET_HA_CNOMINA_TAB WHERE ATTRIBUTE1 = ?";
/*  353 */       PreparedStatement psStmtCNOMINA = luConnection.prepareStatement(psSqlCNOMINA);
/*  354 */       psStmtCNOMINA.setObject(1, Integer.valueOf(piGetIndexValu));
/*  355 */       liResultadoCNOMINA = psStmtCNOMINA.executeUpdate();
/*      */       
/*  357 */       psSqlCNOMINADP = "DELETE FROM XXAP.XXAP_CFDI_RET_HA_CNOMINADP_TAB WHERE ATTRIBUTE1 = ?";
/*  358 */       PreparedStatement psStmtCNOMINADP = luConnection.prepareStatement(psSqlCNOMINADP);
/*  359 */       psStmtCNOMINADP.setObject(1, Integer.valueOf(piGetIndexValu));
/*  360 */       liResultadoCNOMINADP = psStmtCNOMINADP.executeUpdate();
/*      */     } finally {
/*      */       
/*  363 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*      */   }
/*      */ 
/*      */   
/*      */   public static int validarNombreArchivo(String pstNombreArchivo) throws Exception {
/*  369 */     String psSql = null;
/*  370 */     ConnectionWrapper luConnection = null;
/*  371 */     int linCountFile = 0;
/*      */     try {
/*  373 */       luConnection = new ConnectionWrapper();
/*      */ 
/*      */       
/*  376 */       psSql = "SELECT COUNT(*) CUENTA FROM   XXAP.XXAP_CFDIS_RET_HEADERS_TAB WHERE  1=1 AND    FILE_NAME = ?";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  382 */       PreparedStatement stmt = luConnection.prepareStatement(psSql);
/*  383 */       stmt.setString(1, pstNombreArchivo);
/*  384 */       ResultSet luResulset = stmt.executeQuery();
/*      */       
/*  386 */       if (luResulset.next()) {
/*  387 */         linCountFile = luResulset.getInt("CUENTA");
/*      */       }
/*      */     } finally {
/*      */       
/*  391 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*  393 */     return linCountFile;
/*      */   }
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */   
			/*
			 * Kaz - argumel - se agregn tres campos, lugar de exp(cp), regimen fiscal y version
			 */
			public static void insertarCabeceros(String pstNombreArchivo, String pstNoFacturaAP, String pstRFCProvAP, String pstFolioFiscAnt, 
													String pstFolio, String pstFecHorExp, String pstClaveRet, String pstDesRet, String pstRFC_Emp_Emi, 
													String pstRazSocEmi, String pstCURP_Emi, String pstNacRec, String pstRFC_Recep_Nac, 
													String pstRazSocRecNac, String pstCURP_Rec_Nac, String pstRegIdeFisExt, String pstRazSocExt, 
													String pstMesInicial, String pstMesFinal, String pstEjerFisc, String pstMontoTotOpe, 
													String pstTotMonGra, String lstMonTotExe, String pstTotRet, String pstContraInter, 
													String pstGanancia, String pstPerdida, String pstTipoDividendo, String pstRetTerrNac, 
													String pstRetTerrExt, String pstRetTerrExtDiv, String pstTipoDistri, String pstISR_AcrediNac, 
													String pstDivAcumNac, String pstDivAcumExt, String pstRemanente, String pstSisFinan, 
													String pstRetPer, String pstOpeFinanDeriv, String pstCompInteresNo, String pstCompInteresRe, 
													String pstCompPerdida, String pstCompEsBeneCob, String pstCompPaisResid, String pstCompPE_CVE_Ti,
													String pstCompPEDesConc, String pstCompPE_RFC, String pstCompPE_CURP, String pstCompPE_Raz_So, 
													String pstCompPE_IND_Ti, String pstCompPE_DesCon, String pstComp_Ent_Fed, String pstComp_Monto_To, 
													String pstComp_Monto_Gr, String pstComp_Monto_Ex, String lstCom_OD_MonGanAcu, 
													String lstCom_OD_MonPerDed, String pstEmailRecep, String pstIdVendor,
													String lstLugarExpE,String lstRegFiscalE,String lstRegFiscalR,String lstVersion) throws Exception, SQLException {
/*  463 */     int liResultado = 0;
/*  464 */     String psSql = null;
/*  465 */     DecimalFormat dfDosDecimales = new DecimalFormat("#.##");
/*      */     
/*  467 */     ConnectionWrapper luConnection = null;
/*      */     try {
				psSql = "INSERT INTO XXAP.XXAP_CFDIS_RET_HEADERS_TAB(FILE_NAME,NUM_FACTURA_AP,RFC_PROV_AP,FOLIO_FISC_ANT,ID_IS_GENERATED,ID_IS_CONFIRMED,CVE_FOLIO,FEC_EXPEDICION,CVE_RETENCION,DES_RETENCION,CVE_RFC_EMP_EMI,DES_RAZON_SOCIAL_EMI,CVE_CURP_EMISOR,DES_NACIONALIDAD,CVE_RFC_RECEP_NAC,DES_RAZ_SOC_RECEP_NAC,CVE_CURP_RECEP_NAC,CVE_REG_IDE_FIS_EXT,CVE_RAZ_SOC_EXT,CVE_MES_INICIAL,CVE_MES_FINAL,CVE_EJERCICIO_FISCAL,VAL_MONTO_TOT_OPE,VAL_TOT_MON_GRA,VAL_TOT_MON_EXE,VAL_TOT_RETENCIONES,CVE_COMP_CONTRA_INTER,VAL_COMP_GANANCIA,VAL_COMP_ENA_PERDIDA,CVE_COMP_TIPO_DIVIDENDO,VAL_COMP_RET_TERR_NAC,VAL_COMP_RET_TERR_EXT,VAL_COMP_RTE_DIVID_EXT,CVE_COMP_TIPO_DISTRI,VAL_COMP_ISR_ACRED_NAC,VAL_COMP_DIVID_ACUM_NAC,VAL_COMP_DIVID_ACUM_EXT,VAL_COMP_REMANENTE,IND_COMP_SIS_FINANCIERO,IND_COMP_RET_PER,IND_COMP_OPE_FIN_DERI,VAL_COMP_INTE_NOMI,VAL_COMP_INTE_REAL,VAL_COMP_INT_PERDIDA,IND_COMP_ES_BENEFI_COB,IND_COMP_PAIS_RESIDE,IND_COMP_TIPO_CONTRI,DES_COMP_PE_CONCEPTO_SI,CVE_COMP_PE_RFC,CVE_COMP_PE_CURP,DES_COMP_RAZ_SOC_CONTRI,CVE_COMP_TIPO_CONTRI,DES_COMP_PE_CONCEPTO_NO,CVE_COMP_ENTIDAD_FEDE,VAL_COMP_MONTO_TOTAL_PAG,VAL_COMP_MONTO_GRAVADO,VAL_COMP_MONTO_EXCENTO,VAL_COMP_OD_MON_GAN_ACU,VAL_COMP_OD_MON_PER_DED,DES_EMAIL_RECEP,ID_VENDOR,CVE_LUG_EXP_CP,CVE_REG_FISCAL,COD_VERSION,CVE_DOM_FIS_R,FEC_CREATE_DATE)VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, SYSDATE)";
   
/*  604 */       luConnection = new ConnectionWrapper();
/*  605 */       PreparedStatement psStmt = luConnection.prepareStatement(psSql);
/*      */       
/*  607 */       psStmt.setObject(1, pstNombreArchivo);
/*      */       
/*  609 */       psStmt.setObject(2, pstNoFacturaAP);
/*  610 */       psStmt.setObject(3, pstRFCProvAP);
/*  611 */       psStmt.setObject(4, pstFolioFiscAnt);
/*      */       
/*  613 */       psStmt.setObject(5, Integer.valueOf(1));
/*  614 */       psStmt.setObject(6, Integer.valueOf(0));
/*  615 */       psStmt.setObject(7, pstFolio);
/*  616 */       psStmt.setObject(8, pstFecHorExp);
/*  617 */       psStmt.setObject(9, pstClaveRet);
/*  618 */       psStmt.setObject(10, pstDesRet);
/*  619 */       psStmt.setObject(11, pstRFC_Emp_Emi);
/*  620 */       psStmt.setObject(12, pstRazSocEmi);
/*  621 */       psStmt.setObject(13, pstCURP_Emi);
/*  622 */       psStmt.setObject(14, pstNacRec);
/*  623 */       psStmt.setObject(15, pstRFC_Recep_Nac);
/*  624 */       psStmt.setObject(16, pstRazSocRecNac);
/*  625 */       psStmt.setObject(17, pstCURP_Rec_Nac);
/*  626 */       psStmt.setObject(18, pstRegIdeFisExt);
/*  627 */       psStmt.setObject(19, pstRazSocExt);
/*  628 */       psStmt.setObject(20, pstMesInicial);
/*  629 */       psStmt.setObject(21, pstMesFinal);
/*  630 */       psStmt.setObject(22, pstEjerFisc);
/*      */ 
/*      */       
/*  633 */       if (pstMontoTotOpe.equals(""))
/*  634 */         pstMontoTotOpe = "0.0"; 
/*  635 */       pstMontoTotOpe = dfDosDecimales.format(Double.parseDouble(pstMontoTotOpe));
/*  636 */       psStmt.setObject(23, Double.valueOf(Double.parseDouble(pstMontoTotOpe)));
/*      */       
/*  638 */       if (pstTotMonGra.equals(""))
/*  639 */         pstTotMonGra = "0.0"; 
/*  640 */       pstTotMonGra = dfDosDecimales.format(Double.parseDouble(pstTotMonGra));
/*  641 */       psStmt.setObject(24, Double.valueOf(Double.parseDouble(pstTotMonGra)));
/*      */       
/*  643 */       if (lstMonTotExe.equals(""))
/*  644 */         lstMonTotExe = "0.0"; 
/*  645 */       lstMonTotExe = dfDosDecimales.format(Double.parseDouble(lstMonTotExe));
/*  646 */       psStmt.setObject(25, Double.valueOf(Double.parseDouble(lstMonTotExe)));
/*      */       
/*  648 */       if (pstTotRet.equals(""))
/*  649 */         pstTotRet = "0.0"; 
/*  650 */       pstTotRet = dfDosDecimales.format(Double.parseDouble(pstTotRet));
/*  651 */       psStmt.setObject(26, Double.valueOf(Double.parseDouble(pstTotRet)));
/*      */       
/*  653 */       psStmt.setObject(27, pstContraInter);
/*      */       
/*  655 */       if (pstGanancia.equals("") || pstGanancia.equals(" ")){
/*  656 */         pstGanancia = "0.0"; 
					}
/*  657 */       pstGanancia = dfDosDecimales.format(Double.parseDouble(pstGanancia));
/*  658 */       psStmt.setObject(28, Double.valueOf(Double.parseDouble(pstGanancia)));
/*      */       
/*  660 */       if (pstPerdida.equals("") || pstPerdida.equals(" ")) {
/*  661 */         pstPerdida = "0.0"; 
					}
/*  662 */       pstPerdida = dfDosDecimales.format(Double.parseDouble(pstPerdida));
/*  663 */       psStmt.setObject(29, Double.valueOf(Double.parseDouble(pstPerdida)));
/*      */       
/*  665 */       psStmt.setObject(30, pstTipoDividendo);
/*      */       
/*  667 */       if (pstRetTerrNac.equals("") || pstRetTerrNac.equals(" ")) {
/*  668 */         pstRetTerrNac = "0.0"; 
					}
/*  669 */       pstRetTerrNac = dfDosDecimales.format(Double.parseDouble(pstRetTerrNac));
/*  670 */       psStmt.setObject(31, Double.valueOf(Double.parseDouble(pstRetTerrNac)));
/*      */       
/*  672 */       if (pstRetTerrExt.equals("") || pstRetTerrExt.equals(" ")) {
/*  673 */         pstRetTerrExt = "0.0"; 
					}
/*  674 */       pstRetTerrExt = dfDosDecimales.format(Double.parseDouble(pstRetTerrExt));
/*  675 */       psStmt.setObject(32, Double.valueOf(Double.parseDouble(pstRetTerrExt)));
/*      */       
/*  677 */       if (pstRetTerrExtDiv.equals("") || pstRetTerrExtDiv.equals(" ")) {
/*  678 */         pstRetTerrExtDiv = "0.0"; 
					}
/*  679 */       pstRetTerrExtDiv = dfDosDecimales.format(Double.parseDouble(pstRetTerrExtDiv));
/*  680 */       psStmt.setObject(33, Double.valueOf(Double.parseDouble(pstRetTerrExtDiv)));
/*      */       
/*  682 */       psStmt.setObject(34, pstTipoDistri);
/*      */       
/*  684 */       if (pstISR_AcrediNac.equals("") || pstISR_AcrediNac.equals(" ")) {
/*  685 */         pstISR_AcrediNac = "0.0"; 
					}
/*  686 */       pstISR_AcrediNac = dfDosDecimales.format(Double.parseDouble(pstISR_AcrediNac));
/*  687 */       psStmt.setObject(35, Double.valueOf(Double.parseDouble(pstISR_AcrediNac)));
/*      */       
/*  689 */       if (pstDivAcumNac.equals("") || pstDivAcumNac.equals(" ")) {
/*  690 */         pstDivAcumNac = "0.0"; 
					}
/*  691 */       pstDivAcumNac = dfDosDecimales.format(Double.parseDouble(pstDivAcumNac));
/*  692 */       psStmt.setObject(36, Double.valueOf(Double.parseDouble(pstDivAcumNac)));
/*      */       
/*  694 */       if (pstDivAcumExt.equals("") || pstDivAcumExt.equals(" ")) {
/*  695 */         pstDivAcumExt = "0.0"; 
					}
/*  696 */       pstDivAcumExt = dfDosDecimales.format(Double.parseDouble(pstDivAcumExt));
/*  697 */       psStmt.setObject(37, Double.valueOf(Double.parseDouble(pstDivAcumExt)));
/*      */       
/*  699 */       if (pstRemanente.equals("") || pstRemanente.equals(" ")) {
/*  700 */         pstRemanente = "0.0"; 
				}
/*  701 */       pstRemanente = dfDosDecimales.format(Double.parseDouble(pstRemanente));
/*  702 */       psStmt.setObject(38, Double.valueOf(Double.parseDouble(pstRemanente)));
/*      */       
/*  704 */       psStmt.setObject(39, pstSisFinan);
/*  705 */       psStmt.setObject(40, pstRetPer);
/*  706 */       psStmt.setObject(41, pstOpeFinanDeriv);
/*      */       
/*  708 */       if (pstCompInteresNo.equals("") || pstCompInteresNo.equals(" ")) {
/*  709 */         pstCompInteresNo = "0.0"; 
					}
/*  710 */       pstCompInteresNo = dfDosDecimales.format(Double.parseDouble(pstCompInteresNo));
/*  711 */       psStmt.setObject(42, Double.valueOf(Double.parseDouble(pstCompInteresNo)));
/*      */       
/*  713 */       if (pstCompInteresRe.equals("") || pstCompInteresRe.equals(" ")) {
/*  714 */         pstCompInteresRe = "0.0"; 
					}
/*  715 */       pstCompInteresRe = dfDosDecimales.format(Double.parseDouble(pstCompInteresRe));
/*  716 */       psStmt.setObject(43, Double.valueOf(Double.parseDouble(pstCompInteresRe)));
/*      */       
/*  718 */       if (pstCompPerdida.equals("") || pstCompPerdida.equals(" ")) {
/*  719 */         pstCompPerdida = "0.0"; 
					}
/*  720 */       pstCompPerdida = dfDosDecimales.format(Double.parseDouble(pstCompPerdida));
/*  721 */       psStmt.setObject(44, Double.valueOf(Double.parseDouble(pstCompPerdida)));
/*      */       
/*  723 */       psStmt.setObject(45, pstCompEsBeneCob);
/*  724 */       psStmt.setObject(46, pstCompPaisResid);
/*  725 */       psStmt.setObject(47, pstCompPE_CVE_Ti);
/*  726 */       psStmt.setObject(48, pstCompPEDesConc);
/*  727 */       psStmt.setObject(49, pstCompPE_RFC);
/*  728 */       psStmt.setObject(50, pstCompPE_CURP);
/*  729 */       psStmt.setObject(51, pstCompPE_Raz_So);
/*  730 */       psStmt.setObject(52, pstCompPE_IND_Ti);
/*  731 */       psStmt.setObject(53, pstCompPE_DesCon);
/*  732 */       psStmt.setObject(54, pstComp_Ent_Fed);
/*      */       
/*  734 */       if (pstComp_Monto_To.equals("") || pstComp_Monto_To.equals(" ")) {
/*  735 */         pstComp_Monto_To = "0.0"; 
				}
/*  736 */       pstComp_Monto_To = dfDosDecimales.format(Double.parseDouble(pstComp_Monto_To));
/*  737 */       psStmt.setObject(55, Double.valueOf(Double.parseDouble(pstComp_Monto_To)));
/*      */       
/*  739 */       if (pstComp_Monto_Gr.equals("") || pstComp_Monto_Gr.equals(" ")) {
/*  740 */         pstComp_Monto_Gr = "0.0"; 
					}
/*  741 */       pstComp_Monto_Gr = dfDosDecimales.format(Double.parseDouble(pstComp_Monto_Gr));
/*  742 */       psStmt.setObject(56, Double.valueOf(Double.parseDouble(pstComp_Monto_Gr)));
/*      */       
/*  744 */       if (pstComp_Monto_Ex.equals("") || pstComp_Monto_Ex.equals(" ")) {
/*  745 */         pstComp_Monto_Ex = "0.0"; 
				}
/*  746 */       pstComp_Monto_Ex = dfDosDecimales.format(Double.parseDouble(pstComp_Monto_Ex));
/*  747 */       psStmt.setObject(57, Double.valueOf(Double.parseDouble(pstComp_Monto_Ex)));
/*      */ 
/*      */       
/*  750 */       if (lstCom_OD_MonGanAcu.equals("") || lstCom_OD_MonGanAcu.equals(" ")) {
/*  751 */         lstCom_OD_MonGanAcu = "0.0"; 
					}
/*  752 */       lstCom_OD_MonGanAcu = dfDosDecimales.format(Double.parseDouble(lstCom_OD_MonGanAcu));
/*  753 */       psStmt.setObject(58, Double.valueOf(Double.parseDouble(lstCom_OD_MonGanAcu)));
/*      */       
/*  755 */       if (lstCom_OD_MonPerDed.equals("") || lstCom_OD_MonPerDed.equals(" ")) {
/*  756 */         lstCom_OD_MonPerDed = "0.0"; 
					}
/*  757 */       lstCom_OD_MonPerDed = dfDosDecimales.format(Double.parseDouble(lstCom_OD_MonPerDed));
/*  758 */       psStmt.setObject(59, Double.valueOf(Double.parseDouble(lstCom_OD_MonPerDed)));
/*      */ 
/*      */       
/*  761 */       psStmt.setObject(60, pstEmailRecep);
/*      */ 
/*      */       
/*      */       try {
/*  765 */         int liIdVendir = Integer.parseInt(pstIdVendor.substring(0, pstIdVendor.indexOf(".")));
/*  766 */         psStmt.setObject(61, Integer.valueOf(liIdVendir));
/*  767 */       } catch (Exception e) {
/*  768 */         throw new IllegalArgumentException("Error al convertir el IdVendor.");
/*      */       } 

				//Kaz argumel 01-06-2022 inserta en cabecero tres columnas mas
				psStmt.setString(62, lstLugarExpE);
				psStmt.setString(63, lstRegFiscalE);
				psStmt.setString(64, lstVersion);
				psStmt.setString(65, lstRegFiscalR);
/*      */       
/*  775 */       liResultado = psStmt.executeUpdate();
/*      */     } finally {
/*      */       
/*  778 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*      */   }

			
			public static void insertarCfdiRelacionados(String lstFolioLinea, String lstTipoRelacion,String lstUUID)throws Exception {
				
				ConnectionWrapper 	luConnection 	= null;
				String 				psSql 			= null;
			   try {
				     psSql = "INSERT INTO XXAP.XXAP_CFDIS_RET_REL_TAB(ID_FOLIO,CVE_TIPO_REL,CVE_UUID,FEC_CREATE_DATE)VALUES (?,?,?,SYSDATE)";
			     
				     luConnection = new ConnectionWrapper();
				     PreparedStatement psStmt = luConnection.prepareStatement(psSql);
				     psStmt.setString(1, lstFolioLinea);
				     psStmt.setString(2, lstTipoRelacion);
				     psStmt.setString(3, lstUUID);
				     psStmt.executeUpdate();
				     
			   }finally {
				    	       ConnectionWrapper.closeAll(new Object[] { luConnection });
				     } 
				
			}
			
			
			
			
			
/*      */   public static void insertarLineas(String pstIdFolio, String pstBaseImp, String pstCveTipoImp, String pstValMontoRete, String pstTipoPagoRete) throws Exception {
/*  788 */     int liResultado = 0;
/*  789 */     String psSql = null;
/*  790 */     DecimalFormat dfDosDecimales = new DecimalFormat("#.##");
/*      */     
/*  792 */     ConnectionWrapper luConnection = null;
/*      */     
/*      */     try {
/*  795 */       psSql = "INSERT INTO XXAP.XXAP_CFDIS_RET_LINES_TAX_TAB(ID_FOLIO,VAL_BASE_IMP,CVE_TIPO_IMP,VAL_MONTO_RETE,DES_TIPO_PAGO_RETE,FEC_CREATE_DATE)VALUES (?,?,?,?,?,SYSDATE)";
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
/*      */ 
/*      */ 
/*      */       
/*  811 */       luConnection = new ConnectionWrapper();
/*  812 */       PreparedStatement psStmt = luConnection.prepareStatement(psSql);
/*      */       
/*  814 */       psStmt.setObject(1, pstIdFolio);
/*      */       
/*  816 */       if (pstBaseImp.equals(""))
/*  817 */         pstBaseImp = "0.0"; 
/*  818 */       pstBaseImp = dfDosDecimales.format(Double.parseDouble(pstBaseImp));
/*  819 */       psStmt.setObject(2, Double.valueOf(Double.parseDouble(pstBaseImp)));
/*      */       
/*  821 */       psStmt.setObject(3, pstCveTipoImp);
/*      */       
/*  823 */       if (pstValMontoRete.equals(""))
/*  824 */         pstValMontoRete = "0.0"; 
/*  825 */       pstValMontoRete = dfDosDecimales.format(Double.parseDouble(pstValMontoRete));
/*  826 */       psStmt.setObject(4, Double.valueOf(Double.parseDouble(pstValMontoRete)));
/*      */       
/*  828 */       psStmt.setObject(5, pstTipoPagoRete);
/*      */       
/*  830 */       liResultado = psStmt.executeUpdate();
/*      */     } finally {
/*      */       
/*  833 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*      */   }
/*      */   
/*      */   public static boolean validarFolioUnicoEnTablaCC(String pstFolio) throws Exception {
	boolean lbebs = false;
/*  838 */     ConnectionWrapper luCW = null;
/*  839 */     String lstSQL = null;
/*  840 */     String lstFolio = null;
/*  841 */     String lstError = null;
/*      */     try {
/*  843 */       lstSQL = " SELECT    H.CVE_FOLIO FROM      XXAP.XXAP_CFDIS_RET_HEADERS_TAB H WHERE     1=1 AND       H.CVE_FOLIO = ?";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/*  849 */       luCW = new ConnectionWrapper();
/*  850 */       PreparedStatement psStmt = luCW.prepareStatement(lstSQL);
/*  851 */       psStmt.setObject(1, pstFolio);
/*  852 */       ResultSet luResulset = psStmt.executeQuery();
/*      */       
/*  854 */       if (luResulset.next()) {
/*  855 */         lstFolio = luResulset.getString("CVE_FOLIO");
/*  856 */         if (lstFolio != null) {
/*  857 */           lstError = "El Folio " + lstFolio + " ya existe en la Base de Datos.";
					
/*  858 */           throw new IllegalArgumentException(lstError);
/*      */         }else {
								lbebs = true;
						} 
/*      */       } 
/*      */     } finally {
/*      */       
/*  863 */       ConnectionWrapper.closeAll(new Object[] { luCW });
/*      */     }
			return lbebs;
/*      */   }
/*      */   
/*      */   public static void validar_RFCE_Serie_Folio_En_Tabla(ArrayList<RecordCABFAC> palCABFAC) throws SQLException, Exception {
/*  868 */     String lstRFC_E = "";
/*  869 */     String lstSerie = "";
/*  870 */     String lstFolio = "";
/*  871 */     String lstsql = "SELECT    COUNT(*) AS NUM\nFROM      XXAP.XXAP_CFDI_RET_HA_CABFAC_TAB\nWHERE     1=1\nAND       DES_RFC_E = ?\nAND       DES_SERIE = ?\nAND       DES_FOLIO = ?\n";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */     
/*  879 */     ConnectionWrapper luCW = null;
/*  880 */     ResultSet luResulset = null;
/*  881 */     String lstNumReg = "";
/*  882 */     int linNumReg = 0;
/*      */     
/*  884 */     for (RecordCABFAC rCABFAC : palCABFAC) {
/*  885 */       lstRFC_E = (String)rCABFAC.getCampos().get(15);
/*  886 */       lstSerie = (String)rCABFAC.getCampos().get(4);
/*  887 */       lstFolio = (String)rCABFAC.getCampos().get(5);
/*      */       try {
/*  889 */         luCW = new ConnectionWrapper();
/*  890 */         PreparedStatement psStmt = luCW.prepareStatement(lstsql);
/*      */         
/*  892 */         psStmt.setObject(1, lstRFC_E);
/*  893 */         psStmt.setObject(2, lstSerie);
/*  894 */         psStmt.setObject(3, lstFolio);
/*      */         
/*  896 */         luResulset = psStmt.executeQuery();
/*      */         
/*  898 */         if (luResulset.next()) {
/*  899 */           lstNumReg = luResulset.getString("NUM");
/*      */           
/*  901 */           linNumReg = Integer.parseInt(lstNumReg);
/*  902 */           System.out.println("Num Reg Num:  " + linNumReg);
/*      */           
/*  904 */           if (linNumReg > 0) {
/*  905 */             throw new IllegalArgumentException("Ya existe una llave creada en la tabla con los campos:\nRFC_E: \t" + 
/*  906 */                 lstRFC_E + "\n" + 
/*  907 */                 "Serie: \t" + lstSerie + "\n" + 
/*  908 */                 "Folio: \t" + lstFolio);
/*      */           }
/*      */         }
/*      */       
/*      */       } finally {
/*      */         
/*  914 */         ConnectionWrapper.closeAll(new Object[] { luCW });
						
/*      */       } 
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void cargarObjetoEnBaseDatosCABFAC(ArrayList<RecordCABFAC> palCABFAC, int piGetIndexValu,String version) throws SQLException, Exception {
/*  920 */     String psSql = null;
/*  921 */     DecimalFormat dfDosDecimales = new DecimalFormat("#.##");
/*  922 */     ConnectionWrapper luConnection = null;
/*  923 */     luConnection = new ConnectionWrapper();
/*  924 */     int liResultado = 0;
/*  925 */     String pstIdLabel = "";
/*  926 */     int liIdLabel = 0;
/*  927 */     String psSqlX = null;
/*  928 */     ResultSet luResulset = null;
/*      */     
/*      */     try {
/*  931 */       psSqlX = "Alter Session Set NLS_Language='AMERICAN'";
/*      */ 
					//KAZ 02-06-2022 SE AGREGAN 3 ATRIBUTOS - VERSION, CP Y REGIMEN FISCAL
/*  934 */       psSql = "INSERT INTO XXAP.XXAP_CFDI_RET_HA_CABFAC_TAB(ID_LABEL,NUM_FACTURA_AP,RFC_PROV_AP,FOLIO_FISC_ANT,DES_SERIE,DES_FOLIO,DES_FECHAC,DES_FPAGO,NUM_SUBTOTAL,NUM_DESCUENTO,NUM_TOTALFAC,DES_METODOPAGO,DES_DESCRIPCION,NUM_PRECIOUNIT,DES_RFC_E,DES_NOMBRE_E,DES_CALLE_E,DES_NEXT_E,DES_NINT_E,DES_COLONIA_E,DES_LOCAL_E,DES_REF_E,DES_MUNICIP_E,DES_ESTADO_E,DES_PAIS_E,DES_CP_E,DES_REGIMEN,DES_LUGAREXPEDICION,DES_CALLE_X,DES_NEXT_X,DES_NINT_X,DES_COLONIA_X,DES_LOCAL_X,DES_REF_X,DES_MUNICIP_X,DES_ESTADO_X,DES_PAIS_X,DES_CP_X,DES_RFC_R,DES_NOMBRE_R,DES_CALLE_R,DES_NEXT_R,DES_NINT_R,DES_COLONIA_R,DES_LOCAL_R,DES_REF_R,DES_MUNIC_R,DES_ESTADO_R,DES_PAIS_R,DES_CP_R,DES_EMAIL,IS_GENERATED,FEC_CREATE_DATE,ATTRIBUTE1,COD_USOCFDI,CVE_CONFIRMACION,COD_TIPORELACION,COD_UUID,DES_CONCEPTO,COD_CLAVEPRODSERV,VAL_IMPORTE,COD_VERSION,COD_CP_R,REGIMENFISCAL_R)VALUES( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, SYSDATE,?,?,?,?,?,?,?,?,?,?,?)";
      
/* 1069 */       for (RecordCABFAC r : palCABFAC)
/*      */       {
/* 1071 */         String imprime = "Antes de Conexion";
/*      */ 
/*      */         
/* 1074 */         PreparedStatement psStmt = luConnection.prepareStatement(psSqlX);
/* 1075 */         luResulset = psStmt.executeQuery();
/* 1076 */         imprime = "Despues de Conexion";
/*      */ 
/*      */         
/* 1079 */         psStmt = luConnection.prepareStatement(psSql);
/*      */ 
/*      */         
/* 1082 */         imprime = "Query" + psSql;
/*      */ 
/*      */         
/*      */         try {
/* 1086 */           pstIdLabel = (String)r.getCampos().get(0);
/*      */           
/* 1088 */           System.out.println("DATO_01:" + (String)r.getCampos().get(0));
/* 1089 */           liIdLabel = Integer.parseInt(pstIdLabel);
/* 1090 */           psStmt.setObject(1, Integer.valueOf(liIdLabel));
/*      */         }
/* 1092 */         catch (Exception e) {
/* 1093 */           throw new IllegalArgumentException("Error al convertir el IdLabel.\nCargasCommon: 936.\n");
/*      */         } 
/*      */ 
/*      */         
/* 1097 */         psStmt.setObject(2, r.getCampos().get(1));
/* 1098 */         System.out.println("DATO_02:" + (String)r.getCampos().get(1));
/*      */         
/* 1100 */         psStmt.setObject(3, r.getCampos().get(2));
/*      */         
/* 1102 */         System.out.println("DATO_03:" + (String)r.getCampos().get(2));
/* 1103 */         psStmt.setObject(4, r.getCampos().get(3));
/*      */         
/* 1105 */         System.out.println("DATO_04:" + (String)r.getCampos().get(3));
/*      */ 
/*      */         
/* 1108 */         psStmt.setObject(5, r.getCampos().get(4));
/* 1109 */         System.out.println("DATO_05:" + (String)r.getCampos().get(4));
/*      */         
/* 1111 */         psStmt.setObject(6, r.getCampos().get(5));
/* 1112 */         System.out.println("DATO_06:" + (String)r.getCampos().get(5));
/*      */         
/* 1114 */         psStmt.setObject(7, r.getCampos().get(6));
/* 1115 */         System.out.println("DATO_07:" + (String)r.getCampos().get(6));
/*      */         
/* 1117 */         psStmt.setObject(8, r.getCampos().get(7));
/* 1118 */         System.out.println("DATO_08:" + (String)r.getCampos().get(7));
/*      */ 
/*      */         
/*      */         try {
/* 1122 */           String lstNumSubtotal = (String)r.getCampos().get(8);
/*      */           
/* 1124 */           lstNumSubtotal = dfDosDecimales.format(Double.parseDouble(lstNumSubtotal));
/* 1125 */           psStmt.setObject(9, Double.valueOf(Double.parseDouble(lstNumSubtotal)));
/*      */           
/* 1127 */           System.out.println("DATO_09:" + Double.parseDouble(lstNumSubtotal));
/* 1128 */         } catch (Exception e) {
/* 1129 */           psStmt.setObject(9, Integer.valueOf(0));
/*      */         } 
/*      */         
/* 1132 */         imprime = "Antes a NUM_DESCUENTO";
/*      */         
/*      */         try {
/* 1135 */           String lstNumDescuento = (String)r.getCampos().get(9);
/*      */           
/* 1137 */           lstNumDescuento = dfDosDecimales.format(Double.parseDouble(lstNumDescuento));
/* 1138 */           psStmt.setObject(10, Double.valueOf(Double.parseDouble(lstNumDescuento)));
/*      */           
/* 1140 */           System.out.println("DATO_10:" + Double.parseDouble(lstNumDescuento));
/* 1141 */         } catch (Exception e) {
/* 1142 */           psStmt.setObject(10, Integer.valueOf(0));
/*      */         } 
/* 1144 */         imprime = "Despues a NUM_DESCUENTO";
/*      */ 
/*      */         
/* 1147 */         imprime = "Antes a NUM_TOTALFAC";
/*      */         
/*      */         try {
/* 1150 */           String lstNumTotalFac = (String)r.getCampos().get(10);
/*      */           
/* 1152 */           lstNumTotalFac = dfDosDecimales.format(Double.parseDouble(lstNumTotalFac));
/* 1153 */           psStmt.setObject(11, Double.valueOf(Double.parseDouble(lstNumTotalFac)));
/*      */           
/* 1155 */           System.out.println("DATO_11:" + Double.parseDouble(lstNumTotalFac));
/* 1156 */         } catch (Exception e) {
/* 1157 */           psStmt.setObject(11, Integer.valueOf(0));
/*      */         } 
/* 1159 */         imprime = "Despues a NUM_TOTALFAC";
/*      */ 
/*      */         
/* 1162 */         psStmt.setObject(12, r.getCampos().get(11));
/*      */         
/* 1164 */         System.out.println("DATO_12:" + (String)r.getCampos().get(11));
/* 1165 */         psStmt.setObject(13, r.getCampos().get(12));
/*      */         
/* 1167 */         System.out.println("DATO_13:" + (String)r.getCampos().get(12));
/*      */         
/* 1169 */         imprime = "Antes a NUM_PRECIOUNIT";
/*      */ 
/*      */         
/*      */         try {
/* 1173 */           String lstNumPrecioUnit = (String)r.getCampos().get(13);
/*      */           
/* 1175 */           lstNumPrecioUnit = dfDosDecimales.format(Double.parseDouble(lstNumPrecioUnit));
/* 1176 */           psStmt.setObject(14, Double.valueOf(Double.parseDouble(lstNumPrecioUnit)));
/*      */           
/* 1178 */           System.out.println("DATO_14:" + Double.parseDouble(lstNumPrecioUnit));
/* 1179 */         } catch (Exception e) {
/* 1180 */           psStmt.setObject(14, Integer.valueOf(0));
/*      */         } 
/*      */         
/* 1183 */         imprime = "Despues  NUM_PRECIOUNIT";
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */         
/* 1189 */         psStmt.setObject(15, r.getCampos().get(15));
/*      */         
/* 1191 */         System.out.println("DATO_15:" + (String)r.getCampos().get(15));
/* 1192 */         psStmt.setObject(16, r.getCampos().get(16));
/*      */         
/* 1194 */         System.out.println("DATO_16:" + (String)r.getCampos().get(16));
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
/* 1206 */         psStmt.setObject(17, "");
/* 1207 */         System.out.println("DATO_17:''");
/* 1208 */         psStmt.setObject(18, "");
/* 1209 */         System.out.println("DATO_18:''");
/* 1210 */         psStmt.setObject(19, "");
/* 1211 */         System.out.println("DATO_19:''");
/* 1212 */         psStmt.setObject(20, "");
/* 1213 */         System.out.println("DATO_20:''");
/* 1214 */         psStmt.setObject(21, "");
/* 1215 */         System.out.println("DATO_21:''");
/* 1216 */         psStmt.setObject(22, "");
/* 1217 */         System.out.println("DATO_22:''");
/* 1218 */         psStmt.setObject(23, "");
/* 1219 */         System.out.println("DATO_23:''");
/* 1220 */         psStmt.setObject(24, "");
/* 1221 */         System.out.println("DATO_24:''");
/* 1222 */         psStmt.setObject(25, "");
/* 1223 */         System.out.println("DATO_25:''");
/* 1224 */         psStmt.setObject(26, "");
/* 1225 */         System.out.println("DATO_26:''");
/*      */         
/* 1227 */         psStmt.setObject(27, r.getCampos().get(17));
/*      */         
/* 1229 */         System.out.println("DATO_26:" + (String)r.getCampos().get(17));
/* 1230 */         psStmt.setObject(28, r.getCampos().get(18));
/*      */         
/* 1232 */         System.out.println("DATO_26:" + (String)r.getCampos().get(18));
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
/* 1245 */         psStmt.setObject(29, "");
/* 1246 */         System.out.println("DATO_29:''");
/* 1247 */         psStmt.setObject(30, "");
/* 1248 */         System.out.println("DATO_30:''");
/* 1249 */         psStmt.setObject(31, "");
/* 1250 */         System.out.println("DATO_31:''");
/* 1251 */         psStmt.setObject(32, "");
/* 1252 */         System.out.println("DATO_32:''");
/* 1253 */         psStmt.setObject(33, "");
/* 1254 */         System.out.println("DATO_33:''");
/* 1255 */         psStmt.setObject(34, "");
/* 1256 */         System.out.println("DATO_34:''");
/* 1257 */         psStmt.setObject(35, "");
/* 1258 */         System.out.println("DATO_35:''");
/* 1259 */         psStmt.setObject(36, "");
/* 1260 */         System.out.println("DATO_36:''");
/* 1261 */         psStmt.setObject(37, "");
/* 1262 */         System.out.println("DATO_37:''");
/* 1263 */         psStmt.setObject(38, "");
/* 1264 */         System.out.println("DATO_38:''");
/*      */         
/* 1266 */         psStmt.setObject(39, r.getCampos().get(19));
/*      */         
/* 1268 */         System.out.println("DATO_39:" + (String)r.getCampos().get(19));
/* 1269 */         psStmt.setObject(40, r.getCampos().get(20));
/*      */         
/* 1271 */         System.out.println("DATO_40:" + (String)r.getCampos().get(20));
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
/*      */         
/* 1285 */         psStmt.setObject(41, "");
/* 1286 */         System.out.println("DATO_41:''");
/* 1287 */         psStmt.setObject(42, "");
/* 1288 */         System.out.println("DATO_42:''");
/* 1289 */         psStmt.setObject(43, "");
/* 1290 */         System.out.println("DATO_43:''");
/* 1291 */         psStmt.setObject(44, "");
/* 1292 */         System.out.println("DATO_44:''");
/* 1293 */         psStmt.setObject(45, "");
/* 1294 */         System.out.println("DATO_45:''");
/* 1295 */         psStmt.setObject(46, "");
/* 1296 */         System.out.println("DATO_46:''");
/* 1297 */         psStmt.setObject(47, "");
/* 1298 */         System.out.println("DATO_47:''");
/* 1299 */         psStmt.setObject(48, "");
/* 1300 */         System.out.println("DATO_48:''");
/* 1301 */         psStmt.setObject(49, "");
/* 1302 */         System.out.println("DATO_49:''");
/* 1303 */         psStmt.setObject(50, "");
/* 1304 */         System.out.println("DATO_50:''");
/*      */         
/* 1306 */         psStmt.setObject(51, r.getCampos().get(21));
/*      */         
/* 1308 */         System.out.println("DATO_51:" + (String)r.getCampos().get(20));
/* 1309 */         psStmt.setObject(52, Integer.valueOf(1));
/* 1310 */         System.out.println("DATO_52:1");
/* 1311 */         psStmt.setObject(53, Integer.valueOf(piGetIndexValu));
/* 1312 */         System.out.println("DATO_53:" + piGetIndexValu);
/* 1313 */         psStmt.setObject(54, r.getCampos().get(22));
/*      */         
/* 1315 */         System.out.println("DATO_54:" + (String)r.getCampos().get(22));
/* 1316 */         psStmt.setObject(55, r.getCampos().get(23));
/*      */         
/* 1318 */         System.out.println("DATO_55:" + (String)r.getCampos().get(23));
/* 1319 */         psStmt.setObject(56, r.getCampos().get(24));
/*      */         
/* 1321 */         System.out.println("DATO_56:" + (String)r.getCampos().get(24));
/* 1322 */         psStmt.setObject(57, r.getCampos().get(25));
/*      */         
/* 1324 */         System.out.println("DATO_57:" + (String)r.getCampos().get(25));


				


/* 1328 */         psStmt.setObject(58, "");
/* 1329 */         System.out.println("DATO_58:''");
/* 1330 */         psStmt.setObject(59, "");
/* 1331 */         System.out.println("DATO_59:''");

					
/* 1334 */         imprime = "Antes a NUM_IMPORTE";
/*      */ 
/*      */         
/*      */         try {
/* 1338 */           String lstNumPrecioUnit = (String)r.getCampos().get(14);
/*      */           
/* 1340 */           lstNumPrecioUnit = dfDosDecimales.format(Double.parseDouble(lstNumPrecioUnit));
/* 1341 */           psStmt.setObject(60, Double.valueOf(Double.parseDouble(lstNumPrecioUnit)));
/*      */           
/* 1343 */           System.out.println("DATO_60:" + Double.parseDouble(lstNumPrecioUnit));
/* 1344 */         } catch (Exception e) {
/* 1345 */           psStmt.setObject(60, Integer.valueOf(0));
/*      */         } 
/*      */         
/* 1348 */         imprime = "Despues a NUM_IMPORTE";
					
					//KAZ SE INSERTAN LOS 3 ATRIBUTOS DE CFDI 4

					System.out.println("DATO_61:" + (String)r.getCampos().get(26));
					psStmt.setObject(61, r.getCampos().get(26));
					
					System.out.println("DATO_62:" + (version.equals("4.0")?(String)r.getCampos().get(27):""));
					psStmt.setObject(62, version.equals("4.0")?r.getCampos().get(27):null);
					
					System.out.println("DATO_63:" + (version.equals("4.0")?(String)r.getCampos().get(28):""));
					psStmt.setObject(63, version.equals("4.0")?r.getCampos().get(28):null);


/* 1366 */         imprime = "Antes a Ejecutar el update";
/*      */ 
/*      */ 
/*      */         
/* 1370 */         liResultado = psStmt.executeUpdate();
/*      */         
/* 1372 */         imprime = "Despues a Ejecutar el update";
/*      */       
/*      */       }
/*      */     
/*      */     }
/*      */     finally {
/*      */       
/* 1379 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void cargarObjetoEnBaseDatosCNOMINA(ArrayList<RecordCNOMINA> palCNOMINA, int piGetIndexValu) throws SQLException, Exception {
/* 1384 */     int liResultado = 0;
/* 1385 */     String psSql = null;
/* 1386 */     DecimalFormat dfDosDecimales = new DecimalFormat("#.##");
/*      */     
/* 1388 */     ConnectionWrapper luConnection = null;
/* 1389 */     luConnection = new ConnectionWrapper();
/*      */     try {
/* 1391 */       psSql = "INSERT INTO XXAP.XXAP_CFDI_RET_HA_CNOMINA_TAB(ID_LABEL,DES_REGISTROPATRONAL,DES_NUMEMPLEADO,DES_CURP,DES_TIPOREGIMEN,DES_NUMSEGURIDADSOCIAL,DES_FECHAPAGO,DES_FECHAINICIALPAGO,DES_FECHAFINALPAGO,NUM_NUMDIASPAGADOS,DES_FECHAINICIORELLABORAL,DES_ANTIGUEDAD,DES_PUESTO,DES_TIPOCONTRATO,DES_TIPOJORNADA,DES_PERIODICIDADPAGO,NUM_SALARIOBASECOTAPOR,DES_RIESGOPUESTO,NUM_SALARIODIARIOINTEGRADO,NUM_TOTALISR,NUM_PERCEPCIONES_TOTALGRAVADO,NUM_PERCEPCIONES_TOTALEXENTO,NUM_DEDUCCIONES_TOTALGRAVADO,NUM_DEDUCCIONES_TOTALEXENTO,DES_DEPARTAMENTO,DES_CLABE,DES_BANCO,FEC_CREATE_DATE,IS_GENERATED,ATTRIBUTE1,DES_TIPONOMINA,NUM_TOTALPERCEPCIONES,NUM_TOTALDEDUCCIONES,NUM_TOTALOTROSPAGOS,DES_CURPE,DES_RFCPATRONORIGEN,DES_SNCFORIGENRECURSO,NUM_SNCFMONTORECURSOPROPIO,DES_SINDICALIZADO,DES_CLAVEENTFED,NUM_TOTALSUELDOS,NUM_TOTALSEPARACIONINDEM,NUM_TOTALJUBPENSIONRETIRO,NUM_TOTALOTRASDEDUCCIONES,NUM_TOTALIMPUESTOSRETENIDOS,NUM_JPRTOTALUNAEXHIBICION,NUM_JPRTOTALPARCIALIDAD,NUM_MONTODIARIO,NUM_INGRESOACUMULABLE,NUM_INGRESONOACUMULABLE,NUM_SITOTALPAGADO,NUM_SIANIOSSERVICIO,NUM_SIULTIMOSUELDOMENSORD,NUM_SIINGRESOACUMULABLE,NUM_SIINGRESONOACUMULABLE,VAL_DESCUENTO)VALUES(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?, SYSDATE,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
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
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */ 
/*      */       
/* 1507 */       for (RecordCNOMINA r : palCNOMINA) {
/* 1508 */         PreparedStatement psStmt = luConnection.prepareStatement(psSql);
/*      */         
/*      */         try {
/* 1511 */           String pstIdLabel = (String)r.getCampos().get(0);
/* 1512 */           System.out.println("Label vale " + pstIdLabel);
/* 1513 */           int liIdLabel = Integer.parseInt(pstIdLabel);
/* 1514 */           psStmt.setObject(1, Integer.valueOf(liIdLabel));
/* 1515 */         } catch (Exception e) {
/* 1516 */           throw new IllegalArgumentException("Error al convertir el IdLabel.\nCargasCommon: 1086.\n");
/*      */         } 
/*      */         
/* 1519 */         psStmt.setObject(2, r.getCampos().get(1));
/* 1520 */         psStmt.setObject(3, r.getCampos().get(2));
/* 1521 */         psStmt.setObject(4, r.getCampos().get(3));
/* 1522 */         psStmt.setObject(5, r.getCampos().get(4));
/* 1523 */         psStmt.setObject(6, r.getCampos().get(5));
/* 1524 */         psStmt.setObject(7, r.getCampos().get(6));
/* 1525 */         psStmt.setObject(8, r.getCampos().get(7));
/* 1526 */         psStmt.setObject(9, r.getCampos().get(8));
/*      */         
/*      */         try {
/* 1529 */           String lstNumDiasPagados = (String)r.getCampos().get(9);
/* 1530 */           lstNumDiasPagados = dfDosDecimales.format(Double.parseDouble(lstNumDiasPagados));
/* 1531 */           psStmt.setObject(10, Double.valueOf(Double.parseDouble(lstNumDiasPagados)));
/* 1532 */         } catch (Exception e) {
/* 1533 */           psStmt.setObject(10, Integer.valueOf(0));
/*      */         } 
/*      */         
/* 1536 */         psStmt.setObject(11, r.getCampos().get(10));
/* 1537 */         psStmt.setObject(12, r.getCampos().get(11));
/* 1538 */         psStmt.setObject(13, r.getCampos().get(12));
/* 1539 */         psStmt.setObject(14, r.getCampos().get(13));
/* 1540 */         psStmt.setObject(15, r.getCampos().get(14));
/* 1541 */         psStmt.setObject(16, r.getCampos().get(15));
/*      */         
/*      */         try {
/* 1544 */           String lstNumSalarioBaseCotapor = (String)r.getCampos().get(16);
/* 1545 */           lstNumSalarioBaseCotapor = dfDosDecimales.format(Double.parseDouble(lstNumSalarioBaseCotapor));
/* 1546 */           psStmt.setObject(17, Double.valueOf(Double.parseDouble(lstNumSalarioBaseCotapor)));
/* 1547 */         } catch (Exception e) {
/* 1548 */           psStmt.setObject(17, Integer.valueOf(0));
/*      */         } 
/* 1550 */         psStmt.setObject(18, r.getCampos().get(17));
/*      */         
/*      */         try {
/* 1553 */           String lstNumSalarioDiarioIntegrado = (String)r.getCampos().get(18);
/* 1554 */           lstNumSalarioDiarioIntegrado = dfDosDecimales.format(Double.parseDouble(lstNumSalarioDiarioIntegrado));
/* 1555 */           psStmt.setObject(19, Double.valueOf(Double.parseDouble(lstNumSalarioDiarioIntegrado)));
/* 1556 */         } catch (Exception e) {
/* 1557 */           psStmt.setObject(19, Integer.valueOf(0));
/*      */         } 
/*      */         try {
/* 1560 */           String lstNumTotalIsr = (String)r.getCampos().get(19);
/* 1561 */           lstNumTotalIsr = dfDosDecimales.format(Double.parseDouble(lstNumTotalIsr));
/* 1562 */           psStmt.setObject(20, Double.valueOf(Double.parseDouble(lstNumTotalIsr)));
/* 1563 */         } catch (Exception e) {
/* 1564 */           psStmt.setObject(20, Integer.valueOf(0));
/*      */         } 
/*      */         try {
/* 1567 */           String lstNumPercepTotGra = (String)r.getCampos().get(20);
/* 1568 */           lstNumPercepTotGra = dfDosDecimales.format(Double.parseDouble(lstNumPercepTotGra));
/* 1569 */           psStmt.setObject(21, Double.valueOf(Double.parseDouble(lstNumPercepTotGra)));
/* 1570 */         } catch (Exception e) {
/* 1571 */           psStmt.setObject(21, Integer.valueOf(0));
/*      */         } 
/*      */         try {
/* 1574 */           String lstNumPercepTotExe = (String)r.getCampos().get(21);
/* 1575 */           lstNumPercepTotExe = dfDosDecimales.format(Double.parseDouble(lstNumPercepTotExe));
/* 1576 */           psStmt.setObject(22, Double.valueOf(Double.parseDouble(lstNumPercepTotExe)));
/* 1577 */         } catch (Exception e) {
/* 1578 */           psStmt.setObject(22, Integer.valueOf(0));
/*      */         } 
/*      */         try {
/* 1581 */           String lstNumDeducTotGra = (String)r.getCampos().get(22);
/* 1582 */           lstNumDeducTotGra = dfDosDecimales.format(Double.parseDouble(lstNumDeducTotGra));
/* 1583 */           psStmt.setObject(23, Double.valueOf(Double.parseDouble(lstNumDeducTotGra)));
/* 1584 */         } catch (Exception e) {
/* 1585 */           psStmt.setObject(23, Integer.valueOf(0));
/*      */         }  try {
/* 1587 */           String lstNumDeducTotExe = (String)r.getCampos().get(23);
/* 1588 */           lstNumDeducTotExe = dfDosDecimales.format(Double.parseDouble(lstNumDeducTotExe));
/* 1589 */           psStmt.setObject(24, Double.valueOf(Double.parseDouble(lstNumDeducTotExe)));
/* 1590 */         } catch (Exception e) {
/* 1591 */           psStmt.setObject(24, Integer.valueOf(0));
/*      */         } 
/* 1593 */         psStmt.setObject(25, r.getCampos().get(24));
/* 1594 */         psStmt.setObject(26, r.getCampos().get(25));
/* 1595 */         psStmt.setObject(27, r.getCampos().get(26));
/* 1596 */         psStmt.setObject(28, Integer.valueOf(1));
/* 1597 */         psStmt.setObject(29, Integer.valueOf(piGetIndexValu));
/*      */ 
/*      */         
/* 1600 */         psStmt.setObject(30, r.getCampos().get(27));
/*      */         try {
/* 1602 */           String lstTotalPercepciones = (String)r.getCampos().get(28);
/* 1603 */           lstTotalPercepciones = dfDosDecimales.format(Double.parseDouble(lstTotalPercepciones));
/* 1604 */           psStmt.setObject(31, Double.valueOf(Double.parseDouble(lstTotalPercepciones)));
/* 1605 */         } catch (Exception e) {
/* 1606 */           psStmt.setObject(31, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1610 */           String lstTotalDeducciones = (String)r.getCampos().get(29);
/* 1611 */           lstTotalDeducciones = dfDosDecimales.format(Double.parseDouble(lstTotalDeducciones));
/* 1612 */           psStmt.setObject(32, Double.valueOf(Double.parseDouble(lstTotalDeducciones)));
/* 1613 */         } catch (Exception e) {
/* 1614 */           psStmt.setObject(32, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1618 */           String lstTotalOtrosPag = (String)r.getCampos().get(30);
/* 1619 */           lstTotalOtrosPag = dfDosDecimales.format(Double.parseDouble(lstTotalOtrosPag));
/* 1620 */           psStmt.setObject(33, Double.valueOf(Double.parseDouble(lstTotalOtrosPag)));
/* 1621 */         } catch (Exception e) {
/* 1622 */           psStmt.setObject(33, Integer.valueOf(0));
/*      */         } 
/*      */         
/* 1625 */         psStmt.setObject(34, r.getCampos().get(31));
/* 1626 */         psStmt.setObject(35, r.getCampos().get(32));
/* 1627 */         psStmt.setObject(36, r.getCampos().get(33));
/*      */         
/*      */         try {
/* 1630 */           String lstMontoRecPropio = (String)r.getCampos().get(34);
/* 1631 */           lstMontoRecPropio = dfDosDecimales.format(Double.parseDouble(lstMontoRecPropio));
/* 1632 */           psStmt.setObject(37, Double.valueOf(Double.parseDouble(lstMontoRecPropio)));
/* 1633 */         } catch (Exception e) {
/* 1634 */           psStmt.setObject(37, Integer.valueOf(0));
/*      */         } 
/*      */         
/* 1637 */         psStmt.setObject(38, r.getCampos().get(35));
/* 1638 */         psStmt.setObject(39, r.getCampos().get(36));
/*      */         
/*      */         try {
/* 1641 */           String lstTotalSueldos = (String)r.getCampos().get(37);
/* 1642 */           lstTotalSueldos = dfDosDecimales.format(Double.parseDouble(lstTotalSueldos));
/* 1643 */           psStmt.setObject(40, Double.valueOf(Double.parseDouble(lstTotalSueldos)));
/* 1644 */         } catch (Exception e) {
/* 1645 */           psStmt.setObject(40, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1649 */           String lstTotalSepIndemni = (String)r.getCampos().get(38);
/* 1650 */           lstTotalSepIndemni = dfDosDecimales.format(Double.parseDouble(lstTotalSepIndemni));
/* 1651 */           psStmt.setObject(41, Double.valueOf(Double.parseDouble(lstTotalSepIndemni)));
/* 1652 */         } catch (Exception e) {
/* 1653 */           psStmt.setObject(41, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1657 */           String lstTotalJubPensReti = (String)r.getCampos().get(39);
/* 1658 */           lstTotalJubPensReti = dfDosDecimales.format(Double.parseDouble(lstTotalJubPensReti));
/* 1659 */           psStmt.setObject(42, Double.valueOf(Double.parseDouble(lstTotalJubPensReti)));
/* 1660 */         } catch (Exception e) {
/* 1661 */           psStmt.setObject(42, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1665 */           String lstTotalOtrasDeduc = (String)r.getCampos().get(40);
/* 1666 */           lstTotalOtrasDeduc = dfDosDecimales.format(Double.parseDouble(lstTotalOtrasDeduc));
/* 1667 */           psStmt.setObject(43, Double.valueOf(Double.parseDouble(lstTotalOtrasDeduc)));
/* 1668 */         } catch (Exception e) {
/* 1669 */           psStmt.setObject(43, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1673 */           String lstTotalImpuRet = (String)r.getCampos().get(41);
/* 1674 */           lstTotalImpuRet = dfDosDecimales.format(Double.parseDouble(lstTotalImpuRet));
/* 1675 */           psStmt.setObject(44, Double.valueOf(Double.parseDouble(lstTotalImpuRet)));
/* 1676 */         } catch (Exception e) {
/* 1677 */           psStmt.setObject(44, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1681 */           String lstTotalUnaExhibicion = (String)r.getCampos().get(42);
/* 1682 */           lstTotalUnaExhibicion = dfDosDecimales.format(Double.parseDouble(lstTotalUnaExhibicion));
/* 1683 */           psStmt.setObject(45, Double.valueOf(Double.parseDouble(lstTotalUnaExhibicion)));
/* 1684 */         } catch (Exception e) {
/* 1685 */           psStmt.setObject(45, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1689 */           String lstTotalParcialidad = (String)r.getCampos().get(43);
/* 1690 */           lstTotalParcialidad = dfDosDecimales.format(Double.parseDouble(lstTotalParcialidad));
/* 1691 */           psStmt.setObject(46, Double.valueOf(Double.parseDouble(lstTotalParcialidad)));
/* 1692 */         } catch (Exception e) {
/* 1693 */           psStmt.setObject(46, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1697 */           String lstMontoDiario = (String)r.getCampos().get(44);
/* 1698 */           lstMontoDiario = dfDosDecimales.format(Double.parseDouble(lstMontoDiario));
/* 1699 */           psStmt.setObject(47, Double.valueOf(Double.parseDouble(lstMontoDiario)));
/* 1700 */         } catch (Exception e) {
/* 1701 */           psStmt.setObject(47, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1705 */           String lstIngresoAcumulable = (String)r.getCampos().get(45);
/* 1706 */           lstIngresoAcumulable = dfDosDecimales.format(Double.parseDouble(lstIngresoAcumulable));
/* 1707 */           psStmt.setObject(48, Double.valueOf(Double.parseDouble(lstIngresoAcumulable)));
/* 1708 */         } catch (Exception e) {
/* 1709 */           psStmt.setObject(48, Integer.valueOf(0));
/*      */         } 
/*      */ 
/*      */         
/*      */         try {
/* 1714 */           String lstIngresoNoAcumulable = (String)r.getCampos().get(46);
/* 1715 */           lstIngresoNoAcumulable = dfDosDecimales.format(Double.parseDouble(lstIngresoNoAcumulable));
/* 1716 */           psStmt.setObject(49, Double.valueOf(Double.parseDouble(lstIngresoNoAcumulable)));
/* 1717 */         } catch (Exception e) {
/* 1718 */           psStmt.setObject(49, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1722 */           String lstTotalPagado = (String)r.getCampos().get(47);
/* 1723 */           lstTotalPagado = dfDosDecimales.format(Double.parseDouble(lstTotalPagado));
/* 1724 */           psStmt.setObject(50, Double.valueOf(Double.parseDouble(lstTotalPagado)));
/* 1725 */         } catch (Exception e) {
/* 1726 */           psStmt.setObject(50, Integer.valueOf(0));
/*      */         } 
/*      */         
/* 1729 */         psStmt.setObject(51, r.getCampos().get(48));
/*      */         
/*      */         try {
/* 1732 */           String lstSiUltSueMenOrd = (String)r.getCampos().get(49);
/* 1733 */           lstSiUltSueMenOrd = dfDosDecimales.format(Double.parseDouble(lstSiUltSueMenOrd));
/* 1734 */           psStmt.setObject(52, Double.valueOf(Double.parseDouble(lstSiUltSueMenOrd)));
/* 1735 */         } catch (Exception e) {
/* 1736 */           psStmt.setObject(52, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1740 */           String lstSiIngresoAcum = (String)r.getCampos().get(50);
/* 1741 */           lstSiIngresoAcum = dfDosDecimales.format(Double.parseDouble(lstSiIngresoAcum));
/* 1742 */           psStmt.setObject(53, Double.valueOf(Double.parseDouble(lstSiIngresoAcum)));
/* 1743 */         } catch (Exception e) {
/* 1744 */           psStmt.setObject(53, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1748 */           String lstSiIngresoNoAcum = (String)r.getCampos().get(51);
/* 1749 */           lstSiIngresoNoAcum = dfDosDecimales.format(Double.parseDouble(lstSiIngresoNoAcum));
/* 1750 */           psStmt.setObject(54, Double.valueOf(Double.parseDouble(lstSiIngresoNoAcum)));
/* 1751 */         } catch (Exception e) {
/* 1752 */           psStmt.setObject(54, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1756 */           String lstValDescuento = (String)r.getCampos().get(52);
/* 1757 */           lstValDescuento = dfDosDecimales.format(Double.parseDouble(lstValDescuento));
/* 1758 */           psStmt.setObject(55, Double.valueOf(Double.parseDouble(lstValDescuento)));
/* 1759 */         } catch (Exception e) {
/* 1760 */           psStmt.setObject(55, Integer.valueOf(0));
/*      */         } 
/*      */         
/* 1763 */         liResultado = psStmt.executeUpdate();
/*      */       } 
/*      */     } finally {
/* 1766 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*      */   }
/*      */   
/*      */   public static void cargarObjetoEnBaseDatosCNOMINADP(ArrayList<RecordCNOMINADP> palCNOMINADP, int piGetIndexValu) throws SQLException, Exception {
/* 1771 */     String psSql = null;
/* 1772 */     DecimalFormat dfDosDecimales = new DecimalFormat("#.##");
/* 1773 */     DecimalFormat dfSeisDecimales = new DecimalFormat("#.######");
/* 1774 */     ConnectionWrapper luConnection = null;
/* 1775 */     luConnection = new ConnectionWrapper();
/*      */     
/*      */     try {
/* 1778 */       psSql = "INSERT INTO XXAP.XXAP_CFDI_RET_HA_CNOMINADP_TAB(ID_LABEL,DES_CALIFICADOR,DES_TIPO,DES_CLAVE,DES_CONCEPTO,NUM_IMPORTEGRAVADO,NUM_IMPORTEEXENTO,FEC_CREATE_DATE,IS_GENERATED,ATTRIBUTE1,DES_IDP,NUM_AOTVALORMERCADO,NUM_AOTVALORALOTORGARSE,NUM_SAESUBSIDIOCAUSADO,NUM_CSFSALDOAFAVOR,DES_SAFANIO,NUM_CSFREMANENTESALFAV)VALUES(?,?,?,?,?,?,?, SYSDATE, 1,?,?,?,?,?,?,?,?)";
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
/*      */       
/* 1816 */       for (RecordCNOMINADP r : palCNOMINADP) {
/* 1817 */         PreparedStatement psStmt = luConnection.prepareStatement(psSql);
/*      */         
/*      */         try {
/* 1820 */           String pstIdLabel = (String)r.getCampos().get(0);
/* 1821 */           System.out.println("Label vale " + pstIdLabel);
/* 1822 */           int liIdLabel = Integer.parseInt(pstIdLabel);
/* 1823 */           psStmt.setObject(1, Integer.valueOf(liIdLabel));
/* 1824 */         } catch (Exception e) {
/* 1825 */           throw new IllegalArgumentException("Error al convertir el IdLabel.\nCargasCommon: 1233.\n");
/*      */         } 
/* 1827 */         psStmt.setObject(2, r.getCampos().get(1));
/* 1828 */         psStmt.setObject(3, r.getCampos().get(2));
/* 1829 */         psStmt.setObject(4, r.getCampos().get(3));
/* 1830 */         psStmt.setObject(5, r.getCampos().get(4));
/*      */         
/*      */         try {
/* 1833 */           String lstNumImpGra = (String)r.getCampos().get(5);
/* 1834 */           lstNumImpGra = dfDosDecimales.format(Double.parseDouble(lstNumImpGra));
/* 1835 */           psStmt.setObject(6, Double.valueOf(Double.parseDouble(lstNumImpGra)));
/* 1836 */         } catch (Exception e) {
/* 1837 */           psStmt.setObject(6, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1841 */           String lstNumImpExe = (String)r.getCampos().get(6);
/* 1842 */           lstNumImpExe = dfDosDecimales.format(Double.parseDouble(lstNumImpExe));
/* 1843 */           psStmt.setObject(7, Double.valueOf(Double.parseDouble(lstNumImpExe)));
/* 1844 */         } catch (Exception e) {
/* 1845 */           psStmt.setObject(7, Integer.valueOf(0));
/*      */         } 
/*      */         
/* 1848 */         psStmt.setObject(8, Integer.valueOf(piGetIndexValu));
/* 1849 */         psStmt.setObject(9, r.getCampos().get(7));
/*      */         
/*      */         try {
/* 1852 */           String lstValorMercado = (String)r.getCampos().get(8);
/* 1853 */           lstValorMercado = dfSeisDecimales.format(Double.parseDouble(lstValorMercado));
/* 1854 */           psStmt.setObject(10, Double.valueOf(Double.parseDouble(lstValorMercado)));
/* 1855 */         } catch (Exception e) {
/* 1856 */           psStmt.setObject(10, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1860 */           String lstValorOtorgarse = (String)r.getCampos().get(9);
/* 1861 */           lstValorOtorgarse = dfSeisDecimales.format(Double.parseDouble(lstValorOtorgarse));
/* 1862 */           psStmt.setObject(11, Double.valueOf(Double.parseDouble(lstValorOtorgarse)));
/* 1863 */         } catch (Exception e) {
/* 1864 */           psStmt.setObject(11, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1868 */           String lstSaeSubsidioCausado = (String)r.getCampos().get(10);
/* 1869 */           lstSaeSubsidioCausado = dfDosDecimales.format(Double.parseDouble(lstSaeSubsidioCausado));
/* 1870 */           psStmt.setObject(12, Double.valueOf(Double.parseDouble(lstSaeSubsidioCausado)));
/* 1871 */         } catch (Exception e) {
/* 1872 */           psStmt.setObject(12, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1876 */           String lstSafSaldoFavor = (String)r.getCampos().get(11);
/* 1877 */           lstSafSaldoFavor = dfDosDecimales.format(Double.parseDouble(lstSafSaldoFavor));
/* 1878 */           psStmt.setObject(13, Double.valueOf(Double.parseDouble(lstSafSaldoFavor)));
/* 1879 */         } catch (Exception e) {
/* 1880 */           psStmt.setObject(13, Integer.valueOf(0));
/*      */         } 
/*      */         
/*      */         try {
/* 1884 */           String pstSafAnio = (String)r.getCampos().get(12);
/* 1885 */           int lintSafAnio = Integer.parseInt(pstSafAnio);
/* 1886 */           psStmt.setObject(14, Integer.valueOf(lintSafAnio));
/* 1887 */         } catch (Exception e) {
/*      */           
/* 1889 */           psStmt.setObject(14, null);
/*      */         } 
/*      */         
/*      */         try {
/* 1893 */           String lstCsfRemSalFav = (String)r.getCampos().get(13);
/* 1894 */           lstCsfRemSalFav = dfDosDecimales.format(Double.parseDouble(lstCsfRemSalFav));
/* 1895 */           psStmt.setObject(15, Double.valueOf(Double.parseDouble(lstCsfRemSalFav)));
/* 1896 */         } catch (Exception e) {
/* 1897 */           psStmt.setObject(15, Integer.valueOf(0));
/*      */         } 
/*      */         
/* 1900 */         psStmt.executeUpdate();
/*      */       } 
/*      */     } finally {
/* 1903 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*      */     } 
/*      */   }
/*      */ }
