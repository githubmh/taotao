package com.taotao.content.service;

import java.util.List;

import com.taotao.common.pojo.EasyUIDateGridResult;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.pojo.TbContent;

public interface ContentService {
	//增加内容
	TaotaoResult addContent(TbContent content);
	//查询内容
	EasyUIDateGridResult queryContentList(long categoryId,int page,int rows);
	//首页广告位
	List<TbContent> getContentByCid(long cid);
}
