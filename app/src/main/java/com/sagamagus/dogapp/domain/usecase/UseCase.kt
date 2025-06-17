package com.sagamagus.dogapp.domain.usecase

interface UseCase<in Params, out Result> {
    suspend operator fun invoke(params: Params): Result
}