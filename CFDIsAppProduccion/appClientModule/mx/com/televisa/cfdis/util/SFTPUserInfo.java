/*    */ package mx.com.televisa.cfdis.util;
/*    */ 
/*    */ import com.jcraft.jsch.UserInfo;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class SFTPUserInfo
/*    */   implements UserInfo
/*    */ {
/*    */   private String password;
/*    */   private String passPhrase;
/*    */   
/*    */   public SFTPUserInfo(String password, String passPhrase) {
/* 15 */     this.password = password;
/* 16 */     this.passPhrase = passPhrase;
/*    */   }
/*    */ 
/*    */   
/* 20 */   public String getPassphrase() { return this.passPhrase; }
/*    */ 
/*    */ 
/*    */   
/* 24 */   public String getPassword() { return this.password; }
/*    */ 
/*    */ 
/*    */   
/* 28 */   public boolean promptPassphrase(String arg0) { return true; }
/*    */ 
/*    */ 
/*    */   
/* 32 */   public boolean promptPassword(String arg0) { return false; }
/*    */ 
/*    */ 
/*    */   
/* 36 */   public boolean promptYesNo(String arg0) { return true; }
/*    */   
/*    */   public void showMessage(String arg0) {}
/*    */ }

