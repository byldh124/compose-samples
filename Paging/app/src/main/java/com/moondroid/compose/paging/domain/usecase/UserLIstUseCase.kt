package com.moondroid.compose.paging.domain.usecase

import com.moondroid.compose.paging.domain.repository.Repository
import javax.inject.Inject

class UserLIstUseCase @Inject constructor(private val repository: Repository) {
    suspend operator fun invoke() = repository.getUsers()
}