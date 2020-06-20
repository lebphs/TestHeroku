package com.marivan.repositories

import com.marivan.entity.Incomes
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IncomesRepository  : CrudRepository<Incomes, Long> {
}
