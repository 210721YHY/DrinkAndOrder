package com.example.drink_order_system;

import android.support.annotation.Nullable;
// 选择喜好种类，小中大杯，甜度、类型
public class Flavor {
	private String size;
	private String sugar;
	private String temperature;
	Flavor(String size, String sugar, String temperature)
	{
		this.size = size;
		this.sugar = sugar;
		this.temperature = temperature;
	}

	@Override
	public String toString()
	{
		String s = size+"，"+sugar+"，"+temperature;
		return s;
	}

	@Override
	public boolean equals(@Nullable Object obj) {
		return this.toString().equals(((Flavor)obj).toString());
	}

	public String getSize() {
		return size;
	}
}
