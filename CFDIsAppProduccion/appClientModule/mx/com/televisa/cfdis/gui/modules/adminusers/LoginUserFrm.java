/*     */ package mx.com.televisa.cfdis.gui.modules.adminusers;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JTextField;
/*     */ import mx.com.televisa.cfdis.gui.common.MessageBoxes;
/*     */ import mx.com.televisa.cfdis.gui.common.StaticContext;
/*     */ import mx.com.televisa.cfdis.gui.main.MainDesktop;
/*     */ import mx.com.televisa.cfdis.gui.modules.cargasexcel.CargaTercerosFiscalFrm;
/*     */ import mx.com.televisa.cfdis.gui.modules.login.LoginBackground;
/*     */ import mx.com.televisa.cfdis.model.DAO.LoginDAO;
/*     */ import mx.com.televisa.cfdis.model.DTO.LoginDTO;
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
/*     */ public class LoginUserFrm
/*     */   extends JInternalFrame
/*     */ {
/*     */   Dimension screenSize;
/*     */   Dimension windowSize;
/*  62 */   private JLabel LblUser = new JLabel();
/*  63 */   private JTextField TxtUser = new JTextField();
/*     */ 
/*     */   
/*  66 */   private JLabel LblConfirPassword = new JLabel();
/*  67 */   private JPasswordField TxtConfirmPassword = new JPasswordField();
/*  68 */   private JLabel LblRolType = new JLabel();
/*  69 */   private JRadioButton RdbAdministrative = new JRadioButton();
/*  70 */   private JRadioButton RdbOperativo = new JRadioButton();
/*     */ 
/*     */   
/*  73 */   private JPanel PnlAdminUser = new JPanel();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*  80 */   private JPanel jPanel1 = new JPanel();
/*  81 */   private JLabel lblObiSerpelApp = new JLabel();
/*  82 */   private JLabel LblPassword = new JLabel();
/*  83 */   private JPasswordField TxtPassword = new JPasswordField();
/*     */   
/*  85 */   private JButton BtnCancelar = new JButton();
/*  86 */   private JButton BtnAccept = new JButton();
/*  87 */   private JLabel LblMessageError = new JLabel();
/*  88 */   private JLabel LblPassword1 = new JLabel();
/*     */   
/*  90 */   static CargaTercerosFiscalFrm luCargaTercerosFiscalFrm = null;
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public LoginUserFrm() {
/*     */     try {
/*  97 */       jbInit();
/*     */ 
/*     */       
/* 100 */       this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 101 */       this.windowSize = getSize();
/* 102 */       int windowX = Math.max(0, (this.screenSize.width - this.windowSize.width) / 2);
/* 103 */       int windowY = Math.max(0, (this.screenSize.height - this.windowSize.height) / 2);
/* 104 */       setLocation(windowX, windowY);
/*     */     }
/* 106 */     catch (Exception e) {
/* 107 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void jbInit() {
/* 112 */     setSize(new Dimension(395, 299));
/* 113 */     setLocation(450, 250);
/* 114 */     setTitle("Login");
/* 115 */     setResizable(false);
/* 116 */     this.jPanel1.setLayout(null);
/* 117 */     this.lblObiSerpelApp.setText("Cargas Fiscal");
/* 118 */     this.lblObiSerpelApp.setBounds(new Rectangle(85, 35, 220, 35));
/* 119 */     this.lblObiSerpelApp.setFont(new Font("Tahoma", 1, 24));
/* 120 */     this.lblObiSerpelApp.setForeground(new Color(0, 33, 115));
/* 121 */     this.LblPassword.setText("Contraseña:");
/* 122 */     this.LblPassword.setBounds(new Rectangle(70, 155, 70, 15));
/* 123 */     this.LblPassword.setFont(new Font("Tahoma", 0, 11));
/* 124 */     this.TxtPassword.setBounds(new Rectangle(155, 150, 150, 25));
/*     */     
/* 126 */     this.TxtPassword.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 128 */             LoginUserFrm.this.TxtPassword_actionPerformed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 133 */     this.BtnCancelar.setText("Cancelar");
/* 134 */     this.BtnCancelar.setBounds(new Rectangle(275, 230, 85, 20));
/* 135 */     this.BtnCancelar.setFont(new Font("Tahoma", 1, 11));
/*     */ 
/*     */     
/* 138 */     this.BtnCancelar.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 142 */             LoginUserFrm.this.BtnCancelar_actionPerformed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */ 
/*     */ 
/*     */     
/* 149 */     this.BtnAccept.setText("Aceptar");
/* 150 */     this.BtnAccept.setBounds(new Rectangle(165, 230, 80, 20));
/* 151 */     this.BtnAccept.setFont(new Font("Tahoma", 1, 11));
/*     */     
/* 153 */     this.BtnAccept.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/* 155 */             LoginUserFrm.this.BtnAccept_actionPerformed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/* 160 */     this.LblMessageError.setVisible(false);
/* 161 */     this.LblMessageError.setText("Contraseña no son validos.");
/* 162 */     this.LblMessageError.setBounds(new Rectangle(35, 180, 295, 15));
/* 163 */     this.LblMessageError.setForeground(Color.red);
/*     */ 
/*     */ 
/*     */     
/* 167 */     this.LblPassword1.setBounds(new Rectangle(70, 115, 70, 15));
/* 168 */     this.LblPassword1.setFont(new Font("Tahoma", 0, 11));
/*     */     
/* 170 */     this.jPanel1.add(this.LblPassword1, null);
/* 171 */     this.jPanel1.add(this.LblMessageError, null);
/* 172 */     this.jPanel1.add(this.BtnAccept, null);
/* 173 */     this.jPanel1.add(this.BtnCancelar, null);
/*     */ 
/*     */     
/* 176 */     this.jPanel1.add(this.TxtPassword, null);
/* 177 */     this.jPanel1.add(this.LblPassword, null);
/* 178 */     this.jPanel1.add(this.lblObiSerpelApp, null);
/* 179 */     this.jPanel1.add((new LoginBackground()).getPanel());
/* 180 */     this.jPanel1.repaint();
/* 181 */     getContentPane().add(this.jPanel1, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 190 */   protected void TxtPassword_actionPerformed(ActionEvent e) { executeLogin(); }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 195 */   protected void BtnCancelar_actionPerformed(ActionEvent e) { setVisible(false); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 201 */   protected void BtnAccept_actionPerformed(ActionEvent e) { executeLogin();
System.out.println("Usuario Password ");}
/*     */ 
/*     */   
/*     */   private void executeLogin() {
/* 205 */     String lsUser = null;
/* 206 */     String lsPassword = null;
/* 207 */     char[] laPassword = null;
/* 208 */     boolean lbValidateUserPwd = false;
/*     */ 
/*     */ 		System.out.println("Usuario Password "+ this.TxtPassword.getPassword());
/*     */         
/*     */     
/* 212 */     laPassword = this.TxtPassword.getPassword();
/* 213 */     lsPassword = new String(laPassword);
/*     */     
/* 215 */     LoginDAO luLoginDAO = null;
/* 216 */     LoginDTO luLoginDTO = null;
/*     */     
/*     */     try {
/* 219 */       luLoginDAO = new LoginDAO();
/*     */ 
/*     */ 
/*     */       
/* 223 */       if (laPassword.equals("")) {
					System.out.println("no trae pwd");
/* 224 */         throw new IllegalArgumentException("Se requiere password");
/*     */       }
/*     */ 
/*     */       
/* 228 */       String sPwdEncrypted = lsPassword;
/*     */ 
/*     */       //ECC TRATAR EXEPCION
/* 231 */       luLoginDTO = luLoginDAO.loginCorrect(lsPassword);
/*     */       
/* 233 */       lbValidateUserPwd = luLoginDTO.isPbValidoUsrPwd();
				//kaz
				//lbValidateUserPwd = true;
/* 234 */       if (lbValidateUserPwd) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 239 */         SessionApplication session = new SessionApplication();
/*     */ 
/*     */         
/* 242 */         session.setLogin(luLoginDTO);
/*     */ 
/*     */ 
/*     */         
/* 246 */         System.out.println("Usuario Password Login Form: " + SessionApplication.loginDTO.getPsPassword());
/*     */         
/* 248 */         this.LblMessageError.setEnabled(false);
/* 249 */         System.out.println("Es correcto  ");
/* 250 */         this.LblMessageError.setVisible(false);
/* 251 */         setVisible(false);
/* 252 */         StaticContext.lbValidoUserPwd = luLoginDTO.isPbValidoUsrPwd();
/* 253 */         System.out.println("Usuario Password StaticContext: " + StaticContext.lbValidoUserPwd);
/*     */         
/* 255 */         System.out.println("Usuario Password En Main: " + StaticContext.lbValidoUserPwd);
/* 256 */         if (luCargaTercerosFiscalFrm == null) {
/* 257 */           luCargaTercerosFiscalFrm = new CargaTercerosFiscalFrm();
/* 258 */           MainDesktop.setJInternalFrame(luCargaTercerosFiscalFrm);
/* 259 */           luCargaTercerosFiscalFrm.setVisible(true);
/*     */         }
/*     */         else {
/*     */           
/* 263 */           luCargaTercerosFiscalFrm.setClosed(true);
/* 264 */           luCargaTercerosFiscalFrm = new CargaTercerosFiscalFrm();
/* 265 */           MainDesktop.setJInternalFrame(luCargaTercerosFiscalFrm);
/* 266 */           luCargaTercerosFiscalFrm.setVisible(true);
/*     */         } 
/* 268 */         int myInt = StaticContext.lbValidoUserPwd ? 1 : 0;
/* 269 */         System.out.println("Num Reg Num:  " + myInt);
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
/* 283 */       else if (luLoginDTO.getPiTipoError() == 1) {
/* 284 */         this.LblMessageError.setText("Contraseña no es valida.");
/* 285 */         this.LblMessageError.setEnabled(true);
/*     */         
/* 287 */         this.LblMessageError.setVisible(true);
/* 288 */       } else if (luLoginDTO.getPiTipoError() == 2) {
/* 289 */         this.LblMessageError.setText("Usuario y/o Contraseña no son validos.");
/* 290 */         this.LblMessageError.setEnabled(true);
/*     */         
/* 292 */         this.LblMessageError.setVisible(true);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 298 */     catch (IllegalArgumentException iaex) {
/*     */       
/* 300 */       MessageBoxes.warning(iaex.getMessage());
/*     */     }
/* 302 */     catch (Exception ex) {
/*     */       
/* 304 */       MessageBoxes.error(ex);
/*     */     } 
/*     */   }
/*     */ }

