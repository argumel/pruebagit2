/*     */ package mx.com.televisa.cfdis.gui.modules.adminusers;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.MouseAdapter;
/*     */ import java.awt.event.MouseEvent;
/*     */ import java.util.ArrayList;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JScrollPane;
/*     */ import javax.swing.JSeparator;
/*     */ import javax.swing.JTable;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.JViewport;
/*     */ import javax.swing.table.DefaultTableCellRenderer;
/*     */ import javax.swing.table.DefaultTableModel;
/*     */ import javax.swing.table.TableColumn;
/*     */ import javax.swing.table.TableColumnModel;
/*     */ import mx.com.televisa.cfdis.gui.main.MainDesktop;
/*     */ import mx.com.televisa.cfdis.model.DAO.LoginDAO;
/*     */ import mx.com.televisa.cfdis.model.DTO.LoginDTO;
/*     */ import mx.com.televisa.cfdis.model.DTO.RecordDTO;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ public class AdminUserFrm
/*     */   extends JInternalFrame
/*     */ {
/*  51 */   private JPanel PnlSearch = new JPanel();
/*  52 */   private JLabel LblSearch = new JLabel();
/*  53 */   private JTextField TxtSearch = new JTextField();
/*  54 */   private JButton BtnSearch = new JButton();
/*  55 */   private JButton BtnAdd = new JButton();
/*  56 */   private JSeparator jSeparator1 = new JSeparator();
/*  57 */   private JPanel PnlTable = new JPanel();
/*  58 */   private JButton BtnExit = new JButton();
/*  59 */   private JScrollPane jScrollPane1 = new JScrollPane();
/*  60 */   private JTable TblLogin = new JTable();
/*     */   Dimension screenSize;
/*     */   Dimension windowSize;
/*  63 */   private JLabel jLabel1 = new JLabel();
/*     */   
/*     */   public AdminUserFrm() {
/*     */     try {
/*  67 */       System.out.println("Entro: constructor AdminNotas");
/*  68 */       jbInit();
/*  69 */       setDataTable();
/*     */ 
/*     */       
/*  72 */       this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*  73 */       this.windowSize = getSize();
/*  74 */       int windowX = Math.max(0, (this.screenSize.width - this.windowSize.width) / 3);
/*  75 */       int windowY = Math.max(0, (this.screenSize.height - this.windowSize.height) / 3);
/*  76 */       setLocation(windowX, windowY);
/*  77 */       setAnchoAlineacion();
/*     */     }
/*  79 */     catch (Exception e) {
/*  80 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void jbInit() {
/*  85 */     getContentPane().setLayout(null);
/*  86 */     setSize(new Dimension(579, 472));
/*  87 */     setResizable(false);
/*  88 */     setMaximizable(false);
/*  89 */     setClosable(true);
/*  90 */     setTitle("Administración de Usuarios");
/*     */     
/*  92 */     this.PnlSearch.setBounds(new Rectangle(0, 0, 580, 55));
/*  93 */     this.PnlSearch.setLayout(null);
/*  94 */     this.LblSearch.setText("Usuario:");
/*  95 */     this.LblSearch.setBounds(new Rectangle(25, 20, 60, 15));
/*  96 */     this.LblSearch.setFont(new Font("Tahoma", 1, 11));
/*  97 */     this.TxtSearch.setBounds(new Rectangle(95, 15, 175, 20));
/*  98 */     this.TxtSearch.setSize(new Dimension(175, 25));
/*     */     
/* 100 */     this.TxtSearch.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 104 */             AdminUserFrm.this.TxtSearch_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 108 */     this.BtnSearch.setText("Buscar");
/* 109 */     this.BtnSearch.setBounds(new Rectangle(300, 15, 95, 20));
/* 110 */     this.BtnSearch.setSize(new Dimension(95, 25));
/*     */     
/* 112 */     this.BtnSearch.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 116 */             AdminUserFrm.this.BtnBuscar_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 120 */     this.BtnAdd.setText("Agregar");
/* 121 */     this.BtnAdd.setBounds(new Rectangle(450, 15, 95, 20));
/* 122 */     this.BtnAdd.setSize(new Dimension(95, 25));
/*     */     
/* 124 */     this.BtnAdd.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 128 */             AdminUserFrm.this.BtnAdd_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 132 */     this.jSeparator1.setBounds(new Rectangle(0, 55, 575, 2));
/* 133 */     this.PnlTable.setBounds(new Rectangle(0, 60, 580, 335));
/* 134 */     this.PnlTable.setLayout(null);
/* 135 */     this.BtnExit.setText("Salir");
/* 136 */     this.BtnExit.setBounds(new Rectangle(455, 405, 90, 25));
/* 137 */     this.BtnExit.setSize(new Dimension(90, 25));
/*     */     
/* 139 */     this.BtnExit.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 143 */             AdminUserFrm.this.BtnExit_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 147 */     this.jScrollPane1.setBounds(new Rectangle(15, 45, 530, 265));
/* 148 */     this.TblLogin.setLayout(null);
/*     */     
/* 150 */     this.TblLogin.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e)
/*     */           {
/* 154 */             AdminUserFrm.this.TblLogin_mouseClicked(e);
/*     */           }
/*     */         });
/*     */     
/* 158 */     this.jLabel1.setBounds(new Rectangle(80, 10, 375, 15));
/* 159 */     this.jLabel1.setText("Hacer doble clic sobre registro para Modificar ó Eliminar");
/* 160 */     this.jLabel1.setFont(new Font("Tahoma", 1, 12));
/* 161 */     this.jLabel1.setForeground(new Color(255, 132, 0));
/* 162 */     this.PnlSearch.add(this.BtnAdd, null);
/* 163 */     this.PnlSearch.add(this.BtnSearch, null);
/* 164 */     this.PnlSearch.add(this.TxtSearch, null);
/* 165 */     this.PnlSearch.add(this.LblSearch, null);
/* 166 */     this.jScrollPane1.getViewport().add(this.TblLogin, null);
/* 167 */     this.PnlTable.add(this.jLabel1, null);
/* 168 */     this.PnlTable.add(this.jScrollPane1, null);
/* 169 */     getContentPane().add(this.BtnExit, null);
/* 170 */     getContentPane().add(this.PnlTable, null);
/* 171 */     getContentPane().add(this.jSeparator1, null);
/* 172 */     getContentPane().add(this.PnlSearch, null);
/*     */ 
/*     */     
/* 175 */     setClosable(true);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setDataTable() {
/* 194 */     LoginDAO luLoginDAO = new LoginDAO();
/*     */ 
/*     */     
/* 197 */     ArrayList<RecordDTO> alRecord = new ArrayList<RecordDTO>();
/* 198 */     try {
	alRecord = luLoginDAO.bringSimpleQuery();
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
/*     */     
/* 200 */     String[] lscolumnNames = { "ID", "Nombre de Usuario", "Rol" };
/* 201 */     Object[][] loRecord = new Object[alRecord.size()][3];
/*     */     
/* 203 */     for (int liRenglon = 0; liRenglon < alRecord.size(); liRenglon++) {
/* 204 */       int liColumna = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 214 */       loRecord[liRenglon][liColumna] = ((RecordDTO)alRecord.get(liRenglon)).getPsIdUser();
/* 215 */       loRecord[liRenglon][liColumna + 1] = ((RecordDTO)alRecord.get(liRenglon)).getPsUser();
/* 216 */       loRecord[liRenglon][liColumna + 2] = ((RecordDTO)alRecord.get(liRenglon)).getPsRolUser();
/*     */     } 
/*     */ 
/*     */     
/* 220 */     this.TblLogin.setModel(new DefaultTableModel(loRecord, lscolumnNames)
/*     */         {
/*     */           Class[] tipoColumn;
/*     */ 
/*     */           
/*     */           boolean[] editColumn;
/*     */ 
/*     */ 
/*     */           
/* 229 */           public Class getColumnClass(int pIndColumn) { return this.tipoColumn[pIndColumn]; }
/*     */ 
/*     */ 
/*     */           
/* 233 */           public boolean isCellEditable(int pIndFila, int pIndColum) { return this.editColumn[pIndColum]; }
/*     */         });
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void setAnchoAlineacion() {
/* 241 */     JViewport scroll = (JViewport)this.TblLogin.getParent();
/* 242 */     int ancho = scroll.getWidth();
/* 243 */     int anchoColumna = 0;
/* 244 */     TableColumnModel modeloColumna = this.TblLogin.getColumnModel();
/* 245 */     TableColumn columnaTabla = null;
/*     */     
/* 247 */     for (int i = 0; i < this.TblLogin.getColumnCount(); i++) {
/* 248 */       columnaTabla = modeloColumna.getColumn(i);
/* 249 */       DefaultTableCellRenderer tcr = new DefaultTableCellRenderer();
/* 250 */       switch (i) {
/*     */         case 0:
/* 252 */           anchoColumna = 0 * ancho / 100;
/* 253 */           tcr.setHorizontalAlignment(2);
/* 254 */           this.TblLogin.getColumnModel().getColumn(i).setCellRenderer(tcr);
/*     */           break;
/*     */       } 
/*     */     
/*     */     } 
/* 259 */     columnaTabla.setPreferredWidth(anchoColumna);
/* 260 */     TableColumn column = null;
/* 261 */     column = this.TblLogin.getColumnModel().getColumn(0);
/* 262 */     column.setMaxWidth(0);
/* 263 */     column.setMinWidth(0);
/* 264 */     column.setWidth(0);
/*     */   }
/*     */ 
/*     */   
/*     */   private void searchUser() {
/* 269 */     String lsSearch = this.TxtSearch.getText();
/* 270 */     LoginDAO luLoginDAO = new LoginDAO();
/* 271 */     luLoginDAO.searchRecord(lsSearch);
/*     */ 
/*     */ 
/*     */     
/* 275 */     ArrayList<RecordDTO> alRecord = new ArrayList<RecordDTO>();
/* 276 */     alRecord = luLoginDAO.searchRecord(lsSearch);
/*     */     
/* 278 */     String[] lscolumnNames = { "ID", "Nombre de Usuario", "Rol" };
/* 279 */     Object[][] loRecord = new Object[alRecord.size()][4];
/*     */     
/* 281 */     for (int liRenglon = 0; liRenglon < alRecord.size(); liRenglon++) {
/* 282 */       int liColumna = 0;
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 290 */       loRecord[liRenglon][liColumna] = ((RecordDTO)alRecord.get(liRenglon)).getPsIdUser();
/* 291 */       loRecord[liRenglon][liColumna + 1] = ((RecordDTO)alRecord.get(liRenglon)).getPsUser();
/* 292 */       loRecord[liRenglon][liColumna + 2] = ((RecordDTO)alRecord.get(liRenglon)).getPsRolUser();
/*     */     } 
/*     */     
/* 295 */     this.TblLogin.setModel(new DefaultTableModel(loRecord, lscolumnNames)
/*     */         {
/*     */           Class[] tipoColumn;
/*     */ 
/*     */           
/*     */           boolean[] editColumn;
/*     */ 
/*     */           
/* 303 */           public Class getColumnClass(int pIndColumn) { return this.tipoColumn[pIndColumn]; }
/*     */ 
/*     */           
/*     */           public boolean isCellEditable(int pIndFila, int pIndColum) {
/* 307 */             return this.editColumn[pIndColum];
/*     */           }
/*     */         });
/*     */     
/* 311 */     setAnchoAlineacion();
/*     */   }
/*     */ 
/*     */   
/* 315 */   private void BtnExit_actionPerformed(ActionEvent e) { setVisible(false); }
/*     */ 
/*     */ 
/*     */   
/* 319 */   private void BtnBuscar_actionPerformed(ActionEvent e) { searchUser(); }
/*     */ 
/*     */ 
/*     */   
/* 323 */   private void BtnAdd_actionPerformed(ActionEvent e) { MainDesktop.manejoVentanas("createFrm"); }
/*     */ 
/*     */ 
/*     */   
/*     */   private void TblLogin_mouseClicked(MouseEvent pMouseEvent) {
/* 328 */     if (pMouseEvent.getClickCount() != 2) {
/*     */       return;
/*     */     }
/* 331 */     LoginDTO luLoginDTO = new LoginDTO();
/*     */     
/* 333 */     String sId = this.TblLogin.getValueAt(this.TblLogin.getSelectedRow(), 0).toString();
/* 334 */     String sUsr = this.TblLogin.getValueAt(this.TblLogin.getSelectedRow(), 1).toString();
/*     */     
/* 336 */     String sPassword = "*****";
/* 337 */     String sRol = this.TblLogin.getValueAt(this.TblLogin.getSelectedRow(), 2).toString();
/*     */     
/* 339 */     int iId = Integer.parseInt(sId);
/*     */     
/* 341 */     luLoginDTO.setPiIdUser(iId);
/* 342 */     luLoginDTO.setPsUser(sUsr);
/* 343 */     luLoginDTO.setPsPassword(sPassword);
/* 344 */     luLoginDTO.setPsRol(sRol);
/*     */     
/* 346 */     MainDesktop.manejoVentanas("UpdateOrDeleteFrm", luLoginDTO);
/*     */     
/* 348 */     System.out.println(String.valueOf(iId) + " " + sUsr + " " + sPassword + " " + sRol);
/* 349 */     System.out.println(String.valueOf(this.TblLogin.getSelectedRow() + 1) + " " + (this.TblLogin.getSelectedColumn() + 1));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 354 */   private void TxtSearch_actionPerformed(ActionEvent e) { searchUser(); }
/*     */ }

