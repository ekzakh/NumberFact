package com.ekzak.numberfact.data.cache

import com.ekzak.numberfact.data.NumberData

class NumberDataToCache : NumberData.Mapper<NumberCache> {

    override fun map(number: String, fact: String): NumberCache =
        NumberCache(number, fact, System.currentTimeMillis())

}
