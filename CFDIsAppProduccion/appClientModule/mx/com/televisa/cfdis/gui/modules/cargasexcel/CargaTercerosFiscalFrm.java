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
/*     */ import mx.com.televisa.cfdis.process.cargasexcel.CargarInfoFiscalRetTer;
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
/*     */ public class CargaTercerosFiscalFrm
/*     */   extends JInternalFrame
/*     */ {
/*  47 */   private JPanel pnlTabRetencionesTerceros = new JPanel();
/*     */   
/*     */   Dimension screenSize;
/*     */   
/*     */   Dimension windowSize;
/*  52 */   private JPanel pnlRetencionesTerceros = new JPanel();
/*  53 */   private JTabbedPane pnlTabs = new JTabbedPane();
/*  54 */   private JLabel lblArchivoTerceros = new JLabel();
/*  55 */   private JTextField txtArchivoTerceros = new JTextField();
/*  56 */   private JButton btnExaminarTerceros = new JButton();
/*  57 */   private JButton btnCargarTerceros = new JButton();
/*     */ 
/*     */ 
/*     */   
/*     */   public CargaTercerosFiscalFrm() {
/*     */     try {
/*  63 */       jbInit();
/*  64 */       customInit();
/*     */     }
/*  66 */     catch (Exception e) {
/*  67 */       e.printStackTrace();
/*     */     } 
/*     */   }
/*     */   
/*     */   private void jbInit() {
/*  72 */     setSize(new Dimension(668, 254));
/*  73 */     getContentPane().setLayout(null);
/*  74 */     setResizable(false);
/*  75 */     setMaximizable(false);
/*  76 */     setClosable(true);
/*  77 */     setTitle("Cargas de Excel Fiscal");
/*     */ 
/*     */     
/*  80 */     this.pnlTabRetencionesTerceros.setLayout(null);
/*  81 */     this.pnlTabs.setBounds(new Rectangle(10, 10, 645, 210));
/*  82 */     this.lblArchivoTerceros.setText("Archivo de Retenciones Terceros Fiscal Anual (Excel):");
/*     */     
/*  84 */     this.lblArchivoTerceros.setBounds(new Rectangle(25, 30, 180, 20));
/*  85 */     this.lblArchivoTerceros.setFont(new Font("Tahoma", 0, 11));
/*  86 */     this.txtArchivoTerceros.setBounds(new Rectangle(45, 55, 380, 30));
/*  87 */     this.btnExaminarTerceros.setText("Examinar");
/*  88 */     this.btnExaminarTerceros.setBounds(new Rectangle(435, 55, 100, 30));
/*     */     
/*  90 */     this.btnExaminarTerceros.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/*  93 */             CargaTercerosFiscalFrm.this.btnExaminarTerceros_actionPerformed(e);
/*     */           }
/*     */         });
/*  96 */     this.btnCargarTerceros.setText("Cargar");
/*  97 */     this.btnCargarTerceros.setBounds(new Rectangle(500, 110, 95, 30));
/*  98 */     this.btnCargarTerceros.addActionListener(new ActionListener()
/*     */         {
/*     */           public void actionPerformed(ActionEvent e) {
/* 101 */             CargaTercerosFiscalFrm.this.btnCargarTerceros_actionPerformed(e);
/*     */           }
/*     */         });
/* 104 */     this.pnlRetencionesTerceros.setLayout(null);
/* 105 */     this.pnlRetencionesTerceros.setBorder(BorderFactory.createTitledBorder("Carga de Archivo de Excel"));
/* 106 */     this.pnlRetencionesTerceros.setBounds(new Rectangle(10, 10, 615, 170));
/* 107 */     this.pnlRetencionesTerceros.add(this.btnCargarTerceros, null);
/* 108 */     this.pnlRetencionesTerceros.add(this.btnExaminarTerceros, null);
/* 109 */     this.pnlRetencionesTerceros.add(this.txtArchivoTerceros, null);
/* 110 */     this.pnlRetencionesTerceros.add(this.lblArchivoTerceros, null);
/* 111 */     this.pnlTabRetencionesTerceros.add(this.pnlRetencionesTerceros, null);
/* 112 */     this.pnlTabs.addTab("Retenciones Terceros Fiscal Anual", this.pnlTabRetencionesTerceros);
/* 113 */     getContentPane().add(this.pnlTabs, null);
/*     */   }
/*     */ 
/*     */ 
/*     */   
/*     */   private void customInit() {
/* 119 */     this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
/* 120 */     this.windowSize = getSize();
/* 121 */     int windowX = Math.max(0, (this.screenSize.width - this.windowSize.width) / 2);
/* 122 */     int windowY = Math.max(0, (this.screenSize.height - this.windowSize.height) / 2);
/* 123 */     setLocation(windowX, windowY);
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void btnExaminarTerceros_actionPerformed(ActionEvent e) {
/*     */     try {
/* 133 */       JFileChooser chooser = new JFileChooser();
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */ 
/*     */       
/* 140 */       FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos Excel (xlsx)", new String[] { "xlsx" });
/* 141 */       chooser.setFileFilter(filter);
/*     */       
/* 143 */       int returnVal = chooser.showOpenDialog(this);
/*     */       
/* 145 */       if (returnVal == 0)
/*     */       {
/* 147 */         this.txtArchivoTerceros.setText(chooser.getSelectedFile().getAbsolutePath());
/*     */       
/*     */       }
/*     */     }
/* 151 */     catch (IllegalArgumentException iaex) {
/*     */       
/* 153 */       MessageBoxes.warning(iaex.getMessage());
/*     */     }
/* 155 */     catch (Exception ex) {
/*     */       
/* 157 */       MessageBoxes.error(ex);
/*     */     } 
/*     */   }
/*     */ 
/*     */ 
/*     */ 
/*     */   
/*     */   private void btnCargarTerceros_actionPerformed(ActionEvent e) {
/*     */     try {
/* 166 */       ArrayList<String> result = CargarInfoFiscalRetTer.doCargarInfoFiscalRetTer(this.txtArchivoTerceros.getText());
/* 167 */       result.add("El detalle de las validaciones se envian por correo");
				MessageBoxes.message(result);
/*     */     }
/* 169 */     catch (IllegalArgumentException iaex) {
/*     */       
/* 171 */       MessageBoxes.warning(iaex.getMessage());
/*     */     }
/* 173 */     catch (Exception ex) {
/*     */       
/* 175 */       MessageBoxes.error(ex);
/*     */     } 
/*     */   }
/*     */ }

