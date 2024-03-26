package kr.co._29cm.homework.product.application;

import kr.co._29cm.homework.product.domain.Product;
import kr.co._29cm.homework.product.domain.ProductRepository;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.ArrayList;
import java.util.List;

@Service
public class ProductCommandService {

    private final ProductRepository productRepository;
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    public ProductCommandService(ProductRepository productRepository, NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.productRepository = productRepository;
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    @Transactional
    public void init(String[] args) throws IOException {
        String csvPath = readCsvFilePath(args);
        File file = ResourceUtils.getFile(csvPath);
        List<String> productInfoList = Files.lines(file.toPath()).toList();
        List<Product> products = new ArrayList<>();
        for (int i = 1; i < productInfoList.size(); i++) {
            products.add(Product.of(productInfoList.get(i)));
        }

        productRepository.saveAll(products);
    }

    private String readCsvFilePath(String[] args) {
        String csvPath = ResourceUtils.CLASSPATH_URL_PREFIX + "product/items.csv";
        if (args.length != 0) {
            csvPath = args[0];
        }
        return csvPath;
    }
}
