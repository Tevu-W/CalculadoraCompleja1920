package CalculadoraCompleja;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.geometry.Orientation;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Separator;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

public class CalculadoraCompleja extends Application {

	// modelo
	
	private DoubleProperty OP1Real = new SimpleDoubleProperty();
	private DoubleProperty OP2Imagin = new SimpleDoubleProperty();
	private DoubleProperty OP3Real = new SimpleDoubleProperty();
	private DoubleProperty OP4Imagin = new SimpleDoubleProperty();
	private DoubleProperty Real = new SimpleDoubleProperty();
	private DoubleProperty Imaginario = new SimpleDoubleProperty();
	private StringProperty operador = new SimpleStringProperty();


	private DoubleBinding op1 = OP1Real.multiply(OP3Real);
	private DoubleBinding op2 = OP2Imagin.multiply(OP4Imagin);
	private DoubleBinding op3 = OP1Real.multiply(OP4Imagin);
	private DoubleBinding op4 = OP2Imagin.multiply(OP3Real);
	
	private DoubleBinding AC = OP1Real.multiply(OP3Real);
	private DoubleBinding BD = OP2Imagin.multiply(OP4Imagin);
	private DoubleBinding BC = OP2Imagin.multiply(OP3Real);
	private DoubleBinding AD = OP1Real.multiply(OP4Imagin);
	private DoubleBinding P3_2 = OP3Real.multiply(OP3Real);
	private DoubleBinding P4_2 = OP4Imagin.multiply(OP4Imagin);
	private DoubleBinding Suma = AC.add(BD);
	private DoubleBinding Resta = BC.subtract(AD);
	private DoubleBinding Suma_2 = P3_2.add(P4_2);
	
	
	// vista

	private TextField OP1TextReal;
	private TextField OP2TextImagin;
	private TextField OP3TextReal;
	private TextField OP4TextImagin;
	private TextField RealText;
	private TextField ImaginarioText;
	private ComboBox<String> operadorCombo;
	private Separator separador;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	
		OP1TextReal = new TextField();
		OP1TextReal.setPrefColumnCount(4);

		OP2TextImagin = new TextField();
		OP2TextImagin.setPrefColumnCount(4);

		OP3TextReal = new TextField();
		OP3TextReal.setPrefColumnCount(4);

		OP4TextImagin = new TextField();
		OP4TextImagin.setPrefColumnCount(4);
		
		RealText = new TextField();
		RealText.setPrefColumnCount(4);
		RealText.setEditable(false);
		
		ImaginarioText = new TextField();
		ImaginarioText.setPrefColumnCount(4);
		ImaginarioText.setEditable(false);

		operadorCombo = new ComboBox<String>();
		operadorCombo.getItems().addAll("+", "-", "*", "/");
		
		HBox Pareja1 = new HBox(5, OP1TextReal, OP2TextImagin, new Label("i"));
		Pareja1.setAlignment(Pos.CENTER);

		HBox Pareja2 = new HBox(5, OP3TextReal, OP4TextImagin, new Label("i"));
		Pareja2.setAlignment(Pos.CENTER);
		
		separador = new Separator();
		separador.setOrientation(Orientation.VERTICAL);
		
		HBox Pareja3 = new HBox(5, RealText, ImaginarioText, new Label("i"));
		Pareja3.setAlignment(Pos.CENTER);
		
		VBox datos = new VBox(5,Pareja1,Pareja2,separador,Pareja3);
		datos.setAlignment(Pos.CENTER);
		
		VBox operadores = new VBox(5,operadorCombo);
		operadores.setAlignment(Pos.CENTER);
		
		HBox root = new HBox(5,operadores,datos);
		
		Scene scene = new Scene(root, 320, 200);
		
		primaryStage.setTitle("Calculadora Compleja");
		primaryStage.setScene(scene);
		primaryStage.show();
		
		//bindeos
		Bindings.bindBidirectional(OP1TextReal.textProperty(), OP1Real, new NumberStringConverter());
		Bindings.bindBidirectional(OP2TextImagin.textProperty(), OP2Imagin, new NumberStringConverter());
		Bindings.bindBidirectional(OP3TextReal.textProperty(), OP3Real, new NumberStringConverter());
		Bindings.bindBidirectional(OP4TextImagin.textProperty(), OP4Imagin, new NumberStringConverter());
		Bindings.bindBidirectional(RealText.textProperty(), Real, new NumberStringConverter());
		Bindings.bindBidirectional(ImaginarioText.textProperty(), Imaginario, new NumberStringConverter());
		operador.bind(operadorCombo.getSelectionModel().selectedItemProperty());
		
		
		//listeners
		
		operador.addListener((o,ov,nv) -> onOperadorChanged(nv));
		
		operadorCombo.getSelectionModel().selectFirst();
		
	}
	
	
	private void onOperadorChanged(String nv) {
		// TODO Auto-generated method stub
		switch(nv) {
		//A = OP1Real
		//B = OP2Imagin
		//C = OP3Real
		//D = OP4Imagin
		//Suma:(a,b)+(c,d)=(a+c,b+d)
		case "+":
			Real.bind(OP1Real.add(OP3Real));
			Imaginario.bind(OP2Imagin.add(OP4Imagin));
			break;
		//Resta: (a,b)-(c,d)=(a-c,b-d)	
		case "-":
			Real.bind(OP1Real.subtract(OP3Real));
			Imaginario.bind(OP2Imagin.subtract(OP4Imagin));
			break;
		
		case "*":
		//Multiplicación: (a,b)*(c,d)=(ac-bd,ad+bc)
			Real.bind( op1.subtract(op2) );
			Imaginario.bind(op3.add(op4));
			break;
		
		case "/":
			Real.bind(Suma.divide(Suma_2));
			Imaginario.bind(Resta.divide(Suma_2));
			break;
		}
		
			
	}


	public static void main(String[] args) {
		launch(args);
	}

}
