package com.example.drink_order_system;
import java.util.ArrayList;
//  Order的Java类，
//  它包含四个私有成员变量：order_number（订单号）、time（时间）、takeAway（是否外带）和cost（费用）。
public class Order {
	private String order_number;
	private String time;
	private String takeAway;
	private String cost;
	Order(String info)
	{
		String[] info_list = info.split(",");
		order_number = info_list[0];
		time = info_list[1];
		takeAway = info_list[2];
		cost = info_list[3];
	}

	public String getOrder_number() {
		return order_number;
	}

	public String getTime() {
		return time;
	}

	public String getTakeAway() {
		return takeAway;
	}

	public String getCost() {
		return cost;
	}
}
