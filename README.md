# Demo App for SightCall Vision SDK Integration

## Overview

This repository contains a demo Android application designed to showcase and test the functionalities of the SightCall Vision SDK. 
The app demonstrates how to effectively integrate the Vision SDK into an Android project.

For detailed documentation and guidance on the SDK, refer to the [official SightCall Android SDK documentation](https://support.sightcall.com/hc/en-us/articles/16101832875668-Android-SDK-Documentation).

## Prerequisites

Before proceeding with the integration, ensure that your project meets the following requirements:

- **Minimum SDK Version**: `minSdkVersion 26`
- **AndroidManifest Configuration**:  
  If your project includes `android:allowBackup="false"` in the `<application>` element of your `AndroidManifest.xml`, you may need to add the following attribute to avoid conflicts:

  ```xml
  <application
      tools:replace="android:allowBackup">


## Implementation

### Using Gradle Version Catalog (Modern Approach)

1. Add the Maven repository for SightCall SDK:

   ```kotlin
   maven("https://sightcall-maven.s3.amazonaws.com")

2. In your `libs.versions.toml` file, define the SDK version and dependency configuration:

   ```toml
    [versions]
    sightcallSdk = "7.5.1" # Replace with the latest version

    [libraries]
    sightcall-sdk = { group = "com.sightcall.universal", name = "universal-sdk", version.ref = "sightcallSdk" }
    sigthcall-sdf = { group = "com.sightcall.sdf", name = "sdf", version.ref = "sightcallSdk" }


3. Add the dependency to your app-level `build.gradle` file:

    ```kotlin
    implementation(libs.sightcall.sdk)
    implementation(libs.sigthcall.sdf)

### Old Way (Without Version Catalog)

If you're not using the Gradle Version Catalog, follow these steps to add the SDK directly:

1. Add the Maven repository for SightCall SDK:

   ```gradle
       allprojects {
        repositories {
            maven {
                url "https://sightcall-maven.s3.amazonaws.com"
            }
        }
     }

2. Add the dependency to your app-level `build.gradle` file:

   ```gradle
   dependencies {
          implementation "com.sightcall.universal:universal-sdk:7.5.1" // Replace with the latest version
          implementation "com.sightcall.sdf:sdf:7.5.1" // Replace with the latest version
    }' 


#### Requirement

To properly integrate the SightCall Classic SDK, you must use a `Theme.AppCompat` or `MaterialComponents` theme (or one of their descendants) with the relevant activity. 
Below is an example of how to define a theme:

```xml
        <style name="Theme.VisionDemo" parent="Theme.MaterialComponents.Light.NoActionBar" />
```
### Picture-in-Picture (PiP) Feature

The Picture-in-Picture (PiP) feature allows a call to remain active in the background while users interact with other applications. This functionality enhances multitasking by providing users the flexibility to continue their video call in a smaller window while performing other tasks.

#### Requirements for PiP Feature

To enable and support the PiP feature in your app, you must display a notification for the background service handling the call. This notification requires a custom icon that will be shown when PiP mode is activated.

### Steps to Add a Custom Notification Icon

Follow the steps below to configure a custom notification icon for the PiP feature:

1. **Create a `launcher.xml` File**  
   In your project's `res/values` directory, create a file named `launcher.xml` (if it doesn't already exist).

2. **Define Your Custom Drawable Icon**  
   Inside the newly created `launcher.xml` file, add a reference to your custom notification icon. The structure of the file should look like this:

   ```xml
   <drawable name="universal_icon_notification" tools:override="true">
       @drawable/your_icon_name
   </drawable>
   
3. **Replace the Placeholder with Your Icon**
   Replace `your_icon_name` with the actual name of your custom drawable resource. For example, if your icon is called `icon_notification`, 
   the updated code would be:

  ```xml
   <drawable name="universal_icon_notification" tools:override="true">
    @drawable/icon_notification
   </drawable>
   ```

### Usage

Start your call using the following code snippet:

```kotlin
        val uri = Uri.parse("YOUR_URL_HERE")
        SightCall.start(uri)