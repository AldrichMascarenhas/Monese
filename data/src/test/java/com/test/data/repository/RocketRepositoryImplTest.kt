package com.test.data.repository

import com.test.data.mapper.RocketMapper
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before
import org.mockito.Mockito

class RocketRepositoryImplTest {

    private lateinit var rocketRepositoryImpl: RocketRepositoryImpl
    private lateinit var rocketMapper: RocketMapper
    private lateinit var rocketRepository: RocketRepository

    @Before
    fun before() {
        rocketRepository = Mockito.mock(RocketRepository::class.java)
        rocketMapper = Mockito.mock(RocketMapper::class.java)

        rocketRepositoryImpl = RocketRepositoryImpl(
            rocketRepository = rocketRepository,
            rocketMapper = rocketMapper
        )

    }
}