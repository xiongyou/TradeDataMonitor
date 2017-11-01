package com.tradedatamonitor.datacollect;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.util.Date;

import com.tradedatamonitor.dao.BaseDAO;
import com.tradedatamonitor.pojo.ProductURL;

public class ProductURLManager 
{
	BaseDAO dao=new BaseDAO();

	/**判断数据库中是否存在该productURL
	 * @param productURL
	 * @return true or false
	 */
	public boolean isExist(ProductURL productURL)
	{		
		if(dao.find(ProductURL.class,productURL.getProductURL())==null)   //如果数据库中不存在该产品
		{
			return false;
		}
		else    //如果数据库中存在该产品
		{
			return true;
		}
	}
	

	/**插入产品链接信息
	 * @param productURL 产品对象
	 */
	public void insertProductURL(ProductURL productURL)
	{
		if(isExist(productURL))   //如果数据库中存在该产品
		{
			//更新关键字
			updateProductURL(productURL);

		}
		else
		{
			productURL.setKeyWord(";"+productURL.getKeyWord()+";");
			dao.create(productURL);
		}

	}

	/**更新商品链接的关键字，解决多个关键字查找到到同一个商品的问题
	 * @param productURL
	 */
	public void updateProductURL(ProductURL productURL)
	{
		ProductURL oldProductURL=dao.find(ProductURL.class,productURL.getProductURL());
		if(oldProductURL.getKeyWord().contains(";"+productURL.getKeyWord()+";"))   //如果该关键词已存在
		{
			return;
		}
		else    //如果不存在该关键词更新keyWord字段
		{

			oldProductURL.setKeyWord(oldProductURL.getKeyWord()+productURL.getKeyWord()+";");
			dao.update(oldProductURL);
		}
	}
	
	/**根据不同平台不同关键字将商品总数写入文件
	 * @param productURL
	 */
	public void saveProductURLCounttoTxt(String productURLCount) throws IOException
	{
		File file = new File("output\\productURLCount.txt");
		FileOutputStream fos = new FileOutputStream(file,true);
        OutputStreamWriter osw = new OutputStreamWriter(fos,"UTF-8");
        BufferedWriter bw = new BufferedWriter(osw);
		bw.write(productURLCount+"\r\n");
		bw.flush();
	}
	
	/**
	 * 将采集的产品url、对应的平台、对应的关键字写入文件
	 * @throws IOException 
	 */
	public void saveProductURLtoTxt(String productURL,String platform,String keyword) throws IOException
	{
		File file=new File("output\\productURL.txt");
		FileOutputStream fos=new FileOutputStream(file,true);
		OutputStreamWriter osw =new OutputStreamWriter(fos,"UTF-8");
		BufferedWriter bw=new BufferedWriter(osw);
		bw.write(productURL+"\t"+platform+"\t"+keyword+"\r\n");
		bw.flush();
	}
}
