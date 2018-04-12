package com.website.admin.service;

import java.util.List;

import com.website.admin.entity.Ebooks;
import com.website.core.page.Page;
import com.website.core.page.Pageable;

public interface EbooksService {

	/**
	 * 主键查询
	 * @param id
	 * @return
	 */
	public Ebooks getEbooksById(String id);
	
	/**
	 * 名称查询
	 * @param name
	 * @return
	 */
	public Ebooks getEbooksByName(String name);
	
	public void save(Ebooks ebooks);
	
	public void update(Ebooks ebooks);
	
	public void delete(Ebooks ebooks);
	
	/**
	 * 同步书籍
	 * @throws Exception
	 */
	public void syncinitEbooks() throws Exception;
	
	public List<Ebooks> findAllUpdate();
	
	/**
	 * 分页查询书籍
	 * @param pageable
	 * @param keywords
	 * @return
	 */
	public Page<Ebooks> findAdminEbookPage(Pageable pageable,String keywords);
	
	
}
