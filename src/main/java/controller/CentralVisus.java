package controller;

import java.io.FileNotFoundException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalTime;
import java.util.Date;

import com.jfoenix.controls.JFXButton;
import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Accordion;
import javafx.scene.control.Label;
import javafx.scene.control.TitledPane;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

public class CentralVisus {
    @FXML
    private VBox vboxLeft;
    @FXML
    private BorderPane bpPrincipal;
    @FXML
    private JFXButton btnGenerarBC;
    @FXML
    private JFXButton btnCierreZ;
    @FXML
	private JFXButton btnProcesos;
    @FXML
    private JFXButton btnRubros;
	@FXML
	private JFXButton btnMarcas;
	@FXML
	private JFXButton btnLineas;
	@FXML
	private JFXButton btnArticulos;
	@FXML
	private JFXButton btnPresentaciones;
	@FXML
	private JFXButton btnUnidades;
	@FXML
	private JFXButton btnUbicaciones;
	@FXML
	private JFXButton btnDepositos;
	@FXML
	private JFXButton btnStaff;
	@FXML
	private JFXButton btnLocalidades;
	@FXML
	private JFXButton btnBancos;
	@FXML
    private JFXButton btnConfiguracion;
	@FXML
	private JFXButton btnPersonas;
	@FXML
	private JFXButton btnConsultarPersonas;
	@FXML
	private JFXButton btnProveedores;
	@FXML
	private JFXButton btnCaja;
	@FXML
	private JFXButton btnPrecios;
	@FXML
	private JFXButton btnCerrar;
	@FXML
    private JFXButton btnActPorUtilidad;
	@FXML
	private JFXButton btnActPorUtilidadFraccionados;
    @FXML
    private JFXButton btnActPorRubro;
    @FXML
    private JFXButton btnActPorRubroLinea;
    @FXML
    private JFXButton btnActPorListas;
    @FXML
    private JFXButton btnActPorMarca;
    @FXML
    private Accordion accordion;
	@FXML
	private TitledPane tpInventario;
	@FXML
	private TitledPane tpClientes;
	@FXML
	private TitledPane tpProveedores;
	@FXML
	private TitledPane tpCompras;
	@FXML
	private TitledPane tpVentas;
	@FXML
	private TitledPane tpHerramientas;
	@FXML
	private TitledPane tpPrecios;
	@FXML
	private TitledPane tpCaja;
	
	@FXML
	private ImageView imgLocalidades;
	@FXML
	private ImageView imgBancos;
	@FXML
	private ImageView imgStaff;
	@FXML
	private ImageView imgPersonas;
	@FXML
	private ImageView imgConsultarPersonas;
	@FXML
	private ImageView imgCaja;
	@FXML
	private ImageView imgArticulos;
	@FXML
	private ImageView imgLineas;
	@FXML
	private ImageView imgRubros;
	@FXML
	private ImageView imgPresentacion;
	@FXML
	private ImageView imgUnidades;
	@FXML
	private ImageView imgUbicaciones;
	@FXML
	private ImageView imgDepositos;
	@FXML
	private ImageView imgMarcas;
	@FXML
	private ImageView imgBarcode;
	@FXML
	private ImageView imgPrecios;
	@FXML
	private ImageView imgLogin;
	@FXML
	private ImageView imgSalir;
	@FXML
	private ImageView imgLogo;
	@FXML
	private ImageView imgCerrar;
	@FXML
	private Label lblFecha;
	@FXML
	private Label lblHora;
	@FXML
	private JFXButton btnLogin;
	@FXML
	private JFXButton btnSalir;

    
    private double dragOffsetX = 0;
	private double dragOffsetY = 0;
	private static final DateFormat sdf = new SimpleDateFormat("dd MMM yyyy");
	/**
	 * Initializes the controller class. This method is automatically called
	 * after the fxml file has been loaded.
	 * @return 
	 * @return 
	 */
	
