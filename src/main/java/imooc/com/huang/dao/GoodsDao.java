package imooc.com.huang.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import imooc.com.huang.co.GoodsVo;
import imooc.com.huang.domain.Goods;
import imooc.com.huang.domain.MiaoshaGoods;

@Mapper
public interface GoodsDao {
     
	@Select("select * from goods")
	public List<Goods> selectAllGoods();
	
	@Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id")
	public List<GoodsVo> listGoodsVo();
	@Select("select g.*,mg.stock_count, mg.start_date, mg.end_date,mg.miaosha_price from miaosha_goods mg left join goods g on mg.goods_id = g.id where g.id = #{goodsId}")
	public GoodsVo getValueById(@Param ("goodsId")long goodsId);
     
	@Update("update miaosha_goods set stock_count = stock_count-1 where goods_id = #{goodsId}")
	public int reduceStock(MiaoshaGoods g);
}
