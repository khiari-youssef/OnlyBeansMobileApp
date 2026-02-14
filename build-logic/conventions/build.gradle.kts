
plugins {
    `kotlin-dsl`
    `java-gradle-plugin`
}


dependencies{
    implementation(libs.android.gradlePlugin)
    implementation(libs.kotlin.gradlePlugin)
}

gradlePlugin{
   plugins {
       register("androidApplication") {
           id = "onlybeans.android.application"
           implementationClass = "AndroidApplicationConventionPlugin"
       }
       register("androidFeature") {
           id = "onlybeans.android.feature"
           implementationClass = "AndroidFeatureConventionPlugin"
       }
   }
}
