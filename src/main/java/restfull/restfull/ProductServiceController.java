package restfull.restfull;

import Model2.Product;
import java.util.HashMap;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;


/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author ASUS
 */
@RestController //membuat RestController
public class ProductServiceController {
    private static final Map<String, Product> productRepo = new HashMap<>();
   static {
      Product honey = new Product();
      honey.setId("1");
      honey.setName("Honey");
      productRepo.put(honey.getId(), honey);
      
      Product almond = new Product();
      almond.setId("2");
      almond.setName("Almond");
      productRepo.put(almond.getId(), almond);
   }
   
   //Untuk Menghapus data pada page "/products"
   @RequestMapping(value = "/products/{id}", method = RequestMethod.DELETE)
   public ResponseEntity<Object> delete(@PathVariable("id") String id) { 
      if(!productRepo.containsKey(id))
      {
          return new ResponseEntity<>("product could not be found", HttpStatus.NOT_FOUND);
      }
       //jika id yang ingin di hapus ada maka muncul pesan "Product is deleted successfully"
      else{
       productRepo.remove(id);
      return new ResponseEntity<>("Product is deleted successsfully", HttpStatus.OK);
      }
   }
   
   //Untuk Mengedit data pada page "/products"
   @RequestMapping(value = "/products/{id}", method = RequestMethod.PUT)
   public ResponseEntity<Object> updateProduct(@PathVariable("id") String id, @RequestBody Product product) {
       if(!productRepo.containsKey(product.getId())){ 
           return new ResponseEntity<>("There is no product key yet", HttpStatus.NOT_FOUND);
        }
       //id sama, data akan update
       else{
      productRepo.remove(id);
      product.setId(id);
      productRepo.put(id, product);
      return new ResponseEntity<>("Product is updated successsfully", HttpStatus.OK);
   }
   }
   //Untuk Menambahkan data pada page "/products"
   @RequestMapping(value = "/products", method = RequestMethod.POST)
   public ResponseEntity<Object> createProduct(@RequestBody Product product) {
      if(productRepo.containsKey(product.getId()))
      {
          return new ResponseEntity<>("ID Product is Exist",HttpStatus.CONFLICT);
      }
      else
      {
          productRepo.put(product.getId(), product);
          return new ResponseEntity<>("Product is created successfully", HttpStatus.CREATED);
          
      }
      
   }
   
   //Menampilkan page "/products"
   @RequestMapping(value = "/products")
   public ResponseEntity<Object> getProduct() {
      return new ResponseEntity<>(productRepo.values(), HttpStatus.OK);
   }
}

