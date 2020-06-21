package com.marivan.services

import com.marivan.entity.Categories
import com.marivan.entity.Expenses
import com.marivan.repositories.ExpensesRepository
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import java.util.*
import java.util.logging.Level
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
        val expenses = expensesRepository.getAllExpensesForMonth(firstDay, lastDay)
                .groupBy {it.category_id.name}
                .values
                .map {
                    it.reduce{
                        acc, item -> Expenses(amount = acc.amount.toInt().plus(item.amount.toInt()).toString() ,  category_id = item.category_id )
                    }
                }.orEmpty();
        return expenses
    }

    fun addExpenses(text:String) {
        val categoryName = text.split(" ")[0];
        val amount = text.split(" ")[1];

        val category: Categories = categoryServiceImpl.getCategoryByName(categoryName)
        if(category == null){
            logger.log(Level.SEVERE, "Not found category with name $categoryName")
        }
        expensesRepository.save(Expenses(amount = amount, category_id = category))


    }
}
