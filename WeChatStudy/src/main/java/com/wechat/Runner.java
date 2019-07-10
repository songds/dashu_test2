package com.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class Runner {

	public static void main(String[] args) {
		SpringApplication.run(Runner.class, args);
	}
	
	public static void sum(){
		//基本工资
		double basic_money=15500;
		//税基数
		double base_imponible=5000;
		//五险+金
		double insurance_moeny=381.3;
		//交税月份
		int month=1;
		for (int i = month; i <= 12; i++) {
			System.out.println("\t"+income_tax_sum(basic_money, base_imponible, i, insurance_moeny));
		}
	}
	
	/**
	 * 累计峪口预缴应纳税所得额
	 * @param basic_money  基本工资
	 * @param base_imponible 税基数
	 * @param month 交税月份
	 * @param insurance_moeny 五险+金
	 * @return
	 */
	public static double accumulation_money_sum(double basic_money,double base_imponible,int month,double insurance_moeny){
		double sum=0;
		sum=basic_money*month-base_imponible*month-insurance_moeny*month;
		if(month>0){
			month--;
			accumulation_money_sum(basic_money, base_imponible, month, insurance_moeny);
		}
		return sum;
	}
	
	/**
	 * @param basic_money
	 * @param base_imponible
	 * @param month
	 * @param insurance_moeny
	 * @return
	 */
	public static double income_tax_sum(double basic_money,double base_imponible,int month,double insurance_moeny){
		double income_tax=0;
		double sum_moeny=accumulation_money_sum(basic_money, base_imponible, month, insurance_moeny);
		System.out.print(sum_moeny);
		if(sum_moeny<=36000){
			//速算扣除数
			double quick_deductio=0;
			//预扣率
			double discount_in_advance=0.03;
			income_tax=sum_moeny*discount_in_advance-quick_deductio;
		}else if(sum_moeny>36000&&sum_moeny<=144000){
			//速算扣除数
			double quick_deductio=2520;
			//预扣率
			double discount_in_advance=0.10;
			income_tax=sum_moeny*discount_in_advance-quick_deductio;
		}else if(sum_moeny>144000&&sum_moeny<=300000){
			//速算扣除数
			double quick_deductio=16920;
			//预扣率
			double discount_in_advance=0.20;
			/*
			defult_month--;
			//累计月份个人缴纳税
			double preceding_ncome_tax=0;
			if(defult_month>0){
				preceding_ncome_tax=income_tax_sum(basic_money, base_imponible, defult_month, insurance_moeny);
			}*/
			income_tax=sum_moeny*discount_in_advance-quick_deductio;
		}else if(sum_moeny>300000&&sum_moeny<=420000){
			//速算扣除数
			double quick_deductio=31920;
			//预扣率
			double discount_in_advance=0.25;
			income_tax=sum_moeny*discount_in_advance-quick_deductio;
		}else if(sum_moeny>420000&&sum_moeny<=660000){
			//速算扣除数
			double quick_deductio=52920;
			//预扣率
			double discount_in_advance=0.30;
			income_tax=sum_moeny*discount_in_advance-quick_deductio;
		}else if(sum_moeny>660000&&sum_moeny<=960000){
			//速算扣除数
			double quick_deductio=85920;
			//预扣率
			double discount_in_advance=0.35;
			income_tax=sum_moeny*discount_in_advance-quick_deductio;
		}else if(sum_moeny>960000){
			//速算扣除数
			double quick_deductio=181920;
			//预扣率
			double discount_in_advance=0.45;
			income_tax=sum_moeny*discount_in_advance-quick_deductio;
		}
		return income_tax;
	}
	
}
