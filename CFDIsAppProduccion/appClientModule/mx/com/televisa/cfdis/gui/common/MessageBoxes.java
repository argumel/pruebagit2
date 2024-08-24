/*    */ package mx.com.televisa.cfdis.gui.common;
/*    */ 
/*    */ import java.awt.Component;
import java.util.ArrayList;

/*    */ import javax.swing.Icon;
import javax.swing.JList;
/*    */ import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class MessageBoxes
/*    */ {
/* 17 */   public static void message(ArrayList<String> strMessage) { 
	
			JScrollPane scroll = new JScrollPane(new JList(strMessage.toArray()));
			
			JOptionPane.showMessageDialog(null, scroll); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public static void warning(Object strMessage) { JOptionPane.showMessageDialog(null, strMessage, "", 2); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/*    */   public static void error(Exception exception) {
/* 36 */     Component parentComponent = null;
/* 37 */     String title = "Error";
/* 38 */     String message = "Ocurrio un error:\n" + exception.toString() + "\n¿Desea ver el detalle del error?";
/*    */ 
/*    */ 
/*    */     
/* 42 */     int optionType = 0;
/* 43 */     int messageType = 0;
/* 44 */     Icon icon = null;
/*    */     
/* 46 */     Object[] options = { "Si (Ver Detalle)", "No" };
/* 47 */     Object initialValue = "Si (Ver Detalle)";
/*    */ 
/*    */     
/* 50 */     int seleccion = JOptionPane.showOptionDialog(parentComponent, message, title, 
/* 51 */         optionType, messageType, icon, options, initialValue);
/*    */     
/* 53 */     if (seleccion == 0) {
/*    */       
/* 55 */       StaticContext.lastException = exception;
/* 56 */       (new ErrorFrm()).setVisible(true);
/*    */     } 
/*    */   }
/*    */ }

