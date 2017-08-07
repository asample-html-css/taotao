package com.taotao.cart.controller.api;

import com.taotao.cart.pojo.Cart;
import com.taotao.cart.service.CartService;
import com.taotao.common.bean.EasyUIResult;
import org.omg.CosNaming.NamingContextPackage.NotFound;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * Created by dd876799869 on 2017/8/6.
 */
@Controller
@RequestMapping("cart/api")
public class ApiCartController {

    @Autowired
    private CartService cartService;

    /**
     * 对外提供查询购物车信息的接口
     * @return
     */
    @RequestMapping(value = "{userId}",method = RequestMethod.GET)
    public ResponseEntity<List<Cart>> queryCart(@PathVariable("userId") Long userId) {

        try {
            List<Cart> cartList = this.cartService.queryCart(userId);
            if (null == cartList || cartList.isEmpty()){
                return  ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
            }
            return ResponseEntity.ok(cartList) ;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);

    }



}
