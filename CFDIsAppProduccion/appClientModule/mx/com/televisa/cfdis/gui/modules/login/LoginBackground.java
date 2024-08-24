/*    */ package mx.com.televisa.cfdis.gui.modules.login;
/*    */ 
/*    */ import java.awt.Dimension;
/*    */ import java.awt.Graphics;
/*    */ import javax.swing.ImageIcon;
/*    */ import javax.swing.JPanel;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class LoginBackground
/*    */   extends JPanel
/*    */ {
/*    */   public LoginBackground() {
/*    */     try {
/* 17 */       jbInit();
/* 18 */     } catch (Exception e) {
/* 19 */       e.printStackTrace();
/*    */     } 
/*    */   }
/*    */   
/*    */   private void jbInit() {
/* 24 */     setSize(new Dimension(639, 483));
/* 25 */     setLayout(null);
/*    */   }
/*    */   
/*    */   public void paintComponent(Graphics g) {
/* 29 */     Dimension tamanio = getSize();
/* 30 */     ImageIcon fondo = new ImageIcon(getClass().getResource("FondoLogin.jpg"));
/* 31 */     g.drawImage(fondo.getImage(), 0, 0, tamanio.width, tamanio.height, null);
/* 32 */     setOpaque(false);
/* 33 */     super.paintComponent(g);
/*    */   }
/*    */ 
/*    */   
/* 37 */   public JPanel getPanel() { return this; }
/*    */ }

