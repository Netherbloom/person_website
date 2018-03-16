package com.util;

import java.io.File;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.lucene.analysis.Analyzer;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.document.TextField;
import org.apache.lucene.index.DirectoryReader;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.IndexWriterConfig;
import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.wltea.analyzer.lucene.IKAnalyzer;

import com.website.blog.entity.Blog;

/**
 * Lucene搜索
 * @author dream
 *
 */
public class LuceneSerch {

	  //设定索引的存放路径    
    private String searchDir = "E:\\data\\LuceneAndMysql\\index";    
    private static File indexFile = null;    
    private static IndexSearcher searcher = null;    
    public Query query = null;
    
    /**  
     * 获取数据库数据  
     * @param <T>
     *   
     * @return ResultSet  
     * @throws Exception  
     */    
    public <T> List<T> getResult(String queryStr, String[] field_arr, Class<T> clazz) throws Exception {    
        List<T> result = null;    
        TopDocs topDocs = this.search(queryStr, field_arr);    
        ScoreDoc[] scoreDocs = topDocs.scoreDocs;    
        result = this.addHits2List(scoreDocs, field_arr, clazz);    
        return result;    
    }    
    
    /**  
     * 为数据库检索数据创建初始化索引  
     * @param <T>
     *   
     * @param rs  
     * @throws Exception  
     */    
    public <T> void createIndex(List<T> index_list, String[] field_arr) throws Exception {    
        Directory directory = null;    
        IndexWriter indexWriter = null;    
        try {    
            indexFile = new File(searchDir);    
            if (!indexFile.exists()) {    
                indexFile.mkdir();    
            }    
            
            directory = FSDirectory.open(indexFile.toPath());
            IndexWriterConfig iwc = new IndexWriterConfig(this.initAnalyzer());
            indexWriter = new IndexWriter(directory, iwc);
            indexWriter.deleteAll();// 清除以前的index
            Document doc = null;
            //设置索引值
            for (T t : index_list) {
            	//设置索引字段
            	for (String field : field_arr) {
            		String method_name = null;
            		if(field.length() > 0){
            			method_name = "get"+field.substring(0, 1).toUpperCase()+field.subSequence(1, field.length());
            		}
            		Method m =t.getClass().getMethod(method_name);
            		/**  
            		 * TextField默认分词，StringField默认不分词，因为我这里name和descr都需要分词，所以都用的TextField  
            		 */    
	                doc = new Document();    
	                System.out.println("init_index_field:"+m.invoke(t));
	                doc.add(new TextField(field, m.invoke(t).toString(), Field.Store.YES));    
	                indexWriter.addDocument(doc);    
				}
            }    
            indexWriter.close();    
    
        } catch (Exception e) {    
            e.printStackTrace();    
        }  
    }    
    
    /**  
     * 搜索索引  
     *   
     * @param queryStr  
     * @return  
     * @throws Exception  
     */    
    private TopDocs search(String queryStr, String[] field_arr) throws Exception {
        if (searcher == null) {
            indexFile = new File(searchDir);
            IndexReader reader = DirectoryReader.open(FSDirectory.open(indexFile.toPath()));
            searcher = new IndexSearcher(reader);
        }
        /**
         * 多字段同时搜索，并设定它们在搜索结果排序过程中的权重，权重越高，排名越靠前
         */
        Map<String , Float> boosts = new HashMap<String, Float>();
        for (String field : field_arr) {
        	boosts.put(field, 1.0f);
		}
        /**
         * 用MultiFieldQueryParser类实现对同一关键词的跨域搜索
         */
        MultiFieldQueryParser  parser = new MultiFieldQueryParser(field_arr, this.initAnalyzer(), boosts);
        query = parser.parse(queryStr);
        System.out.println("QueryParser :" + query.toString());
        TopDocs topDocs = searcher.search(query, 10000);
        return topDocs;
    }
    
