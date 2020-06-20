package com.marivan.repositories

import com.marivan.entity.Categories
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface CategoryRepository : CrudRepository<Categories, Long> {

    fun findCategoryByName(name: String): Categories;
}
