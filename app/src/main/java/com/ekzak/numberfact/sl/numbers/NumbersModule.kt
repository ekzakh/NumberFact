package com.ekzak.numberfact.sl.numbers

import com.ekzak.numberfact.data.BaseNumbersRepository
import com.ekzak.numberfact.data.HandleDataRequest
import com.ekzak.numberfact.data.HandleDomainError
import com.ekzak.numberfact.data.NumberDataToDomain
import com.ekzak.numberfact.data.cache.NumberDataToCache
import com.ekzak.numberfact.data.cache.NumbersCacheDataSource
import com.ekzak.numberfact.data.cloud.NumbersCloudDataSource
import com.ekzak.numberfact.data.cloud.NumbersService
import com.ekzak.numberfact.domain.HandleError
import com.ekzak.numberfact.domain.HandleRequest
import com.ekzak.numberfact.domain.NumbersInteractor
import com.ekzak.numberfact.presentation.numbers.DetailsUi
import com.ekzak.numberfact.presentation.numbers.NumberResultMapper
import com.ekzak.numberfact.presentation.numbers.NumberUiMapper
import com.ekzak.numberfact.presentation.numbers.NumbersCommunications
import com.ekzak.numberfact.presentation.numbers.NumbersFactFeature
import com.ekzak.numberfact.presentation.numbers.NumbersInitialFeature
import com.ekzak.numberfact.presentation.numbers.NumbersListCommunication
import com.ekzak.numberfact.presentation.numbers.NumbersStateCommunication
import com.ekzak.numberfact.presentation.numbers.NumbersViewModel
import com.ekzak.numberfact.presentation.numbers.ProgressCommunication
import com.ekzak.numberfact.presentation.numbers.RandomNumberFactFeature
import com.ekzak.numberfact.presentation.numbers.ShowDetails
import com.ekzak.numberfact.sl.main.Core
import com.ekzak.numberfact.sl.main.Module

class NumbersModule(
    private val core: Core,
) : Module<NumbersViewModel.Base> {

    override fun viewModel(): NumbersViewModel.Base {

        val communications = NumbersCommunications.Base(
            ProgressCommunication.Base(),
            NumbersStateCommunication.Base(),
            NumbersListCommunication.Base()
        )
        val resultMapper = NumberResultMapper(communications, NumberUiMapper())
        val cacheDataSource =
            NumbersCacheDataSource.Base(core.provideDataBase().numbersDao(), NumberDataToCache())
        val mapperToDomain = NumberDataToDomain()

        val repository = BaseNumbersRepository(
            NumbersCloudDataSource.Base(core.service(NumbersService::class.java)),
            cacheDataSource,
            mapperToDomain,
            HandleDataRequest.Base(
                HandleDomainError(),
                cacheDataSource,
                mapperToDomain
            )
        )
        val interactor = NumbersInteractor.Base(
            repository,
            HandleRequest.Base(
                HandleError.Base(core),
                repository
            ),
            core.provideFactDetails()
        )
        return NumbersViewModel.Base(
            core.provideDispatchers(),
            NumbersInitialFeature(communications, resultMapper, interactor),
            NumbersFactFeature.Base(communications, resultMapper, interactor, core),
            RandomNumberFactFeature(communications, resultMapper, interactor),
            ShowDetails.Base(interactor, core.provideNavigation(), DetailsUi()),
            communications
        )
    }
}
