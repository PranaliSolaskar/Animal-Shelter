package net.javaguides.sms.service;

import java.io.IOException;
import java.util.Base64;
import java.util.List;

import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import net.javaguides.sms.entity.Product;
import net.javaguides.sms.repository.ProductRepositry;


public class ProductService {

private ProductRepositry productRepo;
	
	public void  saveProductToDB(MultipartFile file,String name,String description
			,int price)
	{
		Product p = new Product();
		String fileName = StringUtils.cleanPath(file.getOriginalFilename());
		if(fileName.contains(".."))
		{
			System.out.println("not a a valid file");
		}
		try {
			p.setImage(Base64.getEncoder().encodeToString(file.getBytes()));
		} catch (IOException e) {
			e.printStackTrace();
		}
		p.setDescription(description);
		
        p.setName(name);
        p.setPrice(price);
        
        productRepo.save(p);
	}
	public List<Product> getAllProduct()
	{
		return productRepo.findAll();
	}
    public void deleteProductById(Long id)
    {
    	productRepo.deleteById(id);
    }
    public void chageProductName(Long id ,String name)
    {
    	Product p = new Product();
    	p = productRepo.findById(id).get();
    	p.setName(name);
    	productRepo.save(p);    
    }
    public void changeProductDescription(Long id , String description)
    {
    	Product p = new Product();
    	p = productRepo.findById(id).get();
    	p.setDescription(description);
    	productRepo.save(p);
    }
    public void changeProductPrice(Long id,int price)
    {
    	Product p = new Product();
    	p = productRepo.findById(id).get();
    	p.setPrice(price);
    	productRepo.save(p);
    }
}