    /**
     * 返回结果并添加到List中  (目前只支持字符串字段)
     * @param scoreDocs
     * @param field_arr
     * @param clazz
     * @return
     * @throws Exception
     */
    private <T> List<T> addHits2List(ScoreDoc[] scoreDocs, String[] field_arr, Class<T> clazz) throws Exception {    
        List<T> listBean = new ArrayList<T>();    
        for (int i = 0; i < scoreDocs.length; i++) {    
            int docId = scoreDocs[i].doc;    
            Document doc = searcher.doc(docId);
            //设置索引字段
        	for (String field : field_arr) {
        		String method_name = null;
        		if(field.length() > 0){
        			method_name = "set"+field.substring(0, 1).toUpperCase()+field.subSequence(1, field.length());
        		}
        		System.out.println(clazz.toString());
        		T obj = clazz.newInstance();
        		Method m = clazz.getMethod(method_name, String.class);
        		m.invoke(obj,doc.get(field));  
        		listBean.add(obj);    
			}
        }    
        return listBean;    
    }    
    
    private Analyzer initAnalyzer(){
    	return new IKAnalyzer(true);
    }
    
    public static void main(String[] args) throws Exception {    
    	LuceneSerch logic = new LuceneSerch();    
        //初始化数据
    //	List<Blog> list = new ArrayList<Blog>();
  /*  	for (int i = 0; i < 100000; i++) {
    		Blog a = new Blog();
    		a.setContentstr("多字段同时搜索，并设定它们在搜索结果排序过程中的权重，权重越高，排名越靠前。用MultiFieldQueryParser类实现对同一关键词的跨域搜索"+i);
    		list.add(a);
		}*/
        
        String queryStr = "2018";//要查询的关键字
        String[] fieldarr = {"contentstr"};
        try {    
            Long startTime = System.currentTimeMillis();    
          // logic.createIndex(list, fieldarr);//初始化索引
            List<Blog> result = logic.getResult(queryStr, fieldarr, Blog.class);
            int i = 0;    
            for (Blog bean : result) {    
                if (i == 100)    
                    break;    
                /**  
                 * 打印完整的结果  
                 * 
                 */    
                System.out.println("bean.content " + bean.getContentstr());
                i++;    
            }    
            System.out.println("searchBean.result.size : " + result.size());    
            Long endTime = System.currentTimeMillis();    
            System.out.println("查询所花费的时间为：" + (endTime - startTime) / 1000);    
        } catch (Exception e) {    
            e.printStackTrace();    
            System.out.println(e.getMessage());    
        }    
    }  
    
    /* 为数据库检索数据创建初始化索引  
    * @param <T>
    *   
    * @param rs  
    * @throws Exception  
    */    
   public <T> void createIndex1(List<T> index_list, String[] field_arr) throws Exception {    
       Directory directory = null;    
       IndexWriter indexWriter = null;    
       try {    
           indexFile = new File(searchDir);    
           if (!indexFile.exists()) {    
               indexFile.mkdir();    
           }    
           
           directory = FSDirectory.open(indexFile.toPath());
           IndexWriterConfig iwc = new IndexWriterConfig(this.initAnalyzer());
           indexWriter = new IndexWriter(directory, iwc);
           Document doc = null;
           //设置索引值
           for (T t : index_list) {
           	//设置索引字段
           	for (String field : field_arr) {
           		String method_name = null;
           		if(field.length() > 0){
           			method_name = "get"+field.substring(0, 1).toUpperCase()+field.subSequence(1, field.length());
           		}
           		Method m =t.getClass().getMethod(method_name);
           		/**  
           		 * TextField默认分词，StringField默认不分词，因为我这里name和descr都需要分词，所以都用的TextField  
           		 */    
	                doc = new Document();    
	                System.out.println("init_index_field:"+m.invoke(t));
	                doc.add(new TextField(field, m.invoke(t).toString(), Field.Store.YES));    
	                indexWriter.addDocument(doc);    
				}
           }    
           indexWriter.close();    
   
       } catch (Exception e) {    
           e.printStackTrace();    
       }  
   }    
}
