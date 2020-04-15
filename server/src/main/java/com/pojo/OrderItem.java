package com.pojo;

import com.pojo.Area;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class OrderItem {
    private int id;
    private int uid;
    private Area area;
    private int aid;
    private int nums;//购票数
    private float account;
    /*
    当下单后，会向数据库中插入一条数据，state为0，表示未使用，refund为0，表示未申请退款
    未使用的门票可以申请退款，一旦申请退款refund为1，表示申请中

    当用户使用门票即在景点门票机上刷了后，会传过来一个值修改state为1，即已使用，可以评价
    评价后，修改state值为2，即已评价

    当门票时间过期后，state变为4，即已失效

    用户扫描二维码后，会将支付金额传到支付池(redis)，当state变为1后或者门票有效期过了后，账目会从支付池内转到商家账户中(数据表),
    当用户发起退款后，后台将退款信息转达商家，商家同意后，后台将钱从支付池中返回用户账户
     */
    private int state;//0表示未使用  1表示已使用待评价 2已评价  4失效
    private int refund;//没有申请退款为0  申请退款，退款中为1  已退款为2  退款未到账申请回复 3
    private String times;//下单时间
    private int payWay;//0 微信，1支付宝
}
