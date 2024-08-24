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
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JPasswordField;
/*     */ import javax.swing.JRadioButton;
/*     */ import javax.swing.JTextField;
/*     */ import mx.com.televisa.cfdis.model.DAO.LoginDAO;
/*     */ import mx.com.televisa.cfdis.model.DTO.LoginDTO;
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
/*     */ public class InsertUserFrm
/*     */   extends JInternalFrame
/*     */ {
/*     */   Dimension screenSize;
/*     */   Dimension windowSize;
/*  50 */   private JLabel LblUser = new JLabel();
/*  51 */   private JTextField TxtUser = new JTextField();
/*  52 */   private JLabel LblPassword = new JLabel();
/*  53 */   private JPasswordField TxtPassword = new JPasswordField();
/*  54 */   private JLabel LblConfirPassword = new JLabel();
/*  55 */   private JPasswordField TxtConfirmPassword = new JPasswordField();
/*  56 */   private JLabel LblRolType = new JLabel();
/*  57 */   private JRadioButton RdbAdministrative = new JRadioButton();
/*  58 */   private JRadioButton RdbOperativo = new JRadioButton();
/*  59 */   private JLabel LblMessageError = new JLabel();
/*  60 */   private JButton BtnSave = new JButton();
/*  61 */   private JPanel PnlAdminUser = new JPanel();
/*  62 */   private JButton BtnCancel = new JButton();
/*     */   
/*     */   public InsertUserFrm() {
/*     */     try {
/*  66 */       jbInit();
/*     */ 
/*     */       
/*  69 */       this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/*  70 */       this.windowSize = getSize();
/*  71 */       int windowX = Math.max(0, (this.screenSize.width - this.windowSize.width) / 2);
/*  72 */       int windowY = Math.max(0, (this.screenSize.height - this.windowSize.height) / 2);
/*  73 */       setLocation(windowX, windowY);
/*     */     }
/*  75 */     catch (Exception e) {
/*  76 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void jbInit() {
/*  81 */     System.out.println("Entro InsertUserFrm");
/*     */     
/*  83 */     getContentPane().setLayout(null);
/*  84 */     setSize(new Dimension(454, 340));
/*     */     
/*  86 */     this.BtnSave.setText("Guardar");
/*  87 */     this.BtnSave.setBounds(new Rectangle(315, 245, 120, 20));
/*     */     
/*  89 */     this.BtnSave.setSize(new Dimension(120, 25));
/*     */     
/*  91 */     this.BtnSave.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/*  95 */             InsertUserFrm.this.BtnSave_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/*  99 */     this.PnlAdminUser.setBounds(new Rectangle(5, 20, 440, 220));
/* 100 */     this.PnlAdminUser.setLayout(null);
/*     */     
/* 102 */     this.BtnCancel.setText("Cancelar");
/* 103 */     this.BtnCancel.setBounds(new Rectangle(180, 245, 75, 21));
/* 104 */     this.BtnCancel.setPreferredSize(new Dimension(73, 21));
/* 105 */     this.BtnCancel.setSize(new Dimension(120, 25));
/* 106 */     this.BtnCancel.setMinimumSize(new Dimension(73, 21));
/* 107 */     this.BtnCancel.setMaximumSize(new Dimension(73, 21));
/*     */     
/* 109 */     this.BtnCancel.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 113 */             InsertUserFrm.this.BtnCancel_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 117 */     getContentPane().add(this.BtnCancel, null);
/* 118 */     getContentPane().add(this.PnlAdminUser, null);
/*     */ 
/*     */     
/* 121 */     getContentPane().add(this.BtnSave, "South");
/* 122 */     this.LblMessageError.setBounds(new Rectangle(25, 185, 375, 15));
/* 123 */     this.RdbAdministrative.setText("Administrativo");
/* 124 */     this.RdbAdministrative.setBounds(new Rectangle(165, 150, 110, 20));
/*     */     
/* 126 */     this.RdbAdministrative.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 130 */             InsertUserFrm.this.RdbAdministrative_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 134 */     this.RdbOperativo.setText("Operativo");
/* 135 */     this.RdbOperativo.setBounds(new Rectangle(330, 150, 86, 18));
/* 136 */     this.RdbOperativo.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e)
/*     */           {
/* 140 */             InsertUserFrm.this.RdbOperativo_actionPerformed(e);
/*     */           }
/*     */         });
/*     */     
/* 144 */     this.LblConfirPassword.setText("Confirmar Contraseña:");
/* 145 */     this.LblConfirPassword.setBounds(new Rectangle(15, 110, 150, 15));
/* 146 */     this.LblConfirPassword.setFont(new Font("Tahoma", 1, 11));
/* 147 */     this.TxtConfirmPassword.setBounds(new Rectangle(195, 110, 155, 25));
/* 148 */     this.TxtConfirmPassword.setSize(new Dimension(155, 25));
/*     */     
/* 150 */     this.TxtConfirmPassword.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e)
/*     */           {
/* 154 */             InsertUserFrm.this.TxtConfirmPassword_mouseClicked(e);
/*     */           }
/*     */         });
/*     */     
/* 158 */     this.LblRolType.setText("Tipo de Rol:");
/* 159 */     this.LblRolType.setBounds(new Rectangle(15, 155, 120, 15));
/* 160 */     this.LblRolType.setFont(new Font("Tahoma", 1, 11));
/* 161 */     this.TxtPassword.setBounds(new Rectangle(195, 70, 155, 25));
/* 162 */     this.TxtPassword.setSize(new Dimension(155, 25));
/*     */     
/* 164 */     this.TxtPassword.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e)
/*     */           {
/* 168 */             InsertUserFrm.this.TxtPassword_mouseClicked(e);
/*     */           }
/*     */         });
/*     */     
/* 172 */     this.TxtUser.setBounds(new Rectangle(195, 30, 155, 20));
/* 173 */     this.TxtUser.setSize(new Dimension(155, 25));
/*     */     
/* 175 */     this.TxtUser.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e)
/*     */           {
/* 179 */             InsertUserFrm.this.TxtUser_mouseClicked(e);
/*     */           }
/*     */         });
/*     */     
/* 183 */     this.LblPassword.setText("Contraseña:");
/* 184 */     this.LblPassword.setBounds(new Rectangle(15, 70, 120, 15));
/* 185 */     this.LblPassword.setFont(new Font("Tahoma", 1, 11));
/* 186 */     this.LblUser.setText("Usuario:");
/* 187 */     this.LblUser.setAlignmentX(50.0F);
/* 188 */     this.LblUser.setAlignmentY(1.0F);
/* 189 */     this.LblUser.setBounds(new Rectangle(15, 30, 120, 15));
/*     */     
/* 191 */     this.LblUser.setFont(new Font("Tahoma", 1, 11));
/* 192 */     this.RdbAdministrative.setSelected(false);
/*     */     
/* 194 */     this.RdbAdministrative.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e)
/*     */           {
/* 198 */             InsertUserFrm.this.RdbAdministrative_mouseClicked(e);
/*     */           }
/*     */         });
/*     */     
/* 202 */     this.RdbOperativo.setSelected(true);
/*     */     
/* 204 */     this.RdbOperativo.addMouseListener(new MouseAdapter()
/*     */         {
/*     */           public void mouseClicked(MouseEvent e)
/*     */           {
/* 208 */             InsertUserFrm.this.RdbOperativo_mouseClicked(e);
/*     */           }
/*     */         });
/*     */     
/* 212 */     this.PnlAdminUser.add(this.LblUser, null);
/* 213 */     this.PnlAdminUser.add(this.TxtUser, null);
/* 214 */     this.PnlAdminUser.add(this.LblPassword, null);
/* 215 */     this.PnlAdminUser.add(this.TxtPassword, null);
/* 216 */     this.PnlAdminUser.add(this.LblConfirPassword, null);
/* 217 */     this.PnlAdminUser.add(this.TxtConfirmPassword, null);
/* 218 */     this.PnlAdminUser.add(this.LblRolType, null);
/* 219 */     this.PnlAdminUser.add(this.RdbAdministrative, null);
/* 220 */     this.PnlAdminUser.add(this.RdbOperativo, null);
/* 221 */     this.PnlAdminUser.add(this.LblMessageError, null);
/*     */   }
/*     */ 
/*     */   
/*     */   private void BtnSave_actionPerformed(ActionEvent e) {
/*     */     try {
/* 227 */       String lsUser = null;
/* 228 */       String lsPassword = null;
/* 229 */       char[] laPassword = null;
/*     */       
/* 231 */       String lsConfirmPassword = null;
/* 232 */       char[] laConfirmPassword = null;
/*     */       
/* 234 */       lsUser = this.TxtUser.getText();
/*     */       
/* 236 */       laPassword = this.TxtPassword.getPassword();
/* 237 */       lsPassword = new String(laPassword);
/*     */       
/* 239 */       laConfirmPassword = this.TxtConfirmPassword.getPassword();
/* 240 */       lsConfirmPassword = new String(laConfirmPassword);
/*     */       
/* 242 */       if (lsUser.equals("*****") || lsPassword.equals("*****") || lsConfirmPassword.equals("*****")) {
/* 243 */         System.out.println("Pintan asteriscos");
/*     */       }
/* 245 */       else if (lsUser.equals("") || lsPassword.equals("") || lsConfirmPassword.equals("")) {
/* 246 */         System.out.println("Si blancos");
/* 247 */         this.LblMessageError.setText("Ninguno de los campos debe contener nulos");
/* 248 */         this.LblMessageError.setForeground(Color.RED);
/* 249 */         this.LblMessageError.setVisible(true);
/*     */       } else {
/* 251 */         System.out.println("No blancos - y demas codigo");
/*     */         
/* 253 */         if (lsPassword.equals(lsConfirmPassword)) {
/* 254 */           LoginDAO luLoginDAO = new LoginDAO();
/* 255 */           LoginDTO luLoginDTO = new LoginDTO();
/*     */           
/* 257 */           luLoginDTO.setPsUser(lsUser);
/* 258 */           luLoginDTO.setPsPassword(lsPassword);
/*     */           
/* 260 */           if (this.RdbAdministrative.isSelected() && !this.RdbOperativo.isSelected()) {
/* 261 */             luLoginDTO.setPsRol("ROL_ADMIN");
/* 262 */             System.out.println("Activado Administrativo");
/*     */           } 
/* 264 */           if (this.RdbOperativo.isSelected() && !this.RdbAdministrative.isSelected()) {
/* 265 */             luLoginDTO.setPsRol("ROL_OPE");
/* 266 */             System.out.println("Activado Operativo");
/*     */           } 
/*     */           
/* 269 */           int liReturn = luLoginDAO.executeInsert(luLoginDTO);
/* 270 */           System.out.println(liReturn);
/*     */ 
/*     */           
/* 273 */           if (liReturn == 1) {
/* 274 */             this.LblMessageError.setText("Haz agregado un nuevo usuario satisfactoriamnte");
/* 275 */             this.LblMessageError.setForeground(Color.GREEN);
/* 276 */             this.LblMessageError.setVisible(true);
/*     */           } else {
/* 278 */             this.LblMessageError.setText("Error Oracle DataBase");
/* 279 */             this.LblMessageError.setForeground(Color.RED);
/* 280 */             this.LblMessageError.setVisible(true);
/*     */           } 
/*     */ 
/*     */           
/* 284 */           this.TxtUser.setText("");
/* 285 */           this.TxtPassword.setText("");
/* 286 */           this.TxtConfirmPassword.setText("");
/* 287 */           this.RdbAdministrative.setSelected(false);
/* 288 */           this.RdbOperativo.setSelected(true);
/*     */         } else {
/*     */           
/* 291 */           this.LblMessageError.setText("Las contraseñas no coinciden, ¿Quieres volver a intentarlo?");
/* 292 */           this.LblMessageError.setForeground(Color.RED);
/* 293 */           this.LblMessageError.setVisible(true);
/*     */           
/* 295 */           this.TxtPassword.setText("");
/* 296 */           this.TxtConfirmPassword.setText("");
/*     */           
/* 298 */           System.out.println("No hizo update");
/*     */         }
/*     */       
/*     */       } 
/* 302 */     } catch (Exception exception) {}
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void RdbAdministrative_actionPerformed(ActionEvent e) {
/* 308 */     if (this.RdbAdministrative.isSelected()) {
/* 309 */       this.RdbAdministrative.setSelected(true);
/* 310 */       this.RdbOperativo.setSelected(false);
/*     */     } else {
/* 312 */       this.RdbAdministrative.setSelected(true);
/* 313 */       this.RdbOperativo.setSelected(false);
/*     */     } 
/*     */   }
/*     */   
/*     */   private void RdbOperativo_actionPerformed(ActionEvent e) {
/* 318 */     if (this.RdbOperativo.isSelected()) {
/* 319 */       this.RdbOperativo.setSelected(true);
/* 320 */       this.RdbAdministrative.setSelected(false);
/*     */     } else {
/* 322 */       this.RdbOperativo.setSelected(true);
/* 323 */       this.RdbAdministrative.setSelected(false);
/*     */     } 
/*     */   }
/*     */ 
/*     */   
/* 328 */   private void BtnCancel_actionPerformed(ActionEvent e) { setVisible(false); }
/*     */ 
/*     */ 
/*     */   
/* 332 */   private void TxtUser_mouseClicked(MouseEvent e) { this.LblMessageError.setVisible(false); }
/*     */ 
/*     */ 
/*     */   
/* 336 */   private void TxtPassword_mouseClicked(MouseEvent e) { this.LblMessageError.setVisible(false); }
/*     */ 
/*     */ 
/*     */   
/* 340 */   private void TxtConfirmPassword_mouseClicked(MouseEvent e) { this.LblMessageError.setVisible(false); }
/*     */ 
/*     */ 
/*     */   
/* 344 */   private void RdbAdministrative_mouseClicked(MouseEvent e) { this.LblMessageError.setVisible(false); }
/*     */ 
/*     */ 
/*     */   
/* 348 */   private void RdbOperativo_mouseClicked(MouseEvent e) { this.LblMessageError.setVisible(false); }
/*     */ }
