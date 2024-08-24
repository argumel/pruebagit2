/*    */ package org.eclipse.jdt.internal.jarinjarloader;
/*    */ 
/*    */ import java.io.IOException;
/*    */ import java.net.URL;
/*    */ import java.net.URLConnection;
/*    */ import java.net.URLStreamHandler;
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
/*    */ 
/*    */ 
/*    */ public class RsrcURLStreamHandler
/*    */   extends URLStreamHandler
/*    */ {
/*    */   private ClassLoader classLoader;
/*    */   
/* 34 */   public RsrcURLStreamHandler(ClassLoader classLoader) { this.classLoader = classLoader; }
/*    */ 
/*    */ 
/*    */   
/* 38 */   protected URLConnection openConnection(URL u) throws IOException { return new RsrcURLConnection(u, this.classLoader); }
/*    */ 
/*    */   
/*    */   protected void parseURL(URL url, String spec, int start, int limit) {
/*    */     String file;
/* 43 */     if (spec.startsWith("rsrc:")) {
/* 44 */       file = spec.substring(5);
/* 45 */     } else if (url.getFile().equals("./")) {
/* 46 */       file = spec;
/* 47 */     } else if (url.getFile().endsWith("/")) {
/* 48 */       file = String.valueOf(url.getFile()) + spec;
/*    */     } else {
/* 50 */       file = spec;
/* 51 */     }  setURL(url, "rsrc", "", -1, null, null, file, null, null);
/*    */   }
/*    */ }

