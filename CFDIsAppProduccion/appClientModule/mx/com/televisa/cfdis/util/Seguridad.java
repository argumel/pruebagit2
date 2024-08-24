/*    */ package mx.com.televisa.cfdis.util;
/*    */ 
/*    */ import java.security.AlgorithmParameters;
/*    */ import java.security.spec.KeySpec;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.SecretKeyFactory;
/*    */ import javax.crypto.spec.IvParameterSpec;
/*    */ import javax.crypto.spec.PBEKeySpec;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ import javax.xml.bind.DatatypeConverter;
/*    */ 
/*    */ public class Seguridad
/*    */ {
/*    */   Cipher dcipher;
/* 16 */   byte[] salt = (new String("Kaz2017")).getBytes("UTF-8");
/*    */   
/* 18 */   int iterationCount = 2;
/* 19 */   int keyStrength = 256;
/*    */   SecretKey key;
/*    */   byte[] iv;
/*    */   
/*    */   Seguridad(String passPhrase) throws Exception {
/* 24 */     SecretKeyFactory factory = SecretKeyFactory.getInstance("PBKDF2WithHmacSHA1");
/*    */     
/* 26 */     KeySpec spec = new PBEKeySpec(passPhrase.toCharArray(), this.salt, this.iterationCount, this.keyStrength);
/* 27 */     SecretKey tmp = factory.generateSecret(spec);
/* 28 */     this.key = new SecretKeySpec(tmp.getEncoded(), "AES");
/* 29 */     this.dcipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
/*    */   }
/*    */ 
/*    */   
/*    */   public String encrypt(String data) throws Exception {
/* 34 */     this.dcipher.init(1, this.key);
/* 35 */     AlgorithmParameters params = this.dcipher.getParameters();
/*    */ 
/*    */     
/* 38 */     this.iv = ((IvParameterSpec)params.getParameterSpec(IvParameterSpec.class)).getIV();
/* 39 */     byte[] utf8EncryptedData = this.dcipher.doFinal(data.getBytes());
/* 40 */     String base64EncryptedData = DatatypeConverter.printBase64Binary(utf8EncryptedData);
/*    */     
/* 42 */     System.out.println("\nIV " + DatatypeConverter.printBase64Binary(this.iv));
/* 43 */     System.out.println("\nEncrypted Data " + base64EncryptedData);
/* 44 */     return base64EncryptedData;
/*    */   }
/*    */   
/*    */   public String decrypt(String base64EncryptedData) throws Exception {
/* 48 */     this.dcipher.init(2, this.key, new IvParameterSpec(this.iv));
/* 49 */     byte[] decryptedData = DatatypeConverter.parseBase64Binary(base64EncryptedData);
/* 50 */     byte[] utf8 = this.dcipher.doFinal(decryptedData);
/* 51 */     return new String(utf8, "UTF8");
/*    */   }
/*    */ }

