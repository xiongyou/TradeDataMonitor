
package com.tradedatamonitor.datacollect;

import java.net.URLEncoder;
import java.util.*;

import com.tradedatamonitor.ProductConfig.DataSourceConfig_read;
import com.tradedatamonitor.pojo.ProductURL;


public class MarketCollection 
{
	private String website;
	private int keywordIndex=0;
	private int maximum=0;
	
	ProductURLManager productURLManage=new ProductURLManager();
	DataSourceConfig_read readXML=new DataSourceConfig_read("dataConfig.xml");

	/**
	 *  构造函数（传入电商平台名）
	 * @param websiteName
	 */
	public MarketCollection(String websiteName)
	{
		website=websiteName;
	}
	
	public MarketCollection(String websiteName,int keywordIndex)
	{
		website=websiteName;
		this.keywordIndex=keywordIndex;
		
	}

	/**
	 * 扫描并存储指定网站中指定关键词的商品链接信息
	 * @throws Exception 
	 */
	public void scanAllProduct() throws Exception  
	{
		String keywords[]=readXML.getAllProductsKeyword();
		String keyword_gbk[]=new String[keywords.length];
		for (int i=this.keywordIndex;i<keywords.length;i++) 
		{		
			scanAllProductByKeyword(keywords[i]);   
		}	
	}	   

	/** @pdOid e11e80bb-d5d0-450d-a996-86682c711781 */
	/**
	 * 扫描并存储指定网站中某个关键词的商品链接信息
	 * @param keyword
	 * @throws Exception 
	 */
	public void scanAllProductByKeyword(String keyword) throws Exception 
	{
		int sum=0;
		maximum=Integer.parseInt(readXML.getMaximum());
		
		String queryString=readXML.getSearchURL(this.website,keyword);  //存储查询URL
		String charset=readXML.getCharset(this.website);

		GetContentByURL getProductContent=new GetContentByURL(this.website);

		List<String> pageList = new ArrayList<String>();   //存放所有页面的URL	
		
		pageList=getProductContent.getSearchPagesURL(queryString,keyword,charset);

		//根据页数循环，获取所有页中的productURL
		if(pageList.size()!=0)
		{
			for(int page=0;page<pageList.size()&&sum<=maximum;page++)
			{
				List<String> onePageProductURL=new ArrayList<String>();   //存放一个页面里所有产品的URL
				
				//getOnePageAllURL  获取一个搜索页的所有产品URL,并保存产品URL
				onePageProductURL=getProductContent.getAllProductURL(pageList.get(page),charset);
				
				for(String url:onePageProductURL)
				{
					//productURLManage.insertProductURL(this.setProductURL(url,keyword));    //将信息写入数据库
					sum++;
					
					productURLManage.saveProductURLtoTxt(url, this.website, keyword);   //将产品url、对应的平台、对应的关键字写入文件
					System.out.println(url+"\t"+this.website+"\t"+keyword);    //将产品url打印出来
				}	
				productURLManage.saveProductURLCounttoTxt(this.website+":"+keyword+":共采集到"+sum+"商品");  //将关键字销售统计写入文本文件
				System.out.println(this.website+":"+keyword+":共采集到"+sum+"商品");
			}
		}
		else
		{
			productURLManage.saveProductURLCounttoTxt(this.website+":"+keyword+":共采集到"+sum+"商品");  //将关键字销售统计写入文本文件
			System.out.println(this.website+":"+keyword+":共采集到"+sum+"商品");	
		}
	}
	
	public ProductURL setProductURL(String url,String keyword)
	{
		ProductURL productURL=new ProductURL();

		productURL.setProductURL(url);
		productURL.setGetURLTime(new Date());
		productURL.setPlatform(this.website);
		productURL.setStatus((byte) 0);
		productURL.setKeyWord(keyword);
		return productURL;
	}
}
