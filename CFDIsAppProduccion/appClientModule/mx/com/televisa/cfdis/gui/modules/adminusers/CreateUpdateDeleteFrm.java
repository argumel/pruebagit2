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
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextField;
/*     */ import mx.com.televisa.cfdis.gui.main.MainDesktop;
/*     */ import mx.com.televisa.cfdis.model.DAO.LoginDAO;
/*     */ import mx.com.televisa.cfdis.model.DTO.LoginDTO;
/*     */ import mx.com.televisa.cfdis.util.MD5Crypto;
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
/*     */ public class CreateUpdateDeleteFrm
/*     */   extends JInternalFrame
/*     */ {
/*     */   Dimension screenSize;
/*     */   Dimension windowSize;
/*  52 */   private JTabbedPane TbpCreateUpdateDelete = new JTabbedPane();
/*     */   
/*  54 */   private JPanel PnlCreateOrUpdate = new JPanel();
/*  55 */   private JPanel PnlDelete = new JPanel();
/*  56 */   private JLabel LblUser = new JLabel();
/*  57 */   private JLabel LblPassword = new JLabel();
/*  58 */   private JLabel LblConfirmPassword = new JLabel();
/*  59 */   private JLabel LblRolType = new JLabel();
/*  60 */   private JPasswordField TxtPassword = new JPasswordField();
/*  61 */   private JPasswordField TxtConfirmPassword = new JPasswordField();
/*  62 */   private JRadioButton RdbAdministrative = new JRadioButton();
/*  63 */   private JRadioButton RdbOperativo = new JRadioButton();
/*  64 */   private JTextField TxtUser = new JTextField();
/*  65 */   private JButton BtnCancel = new JButton();
/*  66 */   private JButton BtnSave = new JButton();
/*  67 */   private JLabel LblMessageError = new JLabel();
/*  68 */   private JButton BtnSaveUpdate = new JButton();
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private int piIdUser;
/*     */ 
/*     */ 
/*     */   
/*     */   private JButton BtnDelete;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public CreateUpdateDeleteFrm(String pPanelName) {
/*     */     try {
/*  84 */       jbInit();
/*  85 */       createUser(pPanelName);
/*     */ 
/*     */       
/*  88 */       this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*  89 */       this.windowSize = getSize();
/*  90 */       int windowX = Math.max(0, (this.screenSize.width - this.windowSize.width) / 2);
/*  91 */       int windowY = Math.max(0, (this.screenSize.height - this.windowSize.height) / 2);
/*  92 */       setLocation(windowX, windowY);
/*     */     }
/*  94 */     catch (Exception e) {
/*  95 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/*     */   public CreateUpdateDeleteFrm(String pPanelName, LoginDTO pLoginDTO) {
/*     */     try {
/* 102 */       jbInit();
/* 103 */       updateUser(pPanelName, pLoginDTO);
/*     */ 
/*     */       
/* 106 */       this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 107 */       this.windowSize = getSize();
/* 108 */       int windowX = Math.max(0, (this.screenSize.width - this.windowSize.width) / 2);
/* 109 */       int windowY = Math.max(0, (this.screenSize.height - this.windowSize.height) / 2);
/* 110 */       setLocation(windowX, windowY);
/*     */     }
/* 112 */     catch (Exception e) {
/* 113 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void jbInit() throws Exception {
/* 118 */     getContentPane().setLayout(null);
/* 119 */     setSize(new Dimension(400, 300));
/*     */     
/* 121 */     this.TbpCreateUpdateDelete.setBounds(new Rectangle(0, 0, 400, 275));
/* 122 */     this.PnlDelete.setLayout(null);
/*     */     
/* 124 */     this.PnlCreateOrUpdate.setLayout(null);
/*     */     
/* 126 */     this.LblUser.setText("Usuario:");
/* 127 */     this.LblUser.setBounds(new Rectangle(15, 30, 110, 15));
/* 128 */     this.LblUser.setFont(new Font("Tahoma", 1, 11));
/* 129 */     this.LblPassword.setText("Contrasña:");
/* 130 */     this.LblPassword.setBounds(new Rectangle(15, 65, 115, 15));
/* 131 */     this.LblPassword.setFont(new Font("Tahoma", 1, 11));
/* 132 */     this.LblConfirmPassword.setText("Confirmar Contraseña:");
/* 133 */     this.LblConfirmPassword.setBounds(new Rectangle(15, 100, 130, 15));
/* 134 */     this.LblConfirmPassword.setFont(new Font("Tahoma", 1, 11));
/* 135 */     this.LblRolType.setText("Tipo de Rol:");
/* 136 */     this.LblRolType.setBounds(new Rectangle(15, 150, 125, 15));
/* 137 */     this.LblRolType.setFont(new Font("Tahoma", 1, 11));
/* 138 */     this.TxtPassword.setBounds(new Rectangle(155, 65, 155, 20));
/* 139 */     this.TxtPassword.setSize(new Dimension(155, 25));
/*     */     
/* 141 */     this.TxtPassword.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e)
/*     */           {
/* 145 */             CreateUpdateDeleteFrm.this.TxtPassword_mouseClicked(e);
/*     */           }
/*     */         });
/*     */     
/* 149 */     this.TxtConfirmPassword.setBounds(new Rectangle(155, 100, 155, 25));
/* 150 */     this.TxtConfirmPassword.setSize(new Dimension(155, 25));
/*     */     
/* 152 */     this.TxtConfirmPassword.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e)
/*     */           {
/* 156 */             CreateUpdateDeleteFrm.this.TxtConfirmPassword_mouseClicked(e);
/*     */           }
/*     */         });
/*     */     
/* 160 */     this.RdbAdministrative.setText("Administrativo");
/* 161 */     this.RdbAdministrative.setBounds(new Rectangle(150, 145, 115, 20));
/*     */     
/* 163 */     this.RdbAdministrative.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 167 */             CreateUpdateDeleteFrm.this.RdbAdministrative_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 171 */     this.RdbAdministrative.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e)
/*     */           {
/* 175 */             CreateUpdateDeleteFrm.this.RdbAdministrative_mouseClicked(e);
/*     */           }
/*     */         });
/*     */     
/* 179 */     this.RdbOperativo.setText("Operativo");
/* 180 */     this.RdbOperativo.setBounds(new Rectangle(275, 145, 86, 18));
/*     */     
/* 182 */     this.RdbOperativo.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 186 */             CreateUpdateDeleteFrm.this.RdbOperativo_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 190 */     this.RdbOperativo.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e)
/*     */           {
/* 194 */             CreateUpdateDeleteFrm.this.RdbOperativo_mouseClicked(e);
/*     */           }
/*     */         });
/*     */     
/* 198 */     this.TxtUser.setBounds(new Rectangle(155, 30, 155, 20));
/* 199 */     this.TxtUser.setSize(new Dimension(155, 25));
/*     */     
/* 201 */     this.TxtUser.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e)
/*     */           {
/* 205 */             CreateUpdateDeleteFrm.this.TxtUser_mouseClicked(e);
/*     */           }
/*     */         });
/*     */     
/* 209 */     this.BtnCancel.setText("Cancelar");
/* 210 */     this.BtnCancel.setBounds(new Rectangle(100, 200, 120, 25));
/* 211 */     this.BtnCancel.setSize(new Dimension(120, 25));
/*     */     
/* 213 */     this.BtnCancel.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 217 */             CreateUpdateDeleteFrm.this.BtnCancel_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 221 */     this.BtnSave.setText("Guardar");
/* 222 */     this.BtnSave.setBounds(new Rectangle(260, 200, 120, 25));
/* 223 */     this.BtnSave.setSize(new Dimension(120, 25));
/*     */     
/* 225 */     this.BtnSave.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 229 */             CreateUpdateDeleteFrm.this.BtnSave_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 233 */     this.LblMessageError.setBounds(new Rectangle(15, 180, 370, 15));
/*     */     
/* 235 */     this.BtnSaveUpdate.setText("Guardar");
/* 236 */     this.BtnSaveUpdate.setBounds(new Rectangle(260, 200, 120, 25));
/* 237 */     this.BtnSaveUpdate.setSize(new Dimension(120, 25));
/* 238 */     this.TbpCreateUpdateDelete.addTab("Modificar", this.PnlCreateOrUpdate);
/* 239 */     this.PnlCreateOrUpdate.add(this.BtnSaveUpdate, null);
/* 240 */     this.PnlCreateOrUpdate.add(this.LblMessageError, null);
/* 241 */     this.PnlCreateOrUpdate.add(this.BtnSave, null);
/* 242 */     this.PnlCreateOrUpdate.add(this.BtnCancel, null);
/* 243 */     this.PnlCreateOrUpdate.add(this.TxtUser, null);
/* 244 */     this.PnlCreateOrUpdate.add(this.RdbOperativo, null);
/* 245 */     this.PnlCreateOrUpdate.add(this.RdbAdministrative, null);
/* 246 */     this.PnlCreateOrUpdate.add(this.TxtConfirmPassword, null);
/* 247 */     this.PnlCreateOrUpdate.add(this.TxtPassword, null);
/* 248 */     this.PnlCreateOrUpdate.add(this.LblRolType, null);
/* 249 */     this.PnlCreateOrUpdate.add(this.LblConfirmPassword, null);
/* 250 */     this.PnlCreateOrUpdate.add(this.LblPassword, null);
/*     */     
/* 252 */     this.PnlCreateOrUpdate.add(this.LblUser, null);
/* 253 */     this.BtnSave.setVisible(false);
/* 254 */     this.BtnSaveUpdate.setVisible(false);
/*     */     
/* 256 */     this.BtnSaveUpdate.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 260 */             CreateUpdateDeleteFrm.this.BtnSaveUpdate_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 264 */     getContentPane().add(this.TbpCreateUpdateDelete, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void createUser(String pPanelName) {
/* 270 */     setTitle("Crear Usuario");
/* 271 */     this.TbpCreateUpdateDelete.add(pPanelName, this.PnlCreateOrUpdate);
/* 272 */     this.TbpCreateUpdateDelete.addTab("Eliminar", this.PnlDelete);
/*     */     
/* 274 */     this.PnlCreateOrUpdate.add(this.LblUser, null);
/* 275 */     this.PnlCreateOrUpdate.add(this.TxtUser, null);
/*     */     
/* 277 */     this.RdbOperativo.setSelected(true);
/* 278 */     this.BtnSave.setVisible(true);
/*     */     
/* 280 */     this.BtnDelete = new JButton();
/* 281 */     this.BtnDelete.setText("Eliminar Registro");
/* 282 */     this.BtnDelete.setBounds(new Rectangle(125, 100, 140, 20));
/* 283 */     this.BtnDelete.setVisible(true);
/* 284 */     this.BtnDelete.setEnabled(false);
/* 285 */     this.BtnDelete.setSize(160, 25);
/* 286 */     this.PnlDelete.add(this.BtnDelete, null);
/*     */   }
/*     */   
/*     */   private void updateUser(String pPanelName, LoginDTO pLoginDTO) {
/* 290 */     setTitle("Modificar o Eliminar Usuario");
/*     */     
/* 292 */     this.piIdUser = pLoginDTO.getPiIdUser();
/*     */     
/* 294 */     this.TbpCreateUpdateDelete.add(pPanelName, this.PnlCreateOrUpdate);
/* 295 */     this.TbpCreateUpdateDelete.addTab("Eliminar", this.PnlDelete);
/* 296 */     this.TxtUser.setText(pLoginDTO.getPsUser());
/*     */     
/* 298 */     this.TxtUser.setEditable(false);
/*     */ 
/*     */ 
/*     */     
/* 302 */     this.TxtPassword.setText("");
/* 303 */     this.TxtConfirmPassword.setText("");
/*     */     
/* 305 */     if (pLoginDTO.getPsRol().equals("ROL_ADMIN")) {
/* 306 */       this.RdbAdministrative.setSelected(true);
/* 307 */     } else if (pLoginDTO.getPsRol().equals("ROL_OPE")) {
/* 308 */       this.RdbOperativo.setSelected(true);
/*     */     } 
/* 310 */     this.BtnSaveUpdate.setVisible(true);
/*     */     
/* 312 */     this.BtnDelete = new JButton();
/* 313 */     this.BtnDelete.setText("Eliminar Registro");
/* 314 */     this.BtnDelete.setBounds(new Rectangle(125, 100, 140, 20));
/* 315 */     this.BtnDelete.setVisible(true);
/* 316 */     this.BtnDelete.setEnabled(true);
/* 317 */     this.BtnDelete.setSize(160, 25);
/* 318 */     this.BtnDelete.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 322 */             CreateUpdateDeleteFrm.this.BtnDelete_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 326 */     this.PnlDelete.add(this.BtnDelete, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void BtnSave_actionPerformed(ActionEvent e) {
/*     */     try {
/* 334 */       System.out.println("Entro Btn_Save");
/* 335 */       String lsUser = null;
/* 336 */       String lsPassword = null;
/* 337 */       char[] laPassword = null;
/* 338 */       String lsConfirmPassword = null;
/* 339 */       char[] laConfirmPassword = null;
/* 340 */       lsUser = this.TxtUser.getText();
/* 341 */       laPassword = this.TxtPassword.getPassword();
/* 342 */       lsPassword = new String(laPassword);
/* 343 */       laConfirmPassword = this.TxtConfirmPassword.getPassword();
/* 344 */       lsConfirmPassword = new String(laConfirmPassword);
/*     */       
/* 346 */       if (lsUser.equals("") || lsPassword.equals("") || lsConfirmPassword.equals("")) {
/* 347 */         System.out.println("Si blancos");
/* 348 */         this.LblMessageError.setText("Ninguno de los campos debe contener nulos");
/* 349 */         this.LblMessageError.setForeground(Color.RED);
/* 350 */         this.LblMessageError.setVisible(true);
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
/*     */       }
/* 406 */       else if (lsPassword.equals(lsConfirmPassword)) {
/* 407 */         LoginDAO luLoginDAO = new LoginDAO();
/* 408 */         LoginDTO luLoginDTO = new LoginDTO();
/*     */         
/* 410 */         luLoginDTO.setPsUser(lsUser);
/*     */         
/* 412 */         String sPwdEncrypted = MD5Crypto.getMD5(lsPassword);
/* 413 */         luLoginDTO.setSPwdEncrypted(sPwdEncrypted);
/* 414 */         luLoginDTO.setPsPassword(lsPassword);
/* 415 */         if (this.RdbAdministrative.isSelected() && !this.RdbOperativo.isSelected()) {
/* 416 */           luLoginDTO.setPsRol("ROL_ADMIN");
/* 417 */           System.out.println("Activado Administrativo");
/*     */         } 
/*     */         
/* 420 */         if (this.RdbOperativo.isSelected() && !this.RdbAdministrative.isSelected()) {
/* 421 */           luLoginDTO.setPsRol("ROL_OPE");
/* 422 */           System.out.println("Activado Operativo");
/*     */         } 
/*     */         
/* 425 */         int liReturn = luLoginDAO.executeInsert(luLoginDTO);
/* 426 */         System.out.println(liReturn);
/*     */         
/* 428 */         if (liReturn == 1) {
/* 429 */           this.LblMessageError.setText("Has agregado un nuevo usuario satisfactoriamnte");
/* 430 */           this.LblMessageError.setForeground(Color.GREEN);
/* 431 */           this.LblMessageError.setVisible(true);
/*     */         } else {
/* 433 */           this.LblMessageError.setText("Error Oracle DataBase");
/* 434 */           this.LblMessageError.setForeground(Color.RED);
/* 435 */           this.LblMessageError.setVisible(true);
/*     */         } 
/*     */         
/* 438 */         this.TxtUser.setText("");
/* 439 */         this.TxtPassword.setText("");
/* 440 */         this.TxtConfirmPassword.setText("");
/* 441 */         this.RdbAdministrative.setSelected(false);
/* 442 */         this.RdbOperativo.setSelected(true);
/*     */       } else {
/*     */         
/* 445 */         this.LblMessageError.setText("Las contrase�as no coinciden, �Quieres volver a intentarlo?");
/* 446 */         this.LblMessageError.setForeground(Color.RED);
/* 447 */         this.LblMessageError.setVisible(true);
/*     */         
/* 449 */         this.TxtPassword.setText("");
/* 450 */         this.TxtConfirmPassword.setText("");
/*     */         
/* 452 */         System.out.println("No hizo update");
/*     */       }
/*     */     
/* 455 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void RdbAdministrative_actionPerformed(ActionEvent e) {
/* 462 */     if (this.RdbAdministrative.isSelected()) {
/* 463 */       this.RdbAdministrative.setSelected(true);
/* 464 */       this.RdbOperativo.setSelected(false);
/*     */     } else {
/* 466 */       this.RdbAdministrative.setSelected(true);
/* 467 */       this.RdbOperativo.setSelected(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void RdbOperativo_actionPerformed(ActionEvent e) {
/* 474 */     if (this.RdbOperativo.isSelected()) {
/* 475 */       this.RdbOperativo.setSelected(true);
/* 476 */       this.RdbAdministrative.setSelected(false);
/*     */     } else {
/* 478 */       this.RdbOperativo.setSelected(true);
/* 479 */       this.RdbAdministrative.setSelected(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/* 485 */   private void BtnCancel_actionPerformed(ActionEvent e) { setVisible(false); }
/*     */ 
/*     */ 
/*     */   
/* 489 */   private void TxtUser_mouseClicked(MouseEvent e) { this.LblMessageError.setVisible(false); }
/*     */ 
/*     */ 
/*     */   
/* 493 */   private void TxtPassword_mouseClicked(MouseEvent e) { this.LblMessageError.setVisible(false); }
/*     */ 
/*     */ 
/*     */   
/* 497 */   private void TxtConfirmPassword_mouseClicked(MouseEvent e) { this.LblMessageError.setVisible(false); }
/*     */ 
/*     */ 
/*     */   
/* 501 */   private void RdbAdministrative_mouseClicked(MouseEvent e) { this.LblMessageError.setVisible(false); }
/*     */ 
/*     */ 
/*     */   
/* 505 */   private void RdbOperativo_mouseClicked(MouseEvent e) { this.LblMessageError.setVisible(false); }
/*     */ 
/*     */   
/*     */   private void BtnSaveUpdate_actionPerformed(ActionEvent e) {
/* 509 */     System.out.println("Entro Btn_SaveUpdate");
/*     */     
/* 511 */     String lsUser = null;
/* 512 */     String lsPassword = null;
/* 513 */     char[] laPassword = null;
/* 514 */     String lsConfirmPassword = null;
/* 515 */     char[] laConfirmPassword = null;
/*     */     
/* 517 */     lsUser = this.TxtUser.getText();
/*     */     
/* 519 */     laPassword = this.TxtPassword.getPassword();
/* 520 */     lsPassword = new String(laPassword);
/*     */     
/* 522 */     laConfirmPassword = this.TxtConfirmPassword.getPassword();
/* 523 */     lsConfirmPassword = new String(laConfirmPassword);
/*     */     
/* 525 */     if (lsUser.equals("") || lsPassword.equals("") || lsConfirmPassword.equals("")) {
/* 526 */       JOptionPane.showMessageDialog(null, "Alguno de los campos requeridos es nulo, verifique.", "Warning", 2);
/*     */     
/*     */     }
/* 529 */     else if (lsUser.equals("") || lsPassword.equals("") || lsConfirmPassword.equals("")) {
/* 530 */       System.out.println("Si blancos");
/* 531 */       this.LblMessageError.setText("Ninguno de los campos debe contener nulos");
/* 532 */       this.LblMessageError.setForeground(Color.RED);
/* 533 */       this.LblMessageError.setVisible(true);
/*     */     
/*     */     }
/* 536 */     else if (lsPassword.equals(lsConfirmPassword)) {
/* 537 */       LoginDAO luLoginDAO = new LoginDAO();
/* 538 */       LoginDTO luLoginDTO = new LoginDTO();
/*     */       
/* 540 */       luLoginDTO.setPsUser(lsUser);
/*     */ 
/*     */ 
/*     */       
/* 544 */       String sPwdEncrypted = MD5Crypto.getMD5(lsPassword);
/* 545 */       luLoginDTO.setSPwdEncrypted(sPwdEncrypted);
/*     */       
/* 547 */       luLoginDTO.setPsPassword(lsPassword);
/*     */       
/* 549 */       if (this.RdbAdministrative.isSelected() && !this.RdbOperativo.isSelected()) {
/* 550 */         luLoginDTO.setPsRol("ROL_ADMIN");
/* 551 */         System.out.println("Activado Administrativo");
/*     */       } 
/* 553 */       if (this.RdbOperativo.isSelected() && !this.RdbAdministrative.isSelected()) {
/* 554 */         luLoginDTO.setPsRol("ROL_OPE");
/* 555 */         System.out.println("Activado Operativo");
/*     */       } 
/*     */ 
/*     */       
/* 559 */       luLoginDTO.setPiIdUser(this.piIdUser);
/*     */ 
/*     */       
/* 562 */       int liReturn = 0;
try {
	liReturn = luLoginDAO.executeUpdate(luLoginDTO);
} catch (Exception e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
/* 563 */       System.out.println(liReturn);
/*     */ 
/*     */       
/* 566 */       if (liReturn == 1) {
/* 567 */         this.LblMessageError.setText("Haz actualizado el usuario satisfactoriamente");
/* 568 */         this.LblMessageError.setForeground(Color.GREEN);
/* 569 */         this.LblMessageError.setVisible(true);
/* 570 */         try {
	refreshTable();
} catch (Exception e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
/*     */       } else {
/* 572 */         this.LblMessageError.setText("Error Oracle DataBase");
/* 573 */         this.LblMessageError.setForeground(Color.RED);
/* 574 */         this.LblMessageError.setVisible(true);
/*     */       } 
/*     */     } else {
/*     */       
/* 578 */       this.LblMessageError.setText("Las contraseñas no coinciden, ¿Quieres volver a intentarlo?");
/* 579 */       this.LblMessageError.setForeground(Color.RED);
/* 580 */       this.LblMessageError.setVisible(true);
/*     */       
/* 582 */       this.TxtPassword.setText("");
/* 583 */       this.TxtConfirmPassword.setText("");
/*     */       
/* 585 */       System.out.println("No hizo update");
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void refreshTable() throws Exception {
/* 592 */     dispose();
/* 593 */     MainDesktop.manejoVentanas("luInternalAdminUser");
/*     */   }
/*     */ 
/*     */   
/*     */   private void BtnDelete_actionPerformed(ActionEvent e) {
/* 598 */     int iOption = JOptionPane.showConfirmDialog(null, "¿Estas seguro de eliminar el Registro ?", "Mensaje", 0);
/* 599 */     if (iOption == 0) {
/*     */       
/* 601 */       System.out.println(this.piIdUser);
/*     */       
/* 603 */       LoginDAO luLoginDAO = new LoginDAO();
/* 604 */       luLoginDAO.deleteRecord(this.piIdUser);
/*     */       
/* 606 */       try {
	refreshTable();
} catch (Exception e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
}
/*     */     } 
/*     */   }
/*     */ }

