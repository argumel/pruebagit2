/*    */ package mx.com.televisa.cfdis.util;
/*    */ 
/*    */ import java.math.BigInteger;
/*    */ import java.security.MessageDigest;
/*    */ import java.security.NoSuchAlgorithmException;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MD5Crypto
/*    */ {
/*    */   public static String getMD5(String input) {
/*    */     try {
/* 18 */       MessageDigest md = MessageDigest.getInstance("MD5");
/* 19 */       byte[] messageDigest = md.digest(input.getBytes());
/* 20 */       BigInteger number = new BigInteger(1, messageDigest);
/* 21 */       String hashtext = number.toString(16);
/*    */       
/* 23 */       while (hashtext.length() < 32) {
/* 24 */         hashtext = "0" + hashtext;
/*    */       }
/* 26 */       return hashtext;
/*    */     }
/* 28 */     catch (NoSuchAlgorithmException e) {
/* 29 */       throw new RuntimeException(e);
/*    */     } 
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 35 */   public static void main(String[] args) throws NoSuchAlgorithmException { System.out.println(getMD5("Javarmi.com")); }
/*    */ }

