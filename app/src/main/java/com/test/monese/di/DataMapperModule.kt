package com.test.monese.di

import com.test.data.local.mapper.CachedLaunchMapper
import com.test.data.local.mapper.CachedRocketMapper
import com.test.data.mapper.LaunchMapper
import com.test.data.mapper.RocketMapper
import org.koin.dsl.module.module

val dataMapperModule = module {

    single { LaunchMapper() }

    single { RocketMapper() }

    single { CachedRocketMapper() }

    single { CachedLaunchMapper() }
}