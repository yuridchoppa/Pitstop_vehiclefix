# Pitstop Proguard & R8 Release Optimization Rules

# 1. Preserve Data Models & JSON Deserialization
-keep class com.example.vehiclefix.data.models.** { *; }
-keep class com.example.vehiclefix.data.repository.** { *; }

# 2. Gson Serialization Rules
-keepattributes Signature
-keepattributes *Annotation*
-keepclassmembers,allowobfuscation class * {
    @com.google.gson.annotations.SerializedName <fields>;
}
-keepclassmembers enum * {
    public static **[] values();
    public static ** valueOf(java.lang.String);
    **[] $VALUES;
    public *;
}

# 3. ViewBinding Protection
-keep class com.example.vehiclefix.databinding.** { *; }

# 4. AndroidX & Lifecycle Components
-keep class * extends androidx.lifecycle.ViewModel { *; }
-keep class androidx.lifecycle.DefaultLifecycleObserver
-keep class * implements androidx.lifecycle.LifecycleObserver { *; }

# 5. Prevent Stripping of Custom View Constructors (Material Components)
-keep public class * extends android.view.View {
    public <init>(android.content.Context);
    public <init>(android.content.Context, android.util.AttributeSet);
    public <init>(android.content.Context, android.util.AttributeSet, int);
    public void set*(...);
}
