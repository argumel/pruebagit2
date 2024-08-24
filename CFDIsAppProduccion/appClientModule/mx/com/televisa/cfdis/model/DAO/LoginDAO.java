/*     */ package mx.com.televisa.cfdis.model.DAO;
import java.io.IOException;
/*     */ 
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.util.ArrayList;
/*     */ import java.util.ResourceBundle;
/*     */ import mx.com.televisa.cfdis.data.ConnectionWrapper;
/*     */ import mx.com.televisa.cfdis.model.DTO.LoginDTO;
/*     */ import mx.com.televisa.cfdis.model.DTO.RecordDTO;
import mx.com.televisa.cfdis.service.cveptt.ConsultarClaveFiscal;
import mx.com.televisa.cfdis.service.cveptt.ExecutePttGetValidationFileREQUEST;
import mx.com.televisa.cfdis.service.cveptt.InputElement;
import mx.com.televisa.cfdis.service.cveptt.OutputElement;
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
/*     */ public class LoginDAO
/*     */ {
/*     */   private String psSql;
/*     */   private String psUser;
/*     */   private String psPassword;
/*     */   private int regEncontrado;
/*     */   private String psRol;
/*     */   private int piStatus;
/*     */   private int piIdUser;
/*  39 */   ResourceBundle bundle = ResourceBundle.getBundle("DataConnection");
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginDTO loginCorrect(String tsUser, String tsPassword) throws Exception {
/*  46 */     LoginDTO luLoginDTO = null;
/*     */     
/*  48 */     ConnectionWrapper luConnection = null;
/*     */     
/*     */     try {
/*  51 */       luConnection = new ConnectionWrapper();
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
/*  73 */       this.psSql = "SELECT NOM_USER, COD_PASSWORD, ID_USER, IND_USER_ROL, NUM_STATUS, ATTRIBUTE1 FROM XXAP.XXAP_CFDIS_RET_JAVA_LOGIN_TAB WHERE NOM_USER = ?";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*  80 */       PreparedStatement stmt = luConnection.prepareStatement(this.psSql);
/*     */ 
/*     */ 
/*     */       
/*  84 */       stmt.setString(1, tsUser);
/*  85 */       ResultSet luResulset = stmt.executeQuery();
/*     */ 
/*     */       
/*  88 */       if (luResulset.next()) {
/*  89 */         this.psUser = luResulset.getString("NOM_USER");
/*     */         
/*  91 */         this.psPassword = luResulset.getString("ATTRIBUTE1");
/*  92 */         this.psRol = luResulset.getString("IND_USER_ROL");
/*  93 */         this.piStatus = luResulset.getInt("NUM_STATUS");
/*  94 */         this.piIdUser = luResulset.getInt("ID_USER");
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
/* 105 */         if (this.psUser.equals(tsUser) && this.psPassword.equals(tsPassword)) {
/* 106 */           luLoginDTO = new LoginDTO();
/* 107 */           luLoginDTO.setPsUser(this.psUser);
/* 108 */           luLoginDTO.setPsPassword(this.psPassword);
/* 109 */           luLoginDTO.setPsRol(this.psRol);
/* 110 */           luLoginDTO.setPbValidoUsrPwd(true);
/* 111 */           luLoginDTO.setPiStatus(this.piStatus);
/* 112 */           luLoginDTO.setPiIdUser(this.piIdUser);
/*     */         } else {
/*     */           
/* 115 */           luLoginDTO = new LoginDTO();
/* 116 */           luLoginDTO.setPbValidoUsrPwd(false);
/* 117 */           luLoginDTO.setPiTipoError(1);
/*     */         
/*     */         }
/*     */ 
/*     */       
/*     */       }
/*     */       else {
/*     */ 
/*     */         
/* 126 */         luLoginDTO = new LoginDTO();
/* 127 */         luLoginDTO.setPbValidoUsrPwd(false);
/* 128 */         luLoginDTO.setPiTipoError(2);
/*     */       }
/*     */     
/* 131 */     } catch (SQLException tSqlEx) {
/* 132 */       tSqlEx.printStackTrace();
/* 133 */     } catch (Exception tEx) {
/* 134 */       tEx.printStackTrace();
/*     */     } finally {
/* 136 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*     */     } 
/*     */     
/* 139 */     return luLoginDTO;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginDTO loginCorrect(String tsPassword) throws Exception {
/* 146 */     LoginDTO luLoginDTO = null;
/*     */     
/* 148 */     //ConnectionWrapper luConnection = null;
/*     */     
/*     */     try {	
/* 151 */       //luConnection = new ConnectionWrapper();
/*     */ 
/*     */ 		InputElement loinput = new InputElement();
				loinput.setClave(tsPassword);
/*     */ 		ConsultarClaveFiscal loconcvefisc = new ConsultarClaveFiscal();
				ExecutePttGetValidationFileREQUEST valcvefiscImp = loconcvefisc.getExecutePttGetValidationFileREQUESTPt();
/*     */ 		System.out.println("Validando clave OIC: " + tsPassword);
				OutputElement looutput = new OutputElement();
				looutput = valcvefiscImp.consultarClaveFiscal(loinput);//Kaz se comenta para que pase y no valide
				System.out.println("Validando clave OIC: " + looutput.isExisteClave());
				//kaz
				//looutput.setExisteClave(true);
				if(!looutput.isExisteClave()) {
/*         
       this.psSql = "SELECT COUNT(1) AS NUM_REG FROM   Apps.Fnd_lookup_values Lv WHERE  Lv.Lookup_type = 'XX-CVE ACCS GRAL TIMB TERCEROS'  AND LANGUAGE='US'  AND ENABLED_FLAG='Y'  AND MEANING=?  AND SYSDATE BETWEEN NVL(START_DATE_ACTIVE,SYSDATE) AND NVL(END_DATE_ACTIVE,SYSDATE)";
       
       System.out.println("Query: " + this.psSql);
       
       PreparedStatement stmt = luConnection.prepareStatement(this.psSql);
       stmt.setString(1, tsPassword);
       ResultSet luResulset = stmt.executeQuery();
       if (luResulset.next()) {
         
         this.regEncontrado = luResulset.getInt("NUM_REG");
         if (this.regEncontrado > 0) {
           
          luLoginDTO = new LoginDTO();
           
           luLoginDTO.setPsPassword(tsPassword);
          
           luLoginDTO.setPbValidoUsrPwd(true);
       
        }
        else {
/*     */           
/* 178 */           luLoginDTO = new LoginDTO();
/* 179 */           luLoginDTO.setPbValidoUsrPwd(false);
/* 180 */           luLoginDTO.setPiTipoError(1);
       /*} 
       } else {
         luLoginDTO = new LoginDTO();
        luLoginDTO.setPbValidoUsrPwd(false);
        luLoginDTO.setPiTipoError(2);
       }*/
				}
				else {
					luLoginDTO = new LoginDTO();
				      
					        luLoginDTO.setPsPassword(tsPassword);
					        
					      luLoginDTO.setPbValidoUsrPwd(true);
					
				}
/*     */     
/* 188 */     } catch (Exception tEx) {
/* 191 */       tEx.printStackTrace();
/*     */     } finally {
/* 193 */      // ConnectionWrapper.closeAll(new Object[] { luConnection });
/*     */     } 
/*     */     
/* 196 */     return luLoginDTO;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public ArrayList bringSimpleQuery() throws Exception {
/* 202 */     this.psSql = null;
/*     */     
/* 204 */     ResultSet luResultSet = null;
/* 205 */     ConnectionWrapper luConnection = null;
/* 206 */     ArrayList alRegistros = null;
/* 207 */     RecordDTO luRecordDTO = null;
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
/*     */     try {
/* 219 */       luConnection = new ConnectionWrapper();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 227 */       this.psSql = "SELECT ID_USER, NOM_USER, COD_PASSWORD, IND_USER_ROL FROM XXAP.XXAP_CFDIS_RET_JAVA_LOGIN_TAB ";
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 232 */       luResultSet = luConnection.executeQuery(this.psSql);
/*     */       
/* 234 */       alRegistros = new ArrayList();
/* 235 */       int liContador = 0;
/* 236 */       while (luResultSet.next())
/*     */       {
/* 238 */         luRecordDTO = new RecordDTO();
/* 239 */         luRecordDTO.setPsUser(luResultSet.getString("NOM_USER"));
/* 240 */         luRecordDTO.setPsPassword(luResultSet.getString("COD_PASSWORD"));
/* 241 */         luRecordDTO.setPsRolUser(luResultSet.getString("IND_USER_ROL"));
/* 242 */         luRecordDTO.setPsIdUser(luResultSet.getString("ID_USER"));
/*     */         
/* 244 */         alRegistros.add(liContador++, luRecordDTO);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */       
/* 252 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*     */     } 
/*     */     
/* 255 */     return alRegistros;
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public int executeInsert(LoginDTO tLoginDTO) throws Exception {
/* 261 */     int liResultado = 0;
/* 262 */     this.psSql = null;
/*     */     
/* 264 */     ConnectionWrapper luConnection = null;
/*     */     
/*     */     try {
/* 267 */       this.psSql = 
/* 268 */         "insert into XXAP.XXAP_CFDIS_RET_JAVA_LOGIN_TAB (ID_USER, NOM_USER, COD_PASSWORD, IND_USER_ROL, ATTRIBUTE1) values(XXAP.XXAP_CFDIS_RET_JAVA_LOGIN_SQ.NextVal, '" + 
/* 269 */         tLoginDTO.getPsUser() + "'" + ", " + "'" + 
/* 270 */         "*****" + "'" + ", " + "'" + tLoginDTO.getPsRol() + "'" + ", " + "'" + tLoginDTO.getSPwdEncrypted() + "'" + ")";
/*     */ 
/*     */       
/* 273 */       luConnection = new ConnectionWrapper();
/* 274 */       liResultado = luConnection.executeUpdate(this.psSql);
/*     */ 
/*     */     
/*     */     }
/*     */     finally {
/*     */ 
/*     */       
/* 281 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*     */     } 
/*     */     
/* 284 */     return liResultado;
/*     */   }
/*     */   
/*     */   public int executeUpdate(LoginDTO tLoginDTO) throws Exception {
/* 288 */     int liResultado = 0;
/* 289 */     this.psSql = null;
/*     */     
/* 291 */     int liIdUser = tLoginDTO.getPiIdUser();
/* 292 */     String lsUser = tLoginDTO.getPsUser();
/*     */     
/* 294 */     String lsPassword = "*****";
/* 295 */     String lsRol = tLoginDTO.getPsRol();
/* 296 */     String lsPwsEncrypted = tLoginDTO.getSPwdEncrypted();
/*     */     
/* 298 */     ConnectionWrapper luConnection = null;
/*     */ 
/*     */     
/*     */     try {
/* 302 */       this.psSql = 
/* 303 */         "update XXAP.XXAP_CFDIS_RET_JAVA_LOGIN_TAB set NOM_USER = '" + lsUser + "', " + "COD_PASSWORD " + 
/* 304 */         "= " + "'" + lsPassword + "', " + "IND_USER_ROL " + "= " + "'" + lsRol + "', " + "ATTRIBUTE1 " + "= " + "'" + 
/* 305 */         lsPwsEncrypted + "'" + "where " + "ID_USER " + "= " + "'" + liIdUser + "'";
/*     */       
/* 307 */       luConnection = new ConnectionWrapper();
/* 308 */       liResultado = luConnection.executeUpdate(this.psSql);
/*     */     }
/* 310 */     catch (SQLException tSqlEx) {
/* 311 */       tSqlEx.printStackTrace();
/* 312 */     } catch (Exception tEx) {
/* 313 */       tEx.printStackTrace();
/*     */     } finally {
/* 315 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*     */     } 
/*     */     
/* 318 */     return liResultado;
/*     */   }
/*     */ 
/*     */   
/*     */   public ArrayList searchRecord(String pSearch) {
/* 323 */     this.psSql = null;
/* 324 */     ResultSet luResultSet = null;
/* 325 */     ConnectionWrapper luConnection = null;
/* 326 */     ArrayList alRegistros = null;
/* 327 */     RecordDTO luRecordDTO = null;
/*     */ 
/*     */     
/*     */     try {
/* 331 */       this.psSql = 
/* 332 */         "select ID_USER, NOM_USER, COD_PASSWORD, IND_USER_ROL from XXAP.XXAP_CFDIS_RET_JAVA_LOGIN_TAB where NOM_USER like '%" + 
/* 333 */         pSearch + "%'";
/* 334 */       luConnection = new ConnectionWrapper();
/* 335 */       luResultSet = luConnection.executeQuery(this.psSql);
/*     */       
/* 337 */       alRegistros = new ArrayList();
/* 338 */       int liContador = 0;
/*     */       
/* 340 */       while (luResultSet.next())
/*     */       {
/* 342 */         luRecordDTO = new RecordDTO();
/* 343 */         luRecordDTO.setPsUser(luResultSet.getString("NOM_USER"));
/* 344 */         luRecordDTO.setPsPassword(luResultSet.getString("COD_PASSWORD"));
/* 345 */         luRecordDTO.setPsRolUser(luResultSet.getString("IND_USER_ROL"));
/* 346 */         luRecordDTO.setPsIdUser(luResultSet.getString("ID_USER"));
/*     */         
/* 348 */         alRegistros.add(liContador++, luRecordDTO);
/*     */       }
/*     */     
/*     */     }
/* 352 */     catch (SQLException tSqlEx) {
/* 353 */       tSqlEx.printStackTrace();
/* 354 */     } catch (Exception tEx) {
/* 355 */       tEx.printStackTrace();
/*     */     } finally {
/* 357 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*     */     } 
/*     */     
/* 360 */     return alRegistros;
/*     */   }
/*     */ 
/*     */   
/*     */   public void deleteRecord(int piIdUser) {
/* 365 */     int liResultado = 0;
/* 366 */     this.psSql = null;
/* 367 */     ConnectionWrapper luConnection = null;
/*     */ 
/*     */     
/*     */     try {
/* 371 */       this.psSql = "delete from XXAP.XXAP_CFDIS_RET_JAVA_LOGIN_TAB where ID_USER = " + piIdUser;
/* 372 */       luConnection = new ConnectionWrapper();
/* 373 */       liResultado = luConnection.executeUpdate(this.psSql);
/*     */     }
/* 375 */     catch (SQLException tSqlEx) {
/* 376 */       tSqlEx.printStackTrace();
/* 377 */     } catch (Exception tEx) {
/* 378 */       tEx.printStackTrace();
/*     */     } finally {
/* 380 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public void insertStatus(String tsUser, String tsPassword, int tiStatus) {
/* 386 */     this.psSql = null;
/* 387 */     ConnectionWrapper luConnection = null;
/*     */     try {
/* 389 */       this.psSql = 
/* 390 */         "update XXAP.XXAP_CFDIS_RET_JAVA_LOGIN_TAB set NUM_STATUS = " + tiStatus + " where " + "NOM_USER = '" + 
/* 391 */         tsUser + "' " + "and " + 
/*     */         
/* 393 */         "ATTRIBUTE1 = '" + tsPassword + "'";
/*     */       
/* 395 */       luConnection = new ConnectionWrapper();
/* 396 */       luConnection.executeUpdate(this.psSql);
/*     */     }
/* 398 */     catch (SQLException tSqlEx) {
/* 399 */       tSqlEx.printStackTrace();
/* 400 */     } catch (Exception tEx) {
/* 401 */       tEx.printStackTrace();
/*     */     } finally {
/* 403 */       ConnectionWrapper.closeAll(new Object[] { luConnection });
/*     */     } 
/*     */   }
/*     */ }

