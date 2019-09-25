package org.fasttrackit.onlineshop;

import org.fasttrackit.onlineshop.domain.Product;
import org.fasttrackit.onlineshop.exception.ResourceNotFoundException;
import org.fasttrackit.onlineshop.service.ProductService;
import org.fasttrackit.onlineshop.transfer.product.SaveProductRequest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.TransactionSystemException;

import javax.validation.constraints.NotNull;

import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.CoreMatchers.notNullValue;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.greaterThan;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceIntegrationTests {

    @Autowired
    private ProductService productService;

    @Test
    public void testCreateProduct_whenValidRequest_thenReturnCreatedProduct() {
        createProduct();
    }


    @Test(expected = TransactionSystemException.class)
    public void testCreateProduct_whenInvalidRequest_thenThrowException() {
        SaveProductRequest request = new SaveProductRequest();
//    (mai jos) we're not setting any values on the request, because we want to send an invalid request!

        productService.createProduct(request);

    }

    //    mai jos testam metoda getProduct cand exista ID
    @Test
    public void testGetProduct_whenExistingEntity_thenReturnProduct() {
//        alt+enter pe createProduct pentru a il stoca ca si variabila
        Product createdProduct = createProduct();

        Product retrievedProduct = productService.getProduct(createdProduct.getId());

        assertThat(retrievedProduct, notNullValue());
        assertThat(retrievedProduct.getId(), is(createdProduct.getId()));
        assertThat(retrievedProduct.getName(), is(createdProduct.getName()));

    }


    private Product createProduct() {
        SaveProductRequest request = new SaveProductRequest();
        request.setName("Computer");
        request.setDescription("Some description");
        request.setPrice(2000);
        request.setQuantity(100);

        Product product = productService.createProduct(request);

        assertThat(product, notNullValue());
        assertThat(product.getId(), notNullValue());
        assertThat(product.getId(), greaterThan(0L));
        assertThat(product.getName(), is(request.getName()));
        assertThat(product.getDescription(), is(request.getDescription()));
        assertThat(product.getPrice(), is(request.getPrice()));
        assertThat(product.getQuantity(), is(request.getQuantity()));

        return product;
    }
//        acum facem un test negativ

    @Test(expected = ResourceNotFoundException.class)
    public void testGetProduct_whenNonExistingEntity_thenThrowNotFoundException() {
        productService.getProduct(99999L);
    }


//    testing pentru quest
    @Test
    public void testUpdateProduct_whenExistingEntity_thenThrowNotFoundException(){
        Product createdProduct = createProduct();

        SaveProductRequest request = new SaveProductRequest();
        request.setName(createdProduct.getName() + " Updated");
        request.setPrice(createdProduct.getPrice() + 10);
        request.setQuantity(createdProduct.getQuantity() + 10);
        Product updatedProduct =
                productService.updateProduct(createdProduct.getId(), request);

        assertThat(updatedProduct, notNullValue());
        assertThat(updatedProduct.getId(), greaterThan(0L));
        assertThat(updatedProduct.getName(), is(request.getName()));
        assertThat(updatedProduct.getPrice(), is(request.getPrice()));
        assertThat(updatedProduct.getQuantity(), is(request.getQuantity()));

    }

}
