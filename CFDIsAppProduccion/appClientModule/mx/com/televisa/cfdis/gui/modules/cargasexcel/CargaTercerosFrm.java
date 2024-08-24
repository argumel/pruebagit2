/*     */ package mx.com.televisa.cfdis.gui.modules.cargasexcel;
/*     */ 
/*     */ import java.awt.Dimension;
/*     */ import java.awt.Font;
/*     */ import java.awt.Rectangle;
/*     */ import java.awt.Toolkit;
/*     */ import java.awt.event.ActionEvent;
/*     */ import java.awt.event.ActionListener;
import java.util.ArrayList;

/*     */ import javax.swing.BorderFactory;
/*     */ import javax.swing.JButton;
/*     */ import javax.swing.JFileChooser;
/*     */ import javax.swing.JInternalFrame;
/*     */ import javax.swing.JLabel;
/*     */ import javax.swing.JPanel;
/*     */ import javax.swing.JTabbedPane;
/*     */ import javax.swing.JTextField;
/*     */ import javax.swing.filechooser.FileNameExtensionFilter;
/*     */ import mx.com.televisa.cfdis.gui.common.MessageBoxes;
/*     */ import mx.com.televisa.cfdis.process.cargasexcel.CargarInfoRetTer;
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
/*     */ public class CargaTercerosFrm
/*     */   extends JInternalFrame
/*     */ {
/*  46 */   private JPanel pnlTabRetencionesTerceros = new JPanel();
/*     */   
/*     */   Dimension screenSize;
/*     */   
/*     */   Dimension windowSize;
/*  51 */   private JPanel pnlRetencionesTerceros = new JPanel();
/*  52 */   private JTabbedPane pnlTabs = new JTabbedPane();
/*  53 */   private JLabel lblArchivoTerceros = new JLabel();
/*  54 */   private JTextField txtArchivoTerceros = new JTextField();
/*  55 */   private JButton btnExaminarTerceros = new JButton();
/*  56 */   private JButton btnCargarTerceros = new JButton();
/*     */ 
/*     */ 
/*     */   
/*     */   public CargaTercerosFrm() {
/*     */     try {
/*  62 */       jbInit();
/*  63 */       customInit();
/*     */     }
/*  65 */     catch (Exception e) {
/*  66 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void jbInit() {
/*  71 */     setSize(new Dimension(668, 254));
/*  72 */     getContentPane().setLayout(null);
/*  73 */     setResizable(false);
/*  74 */     setMaximizable(false);
/*  75 */     setClosable(true);
/*  76 */     setTitle("Cargas de Excel");
/*     */ 
/*     */     
/*  79 */     this.pnlTabRetencionesTerceros.setLayout(null);
/*  80 */     this.pnlTabs.setBounds(new Rectangle(10, 10, 645, 210));
/*  81 */     this.lblArchivoTerceros.setText("Archivo de Retenciones (Excel):");
/*  82 */     this.lblArchivoTerceros.setBounds(new Rectangle(25, 30, 180, 20));
/*  83 */     this.lblArchivoTerceros.setFont(new Font("Tahoma", 0, 11));
/*  84 */     this.txtArchivoTerceros.setBounds(new Rectangle(45, 55, 380, 30));
/*  85 */     this.btnExaminarTerceros.setText("Examinar");
/*  86 */     this.btnExaminarTerceros.setBounds(new Rectangle(435, 55, 100, 30));
/*     */     
/*  88 */     this.btnExaminarTerceros.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/*  91 */             CargaTercerosFrm.this.btnExaminarTerceros_actionPerformed(e);
/*     */           }
/*     */         });
/*  94 */     this.btnCargarTerceros.setText("Cargar");
/*  95 */     this.btnCargarTerceros.setBounds(new Rectangle(500, 110, 95, 30));
/*  96 */     this.btnCargarTerceros.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/*  99 */             CargaTercerosFrm.this.btnCargarTerceros_actionPerformed(e);
/*     */           }
/*     */         });
/* 102 */     this.pnlRetencionesTerceros.setLayout(null);
/* 103 */     this.pnlRetencionesTerceros.setBorder(BorderFactory.createTitledBorder("Carga de Archivo de Excel"));
/* 104 */     this.pnlRetencionesTerceros.setBounds(new Rectangle(10, 10, 615, 170));
/* 105 */     this.pnlRetencionesTerceros.add(this.btnCargarTerceros, null);
/* 106 */     this.pnlRetencionesTerceros.add(this.btnExaminarTerceros, null);
/* 107 */     this.pnlRetencionesTerceros.add(this.txtArchivoTerceros, null);
/* 108 */     this.pnlRetencionesTerceros.add(this.lblArchivoTerceros, null);
/* 109 */     this.pnlTabRetencionesTerceros.add(this.pnlRetencionesTerceros, null);
/* 110 */     this.pnlTabs.addTab("Retenciones Terceros", this.pnlTabRetencionesTerceros);
/* 111 */     getContentPane().add(this.pnlTabs, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void customInit() {
/* 117 */     this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 118 */     this.windowSize = getSize();
/* 119 */     int windowX = Math.max(0, (this.screenSize.width - this.windowSize.width) / 2);
/* 120 */     int windowY = Math.max(0, (this.screenSize.height - this.windowSize.height) / 2);
/* 121 */     setLocation(windowX, windowY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void btnExaminarTerceros_actionPerformed(ActionEvent e) {
/*     */     try {
/* 131 */       JFileChooser chooser = new JFileChooser();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 138 */       FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Excel (xlsx)", new String[] { "xlsx" });
/* 139 */       chooser.setFileFilter(filter);
/*     */       
/* 141 */       int returnVal = chooser.showOpenDialog(this);
/*     */       
/* 143 */       if (returnVal == 0)
/*     */       {
/* 145 */         this.txtArchivoTerceros.setText(chooser.getSelectedFile().getAbsolutePath());
/*     */       
/*     */       }
/*     */     }
/* 149 */     catch (IllegalArgumentException iaex) {
/*     */       
/* 151 */       MessageBoxes.warning(iaex.getMessage());
/*     */     }
/* 153 */     catch (Exception ex) {
/*     */       
/* 155 */       MessageBoxes.error(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void btnCargarTerceros_actionPerformed(ActionEvent e) {
/*     */     try {
/* 164 */       ArrayList<String> result = CargarInfoRetTer.doCargarInfoRetTer(this.txtArchivoTerceros.getText());
/* 165 */       result.add("El detalle de las validaciones se envian por correo");
					MessageBoxes.message(result);
/*     */     }
/* 167 */     catch (IllegalArgumentException iaex) {
/*     */       
/* 169 */       MessageBoxes.warning(iaex.getMessage());
/*     */     }
/* 171 */     catch (Exception ex) {
/*     */       
/* 173 */       MessageBoxes.error(ex);
/*     */     } 
/*     */   }
/*     */ }

