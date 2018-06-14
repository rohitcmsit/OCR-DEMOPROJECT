package com.cms.example.controller;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.PrintStream;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import net.sourceforge.tess4j.Tesseract;

@RestController
public class OcrController {
	
   @RequestMapping(value="/image",method=RequestMethod.POST,consumes="multipart/form-data")
   public @ResponseBody String imageToText(@RequestParam("file") MultipartFile file) throws Exception {
	   
	   String fullText="";
	   
       if (!file.isEmpty()) {
           try {
               byte[] bytes = file.getBytes();
               BufferedOutputStream stream = 
                       new BufferedOutputStream(new FileOutputStream(new File("E:\\ocr-input\\img1.jpg")));
               stream.write(bytes);
               stream.close();
               String inputFilePath = "E:\\ocr-input\\img1.jpg";
               String output_file="E:\\ocr-output\\img.txt";
               Tesseract tesseract = new Tesseract();
               tesseract.setDatapath("E:\\OCR\\Tesseract-OCR");
               tesseract.setLanguage("eng");
               
               fullText = tesseract.doOCR(new File(inputFilePath));
               
               System.out.println(fullText);
               try {
               	PrintStream myconsole = new PrintStream(output_file);
               	System.setOut(myconsole);
               	myconsole.print(fullText);
               }
               catch(Exception ex) {
               	ex.printStackTrace();
           }
           } catch (Exception e) {
               e.printStackTrace();
           }
       } else {
           return "You failed to read  file because the file was empty.";
       }  
	   
       return fullText;
	   
     /*  String inputFilePath = "E:\\ocr-input\\img1.jpg";
       String output_file="E:\\ocr-output\\img.txt";
       Tesseract tesseract = new Tesseract();
       tesseract.setDatapath("E:\\OCR\\Tesseract-OCR");
       tesseract.setLanguage("eng");
       
       String fullText = tesseract.doOCR(new File(inputFilePath));
       return fullText;
       
       System.out.println(fullText);
       try {
       	PrintStream myconsole = new PrintStream(output_file);
       	System.setOut(myconsole);
       	myconsole.print(fullText);
       }
       catch(Exception ex) {
       	ex.printStackTrace();
   }*/
}

	

}
