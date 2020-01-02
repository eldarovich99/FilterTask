package com.eldarovich99.avitotesttask

import com.eldarovich99.avitotesttask.domain.entity.Service
import org.junit.Test

class ExtensionsTest {
    @Test
    fun checkServicesArrayListsEquality() {
        val services1 = arrayListOf(Service("a", true), Service("b", true), Service("c", true))
        val services2 = arrayListOf(Service("a", true), Service("b", true), Service("c", true))
        val services3 = arrayListOf(Service("a", false), Service("b", true), Service("c", true))
        val services4 = arrayListOf(Service("a", true), Service("b", true), Service("d", true))
        val services5 = arrayListOf(Service("a", true), Service("b", true), Service("d", true))
        assert(services1.areEqual(services2))
        assert(services2.areEqual(services1))
        assert(!services1.areEqual(services3))
        assert(!services1.areEqual(services4))
        assert(services3.areEqual(services3))
        assert(services4.areEqual(services5))
        assert(services5.areEqual(services4))
    }
}
