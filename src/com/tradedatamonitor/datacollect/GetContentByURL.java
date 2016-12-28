package com.tradedatamonitor.datacollect;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


/** @pdOid ����URL��ȡ��Ʒ��Ϣ*/
public class GetContentByURL 
{
	DataExtractUtility deUtility=null; 
	
	public GetContentByURL(String websiteName)
	{
		this.deUtility=new DataExtractUtility(websiteName);
	}
	
	/**��ȡ��ҳ����by URL 
	 * @param urlString ��ַ
	 * @return ��ҳ����
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
			urlConnection.setRequestProperty("User-Agent", "Mozilla/31.0 (compatible; MSIE 10.0; Windows NT; DigExt)");
			
			final BufferedReader in = new BufferedReader(new InputStreamReader(urlConnection.getInputStream(), charset));// ��ȡ��ҳȫ������
			while ((temp = in.readLine()) != null)
			{
				sb.append(temp);
			}
			in.close();
		}
		catch (final MalformedURLException me)
		{
			System.out.println("�������URL��ʽ�����⣡����ϸ����");
			me.getMessage();
			throw me;
		}
		catch (final IOException e)
		{
			e.printStackTrace();
			throw e;
		}
		//System.out.println(sb.toString()); //��ӡ��ҳ����
		return sb.toString();
	}

	
	/**��ȡ���в�Ʒ����ҳ���URL
	 * @param urlString ����ҳ����ַ
	 * @return ��Ʒ�����������ҳ��URL�б�
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
			int page_max=this.deUtility.extractTotalPageNo(html);  //��ȡҳ��
			
			list=this.deUtility.getAllSearchResPages(page_max,keyword);   //�洢ҳ��URL	
		}
		return list;
	}


	/**����ҳ���URL��ȡ���в�Ʒ��URL
	 * @param urlString ��ҳ��ַ
	 * @return ��ƷURL�б�
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
		System.out.println("------------------------��ҳ�ˣ�------------------------------");

		List<String> list=this.deUtility.extractProductURL(html);   //�洢ҳ��URL
		
		return list;

	}

}