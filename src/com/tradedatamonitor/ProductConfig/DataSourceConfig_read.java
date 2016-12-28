package com.tradedatamonitor.ProductConfig;

import java.io.File;
import java.io.IOException;   
import java.io.Writer;   
import java.net.URLEncoder;
import java.util.Iterator;   
import java.util.List;

import org.dom4j.Attribute;
import org.dom4j.Document;   
import org.dom4j.DocumentException;   
import org.dom4j.DocumentHelper;   
import org.dom4j.Element;   
import org.dom4j.io.SAXReader;   
import org.dom4j.io.XMLWriter; 

/***********************************************************************
 * Module:  ProductConfig.java
 * Author:  Administrator
 * Purpose: Defines the Class ProductConfig
 ***********************************************************************/

/** �������ݣ��ؼ��֣���վ��������ʽ��
 * 
 * @pdOid 2c5f83a3-55c0-4253-bd11-4e0d3caebf21 */
public class DataSourceConfig_read {
	
	private String configFileName;
	public DataSourceConfig_read(String fileName){
		this.configFileName=fileName;
	}
	
	/**
	 * ����XML�ļ�
	 *  @param fileName
	 * @throws DocumentException 
	 * @pdOid 226ee484-6c27-4695-8906-d699d66fc5fd */
	public Document loadConfigFile() throws Exception {
		// TODO: implement
		// ����saxreader����  
        SAXReader reader = new SAXReader();  
        // ��ȡһ���ļ���������ļ�ת����Document����  
        Document document = reader.read(new File(configFileName));
        return document;
     
	}
	/**
	 * ���ĵ�ת���ַ��� 
	 * @param document
	 * @return
	 */
	public String docXmlToString(Document document){
		String docXmlText = document.asXML();  
		return docXmlText;
	}
	/**
	 *  ��ȡ��Ԫ��
	 * @param fileName
	 * @return
	 * @throws DocumentException
	 */
	public Element   getRootEle() throws Exception{
		
		// ��ȡ��Ԫ��  
        Element root = loadConfigFile().getRootElement();        
        return root;
	}
	
	/**��Ԫ��ת��ΪString
	 * @param e
	 * @return
	 */
	public String elementToString(Element e){
		String elemmentXmlText = e.asXML();  
		return elemmentXmlText;
	}
	
	/**
	 * ��ȡĳ������µľ�������
	 * @param node
	 */
	public String getNodeContent(Element node){
		return node.getTextTrim();
	            
	}
	
	/**
	 * ������վwebsite���
	 * @param website
	 * @return
	 * @throws DocumentException
	 */
	public Element intoWebsiteNode(String website) throws Exception{
		Element rootEle=getRootEle();
		Element nextEle=getNextElement(rootEle, "website");
		Element websiteEle=getNextElement(nextEle, website);
		return websiteEle;
	}
	
	/**
	 * ��ȡ��վ�ı���
	 * @param website
	 * @return
	 * @throws Exception
	 */
	public String getCharset(String website) throws Exception 
	{
		Element charset=getNextElement(intoWebsiteNode(website), "charset");
		return getNodeContent(charset);		
	}
	
	/**
	 * ��ȡ��վ����������URL
	 * @param website ��վ
	 * @return ִ������ʱ��URL
	 * @throws Exception 
	 */
	public String getSearchURL(String website,String keyword) throws Exception   //��ͬƽ̨���ݹؼ���������URL����
	{
		Element searchURL=getNextElement(intoWebsiteNode(website), "searchURL");
		
		if(website.equals("JingDong"))
			return getNodeContent(searchURL).replaceAll("\\[keyword\\]",keyword);
		else
			return getNodeContent(searchURL).replaceAll("\\[keyword\\]", URLEncoder.encode(keyword,getCharset(website)));
	}
	
	/**
	 * ��ȡĳ��վ��ҳ����������ʽ
	 * @param website
	 * @return
	 * @throws Exception
	 */
	public String getExtractTotalPageRegx(String website) throws Exception    //��ȡ��ҳ����������ʽ
	{	
		Element totalPageEle=getNextElement(intoWebsiteNode(website), "totalPage");
		return getNodeContent(totalPageEle);
	}
	
	/**
	 * ȡ��ĳ�ڵ��µ�ĳ����,������ת���ı�
	 * @param ele
	 * @param attr
	 * @return
	 * @throws Exception
	 */
	public String targetGroup(Element ele,String attr) throws Exception{
		Attribute attribute=ele.attribute(attr);  
		
		return attribute.getText(); 
	}
	
	/**
	 * ��ȡĳ��վ��ҳ�ķ���
	 * @param website
	 * @return
	 * @throws Exception
	 */
	public int totalPageGroupNo(String website) throws Exception    //��ȡgroupҳ��λ��
	{
		
		Element totalPageEle=getNextElement(intoWebsiteNode(website), "totalPage");
		
		return Integer.parseInt(targetGroup(totalPageEle,"targetgroup"));	
	}
	
	
	
