package imooc.com.huang.rabbitmq;

import imooc.com.huang.domain.MiaoShaUser;

public class MiaoshaMessage {

	private MiaoShaUser miaoshaUser;
	private long goodsId;
	public MiaoShaUser getMiaoshaUser() {
		return miaoshaUser;
	}
	public void setMiaoshaUser(MiaoShaUser miaoshaUser) {
		this.miaoshaUser = miaoshaUser;
	}
	public long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(long goodsId) {
		this.goodsId = goodsId;
	}
}
