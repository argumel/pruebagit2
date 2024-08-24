/*     */ package mx.com.televisa.cfdis.model.DTO;
/*     */ 
/*     */ import java.io.Serializable;
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
/*     */ public class LoginDTO
/*     */   implements Serializable
/*     */ {
/*     */   private static final long serialVersionUID = 0L;
/*     */   private String psUser;
/*     */   private String psPassword;
/*     */   private String psConfirmPwd;
/*     */   private String psRol;
/*     */   private boolean pbValidoUsrPwd;
/*     */   private int piStatus;
/*     */   private int piIdUser;
/*     */   private int piTipoError;
/*     */   private String sPwdEncrypted;
/*     */   
/*  39 */   public String getPsUser() { return this.psUser; }
/*     */ 
/*     */ 
/*     */   
/*  43 */   public void setPsUser(String psUser) { this.psUser = psUser; }
/*     */ 
/*     */ 
/*     */   
/*  47 */   public String getPsPassword() { return this.psPassword; }
/*     */ 
/*     */ 
/*     */   
/*  51 */   public void setPsPassword(String psPassword) { this.psPassword = psPassword; }
/*     */ 
/*     */ 
/*     */   
/*  55 */   public String getPsConfirmPwd() { return this.psConfirmPwd; }
/*     */ 
/*     */ 
/*     */   
/*  59 */   public void setPsConfirmPwd(String psConfirmPwd) { this.psConfirmPwd = psConfirmPwd; }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  64 */   public String getPsRol() { return this.psRol; }
/*     */ 
/*     */ 
/*     */   
/*  68 */   public void setPsRol(String psRol) { this.psRol = psRol; }
/*     */ 
/*     */ 
/*     */   
/*  72 */   public boolean isPbValidoUsrPwd() { return this.pbValidoUsrPwd; }
/*     */ 
/*     */ 
/*     */   
/*  76 */   public void setPbValidoUsrPwd(boolean pbValidoUsrPwd) { this.pbValidoUsrPwd = pbValidoUsrPwd; }
/*     */ 
/*     */ 
/*     */   
/*  80 */   public int getPiIdUser() { return this.piIdUser; }
/*     */ 
/*     */ 
/*     */   
/*  84 */   public void setPiIdUser(int piIdUser) { this.piIdUser = piIdUser; }
/*     */ 
/*     */ 
/*     */   
/*  88 */   public int getPiStatus() { return this.piStatus; }
/*     */ 
/*     */ 
/*     */   
/*  92 */   public void setPiStatus(int piStatus) { this.piStatus = piStatus; }
/*     */ 
/*     */ 
/*     */   
/*  96 */   public int getPiTipoError() { return this.piTipoError; }
/*     */ 
/*     */ 
/*     */   
/* 100 */   public void setPiTipoError(int piTipoError) { this.piTipoError = piTipoError; }
/*     */ 
/*     */ 
/*     */   
/* 104 */   public String getSPwdEncrypted() { return this.sPwdEncrypted; }
/*     */ 
/*     */ 
/*     */   
/* 108 */   public void setSPwdEncrypted(String sPwdEncrypted) { this.sPwdEncrypted = sPwdEncrypted; }
/*     */ }

