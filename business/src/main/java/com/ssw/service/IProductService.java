package com.ssw.service;

import com.ssw.common.ServerResponse;
import com.ssw.pojo.Product;
import com.ssw.vo.ProductDetailVO;
import org.apache.ibatis.annotations.Param;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpSession;
import java.util.List;

public interface IProductService {
    public ServerResponse addOrUpdate(Product product);
    //后台商品搜索
    public ServerResponse search(String productName, Integer productId, Integer pageNum, Integer pageSize);
    //商品详情
    public ServerResponse<ProductDetailVO> detail(Integer productId);
//    //商品详情
//    public ServerResponse<Product> detail(Integer productId);
    //根据商品id查询商品信息
    public ServerResponse<Product> findProductById(Integer productId);
    //扣库存
    public ServerResponse reduceProductStock(Integer productId, Integer stock);
    //查找热销产品
    public ServerResponse isHotProduct(Integer category_id, Integer is_hot);
}
