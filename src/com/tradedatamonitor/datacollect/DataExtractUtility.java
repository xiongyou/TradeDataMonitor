package com.tradedatamonitor.datacollect;

import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.tradedatamonitor.ProductConfig.DataSourceConfig_read;

public class DataExtractUtility 
{
	String website;
	private int startPage;
	private int pageTimes;
	private String prefix;
	
	DataSourceConfig_read readXML=new DataSourceConfig_read("dataConfig.xml");
	
	public DataExtractUtility(String websiteName)   
	{
		this.website=websiteName;
	}

	/**
	 * 根据关键字构造查询URL
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public String getProductSearchURL(String keyword) throws Exception  
	{
		return readXML.getSearchURL(this.website, keyword);

	}
	
	/**关键词搜索结果判定
	 * @param urlString 搜索页面网址
	 * @return 产品所有搜索结果页面URL列表
	 * @throws Exception 
	 */
	public boolean notFoundDetermine(String searchPageContent) throws Exception
	{
		String regex=null;
		
		regex=readXML.getNotFoundRegx(this.website);  //获取没有搜索结果的标记的正则表达式
		
		Pattern pa=Pattern.compile(regex,Pattern.DOTALL);
		Matcher ma=pa.matcher(searchPageContent);
		
		if(regex.equals("null"))
			return true;
		else if(ma.find())
		{
			return false;
		}
		else
			return true;
	}
	
	/**
	 * 获取搜索的总页数
	 * @param searchPageContent
	 * @return
	 * @throws Exception
	 */
	public int extractTotalPageNo(String searchPageContent) throws Exception
	{
		String regex=null;
		String page ="1";


		regex=readXML.getExtractTotalPageRegx(this.website);  //获取分页数正则表达式

		Pattern pa=Pattern.compile(regex,Pattern.DOTALL);
		Matcher ma=pa.matcher(searchPageContent);

		if(ma.find())
		{
			page=ma.group(readXML.totalPageGroupNo(this.website));
		}
	
		try{
			Integer page_int_max=Integer.valueOf(page);
			return page_int_max.intValue();
		}
		catch(Exception e){
			return 0;
		}
		
		
		
	}

	/**
	 * 构造搜索页面URL
	 * @param totalPageNo
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public List<String> getAllSearchResPages(int totalPageNo,String keyword) throws Exception
	{
		startPage=Integer.parseInt(readXML.getStartPage(this.website));
		pageTimes=Integer.parseInt(readXML.getPageTimes(this.website));
		
		List<String> list=new ArrayList<String>();
		Integer []page_int=new Integer[totalPageNo];
		
		if(startPage==0)
		{
			for(int i=0;i<=totalPageNo-1;i++)
			{
				page_int[i]=i*pageTimes;
			}
		}
		else
		{
			for(int i=1;i<=totalPageNo;i++)
			{
				page_int[i-1]=i*pageTimes;
			}
		}


		for(int j=0;j<totalPageNo;j++)
		{
			String pageURL=readXML.getExtractPageURL(this.website, keyword,page_int[j].toString());
			list.add(pageURL);     //各页面URL构造
		}

		return list;
	}

	/**
	 * 构造搜索产品URL
	 * @param pageContent
	 * @return
	 * @throws Exception
	 */
	public List<String> extractProductURL(String pageContent) throws Exception
	{
		String regex=null;
		final List<String> list = new ArrayList<String>();

		regex =readXML.getExtractProductRegx(this.website);   //获取各个产品URL正则表达式
		prefix=readXML.getPrefix(this.website);    //获取产品url的前缀
		
		Pattern pa = Pattern.compile(regex, Pattern.DOTALL);
		Matcher ma = pa.matcher(pageContent);
		while (ma.find())
		{
			String a=ma.group();
			if(prefix.equals("null"))
				list.add(a);
			else
				list.add(prefix+a);									 
		}
		return list;
	}
}