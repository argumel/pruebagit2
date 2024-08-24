/*     */ package mx.com.televisa.cfdis.gui.modules.login;
/*     */ 
/*     */ import java.awt.Color;
/*     */ import java.awt.Dimension;
/*     */ import java.awt.EventQueue;
/*     */ import java.awt.Font;
/*     */ import java.awt.Frame;
/*     */ import java.awt.Image;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
/*     */ import java.awt.event.WindowAdapter;
/*     */ import java.awt.event.WindowEvent;
/*     */ import javax.swing.ImageIcon;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JDialog;
/*     */ import javax.swing.JFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JOptionPane;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.UIManager;
/*     */ import javax.swing.UnsupportedLookAndFeelException;
/*     */ import mx.com.televisa.cfdis.gui.main.MainDesktop;
/*     */ import mx.com.televisa.cfdis.model.DAO.LoginDAO;
/*     */ import mx.com.televisa.cfdis.model.DTO.LoginDTO;
/*     */ import mx.com.televisa.cfdis.util.SessionApplication;

/*     */ public class Login
/*     */   extends JDialog
/*     */ {
/*     */   private JLabel lblImagen;
/*     */   private JLabel LblUser;
/*     */   private JLabel LblPassword;
/*     */   private JButton BtnAccept;
/*     */   private JButton BtnCancelar;
/*     */   private JPasswordField TxtPassword;
/*     */   private JTextField TxtUser;
/*     */   private JLabel LblMessageError;
/*     */   private JLabel lblObiSerpelApp;
/*     */   
/*     */   public Login(Frame parent, String title, boolean modal) {
/*  66 */     this.lblImagen = new JLabel();
 
/* 280 */     this.LblUser = new JLabel();
/* 281 */     this.LblPassword = new JLabel();
/* 282 */     this.BtnAccept = new JButton();
/* 283 */     this.BtnCancelar = new JButton();
/* 284 */     this.TxtPassword = new JPasswordField();
/* 285 */     this.TxtUser = new JTextField();
/* 286 */     this.LblMessageError = new JLabel();
/*     */ 
/*     */     
/* 289 */     this.lblObiSerpelApp = new JLabel();
/*     */     setTitle(title);
/*     */     try {
/*     */       jbInit();
/*     */       setImagenLbl();
/*     */     } catch (Exception e) {
/*     */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void jbInit() throws Exception {
/*     */     setSize(new Dimension(366, 257));
/*     */     getContentPane().setLayout(null);
/*     */     setDefaultCloseOperation(1);
/*     */     setResizable(true);
/*     */     this.LblUser.setText("Usuario: ");
/*     */     this.LblUser.setBounds(new Rectangle(20, 75, 80, 15));
/*     */     this.LblUser.setFont(new Font("Tahoma", 3, 11));
/*     */     this.LblPassword.setText("Contrase–a:");
/*     */     this.LblPassword.setBounds(new Rectangle(15, 130, 80, 15));
/*     */     this.LblPassword.setFont(new Font("Tahoma", 3, 11));
/*     */     this.BtnAccept.setText("Aceptar");
/*     */     this.BtnAccept.setBounds(new Rectangle(110, 180, 85, 20));
/*     */     this.BtnAccept.setFont(new Font("Tahoma", 1, 11));
/*     */     this.BtnAccept.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) { Login.this.getBtnAcceptActionPerformed(e); }
/*     */         });
/*     */     this.BtnCancelar.setText("Cancelar");
/*     */     this.BtnCancelar.setBounds(new Rectangle(210, 180, 85, 20));
/*     */     this.BtnCancelar.setFont(new Font("Tahoma", 1, 11));
/*     */     this.BtnCancelar.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) { Login.this.BtnCancelar_actionPerformed(e); }
/*     */         });
/*     */     this.TxtPassword.setBounds(new Rectangle(110, 120, 220, 25));
/*     */     this.TxtPassword.addActionListener(new ActionListener() {
/*     */           public void actionPerformed(ActionEvent e) { Login.this.TxtPassword_actionPerformed(e); }
/*     */         });
/*     */     this.TxtUser.setBounds(new Rectangle(110, 65, 220, 25));
/*     */     this.LblMessageError.setVisible(false);
/*     */     this.LblMessageError.setText("Usuario y/o Contraseña no son validos.");
/*     */     this.LblMessageError.setBounds(new Rectangle(45, 155, 295, 15));
/*     */     this.LblMessageError.setForeground(Color.red);
/*     */     this.lblObiSerpelApp.setText("ObiSerpelApp");
/*     */     this.lblObiSerpelApp.setBounds(new Rectangle(115, 25, 130, 25));
/*     */     this.lblObiSerpelApp.setFont(new Font("Tahoma", 3, 18));
/*     */     this.lblImagen.setBounds(new Rectangle(15, 220, 85, 25));
/*     */     getContentPane().add(this.lblImagen, null);
/*     */     getContentPane().add(this.lblObiSerpelApp, null);
/*     */     getContentPane().add(this.LblMessageError, null);
/*     */     getContentPane().add(this.TxtUser, null);
/*     */     getContentPane().add(this.TxtPassword, null);
/*     */     getContentPane().add(this.BtnCancelar, null);
/*     */     getContentPane().add(this.BtnAccept, null);
/*     */     getContentPane().add(this.LblPassword, null);
/*     */     getContentPane().add(this.LblUser, null);
/*     */   }
/*     */   
/*     */   public static void main(String[] args) {
/*     */     EventQueue.invokeLater(new Runnable() {
/*     */           Image im = Toolkit.getDefaultToolkit().getImage("src/Locked.png");
/*     */           
/*     */           public void run() {
/*     */             Login dialog = new Login(new JFrame(), "Login", true);
/*     */             dialog.addWindowListener(new WindowAdapter() {
/*     */                   public void windowClosing(WindowEvent e) { System.exit(0); }
/*     */                 });
/*     */             dialog.setResizable(false);
/*     */             dialog.setLocation(450, 250);
/*     */             dialog.setVisible(true);
/*     */           }
/*     */         });
/*     */     byte b;
/*     */     int i;
/*     */     UIManager.LookAndFeelInfo[] arrayOfLookAndFeelInfo = new UIManager.LookAndFeelInfo[UIManager.getInstalledLookAndFeels().length];
/*     */     for (i = UIManager.getInstalledLookAndFeels().length, b = 0; b < i; ) {
/*     */       UIManager.LookAndFeelInfo info = arrayOfLookAndFeelInfo[b];
/*     */       if ("Nimbus".equals(info.getName()))
/*     */         try {
/*     */           UIManager.setLookAndFeel(info.getClassName());
/*     */           break;
/*     */         } catch (ClassNotFoundException classNotFoundException) {
/*     */           break;
/*     */         } catch (InstantiationException instantiationException) {
/*     */           break;
/*     */         } catch (IllegalAccessException illegalAccessException) {
/*     */           break;
/*     */         } catch (UnsupportedLookAndFeelException unsupportedLookAndFeelException) {
/*     */           break;
/*     */         }  
/*     */       b++;
/*     */     } 
/*     */   }
/*     */   
/*     */   private void getBtnAcceptActionPerformed(ActionEvent tActionEvent) { try {
	executeLogin();
} catch (Exception e) {
	// TODO Auto-generated catch block
	e.printStackTrace();
} }
/*     */   
/*     */   private void executeLogin() throws Exception {
/*     */     String lsUser = null;
/*     */     String lsPassword = null;
/*     */     char[] laPassword = null;
/*     */     boolean lbValidateUserPwd = false;
/*     */     lsUser = this.TxtUser.getText();
/*     */     laPassword = this.TxtPassword.getPassword();
/*     */     lsPassword = new String(laPassword);
/*     */     LoginDAO luLoginDAO = null;
/*     */     LoginDTO luLoginDTO = null;
/*     */     try {
/*     */       luLoginDAO = new LoginDAO();
/*     */       luLoginDTO = luLoginDAO.loginCorrect(lsUser, lsPassword);
/*     */       lbValidateUserPwd = luLoginDTO.isPbValidoUsrPwd();
/*     */       if (lbValidateUserPwd) {
/*     */         SessionApplication session = new SessionApplication();
/*     */         session.setLogin(luLoginDTO);
/*     */         this.LblMessageError.setEnabled(false);
/*     */         System.out.println("Es correcto  ");
/*     */         this.LblMessageError.setVisible(false);
/*     */         setVisible(false);
/*     */         (new MainDesktop(luLoginDTO)).setVisible(true);
/*     */         luLoginDAO.insertStatus(lsUser, lsPassword, 1);
/*     */       } else if (luLoginDTO.getPiTipoError() == 0) {
/*     */         JOptionPane.showMessageDialog(null, "Ya existe una sesión activa con el usuario: " + luLoginDTO.getPsUser(), "Error", 0);
/*     */         this.LblMessageError.setText("Ya existe una sesión activa con el usuario: " + luLoginDTO.getPsUser());
/*     */         this.LblMessageError.setEnabled(true);
/*     */         this.LblMessageError.setVisible(true);
/*     */       } else if (luLoginDTO.getPiTipoError() == 1) {
/*     */         this.LblMessageError.setText("Usuario y/o Contraseña no son validos.");
/*     */         this.LblMessageError.setEnabled(true);
/*     */         this.LblMessageError.setVisible(true);
/*     */       } else if (luLoginDTO.getPiTipoError() == 2) {
/*     */         this.LblMessageError.setText("Usuario y/o Contraseña no son validos.");
/*     */         this.LblMessageError.setEnabled(true);
/*     */         this.LblMessageError.setVisible(true);
/*     */       } 
/*     */     } catch (Exception tException) {
/*     */       JOptionPane.showMessageDialog(null, "The Network Adapter could not establish the connection", "Mensaje", 0);
/*     */       tException.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private int validateStatus() { return 0; }
/*     */   
/*     */   private void BtnCancelar_actionPerformed(ActionEvent e) { System.exit(0); }
/*     */   
/*     */   private void TxtPassword_actionPerformed(ActionEvent e) { try {
	executeLogin();
} catch (Exception e1) {
	// TODO Auto-generated catch block
	e1.printStackTrace();
} }
/*     */   
/*     */   private void setImagenLbl() throws Exception {
/*     */     ImageIcon icon = new ImageIcon("src/Locked.jpg");
/*     */     this.lblImagen.setIcon(icon);
/*     */     this.lblImagen.setVisible(false);
/*     */   }
/*     */ }

