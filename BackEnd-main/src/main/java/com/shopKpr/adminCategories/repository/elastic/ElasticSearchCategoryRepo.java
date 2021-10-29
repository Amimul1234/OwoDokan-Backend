package com.shopKpr.adminCategories.repository.elastic;

import com.shopKpr.adminCategories.model.Category;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ElasticSearchCategoryRepo extends ElasticsearchRepository<Category, Long> {

}
