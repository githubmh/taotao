package com.taotao.content.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.taotao.common.pojo.EasyUITreeNode;
import com.taotao.common.pojo.TaotaoResult;
import com.taotao.content.service.ContentCategoryService;
import com.taotao.mapper.TbContentCategoryMapper;
import com.taotao.pojo.TbContentCategory;
import com.taotao.pojo.TbContentCategoryExample;
import com.taotao.pojo.TbContentCategoryExample.Criteria;
/**
 * 内容分类管理service
 * @author Administrator
 *
 */
@Service
public class ContentCategoryServiceImpl implements ContentCategoryService {
	@Autowired
	private TbContentCategoryMapper contentCategoryMapper;
	@Override
	public List<EasyUITreeNode> getContentCategoryList(long parentId) {
		// 根据parentID查询子节点列表
		TbContentCategoryExample example = new TbContentCategoryExample();
		//设置查询条件
		Criteria criteria = example.createCriteria();
		criteria.andParentIdEqualTo(parentId);
		//执行查询
		List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
		List<EasyUITreeNode> resultList = new ArrayList<>();
		for (TbContentCategory tbContentCategory : list) {
			EasyUITreeNode node = new EasyUITreeNode();
			node.setId(tbContentCategory.getId());
			node.setText(tbContentCategory.getName());
			node.setState(tbContentCategory.getIsParent()?"closed":"open");
			//添加到结果列表
			resultList.add(node);
		}
		return resultList;
	}
	@Override
	public TaotaoResult addContentCategory(long parentId, String name) {
		// 创建一个pojo对象
		TbContentCategory contentCategory = new TbContentCategory();
		//补全对象的属性
		contentCategory.setParentId(parentId);
		contentCategory.setName(name);
		//状态 可选值：1（正常）2（删除）
		contentCategory.setStatus(1);
		//排序默认为1
		contentCategory.setSortOrder(1);
		contentCategory.setIsParent(false);
		contentCategory.setCreated(new Date());
		contentCategory.setUpdated(new Date());
		//插入到数据库
		contentCategoryMapper.insert(contentCategory);
		//判断父节点的状态
		TbContentCategory parent = contentCategoryMapper.selectByPrimaryKey(parentId);
		if (!parent.getIsParent()) {
			//如果父节点为叶子节点应该改为父节点
			parent.setIsParent(true);
			//更新父节点
			contentCategoryMapper.updateByPrimaryKey(parent);
		}
		//返回结果
		return TaotaoResult.ok(contentCategory);
	}
	@Override  
    public TaotaoResult updateContentCategory(long id, String name) {  
        //通过id查询节点对象  
        TbContentCategory contentCategory = contentCategoryMapper.selectByPrimaryKey(id);  
        //判断新的name值与原来的值是否相同，如果相同则不用更新  
        if(name != null && name.equals(contentCategory.getName())){  
            return TaotaoResult.ok();  
        }  
        contentCategory.setName(name);  
        //设置更新时间  
        contentCategory.setUpdated(new Date());  
        //更新数据库  
        contentCategoryMapper.updateByPrimaryKey(contentCategory);  
        //返回结果  
        return TaotaoResult.ok();  
    }  
      
    //通过父节点id来查询所有子节点
    private List<TbContentCategory> getChildNodeList(long id){  
       //查询所有父节点为传入ID的节点
    	TbContentCategoryExample example = new TbContentCategoryExample();
    	Criteria criteria = example.createCriteria();
    	criteria.andParentIdEqualTo(id);
    	List<TbContentCategory> list = contentCategoryMapper.selectByExample(example);
    	//返回符合的节点列表
    	return list;
    }  
      
    //递归删除节点  
    private void deleteCategoryAndChildNode(long id){  
       //获取要删除的category
    	TbContentCategory tbContentCategory = contentCategoryMapper.selectByPrimaryKey(id);
    	//判断是否为父节点
    	if (tbContentCategory.getIsParent()) {
			//获得所有的叶子节点
    		List<TbContentCategory> list = getChildNodeList(id);
    		//删除所有的叶子节点
    		for (TbContentCategory category : list) {
				deleteCategoryAndChildNode(category.getId());
			}
		}
    	//判断父节点下是否还有叶子节点
    	if (getChildNodeList(tbContentCategory.getParentId()).size() == 1) {
			//没有则讲父节点标位叶子节点
    		TbContentCategory parentCategory = contentCategoryMapper.selectByPrimaryKey(tbContentCategory.getParentId());
    		parentCategory.setIsParent(false);
    		contentCategoryMapper.updateByPrimaryKey(parentCategory);
		}
    	//删除本节点
    	contentCategoryMapper.deleteByPrimaryKey(id);
    	return;
    }  
  
    @Override  
    public TaotaoResult deleteContentCategory(long id) {  
    	deleteCategoryAndChildNode(id);
        return TaotaoResult.ok();  
    }  
}
