package com.test.data.util.extensions

import org.junit.Assert
import org.junit.Test

class StringExtensionsKtTest {

    @Test
    fun formatToLocalDate() {

        Assert.assertEquals("2010-06-04T18:45:00.000Z".formatToLocalDate(), "04 June 2010")
        Assert.assertEquals("2010-12-08T15:43:00.000Z".formatToLocalDate(), "08 December 2010")

    }
}