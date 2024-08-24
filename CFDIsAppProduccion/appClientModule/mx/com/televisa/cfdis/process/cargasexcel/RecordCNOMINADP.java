/*    */ package mx.com.televisa.cfdis.process.cargasexcel;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class RecordCNOMINADP
/*    */ {
/*    */   private ArrayList<String> campos;
/*    */   
/* 10 */   public RecordCNOMINADP() { setCampos(new ArrayList()); }
/*    */ 
/*    */ 
/*    */   
/* 14 */   public void addCampo(String s) { getCampos().add(s); }
/*    */ 
/*    */ 
/*    */   
/* 18 */   public Iterator<String> iterator() { return getCampos().iterator(); }
/*    */ 
/*    */ 
/*    */   
/* 22 */   public ArrayList<String> getCampos() { return this.campos; }
/*    */ 
/*    */ 
/*    */   
/* 26 */   public void setCampos(ArrayList<String> campos) { this.campos = campos; }
/*    */ }
