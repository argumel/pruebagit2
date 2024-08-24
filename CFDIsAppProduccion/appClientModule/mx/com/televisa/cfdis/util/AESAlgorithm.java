/*    */ package mx.com.televisa.cfdis.util;
/*    */ 
/*    */ import java.security.SecureRandom;
/*    */ import javax.crypto.BadPaddingException;
/*    */ import javax.crypto.Cipher;
/*    */ import javax.crypto.IllegalBlockSizeException;
/*    */ import javax.crypto.SecretKey;
/*    */ import javax.crypto.SecretKeyFactory;
/*    */ import javax.crypto.spec.IvParameterSpec;
/*    */ import javax.crypto.spec.PBEKeySpec;
/*    */ import javax.crypto.spec.SecretKeySpec;
/*    */ import org.apache.commons.codec.binary.Base64;
/*    */ 
/*    */ 
/*    */ 
/*    */ public class AESAlgorithm
/*    */ {
/*    */   private static final String TOKEN = "KAZCFDIRETENCIONESASIMILABLES";
/* 19 */   byte[] salt = { 75, 97, 122, 50, 48, 49, 55 };
/* 20 */   private int pwdIterations = 2;
/* 21 */   private int keySize = 256;
/*    */   private byte[] ivBytes;
/* 23 */   private String keyAlgorithm = "AES";
/* 24 */   private String encryptAlgorithm = "AES/CBC/PKCS5Padding";
/*    */   
/* 26 */   private String secretKeyFactoryAlgorithm = "PBKDF2WithHmacSHA1";
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private String getSalt() {
/* 33 */     SecureRandom random = new SecureRandom();
/* 34 */     byte[] bytes = new byte[20];
/* 35 */     random.nextBytes(bytes);
/* 36 */     return new String(bytes);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String encyrpt(String plainText) throws Exception {
/* 49 */     byte[] saltBytes = this.salt;
/* 50 */     SecretKeyFactory skf = SecretKeyFactory.getInstance(this.secretKeyFactoryAlgorithm);
/* 51 */     PBEKeySpec spec = new PBEKeySpec("KAZCFDIRETENCIONESASIMILABLES".toCharArray(), saltBytes, this.pwdIterations, this.keySize);
/* 52 */     SecretKey secretKey = skf.generateSecret(spec);
/* 53 */     SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), this.keyAlgorithm);
/*    */ 
/*    */     
/* 56 */     Cipher cipher = Cipher.getInstance(this.encryptAlgorithm);
/* 57 */     cipher.init(1, key);
/*    */ 
/*    */     
/* 60 */     this.ivBytes = ((IvParameterSpec)cipher.getParameters().getParameterSpec(IvParameterSpec.class)).getIV();
/* 61 */     byte[] encryptedText = cipher.doFinal(plainText.getBytes("UTF-8"));
/* 62 */     return (new Base64()).encodeAsString(encryptedText);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public String decrypt(String encryptText) throws Exception {
/* 73 */     byte[] saltBytes = this.salt;
/* 74 */     byte[] encryptTextBytes = (new Base64()).decode(encryptText);
/*    */     
/* 76 */     SecretKeyFactory skf = SecretKeyFactory.getInstance(this.secretKeyFactoryAlgorithm);
/* 77 */     PBEKeySpec spec = new PBEKeySpec("KAZCFDIRETENCIONESASIMILABLES".toCharArray(), saltBytes, this.pwdIterations, this.keySize);
/* 78 */     SecretKey secretKey = skf.generateSecret(spec);
/* 79 */     SecretKeySpec key = new SecretKeySpec(secretKey.getEncoded(), this.keyAlgorithm);
/*    */ 
/*    */     
/* 82 */     Cipher cipher = Cipher.getInstance(this.encryptAlgorithm);
/* 83 */     cipher.init(2, key, new IvParameterSpec(this.ivBytes));
/*    */     
/* 85 */     byte[] decyrptTextBytes = null;
/*    */     try {
/* 87 */       decyrptTextBytes = cipher.doFinal(encryptTextBytes);
/* 88 */     } catch (IllegalBlockSizeException e) {
/*    */       
/* 90 */       e.printStackTrace();
/* 91 */     } catch (BadPaddingException e) {
/* 92 */       e.printStackTrace();
/*    */     } 
/* 94 */     return new String(decyrptTextBytes);
/*    */   }
/*    */ }

