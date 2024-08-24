/*    */ package mx.com.televisa.cfdis.process.cargasexcel;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ 
/*    */ public class RecordCABFAC
/*    */ {
/*    */   private ArrayList<String> campos;
/*    */   
/* 11 */   public RecordCABFAC() { setCampos(new ArrayList()); }
/*    */ 
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 17 */   public void addCampo(String s) { getCampos().add(s); }
/*    */ 
/*    */ 
/*    */   
/* 21 */   public Iterator<String> iterator() { return getCampos().iterator(); }
/*    */ 
/*    */ 
/*    */   
/* 25 */   public ArrayList<String> getCampos() { return this.campos; }
/*    */ 
/*    */ 
/*    */   
/* 29 */   public void setCampos(ArrayList<String> campos) { this.campos = campos; }
/*    */ }
