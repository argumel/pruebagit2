/*    */ package mx.com.televisa.cfdis;
/*    */ 
/*    */ import java.awt.EventQueue;
/*    */ import javax.swing.BoxLayout;
/*    */ import javax.swing.JFrame;
/*    */ import javax.swing.JPanel;
/*    */ import javax.swing.JRadioButton;
/*    */ import javax.swing.border.EmptyBorder;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class encripta
/*    */   extends JFrame
/*    */ {
/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
/*    */   private JPanel contentPane;
/*    */   
/*    */   public static void main(String[] args) {
/* 24 */     EventQueue.invokeLater(new Runnable() {
/*    */           public void run() {
/*    */             try {
/* 27 */               encripta frame = new encripta();
/* 28 */               frame.setVisible(true);
/* 29 */             } catch (Exception e) {
/* 30 */               e.printStackTrace();
/*    */             } 
/*    */           }
/*    */         });
/*    */   }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public encripta() {
/* 40 */     setDefaultCloseOperation(3);
/* 41 */     setBounds(100, 100, 521, 374);
/* 42 */     this.contentPane = new JPanel();
/* 43 */     this.contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
/* 44 */     setContentPane(this.contentPane);
/* 45 */     this.contentPane.setLayout(new BoxLayout(this.contentPane, 0));
/*    */     
/* 47 */     JRadioButton rdbtnCrearNuevo = new JRadioButton("Crear Nuevo");
/* 48 */     rdbtnCrearNuevo.setAlignmentY(0.0F);
/* 49 */     this.contentPane.add(rdbtnCrearNuevo);
/*    */     
/* 51 */     JRadioButton rdbtnNewRadioButton = new JRadioButton("Actualizar Existente");
/* 52 */     rdbtnNewRadioButton.setAlignmentY(0.0F);
/* 53 */     rdbtnNewRadioButton.setAlignmentX(2.0F);
/* 54 */     this.contentPane.add(rdbtnNewRadioButton);
/*    */   }
/*    */ }
