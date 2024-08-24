package mx.com.televisa.cfdis.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URISyntaxException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.LinkedList;
import java.util.List;
import java.util.Properties;
import java.util.ResourceBundle;

public class LeerProperties {
  public static String forName;
  
  public static String diverManager;
  
  public static String usuario;
  
  public static String password;
  
  public static String sftpServer;
  
  public static String sftpPort;
  
  public static String sftpUser;
  
  public static String sftpPassword;
  
  public static String sftpRemoteDirectory;
  
  public static String sftpRemoteDirectoryNomiAsimi;
  
  public static String psstTitle;
  
  public static String wsOIC;
  
  static String imprime;
  
  public static String[] algoritmos = new String[] { "MD2", "MD5", "SHA-1", "SHA-256", "SHA-384", "SHA-512" };
  
  private static String toHexadecimal(byte[] digest) {
    String hash = "";
    byte b;
    int i;
    byte[] arrayOfByte;
    for (i = (arrayOfByte = digest).length, b = 0; b < i; ) {
      byte aux = arrayOfByte[b];
      int j = aux & 0xFF;
      if (Integer.toHexString(j).length() == 1)
        hash = String.valueOf(hash) + "0"; 
      hash = String.valueOf(hash) + Integer.toHexString(j);
      b++;
    } 
    return hash;
  }
  
  public static String getHash(String cadena, int tipoAlgoritmo) {
    byte[] digest = null;
    byte[] buffer = cadena.getBytes();
    try {
      MessageDigest messageDigest = MessageDigest.getInstance(algoritmos[tipoAlgoritmo]);
      messageDigest.reset();
      messageDigest.update(buffer);
      digest = messageDigest.digest();
    } catch (NoSuchAlgorithmException ex) {
      System.out.println("Error creando Hash");
    } 
    return toHexadecimal(digest);
  }
  
  public static void LeerPropertiesFinal() throws URISyntaxException {
    Properties prop = new Properties();
    InputStream input = null;
    InputStream input_pasword = null;
    ResourceBundle bundle = ResourceBundle.getBundle("DataConnection");
    try {
      System.out.println("remoteDirectory: " + bundle.getString("mx.com.televisa.cfdis.sftpRemoteDirectory"));
      
      String remoteDirectory = bundle.getString("mx.com.televisa.cfdis.sftpRemoteDirectory").trim();
      imprime = "Antes la lectura archivo properties remoteDirectory" + remoteDirectory;
      File file = new File(remoteDirectory);
      input = new FileInputStream(file);
      prop.load(input);
      imprime = "Pase la lectura archivo properties";
      wsOIC = prop.getProperty("mx.com.televisa.cfdis.wsOIC");
      forName = prop.getProperty("mx.com.televisa.cfdis.class.forname");
      diverManager = prop.getProperty("mx.com.televisa.cfdis.diverManager");
      usuario = prop.getProperty("mx.com.televisa.cfdis.usuario");
      imprime = "usuario " + forName + usuario;
      sftpServer = prop.getProperty("mx.com.televisa.cfdis.sftpServer");
      sftpPort = prop.getProperty("mx.com.televisa.cfdis.sftpPort");
      sftpUser = prop.getProperty("mx.com.televisa.cfdis.sftpUser");
      sftpRemoteDirectory = prop.getProperty("mx.com.televisa.cfdis.sftpRemoteDirectory");
      sftpRemoteDirectoryNomiAsimi = prop.getProperty("mx.com.televisa.cfdis.sftpRemoteDirectoryNomiAsimi");
      System.out.println("1: " + prop.getProperty("mx.com.televisa.cfdis.sftpRemoteDirectory"));
      System.out.println("2: " + prop.getProperty("mx.com.televisa.cfdis.class.forname"));
      System.out.println("3: " + prop.getProperty("mx.com.televisa.cfdis.diverManager"));
      System.out.println("4: " + prop.getProperty("mx.com.televisa.cfdis.usuario"));
      System.out.println("6: " + prop.getProperty("mx.com.televisa.cfdis.sftpServer"));
      System.out.println("7: " + prop.getProperty("mx.com.televisa.cfdis.sftpPort"));
      System.out.println("8: " + prop.getProperty("mx.com.televisa.cfdis.sftpUser"));
      String remoteDirectoryPassword = bundle.getString("mx.com.televisa.cfdis.sftpRemotePassword").trim();
      imprime = "Antes la lectura archivo properties remoteDirectoryPassword" + remoteDirectoryPassword;
      File file_password = new File(remoteDirectoryPassword);
      input_pasword = new FileInputStream(file_password);
      prop.load(input_pasword);
      String passwordBase = prop.getProperty("mx.com.televisa.cfdis.password");
      String sftpPasswordFTP = prop.getProperty("mx.com.televisa.cfdis.sftpPassword");
      System.out.println("5: " + prop.getProperty("mx.com.televisa.cfdis.password"));
      System.out.println("9: " + prop.getProperty("mx.com.televisa.cfdis.sftpPassword"));
      System.out.println("10: " + prop.getProperty("mx.com.televisa.cfdis.wsOIC"));
      imprime = "Recupera " + passwordBase + sftpPasswordFTP;
      AESSencillo miAESSencillo = new AESSencillo();
      
      String qa = "TELES_EGRTVQA48th";
      qa = miAESSencillo.encrypt(qa);
      
      System.out.println("999: " + qa);
      password = miAESSencillo.decrypt(passwordBase);
      sftpPassword = miAESSencillo.decrypt(sftpPasswordFTP);
      imprime = "Desencripta " + password + sftpPassword;
      System.out.println("pssBase " + password);
      System.out.println("sftpPassword " + sftpPassword);
    } catch (IOException ex) {
      ex.printStackTrace();
    } catch (Exception e) {
      e.printStackTrace();
    } finally {
      if (input != null)
        try {
          input.close();
          input_pasword.close();
        } catch (IOException e) {
          e.printStackTrace();
        }  
    } 
  }
  
  public static List<String> listarRecursivo(String ruta) {
    File archivo = new File(ruta);
    List<String> list_directorios = new LinkedList<>();
    if (archivo.exists()) {
      File[] ficheros = archivo.listFiles();
      for (int i = 0; i < ficheros.length; i++) {
        if (ficheros[i].isDirectory()) {
          System.out.println("Directorio: " + ficheros[i].getAbsolutePath());
          list_directorios.add(ficheros[i].getAbsolutePath());
        } 
      } 
    } else {
      System.out.println("Ruta incorrecta");
    } 
    return list_directorios;
  }
  
  public static void main(String[] args) {}
}
