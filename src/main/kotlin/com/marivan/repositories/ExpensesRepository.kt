package com.marivan.repositories

import com.marivan.entity.Expenses
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import java.util.*

@Repository
interface ExpensesRepository  : CrudRepository<Expenses, Long> {

    @Query("SELECT id, name, cate FROM Expenses WHERE date > ?1 and date < ?2", nativeQuery = true)
    fun getAllExpensesForMonth(start:Date, end: Date) : List<Expenses>
}
