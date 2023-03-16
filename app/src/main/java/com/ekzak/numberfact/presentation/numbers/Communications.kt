package com.ekzak.numberfact.presentation.numbers

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer

interface Communications {

    interface Observe<T> {
        fun observe(owner: LifecycleOwner, observer: Observer<T>)
    }

    interface Mutate<T> : Mapper.Unit<T>

    interface Mutable<T> : Observe<T>, Mutate<T>

    abstract class Abstract<T>(
        private val liveData: MutableLiveData<T> = MutableLiveData(),
    ) : Mutable<T> {
        override fun observe(owner: LifecycleOwner, observer: Observer<T>) = liveData.observe(owner, observer)
    }

    abstract class Ui<T>(
        private val liveData: MutableLiveData<T> = MutableLiveData(),
    ) : Abstract<T>(liveData) {
        override fun map(source: T) {
            source?.let { liveData.value = it }
        }
    }

    abstract class Post<T>(
        private val liveData: MutableLiveData<T> = MutableLiveData(),
    ) : Abstract<T>(liveData) {
        override fun map(source: T) {
            source?.let { liveData.postValue(it) }
        }
    }
}
