package org.powerbat.methods;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;

public class IOUtils {

	private IOUtils() {
	}

	public static void download(URL url, File file) throws IOException {
		write(file, download(url));
	}

	public static byte[] download(URL url) throws IOException {
		return readData(url.openStream());
	}

	public static byte[] readData(File file) throws IOException {
		return readData(new FileInputStream(file));
	}

	public static byte[] readData(InputStream in) throws IOException {
		final ByteArrayOutputStream out = new ByteArrayOutputStream();
		final byte[] data = new byte[1024];
		int read;
		while ((read = in.read(data, 0, 1024)) != -1) {
			out.write(data, 0, read);
		}
		in.close();
		return out.toByteArray();
	}

	public static void write(File file, byte[] data) throws IOException {
		final FileOutputStream out = new FileOutputStream(file);
		out.write(data);
		out.close();
	}

}
