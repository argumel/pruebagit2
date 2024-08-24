/*    */ package mx.com.televisa.cfdis.gui.common;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Rectangle;
/*    */ import java.awt.Toolkit;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JScrollPane;
/*    */ import javax.swing.JTextArea;
/*    */ import mx.com.televisa.cfdis.util.ExceptionHelper;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ErrorFrm
/*    */   extends JFrame
/*    */ {
/*    */   Dimension screenSize;
/*    */   Dimension windowSize;
/* 22 */   private JScrollPane jScrollPane1 = new JScrollPane();
/* 23 */   private JTextArea jTextArea1 = new JTextArea();
/*    */ 
/*    */ 
/*    */   
/*    */   public ErrorFrm() {
/*    */     try {
/* 29 */       jbInit();
/* 30 */       customInit();
/*    */     }
/* 32 */     catch (Exception e) {
/* 33 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void jbInit() {
/* 38 */     setSize(new Dimension(747, 384));
/* 39 */     getContentPane().setLayout(null);
/*    */ 
/*    */     
/* 42 */     setTitle("Detalle del Error");
/* 43 */     this.jScrollPane1.setBounds(new Rectangle(5, 5, 735, 350));
/* 44 */     this.jTextArea1.setEditable(false);
/* 45 */     this.jScrollPane1.getViewport().add(this.jTextArea1, null);
/* 46 */     getContentPane().add(this.jScrollPane1, null);
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   private void customInit() {
/* 54 */     this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 55 */     this.windowSize = getSize();
/* 56 */     int windowX = Math.max(0, (this.screenSize.width - this.windowSize.width) / 2);
/* 57 */     int windowY = Math.max(0, (this.screenSize.height - this.windowSize.height) / 2);
/* 58 */     setLocation(windowX, windowY);
/*    */     
/* 60 */     setVisible(true);
/*    */     
/* 62 */     Exception ex = StaticContext.lastException;
/* 63 */     String errorDetail = ExceptionHelper.getStackTraceAsString(ex);
/*    */ 
/*    */     
/* 66 */     this.jTextArea1.setText(errorDetail);
/* 67 */     this.jTextArea1.setCaretPosition(0);
/*    */   }
/*    */ }

