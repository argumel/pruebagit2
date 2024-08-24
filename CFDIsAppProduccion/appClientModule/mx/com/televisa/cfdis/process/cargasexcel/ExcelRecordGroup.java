/*    */ package mx.com.televisa.cfdis.process.cargasexcel;
/*    */ 
/*    */ import java.util.ArrayList;
/*    */ import java.util.Iterator;
/*    */ 
/*    */ public class ExcelRecordGroup
/*    */   extends Object
/*    */   implements Iterable<ExcelRecord>
/*    */ {
/*    */   private String groupId;
/*    */   private ArrayList<ExcelRecord> registros;
/*    */   
/*    */   public ExcelRecordGroup(String groupId) {
/* 14 */     setGroupId(groupId);
/* 15 */     setRegistros(new ArrayList());
/*    */   }
/*    */ 
/*    */ 
/*    */   
/* 20 */   public boolean equals(Object other) { return getGroupId().equals(((ExcelRecordGroup)other).getGroupId()); }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 25 */   public String getGroupId() { return this.groupId; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 30 */   public void setGroupId(String groupId) { this.groupId = groupId; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 35 */   public ArrayList<ExcelRecord> getRegistros() { return this.registros; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 40 */   public void setRegistros(ArrayList<ExcelRecord> registros) { this.registros = registros; }
/*    */ 
/*    */ 
/*    */ 
/*    */   
/* 45 */   public Iterator<ExcelRecord> iterator() { return this.registros.iterator(); }
/*    */ }
