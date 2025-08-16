package controller;


import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Arrays;
import java.util.Date;

import java.util.logging.Level;
import java.util.logging.Logger;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintException;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.HashAttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.PrinterName;

import com.github.anastaciocintra.escpos.EscPos;
import com.github.anastaciocintra.escpos.EscPos.CharacterCodeTable;
import com.github.anastaciocintra.escpos.Style;
import com.github.anastaciocintra.escpos.barcode.BarCode;
import com.github.anastaciocintra.output.PrinterOutputStream;
import com.github.anastaciocintra.escpos.EscPosConst;
import com.jfoenix.controls.JFXTextField;

import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.util.Duration;

public class GenerarBC {
	@FXML
	private ImageView imgHeader;
	@FXML
	private JFXTextField btnCodigoInterno;
	@FXML
	private JFXTextField btnCantidadEtiquetas;
	@FXML
	private Button btnGenerar;
	@FXML
	private Label lblFecha;
	@FXML
	private Label lblHora;
	@FXML
	private Label lblImpresora;
	
	
	private final byte[] ALIGN_CENTER = {0x1b, 0x61, 0x01};
	private final byte[] ALIGN_LEFT = {0x1b, 0x61, 0x00};
	private final byte[] ALIGN_RIGHT = {0x1b, 0x61, 0x02};
	private final byte[] TEXT_SIZE_NORMAL = {0x1b, 0x21, 0x00};
	private final byte[] TEXT_SIZE_LARGE = {0x1b, 0x21, 0x30};
	private final byte[] INVERTED_COLOR_ON = {0x1d, 0x42, 0x01};
	private final byte[] BEEPER = {0x1b,0x42,0x05,0x05};
	private final byte[] INIT = {0x1b, 0x40};

	private static final DateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	private static PrintRequestAttributeSet pras = new HashPrintRequestAttributeSet();
//	private static DocFlavor flavor = DocFlavor.INPUT_STREAM.AUTOSENSE;
	private static DocFlavor flavor = DocFlavor.BYTE_ARRAY.AUTOSENSE;
	
	private PrintService printService;

//	public static DocPrintJob job;

