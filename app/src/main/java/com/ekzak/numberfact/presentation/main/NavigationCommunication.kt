package com.ekzak.numberfact.presentation.main

import com.ekzak.numberfact.presentation.numbers.Communications

interface NavigationCommunication{

    interface Observe : Communications.Observe<NavigationStrategy>
    interface Mutate : Communications.Mutate<NavigationStrategy>
    interface Mutable: Observe, Mutate

    class Base: Communications.SingleUi<NavigationStrategy>(), Mutable
}
