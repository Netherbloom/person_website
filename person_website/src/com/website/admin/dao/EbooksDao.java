package com.website.admin.dao;

import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import org.springframework.stereotype.Repository;
import com.website.admin.entity.Ebooks;
import com.website.core.dao.BaseDaoWithQuery;
import com.website.core.dao.MapQuery;
import com.website.core.page.Page;
import com.website.core.page.Pageable;

@Repository
public class EbooksDao extends BaseDaoWithQuery<Ebooks, String>{

	@PersistenceContext
	EntityManager em;
	
	@Override
	protected EntityManager getEntityManager() {
		return em;
	}
	
	protected EbooksDao() {
		super(Ebooks.class);
	}
	
	public List<Ebooks> findAllUpdate(){
		StringBuilder sb=new StringBuilder();
		sb.append("select * from ebooks where  updatetime is null");
		Query query=em.createNativeQuery(sb.toString());
		MapQuery<Ebooks> mq = new MapQuery<Ebooks>(Ebooks.class);
		List<Ebooks>list=mq.getResultList(query);
		if(list!=null && list.size()>0){
			return list;
		}
		return null;
	}
	
	/**
	 * 分页获取后台书籍列表
	 * @param pageable
	 * @param keywords
	 * @return
	 */
	public Page<Ebooks> findAdminEbookPage(Pageable pageable,String keywords) {
		StringBuilder sb=new StringBuilder();
		if(keywords!=null && !"".equals(keywords)){
			sb.append(" and (writer like '%"+keywords+"%' or name like '%"+keywords+"%')");
		}
		String sql="select * from ebooks where 1=1 "+sb.toString();
		String totalQuery = "select count(*) from ebooks where 1=1 "+sb.toString();
		int nTotal = 0;
		Object oTotal = em.createNativeQuery(totalQuery).getSingleResult();
		if(null != oTotal){
			nTotal = Integer.parseInt(oTotal.toString());
		}
		Query query = em.createNativeQuery(sql);
		query.setMaxResults(pageable.getPageSize());
		query.setFirstResult(pageable.getOffset());
		MapQuery<Ebooks> mq = new MapQuery<Ebooks>(Ebooks.class);
	
		return new Page<Ebooks>(mq.getResultList(query), pageable, nTotal);
	}
	
}
