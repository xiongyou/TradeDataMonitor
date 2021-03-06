package com.tradedatamonitor.datacollect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/** @pdOid 根据URL获取产品信息*/
public class GetContentByURL 
{
	DataExtractUtility deUtility=null; 
	
	public GetContentByURL(String websiteName)
	{
		this.deUtility=new DataExtractUtility(websiteName);
	}
	
	/**获取网页内容by URL   
	 * @param urlString 网址  
	 * @return 网页内容  

	 * @throws IOException
	 */
	public String getOneHtml(final String urlString,String charset) throws IOException
	{
		URL url;
		String temp;
		final StringBuffer sb = new StringBuffer();
		try
		{
			url = new URL(urlString);
			HttpURLConnection urlConnection = (HttpURLConnection) url.openConnection();
			urlConnection.setRequestProperty("User-Agent", "Mozilla/5.0 (Windows NT 10.0; WOW64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/49.0.2623.221 Safari/537.36 SE 2.X MetaSr 1.0");//"Mozilla/31.0 (compatible; MSIE 10.0; Windows NT; DigExt)");
			urlConnection.setDoOutput(true);
			urlConnection.setConnectTimeout(10000);
			urlConnection.connect();
			final BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(),charset));// 读取网页全部内容
			
			while ((temp = in.readLine()) != null)
			{
				sb.append(temp);
			}
			in.close();
		}
		catch (final MalformedURLException me)
		{
			System.out.println("你输入的URL格式有问题！请仔细输入");
			//System.out.println("打开URL失败");
			me.getMessage();
			throw me;
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			throw e;
		}
		//System.out.println(sb.toString()); //打印网页内容
		return sb.toString();
	}

	
	/**获取所有产品搜索页面的URL
	 * @param urlString 搜索页面网址ַ
	 * @return 产品所有搜索结果页面URL列表
	 * @throws Exception 
	 */
	public List<String> getSearchPagesURL(String urlString,String keyword,String charset) throws Exception
	{
		List<String> list=new ArrayList<String>();
		String html = "";                                             
		try
		{                                            
			html =getOneHtml(urlString,charset);
		}
		catch (final Exception e)
		{
			e.getMessage();
		}
		
		if(this.deUtility.notFoundDetermine(html))
		{
			int page_max=this.deUtility.extractTotalPageNo(html);  //获取页数
			
			list=this.deUtility.getAllSearchResPages(page_max,keyword);   //存储页面URL	
		}
		return list;
	}


	/**根据页面的URL获取所有产品的URL
	 * @param urlString 分页网址ַ
	 * @return 产品URL列表
	 * @throws Exception 
	 */
	public List<String> getAllProductURL(String urlString,String charset) throws Exception  
	{
		String html = "";
		try
		{
			html =getOneHtml(urlString,charset);
		}
		catch (final Exception e)
		{
			e.getMessage();
		}
		System.out.println("------------------------换页了！------------------------------");

		List<String> list=this.deUtility.extractProductURL(html);   //存储页面URL
		
		return list;

	}

}