package com.pritamprasad.productservice.config;

import com.pritamprasad.productservice.models.Product;
import com.pritamprasad.productservice.repo.ProductsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
public class SetupData implements CommandLineRunner {

    @Autowired
    private ProductsRepository productsRepository;

    @Override
    public void run(String... args) throws Exception {
        /**
         * Uncomment dataSetup(); line to add initial data to database
         */
        dataSetup();
    }

    private void dataSetup(){
        productsRepository.save(new Product(UUID.randomUUID(),"Java Book", 1000.0));
        productsRepository.save(new Product(UUID.randomUUID(),"C Book", 760.0));
        productsRepository.save(new Product(UUID.randomUUID(),"C++ Book", 190.0));
        productsRepository.save(new Product(UUID.randomUUID(),"Python Book", 100.0));
        productsRepository.save(new Product(UUID.randomUUID(),"C# Book", 730.0));
        productsRepository.save(new Product(UUID.randomUUID(),"Psychology Book", 760.0));
        productsRepository.save(new Product(UUID.randomUUID(),"Julia Book", 190.0));
        productsRepository.save(new Product(UUID.randomUUID(),"Philosophy Book", 100.0));
        productsRepository.save(new Product(UUID.randomUUID(),"CUDA Book", 2000.0));
        productsRepository.save(new Product(UUID.randomUUID(),"OpenCV Book", 860.0));
        productsRepository.save(new Product(UUID.randomUUID(),"AI/ML Book", 690.0));
        productsRepository.save(new Product(UUID.randomUUID(),"Blockchain Book", 650.0));
    }
}
