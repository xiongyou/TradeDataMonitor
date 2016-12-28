package com.tradedatamonitor.test;

import com.tradedatamonitor.datacollect.MarketCollection;

public class TestMain 
{
	public static void main(String args[])throws Exception
	{
		MarketCollection marketCollection=new MarketCollection(args[0]/*,Integer.parseInt(args[1])*/);
		marketCollection.scanAllProduct();
	}
}
