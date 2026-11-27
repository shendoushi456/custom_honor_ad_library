


-keep class com.google.gson.reflect.TypeToken { *; }
-keep class * extends com.google.gson.reflect.TypeToken
-keep class * implements com.google.gson.TypeAdapterFactory
-keep class * implements com.google.gson.JsonSerializer
-keep class * implements com.google.gson.JsonDeserializer


#///////////////////////////
-keep class com.tencent.mmkv.MMKV{*;}
-keep class android.util.Log{*;}
-keep class kotlin.jvm.internal.Intrinsics{*;}
-keep class kotlin.text.StringsKt{*;}

-keep class com.clean.common_ad_libaray.bean.**{*;}

#日志混淆
# disable android logs
-assumenosideeffects class android.util.Log {
   public static *** v(...);
   public static *** d(...);
   public static *** i(...);
   public static *** w(...);
   public static *** e(...);
}
