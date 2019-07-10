package imooc.com.huang.co;

import imooc.com.huang.domain.MiaoShaUser;

public class GoodsDetailvo {
    private int miaoShaStatus;  //当前状态
    private int remainSeconds;  // 剩余时间
    private GoodsVo goods;
    private MiaoShaUser miaoShaUser;
	public int getMiaoShaStatus() {
		return miaoShaStatus;
	}
	public void setMiaoShaStatus(int miaoShaStatus) {
		this.miaoShaStatus = miaoShaStatus;
	}
	public int getRemainSeconds() {
		return remainSeconds;
	}
	public void setRemainSeconds(int remainSeconds) {
		this.remainSeconds = remainSeconds;
	}
	public GoodsVo getGoods() {
		return goods;
	}
	public void setGoods(GoodsVo goods) {
		this.goods = goods;
	}
	public MiaoShaUser getMiaoShaUser() {
		return miaoShaUser;
	}
	public void setMiaoShaUser(MiaoShaUser miaoShaUser) {
		this.miaoShaUser = miaoShaUser;
	}
}
