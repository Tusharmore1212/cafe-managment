package com.inn.cafe.serviceImp;
//
//import java.io.File;
//import com.itextpdf.text.Image;
//import java.io.FileInputStream;
//import java.io.FileOutputStream;
//import java.io.IOException;
//import java.util.ArrayList;
//import java.util.HashMap;
//import java.util.List;
//import java.util.Map;
//import java.util.stream.Stream;
//import com.google.gson.Gson;
//import com.google.gson.reflect.TypeToken;
//import java.lang.reflect.Type;
//import java.util.Map;
//
//import org.apache.pdfbox.io.IOUtils;
//import org.json.JSONArray;
//import org.slf4j.Logger;
//import org.slf4j.LoggerFactory;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
//import org.springframework.dao.RecoverableDataAccessException;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.stereotype.Service;
//
//import com.google.common.base.Strings;
//import com.inn.cafe.JWT.JwtFilter;
//import com.inn.cafe.POJO.Bill;
//import com.inn.cafe.POJO.Category;
//import com.inn.cafe.constents.CafeConstants;
//import com.inn.cafe.dao.BillDao;
//import com.inn.cafe.service.BillService;
//import com.inn.cafe.utils.CafeUtils;
//import com.itextpdf.awt.geom.Rectangle;
//import com.itextpdf.text.BaseColor;
//import com.itextpdf.text.Document;
//import com.itextpdf.text.DocumentException;
//import com.itextpdf.text.Element;
//import com.itextpdf.text.Font;
//import com.itextpdf.text.FontFactory;
//import com.itextpdf.text.Paragraph;
//import com.itextpdf.text.Phrase;
//import com.itextpdf.text.pdf.PdfContentByte;
//import com.itextpdf.text.pdf.PdfPCell;
//import com.itextpdf.text.pdf.PdfPTable;
//import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
//import com.itextpdf.text.pdf.codec.Base64.InputStream;
//import com.itextpdf.text.pdf.PdfWriter;
//
//import lombok.Data;
//import lombok.extern.slf4j.Slf4j;
//@Slf4j
//@Service
//public class BillServiceImp implements BillService {
//	
//    private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);
//    @Autowired
//    JwtFilter jwtFilter;    
//    
//    
//    @Autowired
//    BillDao billDao;
//    
//    @Override
//	public ResponseEntity<String> generateReport(Map<String, Object> reqMap) {
//		log.info("Inside gerateReport");
//		try {
//			String fileName;
//			if(validateRequestMap(reqMap)) {
//				if(reqMap.containsKey("isGenerate") && !(Boolean) reqMap.get("isGenerate")) {
//					fileName=(String) reqMap.get("uuid");
//					}else {
//					fileName= CafeUtils.getUUID();
//					reqMap.put("uuid", fileName);	
//					insertBill(reqMap);
//					}
//				String data = "Name: "+reqMap.get("name")+"\n"+"Contact Number: "+reqMap.get("contactNumber")+"\n"+"Email: "+reqMap.get("email")+"\n"+"Payment Method: "+reqMap.get("paymentMethod");				
//				Document document = new Document();
//				PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream(CafeConstants.STORE_LOCATION+"\\"+fileName+".pdf"));		
//				document.open();
//				setRectangleInPdf(document,writer);
//				Paragraph chunk = new Paragraph("Best BELGIAN WAFFLE",getFont("Header"));		
//				chunk.setAlignment(Element.ALIGN_CENTER);
//				document.add(chunk);				
//				
//				Paragraph paragraph = new Paragraph(data+"\n \n",getFont("Data"));		
//				document.add(paragraph);
//				PdfPTable table = new PdfPTable(5);
//				table.setWidthPercentage(100);
//				addTableHeader(table);
//				
//				JSONArray jsonArray = CafeUtils.getJsonArrayFromString((String)reqMap.get("productDetails"));				
//				for(int i = 0;i<jsonArray.length();i++) {
//					addRows(table,CafeUtils.getMapFroMapJson(jsonArray.getString(i)));						
//				}
//				document.add(table);
//				Paragraph footer = new Paragraph("Total :"+reqMap.get("totalAmount")+"\n"+"Thank you for Visiting. Please visit again!!",getFont("data"));		
//				document.add(footer);
//				
//				Image img = Image.getInstance("F:\\Coffee Shop\\cafe_feed_back.jpeg");
//				img.scaleToFit(300, 300); // Scale the image if necessary
//				img.setAlignment(Element.ALIGN_CENTER);
//				document.add(img);
//
//
//	            document.close();
//				document.close();
//				return new ResponseEntity<>("{\"uuid\":\""+fileName+"\"}",HttpStatus.OK);			
//			
//			
//			}
//			return CafeUtils.getResponseEntity("Required Data not found.",HttpStatus.BAD_REQUEST);
//
//		}catch (Exception e) {	
//			e.printStackTrace();
//		}
//		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}
//
//	private void addRows(PdfPTable table, Map<String, Object> data) {
//		log.info("Inside addRows");
//		table.addCell((String)data.get("name"));
//		table.addCell((String)data.get("category"));
//		table.addCell((String)data.get("quantity"));
//		table.addCell(Double.toString((Double) data.get("price")));		
//		table.addCell(Double.toString((Double) data.get("total")));		
//
//	}
//
//	private void addTableHeader(PdfPTable table) {
//		log.info("Inside addTableHeader");
//		Stream.of("Name","Category","Quantity","Price","Sub Total").forEach(columnTitle -> {PdfPCell header = new PdfPCell(); header.setBackgroundColor(BaseColor.LIGHT_GRAY); header.setBorderWidth(2);header.setPhrase(new Phrase(columnTitle)); header.setBackgroundColor(BaseColor.YELLOW); header.setHorizontalAlignment(Element.ALIGN_CENTER);header.setVerticalAlignment(Element.ALIGN_CENTER); table.addCell(header);});	
//	}
//
//	private Font getFont(String type) {
//		log.info("Inside getFont");
//		switch(type) {
//		case "Header":
//			Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE,18,BaseColor.BLACK);
//			return headerFont;	
//		case  "Data":
//			Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN,11,BaseColor.BLACK);
//			dataFont.setStyle(Font.BOLD);
//		default:
//			return new Font();
//			}
//	}
//	private void setRectangleInPdf(Document document, PdfWriter writer) throws DocumentException {
//		log.info("Inside setRectangleInPdf");
//		Rectangle rect= new Rectangle(577,825,18,15); // you can resize rectangle 
//	     
////		 rect.enableBorderSide(1);
////	     rect.enableBorderSide(2);
////	     rect.enableBorderSide(4);
////	     rect.enableBorderSide(8);
////	     rect.setBorderColor(BaseColor.BLACK);
////	     rect.setBorderWidth(1);
////	     document.add(rect);
//	     PdfContentByte cb = writer.getDirectContent();
//	     cb.rectangle(0, 0, 600, 2000); // x, y, width, height
//	     cb.setLineWidth(1f);
//	     cb.stroke();
//	
//	}
//
//	private void insertBill(Map<String, Object> reqMap) {
//		try {
//			Bill bill = new Bill();
//			bill.setUuid((String)reqMap.get("uuid"));			
//			bill.setName((String) reqMap.get("name"));
//			bill.setEmail((String)reqMap.get("email"));		
//			bill.setContactNumber((String) reqMap.get("contactNumber"));
//			bill.setPaymentmethod((String)reqMap.get("paymentMethod"));
//			bill.setTotal(Integer.parseInt((String) reqMap.get("totalAmount")));
//			bill.setProductDetail((String) reqMap.get("productDetails"));			
//			bill.setCreateBy(jwtFilter.getCurrentUser());	
//			billDao.save(bill);
//			
//		}catch (Exception e) {	
//			e.printStackTrace();
//		}
//	}
//
//	private boolean validateRequestMap(Map<String, Object> reqMap) {
//		
//		return reqMap.containsKey("name")&& reqMap.containsKey("contactNumber")&& reqMap.containsKey("email")&& reqMap.containsKey("paymentMethod")&& reqMap.containsKey("productDetails")&& reqMap.containsKey("totalAmount");
//						
//	}
//
//	@Override
//	public ResponseEntity<List<Bill>> getBills() {
//		List<Bill> list= new ArrayList();	
//		if(jwtFilter.isAdmin()) {
//			list= billDao.getAllBills();
//		}else {
//			list = billDao.getBillByUserName(jwtFilter.getCurrentUser());					
//		}
//		
//		return new ResponseEntity<>(list,HttpStatus.OK);				
//	}
//
//	@Override
//	public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
//		log.info("Inside getPdf: requestMap {}",requestMap);
//		try {
//			byte[] byteArray = new byte[0];
//			if(!requestMap.containsKey("uuid") && validateRequestMap(requestMap)) {
//				return new ResponseEntity<>(byteArray,HttpStatus.BAD_REQUEST);				
//			}
//			else {
//				String filePath = CafeConstants.STORE_LOCATION+"\\"+(String) requestMap.get("uuid")+".pdf";	
//				if(CafeUtils.isFileExist(filePath)){
//					byteArray = getByteArray(filePath);					
//					return new ResponseEntity<>(byteArray,HttpStatus.OK);					
//				}
//				else {
//					requestMap.put("isGenerate", false);				
//					generateReport(requestMap);				
//					byteArray = getByteArray(filePath);
//					return new ResponseEntity<>(byteArray,HttpStatus.OK);					
//				}
//			}
//		}catch (Exception e) {
//			
//			e.printStackTrace();
//			
//		}
//		return null;
//	}
//
//	private byte[] getByteArray(String filePath) throws IOException {
//		File initialfile = new File(filePath);
//		java.io.InputStream targetStream = new FileInputStream(initialfile);	
//		byte[] byteArray = IOUtils.toByteArray(targetStream);
//		targetStream.close();
//		return byteArray;
//		}
//
//	@Override
//	public ResponseEntity<String> deleteBill(Integer id) {
//		try {
//            java.util.Optional<Bill> optional= billDao.findById(id);
//            if(!optional.isEmpty()) {
//           	 	billDao.deleteById(id);            	
//            	return CafeUtils.getResponseEntity("Bill Deleted Successfully", HttpStatus.OK);              	 
//            
//            }else {
//           	 return CafeUtils.getResponseEntity("Bill id does not exist", HttpStatus.OK);
//            }		}catch (Exception e) {	
//			e.printStackTrace();
//		}
//		return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,HttpStatus.INTERNAL_SERVER_ERROR);
//
//	}
//	
//}
//package com.inn.cafe.serviceImp;

