package bank.test.david;

import com.fasterxml.jackson.annotation.JsonInclude;

public class CalcResult {

	private int addition;
	private int substraction;
	private int multipilcation;
	private double division;
	
	@JsonInclude(JsonInclude.Include.NON_NULL)
	private String errorMsg;
	
	public CalcResult() {
	}
	public CalcResult calculate(int first, int second) {
		this.addition = first + second;
		this.substraction = first - second;
		this.multipilcation = first * second;
		if (second != 0) {
			this.division = (double)first/second;
		}
		else{
			this.errorMsg = "can't devide by zero";
		}
		return this;
	}
	public int getAddition() {
		return addition;
	}
	public int getMultipilcation() {
		return multipilcation;
	}
	public double getDivision() {
		return division;
	}
	public int getSubstraction() {
		return substraction;
	}
	public String getErrorMsg() {
		return errorMsg;
	}
}