	@FXML
	public void initialize() throws FileNotFoundException {

		Date date = new Date();
		lblFecha.setText((sdf.format(date)));

		Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {
			LocalTime currentTime = LocalTime.now();
			lblHora.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
		}), new KeyFrame(Duration.seconds(1)));
		clock.setCycleCount(Animation.INDEFINITE);
		clock.play();

		imgHeader.setImage(new Image("/images/barcode.png"));
		imgHeader.setSmooth(true);
		imgHeader.setPreserveRatio(true);

		buscarImpresora("Brother QL-800");

		btnGenerar.setOnAction(event -> {

			/**
			 * OPCIÓN 1: no anda
			 */
			
//			PrintService printService = PrinterOutputStream.getPrintServiceByName("Brother QL-800");
//			
//			DocPrintJob job = printService.createPrintJob();  
//
//			String commands = "";
//			commands += "\\u001B\\u0045\\u000A";
//			commands += "\\u001B\\u0045\\u000A"; // plain
//			commands += "Hello ";
//			commands += "\\u001B\\u0045\\u000D"; // bold
//			commands += "ESCP!";
//			// flavor tiene que ser de tipo BYTE_ARRAY
//			Doc doc = new SimpleDoc(commands.getBytes(), flavor, null);
//			try {
//				job.print(doc, null);
//			} catch (PrintException e1) {
//				Logger.getLogger(GenerarBC.class.getName()).log(Level.SEVERE, null, e1);
//			}
			
			
			/**
			 * OPCIÓN 2: Usando la librería escpos-coffee tampoco 
			 */
			
//			PrintService printService = PrinterOutputStream.getPrintServiceByName("Brother QL-800");
//			EscPos escPos;
//			
//			try {
//				
//				escPos = new EscPos(new PrinterOutputStream(printService));
//
//				Style title = new Style().setFontSize(Style.FontSize._2, Style.FontSize._2)
//						.setJustification(EscPosConst.Justification.Center);
//				
//				escPos.writeLF(title, "Barcode");
//	            escPos.feed(2);
//	            BarCode barcode = new BarCode();
//				
//	            escPos.writeLF("barcode default options EAN13 system");
//	            escPos.feed(2);
//	            escPos.write(barcode, "12345678901");
//	            escPos.feed(3);
//				
//	            escPos.cut(EscPos.CutMode.FULL);
//	            
//	            Doc doc = new SimpleDoc(escPos.toString().getBytes(), flavor, null);
//	            DocPrintJob dpj = printService.createPrintJob();
//	            try {
//	            	dpj.print(doc, null);
//	            	escPos.close();
//	            } catch (PrintException e1) {
//	            	// TODO Auto-generated catch block
//	            	e1.printStackTrace();
//	            }
//	            
//			} catch (IOException e1) {
//	            Logger.getLogger(GenerarBC.class.getName()).log(Level.SEVERE, null, e1);
//			}
//
//			
//			
			/**
			 * OPCIÓN 3: no anda
			 */
			
			PrintService printService = PrinterOutputStream.getPrintServiceByName("Brother QL-800");
			
			DocPrintJob job = printService.createPrintJob();  
			
			String data = 
			            "\\x1B" + "\\x69" + "\\x61" + "\\x00" + "\\x1B" + "\\x40\r\n" // set printer to ESC/P mode and clear memory buffer
			            + "\\x1B" + "\\x69" + "\\x4C" + "\\x01\r\n"  // set landscape mode
			            + "\\x1B" + "\\x55" + "\\x02" + "\\x1B" + "\\x33" + "\\x0F\r\n" // set margin (02) and line feed (0F) values
			            + "\\x1B" + "\\x6B" + "\\x0B" + "\\x1B" + "\\x58" + "\\x00" + "\\x3A" + "\\x00\r\n" // set font and font size 
			            + "Printed by \r\n" // "Printed by "
			            + "QZ-Tray\r\n" // "QZ-Tray"
			            + "\\x0A" + "\\x0A\r\n"// line feed 2 times
			            + "\\x1B" + "\\x69" + "\\x74" + "\\x30\r\n" // set to code39 barcode
			            + "\\x72" + "\\x31\r\n" // characters below barcode
			            + "\\x65" + "\\x30" + "\\x68" + "\\x65" + "\\x00" + "\\x77" +"\\x34" + "\\x7A" + "\\x32\r\n" // parentheses y/n, height, width of barcode, 2:1 ratio wide to narrow bars
			            + "\\x42" + "1234567890" + "\\x5C\r\n" // begin barcode data, data, end barcode data
			            + "\\x0A" + "\\x0A\r\n" // line feed 2x
			            + "\\x0C"; // <--- Tells the printer to print 
			
			// flavor tiene que ser de tipo BYTE_ARRAY
			Doc doc;
			try {
				doc = new SimpleDoc(data.getBytes("UTF-8"), flavor, null);
				job.print(doc, null);
			} catch (UnsupportedEncodingException | PrintException e1) {
				Logger.getLogger(GenerarBC.class.getName()).log(Level.SEVERE, null, e1);
			}
			
			
			
			/**
			 * OPCIÓN 4: no anda
			 */
			
//			PrintService printService = PrinterOutputStream.getPrintServiceByName("Brother QL-800");
//
//			char[] initEP = new char[] { 0x1b, '@' };
//			char[] cutP = new char[] { 0x1d, 'V', 1 };
//			String Ptxt = new String(initEP) + " text data \n \n \n" + new String(cutP);
//
//			InputStream pis = new ByteArrayInputStream(Ptxt.getBytes());
//			Doc d = new SimpleDoc(pis, flavor, null);
//
//			if (printService != null) {
//				DocPrintJob job = printService.createPrintJob();
//				try {
//					job.print(d, null);
//				} catch (PrintException e1) {
//					Logger.getLogger(GenerarBC.class.getName()).log(Level.SEVERE, null, e1);
//				}
//			}
			
			
				
//				pras.add(new Copies(1));   
				
//				String _ESC_P_Code = "ESC i a 00h\r\n" + 				// Selecciono el modo ESC/P 
//				"ESC @\r\n" +											// Inicializo la impresora a sus valores por defecto (1Bh, 40h)
//        		"ESC i L 01h\r\n" +										// Orientación apaisada
//        		"ESC i t5 r0 he0h 01h w3 z0 B 7798899123452 \r\n" +		// BC EAN13        		
//        		"ESC i C\r\n" +											// Cutt feed
//        		"FF";													// Comienzo de impresión
				
				

				
				
			

		});

	}

	private void buscarImpresora(String impresora) {
		HashAttributeSet attributeSet = new HashAttributeSet();
		
		attributeSet.add(new PrinterName(impresora, null));
		PrintService[] services = PrintServiceLookup.lookupPrintServices(flavor, attributeSet);
		if (services.length == 0) {
			lblImpresora.setText("Impresora no encontrada");
		} else if (services.length > 1) {
			lblImpresora.setText("Más de una impresora. Se usará la primera");
		}
		printService = services[0];
		
		/**
		 * Esto es para saber que streams soporta la impresora 
		 */
//		if (printService.getName().contains("Brother QL-800")) {
//		    Arrays.stream(printService.getSupportedDocFlavors()).forEach(f->System.out.println(f.getMediaType() + ":" + f.getMimeType() + ":" + f.getRepresentationClassName()));
//		}

		Timeline timeline = new Timeline(new KeyFrame(Duration.seconds(1.20), evt -> lblImpresora.setVisible(false)),
				new KeyFrame(Duration.seconds(2.0), evt -> lblImpresora.setVisible(true)));
		timeline.setCycleCount(Animation.INDEFINITE);
		timeline.play();

		lblImpresora.setText("Impresora encontrada: " + printService.getName());
	};

}