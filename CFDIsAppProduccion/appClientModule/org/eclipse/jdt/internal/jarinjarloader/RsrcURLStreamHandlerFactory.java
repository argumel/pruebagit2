/*    */ package org.eclipse.jdt.internal.jarinjarloader;
/*    */ 
/*    */ import java.net.URLStreamHandler;
/*    */ import java.net.URLStreamHandlerFactory;
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
/*    */ public class RsrcURLStreamHandlerFactory
/*    */   implements URLStreamHandlerFactory
/*    */ {
/*    */   private ClassLoader classLoader;
/*    */   private URLStreamHandlerFactory chainFac;
/*    */   
/* 31 */   public RsrcURLStreamHandlerFactory(ClassLoader cl) { this.classLoader = cl; }
/*    */ 
/*    */   
/*    */   public URLStreamHandler createURLStreamHandler(String protocol) {
/* 35 */     if ("rsrc".equals(protocol))
/* 36 */       return new RsrcURLStreamHandler(this.classLoader); 
/* 37 */     if (this.chainFac != null)
/* 38 */       return this.chainFac.createURLStreamHandler(protocol); 
/* 39 */     return null;
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
/* 51 */   public void setURLStreamHandlerFactory(URLStreamHandlerFactory fac) { this.chainFac = fac; }
/*    */ }

