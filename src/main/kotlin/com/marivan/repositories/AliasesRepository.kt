package com.marivan.repositories

import com.marivan.entity.Aliases
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository

@Repository
interface AliasesRepository  : CrudRepository<Aliases, Long> {
}
