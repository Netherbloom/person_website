package com.website.admin.dao;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.website.admin.entity.EbookChapter;
import com.website.core.dao.BaseDaoWithQuery;
import com.website.core.dao.MapQuery;
import com.website.core.page.Page;
import com.website.core.page.Pageable;

@Repository
public class EbookChapterDao extends BaseDaoWithQuery<EbookChapter, String>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected EbookChapterDao() {
		super(EbookChapter.class);
	}

	//获取最大章节
	public int getMaxPri(String ebookid) {
		String sql = "select max(pri) from ebookchapter where ebookid='"+ebookid+"'";
		Object result = em.createNativeQuery(sql).getSingleResult();
		if(null != result){
			return Integer.parseInt(result.toString());
		}
		return 0;
	}
	
	/**
	 * 根据书id分页获取章节
	 * @param pageable
	 * @param ebookid
	 * @return
	 */
	public Page<EbookChapter> findPageByEbookid(Pageable pageable,String ebookid) {
		String sql="select * from ebookchapter where 1=1 and ebookid= '"+ebookid+"'";
		String totalQuery = "select count(*) from ebookchapter where 1=1 and ebookid= '"+ebookid+"'";
		int nTotal = 0;
		Object oTotal = em.createNativeQuery(totalQuery).getSingleResult();
		if(null != oTotal){
			nTotal = Integer.parseInt(oTotal.toString());
		}
		Query query = em.createNativeQuery(sql);
		query.setMaxResults(pageable.getPageSize());
		query.setFirstResult(pageable.getOffset());
		MapQuery<EbookChapter> mq = new MapQuery<EbookChapter>(EbookChapter.class);
		return new Page<EbookChapter>(mq.getResultList(query), pageable, nTotal);
	}
}
