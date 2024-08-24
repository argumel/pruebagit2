/*     */ package mx.com.televisa.cfdis.gui.modules.login;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Font;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import mx.com.televisa.cfdis.gui.common.MessageBoxes;
/*     */ import mx.com.televisa.cfdis.gui.common.StaticContext;
/*     */ import mx.com.televisa.cfdis.gui.main.MainDesktop;
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
/*     */ public class LoginFrm
/*     */   extends JFrame
/*     */ {
/*  47 */   private JPanel jPanel1 = new JPanel();
/*  48 */   private JLabel lblObiSerpelApp = new JLabel();
/*  49 */   private JLabel LblPassword = new JLabel();
/*  50 */   private JPasswordField TxtPassword = new JPasswordField();
/*     */   
/*  52 */   private JButton BtnCancelar = new JButton();
/*  53 */   private JButton BtnAccept = new JButton();
/*  54 */   private JLabel LblMessageError = new JLabel();
/*     */ 
/*     */ 
/*     */   
/*     */   MainDesktop desktop;
/*     */ 
/*     */   
/*  61 */   private JLabel LblPassword1 = new JLabel();
/*     */   
/*     */   public LoginFrm() {
/*     */     try {
/*  65 */       jbInit();
/*  66 */       setVisible(true);
/*  67 */     } catch (Exception e) {
/*  68 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void jbInit() {
/*  73 */     setSize(new Dimension(395, 299));
/*  74 */     setLocation(450, 250);
/*  75 */     setTitle("Login");
/*  76 */     setResizable(false);
/*  77 */     this.jPanel1.setLayout(null);
/*  78 */     this.lblObiSerpelApp.setText("Cargas Fiscal");
/*  79 */     this.lblObiSerpelApp.setBounds(new Rectangle(85, 35, 220, 35));
/*  80 */     this.lblObiSerpelApp.setFont(new Font("Tahoma", 1, 24));
/*  81 */     this.lblObiSerpelApp.setForeground(new Color(0, 33, 115));
/*  82 */     this.LblPassword.setText("Contraseña:");
/*  83 */     this.LblPassword.setBounds(new Rectangle(70, 155, 70, 15));
/*  84 */     this.LblPassword.setFont(new Font("Tahoma", 0, 11));
/*  85 */     this.TxtPassword.setBounds(new Rectangle(155, 150, 150, 25));
/*     */     
/*  87 */     this.TxtPassword.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) {
/*  89 */             LoginFrm.this.TxtPassword_actionPerformed(e);
/*  90 */             LoginFrm.this.TxtPassword_actionPerformed(e);
/*     */           }
/*     */         });
/*     */ 
/*     */     
/*  95 */     this.BtnCancelar.setText("Cancelar");
/*  96 */     this.BtnCancelar.setBounds(new Rectangle(275, 230, 85, 20));
/*  97 */     this.BtnCancelar.setFont(new Font("Tahoma", 1, 11));
/*     */     
/*  99 */     this.BtnCancelar.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 102 */             LoginFrm.this.BtnCancelar_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 106 */     this.BtnAccept.setText("Aceptar");
/* 107 */     this.BtnAccept.setBounds(new Rectangle(165, 230, 80, 20));
/* 108 */     this.BtnAccept.setFont(new Font("Tahoma", 1, 11));
/*     */     
/* 110 */     this.BtnAccept.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 113 */             LoginFrm.this.getBtnAcceptActionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 117 */     this.LblMessageError.setVisible(false);
/* 118 */     this.LblMessageError.setText("Contraseña no son validos.");
/* 119 */     this.LblMessageError.setBounds(new Rectangle(35, 180, 295, 15));
/* 120 */     this.LblMessageError.setForeground(Color.red);
/*     */ 
/*     */ 
/*     */     
/* 124 */     this.LblPassword1.setBounds(new Rectangle(70, 115, 70, 15));
/* 125 */     this.LblPassword1.setFont(new Font("Tahoma", 0, 11));
/*     */     
/* 127 */     this.jPanel1.add(this.LblPassword1, null);
/* 128 */     this.jPanel1.add(this.LblMessageError, null);
/* 129 */     this.jPanel1.add(this.BtnAccept, null);
/* 130 */     this.jPanel1.add(this.BtnCancelar, null);
/*     */ 
/*     */     
/* 133 */     this.jPanel1.add(this.TxtPassword, null);
/* 134 */     this.jPanel1.add(this.LblPassword, null);
/* 135 */     this.jPanel1.add(this.lblObiSerpelApp, null);
/* 136 */     this.jPanel1.add((new LoginBackground()).getPanel());
/* 137 */     this.jPanel1.repaint();
/* 138 */     getContentPane().add(this.jPanel1, null);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   public static void main(String[] s) {
/* 146 */     EventQueue.invokeLater(new Runnable() {
/* 147 */           Image im = Toolkit.getDefaultToolkit().getImage("src/Locked.png");
/*     */           
/* 149 */           public void run() { LoginFrm loginFrm = new LoginFrm();
/* 150 */             loginFrm.addWindowListener(new WindowAdapter()
/*     */                 {
/* 152 */                   public void windowClosing(WindowEvent e) { System.exit(0); }
/*     */                 }); }
/*     */         });
/*     */     byte b;
/*     */     int i;
/*     */     UIManager.LookAndFeelInfo[] arrayOfLookAndFeelInfo = new UIManager.LookAndFeelInfo[UIManager.getInstalledLookAndFeels().length];
/* 158 */     for (i = UIManager.getInstalledLookAndFeels().length, b = 0; b < i; ) { UIManager.LookAndFeelInfo info = arrayOfLookAndFeelInfo[b];
/*     */       
/* 160 */       if ("Nimbus".equals(info.getName())) {
/*     */         
/* 162 */         try { UIManager.setLookAndFeel(info.getClassName()); }
/* 163 */         catch (ClassNotFoundException classNotFoundException) {  }
/* 164 */         catch (InstantiationException instantiationException) {  }
/* 165 */         catch (IllegalAccessException illegalAccessException) {  }
/* 166 */         catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {}
/*     */       }
/*     */       b++; }
/*     */   
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 176 */   private void getBtnAcceptActionPerformed(ActionEvent tActionEvent) { executeLogin(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/* 182 */   private void BtnCancelar_actionPerformed(ActionEvent e) { System.exit(0); }
/*     */ 
/*     */ 
/*     */   
/* 186 */   private void TxtPassword_actionPerformed(ActionEvent e) { executeLogin(); }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void executeLogin() {
/* 193 */     String lsUser = null;
/* 194 */     String lsPassword = null;
/* 195 */     char[] laPassword = null;
/* 196 */     boolean lbValidateUserPwd = false;
/*     */ 
/*     */ 
/*     */     
/* 200 */     laPassword = this.TxtPassword.getPassword();
/* 201 */     lsPassword = new String(laPassword);
/*     */     
/* 203 */     LoginDAO luLoginDAO = null;
/* 204 */     LoginDTO luLoginDTO = null;
/*     */     
/*     */     try {
/* 207 */       luLoginDAO = new LoginDAO();
/*     */ 
/*     */ 
/*     */       
/* 211 */       if (laPassword.equals("")) {
/* 212 */         throw new IllegalArgumentException("Se requiere password");
/*     */       }
/*     */ 
/*     */       
/* 216 */       String sPwdEncrypted = lsPassword;
/*     */ 
/*     */       
/* 219 */       luLoginDTO = luLoginDAO.loginCorrect(lsPassword);
/*     */       
/* 221 */       lbValidateUserPwd = luLoginDTO.isPbValidoUsrPwd();
/* 222 */       if (lbValidateUserPwd) {
/*     */ 
/*     */ 
/*     */ 
/*     */         
/* 227 */         SessionApplication session = new SessionApplication();
/*     */ 
/*     */         
/* 230 */         session.setLogin(luLoginDTO);
/*     */ 
/*     */ 
/*     */         
/* 234 */         System.out.println("Usuario Password Login Form: " + SessionApplication.loginDTO.getPsPassword());
/*     */         
/* 236 */         this.LblMessageError.setEnabled(false);
/* 237 */         System.out.println("Es correcto  ");
/* 238 */         this.LblMessageError.setVisible(false);
/* 239 */         setVisible(false);
/* 240 */         StaticContext.lbValidoUserPwd = luLoginDTO.isPbValidoUsrPwd();
/* 241 */         System.out.println("Usuario Password StaticContext: " + StaticContext.lbValidoUserPwd);
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/*     */       }
/* 251 */       else if (luLoginDTO.getPiTipoError() == 1) {
/* 252 */         this.LblMessageError.setText("Contraseña no es valida.");
/* 253 */         this.LblMessageError.setEnabled(true);
/*     */         
/* 255 */         this.LblMessageError.setVisible(true);
/* 256 */       } else if (luLoginDTO.getPiTipoError() == 2) {
/* 257 */         this.LblMessageError.setText("Usuario y/o Contraseña no son validos.");
/* 258 */         this.LblMessageError.setEnabled(true);
/*     */         
/* 260 */         this.LblMessageError.setVisible(true);
/*     */       
/*     */       }
/*     */ 
/*     */     
/*     */     }
/* 266 */     catch (IllegalArgumentException iaex) {
/*     */       
/* 268 */       MessageBoxes.warning(iaex.getMessage());
/*     */     }
/* 270 */     catch (Exception ex) {
/*     */       
/* 272 */       MessageBoxes.error(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 277 */   private int validateStatus() { return 0; }
/*     */ }

