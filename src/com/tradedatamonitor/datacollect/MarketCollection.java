
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
	DataSourceConfig_read readXML=new DataSourceConfig_read("src\\dataConfig.xml");

	/**
	 * ���캯�������ƽ̨��
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
	 * ɨ�貢�洢ָ����վ��ָ���ؼ�ʵ���Ʒ������Ϣ
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
	 * ɨ�貢�洢ָ����վ��ĳ���ؼ�ʵ���Ʒ������Ϣ
	 * @param keyword
	 * @throws Exception 
	 */
	public void scanAllProductByKeyword(String keyword) throws Exception 
	{
		int sum=0;
		maximum=Integer.parseInt(readXML.getMaximum());
		
		String queryString=readXML.getSearchURL(this.website,keyword);  //�洢��ѯURL
		String charset=readXML.getCharset(this.website);

		GetContentByURL getProductContent=new GetContentByURL(this.website);

		List<String> pageList = new ArrayList<String>();   //�������ҳ���URL	
		
		pageList=getProductContent.getSearchPagesURL(queryString,keyword,charset);

		//���ҳ��ѭ������ȡ����ҳ�е�productURL
		if(pageList.size()!=0)
		{
			for(int page=0;page<pageList.size()&&sum<=maximum;page++)
			{
				List<String> onePageProductURL=new ArrayList<String>();   //���һ��ҳ�������в�Ʒ��URL
				
				//getOnePageAllURL  ��ȡһ������ҳ�����в�ƷURL,�������ƷURL
				onePageProductURL=getProductContent.getAllProductURL(pageList.get(page),charset);
				
				for(String url:onePageProductURL)
				{
					//productURLManage.insertProductURL(this.setProductURL(url,keyword));    //插入到数据库����Ϣд����ݿ�
					sum++;
					
					productURLManage.saveProductURLtoTxt(url, this.website, keyword);   //保存到url文件����Ʒurl����Ӧ��ƽ̨����Ӧ�Ĺؼ���д���ļ�
					System.out.println(url+"\t"+this.website+"\t"+keyword);    //����Ʒurl��ӡ����
				}	
				productURLManage.saveProductURLCounttoTxt(this.website+":"+keyword+":共采集到"+sum+"商品");  //���ؼ�������ͳ��д���ı��ļ�
				System.out.println(this.website+":"+keyword+":共采集到"+sum+"商品");
			}
		}
		else
		{
			productURLManage.saveProductURLCounttoTxt(this.website+":"+keyword+":共采集到"+sum+"商品");  //���ؼ�������ͳ��д���ı��ļ�
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