import java.io.File;
import com.itextpdf.text.Image;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;

import javax.imageio.ImageIO;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.Map;

import org.apache.pdfbox.io.IOUtils;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.gson.GsonAutoConfiguration;
import org.springframework.dao.RecoverableDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.google.common.base.Strings;
import com.inn.cafe.JWT.JwtFilter;
import com.inn.cafe.POJO.Bill;
import com.inn.cafe.POJO.Category;
import com.inn.cafe.constents.CafeConstants;
import com.inn.cafe.dao.BillDao;
import com.inn.cafe.service.BillService;
import com.inn.cafe.utils.CafeUtils;
import com.itextpdf.awt.geom.Rectangle;
import com.itextpdf.text.BaseColor;
import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.Element;
import com.itextpdf.text.Font;
import com.itextpdf.text.FontFactory;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.Phrase;
import com.itextpdf.text.pdf.PdfContentByte;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfStructTreeController.returnType;
import com.itextpdf.text.pdf.codec.Base64.InputStream;
import com.itextpdf.text.pdf.PdfWriter;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.WriterException;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;

import java.awt.Color;
import java.awt.Graphics2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.EnumMap;
import java.util.Map;

@Slf4j
@Service
public class BillServiceImp implements BillService {

    private static final Logger log = LoggerFactory.getLogger(UserServiceImp.class);

