/*     */ package mx.com.televisa.cfdis.util;
/*     */ 
/*     */ import com.jcraft.jsch.ChannelSftp;
/*     */ import com.jcraft.jsch.JSch;
/*     */ import com.jcraft.jsch.Session;
/*     */ import com.jcraft.jsch.UserInfo;
/*     */ import mx.com.televisa.cfdis.process.cargasexcel.CargasCommon;
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
/*     */ public class SFTPHelper
/*     */ {
/*     */   public static void sendFile(String fileName, String localFolder, String pstTipo, int piGetIndexValu) throws Exception {
/*     */     try {
/*  36 */       if (pstTipo.equals("Retenciones")) {
/*  37 */         String serverAddress = LeerProperties.sftpServer;
/*  38 */         int serverPort = Integer.valueOf(LeerProperties.sftpPort).intValue();
/*  39 */         String userId = LeerProperties.sftpUser;
/*  40 */         String password = LeerProperties.sftpPassword;
/*  41 */         String remoteDirectory = LeerProperties.sftpRemoteDirectory;
/*     */ 
/*     */ 
/*     */         
/*  45 */         JSch jsch = new JSch();
/*  46 */         Session session = jsch.getSession(userId, serverAddress, serverPort);
/*  47 */         UserInfo ui = new SFTPUserInfo(password, null);
/*     */         
				   
/*  49 */         session.setUserInfo(ui);
/*  50 */         session.setPassword(password);

/*  52 */         session.connect();
/*     */         
/*  54 */         ChannelSftp sftp = (ChannelSftp)session.openChannel("sftp");
/*  55 */         sftp.connect();
/*  56 */         String path = sftp.pwd();
/*  57 */         sftp.cd(remoteDirectory);
/*  58 */         String pwd = sftp.pwd();
/*     */         
/*  60 */         String ruta = String.valueOf(localFolder) + "/" + fileName;
				//	System.out.println("Ruta " + ruta);
				  //sftp.put(String.valueOf(localFolder) + "/" + fileName, fileName);
				    sftp.put(ruta, fileName);
/*     */         
/*  64 */         sftp.exit();
/*  65 */         sftp.disconnect();
/*  66 */         session.disconnect();
/*  67 */       } else if (pstTipo.equals("Asimilables")) {
/*  68 */         String serverAddress = LeerProperties.sftpServer;
/*  69 */         int serverPort = Integer.valueOf(LeerProperties.sftpPort).intValue();
/*  70 */         String userId = LeerProperties.sftpUser;
/*  71 */         String password = LeerProperties.sftpPassword;
/*  72 */         String sftpRemoteDirectoryNomiAsimi = LeerProperties.sftpRemoteDirectoryNomiAsimi;
/*     */         
/*  74 */         JSch jsch = new JSch();
/*  75 */         Session session = jsch.getSession(userId, serverAddress, serverPort);
/*  76 */         UserInfo ui = new SFTPUserInfo(password, null);
/*     */         
/*  78 */         session.setUserInfo(ui);
/*  79 */         session.setPassword(password);
/*  80 */         session.connect();
/*     */         
/*  82 */         ChannelSftp sftp = (ChannelSftp)session.openChannel("sftp");
/*  83 */         int i = 0;
/*     */         
/*  85 */         sftp.connect();
/*     */         
/*  87 */         System.out.println("ExitStatus Connect: " + i);
/*     */         
/*  89 */         sftp.cd(sftpRemoteDirectoryNomiAsimi);
/*     */         
/*  91 */         System.out.println("ExitStatus Cd: " + i);
/*     */         
/*  93 */         sftp.put(String.valueOf(localFolder) + "/" + fileName, fileName);
/*     */         
/*  95 */         System.out.println("ExitStatus Put: " + i);
/*     */         
/*  97 */         sftp.exit();
/*     */         
/*  99 */         System.out.println("ExitStatus Exit: " + i);
/*     */         
/* 101 */         sftp.disconnect();
/*     */         
/* 103 */         System.out.println("ExitStatus Disconnect: " + i);
/*     */         
/* 105 */         session.disconnect();
/*     */       } 
/* 107 */     } catch (Exception ex) {
/* 108 */       System.out.println("Exception: " + ex);
/* 109 */       CargasCommon.borrarFacturasAsimilables(piGetIndexValu);
/* 110 */       throw new IllegalArgumentException("Error al enviar el archivo " + fileName + ", el servidor SFTP no se encuentra disponible");
/*     */     } 
/*     */   }
/*     */ }

