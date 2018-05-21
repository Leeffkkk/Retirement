package pkgApp.controller;

import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.ResourceBundle;

import org.apache.poi.ss.formula.functions.FinanceLib;

import com.sun.prism.paint.Color;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.TextField;
import javafx.scene.text.FontWeight;
import javafx.scene.control.TextFormatter;
import javafx.stage.Stage;
import javafx.util.converter.NumberStringConverter;

import javafx.beans.value.*;

import pkgApp.RetirementApp;
import pkgCore.Retirement;

public class RetirementController implements Initializable {

	private RetirementApp mainApp = null;
	private Retirement retirement = new Retirement();
	@FXML
	private TextField txtSaveEachMonth;
	@FXML
	private TextField txtYearsToWork;
	@FXML
	private TextField txtAnnualReturnWorking;
	@FXML
	private TextField txtWhatYouNeedToSave;
	@FXML
	private TextField txtYearsRetired;
	@FXML
	private TextField txtAnnualReturnRetired;
	@FXML
	private TextField txtRequiredIncome;
	@FXML
	private TextField txtMonthlySSI;

	private HashMap<TextField, String> hmTextFieldRegEx = new HashMap<TextField, String>();

	public RetirementApp getMainApp() {
		return mainApp;
	}

	public void setMainApp(RetirementApp mainApp) {
		this.mainApp = mainApp;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {

		hmTextFieldRegEx.put(txtYearsToWork, "\\d*?");
		hmTextFieldRegEx.put(txtAnnualReturnWorking, "\\d*(\\.\\d*)?");
		hmTextFieldRegEx.put(txtAnnualReturnRetired, "\\d*(\\.\\d*)?");
		hmTextFieldRegEx.put(txtYearsRetired, "\\d*?");
		hmTextFieldRegEx.put(txtRequiredIncome, "\\d*?");
		hmTextFieldRegEx.put(txtMonthlySSI, "\\d*?");

		Iterator it = hmTextFieldRegEx.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TextField txtField = (TextField) pair.getKey();
			String strRegEx = (String) pair.getValue();

			txtField.focusedProperty().addListener(new ChangeListener<Boolean>() {
				@Override
				public void changed(ObservableValue<? extends Boolean> arg0, Boolean oldPropertyValue,
						Boolean newPropertyValue) {

					if (!newPropertyValue) {
						if (txtField.getText()!=null && txtField.getText()!="" && (!(txtField.getText().matches(strRegEx)&&inputCheck(txtField)))) {
							txtField.setText("");
							txtField.requestFocus();
						}
					}
				}
			});
		}

	}

	@FXML
	public void btnClear(ActionEvent event) {
		
		Iterator it = hmTextFieldRegEx.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			TextField txtField = (TextField) pair.getKey();
			txtField.clear();
			txtYearsToWork.setDisable(false);
		}
		
				txtSaveEachMonth.setDisable(true);
				txtWhatYouNeedToSave.setDisable(true);
	}

	@FXML
	public void btnCalculate() {

		txtSaveEachMonth.setDisable(false);
		txtWhatYouNeedToSave.setDisable(false);
		
		
		retirement.setiYearsToWork(Integer.valueOf(txtYearsToWork.getText()));
		retirement.setdAnnualReturnWorking(Double.valueOf(txtAnnualReturnWorking.getText()));
		retirement.setiYearsRetired(Integer.valueOf(txtYearsRetired.getText()));
		retirement.setdAnnualReturnRetired(Double.valueOf(txtAnnualReturnRetired.getText()));
		retirement.setdRequiredIncome(Integer.valueOf(txtRequiredIncome.getText()));
		retirement.setdMonthlySSI(Integer.valueOf(txtMonthlySSI.getText()));
		
		
		txtWhatYouNeedToSave.setText(String.valueOf(Math.abs(this.retirement.TotalAmountToSave())));
		txtSaveEachMonth.setText(String.valueOf(this.retirement.MonthlySavings()));
		
	}
	
	private boolean inputCheck(TextField textfield) {
		try {
			switch (textfield.getId())
			{
			case "txtYearsToWork": 
				return Integer.valueOf(textfield.getText()) >= 0 && Integer.valueOf(textfield.getText()) <= 40;
			case "txtAnnualReturnWorking":
			case "txtAnnualReturnRetired":
				return Double.valueOf(textfield.getText()) >= 0 && Double.valueOf(textfield.getText()) <= 10;
			case "txtYearsRetired":
				return Integer.valueOf(textfield.getText()) >= 0 && Integer.valueOf(textfield.getText()) <= 20;
			case "txtRequiredIncome":
				return Integer.valueOf(textfield.getText()) >= 2642 && Integer.valueOf(textfield.getText()) <= 10000;
			case "txtMonthlySSI":
				return Integer.valueOf(textfield.getText()) >= 0 && Integer.valueOf(textfield.getText()) <= 2642;
			}
		} catch (Exception e) {return true;}
		return true;
	}
}