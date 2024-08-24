/*    */ package mx.com.televisa.cfdis.process.cargasexcel;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class ExcelRecordLines  extends Object implements Iterable<String> {

	     private ArrayList<String> campos;
/*    */   
/* 11 */   public ExcelRecordLines() { setCampos(new ArrayList()); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 17 */   public void addCampo(String s) { getCampos().add(s); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 23 */   public Iterator<String> iterator() { return getCampos().iterator(); }
/*    */ 
/*    */ 
/*    */   
/* 27 */   public ArrayList<String> getCampos() { return this.campos; }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public void setCampos(ArrayList<String> campos) { this.campos = campos; }
/*    */ }

