package com.waylau.uicompressor;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.Reader;
import java.io.Writer;
import java.util.Properties;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import com.waylau.uicompressor.util.ArrayUtil;

/**
 * 压缩应用主入口
 * 
 * @author waylau.com
 * @date 2014-11-27
 *
 */
public class App {
	static String infile = null;
	static String outfile = null;
	static String ignore = null;
	static String[] ignoreArray = null;

	static String charset = "utf-8";
	static String type = null;
	static String[] types = null;
	static int linebreak = -1;
	static boolean nomunge = true;
	static boolean verbose = false;
	static boolean preserveSemi = false;
	static boolean disableOptimizations = false;

	public static void main(String[] args) throws Exception {
		// 读取配置 /config.properties
		Properties prop = new Properties();
		InputStream in = Object.class.getResourceAsStream("/config.properties");
		try {
			prop.load(in);

			infile = prop.getProperty("infile").trim();
			outfile = prop.getProperty("outfile").trim();
			ignore = prop.getProperty("ignore").trim();
			charset = prop.getProperty("charset").trim();
			type = prop.getProperty("type").trim();
			linebreak = Integer.parseInt(prop.getProperty("linebreak").trim());
			nomunge = Boolean.valueOf(prop.getProperty("nomunge").trim());
			verbose = Boolean.valueOf(prop.getProperty("verbose").trim());
			preserveSemi = Boolean.valueOf(prop.getProperty("preserveSemi")
					.trim());
			disableOptimizations = Boolean.valueOf(prop.getProperty(
					"disableOptimizations").trim());

			System.out.println("infile:" + infile);
			System.out.println("outfile:" + outfile);
			System.out.println("ignore:" + ignore);
			System.out.println("charset:" + charset);
			System.out.println("type:" + type);
			System.out.println("linebreakpos:" + linebreak);
			System.out.println("munge:" + nomunge);
			System.out.println("verbose:" + verbose);
			System.out.println("preserveAllSemiColons:" + preserveSemi);
			System.out.println("disableOptimizations:" + disableOptimizations);

			if (ignore.length() > 0) {
				ignoreArray = ignore.split(";");

				for (String i : ignoreArray) {
					System.out.println("ignoreArray:" + i);
				}
			}

			if (type.length() > 0) {
				types = type.split(";");

				for (String i : types) {
					System.out.println("types:" + i);
				}
			}

			System.out.println("--------Copying files start------");
			
			long copyStime = System.currentTimeMillis();
			
			copyFolder(infile, outfile);
			
			long copyEtime = System.currentTimeMillis();
			long copytime = copyEtime - copyStime;
			
			System.out.println("--------Copying files end,total :"+copytime +" ms ------");
			
			System.out.println("--------Compressing files start------");
			
			long compressStime = System.currentTimeMillis();
			
			
			scanDirectory(new File(outfile));
			
			long compressEtime = System.currentTimeMillis();
			long compresstime = compressEtime - compressStime;
			
			System.out.println("--------Compressing files end,total :"+compresstime +" ms ------");
			
		} catch (IOException e) {
			e.printStackTrace();
		}

	}

