/*    */ package mx.com.televisa.cfdis.gui.main;
/*    */ 
/*    */ import java.awt.Graphics;
/*    */ import java.awt.Image;
/*    */ import javax.swing.JDesktopPane;
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
/*    */ public class DesktopBackground
/*    */   extends JDesktopPane
/*    */ {
/*    */   private Image pimagen;
/*    */   private Image imagenFondo;
/*    */   
/*    */   public DesktopBackground() {}
/*    */   
/*    */   public DesktopBackground(Image imagenInicial) {
/* 37 */     if (imagenInicial != null)
/*    */     {
/*    */       
/* 40 */       this.pimagen = imagenInicial;
/*    */     }
/*    */   }
/*    */   
/*    */   public DesktopBackground(Image imagenInicial, Image pImagenFondo) {
/* 45 */     if (imagenInicial != null)
/*    */     {
/*    */       
/* 48 */       this.pimagen = imagenInicial;
/*    */     }
/* 50 */     this.imagenFondo = pImagenFondo;
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public void paint(Graphics g) {
/* 59 */     if (this.pimagen != null) {
/*    */       
/* 61 */       g.drawImage(this.imagenFondo, getX(), getY(), getWidth(), getHeight(), this);
/* 62 */       g.drawImage(this.pimagen, 400, 150, 590, 400, this);
/* 63 */       setOpaque(false);
/*    */     } else {
/* 65 */       setOpaque(true);
/*    */     } 
/*    */     
/* 68 */     super.paint(g);
/*    */   }
/*    */ }
