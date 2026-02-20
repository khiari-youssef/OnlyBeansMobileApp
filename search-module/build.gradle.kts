plugins {
    id("onlybeans.android.feature")
    alias(libs.plugins.compose.compiler)
    id("com.google.android.libraries.mapsplatform.secrets-gradle-plugin")
}

android.run {
    namespace = "com.youapps.onlybeans.search_module"
    defaultConfig.setConsumerProguardFiles(
        listOf("consumer-rules.pro")
    )
}

dependencies {
    implementation(projects.core)
    implementation(projects.designsystem)
    implementation(libs.kotlinx.datetime)
    implementation(libs.kotlin.coroutines)
    implementation(libs.androidx.splashscreen)
    debugImplementation(libs.compose.ui.tooling)
    implementation(libs.bundles.composelibs)
    implementation(libs.koin.core)
    implementation(libs.koin.android)
    implementation(libs.jetpack.viewmodel.core)
    implementation(libs.maps.compose)
    // (Optional) Maps Compose Utils (Clustering, etc.)
    implementation(libs.maps.compose.utils)
    // (Optional) Maps Compose Widgets (Scale bar, etc.)
    implementation(libs.maps.compose.widgets)
    // Base Maps SDK (Required for data models like LatLng)
    implementation(libs.play.services.maps)
}