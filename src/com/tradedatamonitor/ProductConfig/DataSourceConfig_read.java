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

/** 配置数据（关键字，网站，正则表达式）
 * 
 * @pdOid 2c5f83a3-55c0-4253-bd11-4e0d3caebf21 */
public class DataSourceConfig_read {
	
	private String configFileName;
	public DataSourceConfig_read(String fileName){
		this.configFileName=fileName;
	}
	
	/**
	 * 加载XML文件
	 *  @param fileName
	 * @throws DocumentException 
	 * @pdOid 226ee484-6c27-4695-8906-d699d66fc5fd */
	public Document loadConfigFile() throws Exception {
		// TODO: implement
		// 创建saxreader对象  
        SAXReader reader = new SAXReader();  
        // 读取一个文件，把这个文件转换成Document对象  
        Document document = reader.read(new File(configFileName));
        return document;
     
	}
	/**
	 * 把文档转换字符串 
	 * @param document
	 * @return
	 */
	public String docXmlToString(Document document){
		String docXmlText = document.asXML();  
		return docXmlText;
	}
	/**
	 *  获取根元素
	 * @param fileName
	 * @return
	 * @throws DocumentException
	 */
	public Element   getRootEle() throws Exception{
		
		// 获取根元素  
        Element root = loadConfigFile().getRootElement();        
        return root;
	}
	
	/**将元素转换为String
	 * @param e
	 * @return
	 */
	public String elementToString(Element e){
		String elemmentXmlText = e.asXML();  
		return elemmentXmlText;
	}
	
	/**
	 * 获取某个结点下的具体内容
	 * @param node
	 */
	public String getNodeContent(Element node){
		return node.getTextTrim();
	            
	}
	
	/**
	 * 进入网站website结点
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
	 * 获取网站的编码
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
	 * 获取网站构造搜索的URL
	 * @param website 网站
	 * @return 执行搜索时的URL
	 * @throws Exception 
	 */
	public String getSearchURL(String website,String keyword) throws Exception   //不同平台根据关键词搜索的URL构造
	{
		Element searchURL=getNextElement(intoWebsiteNode(website), "searchURL");
		
		if(website.equals("JingDong"))
			return getNodeContent(searchURL).replaceAll("\\[keyword\\]",keyword);
		else
			return getNodeContent(searchURL).replaceAll("\\[keyword\\]", URLEncoder.encode(keyword,getCharset(website)));
	}
	
	/**
	 * 获取某网站总页数的正则表达式
	 * @param website
	 * @return
	 * @throws Exception
	 */
	public String getExtractTotalPageRegx(String website) throws Exception    //获取总页数的正则表达式
	{	
		Element totalPageEle=getNextElement(intoWebsiteNode(website), "totalPage");
		return getNodeContent(totalPageEle);
	}
	
	/**
	 * 取得某节点下的某属性,并将其转成文本
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
	 * 获取某网站分页的分组
	 * @param website
	 * @return
	 * @throws Exception
	 */
	public int totalPageGroupNo(String website) throws Exception    //获取group页码位置
	{
		
		Element totalPageEle=getNextElement(intoWebsiteNode(website), "totalPage");
		
		return Integer.parseInt(targetGroup(totalPageEle,"targetgroup"));	
	}
	
	
	
	/**
	 * 获取网站用来构造下一页URL的字符串
	 * @param website
	 * @return
	 * @throws Exception
	 */
	public String getExtractPageURL(String website,String keyword,String pageNum) throws Exception    //获取不同页面URL的正则表达式
	{
		Element nextPageURL=getNextElement(intoWebsiteNode(website), "nextPageURL");
		
		if(website.equals("JingDong"))
			return getNodeContent(nextPageURL).replaceAll("\\[keyword\\]",keyword).replaceAll("\\[PageNum\\]", pageNum);
		else
			return getNodeContent(nextPageURL).replaceAll("\\[keyword\\]", URLEncoder.encode(keyword,getCharset(website))).replaceAll("\\[PageNum\\]", pageNum);
	}
	
	
	
	/**
	 * 获取某网站搜索结果页面中产品的正则表达式
	 * @param website
	 * @return
	 * @throws Exception
	 */
	public String getExtractProductRegx(String website) throws Exception    //获取页面中所有产品URL正则表达式
	{
		
		Element productURL=getNextElement(intoWebsiteNode(website), "productURL");
		return getNodeContent(productURL);
	}
	
	/**
	 * 获取没有搜索到关键词的判断条件正则表达式
	 * @param website
	 * @return
	 */
	public String getNotFoundRegx(String website) throws Exception    //获取没有搜索到关键词的判断条件正则表达式
	{
		Element notFound=getNextElement(intoWebsiteNode(website),"notFound");
		return getNodeContent(notFound);
	}
	
	/**
	 * 获取page的开始页
	 * @throws Exception 
	 */
	public String getStartPage(String website) throws Exception
	{
		Element startPage=getNextElement(intoWebsiteNode(website),"startPage");
		return getNodeContent(startPage);
	}
	
	/**
	 * 获取换页时页码所乘以的倍数
	 * @throws Exception 
	 */
	public String getPageTimes(String website) throws Exception
	{
		Element pageTimes=getNextElement(intoWebsiteNode(website),"pageTimes");
		return getNodeContent(pageTimes);
	}
	
	/**
	 * 获取各平台产品url的前缀
	 * @throws Exception 
	 */
	public String getPrefix(String website) throws Exception
	{
		Element prefix=getNextElement(intoWebsiteNode(website),"prefix");
		return getNodeContent(prefix);
	}
	
	 /** 
     * 遍历当前节点元素下面的所有(元素的)子节点 
     *  
     * @param node 
     */  
    public void listNodes(Element node) {  
        System.out.println("当前节点的名称：：" + node.getName());  
        // 获取当前节点的所有属性节点  
        List<Attribute> list = node.attributes();  
        // 遍历属性节点  
        for (Attribute attr : list) {  
            System.out.println(attr.getText() + "-----" + attr.getName()  
                    + "---" + attr.getValue());  
        }  
  
        if (!(node.getTextTrim().equals(""))) {  
            System.out.println("文本内容：：：：" + node.getText());  
        }  
  
        // 当前节点下面子节点迭代器  
        Iterator<Element> it = node.elementIterator();  
        // 遍历  
        while (it.hasNext()) {  
            // 获取某个子节点对象  
            Element e = it.next();  
            // 对子节点进行遍历  
            listNodes(e);  
        }  
    }

	// 获取某个元素下标签 (strLabel)代表的内容  
	public Element getNextElement(Element priorEle,String strLabel){
		return priorEle.element(strLabel);  
	}
    
	/**
	 * 获取所有的关键字
	 * @return 关键字字符串
	 * @throws Exception 
	 */
	public String[] getAllProductsKeyword() throws Exception   //所有需要搜索的关键词
	{
		//String []keys={"脐橙","奉节脐橙"};
		Element rootEle=getRootEle();
		Element keywordsEle=getNextElement(rootEle, "keywords");
		
		String[] keys=getNodeContent(keywordsEle).split(";");
		return keys;
	}
	
	/**
	 * 获取get productURL数量的最大限制
	 * @return 数量限制
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