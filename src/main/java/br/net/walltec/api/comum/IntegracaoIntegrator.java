package br.net.walltec.api.comum;

import java.io.BufferedInputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Path;
import java.util.Date;

import com.google.api.services.drive.model.FileList;

import br.net.walltec.api.excecoes.NegocioException;
import br.net.walltec.api.utilitarios.UtilBase64;

public class IntegracaoIntegrator {
	
	private static final String DOCUMENTOS_PATH = "/home/wallace/documentos";

	private static final String DOCUMENTOS_PATH_LOCAL = "/Volumes/DADOS/documentos";

	public static boolean localHost = false;
	

	public static String salvarArquivo(String contentBase64, String fileName, String mimeType) throws NegocioException {
		FileOutputStream fos = null;
		
		try {
			byte[] bytesArquivo = UtilBase64.decodificarBase64(contentBase64);
			String idGerado = new Date().getTime() + fileName; 
			
			java.io.File file = new java.io.File(getPathArquivos() + "/"+ idGerado);
			file.createNewFile();
			fos = new FileOutputStream(file);
			fos.write(bytesArquivo);
			
			return idGerado;
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
	
	private static String getPathArquivos() {
		return localHost ? DOCUMENTOS_PATH_LOCAL :  DOCUMENTOS_PATH; 
	}

	public static byte[] recuperarArquivo(String fileId) throws Exception {
		java.io.File file = new java.io.File(getPathArquivos() + "/"+ fileId);
		
		if (file.exists()) {
			FileInputStream fis = new FileInputStream(file);
			BufferedInputStream bis = new BufferedInputStream(fis);
			byte[] bytes = new byte[bis.available()];
			bis.read(bytes);
			bis.close();
			return bytes;
		}

		throw new NegocioException("Arquivo não encontrado com o id " + fileId);
	}

	public static boolean apagarArquivo(String fileId) {
		try {
			java.io.File file = new java.io.File(getPathArquivos() + "/"+ fileId);
			
			if (file.exists()) {
				file.delete();
			}
				return true;
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			return false;
		}

	}


}
