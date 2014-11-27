package com.waylau.compressor;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.UnsupportedEncodingException;
import java.util.Properties;

import org.mozilla.javascript.ErrorReporter;
import org.mozilla.javascript.EvaluatorException;

import com.yahoo.platform.yui.compressor.CssCompressor;
import com.yahoo.platform.yui.compressor.JavaScriptCompressor;
import com.waylau.compressor.util.ArrayUtil;
/**
 * Hello world!
 *
 */
public class App 
{
    static String infile = null;   
    static String outfile = null;   
    static String ignore = null;
    static String[] ignoreArray = null;
    
    public static void main( String[] args )
    {


        // 读取配置 /config.properties
        Properties prop = new Properties();   
        InputStream in = Object.class.getResourceAsStream("/config.properties");   
        try {   
            prop.load(in);   
            
            infile = prop.getProperty("infile").trim();   
            outfile = prop.getProperty("outfile").trim();   
            ignore = prop.getProperty("ignore").trim();   
            
            System.out.println( "infile:" + infile );
            System.out.println( "outfile:" + outfile );
            System.out.println( "ignore:" + ignore );
            
            if ( ignore.length() > 0 ){
                ignoreArray = ignore.split(";");
                
                for( String i : ignoreArray ){
                    System.out.println( "ignoreArray:"+ i );
                }
            }

            scanDirectory( new File( infile ));
            
        } catch (IOException e) {   
            e.printStackTrace();   
        }   
        

    }
    
    private static void compressJS( File file ) throws Exception {
    	String fileName = file.getName();
    	if( !fileName.endsWith(".js") && !fileName.endsWith(".css")) {
    		return;
    	}
    	
		Reader in = new InputStreamReader(new FileInputStream(file),"utf-8");
		String filePath=file.getAbsolutePath();

		
    }
    
    private static void scanDirectory( File infile ) {
        // 列出所有该目录下的文件
        File[] files =  infile.listFiles();
        
        if( files == null || files.length == 0 ){
        	return ;
        }
        
      
        for( File file : files ){
        	
        	if( ArrayUtil.isExist( ignoreArray, file.getName())){
        		
        		continue;
        	}
        	/*
        	for (String ig : ignoreArray) {
        		
        		//判断是否是忽略目录，是的话就继续
        		
            	if( file.getName().endsWith( ig.trim() ) ) {
            		continue;
            	}
            	if( file.isFile() ){
            		compressJS( file );
            	}
            	
            	if( file.isDirectory() ){
            		scanDirectory( file );
            	}
        	}
	*/
        }
   
    }
}
