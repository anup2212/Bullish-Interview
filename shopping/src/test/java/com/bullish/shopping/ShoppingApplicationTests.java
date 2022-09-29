package com.bullish.shopping;

import com.bullish.shopping.controller.CustomerController;
import com.bullish.shopping.entity.DiscountValue;
import com.bullish.shopping.entity.Product;
import com.bullish.shopping.entity.ProductResponse;
import com.bullish.shopping.entity.Receipt;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest (classes = ShoppingApplication.class)
@AutoConfigureMockMvc
class ShoppingApplicationTests extends AbstractTestUtil {

	private final String CREATE_PRODUCT_ENDPOINT = "/admin/product/add" ;
	private final String CREATE_DISCOUNT_ENDPOINT = "/admin/discount/add" ;
	private final String PRODUCT_FETCH_ENDPOINT ="/admin/product/fetch";

	private final String CUSTOMER_ADD_TO_CART = "/customer/cart/add";
	private final String CUSTOMER_CART_CHECKOUT = "/customer/cart/checkout";

	@Autowired
	MockMvc mock;

	@Test
	public void testCart() throws Exception {

		List<Product> products = new ArrayList();

		Product product = new Product();
		product.setProductName("IPHONE 14");
		product.setPrice(1400);
		products.add(product);

		ObjectMapper mapper = new ObjectMapper();

		String json = mapper.writeValueAsString(products);

		//Add a product
		mock.perform(MockMvcRequestBuilders.post(CREATE_PRODUCT_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.content(json)
				.characterEncoding("utf-8"));

		MvcResult mvcResult = fetchProducts();


		Product[] productResponse = parseResponse(mvcResult, Product[].class);

		//Assertion 1 : Product successfully inserted
		Assert.assertEquals(0, Double.compare(productResponse[0].getPrice(), 1400));
		Assert.assertNull(productResponse[0].getDiscounts());

		//Add a discount
		mock.perform(MockMvcRequestBuilders.post(CREATE_DISCOUNT_ENDPOINT)
				.contentType(MediaType.APPLICATION_JSON)
				.queryParam("productId", productResponse[0].getProductId().toString())
				.queryParam("discount", DiscountValue.FIFTY_PERCENT_OFF.name()));

		mvcResult = fetchProducts();
		productResponse = parseResponse(mvcResult, Product[].class);

		//Assertion 2: Discount successfully inserted
		Assert.assertNotNull(productResponse[0].getDiscounts());

		//Add to cart

		mock.perform(MockMvcRequestBuilders.post(CUSTOMER_ADD_TO_CART)
				.contentType(MediaType.APPLICATION_JSON)
				.queryParam("productId", productResponse[0].getProductId().toString())
				.queryParam("customerId", "1"));


		mvcResult = mock.perform(MockMvcRequestBuilders.post(CUSTOMER_CART_CHECKOUT)
				.contentType(MediaType.APPLICATION_JSON)
				.queryParam("customer", "1"))
				.andReturn();

		Receipt receipt = parseResponse(mvcResult, Receipt.class);

		//Assertion 3: Check the final price
		Assert.assertEquals(0, Double.compare(receipt.getPriceAfterDiscount(), 700));


	}

	private MvcResult fetchProducts() throws Exception {
		//Fetch the products
		MvcResult mvcResult = mock.perform(MockMvcRequestBuilders.get(PRODUCT_FETCH_ENDPOINT))
				.andDo(MockMvcResultHandlers.print())
				.andExpect(MockMvcResultMatchers.status().isOk())
				.andReturn();
		return mvcResult;
	}

}