    @FXML
	public void initialize() throws FileNotFoundException {
    	
    	Date date = new Date();
        lblFecha.setText((sdf.format(date)));
        
        Timeline clock = new Timeline(new KeyFrame(Duration.ZERO, e -> {        
        	LocalTime currentTime = LocalTime.now();
        	lblHora.setText(currentTime.getHour() + ":" + currentTime.getMinute() + ":" + currentTime.getSecond());
        }),
             new KeyFrame(Duration.seconds(1))
        );
        clock.setCycleCount(Animation.INDEFINITE);
        clock.play();
    	
//      imgLogo.setImage(new Image("/images/datawarehouse.png"));
        imgLogo.setImage(new Image("/resources/images/datawarehouse.png"));
		imgLogo.setSmooth(true);
		imgLogo.setPreserveRatio(true);
		
//		imgLogin.setImage(new Image("/images/ic_account_circle_black_36dp.png"));
		imgLogin.setImage(new Image("/resources/images/ic_account_circle_black_36dp.png"));
		imgLogin.setSmooth(true);
		imgLogin.setPreserveRatio(true);
		
//		imgSalir.setImage(new Image("/images/ic_exit_to_app_black_36dp.png"));
		imgSalir.setImage(new Image("/resources/images/ic_exit_to_app_black_36dp.png"));
		imgSalir.setSmooth(true);
		imgSalir.setPreserveRatio(true);
		
//		imgLocalidades.setImage(new Image("/images/ic_business_black_36dp.png"));
		imgLocalidades.setImage(new Image("/resources/images/ic_business_black_36dp.png"));
		imgLocalidades.setSmooth(true);
		imgLocalidades.setPreserveRatio(true);
		
//		imgBancos.setImage(new Image("/images/ic_account_balance_black_48dp.png"));
		imgBancos.setImage(new Image("/resources/images/ic_account_balance_black_48dp.png"));
		imgBancos.setSmooth(true);
		imgBancos.setPreserveRatio(true);
		
//		imgPersonas.setImage(new Image("/images/ic_contacts_black_36dp.png"));
		imgPersonas.setImage(new Image("/resources/images/ic_contacts_black_36dp.png"));
		imgPersonas.setSmooth(true);
		imgPersonas.setPreserveRatio(true);
		
//		imgConsultarPersonas.setImage(new Image("/images/ic_contact_phone_black_36dp.png"));
		imgConsultarPersonas.setImage(new Image("/resources/images/ic_contact_phone_black_36dp.png"));
		imgConsultarPersonas.setSmooth(true);
		imgConsultarPersonas.setPreserveRatio(true);
		
//		imgCaja.setImage(new Image("/images/ic_local_atm_black_36dp.png"));
		imgCaja.setImage(new Image("/resources/images/ic_local_atm_black_36dp.png"));
		imgCaja.setSmooth(true);
		imgCaja.setPreserveRatio(true);
		
//		imgArticulos.setImage(new Image("/images/ic_local_convenience_store_black_36dp.png"));
		imgArticulos.setImage(new Image("/resources/images/ic_local_convenience_store_black_36dp.png"));
		imgArticulos.setSmooth(true);
		imgArticulos.setPreserveRatio(true);
		
//		imgLineas.setImage(new Image("/images/ic_assessment_black_36dp.png"));
		imgLineas.setImage(new Image("/resources/images/ic_assessment_black_36dp.png"));
		imgLineas.setSmooth(true);
		imgLineas.setPreserveRatio(true);
		
//		imgRubros.setImage(new Image("/images/ic_assignment_black_36dp.png"));
		imgRubros.setImage(new Image("/resources/images/ic_assignment_black_36dp.png"));
		imgRubros.setSmooth(true);
		imgRubros.setPreserveRatio(true);
		
//		imgPresentacion.setImage(new Image("/images/ic_book_black_36dp.png"));
		imgPresentacion.setImage(new Image("/resources/images/ic_book_black_36dp.png"));
		imgPresentacion.setSmooth(true);
		imgPresentacion.setPreserveRatio(true);
		
//		imgUnidades.setImage(new Image("/images/ic_build_black_36dp.png"));
		imgUnidades.setImage(new Image("/resources/images/ic_build_black_36dp.png"));
		imgUnidades.setSmooth(true);
		imgUnidades.setPreserveRatio(true);
		
//		imgUbicaciones.setImage(new Image("/images/ic_group_work_black_36dp.png"));
		imgUbicaciones.setImage(new Image("/resources/images/ic_group_work_black_36dp.png"));
		imgUbicaciones.setSmooth(true);
		imgUbicaciones.setPreserveRatio(true);
		
//		imgDepositos.setImage(new Image("/images/ic_widgets_black_36dp.png"));
		imgDepositos.setImage(new Image("/resources/images/ic_widgets_black_36dp.png"));
		imgDepositos.setSmooth(true);
		imgDepositos.setPreserveRatio(true);
		
//		imgMarcas.setImage(new Image("/images/ic_collections_bookmark_black_36dp.png"));
		imgMarcas.setImage(new Image("/resources/images/ic_collections_bookmark_black_36dp.png"));
		imgMarcas.setSmooth(true);
		imgMarcas.setPreserveRatio(true);
		
//		imgBarcode.setImage(new Image("/images/barcode.png"));
		imgBarcode.setImage(new Image("/resources/images/barcode.png"));
		imgBarcode.setSmooth(true);
		imgBarcode.setPreserveRatio(true);
		
//		imgPrecios.setImage(new Image("/images/ic_local_offer_black_36dp.png"));
		imgPrecios.setImage(new Image("/resources/images/ic_local_offer_black_36dp.png"));
		imgPrecios.setSmooth(true);
		imgPrecios.setPreserveRatio(true);
		
//		imgCerrar.setImage(new Image("/images/ic_assignment_return_black_18dp.png"));
		imgCerrar.setImage(new Image("/resources/images/ic_assignment_return_black_18dp.png"));
		imgCerrar.setSmooth(true);
		imgCerrar.setPreserveRatio(true);
		
		bpPrincipal.setLeft(null);
		
		btnCaja.setOnAction(e -> {
			
				
			
		});
		
		btnCierreZ.setOnAction(e -> {
			
				
				
			
		});
		
		btnCerrar.setOnAction(e -> {
    		bpPrincipal.setLeft(null);	
		});
		
		btnBancos.setOnAction(e -> {
			try {
//				String fxmlFile = "/fxml/bancos.fxml"; // para el IDE 
				String fxmlFile = "/resources/fxml/bancos.fxml";
				FXMLLoader loader = new FXMLLoader();
				Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
				
				final Scene scene = new Scene(root);
				
				Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
				
				scene.setOnMousePressed(e1 -> {
					dragOffsetX = e1.getSceneX();
					dragOffsetY = e1.getSceneY();
				});
				
				scene.setOnMouseDragged(e1 -> {
					stage.setX(e1.getScreenX() - dragOffsetX);
					stage.setY(e1.getScreenY() - dragOffsetY);
				});
				
				stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
					if (KeyCode.ESCAPE == event.getCode()) {
						stage.close();
					}
				});
				
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.show();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		btnLocalidades.setOnAction(e -> {
			try {
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/localidades.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/localidades.fxml"));
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				Scene scene = new Scene(loader.load());
				
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
				
				scene.setOnMousePressed(e1 -> {
					dragOffsetX = e1.getSceneX();
					dragOffsetY = e1.getSceneY();
				});
				
				scene.setOnMouseDragged(e1 -> {
					stage.setX(e1.getScreenX() - dragOffsetX);
					stage.setY(e1.getScreenY() - dragOffsetY);
				});
				
				stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
					if (KeyCode.ESCAPE == event.getCode()) {
						stage.close();
					}
				});
				
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.show();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		btnPersonas.setOnAction(e -> {
			try {
//				FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/localidades.fxml"));
				FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/personas.fxml"));
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
				stage.initModality(Modality.APPLICATION_MODAL);
				Scene scene = new Scene(loader.load());
				
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
				
				scene.setOnMousePressed(e1 -> {
					dragOffsetX = e1.getSceneX();
					dragOffsetY = e1.getSceneY();
				});
				
				scene.setOnMouseDragged(e1 -> {
					stage.setX(e1.getScreenX() - dragOffsetX);
					stage.setY(e1.getScreenY() - dragOffsetY);
				});
				
				stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
					if (KeyCode.ESCAPE == event.getCode()) {
						stage.close();
					}
				});
				
				stage.setScene(scene);
				stage.centerOnScreen();
				stage.show();
				
			} catch (Exception e2) {
				e2.printStackTrace();
			}
		});
		
		btnGenerarBC.setOnAction(e -> {
			try {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/generarBC.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/generarBC.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());

//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");

    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});

    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});

    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});

    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();

    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
		});
		
    	btnArticulos.setOnAction(e -> {
    		try {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/articulos.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/articulos.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());

//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");

    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});

    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});

    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});

    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();

    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnRubros.setOnAction(e -> {
    		try {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/rubros.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/rubros.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnMarcas.setOnAction(e -> {
    		try {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/marcas.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/marcas.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnUnidades.setOnAction(e -> {
    		try {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/unidades.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/unidades.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnDepositos.setOnAction(e -> {
    		try {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/depositos.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/depositos.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnUbicaciones.setOnAction(e -> {
    		try {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/ubicaciones.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/ubicaciones.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnLineas.setOnAction(e -> {
    		try {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/lineas.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/lineas.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnPresentaciones.setOnAction(e -> {
    		try {
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/presentaciones.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/presentaciones.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnActPorListas.setOnAction(e -> {
    		try {
    			/**
    			 * Cierro el TitledPane
    			 */
    			bpPrincipal.setLeft(null);	
    			
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/listas.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/listas.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnActPorUtilidad.setOnAction(e -> {
    		try {
    			/**
    			 * Cierro el TitledPane
    			 */
    			bpPrincipal.setLeft(null);	
    			
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/apUtilidad.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/apUtilidad.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add("/styles/style.css");
    			scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnActPorUtilidadFraccionados.setOnAction(e -> {
    		try {
    			/**
    			 * Cierro el TitledPane
    			 */
    			bpPrincipal.setLeft(null);	
    			
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/apUtilidadFraccionado.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/apUtilidadFraccionado.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add("/styles/style.css");
    			scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnActPorRubro.setOnAction(e -> {
    		try {
    			/**
    			 * Cierro el TitledPane
    			 */
    			bpPrincipal.setLeft(null);	
    			
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/apRubro.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/apRubro.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add("/styles/style.css");
    			scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnActPorRubroLinea.setOnAction(e -> {
    		try {
    			/**
    			 * Cierro el TitledPane
    			 */
    			bpPrincipal.setLeft(null);	
    			
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/apRubroAndLinea.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/apRubroAndLinea.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add("/styles/style.css");
    			scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnActPorMarca.setOnAction(e -> {
    		try {
    			/**
    			 * Cierro el TitledPane
    			 */
    			bpPrincipal.setLeft(null);	
    			
//    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/fxml/apMarca.fxml"));
    			FXMLLoader loader = new FXMLLoader(getClass().getResource("/resources/fxml/apMarca.fxml"));
    			Stage stage = new Stage();
    			stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
    			Scene scene = new Scene(loader.load());
    			
//    			scene.getStylesheets().add("/styles/style.css");
    			scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	
    	
    	
    	
    	
    	
    	btnConfiguracion.setOnAction(e -> {
    		try {
//    			String fxmlFile = "/fxml/configuracion.fxml";
    			String fxmlFile = "/resources/fxml/configuracion.fxml";
				FXMLLoader loader = new FXMLLoader();
				Parent root = (Parent) loader.load(getClass().getResourceAsStream(fxmlFile));
				
				Stage stage = new Stage();
				stage.initStyle(StageStyle.UNDECORATED);
    			stage.initModality(Modality.APPLICATION_MODAL);
				
    			final Scene scene = new Scene(root);
//				scene.getStylesheets().add("/styles/style.css");
				scene.getStylesheets().add("/resources/styles/style.css");
    			
    			scene.setOnMousePressed(e1 -> {
    				dragOffsetX = e1.getSceneX();
    				dragOffsetY = e1.getSceneY();
    			});
    			
    			scene.setOnMouseDragged(e1 -> {
    				stage.setX(e1.getScreenX() - dragOffsetX);
    				stage.setY(e1.getScreenY() - dragOffsetY);
    			});
    			
    			stage.addEventHandler(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {
    				if (KeyCode.ESCAPE == event.getCode()) {
    					stage.close();
    				}
    			});
    			
    			stage.setScene(scene);
    			stage.centerOnScreen();
    			stage.show();
    			
    		} catch (Exception e2) {
    			e2.printStackTrace();
    		}
    	});
    	
    	btnPrecios.setOnAction(e -> {
    		/*
    		 * Desactivo los TitledPane
    		 */
    		tpInventario.setDisable(true);
    		tpClientes.setDisable(true);
    		tpProveedores.setDisable(true);
    		tpCompras.setDisable(true);
    		tpVentas.setDisable(true);
    		tpHerramientas.setDisable(true);
    		tpCaja.setDisable(true);
    		
    		/**
    		 * Activo el TitledPane de Precios
    		 */
    		tpPrecios.setVisible(true);
    		accordion.setExpandedPane(tpPrecios);

    		/**
    		 * Abro el TitledPane
    		 */
    		bpPrincipal.setLeft(vboxLeft);
    	});
    	
    	
    	
    	
    	
    	
    	btnSalir.setOnAction(e -> {
    		Platform.exit();
    		System.exit(0);
    	});



    }

}