package mx.com.televisa.cfdis.gui.modules.cargasexcel;

import java.awt.Dimension;
import java.awt.Font;
import java.awt.LayoutManager;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JInternalFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import mx.com.televisa.cfdis.gui.common.MessageBoxes;
import mx.com.televisa.cfdis.process.cargasexcel.CargarInfoHonoAsimiNomi;

public class CargaHonorariosFrm extends JInternalFrame {
  Dimension screenSize;
  
  Dimension windowSize;
  
  private JTabbedPane pnlTabs = new JTabbedPane();
  
  private JPanel pnlTabRetencionesHonorarios = new JPanel();
  
  private JPanel pnlRetencionesHonorarios = new JPanel();
  
  private JButton btnCargarHonorarios = new JButton();
  
  private JButton btnExaminarHonorarios = new JButton();
  
  private JTextField txtArchivoHonorarios = new JTextField();
  
  private JLabel lblArchivoHonorarios = new JLabel();
  
  private File[] variosArchivos;
  
  public CargaHonorariosFrm() {
    try {
      jbInit();
      customInit();
    } catch (Exception e) {
      e.printStackTrace();
    } 
  }
  
  private void jbInit() throws Exception {
    setSize(new Dimension(668, 254));
    getContentPane().setLayout((LayoutManager)null);
    setResizable(false);
    setMaximizable(false);
    setClosable(true);
    setTitle("Carga de Archivos");
    this.pnlTabs.setBounds(new Rectangle(10, 10, 645, 210));
    this.pnlTabRetencionesHonorarios.setLayout((LayoutManager)null);
    this.pnlRetencionesHonorarios.setLayout((LayoutManager)null);
    this.pnlRetencionesHonorarios.setBorder(BorderFactory.createTitledBorder("Carga de Archivos"));
    this.pnlRetencionesHonorarios.setBounds(new Rectangle(10, 10, 615, 170));
    this.btnCargarHonorarios.setText("Cargar");
    this.btnCargarHonorarios.setBounds(new Rectangle(500, 110, 95, 30));
    this.btnCargarHonorarios.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            CargaHonorariosFrm.this.btnCargarHonorarios_actionPerformed(e);
          }
        });
    this.btnExaminarHonorarios.setText("Examinar");
    this.btnExaminarHonorarios.setBounds(new Rectangle(435, 55, 100, 30));
    this.btnExaminarHonorarios.addActionListener(new ActionListener() {
          public void actionPerformed(ActionEvent e) {
            CargaHonorariosFrm.this.btnExaminarHonorarios_actionPerformed(e);
          }
        });
    this.txtArchivoHonorarios.setBounds(new Rectangle(45, 55, 380, 30));
    this.lblArchivoHonorarios.setText("Archivos de Texto:");
    this.lblArchivoHonorarios.setBounds(new Rectangle(25, 30, 180, 20));
    this.lblArchivoHonorarios.setFont(new Font("Tahoma", 0, 11));
    this.pnlRetencionesHonorarios.add(this.lblArchivoHonorarios, (Object)null);
    this.pnlRetencionesHonorarios.add(this.txtArchivoHonorarios, (Object)null);
    this.pnlRetencionesHonorarios.add(this.btnExaminarHonorarios, (Object)null);
    this.pnlRetencionesHonorarios.add(this.btnCargarHonorarios, (Object)null);
    this.pnlTabRetencionesHonorarios.add(this.pnlRetencionesHonorarios, (Object)null);
    this.pnlTabs.addTab("Retenciones Asimilables", this.pnlTabRetencionesHonorarios);
    getContentPane().add(this.pnlTabs, (Object)null);
  }
  
  private void customInit() {
    this.screenSize = Toolkit.getDefaultToolkit().getScreenSize();
    this.windowSize = getSize();
    int windowX = Math.max(0, (this.screenSize.width - this.windowSize.width) / 2);
    int windowY = Math.max(0, (this.screenSize.height - this.windowSize.height) / 2);
    setLocation(windowX, windowY);
  }
  
  private void btnExaminarHonorarios_actionPerformed(ActionEvent e) {
    try {
      JFileChooser chooser = new JFileChooser();
      FileNameExtensionFilter filter = new FileNameExtensionFilter("Archivos (txt)", new String[] { "txt" });
      chooser.setFileFilter(filter);
      chooser.setMultiSelectionEnabled(true);
      int returnVal = chooser.showOpenDialog(this);
      if (returnVal == 0) {
        this.variosArchivos = chooser.getSelectedFiles();
        String lstNombresArchivos = "";
        int i = 0;
        byte b;
        int j;
        File[] arrayOfFile;
        for (j = (arrayOfFile = this.variosArchivos).length, b = 0; b < j; ) {
          File f = arrayOfFile[b];
          lstNombresArchivos = String.valueOf(lstNombresArchivos) + " " + f.getName();
          b++;
        } 
        this.txtArchivoHonorarios.setText(lstNombresArchivos);
      } 
    } catch (IllegalArgumentException iaex) {
      MessageBoxes.warning(iaex.getMessage());
    } catch (Exception ex) {
      MessageBoxes.error(ex);
    } 
  }
  
  private void btnCargarHonorarios_actionPerformed(ActionEvent e) {
    try {
      ArrayList<String> result = CargarInfoHonoAsimiNomi.doCargarInfoHonoAsimiNomi(this.variosArchivos);
      result.add("El detalle de las validaciones se envian por correo");
      
      MessageBoxes.message(result);
      
    } catch (IllegalArgumentException iaex) {
      MessageBoxes.warning(iaex.getMessage());
    } catch (Exception ex) {
      MessageBoxes.error(ex);
    } 
  }
}
