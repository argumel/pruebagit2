/*    */ package mx.com.televisa.cfdis.process.cargasexcel;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */ public class ExcelRecord
/*    */   extends Object
/*    */   implements Iterable<String>
/*    */ {
/*    */   private ArrayList<String> campos;
/*    */   
/* 15 */   public ExcelRecord() { setCampos(new ArrayList()); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 21 */   public void addCampo(String s) { getCampos().add(s); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 27 */   public Iterator<String> iterator() { return getCampos().iterator(); }
/*    */ 
/*    */ 
/*    */   
/* 31 */   public ArrayList<String> getCampos() { return this.campos; }
/*    */ 
/*    */ 
/*    */   
/* 35 */   public void setCampos(ArrayList<String> campos) { this.campos = campos; }
/*    */ }

