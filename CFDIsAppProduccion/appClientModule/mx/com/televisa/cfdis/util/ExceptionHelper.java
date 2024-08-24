/*    */ package mx.com.televisa.cfdis.util;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExceptionHelper
/*    */ {
/*    */   public static String getStackTraceAsString(Throwable throwable) {
/* 13 */     String trace = String.valueOf(throwable.toString()) + "\n";
/* 14 */     StackTraceElement[] elements = throwable.getStackTrace();
/*    */     
/* 16 */     for (int i = 0; i < elements.length; i++) {
/* 17 */       trace = String.valueOf(trace) + "\tat " + elements[i].toString() + " \n";
/*    */     }
/*    */     
/* 20 */     return trace;
/*    */   }
/*    */ }
