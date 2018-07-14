package com.mannu.utility;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.URL;
import java.net.URLConnection;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Base64;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Rectangle;
import com.itextpdf.text.pdf.PdfWriter;
import com.mannu.model.AckDetail;
import com.mannu.model.User;

public class DataUtility {
	String sorceURL ="http://192.168.49.44/karvyetdswebservice-0.0.1/etdswebservice/";
//	String sorceURL ="http://localhost:8090/etdswebservice/";
	
	public User getUser(String username, String password) {
		User user=new User();
		JSONParser parser = new JSONParser();
		try {
			URL oracle = new URL(sorceURL+"user/user="+username+"&"+password);
			 URLConnection yc = oracle.openConnection();
	         BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	         String inputLine;
	            while ((inputLine = in.readLine()) != null) {               
	                JSONArray a = (JSONArray) parser.parse(inputLine);
	                for (Object o : a) {
	                    JSONObject tutorials = (JSONObject) o;
	                    user.setSta(tutorials.get("sta").toString());
	                    user.setUsername(tutorials.get("username").toString());
	                    user.setFullname(tutorials.get("fullname").toString());
	                    user.setRole(tutorials.get("role").toString());
	                }
	            }
	            in.close();
		} catch (Exception e) {
			return user;
		}
		return user;
	}

	public AckDetail getImage(String username, String role) {
		File file=new File("C:\\temp");
		if(!file.exists()) {
			file.mkdirs();
		}
		AckDetail ackDetail=new AckDetail();
		JSONParser parser = new JSONParser();
		try {
			URL url = new URL(sorceURL+role+"/user="+username);
			 URLConnection yc = url.openConnection();
	         BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	         String inputLine;
	            while ((inputLine = in.readLine()) != null) {               
	                JSONArray a = (JSONArray) parser.parse(inputLine);
	                for (Object o : a) {
	                    JSONObject tutorials = (JSONObject) o;
	                    InputStream input = new ByteArrayInputStream(Base64.getDecoder().decode(tutorials.get("fileBytes").toString()));
	                    BufferedImage image = ImageIO.read(input);
	                    BufferedImage newBi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
	            	    newBi.getGraphics().drawImage(image, 0, 0, null);
	                    ImageIO.write(newBi, "png", new File("C:\\temp\\temp.png"));
	                    ackDetail.setAckno(tutorials.get("ackno").toString());
	                    ackDetail.setFilepath("C:\\temp\\temp.png");
	                    ackDetail.setEmail(tutorials.get("email").toString());
	                    ackDetail.setSamackno(tutorials.get("samackno").toString());
	                    ackDetail.setAckdate(tutorials.get("ackdate").toString());
	                    ackDetail.setResname(tutorials.get("resname").toString());
	                    ackDetail.setScandate(tutorials.get("scandate").toString());
	                    ackDetail.setSmartinwardno(tutorials.get("smartinwardno").toString());
	                }
	            }
	            in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ackDetail;
	}

	public AckDetail getImageSave(String username, String role, String ackno) {
		DateFormat df=new SimpleDateFormat("dd-MM-yyyy");
		File fil=new File("\\\\192.168.49.89\\etds-05072018\\etds\\"+df.format(new Date()));
		Runtime rt = Runtime.getRuntime();
		if(!fil.exists()) {
			try {
				rt.exec(new String[] {"cmd.exe","/c","net use "+fil+" /user:narender Karvy123$"});
				fil.mkdirs();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		Document doc=new Document();
		try {
			PdfWriter writer=PdfWriter.getInstance(doc, new FileOutputStream(fil+"\\"+ackno+".pdf"));
			doc.open();
			com.itextpdf.text.Image image1 = null;
			try {
				image1 = com.itextpdf.text.Image.getInstance("C:\\temp\\temp.png");
			} catch (IOException e) {
				e.printStackTrace();
			}
	        doc.setPageSize(new Rectangle(image1.getWidth(), image1.getHeight()));
	        doc.setMargins(0, 0, 0, 0);
	        doc.newPage();
	        doc.add(image1);
	        doc.close();
	        writer.close();
			
		} catch (FileNotFoundException | DocumentException e) {
			e.printStackTrace();
		}
		AckDetail ackDetail=new AckDetail();
		JSONParser parser = new JSONParser();
		try {
			URL url = new URL(sorceURL+role+"/user="+username);
			 URLConnection yc = url.openConnection();
	         BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	         String inputLine;
	            while ((inputLine = in.readLine()) != null) {               
	                JSONArray a = (JSONArray) parser.parse(inputLine);
	                for (Object o : a) {
	                	 JSONObject tutorials = (JSONObject) o;
		                    InputStream input = new ByteArrayInputStream(Base64.getDecoder().decode(tutorials.get("fileBytes").toString()));
		                    BufferedImage image = ImageIO.read(input);
		                    BufferedImage newBi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		            	    newBi.getGraphics().drawImage(image, 0, 0, null);
		                    ImageIO.write(newBi, "png", new File("C:\\temp\\temp.png"));
		                    ackDetail.setAckno(tutorials.get("ackno").toString());
		                    ackDetail.setFilepath("C:\\temp\\temp.png");
		                    ackDetail.setEmail(tutorials.get("email").toString());
		                    ackDetail.setSamackno(tutorials.get("samackno").toString());
		                    ackDetail.setAckdate(tutorials.get("ackdate").toString());
		                    ackDetail.setResname(tutorials.get("resname").toString());
		                    ackDetail.setScandate(tutorials.get("scandate").toString());
		                    ackDetail.setSmartinwardno(tutorials.get("smartinwardno").toString());
	                }
	            }
	            in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ackDetail;
	}

	public AckDetail getImageReject(String username, String role, String ackno, String email, String samackno,
			String ackdate, String resname, String smartinwardno, String scandate) {
		
		AckDetail ackDetail=new AckDetail();
		JSONParser parser = new JSONParser();
		try {
			URL url = new URL(sorceURL+"reject/imgid="+smartinwardno+"&"+username+"&"+ackno);
			 URLConnection yc = url.openConnection();
	         BufferedReader in = new BufferedReader(new InputStreamReader(yc.getInputStream()));
	         String inputLine;
	            while ((inputLine = in.readLine()) != null) {               
	                JSONArray a = (JSONArray) parser.parse(inputLine);
	                for (Object o : a) {
	                	 JSONObject tutorials = (JSONObject) o;
		                    InputStream input = new ByteArrayInputStream(Base64.getDecoder().decode(tutorials.get("fileBytes").toString()));
		                    BufferedImage image = ImageIO.read(input);
		                    BufferedImage newBi = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_BYTE_BINARY);
		            	    newBi.getGraphics().drawImage(image, 0, 0, null);
		                    ImageIO.write(newBi, "png", new File("C:\\temp\\temp.png"));
		                    ackDetail.setAckno(tutorials.get("ackno").toString());
		                    ackDetail.setFilepath("C:\\temp\\temp.png");
		                    ackDetail.setEmail(tutorials.get("email").toString());
		                    ackDetail.setSamackno(tutorials.get("samackno").toString());
		                    ackDetail.setAckdate(tutorials.get("ackdate").toString());
		                    ackDetail.setResname(tutorials.get("resname").toString());
		                    ackDetail.setScandate(tutorials.get("scandate").toString());
		                    ackDetail.setSmartinwardno(tutorials.get("smartinwardno").toString());
	                }
	            }
	            in.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ackDetail;
	}
}
