package com.youapps.users_management.ui.registration

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.State
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.youapps.designsystem.components.lists.CarouselState
import com.youapps.designsystem.components.menus.DropDownMenuData
import com.youapps.designsystem.components.menus.DropDownMenuItemData
import com.youapps.designsystem.components.menus.KeywordsData
import com.youapps.onlybeans.domain.entities.users.OBLocation
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.exception.DomainErrorType
import com.youapps.onlybeans.domain.services.InputRuleType
import com.youapps.onlybeans.domain.valueobjects.UserSex
import com.youapps.onlybeans.ui.product.ProductListData


sealed interface InputRuleCheckState {

    data object Initial : InputRuleCheckState

    @Immutable
    data class Invalid(
        val input: String?,
        val brokenRule: InputRuleType,
    ) : InputRuleCheckState

    @Immutable
    data class Valid(
        val input: String
    ) : InputRuleCheckState

}


sealed interface OBRegistrationScreenState {


    @Stable
    data object Loading : OBRegistrationScreenState

    @Stable
    data class Error(val errorType: DomainErrorType = DomainErrorType.Undefined) :
        OBRegistrationScreenState

    @Stable
    data class Success(val userProfile: OBUserProfile) : OBRegistrationScreenState
}

data class OBRegistrationStateHolder(
    val profilePicture: State<String?>,
    val coverPicture: State<String?>,
    val profileDescription: State<InputRuleCheckState>,
    val profileStatus: State<InputRuleCheckState>,
    val firstName: State<String?>,
    val lastName: State<String?>,
    val userSex: State<UserSex?>,
    val email: State<String?>,
    val country: State<InputRuleCheckState>,
    val city: State<InputRuleCheckState>,
    val location: State<OBLocation?>,
    val phone: State<InputRuleCheckState>,
    val countriesListData: State<DropDownMenuData?>,
    val citiesListData: State<DropDownMenuData?>,
    val coffeeSpaceCarouselState: State<CarouselState.Loaded>,
    val countryCodesDropDownMenuData: State<DropDownMenuData?>,
    val selectedCountryCode: State<DropDownMenuItemData?>,
    val link: State<InputRuleCheckState>,
    val keywords: State<KeywordsData?>,
    val coffeeGear: State<ProductListData?>,
    val coffeeBeans: State<ProductListData?>,
) {

    @Composable
    fun isFormReadyToSubmitState(): State<Boolean> = remember {
        derivedStateOf {
            profileDescription.value is InputRuleCheckState.Valid &&
                    profileStatus.value is InputRuleCheckState.Valid &&
                    country.value is InputRuleCheckState.Valid &&
                    city.value is InputRuleCheckState.Valid &&
                    phone.value is InputRuleCheckState.Valid
        }
    }

    companion object {

        @Composable
        fun rememberOBRegistrationState(
            profilePicture: State<String?>,
            coverPicture: State<String?>,
            profileDescription: State<InputRuleCheckState>,
            profileStatus: State<InputRuleCheckState>,
            firstName: State<String?>,
            lastName: State<String?>,
            userSex: State<UserSex?>,
            email: State<String?>,
            country: State<InputRuleCheckState>,
            city: State<InputRuleCheckState>,
            location: State<OBLocation?>,
            phone: State<InputRuleCheckState>,
            countriesListData: State<DropDownMenuData?>,
            citiesListData: State<DropDownMenuData?>,
            coffeeSpaceCarouselState: State<CarouselState.Loaded>,
            countryCodesDropDownMenuData: State<DropDownMenuData?>,
            selectedCountryCode: State<DropDownMenuItemData?>,
            link: State<InputRuleCheckState>,
            keywords: State<KeywordsData?>,
            coffeeGear: State<ProductListData?>,
            coffeeBeans: State<ProductListData?>,
        ): OBRegistrationStateHolder = remember(
            profilePicture,
            coverPicture,
            profileDescription,
            profileStatus,
            firstName,
            lastName,
            userSex,
            email,
            country,
            city,
            location,
            countriesListData,
            citiesListData,
            coffeeSpaceCarouselState,
            countryCodesDropDownMenuData,
            selectedCountryCode,
            link,
            keywords,
            coffeeGear,
            coffeeBeans
        ) {
            OBRegistrationStateHolder(
                profilePicture,
                coverPicture,
                profileDescription,
                profileStatus,
                firstName,
                lastName,
                userSex,
                email,
                country,
                city,
                location,
                phone,
                countriesListData,
                citiesListData,
                coffeeSpaceCarouselState,
                countryCodesDropDownMenuData,
                selectedCountryCode,
                link,
                keywords,
                coffeeGear,
                coffeeBeans
            )
        }

        @Composable
        fun bindViewModelState(
            viewModel: OBRegistrationViewModel
        ): OBRegistrationStateHolder =  rememberOBRegistrationState(
            profileDescription = viewModel.getProfileDescription()
                .collectAsStateWithLifecycle(initialValue = InputRuleCheckState.Initial),
            profilePicture = viewModel.getProfilePicture()
                .collectAsStateWithLifecycle(initialValue = null),
            profileStatus = viewModel.getProfileStatus()
                .collectAsStateWithLifecycle(initialValue = InputRuleCheckState.Initial),
            coverPicture = viewModel.getCoverPicture()
                .collectAsStateWithLifecycle(initialValue = null),
            firstName = viewModel.getFistName()
                .collectAsStateWithLifecycle(initialValue = null),
            lastName = viewModel.getLastName()
                .collectAsStateWithLifecycle(initialValue = null),
            userSex = viewModel.getUserSex()
                .collectAsStateWithLifecycle(initialValue = null),
            email = viewModel.getEmail()
                .collectAsStateWithLifecycle(initialValue = null),
            country = viewModel.getCountry()
                .collectAsStateWithLifecycle(initialValue = InputRuleCheckState.Initial),
            city = viewModel.getCity()
                .collectAsStateWithLifecycle(initialValue = InputRuleCheckState.Initial),
            location = viewModel.getLocation()
                .collectAsStateWithLifecycle(initialValue = null),
            phone = viewModel.getPhone()
                .collectAsStateWithLifecycle(initialValue = InputRuleCheckState.Initial),
            countriesListData = viewModel.getCountriesList()
                .collectAsStateWithLifecycle(initialValue = null),
            citiesListData = viewModel.getCitiesList()
                .collectAsStateWithLifecycle(initialValue = null),
            coffeeSpaceCarouselState = viewModel.getCoffeeSpaceCarouselImages()
                .collectAsStateWithLifecycle(
                    initialValue = CarouselState.Loaded(emptyList())
                ),
            countryCodesDropDownMenuData = viewModel.countryCodesDropDownMenuDataStateFlow.collectAsStateWithLifecycle(
                initialValue = null
            ),
            selectedCountryCode = viewModel.getSelectedPhonePrefix()
                .collectAsStateWithLifecycle(initialValue = null),
            link = viewModel.getProfileLink()
                .collectAsStateWithLifecycle(initialValue = InputRuleCheckState.Initial),
            keywords = viewModel.getProfileKeywordsList()
                .collectAsStateWithLifecycle(initialValue = null),
            coffeeBeans = viewModel.getUserCoffeeBeansProducts()
                .collectAsStateWithLifecycle(initialValue = null),
            coffeeGear = viewModel.getUserCoffeeGearProducts()
                .collectAsStateWithLifecycle(initialValue = null)
        )

    }
}