package com.marivan.services

import com.marivan.entity.Categories
import com.marivan.repositories.CategoryRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service

@Service
class CategoryServiceImpl {

    @Autowired
    lateinit var categoryRepository: CategoryRepository

    fun getAllCategories() : List<Categories> = categoryRepository.findAll() as List<Categories>;

    fun getCategoryByName(name: String) : Categories = categoryRepository.findCategoryByName(name);

    fun addCategory(text: String) = categoryRepository.save(Categories(name = text.split(" ")[1].trim()))

}
