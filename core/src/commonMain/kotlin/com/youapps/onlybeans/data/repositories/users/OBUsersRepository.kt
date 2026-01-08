package com.youapps.onlybeans.data.repositories.users

import com.youapps.onlybeans.data.dataSources.UserPreferencesStore
import com.youapps.onlybeans.data.dataSources.UsersLocalDAO
import com.youapps.onlybeans.data.dataSources.UsersRemoteDAO
import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.valueobjects.OBAuthInterface
import kotlinx.coroutines.flow.firstOrNull




internal class OBUsersRepository(
    private val usersLocalDAO: UsersLocalDAO,
    private val usersRemoteDAO: UsersRemoteDAO,
    private val userPreferencesStore: UserPreferencesStore
) : OBUsersRepositoryInterface {



    override suspend fun authenticateUser(loginMethodInterface: OBAuthInterface): OBUserProfile = toDomainAuthenticationError(withCredentials = false){
        val response = when(loginMethodInterface){
            is OBAuthInterface.OBCredentialsLogin -> {
                 usersRemoteDAO.fetchEmailAndPasswordLoginAPI(loginMethodInterface.email,loginMethodInterface.password)
            }
            is OBAuthInterface.OBTokenLogin -> {
                usersRemoteDAO.fetchTokenLoginAPI(loginMethodInterface.value)
            }
        }
        runCatching {
            val hasTransactionSucceeded = usersLocalDAO.saveUserData(response.token, response.data)
            if (hasTransactionSucceeded) {
                response.data.myCoffeeSpace?.run {
                    usersLocalDAO.saveCoffeeSpace(this)
                }
            }
            return@runCatching response.data.toDomainModel()
        }.getOrNull() ?: throw IllegalStateException(
            "An error has occurred saving your post-login user data!"
        )
    }


    override suspend fun getActiveUserSessionToken(): String? = userPreferencesStore.getUserToken().firstOrNull()


    override suspend fun clearUsersFromLocalStorage() : Boolean {
        return usersLocalDAO.deleteLoggedINUser()
    }

    override suspend fun getCurrentUserData(withRefresh : Boolean): OBUserProfile? = runCatching {
        if (withRefresh) {
            userPreferencesStore.getUserToken().firstOrNull()?.let { token->
               val result = usersRemoteDAO.fetchUserProfileData(
                    token = token
                )
                result.myCoffeeSpace?.run {
                    usersLocalDAO.saveCoffeeSpace(result.myCoffeeSpace)
                }
                usersLocalDAO.saveUserData(token, result)
                result.toDomainModel()
            }
        } else {
            usersLocalDAO.getCurrentUserData()
        }
    }.onFailure {
        it.printStackTrace()
    }.getOrNull()



}

