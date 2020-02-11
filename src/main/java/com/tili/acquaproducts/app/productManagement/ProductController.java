package com.tili.acquaproducts.app.productManagement;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.hateoas.RepresentationModel;
import org.springframework.hateoas.mediatype.vnderrors.VndErrors;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
public class ProductController {

    private final ProductRepository productRepository;
    private final ProductResourceAssembler assembler;

    public ProductController(ProductRepository productRepository, ProductResourceAssembler assembler) {
        this.productRepository = productRepository;
        this.assembler = assembler;
    }

    @GetMapping("/products")
    public CollectionModel<EntityModel<Product>> all() {
        List<EntityModel<Product>> products = productRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return new CollectionModel<>(products,
                linkTo(methodOn(ProductController.class).all()).withSelfRel());
    }

    @GetMapping("/products/{id}")
    public EntityModel<Product> one(@PathVariable Long id) {
        return assembler.toModel(
                productRepository.findById(id)
                .orElseThrow(() -> new ProductNotFoundException(id))
        );
    }

    @PostMapping("/products")
    public ResponseEntity<EntityModel<Product>> newProduct(@RequestBody Product product) {
        Product newProduct = productRepository.save(product);

        return ResponseEntity
                .created(linkTo(methodOn(ProductController.class).one(newProduct.getId())).toUri())
                .body(assembler.toModel(newProduct));
    }

    @PutMapping("/products/{id}")
    public ResponseEntity<RepresentationModel> replaceProduct(@RequestBody Product newProduct, @PathVariable Long id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new ProductNotFoundException(id));

        if (product.getId().equals(id)) {
            product.setDescription(newProduct.getDescription());
            product.setDiscount(newProduct.getDiscount());
            product.setPrice(newProduct.getPrice());
            product.setName(newProduct.getName());
            product.setGender(newProduct.getGender());
            product.setSize(newProduct.getSize());
            product.setStock(newProduct.getStock());
            return ResponseEntity.ok(assembler.toModel(productRepository.save(product)));
        }

        return ResponseEntity
                .status(HttpStatus.METHOD_NOT_ALLOWED)
                .body(new VndErrors.VndError("Method not allowed" , "You cannot modify the product if it does not exist"));
    }

    @DeleteMapping("/products/{id}")
    public ResponseEntity<RepresentationModel> deleteProduct(@PathVariable Long id) {
        if (productRepository.existsById(id)) {
            productRepository.deleteById(id);
            return ResponseEntity.noContent().build();
        }
        else {
            return ResponseEntity
                    .status(HttpStatus.BAD_REQUEST)
                    .body(new VndErrors.VndError("Bad Request" , "The product you want to delete does not exist"));
        }
    }
}
