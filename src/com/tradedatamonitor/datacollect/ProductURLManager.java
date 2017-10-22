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

	/**?§Ø??????????????productURL
	 * @param productURL
	 * @return true or false
	 */
	public boolean isExist(ProductURL productURL)
	{		
		if(dao.find(ProductURL.class,productURL.getProductURL())==null)   //????????§Ó?????¨°??
		{
			return false;
		}
		else    //????????§Õ???¨°??
		{
			return true;
		}
	}
	

	/**?????????????
	 * @param productURL ???????
	 */
	public void insertProductURL(ProductURL productURL)
	{
		if(isExist(productURL))   //????????§Õ???¨°??
		{
			//????????
			updateProductURL(productURL);

		}
		else
		{
			productURL.setKeyWord(";"+productURL.getKeyWord()+";");
			dao.create(productURL);
		}

	}

	/**???????????????????????????????????????????????
	 * @param productURL
	 */
	public void updateProductURL(ProductURL productURL)
	{
		ProductURL oldProductURL=dao.find(ProductURL.class,productURL.getProductURL());
		if(oldProductURL.getKeyWord().contains(";"+productURL.getKeyWord()+";"))   //???¨´????????
		{
			return;
		}
		else    //??????¨´??????keyWord???
		{

			oldProductURL.setKeyWord(oldProductURL.getKeyWord()+productURL.getKeyWord()+";");
			dao.update(oldProductURL);
		}
	}
	
	/**???????????????????????§Õ?????
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
	 * ?????????url????????????????????§Õ?????
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
