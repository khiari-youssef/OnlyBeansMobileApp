package com.youapps.onlybeans.data.repositories.users


import com.youapps.onlybeans.domain.entities.users.OBUserProfile
import com.youapps.onlybeans.domain.valueobjects.OBAuthInterface


interface OBUsersRepositoryInterface {

    suspend fun authenticateUser(
       loginMethodInterface: OBAuthInterface
    ) : OBUserProfile


    suspend fun getActiveUserSessionToken() : String?

    suspend fun clearUsersFromLocalStorage() : Boolean

    suspend fun getCurrentUserData(withRefresh : Boolean = false) : OBUserProfile?


}