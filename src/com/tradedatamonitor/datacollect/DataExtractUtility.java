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
	 * ��ݹؼ��ֹ����ѯURL
	 * @param keyword
	 * @return
	 * @throws Exception
	 */
	public String getProductSearchURL(String keyword) throws Exception  
	{
		return readXML.getSearchURL(this.website, keyword);

	}
	
	/**�ؼ����������ж�
	 * @param urlString ����ҳ����ַ
	 * @return ��Ʒ�����������ҳ��URL�б�
	 * @throws Exception 
	 */
	public boolean notFoundDetermine(String searchPageContent) throws Exception
	{
		String regex=null;
		
		regex=readXML.getNotFoundRegx(this.website);  //��ȡû���������ı�ǵ�������ʽ
		
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
	 * ��ȡ��������ҳ��
	 * @param searchPageContent
	 * @return
	 * @throws Exception
	 */
	public int extractTotalPageNo(String searchPageContent) throws Exception
	{
		String regex=null;
		String page ="1";


		regex=readXML.getExtractTotalPageRegx(this.website);  //��ȡ��ҳ��������ʽ

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
	 * ��������ҳ��URL
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
			list.add(pageURL);     //��ҳ��URL����
		}

		return list;
	}

	/**
	 * ����������ƷURL
	 * @param pageContent
	 * @return
	 * @throws Exception
	 */
	public List<String> extractProductURL(String pageContent) throws Exception
	{
		String regex=null;
		final List<String> list = new ArrayList<String>();

		regex =readXML.getExtractProductRegx(this.website);   //��ȡ������ƷURL������ʽ
		prefix=readXML.getPrefix(this.website);    //��ȡ��Ʒurl��ǰ׺
		
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
