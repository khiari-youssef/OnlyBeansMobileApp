package com.youapps.onlybeans.android.notifications.data.data_sources

import com.youapps.onlybeans.android.notifications.data.dto.OBNotificationItemDataDTO
import com.youapps.onlybeans.android.notifications.domain.entities.OBNotificationItemData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.count
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.flow.update


interface OBNotificationsRemoteDataSource {

    fun fetchNotificationsByPagination(skip : Int,limit : Int) : Flow<List<OBNotificationItemData>>

    suspend fun updateNotificationReadStatus(id : String,isRead : Boolean) : Boolean

    suspend fun removeNotification(id : String) : Boolean

    suspend fun setAllCurrentNotificationsRead() : Boolean

    suspend fun fetchUnReadNotificationsCount() : Long
}


class OBNotificationsRemoteDataSourceImpl : OBNotificationsRemoteDataSource {

    var mockNotificationsState = MutableStateFlow<List<OBNotificationItemDataDTO>>(
        mutableListOf(
            OBNotificationItemDataDTO("1", "Order Received", "We've got your order #101.", System.currentTimeMillis(), false, "ORDER"),
            OBNotificationItemDataDTO("2", "Flash Sale!", "50% off all sneakers for the next hour.", System.currentTimeMillis() - 3600000, false, "PROMO"),
            OBNotificationItemDataDTO("3", "Points Earned", "You just earned 500 reward points!", System.currentTimeMillis() - 7200000, true, "REWARD"),
            OBNotificationItemDataDTO("4", "System Update", "We've updated our privacy policy.", System.currentTimeMillis() - 10800000, true, "GENERAL"),
            OBNotificationItemDataDTO("5", "Out for Delivery", "Your courier is nearby.", System.currentTimeMillis() - 14400000, false, "ORDER"),
            OBNotificationItemDataDTO("6", "Exclusive Deal", "Check out these items picked for you.", System.currentTimeMillis() - 18000000, false, "PROMO"),
            OBNotificationItemDataDTO("7", "Reward Expiring", "Your points expire in 2 days!", System.currentTimeMillis() - 21600000, false, "REWARD"),
            OBNotificationItemDataDTO("8", "Welcome!", "Thanks for joining our community.", System.currentTimeMillis() - 25200000, true, "GENERAL"),
            OBNotificationItemDataDTO("9", "Order Delivered", "Enjoy your purchase!", System.currentTimeMillis() - 28800000, true, "ORDER"),
            OBNotificationItemDataDTO("10", "Weekend Promo", "Get a free drink with any meal.", System.currentTimeMillis() - 32400000, false, "PROMO"),
            OBNotificationItemDataDTO("11", "Level Up", "You've reached Silver Status!", System.currentTimeMillis() - 36000000, false, "REWARD"),
            OBNotificationItemDataDTO("12", "Maintenance", "App will be down for 2 hours tonight.", System.currentTimeMillis() - 39600000, true, "GENERAL"),
            OBNotificationItemDataDTO("13", "Payment Success", "Payment for order #102 was successful.", System.currentTimeMillis() - 43200000, true, "ORDER"),
            OBNotificationItemDataDTO("14", "Holiday Gift", "Open the app to claim your gift.", System.currentTimeMillis() - 46800000, false, "PROMO"),
            OBNotificationItemDataDTO("15", "Redeem Now", "You have enough points for a free coffee.", System.currentTimeMillis() - 50400000, false, "REWARD"),
            OBNotificationItemDataDTO("16", "New Feature", "Try out our new Dark Mode!", System.currentTimeMillis() - 54000000, true, "GENERAL"),
            OBNotificationItemDataDTO("17", "Package Delayed", "Sorry, your order #103 is running late.", System.currentTimeMillis() - 57600000, false, "ORDER"),
            OBNotificationItemDataDTO("18", "Last Chance", "Sale ends at midnight!", System.currentTimeMillis() - 61200000, false, "PROMO"),
            OBNotificationItemDataDTO("19", "Birthday Bonus", "Happy Birthday! Here is a treat.", System.currentTimeMillis() - 64800000, false, "REWARD"),
            OBNotificationItemDataDTO("20", "Security Alert", "New login detected on your account.", System.currentTimeMillis() - 68400000, false, "GENERAL"),
            OBNotificationItemDataDTO("21", "Order Shipped", "Order #104 is on its way.", System.currentTimeMillis() - 72000000, true, "ORDER"),
            OBNotificationItemDataDTO("22", "Daily Deals", "See today's top picks.", System.currentTimeMillis() - 75600000, false, "PROMO"),
            OBNotificationItemDataDTO("23", "Points Summary", "You have 1200 total points.", System.currentTimeMillis() - 79200000, true, "REWARD"),
            OBNotificationItemDataDTO("24", "App Update", "New version 2.1 is now available.", System.currentTimeMillis() - 82800000, true, "GENERAL"),
            OBNotificationItemDataDTO("25", "Delivery Update", "Your courier is 5 minutes away.", System.currentTimeMillis() - 86400000, false, "ORDER"),
            OBNotificationItemDataDTO("26", "Big Savings", "Up to 70% off site-wide!", System.currentTimeMillis() - 90000000, false, "PROMO"),
            OBNotificationItemDataDTO("27", "Achievement Unlocked", "You've earned the 'Frequent Buyer' badge.", System.currentTimeMillis() - 93600000, false, "REWARD"),
            OBNotificationItemDataDTO("28", "Service Notice", "We've improved our server speeds.", System.currentTimeMillis() - 97200000, true, "GENERAL"),
            OBNotificationItemDataDTO("29", "Order Arrived", "Your order #105 has been left at your door.", System.currentTimeMillis() - 100800000, true, "ORDER"),
            OBNotificationItemDataDTO("30", "Promo Code", "Use 'SAVE10' for 10% off.", System.currentTimeMillis() - 104400000, false, "PROMO"),
            OBNotificationItemDataDTO("31", "Gold Status", "Congratulations, you are now Gold!", System.currentTimeMillis() - 108000000, false, "REWARD"),
            OBNotificationItemDataDTO("32", "Privacy Note", "Your data settings have been updated.", System.currentTimeMillis() - 111600000, true, "GENERAL"),
            OBNotificationItemDataDTO("33", "Processing", "We're preparing order #106.", System.currentTimeMillis() - 115200000, true, "ORDER"),
            OBNotificationItemDataDTO("34", "Limited Offer", "Only 5 items left in stock!", System.currentTimeMillis() - 118800000, false, "PROMO"),
            OBNotificationItemDataDTO("35", "Claim Reward", "Your gift card is ready to use.", System.currentTimeMillis() - 122400000, false, "REWARD"),
            OBNotificationItemDataDTO("36", "New Tutorial", "Check out our new help center articles.", System.currentTimeMillis() - 126000000, true, "GENERAL"),
            OBNotificationItemDataDTO("37", "Address Check", "Please confirm your delivery address.", System.currentTimeMillis() - 129600000, false, "ORDER"),
            OBNotificationItemDataDTO("38", "Flash Deal", "15 minutes remaining for this deal.", System.currentTimeMillis() - 133200000, false, "PROMO"),
            OBNotificationItemDataDTO("39", "Referral Bonus", "You earned 100 points for a referral.", System.currentTimeMillis() - 136800000, false, "REWARD"),
            OBNotificationItemDataDTO("40", "Password Change", "Security alert: password reset successful.", System.currentTimeMillis() - 140400000, true, "GENERAL"),
            OBNotificationItemDataDTO("41", "Order Confirmed", "We have received order #107.", System.currentTimeMillis() - 144000000, true, "ORDER"),
            OBNotificationItemDataDTO("42", "Member Only", "Early access to the summer sale.", System.currentTimeMillis() - 147600000, false, "PROMO"),
            OBNotificationItemDataDTO("43", "Point Milestone", "You hit 2000 points!", System.currentTimeMillis() - 151200000, false, "REWARD"),
            OBNotificationItemDataDTO("44", "System Note", "Scheduled maintenance on Sunday.", System.currentTimeMillis() - 154800000, true, "GENERAL"),
            OBNotificationItemDataDTO("45", "Courier Assigned", "Your delivery person is on the way.", System.currentTimeMillis() - 158400000, false, "ORDER"),
            OBNotificationItemDataDTO("46", "Weekend Sale", "Don't miss out on these deals.", System.currentTimeMillis() - 162000000, false, "PROMO"),
            OBNotificationItemDataDTO("47", "Reward Voucher", "Your voucher expires tomorrow.", System.currentTimeMillis() - 165600000, false, "REWARD"),
            OBNotificationItemDataDTO("48", "New Support", "Chat is now available 24/7.", System.currentTimeMillis() - 169200000, true, "GENERAL"),
            OBNotificationItemDataDTO("49", "Cancelled", "Order #108 was successfully cancelled.", System.currentTimeMillis() - 172800000, true, "ORDER"),
            OBNotificationItemDataDTO("50", "Welcome Back", "We missed you! Here is a coupon.", System.currentTimeMillis() - 176400000, false, "PROMO")
        )
    )


    override fun fetchNotificationsByPagination(
        skip: Int,
        limit: Int
    ): Flow<List<OBNotificationItemData>> = mockNotificationsState
        .map { mockNotifications->
            delay(1000)
            mockNotifications
                .drop(skip)
                .take(limit).mapNotNull {
                    it.toDomainModel()
                }
        }





    override suspend fun updateNotificationReadStatus(
        id: String,
        isRead: Boolean
    ): Boolean {
        mockNotificationsState.update { notifications ->
            notifications.map {
                if (it.id == id) {
                    it.copy(isRead = isRead)
                } else {
                    it
                }
            }.toMutableList()
        }
        return true
    }

    override suspend fun removeNotification(id: String): Boolean {
        mockNotificationsState.update { notifications->
           val newNotifications = notifications.filter {
               it.id != id
           }
            newNotifications
        }
        return true
        }

    override suspend fun setAllCurrentNotificationsRead(): Boolean {
        mockNotificationsState.update { notifications ->
            notifications.map {
                    it.copy(isRead = true)
            }.toMutableList()
        }
        return true
    }

    override suspend fun fetchUnReadNotificationsCount(): Long = mockNotificationsState.value.count {
        it.isRead.not()
    }.toLong()

}