    @Autowired
    JwtFilter jwtFilter;

    @Autowired
    BillDao billDao;

    @Override
    public ResponseEntity<String> generateReport(Map<String, Object> reqMap) {
        log.info("Inside generateReport");
        try {
            String fileName;
            if (validateRequestMap(reqMap)) {
                if (reqMap.containsKey("isGenerate") && !(Boolean) reqMap.get("isGenerate")) {
                    fileName = (String) reqMap.get("uuid");
                } else {
                    fileName = CafeUtils.getUUID();
                    reqMap.put("uuid", fileName);
                    insertBill(reqMap);
                }
                String data = "Name: " + reqMap.get("name") + "\n" + "Contact Number: "
                        + reqMap.get("contactNumber") + "\n" + "Email: " + reqMap.get("email") + "\n"
                        + "Payment Method: " + reqMap.get("paymentMethod");
                Document document = new Document();
                PdfWriter writer = PdfWriter.getInstance(document,
                        new FileOutputStream(CafeConstants.STORE_LOCATION + "\\" + fileName + ".pdf"));
                document.open();
                setRectangleInPdf(document, writer);
                Paragraph chunk = new Paragraph("Best BELGIAN WAFFLE", getFont("Header"));
                chunk.setAlignment(Element.ALIGN_CENTER);
                document.add(chunk);

                Paragraph paragraph = new Paragraph(data + "\n \n", getFont("Data"));
                document.add(paragraph);
                PdfPTable table = new PdfPTable(5);
                table.setWidthPercentage(100);
                addTableHeader(table);

                JSONArray jsonArray = CafeUtils.getJsonArrayFromString((String) reqMap.get("productDetails"));
                for (int i = 0; i < jsonArray.length(); i++) {
                    addRows(table, CafeUtils.getMapFroMapJson(jsonArray.getString(i)));
                }
                document.add(table);
                Paragraph footer = new Paragraph(
                        "Total :" + reqMap.get("totalAmount") + "\n" + "Thank you for Visiting. Please visit again!!",
                        getFont("data"));
                document.add(footer);

                String totalAmount = (String) reqMap.get("totalAmount");
                String upiId = "tusharumore0285-1@okaxis"; // Replace with your actual UPI ID
                byte[] paymentQrCodeBytes = generateQRCode(totalAmount, upiId);
                Image paymentQrCodeImage = Image.getInstance(paymentQrCodeBytes);
                paymentQrCodeImage.setAlignment(Element.ALIGN_CENTER);

                // Add a label for the payment QR code
                Paragraph paymentQrLabel = new Paragraph("Scan this QR code for payment:", getFont("Data"));
                paymentQrLabel.setAlignment(Element.ALIGN_CENTER);

                // Add the payment QR code and label to the document
                document.add(paymentQrLabel);
                document.add(paymentQrCodeImage);

                Image img = Image.getInstance("F:\\Coffee Shop\\cafe_feed_back.jpeg");
                img.scaleToFit(190, 190); // Scale the image if necessary
                img.setAlignment(Element.ALIGN_CENTER);

                // Add the label for feedback QR code
                Paragraph feedbackQrLabel = new Paragraph("Scan this QR code for feedback:", getFont("Data"));
                feedbackQrLabel.setAlignment(Element.ALIGN_CENTER);

                // Add the feedback QR label and image to the document
                document.add(feedbackQrLabel);
                document.add(img);


                document.close();
                return new ResponseEntity<>("{\"uuid\":\"" + fileName + "\"}", HttpStatus.OK);

            }
            return CafeUtils.getResponseEntity("Required Data not found.", HttpStatus.BAD_REQUEST);

        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,
                HttpStatus.INTERNAL_SERVER_ERROR);
    }

