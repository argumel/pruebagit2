/*     */ package mx.com.televisa.cfdis.data;
/*     */ 
/*     */ import java.sql.CallableStatement;
/*     */ import java.sql.Connection;
/*     */ import java.sql.DriverManager;
/*     */ import java.sql.PreparedStatement;
/*     */ import java.sql.ResultSet;
/*     */ import java.sql.SQLException;
/*     */ import java.sql.Statement;
/*     */ import mx.com.televisa.cfdis.util.LeerProperties;
/*     */ 

/*     */ 
/*     */ public class ConnectionWrapper
/*     */ {
/*     */   private Connection connection;
/*     */   
/*     */   public ConnectionWrapper() throws Exception {
/*  54 */     String lsDriver = LeerProperties.forName;
/*  55 */     String lsUrl = LeerProperties.diverManager;
/*  56 */     String lsUser = LeerProperties.usuario;
/*  57 */     String lsPassword = LeerProperties.password;
/*     */     
/*  59 */     Class.forName(lsDriver).newInstance();
/*  60 */     this.connection = DriverManager.getConnection(lsUrl, lsUser, lsPassword);
/*     */   }

/*     */   public ResultSet executeQuery(String tsSql) throws Exception {
/*  75 */     System.out.println(tsSql);
/*     */     
/*  77 */     return this.connection.createStatement().executeQuery(tsSql);
/*     */   }
 
/*     */   public int executeUpdate(String tsSql) throws Exception {
/*  90 */     System.out.println(tsSql);
/*     */     
/*  92 */     return this.connection.createStatement().executeUpdate(tsSql);
/*     */   }

/*     */   
/*     */   public PreparedStatement prepareStatement(String tsSql) throws SQLException {
/* 100 */     System.out.println(tsSql);
/* 101 */     return this.connection.prepareStatement(tsSql);
/*     */   }
/*     */ 
  
/* 109 */   public Statement createStatement() throws SQLException { return this.connection.createStatement(); }
/*     */ 
 
/*     */   public CallableStatement prepareCall(String tsStoredProcedure, int tiParamNumber) throws SQLException {
/* 119 */     if (tiParamNumber == 0) {
/* 120 */       return this.connection.prepareCall("{CALL " + tsStoredProcedure + "}");
/*     */     }
/*     */     
/* 123 */     StringBuilder lsSb = new StringBuilder();
/* 124 */     for (int i = 1; i <= tiParamNumber; i++) {
/* 125 */       lsSb.append("?,");
/*     */     }
/*     */ 
/*     */     
/* 129 */     String lsParams = lsSb.substring(0, lsSb.length() - 1);
/* 130 */     lsParams = "(" + lsParams + ")";
/*     */     
/* 132 */     System.out.println("{CALL " + tsStoredProcedure + lsParams + 
/* 133 */         "}");
/*     */     
/* 135 */     return this.connection.prepareCall("{CALL " + tsStoredProcedure + lsParams + 
/* 136 */         "}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CallableStatement prepareCallProcedure(String tsStoredProcedure, Object... toParams) throws SQLException {
/* 147 */     StringBuilder lsSb = new StringBuilder();
				byte b; 
				int i; 
				Object[] arrayOfObject = new Object[toParams.length]; 
/* 148 */     for (i = toParams.length, b = 0; b < i; ) { Object param = arrayOfObject[b];
/* 149 */       if (param.equals("?")) {
/* 150 */         lsSb.append("?,");
/*     */       } else {
/* 152 */         lsSb.append("'" + param + "',");
/*     */       } 
/*     */       b++; }
/*     */     
/* 156 */     String lsParams = lsSb.substring(0, lsSb.length() - 1);
/* 157 */     lsParams = "(" + lsParams + ")";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 162 */     System.out.println("{CALL " + tsStoredProcedure + lsParams + 
/* 163 */         "}");
/*     */     
/* 165 */     return this.connection.prepareCall("{CALL " + tsStoredProcedure + lsParams + 
/* 166 */         "}");
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CallableStatement prepareCallFunction(String tsStoredProcedure, Object... toParams) throws SQLException {
/* 176 */     StringBuilder lsSb = new StringBuilder();
				byte b; 
				int i; 
				Object[] arrayOfObject = new Object[toParams.length];
/* 177 */     for (i = toParams.length, b = 0; b < i; ) { Object param = arrayOfObject[b];
/* 178 */       lsSb.append("'" + param + "',");
/*     */       b++; }
/*     */     
/* 181 */     String lsParams = lsSb.substring(0, lsSb.length() - 1);
/* 182 */     lsParams = "(" + lsParams + ")";
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 189 */     String lsCallableQuery = 
/* 190 */       "{ call ? := " + tsStoredProcedure + lsParams + "}";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 195 */     System.out.println(lsCallableQuery);
/* 196 */     return this.connection.prepareCall(lsCallableQuery);
/*     */   }
/*     */ 

/*     */   
/*     */   public int getSecuenceNextValue(String tsSecuencia) throws Exception {
/* 209 */     String lsQuery = "SELECT " + tsSecuencia + ".nextval AS ID FROM DUAL";
/* 210 */     ResultSet loSet = executeQuery(lsQuery);
/* 211 */     loSet.next();
/* 212 */     return loSet.getInt("ID") + 1;
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 220 */   public Connection getConnection() { return this.connection; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 230 */   public void begin() throws Exception { this.connection.setAutoCommit(false); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 239 */   public void commit() throws Exception { this.connection.commit(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 248 */   public void rollback() throws Exception { this.connection.rollback(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void close() throws Exception {
/*     */     try {
/* 257 */       this.connection.close();
/* 258 */     } catch (Exception e) {
/* 259 */       this.connection = null;
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public static void closeAll(Object... toCloseableObjects) {
/*     */     byte b;
/*     */     int i;
/*     */     Object[] arrayOfObject = new Object[toCloseableObjects.length];
/* 268 */     for (i  = toCloseableObjects.length, b = 0; b < i; ) { Object loCloseable = arrayOfObject[b];
/*     */       
/* 270 */       if (loCloseable != null) {
/*     */         
/* 272 */         if (loCloseable instanceof ResultSet) {
/*     */           try {
/* 274 */             ((ResultSet)loCloseable).close();
/* 275 */           } catch (Exception exception) {}
/*     */         }
/*     */ 
/*     */         
/* 279 */         if (loCloseable instanceof Statement) {
/*     */           try {
/* 281 */             ((Statement)loCloseable).close();
/* 282 */           } catch (Exception exception) {}
/*     */         }
/*     */ 
/*     */         
/* 286 */         if (loCloseable instanceof Connection) {
/*     */           try {
/* 288 */             ((Connection)loCloseable).close();
/* 289 */           } catch (Exception exception) {}
/*     */         }
/*     */ 
/*     */         
/* 293 */         if (loCloseable instanceof ConnectionWrapper)
/*     */           try {
/* 295 */             ((ConnectionWrapper)loCloseable).close();
/* 296 */           } catch (Exception exception) {} 
/*     */       } 
/*     */       b++; }
/*     */   
/*     */   }
/*     */ }

