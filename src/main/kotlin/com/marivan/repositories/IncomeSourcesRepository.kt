package com.marivan.repositories

import com.marivan.entity.IncomeSources
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface IncomeSourcesRepository  : CrudRepository<IncomeSources, Long> {
}
