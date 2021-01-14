package br.net.walltec.api.comum;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.UUID;

import javax.ws.rs.core.StreamingOutput;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.HttpResponse;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.jackson2.JacksonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.utilitarios.UtilBase64;

public class IntegracaoGoogleDrive {
	private static final String APPLICATION_NAME = "MyFinanc";
	// private static final String TOKENS_DIRECTORY_PATH = "tokens";

	private static JacksonFactory JSON_FACTORY;
	private static NetHttpTransport HTTP_TRANSPORT;
	private static java.io.File DATA_STORE_DIR;
	private static FileDataStoreFactory DATA_STORE_FACTORY;

	/**
	 * Global instance of the scopes required by this quickstart. If modifying these
	 * scopes, delete your previously saved tokens/ folder.
	 */
	private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
	private static final String CREDENTIALS_FILE_PATH = "/auth-google/credentials.json";

	static {
		init();
	}

	private static void init() {
		JSON_FACTORY = JacksonFactory.getDefaultInstance();
		try {
			HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
			DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), "." + APPLICATION_NAME);
			DATA_STORE_FACTORY = new FileDataStoreFactory(DATA_STORE_DIR);
		} catch (GeneralSecurityException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static Credential authorize() throws Exception {
		// load client secrets
		GoogleClientSecrets clientSecrets = GoogleClientSecrets.load(JSON_FACTORY,
				new InputStreamReader(IntegracaoGoogleDrive.class.getResourceAsStream(CREDENTIALS_FILE_PATH)));

		// set up authorization code flow
		GoogleAuthorizationCodeFlow flow = new GoogleAuthorizationCodeFlow.Builder(HTTP_TRANSPORT, JSON_FACTORY,
				clientSecrets, Collections.singleton(DriveScopes.DRIVE_FILE)).setDataStoreFactory(DATA_STORE_FACTORY)
						.build();

		// authorize
		return new AuthorizationCodeInstalledApp(flow, new LocalServerReceiver()).authorize("domain");
	}

	private static Drive getDriveService() throws IOException, Exception {
		return new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, authorize()).setApplicationName(APPLICATION_NAME)
				.build();
	}

	public static String salvarArquivo(String contentBase64, String fileName) throws NegocioException {
		File fileMetadata = new File();
		fileMetadata.setName(fileName);
		//fileMetadata.setWebContentLink("/arquivo-teste.html");
		FileOutputStream fos = null;
		
		try {
			byte[] bytesArquivo = UtilBase64.decodificarBase64(contentBase64);
			java.io.File filePath = new java.io.File(fileName).createTempFile( UUID.randomUUID().toString(), ".tmp");
			fos = new FileOutputStream(filePath);
			fos.write(bytesArquivo);
			
			FileContent mediaContent = new FileContent("image/png", filePath);
			File file = getDriveService().files().create(fileMetadata, mediaContent).setFields("id").execute();
			
			return file.getId();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			throw new NegocioException("Falha ao salvar o arquivo no google drive. Detalhes: " + e.getMessage());

		} finally {
			if (fos != null) {
				try {
					fos.close();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}

	public static byte[] recuperarArquivo(String fileId) throws Exception {
		
		ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
		getDriveService().files().get(fileId).executeMediaAndDownloadTo(outputStream);

		return outputStream.toByteArray();
	}

	public static FileList listarTodosArquivos() throws Exception {
		return getDriveService().files().list().execute();
	}
	
	public static boolean apagarArquivo(String fileId) {
		try {
			getDriveService().files().delete(fileId).execute();
			return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}

	public static void main(String[] args) throws Exception {
		String idFile = "1CMPog8YZQBpVaR2QZAblSC5biJoi-BqW";
		
//		FileList lista = IntegracaoGoogleDrive.listarTodosArquivos();
//		System.out.println(lista);
		
		java.io.File arquivo = new java.io.File("/Volumes/DADOS/imagens/dudoida/Foto1701_1116.png");
//		
//		BufferedInputStream bis =new BufferedInputStream(new FileInputStream(arquivo));
//		byte[] bytesArquivo = new byte[(int)arquivo.length()];
//		bis.read(bytesArquivo);
//		
//		String conteudoEmBase64 = UtilBase64.codificarBase64(bytesArquivo);
//		bis.close();
//		
		FileOutputStream fos = new FileOutputStream(arquivo);
		
		byte[] bytes = IntegracaoGoogleDrive.recuperarArquivo(idFile);
		
//		System.out.println(bytes);
//		
		fos.write(bytes);
//		
		fos.close();
		
		System.out.println("baixado com sucesso");
//		Object id = IntegracaoGoogleDrive.
//				salvarArquivo(conteudoEmBase64, "Foto10.png");
//		if (id != null) {
//			System.out.println("Arquivo salvo com sucesso. ID:" + id);
//		}
	}

}