	/**
	 * ��ȡ��վ����������һҳURL���ַ���
	 * @param website
	 * @return
	 * @throws Exception
	 */
	public String getExtractPageURL(String website,String keyword,String pageNum) throws Exception    //��ȡ��ͬҳ��URL��������ʽ
	{
		Element nextPageURL=getNextElement(intoWebsiteNode(website), "nextPageURL");
		
		if(website.equals("JingDong"))
			return getNodeContent(nextPageURL).replaceAll("\\[keyword\\]",keyword).replaceAll("\\[PageNum\\]", pageNum);
		else
			return getNodeContent(nextPageURL).replaceAll("\\[keyword\\]", URLEncoder.encode(keyword,getCharset(website))).replaceAll("\\[PageNum\\]", pageNum);
	}
	
	
	
	/**
	 * ��ȡĳ��վ�������ҳ���в�Ʒ��������ʽ
	 * @param website
	 * @return
	 * @throws Exception
	 */
	public String getExtractProductRegx(String website) throws Exception    //��ȡҳ�������в�ƷURL������ʽ
	{
		
		Element productURL=getNextElement(intoWebsiteNode(website), "productURL");
		return getNodeContent(productURL);
	}
	
	/**
	 * ��ȡû���������ؼ��ʵ��ж�����������ʽ
	 * @param website
	 * @return
	 */
	public String getNotFoundRegx(String website) throws Exception    //��ȡû���������ؼ��ʵ��ж�����������ʽ
	{
		Element notFound=getNextElement(intoWebsiteNode(website),"notFound");
		return getNodeContent(notFound);
	}
	
	/**
	 * ��ȡpage�Ŀ�ʼҳ
	 * @throws Exception 
	 */
	public String getStartPage(String website) throws Exception
	{
		Element startPage=getNextElement(intoWebsiteNode(website),"startPage");
		return getNodeContent(startPage);
	}
	
	/**
	 * ��ȡ��ҳʱҳ�������Եı���
	 * @throws Exception 
	 */
	public String getPageTimes(String website) throws Exception
	{
		Element pageTimes=getNextElement(intoWebsiteNode(website),"pageTimes");
		return getNodeContent(pageTimes);
	}
	
	/**
	 * ��ȡ��ƽ̨��Ʒurl��ǰ׺
	 * @throws Exception 
	 */
	public String getPrefix(String website) throws Exception
	{
		Element prefix=getNextElement(intoWebsiteNode(website),"prefix");
		return getNodeContent(prefix);
	}
	
	 /** 
     * ������ǰ�ڵ�Ԫ�����������(Ԫ�ص�)�ӽڵ� 
     *  
     * @param node 
     */  
    public void listNodes(Element node) {  
        System.out.println("��ǰ�ڵ�����ƣ���" + node.getName());  
        // ��ȡ��ǰ�ڵ���������Խڵ�  
        List<Attribute> list = node.attributes();  
        // �������Խڵ�  
        for (Attribute attr : list) {  
            System.out.println(attr.getText() + "-----" + attr.getName()  
                    + "---" + attr.getValue());  
        }  
  
        if (!(node.getTextTrim().equals(""))) {  
            System.out.println("�ı����ݣ�������" + node.getText());  
        }  
  
        // ��ǰ�ڵ������ӽڵ������  
        Iterator<Element> it = node.elementIterator();  
        // ����  
        while (it.hasNext()) {  
            // ��ȡĳ���ӽڵ����  
            Element e = it.next();  
            // ���ӽڵ���б���  
            listNodes(e);  
        }  
    }

	// ��ȡĳ��Ԫ���±�ǩ (strLabel)���������  
	public Element getNextElement(Element priorEle,String strLabel){
		return priorEle.element(strLabel);  
	}
    
	/**
	 * ��ȡ���еĹؼ���
	 * @return �ؼ����ַ���
	 * @throws Exception 
	 */
	public String[] getAllProductsKeyword() throws Exception   //������Ҫ�����Ĺؼ���
	{
		//String []keys={"���","������"};
		Element rootEle=getRootEle();
		Element keywordsEle=getNextElement(rootEle, "keywords");
		
		String[] keys=getNodeContent(keywordsEle).split(";");
		return keys;
	}
	
	/**
	 * ��ȡget productURL�������������
	 * @return ��������
	 * @throws Exception
	 */
	public String getMaximum() throws Exception     
	{
		Element rootEle=getRootEle();
		Element maximumEle=getNextElement(rootEle,"maximum");
		
		String maximum=getNodeContent(maximumEle);
		return maximum;
	}
	

	/** @pdOid e6c5ea3a-4a79-4117-8fa7-1a5b774bc5d8 */
	public String[] getCategory() {
		// TODO: implement
		return null;
	}

	/** @param category
	 * @pdOid dc771c1d-fc30-4174-b960-afda3155f6e8 */
	public void addCategory(String category) {
		// TODO: implement
	}

	/** @param productKeyWord
	 * @pdOid 85cb9537-1fb3-4f76-8ae6-ff6ab312ed31 */
	public void addProductKeyword(String productKeyWord) {
		// TODO: implement
	}
	

	

}