	/**
	 * Compress JS
	 * 
	 * @param file
	 *            File
	 */
	private static void compressJS(File file) {

		//判断文件里面是否有内容，没有就返回，不往下执行了
		if( file.length() == 0){
			System.out.println("File is empty :" + file.getPath());
			return ;
		}
		
		Reader in = null;
		Writer out = null;
		File tempFile = null;
		String filePath = file.getAbsolutePath();
		
		try {
			in= new InputStreamReader(new FileInputStream(file), charset);


			System.out.println("Compress js file to :" + filePath);

			tempFile = new File(filePath + ".tempFile");

			out = new OutputStreamWriter(new FileOutputStream(tempFile),
					charset);

			JavaScriptCompressor jscompressor = new JavaScriptCompressor(in,
					new ErrorReporter() {
						public void warning(String message, String sourceName,
								int line, String lineSource, int lineOffset) {
							if (line < 0) {
								System.err.println("\n[WARNING] " + message);
							} else {
								System.err.println("\n[WARNING] " + line + ':'
										+ lineOffset + ':' + message);
							}
						}

						public void error(String message, String sourceName,
								int line, String lineSource, int lineOffset) {
							if (line < 0) {
								System.err.println("\n[ERROR] " + message);
							} else {
								System.err.println("\n[ERROR] " + line + ':'
										+ lineOffset + ':' + message);
							}
						}

						public EvaluatorException runtimeError(String message,
								String sourceName, int line, String lineSource,
								int lineOffset) {
							error(message, sourceName, line, lineSource, lineOffset);
							return new EvaluatorException(message);
						}
					});

			jscompressor.compress(out, linebreak, nomunge, verbose, preserveSemi,
					disableOptimizations);


			
		}catch( Exception e){
			System.out.println("Compress js file [FAILED] :" + filePath);
			e.printStackTrace();
		}finally{
			try {
				in.close();
				out.close();
				file.delete();
				tempFile.renameTo(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
 
			
			tempFile.delete();
		}
		

	}

	/**
	 * Compress CSS
	 * 
	 * @param file
	 *            File
	 */
	private static void compressCSS(File file) throws Exception {

		Reader in = new InputStreamReader(new FileInputStream(file), charset);
		String filePath = file.getAbsolutePath();

		System.out.println("Compress css file to :" + filePath);

		File tempFile = new File(filePath + ".tempFile");

		Writer out = new OutputStreamWriter(new FileOutputStream(tempFile),
				charset);

		CssCompressor csscompressor = new CssCompressor(in);
		csscompressor.compress(out, linebreak);

		out.close();
		in.close();
		file.delete();
		tempFile.renameTo(file);
		tempFile.delete();
	}

	/**
	 * Scan Directory
	 * 
	 * @param infile
	 *            File
	 */
	private static void scanDirectory(File infile) throws Exception {

		if (!infile.exists()) {
			System.out.println("Directory is not exist :" + infile.toPath());
			return;

		}

		// list all the files
		File[] files = infile.listFiles();

		if (files == null || files.length == 0) {
			System.out.println("Directory has no file :" + infile.toPath());
			return;
		}

		for (File file : files) {

			if (ArrayUtil.isExist(ignoreArray, file.getName())) {
				continue;
			}
			if (file.isFile()) {
				if (file.getName().endsWith(".js")
						&& ArrayUtil.isExist(types, "js")) {
					compressJS(file);
				} else if (file.getName().endsWith(".css")
						&& ArrayUtil.isExist(types, "css")) {
					compressCSS(file);
				}

			}

			if (file.isDirectory()) {
				scanDirectory(file);
			}

		}

	}

	/**
	 * Copy Folder
	 * 
	 * @param oldPath
	 *            String
	 * @param newPath
	 *            String
	 */
	public static void copyFolder(String oldPath, String newPath) {

		if (!(new File(oldPath)).exists()) {
			System.out.println("Directory is not exist : "+ oldPath ) ;
			return;
		}
		try {
			(new File(newPath)).mkdirs(); // 如果文件夹不存在 则建立新文件夹
			File a = new File(oldPath);
			String[] file = a.list();
			File temp = null;
			for (int i = 0; i < file.length; i++) {
				if (oldPath.endsWith(File.separator)) {
					temp = new File(oldPath + file[i]);
				} else {
					temp = new File(oldPath + File.separator + file[i]);
				}

				if (temp.isFile()) {
					FileInputStream input = new FileInputStream(temp);
					FileOutputStream output = new FileOutputStream(newPath
							+ "/" + (temp.getName()).toString());
					byte[] b = new byte[1024 * 5];
					int len;
					while ((len = input.read(b)) != -1) {
						output.write(b, 0, len);
					}
					output.flush();
					output.close();
					input.close();
				}
				if (temp.isDirectory()) {// 如果是子文件夹
					copyFolder(oldPath + "/" + file[i], newPath + "/" + file[i]);
				}
			}
		} catch (Exception e) {
			System.out.println("Copying Folder has a Error!");
			e.printStackTrace();

		}

	}
}