    private byte[] generateQRCode(String totalAmount, String upiId) throws IOException, WriterException {
        int size = 200; // Size of the QR code
        String gpayUrl = "upi://pay?pa=" + upiId + "&am=" + totalAmount; // GPay URL with UPI ID and total amount
        String qrCodeText = gpayUrl; // QR code text is the GPay URL

        // Configure the QR code writer
        Map<EncodeHintType, Object> hints = new EnumMap<>(EncodeHintType.class);
        hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        QRCodeWriter qrCodeWriter = new QRCodeWriter();
        BitMatrix bitMatrix = qrCodeWriter.encode(qrCodeText, com.google.zxing.BarcodeFormat.QR_CODE, size, size, hints);

        // Create buffered image to draw QR code
        BufferedImage qrImage = new BufferedImage(size, size, BufferedImage.TYPE_INT_RGB);
        qrImage.createGraphics();
        Graphics2D graphics = (Graphics2D) qrImage.getGraphics();
        graphics.setColor(Color.WHITE);
        graphics.fillRect(0, 0, size, size);
        graphics.setColor(Color.BLACK);

        // Draw QR code to the buffered image
        for (int i = 0; i < size; i++) {
            for (int j = 0; j < size; j++) {
                if (bitMatrix.get(i, j)) {
                    graphics.fillRect(i, j, 1, 1);
                }
            }
        }

        // Convert buffered image to byte array
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(qrImage, "png", byteArrayOutputStream);
        byte[] byteArray = byteArrayOutputStream.toByteArray();
        byteArrayOutputStream.close();
        return byteArray;
    }


    // Existing methods

    private void addRows(PdfPTable table, Map<String, Object> data) {
        log.info("Inside addRows");
        table.addCell((String) data.get("name"));
        table.addCell((String) data.get("category"));
        table.addCell((String) data.get("quantity"));
        table.addCell(Double.toString((Double) data.get("price")));
        table.addCell(Double.toString((Double) data.get("total")));
    }

    private void addTableHeader(PdfPTable table) {
        log.info("Inside addTableHeader");
        Stream.of("Name", "Category", "Quantity", "Price", "Sub Total").forEach(columnTitle -> {
            PdfPCell header = new PdfPCell();
            header.setBackgroundColor(BaseColor.LIGHT_GRAY);
            header.setBorderWidth(2);
            header.setPhrase(new Phrase(columnTitle));
            header.setBackgroundColor(BaseColor.YELLOW);
            header.setHorizontalAlignment(Element.ALIGN_CENTER);
            header.setVerticalAlignment(Element.ALIGN_CENTER);
            table.addCell(header);
        });
    }

