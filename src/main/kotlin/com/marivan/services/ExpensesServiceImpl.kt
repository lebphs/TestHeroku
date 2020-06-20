package com.marivan.services

import com.marivan.entity.Categories
import com.marivan.entity.Expenses
import com.marivan.repositories.ExpensesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.logging.Logger


@Service
class ExpensesServiceImpl {

    private val logger: Logger = Logger.getLogger("ExpensesService")

    @Autowired
    lateinit var expensesRepository: ExpensesRepository
    @Autowired
    lateinit var categoryServiceImpl: CategoryServiceImpl

    fun getAllExpensesForMonth() : List<Expenses> {
        val calendar = Calendar.getInstance();
        calendar[Calendar.DATE] = calendar.getActualMinimum(Calendar.DAY_OF_MONTH)
        val firstDay = calendar.time
        calendar[Calendar.DATE] = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val lastDay = calendar.time
        return expensesRepository.getAllExpensesForMonth(firstDay, lastDay);
    }

    fun addExpenses(text:String) {
        val categoryName = text.split(" ")[0];
        val amount = text.split(" ")[1];

        val category: Categories = categoryServiceImpl.getCategoryByName(categoryName)
        expensesRepository.save(Expenses(amount = amount, category_id = category))


    }
}
