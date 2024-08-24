/*     */ package mx.com.televisa.cfdis.gui.main;
/*     */ 
/*     */ import java.awt.Cursor;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Image;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import java.beans.PropertyVetoException;
/*     */ import java.net.URISyntaxException;
/*     */ import java.util.ResourceBundle;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JMenu;
/*     */ import javax.swing.JMenuBar;
/*     */ import javax.swing.JMenuItem;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JSeparator;
/*     */ import mx.com.televisa.cfdis.gui.modules.adminusers.AdminUserFrm;
/*     */ import mx.com.televisa.cfdis.gui.modules.adminusers.CreateUpdateDeleteFrm;
/*     */ import mx.com.televisa.cfdis.gui.modules.adminusers.InsertUserFrm;
/*     */ import mx.com.televisa.cfdis.gui.modules.adminusers.LoginUserFrm;
/*     */ import mx.com.televisa.cfdis.gui.modules.cargasexcel.CargaHonorariosFrm;
/*     */ import mx.com.televisa.cfdis.gui.modules.cargasexcel.CargaTercerosFiscalFrm;
/*     */ import mx.com.televisa.cfdis.gui.modules.cargasexcel.CargaTercerosFrm;
/*     */ import mx.com.televisa.cfdis.model.DTO.LoginDTO;
/*     */ import mx.com.televisa.cfdis.util.LeerProperties;
/*     */ import mx.com.televisa.cfdis.util.SessionApplication;
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
/*     */ 
/*     */ public class MainDesktop
/*     */   extends JFrame
/*     */ {
/*     */   private static final String WIN_CARGA_TERCEROS = "luCargasExcel";
/*     */   private static final String WIN_CARGA_TERCEROS_FISCAL = "luCargasExcel_Fiscal";
/*     */   private static final String WIN_CARGA_HONORARIOS = "luCargasExcelHonorarios";
/*     */   private static final String WIN_ADMIN_USERS = "luInternalAdminUser";
/*     */   public static final String WIN_CREATE_USER = "createFrm";
/*     */   private static DesktopBackground desktop;
/*     */   
/*     */   public MainDesktop() {
/*     */     try {
/*  80 */       desktop = new DesktopBackground();
/*  81 */       setContentPane(desktop);
/*     */ 
/*     */       
/*  84 */       desktop.setDragMode(1);
/*  85 */     } catch (Exception e) {
/*  86 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public MainDesktop(LoginDTO tLoginDTO) throws URISyntaxException {
/*  94 */     String imprime = "Antes de la clase LeerProperties";
/*  98 */     LeerProperties.LeerPropertiesFinal();
/*  99 */
/*     */ 
/*     */ 
/*     */     
/* 103 */     imprime = "Pase la lectura clase LeerProperties";
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 108 */     setExtendedState(6);
/* 109 */     validateRolUser(tLoginDTO);
/* 110 */     Image imagenInterna = (new ImageIcon(getClass().getResource("/mx/com/televisa/images/tvsa.jpg"))).getImage();
/* 111 */     Image imagenFondo = (new ImageIcon(getClass().getResource("/mx/com/televisa/images/fondo_azul.jpg"))).getImage();
/*     */     try {
/* 113 */       jbInit();
/*     */       
/* 115 */       desktop = new DesktopBackground(imagenInterna, imagenFondo);
/* 116 */       setContentPane(desktop);
/*     */ 
/*     */ 
/*     */       
/* 120 */       desktop.setDragMode(1);
/* 121 */     } catch (Exception e) {
/* 122 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void jbInit() {
/* 127 */     ResourceBundle bundle = ResourceBundle.getBundle("DataConnection");
/*     */     
/* 129 */     getContentPane().setLayout(null);
/* 130 */     setSize(new Dimension(400, 300));
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 135 */     setTitle(bundle.getString("mx.com.televisa.cfdis.TitleApp").trim());
/* 136 */     setJMenuBar(this.MnbMenuBar);
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 141 */     addWindowListener(new WindowAdapter()
/*     */         {
/*     */           public void windowClosing(WindowEvent e)
/*     */           {
/* 145 */             MainDesktop.this.this_windowClosing(e);
/*     */           }
/*     */         });
/*     */     
/* 149 */     this.MnuMenu.setText("Menú");
/* 150 */     this.MnuMenuSeg.setText("MenúSeguro");
/*     */ 
/*     */ 
/*     */     
/* 154 */     this.menuItemUserAdmin.setText("Administración de Usuarios");
/* 155 */     this.menuItemUserAdmin.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 159 */             MainDesktop.this.MniUserAdmin_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 163 */     this.menuItemCargaTerceros.setText("Carga de Retenciones Terceros");
/* 164 */     this.menuItemCargaTerceros.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 168 */             MainDesktop.this.menuItemCargaTerceros_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 172 */     this.menuItemCargaTercerosSeg.setText("Carga de Retenciones Terceros Fiscal Anual");
/* 173 */     this.menuItemCargaTercerosSeg.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 177 */             MainDesktop.this.menuItemCargaTercerosSeg_actionPerformed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 182 */     this.menuItemCargaHonorarios.setText("Carga de Retenciones Asimilables");
/* 183 */     this.menuItemCargaHonorarios.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 187 */             MainDesktop.this.menuItemCargaHonorarios_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 191 */     this.menuItemSalir.setText("Salir");
/* 192 */     this.menuItemSalir.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 196 */             MainDesktop.this.menuItemSalir_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 200 */     this.MnuMenu.add(this.menuItemUserAdmin);
/* 201 */     this.MnuMenu.addSeparator();
/* 202 */     this.MnuMenu.add(this.menuItemCargaTerceros);
/* 203 */     this.MnuMenu.add(this.menuItemCargaHonorarios);
/* 204 */     this.MnuMenu.addSeparator();
/* 205 */     this.MnuMenu.add(this.menuItemSalir);
/*     */     
/* 207 */     this.MnbMenuBar.add(this.MnuMenu);
/*     */ 
/*     */     
/* 210 */     this.MnuMenuSeg.add(this.menuItemUserAdmin);
/* 211 */     this.MnuMenuSeg.addSeparator();
/* 212 */     this.MnuMenuSeg.add(this.menuItemCargaTercerosSeg);
/*     */ 
/*     */     
/* 215 */     this.MnbMenuBar.add(this.MnuMenuSeg);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 220 */   private void validateRolUser(LoginDTO tLoginDTO) { this.menuItemUserAdmin.setVisible(false); }
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
/*     */   private void MniUserAdmin_actionPerformed(ActionEvent e) {
/* 233 */     setCursor(Cursor.getPredefinedCursor(3));
/* 234 */     cierreVentanas();
/* 235 */     manejoVentanas("luInternalAdminUser");
/* 236 */     setCursor(Cursor.getPredefinedCursor(0));
/*     */   }
/*     */   
/*     */   private void menuItemCargaTerceros_actionPerformed(ActionEvent e) {
/* 240 */     setCursor(Cursor.getPredefinedCursor(3));
/* 241 */     cierreVentanas();
/* 242 */     manejoVentanas("luCargasExcel");
/* 243 */     setCursor(Cursor.getPredefinedCursor(0));
/*     */   }
/*     */   
/*     */   private void menuItemCargaTercerosSeg_actionPerformed(ActionEvent e) {
/* 247 */     setCursor(Cursor.getPredefinedCursor(3));
/* 248 */     cierreVentanas();
/* 249 */     manejoVentanas("luCargasExcel_Fiscal");
/* 250 */     setCursor(Cursor.getPredefinedCursor(0));
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void menuItemCargaHonorarios_actionPerformed(ActionEvent e) {
/* 256 */     setCursor(Cursor.getPredefinedCursor(3));
/* 257 */     cierreVentanas();
/* 258 */     manejoVentanas("luCargasExcelHonorarios");
/* 259 */     setCursor(Cursor.getPredefinedCursor(0));
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 268 */   private void menuItemSalir_actionPerformed(ActionEvent e) { System.exit(0); }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void manejoVentanas(String frame) {
/*     */     try {
/* 274 */       if (frame.equals("luInternalAdminUser")) {
/* 275 */         if (luInternalAdminUser == null) {
/* 276 */           luInternalAdminUser = new AdminUserFrm();
/* 277 */           setJInternalFrame(luInternalAdminUser);
/*     */         } else {
/* 279 */           luInternalAdminUser.setClosed(true);
/* 280 */           luInternalAdminUser = new AdminUserFrm();
/* 281 */           setJInternalFrame(luInternalAdminUser);
/*     */         }
/*     */       
/* 284 */       } else if (frame.equals("luCargasExcel")) {
/* 285 */         if (luCargaTercerosFrm == null) {
/* 286 */           luCargaTercerosFrm = new CargaTercerosFrm();
/* 287 */           setJInternalFrame(luCargaTercerosFrm);
/*     */         } else {
/* 289 */           luCargaTercerosFrm.setClosed(true);
/* 290 */           luCargaTercerosFrm = new CargaTercerosFrm();
/* 291 */           setJInternalFrame(luCargaTercerosFrm);
/*     */         }
/*     */       
/* 294 */       } else if (frame.equals("luCargasExcelHonorarios")) {
/* 295 */         if (luCargaHonorariosFrm == null) {
/* 296 */           luCargaHonorariosFrm = new CargaHonorariosFrm();
/* 297 */           setJInternalFrame(luCargaHonorariosFrm);
/*     */         } else {
/* 299 */           luCargaHonorariosFrm.setClosed(true);
/* 300 */           luCargaHonorariosFrm = new CargaHonorariosFrm();
/* 301 */           setJInternalFrame(luCargaHonorariosFrm);
/*     */         }
/*     */       
/* 304 */       } else if (frame.equals("insertUserFrm")) {
/* 305 */         if (insertUserFrm == null) {
/* 306 */           insertUserFrm = new InsertUserFrm();
/* 307 */           desktop.add(insertUserFrm);
/* 308 */           insertUserFrm.setVisible(true);
/*     */         } else {
/* 310 */           insertUserFrm.setClosed(true);
/* 311 */           insertUserFrm = new InsertUserFrm();
/* 312 */           setJInternalFrame(insertUserFrm);
/*     */         }
/*     */       
/* 315 */       } else if (frame.equals("createFrm")) {
/* 316 */         if (luCreateFrm == null) {
/* 317 */           luCreateFrm = new CreateUpdateDeleteFrm("Crear");
/* 318 */           desktop.add(luCreateFrm);
/* 319 */           luCreateFrm.setVisible(true);
/*     */         } else {
/* 321 */           luCreateFrm.setClosed(true);
/* 322 */           luCreateFrm = new CreateUpdateDeleteFrm("Crear");
/* 323 */           setJInternalFrame(luCreateFrm);
/*     */         } 
/* 325 */       } else if (frame.equals("luCargasExcel_Fiscal")) {
/*     */         
/* 327 */         if (luLoginUserFrm == null)
/*     */         {
/* 329 */           luLoginUserFrm = new LoginUserFrm();
/* 330 */           desktop.add(luLoginUserFrm);
/* 331 */           luLoginUserFrm.setVisible(true);
/*     */         }
/*     */         else
/*     */         {
/* 335 */           luLoginUserFrm.setClosed(true);
/* 336 */           luLoginUserFrm = new LoginUserFrm();
/* 337 */           setJInternalFrame(luLoginUserFrm);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */         
/*     */         }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */     
/*     */     }
/* 363 */     catch (Exception ex) {
/*     */       
/* 365 */       JOptionPane.showMessageDialog(null, "Error al cerrar la ventana " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 370 */   public DesktopBackground getdesktop() { return desktop; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 376 */   public DesktopBackground setdesktop(DesktopBackground desktop) { return desktop; }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void manejoVentanas(String frame, LoginDTO pLoginDTO) {
/*     */     try {
/* 384 */       if (frame.equals("UpdateOrDeleteFrm")) {
/* 385 */         if (luUpdateOrDeleteFrm == null) {
/* 386 */           luUpdateOrDeleteFrm = new CreateUpdateDeleteFrm("Modificar", pLoginDTO);
/* 387 */           desktop.add(luUpdateOrDeleteFrm);
/* 388 */           luUpdateOrDeleteFrm.setVisible(true);
/*     */         } else {
/* 390 */           luUpdateOrDeleteFrm.setClosed(true);
/* 391 */           luUpdateOrDeleteFrm = new CreateUpdateDeleteFrm("Modificar", pLoginDTO);
/* 392 */           setJInternalFrame(luUpdateOrDeleteFrm);
/*     */         } 
/*     */       }
/* 395 */     } catch (Exception ex) {
/* 396 */       JOptionPane.showMessageDialog(null, "Error al cerrar la ventana " + ex.getMessage());
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   public static void setJInternalFrame(JInternalFrame frame) throws PropertyVetoException {
/* 403 */     desktop.add(frame);
/* 404 */     frame.setVisible(true);
/*     */   }
/*     */   
/*     */   private void this_windowClosing(WindowEvent e) {
/* 408 */     int iAnwser = 
/* 409 */       JOptionPane.showConfirmDialog(null, "Esta seguro que desea cerrar sesión?", "Advertencia", 
/* 410 */         0);
/* 411 */     if (iAnwser == 0) {
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 416 */       System.exit(0);
/* 417 */     } else if (iAnwser == 1) {
/* 418 */       System.out.println("Entro opc 1");
/* 419 */       try {
	(new MainDesktop(SessionApplication.loginDTO)).setVisible(true);
} catch (URISyntaxException e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   private void cierreVentanas() {
/* 425 */     JInternalFrame[] frame = desktop.getAllFrames();
/*     */     
/* 427 */     for (int i = 0; i < frame.length; i++) {
/*     */       try {
/* 429 */         frame[i].setClosed(true);
/* 430 */       } catch (PropertyVetoException propertyVetoException) {}
/*     */     } 
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
/*     */   public static void main(String[] args) {
/* 449 */     LoginDTO loginDTO = new LoginDTO();
/* 450 */     loginDTO.setPiIdUser(1);
/* 451 */     loginDTO.setPiStatus(0);
/* 452 */     loginDTO.setPsPassword("");
/* 453 */     loginDTO.setPsConfirmPwd("");
/* 454 */     loginDTO.setPsRol("ROL_OPE");
/* 455 */     try {
	(new MainDesktop(new LoginDTO())).setVisible(true);
} catch (URISyntaxException e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
}
/*     */   }
/*     */   
/* 458 */   private JMenuBar MnbMenuBar = new JMenuBar();
/* 459 */   private JMenu MnuMenu = new JMenu();
/* 460 */   private JMenu MnuMenuSeg = new JMenu();
/* 461 */   private JMenuItem menuItemUserAdmin = new JMenuItem();
/* 462 */   private JSeparator jSeparator2 = new JSeparator();
/* 463 */   private JMenuItem menuItemSalir = new JMenuItem();
/* 464 */   private JMenuItem menuItemCargaTerceros = new JMenuItem();
/* 465 */   private JMenuItem menuItemCargaTercerosSeg = new JMenuItem();
/* 466 */   private JMenuItem menuItemCargaHonorarios = new JMenuItem();
/* 467 */   static AdminUserFrm luInternalAdminUser = null;
/* 468 */   static CargaTercerosFrm luCargaTercerosFrm = null;
/* 469 */   static CargaTercerosFiscalFrm luCargaTercerosFiscalFrm = null;
/*     */   
/* 471 */   static CargaHonorariosFrm luCargaHonorariosFrm = null;
/* 472 */   static InsertUserFrm insertUserFrm = null;
/* 473 */   static LoginUserFrm luLoginUserFrm = null;
/* 474 */   static CreateUpdateDeleteFrm luUpdateOrDeleteFrm = null;
/* 475 */   static CreateUpdateDeleteFrm luCreateFrm = null;
/*     */ }

