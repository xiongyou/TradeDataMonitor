package com.tradedatamonitor.test;
//
import java.io.IOException;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//
//import com.tradedatamonitor.dao.BaseDAO;
//import com.tradedatamonitor.datacollect.GetContentByURL;
//import com.tradedatamonitor.pojo.Product;
//import com.tradedatamonitor.pojo.ProductURL;
//import com.tradedatamonitor.pojo.Store;

public class Test 
{
	public static void main(String args[]) throws IOException
	{
		/*	Product apple=new Product();
		apple.setProductName("ƻ��");
		apple.setProductURL("//item.jd.com/41545651");
		apple.setShelveDate(new Date());

		Store store=new Store();
		store.setStoreName("ˮ��֮��");
		store.setStoreURL("//mall.jd.com//4458");

		BaseDAO dao=new BaseDAO();
		dao.create(store);
		apple.setStore(store);
		dao.create(apple);*/




		/*List<String> list = new ArrayList<String>();   //�������ҳ���URL

		list=GetContentByURL.getAllPageURL(GetContentByURL.setMainURL("���"));

		List<String> list_URL = new ArrayList<String>();   //���ÿһҳ��Ʒ��URL

		list_URL=GetContentByURL.getAllProductURL(list.get(0));

		ProductURL productURL[]=new ProductURL[list_URL.size()];  //��Ų�Ʒ����

		BaseDAO dao=new BaseDAO();

		for(int i=0,j=0;i<list_URL.size()&&j<list_URL.size();i=i+2,j++)
		{ 
			productURL[j]=new ProductURL();
			productURL[j].setProductURL(list_URL.get(i));
			productURL[j].setPlatform("JingDong");
			productURL[j].setGetURLTime(new Date());
			System.out.println(list_URL.get(j)); 
			dao.create(productURL[j]);
		}*/


		//			GetContentByURL getContentByURL=new GetContentByURL();
		//			DataSourceConfig dataSourceConfig=DataSourceConfig();
		//		
		//		   int sum=0;    //ͳ�Ƽ�����ũ��Ʒ��Ŀ
		//		   
		//		   List<String> list = new ArrayList<String>();   //�������ҳ���URL
		//		
		//		   list=GetContentByURL.getAllPageURL(GetContentByURL.getSearchURL("SuNing"));
		//		   
		//		   List<String> list_URL = new ArrayList<String>();   //���ÿһҳ��Ʒ��URL
		//		   
		//		   for(int i=0;i<list.size();i++)
		//		   { 
		//			   list_URL=getContentByURL.getAllProductURL(list.get(i));
		//			   for(int j=0;j<list_URL.size();j++)
		//			   { 
		//				   System.out.println(list_URL.get(j)); 
		//				   sum++;
		//			   }
		//		   }
		//			   
		//		   System.out.println(GetContentByURL.setMainURL("���")+"&page="+list.get(2)+"  ȫ���������ݽ���!");
		//		   System.out.println("���в�Ʒ��ĿΪ��"+sum/2);

		//List<String> list=new ArrayList<String>();    

		//list=GetContentByURL.getAllProductURL(GetContentByURL.setMainURL("���"));

		//		for(int i=0;i<list.size();i++)
		//		{
		//			System.out.println(list.get(i));
		//		}



	}
}
