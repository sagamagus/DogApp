# Add project specific ProGuard rules here.
# You can control the set of applied configuration files using the
# proguardFiles setting in build.gradle.
#
# For more details, see
#   http://developer.android.com/guide/developing/tools/proguard.html

# If your project uses WebView with JS, uncomment the following
# and specify the fully qualified class name to the JavaScript interface
# class:
#-keepclassmembers class fqcn.of.javascript.interface.for.webview {
#   public *;
#}

# Uncomment this to preserve the line number information for
# debugging stack traces.
#-keepattributes SourceFile,LineNumberTable

# If you keep the line number information, uncomment this to
# hide the original source file name.
#-renamesourcefileattribute SourceFile

# Moshi
-keep class com.squareup.moshi.** { *; }
-keepclassmembers class ** {
    @com.squareup.moshi.Json <fields>;
}

# Retrofit
-keep class retrofit2.** { *; }
-keep interface retrofit2.** { *; }

# Room
-keep class androidx.room.** { *; }
-keepclassmembers class * {
    @androidx.room.* <methods>;
}

# Koin
-keepclassmembers class * {
    @org.koin.core.annotation.* <methods>;
}

# ViewModels
-keep class androidx.lifecycle.ViewModel { *; }

# Data Classes
-keep class com.sagamagus.dogapp.domain.model.** { *; }

# Compose
-dontwarn kotlin.**
-keep class androidx.compose.** { *; }