    private Font getFont(String type) {
        log.info("Inside getFont");
        switch (type) {
            case "Header":
                Font headerFont = FontFactory.getFont(FontFactory.HELVETICA_BOLDOBLIQUE, 18, BaseColor.BLACK);
                return headerFont;
            case "Data":
                Font dataFont = FontFactory.getFont(FontFactory.TIMES_ROMAN, 11, BaseColor.BLACK);
                dataFont.setStyle(Font.BOLD);
                return dataFont;
            default:
                return new Font();
        }
    }

    private void setRectangleInPdf(Document document, PdfWriter writer) throws DocumentException {
        log.info("Inside setRectangleInPdf");
        Rectangle rect = new Rectangle(577, 825, 18, 15); // you can resize rectangle

        //		 rect.enableBorderSide(1);
        //	     rect.enableBorderSide(2);
        //	     rect.enableBorderSide(4);
        //	     rect.enableBorderSide(8);
        //	     rect.setBorderColor(BaseColor.BLACK);
        //	     rect.setBorderWidth(1);
        //	     document.add(rect);
        PdfContentByte cb = writer.getDirectContent();
        cb.rectangle(0, 0, 600, 2000); // x, y, width, height
        cb.setLineWidth(1f);
        cb.stroke();
    }

    private void insertBill(Map<String, Object> reqMap) {
        try {
            Bill bill = new Bill();
            bill.setUuid((String) reqMap.get("uuid"));
            bill.setName((String) reqMap.get("name"));
            bill.setEmail((String) reqMap.get("email"));
            bill.setContactNumber((String) reqMap.get("contactNumber"));
            bill.setPaymentmethod((String) reqMap.get("paymentMethod"));
            bill.setTotal(Integer.parseInt((String) reqMap.get("totalAmount")));
            bill.setProductDetail((String) reqMap.get("productDetails"));
            bill.setCreateBy(jwtFilter.getCurrentUser());
            billDao.save(bill);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private boolean validateRequestMap(Map<String, Object> reqMap) {

        return reqMap.containsKey("name") && reqMap.containsKey("contactNumber") && reqMap.containsKey("email")
                && reqMap.containsKey("paymentMethod") && reqMap.containsKey("productDetails")
                && reqMap.containsKey("totalAmount");

    }

    @Override
    public ResponseEntity<List<Bill>> getBills() {
        List<Bill> list = new ArrayList();
        if (jwtFilter.isAdmin()) {
            list = billDao.getAllBills();
        } else {
            list = billDao.getBillByUserName(jwtFilter.getCurrentUser());
        }

        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @Override
    public ResponseEntity<byte[]> getPdf(Map<String, Object> requestMap) {
        log.info("Inside getPdf: requestMap {}", requestMap);
        try {
            byte[] byteArray = new byte[0];
            if (!requestMap.containsKey("uuid") && validateRequestMap(requestMap)) {
                return new ResponseEntity<>(byteArray, HttpStatus.BAD_REQUEST);
            } else {
                String filePath = CafeConstants.STORE_LOCATION + "\\" + (String) requestMap.get("uuid") + ".pdf";
                if (CafeUtils.isFileExist(filePath)) {
                    byteArray = getByteArray(filePath);
                    return new ResponseEntity<>(byteArray, HttpStatus.OK);
                } else {
                    requestMap.put("isGenerate", false);
                    generateReport(requestMap);
                    byteArray = getByteArray(filePath);
                    return new ResponseEntity<>(byteArray, HttpStatus.OK);
                }
            }
        } catch (Exception e) {

            e.printStackTrace();

        }
        return null;
    }

    private byte[] getByteArray(String filePath) throws IOException {
        File initialfile = new File(filePath);
        java.io.InputStream targetStream = new FileInputStream(initialfile);
        byte[] byteArray = IOUtils.toByteArray(targetStream);
        targetStream.close();
        return byteArray;
    }

    @Override
    public ResponseEntity<String> deleteBill(Integer id) {
        try {
            java.util.Optional<Bill> optional = billDao.findById(id);
            if (!optional.isEmpty()) {
                billDao.deleteById(id);
                return CafeUtils.getResponseEntity("Bill Deleted Successfully", HttpStatus.OK);

            } else {
                return CafeUtils.getResponseEntity("Bill id does not exist", HttpStatus.OK);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return CafeUtils.getResponseEntity(CafeConstants.SOMETHING_WENT_WRON_STRING,
                HttpStatus.INTERNAL_SERVER_ERROR);

    }

}

