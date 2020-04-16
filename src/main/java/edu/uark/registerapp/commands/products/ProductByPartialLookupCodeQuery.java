package edu.uark.registerapp.commands.products;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import edu.uark.registerapp.commands.ResultCommandInterface;
import edu.uark.registerapp.commands.exceptions.NotFoundException;
import edu.uark.registerapp.commands.exceptions.UnprocessableEntityException;
import edu.uark.registerapp.models.api.Product;
import edu.uark.registerapp.models.entities.ProductEntity;
import edu.uark.registerapp.models.repositories.ProductRepository;

@Service
public class ProductByPartialLookupCodeQuery implements ResultCommandInterface<Product>{

	@Override
	public Product[] execute() {
		this.validateProperties();

        final List<Product> products = new LinkedList<Product>();
		final List<ProductEntity> productEntity =
			this.productRepository.findByLookupCodeContainingIgnoreCase(this.partialLookupCode);
		//if (productEntity.isPresent()) {
		//	return new Product(productEntity.get());
		//} else {
		//	throw new NotFoundException("Product");
        //}
        //for (ProductEntity productEntity : productEntites) {
          //  products.add(new Product(productEntity));
        //}
        //return (Product[])products toArray();
        return this.productRepository.findByLookupCodeContainingIgnoreCase(
            this.partialLookupCode
        ).stream()
        .map(productEntity -> (new Product(productEntity)))
        .toArray(Product[]::new);
	}

	// Helper methods
	private void validateProperties() {
		if (StringUtils.isBlank(this.partialLookupCode)) {
			throw new UnprocessableEntityException("partialLookupCode");
		}
	}

	// Properties
	private String partialLookupCode;
	public String getPartialLookupCode() {
		return this.partialLookupCode;
	}
	public ProductByPartialLookupCodeQuery setPartialLookupCode(final String partialLookupCode) {
		this.partialLookupCode = partialLookupCode;
		return this;
	}

	@Autowired
	private ProductRepository productRepository;
} 