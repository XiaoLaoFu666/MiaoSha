package imooc.com.huang.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import imooc.com.huang.co.GoodsVo;
import imooc.com.huang.dao.GoodsDao;
import imooc.com.huang.domain.Goods;
import imooc.com.huang.domain.MiaoshaGoods;

@Service
public class GoodsService {

	@Autowired
	GoodsDao goodsDao;
	
	public List<Goods> getAllGoods(){
		return goodsDao.selectAllGoods();
	}
	
	public List<GoodsVo> getMiaoShaoGoods(){
		return goodsDao.listGoodsVo();
	}

	public GoodsVo getValueById(long goodsId) {
		// TODO Auto-generated method stub
		return goodsDao.getValueById(goodsId);
	}


	public GoodsVo getGoodsByGoodsID(long goodsId) {
		// TODO Auto-generated method stub
		return goodsDao.getValueById(goodsId);
	}

	public boolean reduceStock(GoodsVo goods) {
		// TODO Auto-generated method stub
		MiaoshaGoods g = new MiaoshaGoods ();
		g.setGoodsId(goods.getId());
		int ret = goodsDao.reduceStock(g);
		return ret>0;
	}
}
