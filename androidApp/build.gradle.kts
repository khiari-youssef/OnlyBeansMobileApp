plugins {
    id("onlybeans.android.application")
    id("kotlin-parcelize")
    alias(libs.plugins.compose.compiler)
    alias(libs.plugins.google.playservices)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")

}


android.run {
    namespace = "com.youapps.onlybeans.android"
    defaultConfig.applicationId = "com.youapps.onlybeans.android"
    defaultConfig.proguardFile("proguard-rules.pro")

}

dependencies {
    api(projects.core)
    implementation(projects.designsystem)
    implementation(projects.usersManagement)
    implementation(projects.marketplace)
    implementation(projects.searchModule)
    implementation(libs.bundles.composelibs)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.splashscreen)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.jetpack.viewmodel.core)
    implementation(platform(libs.firebase.bom))
    implementation(libs.firebase.analytics)
}