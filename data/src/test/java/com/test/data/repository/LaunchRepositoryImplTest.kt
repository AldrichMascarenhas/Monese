package com.test.data.repository

import com.test.data.mapper.LaunchMapper
import org.junit.Before
import org.mockito.Mockito.mock

class LaunchRepositoryImplTest {

    private lateinit var launchRepositoryImpl: LaunchRepositoryImpl
    private lateinit var launchRepository: LaunchRepository
    private lateinit var launchMapper: LaunchMapper

    @Before
    fun before() {

        launchRepository = mock(LaunchRepository::class.java)
        launchMapper = mock(LaunchMapper::class.java)

        launchRepositoryImpl = LaunchRepositoryImpl(
            launchRepository = launchRepository,
            launchMapper = launchMapper
        )
    }
}