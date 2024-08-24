/*    */ package mx.com.televisa.cfdis.util;
/*    */ 
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.spec.IvParameterSpec;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ import org.apache.commons.codec.binary.Base64;
/*    */ 
/*    */ 
				
/*    */ 
/*    */ public class AESSencillo
/*    */ {
/* 13 */   String key = "RETENASIMILABLES";
/* 14 */   String initVector = "RandomInitVector";
/*    */   
/*    */   public String encrypt(String value) {
/*    */     try {
/* 18 */       IvParameterSpec iv = new IvParameterSpec(this.initVector.getBytes("UTF-8"));
/* 19 */       SecretKeySpec skeySpec = new SecretKeySpec(this.key.getBytes("UTF-8"), "AES");
/*    */       
/* 21 */       Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
/* 22 */       cipher.init(1, skeySpec, iv);
/*    */       
/* 24 */       byte[] encrypted = cipher.doFinal(value.getBytes());
/* 25 */       System.out.println("encrypted string: " + 
/* 26 */           Base64.encodeBase64String(encrypted));
/*    */       
/* 28 */       return Base64.encodeBase64String(encrypted);
/* 29 */     } catch (Exception ex) {
/* 30 */       ex.printStackTrace();
/*    */ 
/*    */       
/* 33 */       return null;
/*    */     } 
/*    */   }
/*    */   public String decrypt(String encrypted) {
/*    */     try {
/* 38 */       IvParameterSpec iv = new IvParameterSpec(this.initVector.getBytes("UTF-8"));
/* 39 */       SecretKeySpec skeySpec = new SecretKeySpec(this.key.getBytes("UTF-8"), "AES");
/*    */       
/* 41 */       Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5PADDING");
/* 42 */       cipher.init(2, skeySpec, iv);
/*    */       
/* 44 */       byte[] original = cipher.doFinal(Base64.decodeBase64(encrypted));
/*    */       
/* 46 */       return new String(original);
/* 47 */     } catch (Exception ex) {
/* 48 */       ex.printStackTrace();
/*    */ 
/*    */       
/* 51 */       return null;
/*    */     } 
/*    */   }


				public static void main(String args[]) {
					
					AESSencillo o = new AESSencillo();
					System.out.println(o.encrypt("p0sbler_lB43cK5nDo")); 
					//System.out.println(o.decrypt("8WZgSbbZIxklL2I2H7tibcEP09S+Mho6CkVxS3C5HTE=")); 
					
				}
/*    */ }

